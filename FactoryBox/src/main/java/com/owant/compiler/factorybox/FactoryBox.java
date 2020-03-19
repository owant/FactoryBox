package com.owant.compiler.factorybox;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * created by Kyle.Zhong on 2020-03-04
 */

@Target(ElementType.TYPE)
public @interface FactoryBox {

    /**
     * 产品的唯一标识
     */
    String key();

    /**
     * 实现的产品的接口
     */
    Class product();
}
