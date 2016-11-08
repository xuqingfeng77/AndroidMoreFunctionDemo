package eeepay.androidmorefunctiondemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by xqf on 2016/4/6.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
