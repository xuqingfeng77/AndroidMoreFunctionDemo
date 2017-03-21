package eeepay.androidmorefunctiondemo.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import eeepay.androidmorefunctiondemo.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 *@author : xqf
 *@date   :2017/3/21 下午2:03
 *@desc   :权限管理测试类
 *@update :
 */
@RuntimePermissions
public class Main2Activity extends AppCompatActivity {

private String TAG="Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void OnBtnPhoneClick(View v) {

//        requestCode=0
        Main2ActivityPermissionsDispatcher.testCallPhoneNeedsWithCheck(this);
    }
    public void OnBtnClick(View v){
//        requestCode=1
        Main2ActivityPermissionsDispatcher.testWriteNeedsPermissionWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE})
    void testWriteNeedsPermission() {
        Toast.makeText(this, "已经授权成功，可以执行方法", Toast.LENGTH_SHORT).show();
        File cacheFile = null;
        String strIMEI=getDeviceIMEI(Main2Activity.this);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheFile = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "www7578");
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            cacheFile = new File(cacheFile + File.separator + "ay.apk");

        } else {

        }
    }

    //点击系统对话框的拒绝按钮就会回调这方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Main2ActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        Log.d(TAG,"onRequestPermissionsResult=permissions"+permissions+"\nrequestCode="+requestCode);
//        Toast.makeText(this, "onRequestPermissionsResult=permissions"+permissions+"\nrequestCode="+requestCode, Toast.LENGTH_SHORT).show();
    }

    //勾选了系统权限提示对话框的"不在提示"按钮，就会回调这个方法，MIUI系统则不会回调这方法
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE})
    void testOnShowRationale(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_readphone_rationale, request);

    }
    //拒绝其中任何一个权限都会回调下面的方法
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE})
    void testOnPermissionDenied() {
        Toast.makeText(this, "存储和电话，你拒绝了权限", Toast.LENGTH_SHORT).show();
    }
    //不再询问已经被勾选,也就是不会弹出系统的授权询问的对话框
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE})
    void testOnNeverAskAgain() {
        gotoSetting();
    }


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void testCallPhoneNeeds() {
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "4006109993"));
//启动
        startActivity(phoneIntent);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void testCallPhoneOnshow(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_contacts_rationale, request);
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void testCallPhoneONper() {
        Toast.makeText(this, "您拒绝了打电话权限", Toast.LENGTH_SHORT).show();

    }
///拒绝授权打电话，已经勾选了不再提示
    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void testCallPhoneOnNever() {
        gotoSetting();

    }
    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
    private void gotoSetting(){
        new AlertDialog.Builder(this)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + Main2Activity.this.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setMessage("去设置中心打开权限")
                .show();
    }
    /**
     * 获取设备id（IMEI）
     *
     * @param context
     * @return
     */
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephony.getDeviceId();
        if (deviceId == null) {
            deviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return deviceId != null ? deviceId : "Unknown";
    }

}
