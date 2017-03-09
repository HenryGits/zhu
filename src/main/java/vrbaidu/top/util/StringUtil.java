package vrbaidu.top.util;

import java.util.List;

/**
 * @desc 字符串操作
 * @author Someone
 *
 * @date 2016-08-15 15:13:30
 *
 */
public class StringUtil {

	public static String listToString(List<?> list, String split) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<list.size(); ++i) {
			if (i > 0) {
				buffer.append(split);
			}
			buffer.append(list.get(i));
		}
		return buffer.toString();
	}
}
