package eeepay.androidmorefunctiondemo.html;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;

import eeepay.androidmorefunctiondemo.R;

public class CameraWebviewActivity extends AppCompatActivity {
    private final static String TAG = "CameraWebviewActivity";

    private Button bt;
    private WebView wv;
    public String fileFullName;//照相后的照片的全整路径
    private boolean fromTakePhoto; //是否是从摄像界面返回的webview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_webview);
        initViews();
    }

    private void initViews() {

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("----------------");
                takePhoto( Math.random()*1000+1 + ".jpg");
            }
        });

        wv = (WebView) findViewById(R.id.wv);
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        wv.setWebChromeClient(new WebChromeClient(){
            @Override//实现js中的alert弹窗在Activity中显示
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, message);
                result.confirm();
                return true;
            }
        });

//        wv.loadUrl("file:///android_asset/photo.html");
//        wv.loadUrl("file:///android_asset/index.html");
        //测试html代码注入功能更
        wv.loadUrl("file:///android_asset/intoHtm.html");
        final Handler mHandler = new Handler();
        //webview增加javascript接口，监听html页面中的js点击事件
        wv.addJavascriptInterface(new Object(){
            public String clickOnAndroid() {//将被js调用
                mHandler.post(new Runnable() {
                    public void run() {
                        fromTakePhoto  = true;
                        //调用 启用摄像头的自定义方法
                        takePhoto("testimg" + Math.random()*1000+1 + ".jpg");
                        System.out.println("========fileFullName: " + fileFullName);

                    }
                });
                return fileFullName;
            }
        }, "demo");
    }

    /*
     * 调用摄像头的方法
     */
    public void takePhoto(String filename) {
        System.out.println("----start to take photo2 ----");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");

        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
        //确定相片保存路径
        String targetDir = sdDir + "/" + "webview_camera";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileFullName = targetDir + "/" + filename;
        System.out.println("----taking photo fileFullName: " + fileFullName);
        //初始化并调用摄像头
        intent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileFullName)));
        startActivityForResult(intent, 1);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     * 重写些方法，判断是否从摄像Activity返回的webview activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("----requestCode: " + requestCode + "; resultCode " + resultCode + "; fileFullName: " + fileFullName);
        if (fromTakePhoto && requestCode ==1 && resultCode ==-1) {
            wv.loadUrl("javascript:wave2('" + fileFullName + "')");
        } else {
//            wv.loadUrl("javascript:wave2('Please take your photo')");
//            wv.loadUrl("javascript:tianchongcontentstr('<p>申请条件：<br\\/><\\/p><ol class=\\\" list-paddingleft-2\\\" style=\\\"list-style-type: decimal;\\\"><li><p>年龄18-55周岁<br\\/><\\/p><\\/li><li><p>芝麻分580分以上<\\/p><\\/li><li><p>身份认证，运营商，淘宝账号等<\\/p><\\/li><\\/ol><p><br\\/><\\/p><p><br\\/><\\/p>\n')");
            //注入htmp代码片段
            wv.loadUrl("javascript:tianchongcontentstr('<p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">审核方式：</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">线上审核，最快</span>10<span style=\"font-family:宋体\">分钟 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span></p><p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">申请条件： &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></p><p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:Calibri\">22</span><span style=\"font-family:宋体\">（含）</span><span style=\"font-family:Calibri\">~55</span><span style=\"font-family:宋体\">（含）周岁的私家车车主 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span></p><p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">所需材料： &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span></p><p style=\"text-align: left;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">中国大陆居民二代身份证、本人实名制手机号、本人行驶证 &nbsp; &nbsp; &nbsp;&nbsp;</span></span></p><p style=\"text-align: left;\"><span style=\"font-family: 宋体; font-size: 19px;\">授权认证： &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></p><p style=\"text-align: left;\"><span style=\"font-family: 宋体; font-size: 19px;\">身份证、车辆认证</span></p>\n')");

        }
        fromTakePhoto = false;
        super.onActivityResult(requestCode, resultCode, data);
    }



}
