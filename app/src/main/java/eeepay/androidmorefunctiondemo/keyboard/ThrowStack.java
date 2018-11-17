package eeepay.androidmorefunctiondemo.keyboard;

/**
 * 分类: 异常信息处理类
 * 
 * @功能: 处理异常堆栈信息
 * @说明:
 * 
 * @author: TangHua, SinoTrend Co.,Ltd
 * @date: 2009-6-17
 * @Version: 1.0
 */

public class ThrowStack {

	String tag;
	String pack;

	public ThrowStack(int number) {
		getStackInfo(number);
	}

	public String getTag() {
		return tag;
	}

	public String getPack() {
		return pack;
	}

	/**
	 * 通过异常获取当前运行信息： 行号、类名、文件名等等
	 * 打印实例：[getOut(com.sinotrend.core.app.Switcher:281)]你好，这是打印输出调试！
	 * 
	 * 输入参数 int number: 输出的行号
	 * 
	 * 返回 String: 行号信息
	 */
	public void getStackInfo(int number) {

		// 取得StackTraceElement
		StackTraceElement e = getThrowableStack(new Throwable(), number);

		// 取相关信息
		pack = "";
		String cn = e.getClassName();
		int pos = cn.lastIndexOf(".");
		if (pos >= 0) {
			pack = cn.substring(0, pos); // 取包名
			cn = cn.substring(pos + 1); // 只需要最后一级域名
		}
		tag = String.format("<%d>%s(%s:%d)", Thread.currentThread().getId(), e
				.getMethodName(), cn, e.getLineNumber());
	}

	/**
	 * 通过异常获取当前运行信息： 行号、类名、文件名等等 stack.getLineNumber(); stack.getMethodName();
	 * stack.getClassName(); stack.getFileName();
	 * 
	 * 输入参数 Throwable throwable: 要打印信息的异常类 int number: 输出的行号
	 * 
	 * 返回 String: 行号信息
	 * 
	 * 实现方法1：new Throwable().getStackTrace();//for jdk1.4 or later
	 * 实现方法2：Thread.currentThread().getStackTrace();//for jdk1.5 or later
	 * 结果：经测试对比，使用Throwable()速度快一个一个数量级
	 * 
	 */
	static private StackTraceElement getThrowableStack(Throwable throwable,
			int number) {

		// 通过异常或其他方式取得StackTrace
		StackTraceElement[] stacks;
		stacks = throwable.getStackTrace();// for jdk1.4 or later
		// stacks = Thread.currentThread().getStackTrace();//for jdk1.5 or later

		return stacks[number];
	}

}
