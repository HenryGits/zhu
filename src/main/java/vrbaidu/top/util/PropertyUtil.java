package vrbaidu.top.util;

import java.lang.reflect.Field;

public class PropertyUtil {

	/**
	 * @desc 对象属性拷贝,以目标对象为准
	 * @author Vampire
	 * @param dst 目标对象
	 * @param src 源对象
	 * @return
	 */
	public static boolean copyDst(Object dst, Object src) {
		try {
			Field[] fields = dst.getClass().getDeclaredFields();
			for (Field f : fields) {
				String name = f.getName();
				Field srcField = src.getClass().getDeclaredField(name);
				boolean accessible = f.isAccessible();
				boolean srcAccessible = srcField.isAccessible();
				f.setAccessible(true);
				srcField.setAccessible(true);
				Object value = srcField.get(src);
				if (value == null || "".equals(value)) {
					f.setAccessible(accessible);
					srcField.setAccessible(srcAccessible);
					continue;
				}
				f.set(dst, value);
				f.setAccessible(accessible);
				srcField.setAccessible(srcAccessible);
			}
			return true;
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @desc 对象属性拷贝,以源为基准
	 * @author Vampire
	 * @param dst 目标对象
	 * @param src 源对象
	 * @return
	 */
	public static boolean copySrc(Object dst, Object src) {
		try {
			Field[] fields = src.getClass().getDeclaredFields();
			for (Field f : fields) {
				String name = f.getName();
				Field dstField = dst.getClass().getDeclaredField(name);
				boolean accessible = f.isAccessible();
				boolean dstAccessible = dstField.isAccessible();
				f.setAccessible(true);
				dstField.setAccessible(true);
				Object value = f.get(src);
				if (value == null || "".equals(value)) {
					f.setAccessible(accessible);
					dstField.setAccessible(dstAccessible);
					continue;
				}
				dstField.set(dst, value);
				f.setAccessible(accessible);
				dstField.setAccessible(dstAccessible);
			}
			return true;
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @desc 对象属性拷贝
	 * @author Vampire
	 * @param dst 目标对象
	 * @param src 源对象
	 * @return
	 */
	public static boolean copy(Object dst, Object src) {
		Field[] fields = src.getClass().getDeclaredFields();
		for (Field srcField : fields) {
			String name = srcField.getName();
			Field dstField = null;
			try {
				dstField = dst.getClass().getDeclaredField(name);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			if (dstField == null) {
				continue;
			} else {
				boolean srcAccessible = srcField.isAccessible();
				boolean dstAccessible = dstField.isAccessible();
				srcField.setAccessible(true);
				dstField.setAccessible(true);
				Object value = null;
				try {
					value = srcField.get(src);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (value == null || "".equals(value)) {
					srcField.setAccessible(srcAccessible);
					dstField.setAccessible(dstAccessible);
					continue;
				}
				try {
					dstField.set(dst, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
				srcField.setAccessible(srcAccessible);
				dstField.setAccessible(dstAccessible);
			}
		}
		return true;
	}
	
	
	/*public static boolean copyIgnoreCase(Object dst, Object src) {
		Field[] srcFields = src.getClass().getDeclaredFields();
		Field[] dstFields = dst.getClass().getDeclaredFields();
		for (Field srcField : srcFields) {
			String name = srcField.getName();
			Field dstField = null;
			try {
				dstField = dst.getClass().getDeclaredField(name);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			if (dstField == null) {
				continue;
			} else {
				boolean srcAccessible = srcField.isAccessible();
				boolean dstAccessible = dstField.isAccessible();
				srcField.setAccessible(true);
				dstField.setAccessible(true);
				Object value = null;
				try {
					value = srcField.get(src);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (value == null || "".equals(value)) {
					srcField.setAccessible(srcAccessible);
					dstField.setAccessible(dstAccessible);
					continue;
				}
				try {
					dstField.set(dst, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
				srcField.setAccessible(srcAccessible);
				dstField.setAccessible(dstAccessible);
			}
		}
		return true;
	}*/
}
