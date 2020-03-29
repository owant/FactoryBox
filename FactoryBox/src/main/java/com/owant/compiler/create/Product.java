package com.owant.compiler.create;

import java.util.ArrayList;
import java.util.Objects;

/**
 * kylezhong@etekcity.com.cn
 *
 * 2020-03-05 18:14
 */
public class Product {

    public String key;
    public String fatherClassName;
    public String className;

    ArrayList<InputModel> constructorArray;

    public Product(String key, String fatherClassName, String className) {
        this.key = key;
        this.fatherClassName = fatherClassName;
        this.className = className;

        constructorArray = new ArrayList<>();
    }

    public ArrayList<InputModel> getConstructorArray() {
        return constructorArray;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(key, product.key) &&
                Objects.equals(fatherClassName, product.fatherClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, fatherClassName);
    }


    @Override
    public String toString() {
        return "Product{" +
                "key='" + key + '\'' +
                ", fatherClassName='" + fatherClassName + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
