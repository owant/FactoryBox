package com.owant.createcode;

import android.content.Context;
import com.owant.compiler.factorybox.FactoryBox;
import com.owant.createcode.testcode.Event;
import com.owant.createcode.testcode.Events;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = Events.create,
        product = Event.class,
        constructorName = {"context","title"},
        constructorType = {Context.class,String.class})
public class CreateEvent implements Event {

    private Context context;

    public CreateEvent(Context context) {
        this.context = context;
    }

    @Override
    public void onEvent() {

    }
}
