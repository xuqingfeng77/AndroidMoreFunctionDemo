package eeepay.androidmorefunctiondemo.keyboard;

/**
 * @分类: 系统公共类,使用Log4j打印日志
 * @功能: 自动打印日志所在类,方法等信息
 * 
 * DEBUG 指出细粒度信息事件对调试应用程序是非常有帮助的。      
 * INFO 表明 消息在粗粒度级别上突出强调应用程序的运行过程。    
 * WARN 表明会出现潜在错误的情形。   
 * ERROR 指出虽然发生错误事件，但仍然不影响系统的继续运行。   
 * FATAL 指出每个严重的错误事件将会导致应用程序的退出。
 * 
 * @since: API Level 1 (Android1)
 * @company: eeepay Co.,Ltd
 * 
 * @author: TangHua(2011-4-10)
 * @modify: xxx(2011-3-31)
 */

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.util.Log;

public class Log4j {
	static final boolean DEFLOGOUT = true;
	static final String DEFLOGLEVEL = "i";

	// 根据包名,存储不同的打印方式
	static Map<String, Log4j> map = new TreeMap<String, Log4j>();

	// 打印stack级别
	static int stackLevel = 3;

	// 调试信息参数
	boolean logDebug = DEFLOGOUT; // 是否是调试状态
	String logLevel = DEFLOGLEVEL; // Log4j信息级别: v,d,i,w,e

	static public void init() {
		newInstance("com.eeepay.android", DEFLOGOUT, DEFLOGLEVEL);
		newInstance("com.eeepay.component", DEFLOGOUT, DEFLOGLEVEL);
	}

	static public void newInstance(String pack, boolean logDebug,
			String logLevel) {
		Log4j log4j = new Log4j(logDebug, logLevel);
		map.put(pack, log4j);
	}

	public static void debug(String msg) {
//	    boolean logout = false;
	    boolean logout = true;

		// 获取tag
		ThrowStack throwStack = new ThrowStack(stackLevel);
		Log4j log4j = getPackLog4j(throwStack.getPack());

		// 打印信息
		if (logout) {
		    log4j.print("yetii", "[" + throwStack.getTag() + "]" + msg);
		}
	}

	public static void debug(Exception e) {

		// 获取tag
		ThrowStack throwStack = new ThrowStack(stackLevel);
		Log4j log4j = getPackLog4j(throwStack.getPack());

		// 不调试,则不打印任何信息
		if (log4j.logDebug) {
			e.printStackTrace();
		}
	}

	private Log4j() {
		this.logDebug = true;
		this.logLevel = "i";
	}

	private Log4j(boolean logDebug, String logLevel) {
		this.logDebug = logDebug;
		this.logLevel = logLevel;
	}

	/**
	 * 统一调试打印级别,使用INFO级别打印 注: 由于内部模块一般使用DEBUG打印,为了不使打印信息混淆, 应用信息打印上调为INFO基本
	 * 
	 * @param tag
	 *            : 打印标识
	 * @param msg
	 *            : 打印信息
	 */

	private void print(String tag, String msg) {

		if (!logDebug)
			return;

		// 信息预处理
		if (msg == null) {
			msg = "[null]";
		}

		// 打印v,d,i,w,e
		if (logLevel.equalsIgnoreCase("v")) {
			Log.v(tag, msg);
		} else if (logLevel.equalsIgnoreCase("d")) {
			Log.d(tag, msg);
		} else if (logLevel.equalsIgnoreCase("w")) {
			Log.w(tag, msg);
		} else if (logLevel.equalsIgnoreCase("e")) {
			Log.e(tag, msg);
		} else {
			Log.i(tag, msg); // default "i"
		}
	}

	static private Log4j getPackLog4j(String pack) {
		Iterator<Entry<String, Log4j>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Log4j> entry = it.next();
			if (pack.startsWith(entry.getKey())) {
				return entry.getValue();
			}
		}
		return new Log4j();
	}

}
