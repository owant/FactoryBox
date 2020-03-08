package com.owant.compiler.create;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
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
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(packageName, productName))
                .addParameter(String.class, "key");

        int size = productChildes.size();
        for (int i = 0; i < size; i++) {
            Product product = productChildes.get(i);
            if (i == 0) {
                methodCreateBuilder
                        .beginControlFlow("if($S.equals(key))", product.key)
                        .addStatement("return new "+
                                product.className +"()");
            } else {
                methodCreateBuilder.nextControlFlow("else if($S.equals(key))", product.key)
                        .addStatement("return new $T()",
                                ClassName.get(packageName, product.className));
            }
        }

        MethodSpec methodCreate = methodCreateBuilder
                .nextControlFlow("else")
                .addStatement("throw new $T($T.format(\"没有到key=%s对应的实现\",key))",Exception.class,String.class)
                .endControlFlow()
                .build();

        System.err.println();
        //类
        TypeSpec typeMain = TypeSpec.classBuilder(createClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
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
