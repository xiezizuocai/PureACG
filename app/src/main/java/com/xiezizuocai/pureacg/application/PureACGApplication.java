package com.xiezizuocai.pureacg.application;

import android.app.Application;

import org.xutils.x;

public class PureACGApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }
}
