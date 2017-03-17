AndroidMoreFunctionDemo
=======================
**package introduce**

1.des：3des算法，加密用的<br>

2.device：无用<br>

3.html：测试html调用摄像头问题，目前还未实现<br>

4.md5：md5<br>

5.photo：仿照微信上传图片的做法<br>

6.rsa:rsa加密用的<br>

7.util:常用工具类<br>

8.phototwo:拍身份证照截图<br>

9.AIDL<br>

10.不同渠道的打包方法<br>

11.很漂亮的优化布局方法<br>

12.调用其他app<br>

13.下拉刷新测试，<br>

**assets**

**so**

**jar**

**module**

1.cameracropper:自定义拍照界面，实现拍照完可以自定义剪切用法：
```
/* *跳转方法 */ private void intentPhotoAct(){ intentPhoto=new Intent(mContext,TakePhoteActivity.class); startActivityForResult(intentPhoto,requestCodePhoto); }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    MyLogger.aLog().i("requestCode=" + requestCode);
    MyLogger.aLog().i("resultCode=" + resultCode);
    if(resultCode==RESULT_OK){

        switch (requestCode) {
            case requestCodePhoto://拍照成功
               selectPhotoPahtResult(data.getData(),data.getStringExtra("path"));
                break;

        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
private void selectPhotoPahtResult(Uri uri,String filepath){ switch (index) { case R.id.iv_acc_err1:// fileSFZZMPath = filepath; ivewSFZZM.setImageURI(uri); ivewSFZZM_pz.setImageResource(R.mipmap.photo2); break;

   }
}
```
2.android-pulltorefresh:上下拉刷新，这个一个开源库，已经停止更新好多年，2012 年左右就出来，支持listview，scrollview，webview 等等的刷新
```
demo 示例都在 com.handmark.pulltorefresh.samples 下面，启动 Activity 是 LauncherActivity，源码是 eclipse 版的，这里我转成 AndroidStudio 版，如果您项目中要使用，
则直接导入android-pulltorefresh库就好，在导入过程中常见的问题就是以下配置和你主项目不一致
  > minSdkVersion 14
  > targetSdkVersion 23
  > compileSdkVersion
  > buildToolsVersion
  > com.android.support
  
这里 demo 在官方的基础上修改了 Math 计算问题

源码出自：https://github.com/chrisbanes/Android-PullToRefresh，源码作者还有一份改版 https://github.com/chrisbanes/ActionBar-PullToRefresh

```