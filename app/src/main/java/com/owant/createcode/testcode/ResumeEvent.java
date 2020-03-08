package com.owant.createcode.testcode;

import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = "resume", product = Event.class)
public class ResumeEvent implements Event {

    @Override
    public void onEvent() {

    }
}
