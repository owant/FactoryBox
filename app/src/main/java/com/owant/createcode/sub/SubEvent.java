package com.owant.createcode.sub;

import com.owant.compiler.factorybox.FactoryBox;
import com.owant.createcode.testcode.Event;

/**
 * created by Kyle.Zhong on 2020-03-07
 */

@FactoryBox(key = "sub_event", product = Event.class)
public class SubEvent implements Event {

    @Override
    public void onEvent() {

    }
}
