package com.icall.free;

import android.app.Application;
import android.content.Intent;

import com.android.pc.ioc.app.Ioc;
import com.icall.free.service.CoreService;

public class ICallApplication extends Application {

    private static ICallApplication mInstance = null;

    @Override
    public void onCreate() {
        Ioc.getIoc().init(this);
        super.onCreate();
        mInstance = this;
        startService(new Intent(this, CoreService.class));
    }

    public static ICallApplication getApplication() {
        if (mInstance == null) {
            return null;
        } else {
            return mInstance;
        }
    }
}
