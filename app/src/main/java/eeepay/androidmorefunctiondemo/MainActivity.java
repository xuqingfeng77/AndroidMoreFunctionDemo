package eeepay.androidmorefunctiondemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.samples.LauncherActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import eeepay.androidmorefunctiondemo.aidl.MobileSecurePayer;
import eeepay.androidmorefunctiondemo.bohao.BohaoActivity;
import eeepay.androidmorefunctiondemo.des.EncryptAct;
import eeepay.androidmorefunctiondemo.device.EncryptActivity;
import eeepay.androidmorefunctiondemo.edittext.EditActivity;
import eeepay.androidmorefunctiondemo.html.CameraWebviewActivity;
import eeepay.androidmorefunctiondemo.intentapp.MoreIntentActivity;
import eeepay.androidmorefunctiondemo.keyboard.KeyboardAct;
//import eeepay.androidmorefunctiondemo.kotlin.KotlinActivity;
import eeepay.androidmorefunctiondemo.payball.PayBallActivity;
import eeepay.androidmorefunctiondemo.perfectlayout.PerfectActivity;
import eeepay.androidmorefunctiondemo.permission.Main2Activity;
import eeepay.androidmorefunctiondemo.phototwo.ActivityCapture;
import eeepay.androidmorefunctiondemo.util.PhoneUtil;
import eeepay.androidmorefunctiondemo.webp.WebPActivity;

//import eeepay.mylibrary.MainActivity1;

public class MainActivity extends AppCompatActivity {
    private ListView mlistview;
    //
    private String[] mTransType;
    private Intent mIntent;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mlistview = (ListView) findViewById(R.id.listview);
        setAdapter();
        setListener();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
        }
    };

    private void setAdapter() {
        Resources mRes = MainActivity.this.getResources();
        mTransType = mRes.getStringArray(R.array.main_activity_items);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < mTransType.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("ItemText", mTransType[i]);
            listItem.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 锟斤拷锟皆�
                R.layout.list_items,//
                new String[]{"ItemText"}, new int[]{R.id.item_txt_name});

        mlistview.setAdapter(listItemAdapter);
    }

    private void setListener() {
        mlistview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                if (position == 0) {// 拍照
                    // JsonTest.jsonTestA();

                    mIntent = new Intent(MainActivity.this, eeepay.androidmorefunctiondemo.photo.MainActivity2.class);
                    MainActivity.this.startActivity(mIntent);

                } else if (position == 1) {

                    // api=18(4.3)才开始支持4.0，需要支持BLE通讯模块，需要包含LE
                    int api = PhoneUtil.getSDK();
                    boolean isSupportLE = getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_BLUETOOTH_LE);
                    Toast.makeText(mContext, "api=" + api + "\ngetPackageManager().hasSystemFeature(\n" +
                            "                            PackageManager.FEATURE_BLUETOOTH_LE)=" + getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE), Toast.LENGTH_LONG).show();

                } else if (position == 2) {

                    mIntent = new Intent(MainActivity.this, EncryptActivity.class);
                    MainActivity.this.startActivity(mIntent);
                } else if (position == 3) {
                    //defaultConfig {        resValue "string", "build_time", buildTime()        resValue "string", "build_host", hostName()        resValue "string", "build_revision", revision()    }
                    Toast.makeText(mContext, "包名：" + MainActivity.this.getPackageName() + "\n打包时间：" + getString(R.string.build_time) + "\n打包的主机信息：" + getString(R.string.build_host)+ "\n调试环境：" + BuildConfig.DEBUG, Toast.LENGTH_SHORT).show();

                } else if (position == 4) {//加密
                    mIntent = new Intent(MainActivity.this, EncryptAct.class);
                    MainActivity.this.startActivity(mIntent);

                } else if (position == 5) {//html调摄像头
                    mIntent = new Intent(MainActivity.this, CameraWebviewActivity.class);
                    MainActivity.this.startActivity(mIntent);
                } else if (position == 6) {
                    mIntent = new Intent(MainActivity.this, CacheActivity.class);
                    MainActivity.this.startActivity(mIntent);
                } else if (position == 7) {
                    mIntent = new Intent(MainActivity.this, ActivityCapture.class);
                    MainActivity.this.startActivity(mIntent);
                } else if (position == 8) {//AIDL测试
                    try {
                        // start the pay.
                        MobileSecurePayer msp = new MobileSecurePayer();
                        boolean bRet = msp
                                .pay(setOrderInfo(), handler, 0x01, (Activity) mContext);

                        if (bRet) {
                            // show the progress bar to indicate that we have started
                            // paying.

                        } else {

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(mContext, "远程回调失败", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (position == 9) {//渠道打包
                    Toast.makeText(mContext, "所属渠道：" + getApplicationMetaValue("UMENG_CHANNEL"), Toast.LENGTH_SHORT).show();
                } else if (position == 10) {//布局优化
                    mIntent = new Intent(MainActivity.this, PerfectActivity.class);
                    MainActivity.this.startActivity(mIntent);
                } else if (position == 11) {//多种调用其他app方法
                    mIntent = new Intent(MainActivity.this, MoreIntentActivity.class);
                    MainActivity.this.startActivity(mIntent);
                } else if(position==12){//下拉刷新测试
                    mIntent = new Intent(MainActivity.this, LauncherActivity.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==13){//权限测试
                    mIntent = new Intent(MainActivity.this, Main2Activity.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==14){//
                    mIntent = new Intent(MainActivity.this, EditActivity.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==15){//
                    mIntent = new Intent(MainActivity.this, KeyboardAct.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==16){//webp测试 4.0-4.2.1 和 4.2.1+
                    mIntent = new Intent(MainActivity.this, WebPActivity.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==17){//kotlin测试
//                    mIntent = new Intent(MainActivity.this, KotlinActivity.class);
//                    MainActivity.this.startActivity(mIntent);
                }else if(position==18){//kotlin测试
                    mIntent = new Intent(MainActivity.this, PayBallActivity.class);
                    MainActivity.this.startActivity(mIntent);
                }else if(position==19){//kotlin测试
                    mIntent = new Intent(MainActivity.this, BohaoActivity.class);
                    MainActivity.this.startActivity(mIntent);
                }
//                Toast.makeText(mContext, "打包时间：" + getString(R.string.build_time) + "\n打包的主机信息：" + getString(R.string.build_host), Toast.LENGTH_SHORT).show();


            }
        });
    }

    /**
     * 拼接json串
     */
    private String setOrderInfo() throws Exception {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("TradeSn", "asdfasfa");
        jsonObj.put("TranType", "");
        jsonObj.put("mode", "01");//0测试；1生产
        String jsonStr = jsonObj.toString();
        return jsonStr;
    }

    private String getApplicationMetaValue(String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }
}
