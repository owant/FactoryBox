package com.owant.createcode;

import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = "event2", product = AbstractAction.class)
public class DoAbstractAction implements AbstractAction {

    @Override
    public void onClick() {
        System.out.println("ddo do");
    }
}
