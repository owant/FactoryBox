package com.owant.compiler.create;

import java.util.ArrayList;

/**
 * kylezhong@etekcity.com.cn
 *
 * 2020-03-05 18:16
 */
public class CollectionProduct {

    private ArrayList<Product> products = new ArrayList<>();

    public CollectionProduct() {
    }

    public boolean addProduct(Product product) {
        if (products.contains(product)) {
            return false;
        } else {
            products.add(product);
        }
        return true;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
