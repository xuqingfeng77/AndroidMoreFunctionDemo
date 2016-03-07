package eeepay.androidmorefunctiondemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author xuqingfeng 获取apk信息
 * 
 */
public class ApkUtil {

	/**
	 * 检查apk是不是debug状态
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isApkDebugable(Context context,String packageName) {
		try {
			PackageInfo pkginfo = context.getPackageManager().getPackageInfo(
					packageName, 1);
			if (pkginfo != null ) {
				ApplicationInfo info= pkginfo.applicationInfo;
				return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
			}

		} catch (Exception e) {

		}
		return false;
	}
	

}
