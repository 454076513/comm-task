package self.mf.utils;

/**
 * 
*	调用跟踪工具
* @version V1.0
 */
public class TraceUtils {


    /**
	 * 
	* @Description: 获取当前调用方法完整路径
	* @throws
	 */
	public static String callCurrentMethodPath() {

		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement ste = null;
		if (stack.length > 2) {
			ste = stack[2];
		} else if (stack.length > 0) {
			ste = stack[1];
		} else {
			ste = stack[0];
		}
		return ste.getClassName() + "." + ste.getMethodName();

	}

	
	/**
	 * 
	* @Description: 获取当前调用方法名
	* @throws
	 */
	public static String callCurrentMethodName() {

		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement ste = null;
		if (stack.length > 2) {
			ste = stack[2];
		} else if (stack.length > 0) {
			ste = stack[1];
		} else {
			ste = stack[0];
		}
		return ste.getMethodName();

	}
	
}