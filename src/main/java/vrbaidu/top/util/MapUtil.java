package vrbaidu.top.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc Map转换工具类
 * @author Someone
 *
 * @date 2016-08-19 10:02:07
 *
 */
public class MapUtil {

	/**
	 * @desc 普通对象转换为Map
	 * @author Someone
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i< fields.length; ++i) {
			Field f = fields[i];
			map.put(f.getName(), getFieldValue(obj, f));
		}
		return map;
	}
	
	/**
	 * @desc 获取对象属性值
	 * @author Someone
	 * @param obj
	 * @param f
	 * @return
	 */
	private static Object getFieldValue(Object obj, Field f) {
		if (null == obj || null == f) {
			return null;
		}
		Object value = null;
		boolean b = f.isAccessible();
		f.setAccessible(true);
		try {
			value = f.get(obj);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			f.setAccessible(b);
		}
		return value;
	}
}
