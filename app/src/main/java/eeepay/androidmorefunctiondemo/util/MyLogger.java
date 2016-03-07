package eeepay.androidmorefunctiondemo.util;

import java.util.Hashtable;

import android.util.Log;

/**
 * Android  The class for print log
 * 
 * @author update by xuqingfeng
 * 通过应用名AppName来过滤log
 * 该log实现的功能是会输入当前log是谁写的，log所在的包名，线程名，类名，哪一行，所在方法名
 * 还可以通过设置true，false来控制发布的时候不显示log
 * 
 */
public class MyLogger {
	private final static boolean logFlag = true;

	public final static String tag = "[AppName]";
	private final static int logLevel = Log.VERBOSE;
	private static Hashtable<String, MyLogger> sLoggerTable = new Hashtable<String, MyLogger>();
	private  String mClassName;

	private static MyLogger jlog;
	private static MyLogger klog;

	private static final String JAMES = "@xuqingfeng88@ ";
	private static final String KESEN = "@xuqingfeng77@ ";
	
	private boolean isOutLog=true;//true表示输出日子，false表示不输入日子

	private MyLogger(String name) {
		mClassName = name;
	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unused")
	private static MyLogger getLogger(String className) {
		MyLogger classLogger = (MyLogger) sLoggerTable.get(className);
		if (classLogger == null) {
			classLogger = new MyLogger(className);
			sLoggerTable.put(className, classLogger);
		}
		return classLogger;
	}

	/**
	 * Purpose:Mark user one
	 * 
	 * @return
	 */
	public static MyLogger aLog() {
		if (klog == null) {
			klog = new MyLogger(KESEN);
		}
		return klog;
	}

	/**
	 * Purpose:Mark user two
	 * 
	 * @return
	 */
	public static MyLogger bLog() {
		if (jlog == null) {
			jlog = new MyLogger(JAMES);
		}
		return jlog;
	}

	/**
	 * Get The Current Function Name
	 * 
	 * @return
	 */
	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return mClassName + "[ " + Thread.currentThread().getName() + ": "
					+ st.getFileName() + ":" + st.getLineNumber() + " "
					+ st.getMethodName() + " ]";
		}
		return null;
	}

	/**
	 * The Log Level:i
	 * 
	 * @param str
	 */
	public void i(Object str) {
		if (logFlag) {
			if (logLevel <= Log.INFO) {
				String name = getFunctionName();
				if (name != null) {
					if(isOutLog)
					Log.i(tag, name + " - " + str);
				} else {
					if(isOutLog)
					Log.i(tag, str.toString());
				}
			}
		}

	}

	/**
	 * The Log Level:d
	 * 
	 * @param str
	 */
	public void d(Object str) {
		if (logFlag) {
			if (logLevel <= Log.DEBUG) {
				String name = getFunctionName();
				if (name != null) {
					Log.d(tag, name + " - " + str);
				} else {
					Log.d(tag, str.toString());
				}
			}
		}
	}

	/**
	 * The Log Level:V
	 * 
	 * @param str
	 */
	public void v(Object str) {
		if (logFlag) {
			if (logLevel <= Log.VERBOSE) {
				String name = getFunctionName();
				if (name != null) {
					Log.v(tag, name + " - " + str);
				} else {
					Log.v(tag, str.toString());
				}
			}
		}
	}

	/**
	 * The Log Level:w
	 * 
	 * @param str
	 */
	public void w(Object str) {
		if (logFlag) {
			if (logLevel <= Log.WARN) {
				String name = getFunctionName();
				if (name != null) {
					Log.w(tag, name + " - " + str);
				} else {
					Log.w(tag, str.toString());
				}
			}
		}
	}

	/**
	 * The Log Level:e
	 * 
	 * @param str
	 */
	public void e(Object str) {
		if (logFlag) {
			if (logLevel <= Log.ERROR) {
				String name = getFunctionName();
				if (name != null) {
					if(isOutLog)
					Log.e(tag, name + " - " + str);
				} else {
					if(isOutLog)
					Log.e(tag, str.toString());
				}
			}
		}
	}
    
	/**
	 * The Log Level:e
	 * 
	 * @param ex
	 */
	public void e(Exception ex) {
		if (logFlag) {
			if (logLevel <= Log.ERROR) {
				if(isOutLog)
				Log.e(tag, "error", ex);
			}
		}
	}

	/**
	 * The Log Level:e
	 * 
	 * @param log
	 * @param tr
	 */
	public void e(String log, Throwable tr) {
		if (logFlag) {
			String line = getFunctionName();
			if(isOutLog)
			Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"
					+ "[" + mClassName + line + ":] " + log + "\n", tr);
		}
	}
}
