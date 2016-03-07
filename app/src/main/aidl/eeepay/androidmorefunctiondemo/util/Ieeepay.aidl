// Ieeepay.aidl
package eeepay.androidmorefunctiondemo.util;
import eeepay.androidmorefunctiondemo.util.IRemoteServiceCallback;
// Declare any non-default types here with import statements

interface Ieeepay {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    String Pay(String paramString);
    String test();
    void registerCallback(
             			IRemoteServiceCallback paramIRemoteServiceCallback);
    void unregisterCallback(
			IRemoteServiceCallback paramIRemoteServiceCallback);
}
