package com.github.muxmax.juggrnotesapp.presentation.di;

import dagger.ObjectGraph;

public class Application extends android.app.Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        // this needs to set before the StreamSupport library is used because auf this bug: http://sourceforge.net/p/streamsupport/tickets/13/
        System.setProperty("java8.util.Spliterators.assume.oracle.collections.impl", "false");
        super.onCreate();
        objectGraph = ObjectGraph.create(new ApplicationModule(this));
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}