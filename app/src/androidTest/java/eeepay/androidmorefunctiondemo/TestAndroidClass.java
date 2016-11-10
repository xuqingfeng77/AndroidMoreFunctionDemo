package eeepay.androidmorefunctiondemo;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by xqf on 2016/11/10.
 */

public class TestAndroidClass extends InstrumentationTestCase {

    private static final String TAG = "TestAndroidClass";

    public void test() throws Exception{
        assertEquals(2, 2);
        Log.d(TAG,"result=................");
    }
    public void aaTest() throws Exception{
        assertEquals(2, 2);
        Log.d(TAG,"result=................");
    }
}

