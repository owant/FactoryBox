package com.owant.compiler.factorybox;

import com.google.auto.service.AutoService;
import com.owant.compiler.create.CollectionProduct;
import com.owant.compiler.create.FactoryTemp;
import com.owant.compiler.create.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * created by Kyle.Zhong on 2020-03-04
 */

@SupportedAnnotationTypes("com.owant.compiler.factorybox.FactoryBox")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class FactoryBoxProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private Messager mMessager;

    private CollectionProduct products;
    private TreeSet<String> productTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        products = new CollectionProduct();
        productTypes = new TreeSet<>();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //找到注解的类，并把实现的接口进行分类
        productTypes.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(FactoryBox.class)) {

            String key = element.getAnnotation(FactoryBox.class).key();
            //获取注解的所有文本情况
            String annotationValue = element.getAnnotationMirrors().get(0).toString()
                    .replaceAll("\"", "");
            //实现的接口
            String interfaceName = productInterface("product", annotationValue);
            //被注解所在的包
            String productPackageName = mElementUtils.getPackageOf(element).getQualifiedName()
                    .toString();
            //被注解的类
            String className = productPackageName + "." + element.getSimpleName().toString();

            Product product = new Product(key, interfaceName, className);
            productTypes.add(interfaceName);
            boolean multiple = products.addProduct(product);
            if (!multiple) {
                printlnProcessorError("发现有重复的Key接口实现:" + product.toString());
                return true;
            }
        }

        //接口
        for (String productInterfaceName : productTypes) {
            String factoryPackage = productInterfaceName
                    .substring(0, productInterfaceName.lastIndexOf("."));
            String productName = productInterfaceName
                    .substring(productInterfaceName.lastIndexOf(".") + 1);

            List<Product> childPrs = new ArrayList<>();
            for (Product product : products.getProducts()) {
                if (product.fatherClassName.equals(productInterfaceName)) {
                    childPrs.add(product);
                }
            }

            FactoryTemp.create(processingEnv,
                    factoryPackage,
                    productName,
                    productName + "Factory",
                    childPrs
            );
        }
        return true;

    }

    public static String productInterface(String annotationClassKey, String context) {
        String productFatherName = "";
        String patternKey = String.format("%s=([^,\\s]*).class", annotationClassKey);
        Pattern pattern = Pattern.compile(patternKey);
        Matcher matcher = pattern.matcher(context);
        if (matcher.find()) {
            productFatherName = matcher.group(1);
        }
        return productFatherName;
    }

    private void printlnProcessorInfo(String info) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, info);
    }

    private void printlnProcessorError(String info) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, info);
    }

}
