package com.horf.derp.testproject;

import com.google.common.eventbus.EventBus;

public class BusProvider {
    final static EventBus bus = new EventBus();

    public static EventBus getInstance(){
        return bus;
    }

    private BusProvider(){
    }
}