package com.owant.compiler.create;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * kylezhong@etekcity.com.cn
 *
 * 2020-03-05 18:30
 */
public class FactoryTemp {


    public static void create(ProcessingEnvironment processingEnvironment,
            String packageName,
            String productName,
            String createClassName,
            List<Product> productChildes) {

        //函数
        MethodSpec.Builder methodCreateBuilder = MethodSpec
                .methodBuilder("create")
                .addException(Exception.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(packageName, productName))
                .addParameter(String.class, "key")
                .addStatement("$T cache=products.get(key)", ClassName.get(packageName, productName))
                .beginControlFlow("if(cache!=null)")
                .addStatement("return cache")
                .endControlFlow();

        int size = productChildes.size();
        for (int i = 0; i < size; i++) {
            Product product = productChildes.get(i);
            String productKey = product.key;
            String productClassName = product.className;
            String productClassSimpleName = product.className
                    .substring(productClassName.lastIndexOf(".") + 1);
            String productPackageName = productClassName
                    .substring(0, productClassName.lastIndexOf("."));

            if (i == 0) {
                methodCreateBuilder
                        .beginControlFlow("if($S.equals(key))", productKey)
                        .addStatement("products.put(key , new $T())",
                                ClassName.get(productPackageName, productClassSimpleName));
            } else {
                methodCreateBuilder.nextControlFlow("else if($S.equals(key))", productKey)
                        .addStatement("products.put(key , new $T())",
                                ClassName.get(productPackageName, productClassSimpleName));
            }
        }

        MethodSpec methodCreate = methodCreateBuilder
                .nextControlFlow("else")
                .addStatement("throw new $T($T.format(\"没有到key=%s对应的实现\",key))", Exception.class,
                        String.class)
                .endControlFlow()
                .addStatement("return products.get(key)")
                .build();

        ClassName productClassName = ClassName.get(packageName, productName);
        ClassName hasMap = ClassName.get(HashMap.class);
        TypeName hasMapStringProduct = ParameterizedTypeName
                .get(hasMap, TypeName.get(String.class), productClassName);

        //变量
        FieldSpec events = FieldSpec
                .builder(hasMapStringProduct, "products")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this.$N = new $T<$T,$T>()", "products", HashMap.class, String.class,
                        ClassName.get(packageName, productName))
                .build();

        //类
        TypeSpec typeMain = TypeSpec.classBuilder(createClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(events)
                .addMethod(constructor)
                .addMethod(methodCreate)
                .build();

        //包名
        JavaFile javaFile = JavaFile.builder(packageName, typeMain).build();

        try {
            javaFile.writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {

        }

    }
}
