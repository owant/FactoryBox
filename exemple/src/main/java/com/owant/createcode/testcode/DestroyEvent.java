package com.owant.createcode.testcode;

import android.content.Context;
import com.owant.compiler.factorybox.FactoryBox;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

@FactoryBox(key = "destroy",
        product = Event.class,
        constructorName = {"context"},
        constructorType = {Context.class})
public class DestroyEvent implements Event {

    private Context context;

    public DestroyEvent(Context context) {
        this.context = context;
    }

    @Override
    public void onEvent() {

    }
}
