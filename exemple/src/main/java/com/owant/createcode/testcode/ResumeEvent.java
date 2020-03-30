package com.owant.createcode.testcode;

import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(
        key = Events.resume,
        product = Event.class,
        constructorType = {String.class},
        constructorName = {"title"}

)
public class ResumeEvent implements Event {

    String title;

    public ResumeEvent(String title) {
        this.title = title;
    }

    @Override
    public void onEvent() {

    }
}
