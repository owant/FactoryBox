package com.owant.compiler.create;

import java.util.Objects;

/**
 * created by Kyle.Zhong on 2020-03-29
 */

public class InputModel {

    public String productQualifiedName;

    public String typeQualifiedName;

    public String typeName;

    public InputModel(String productQualifiedName, String typeQualifiedName, String typeName) {
        this.productQualifiedName = productQualifiedName;
        this.typeQualifiedName = typeQualifiedName;
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InputModel that = (InputModel) o;
        return productQualifiedName.equals(that.productQualifiedName) &&
                typeQualifiedName.equals(that.typeQualifiedName) &&
                typeName.equals(that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productQualifiedName, typeQualifiedName, typeName);
    }
}
