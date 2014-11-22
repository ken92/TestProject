package edu.unh.cs.cs619_2014_project2.g1.server;

import com.google.common.eventbus.EventBus;

public class BusProvider {
    final static EventBus bus = new EventBus();

    public static EventBus getInstance(){
        return bus;
    }

    private BusProvider(){
    }
}