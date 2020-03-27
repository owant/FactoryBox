package com.owant.createcode;

import com.owant.compiler.factorybox.FactoryBox;
import com.owant.createcode.testcode.Event;
import com.owant.createcode.testcode.Events;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = Events.create, product = Event.class)
public class CreateEvent implements Event {

    @Override
    public void onEvent() {

    }
}
