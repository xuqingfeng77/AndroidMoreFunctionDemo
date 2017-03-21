package eeepay.androidmorefunctiondemo.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by Kenn on 2016/6/22.
 * 检查权限类
 * 安卓6.0以后权限需要手动设置
 */
public class CheckPermissionUtil {

    private static final String TAG ="CheckPermissionUtil";
    public interface onPermissionListener{
        void  onSuccess();
        void onFailure();
    };
    public static onPermissionListener listener;
    /**
     *  @param context
     * @param permissions
     * @param v   随便传一个界面上的view，最终都会显示在底部
     * @param requestCode
     */
    public static  void checkPermission(Activity context, String[] permissions, View v, int requestCode){
        if (Build.VERSION.SDK_INT>=23) {//6.0以上
            for (String s : permissions) {
                if (ActivityCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(context, permissions,v, requestCode);
                    return;
                }
            }
        }
        if(listener!=null){
            listener.onSuccess();
        }
    }

    private static void requestPermissions(final Activity context, final String[] permissions, View v, final int requestCode) {
        boolean Flag =true;
        for (String s : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, s)) {
                Flag=true;
            }
        }
        if(Flag){
//            Snackbar.make(v, "是否允许打开权限设置？",
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(context, permissions,
                                            requestCode);
//                        }
//                    })
//                    .show();

        }else{
            ActivityCompat
                    .requestPermissions(context, permissions,
                            requestCode);
        }
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            if(listener!=null){
                listener.onFailure();
            }
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                if(listener!=null){
                    listener.onFailure();
                }
                return false;
            }
        }
        if(listener!=null){
            listener.onSuccess();
        }
        return true;
    }

}
