package eeepay.androidmorefunctiondemo.keyboard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

public class Act {
	
	/**
	 * dp值转化成px值
	 * @param ctx
	 * @param dpValue
	 * @return
	 */
	public static int dp2px(Context ctx, float dpValue){
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (dpValue/scale + 0.5f);
	}
	
	public static int px2dp(Context ctx, float pxValue){
		final float scale = ctx.getResources().getDisplayMetrics().density;
		return (int) (pxValue * scale + 0.5f);
	}
    
    /**
     * 
     * 功能：获取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx){
        return  ((Activity) ctx).getWindowManager().getDefaultDisplay().getWidth();
    }
    /**
     * 
     * 功能：获取屏幕高度
     * @param ctx
     * @return
     */
    public static int getScreenHeight(Context ctx){
        return ((Activity) ctx).getWindowManager().getDefaultDisplay().getHeight();
    }
    
    /**
     * 
     * 功能：获取程序版本名
     * @param ctx
     * @return verName 版本名称
     */
    public static String getVerName(Context ctx){
        return String.valueOf(getPi(ctx).versionName);
    }
    /**
     * 
     * 功能：获取程序版本码
     * @param ctx
     * @return verCode 版本码
     */
    public static String getVerCode(Context ctx){
        return String.valueOf(getPi(ctx).versionCode);
    }

    public static PackageInfo getPi(Context ctx){
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }//getPackageName表示获取当前ctx所在的包的名称，0表示获取版本信息
        return pi;
    }
    
    

	/**
	 * 去掉标题栏，并全屏显示
	 * 
	 * @param activity
	 */
	public static void setNoTitleFullScreen(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 在activity上显示ProgressBar 注: 在生成setContentView之前调用
	 * 
	 * @param activity
	 */
	public static void enableProgressBar(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_PROGRESS);
		activity.setProgressBarVisibility(false);
	}

	public static void lauchIntent(Context packageContext, Class<?> nextCls) {
		Intent intent = new Intent(packageContext, nextCls);
		packageContext.startActivity(intent);
	}

	public static void lauchIntent(Context packageContext, String className) {
		if (TextUtils.isEmpty(className))
			return;
		Intent intent = getIntent(packageContext, className);
		packageContext.startActivity(intent);
	}

	public static Intent getIntent(Context packageContext, String className) {
		if (TextUtils.isEmpty(className))
			return null;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClassName(packageContext, className);
		return intent;
	}

	public static Intent getIntent(Context packageContext, Class<?> nextCls) {
		Intent intent = new Intent(packageContext, nextCls);
		return intent;
	}

	public static Intent getIntent(Context packageContext, String className,
			Bundle bundle) {
		if (TextUtils.isEmpty(className))
			return null;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClassName(packageContext, className);
		intent.putExtras(bundle);
		return intent;
	}

	public static Intent getIntent(Context packageContext, Class<?> nextCls,
			Bundle bundle) {
		Intent intent = new Intent(packageContext, nextCls);
		intent.putExtras(bundle);
		return intent;
	}

	public static void lauchIntent(Context packageContext, String className,
			String parKey, Parcelable parcelable) {

		// 生产Intent
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClassName(packageContext, className);

		// 设置要传递的参数
		Bundle bundle = new Bundle();
		bundle.putParcelable(parKey, parcelable);
		intent.putExtras(bundle);

		// 显示新的UI
		packageContext.startActivity(intent);
	}

	// /**
	// * 完全退出程序
	// *
	// * 注,必须添加以下权限
	// * <!-- 关闭应用程序的权限 -->
	// * <uses-permission android:name="android.permission.RESTART_PACKAGES" />
	// *
	// * @param context
	// * @param packageName
	// */
	// public static void exit(Context context){
	// ActivityManager
	// activityMgr=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	// activityMgr.restartPackage(context.getPackageName());
	// android.os.Process.killProcess(android.os.Process.myPid());
	// }
	//	

	/**
	 * 完全退出程序
	 * 
	 * 注,必须添加以下权限 <!-- 关闭应用程序的权限 --> <uses-permission
	 * android:name="android.permission.RESTART_PACKAGES" /> <uses-permission
	 * android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
	 * 
	 * @param context
	 * @param packageName
	 */
	public static void exit(Context context) {
		int iSdk = SysInfo.getSDK();
		if (iSdk > 7) {
			// 2.2以上版本退出
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMain);
			System.exit(0);// 退出程序

			// ActivityManager
			// activityMgr=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
			// activityMgr.restartPackage(context.getPackageName());
			// activityMgr.killBackgroundProcesses(context.getPackageName());
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(0);//退出程序
		} else {
			// 2.1以下版本退出
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			am.restartPackage(context.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
			// am.killBackgroundProcesses(context.getPackageName());
		}
	}

}
