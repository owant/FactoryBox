package com.owant.createcode.testcode;

import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = "destroy", product = Event.class)
public class DestroyEvent implements Event {

    @Override
    public void onEvent() {

    }
}
