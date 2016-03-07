// IRemoteServiceCallback.aidl
package eeepay.androidmorefunctiondemo.util;
import android.os.Bundle;
// Declare any non-default types here with import statements

interface IRemoteServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void startActivity(String paramString1, String paramString2, int paramInt, in Bundle paramBundle);
    void payEnd(boolean paramBoolean, String paramString1,String paramString2);
    boolean isHideLoadingScreen();

}
