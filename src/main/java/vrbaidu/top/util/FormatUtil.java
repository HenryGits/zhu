package vrbaidu.top.util;

/**
 * @desc 格式化工具类
 * @author Someone
 *
 * @date 2016-08-19 17:53:38
 *
 */
public class FormatUtil {

	/**
	 * @desc addLeftZero
	 * @author Someone
	 * @param length 格式化后长度
	 * @param val 值
	 * @return
	 */
	public static String addLeftZero(int length, Object val) {
		if (((String) val).length() >= length) {
			return (String) val;
		}
		if (val instanceof String) {
			int len = length - ((String) val).length();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < len; i++) {
				sb.append("0");
			}
			return sb.append(val).toString();
		}
		return String.format("%0" + length + "d", val);
	}
	
	/**
	 * @desc 右侧补足空格
	 * @author Someone
	 * @param length 格式化后长度
	 * @param val 值
	 * @return
	 */
	public static String addRightSpace(int length, Object val) {
		if (val == null || val == "") {
			return String.format("%-" + length + "s", " ");
		}
		if (((String) val).length() >= length) {
			return (String) val;
		}
		return String.format("%-" + length + "s", val);
	}
	
	/**
	 * @desc 生成指定数量的空格
	 * @author Someone
	 * @param length
	 * @return
	 */
	public static String spacePadding(int length) {
		return String.format("%-" + length + "s", " ");
	}
	
	/**
	 * @desc paddingLong
	 * @author Someone
	 * @param length
	 * @param value
	 * @return
	 */
	public static String paddingLong(int length, Long value) {
		long divNum = Math.round(Math.pow(10,length));
		value = value % divNum;	
		return String.format("%0" + length + "d", value);
	}
}
