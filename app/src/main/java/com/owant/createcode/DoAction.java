package com.owant.createcode;

import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = "event", product = AbstractAction.class)
public class DoAction implements AbstractAction {

    @Override
    public void onClick() {
        System.out.println("Hallo");
    }
}
