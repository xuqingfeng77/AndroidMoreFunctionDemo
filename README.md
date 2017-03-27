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

14.权限框架测试，PermissionsDispatcher使用<br>

15.test

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
3.PermissionsDispatcher:权限库和 androidstudio plugin（20170321)
开源项目地址：https://github.com/hotchemi/PermissionsDispatcher
```
安装 PermissionsDispatcher 方法

1.project build.gradle 添加 classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
2.app build.gradle 添加 apply plugin: 'android-apt' 和dependencies中添加  compile 'com.github.hotchemi:permissionsdispatcher:2.3.2'
apt 'com.github.hotchemi:permissionsdispatcher-processor:2.3.2'

安装PermissionsDispatcher plugin 插件（Mac环境）

1.Preferences-Plugins -搜索 PermissionsDispatcher plugin
2.安装完重启就好
3.command+N，选择Generate Runtime Permission
4.插件生产的几个方法介绍
NeedsPermission:涉及到权限的，需要执行的方法
OnShowRationale:第一次拒绝后，第二次申请会走的方法，一般在这提示该权限使用目的; MIUI 系统不会回调这个方法, MIUI 系统安装完app会自动授权一些基本权限
OnPermissionDenied:权限被拒绝后被回调的方法
OnNeverAskAgain:第二次申请后，会出现"拒绝后不再询问"勾选，如果勾选了，再次申请权限就会走这个方法


注意事项：

每次在新的一个类里使用插件功能生产都需要重新 build 一次；
那些 6.0 权限一定要在 AndroidManifest 里有写，然后需要申请的再去申请
例如 eeepay.androidmorefunctiondemo.permission.Main2Activity 
如果 READ_PHONE_STATE 在 AndroidManifest 中没用申请，则这个权限永远都没法授权成功

```