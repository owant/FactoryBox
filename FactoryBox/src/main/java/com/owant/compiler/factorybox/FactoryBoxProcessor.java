package com.owant.compiler.factorybox;

import com.google.auto.service.AutoService;
import com.owant.compiler.create.CollectionProduct;
import com.owant.compiler.create.FactoryTemp;
import com.owant.compiler.create.InputModel;
import com.owant.compiler.create.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * created by Kyle.Zhong on 2020-03-04
 */

@SupportedAnnotationTypes("com.owant.compiler.factorybox.FactoryBox")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class FactoryBoxProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;

    private CollectionProduct products;
    private TreeSet<String> productTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        products = new CollectionProduct();
        productTypes = new TreeSet<>();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //找到注解的类，并把实现的接口进行分类
        productTypes.clear();

        for (Element typeElement : roundEnvironment.getElementsAnnotatedWith(FactoryBox.class)) {

            List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {

                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror
                        .getElementValues();

                String keyValue = "";
                String productClassName = "";
                List<? extends AnnotationValue> constructorNamesArray = null;
                List<? extends AnnotationValue> constructorTypeArray = null;

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues
                        .entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    Object value = entry.getValue().getValue();
                    switch (key) {
                        case "key":
                            keyValue = (String) value;
                            break;
                        case "product":
                            TypeMirror typeMirror = (TypeMirror) value;
                            productClassName = typeMirror.toString();
                            break;
                        case "constructorName":
                            constructorNamesArray = (List<? extends AnnotationValue>) value;
                            break;
                        case "constructorType":
                            constructorTypeArray = (List<? extends AnnotationValue>) value;
                            break;
                        default:
                            break;
                    }
                }

                //被注解所在的包
                String productPackageName = elementUtils.getPackageOf(typeElement)
                        .getQualifiedName()
                        .toString();

                //被注解的类
                String className =
                        productPackageName + "." + typeElement.getSimpleName().toString();

                if (productClassName == null || productClassName.length() == 0
                        || keyValue == null && keyValue.length() == 0) {
                    printlnProcessorInfo("productClassName is empty");
                } else {
                    Product product = new Product(keyValue, productClassName, className);
                    productTypes.add(productClassName);
                    printlnProcessorInfo("添加构建：" + product.toString() + "\t");

                    if (constructorNamesArray != null && constructorTypeArray != null) {
                        int size = constructorNamesArray.size();
                        if (size == constructorTypeArray.size()) {
                            for (int i = 0; i < size; i++) {

                                String typeQualifiedName = ((TypeMirror) constructorTypeArray.get(i)
                                        .getValue()).toString();
                                String typeName = constructorNamesArray.get(i).getValue()
                                        .toString();

                                InputModel inputModel = new InputModel(productClassName,
                                        typeQualifiedName,
                                        typeName);

                                product.getConstructorArray().add(inputModel);
                            }
                        }
                    }

                    boolean multiple = products.addProduct(product);
                    if (!multiple) {
                        printlnProcessorError("发现有重复的Key接口实现:" + product.toString());
                        return true;
                    }
                }
            }
        }

        for (String productInterfaceName : productTypes) {
            String factoryPackage = productInterfaceName
                    .substring(0, productInterfaceName.lastIndexOf("."));
            String productName = productInterfaceName
                    .substring(productInterfaceName.lastIndexOf(".") + 1);

            List<Product> childPrs = new ArrayList<>();
            for (Product product : products.getProducts()) {
                if (product.productFatherClassName.equals(productInterfaceName)) {
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

    private void printlnProcessorInfo(String info) {
        messager.printMessage(Diagnostic.Kind.NOTE, info);
    }

    private void printlnProcessorError(String info) {
        messager.printMessage(Diagnostic.Kind.ERROR, info);
    }

}
