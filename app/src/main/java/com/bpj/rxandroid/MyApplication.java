package com.bpj.rxandroid;

import android.app.Application;
import android.util.Log;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Ray on 2018/3/12 .
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("MyApplication", "throw throwable="+throwable.getMessage());
            }
        });
    }
}
