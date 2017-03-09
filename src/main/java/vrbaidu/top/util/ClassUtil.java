/*
 * 描       述:  JAVA反射工具
 */
package vrbaidu.top.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * 反射工具类
 * 
 * @since
 */
@SuppressWarnings("unchecked")
public abstract class ClassUtil {
	
	/** Suffix for array class names: "[]" */
	public static final String ARRAY_SUFFIX = "[]";

	/** The package separator character '.' */
	private static final char PACKAGE_SEPARATOR = '.';

	/** The inner class separator character '$' */
	private static final char INNER_CLASS_SEPARATOR = '$';

	/** The CGLIB class separator character "$$" */
	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	/** The ".class" file suffix */
	public static final String CLASS_FILE_SUFFIX = ".class";
	
	/**
	 * Map with primitive type as key and corresponding wrapper
	 * type as value, for example: int.class -> Integer.class.
	 */
	private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);
	

	private static final Log logger = LogFactory.getLog(ClassUtil.class);
	

	/**
	 * 获取类指定注解的所有属性。
	 * 
	 * @param clazz Class
	 * @param annotationClass 注解的Class
	 * @return List<Field>
	 */
	public static List<Field> getFieldsByAnnotation(Class<?> clazz ,Class<? extends Annotation> annotationClass){
		List<Field> listField = new ArrayList<Field>();
		lookupFieldsByAnnotation(listField, clazz, annotationClass);
		return listField;
	}

	/**
	 * 获取类指定注解的所有属性。
	 * 
	 * @param listField 属性集合
	 * @param clazz Class
	 * @param annotationClass 注解的Class
	 */
	private static void lookupFieldsByAnnotation(List<Field> listField ,Class<?> clazz ,Class<? extends Annotation> annotationClass){
		if(null == listField || null == clazz || null == annotationClass) return;
		Field[] fields = clazz.getDeclaredFields();
		if(ArrayUtils.isNotEmpty(fields)){
			for(Field f : fields){
				if(null != f.getAnnotation(annotationClass)){
					listField.add(f);
				}
			}
		}
		lookupFieldsByAnnotation(listField, clazz.getSuperclass() ,annotationClass);
	}
	
	/**
	 * 获取类指定注解的所有方法。
	 * 
	 * @param clazz Class
	 * @param annotationClass 注解的Class
	 * @return List<Method>
	 */
	public static List<Method> getMethodsByAnnotation(Class<?> clazz ,Class<? extends Annotation> annotationClass){
		List<Method> listMethod = new ArrayList<Method>();
		lookupMethodsByAnnotation(listMethod, clazz, annotationClass);
		return listMethod;
	}
	
	
	/**
	 * 获取类指定注解的所有方法。
	 * 
	 * @param listField 属性集合
	 * @param clazz Class
	 * @param annotationClass 注解的Class
	 */
	private static void lookupMethodsByAnnotation(List<Method> listMethod ,Class<?> clazz ,Class<? extends Annotation> annotationClass){
		if(null == listMethod || null == clazz || null == annotationClass) return;
		Method[] methods = clazz.getDeclaredMethods();
		if(ArrayUtils.isNotEmpty(methods)){
			for(Method m : methods){
				if(null != m.getAnnotation(annotationClass)){
					listMethod.add(m);
				}
			}
		}
		lookupMethodsByAnnotation(listMethod, clazz.getSuperclass() ,annotationClass);
	}
	
	/**
	 * 获取类所有属性。
	 * 
	 * @param clazz Class
	 * @return List<Field>
	 */
	public static List<Field> getFields(Class<?> clazz){
		return getFields(clazz , true);
	}
	
	/**
	 * 获取类所有属性。
	 * 
	 * @param clazz Class
	 * @param isSuperclass boolean (isSuperclass:true 表示包括父类属性查找；isSuperclass:false 表示只查找本类。)
	 * @return List<Field>
	 */
	public static List<Field> getFields(Class<?> clazz ,boolean isSuperclass){
		List<Field> listField = new ArrayList<Field>();
		lookupFields(listField, clazz, isSuperclass);
		return listField;
	}

	/**
	 * 获取类所有属性。
	 * 
	 * @param listField 属性集合
	 * @param clazz Class
	 * @param isSuperclass boolean (isSuperclass:true 表示包括父类属性查找；isSuperclass:false 表示只查找本类。)
	 */
	private static void lookupFields(List<Field> listField ,Class<?> clazz ,boolean isSuperclass){
		if(null == listField || null == clazz) return;
		Field[] fields = clazz.getDeclaredFields();
		if(ArrayUtils.isNotEmpty(fields)){
			for(Field f : fields){
				listField.add(f);
			}
		}
		if(isSuperclass)
			lookupFields(listField, clazz.getSuperclass() ,isSuperclass);
	}
	
	/**
	 * 获取类所有属性的键值映射[Map<String ,Field>:key是字段名称，value是字段Field]。
	 * 
	 * @param clazz Class
	 * @return Map<String ,Field>，key是字段名称，value是字段Field。
	 */
	public static Map<String ,Field> getFieldMap(Class<?> clazz){
		return getFieldMap(clazz , true);
	}
	
	/**
	 * 获取类所有属性的键值映射[Map<String ,Field>:key是字段名称，value是字段Field]。
	 * 
	 * @param clazz Class
	 * @param isSuperclass boolean (isSuperclass:true 表示包括父类属性查找；isSuperclass:false 表示只查找本类。)
	 * @return Map<String ,Field>，key是字段名称，value是字段Field。
	 */
	public static Map<String ,Field> getFieldMap(Class<?> clazz ,boolean isSuperclass){
		Map<String ,Field> fieldMap = new HashMap<String, Field>();
		lookupFields(fieldMap, clazz, isSuperclass);
		return fieldMap;
	}
	
	/**
	 * 获取类所有属性的键值映射[Map<String ,Field>:key是字段名称，value是字段Field]。
	 * 
	 * @param fieldMap Map<String ,Field>,key是字段名称，value是字段Field
	 * @param clazz Class
	 * @param isSuperclass boolean (isSuperclass:true 表示包括父类属性查找；isSuperclass:false 表示只查找本类。)
	 */
	private static void lookupFields(Map<String ,Field> fieldMap ,Class<?> clazz ,boolean isSuperclass){
		if(null == fieldMap || null == clazz) return;
		Field[] fields = clazz.getDeclaredFields();
		if(ArrayUtils.isNotEmpty(fields)){
			for(Field f : fields){
				fieldMap.put(f.getName() ,f);
			}
		}
		if(isSuperclass)
			lookupFields(fieldMap, clazz.getSuperclass() ,isSuperclass);
	}
	
	
	/**
	 * 获取类指定名称的属性。
	 * 
	 * @param clazz Class
	 * @param fieldName 字段名称
	 * @return List<Field>
	 */
	public static Field getField(Class<?> clazz ,String fieldName){
		List<Field> fields = getFields(clazz);
		return getField(fields, fieldName, false);
	}
	
	/**
	 * 获取类指定名称的属性。
	 * 
	 * @param fields List<Field>
	 * @param fieldName 字段名称
	 * @return List<Field>
	 */
	public static Field getField(List<Field> fields ,String fieldName){
		return getField(fields, fieldName, false);
	}
	
	/**
	 * 获取类指定名称的属性。
	 * 
	 * @param clazz Class
	 * @param fieldName 字段名称
	 * @param isIgnoreCase 是否忽略大小写
	 * @return List<Field>
	 */
	public static Field getField(Class<?> clazz ,String fieldName ,boolean isIgnoreCase){
		List<Field> fields = getFields(clazz);
		return getField(fields, fieldName, isIgnoreCase);
	}
	
	/**
	 * 获取类指定名称的属性。
	 * 
	 * @param fields List<Field>
	 * @param fieldName 字段名称
	 * @param isIgnoreCase 是否忽略大小写
	 * @return List<Field>
	 */
	public static Field getField(List<Field> fields ,String fieldName ,boolean isIgnoreCase){
		if(fields == null || fields.isEmpty()) return null;
		//转换大写
		String fName = fieldName;
		if(isIgnoreCase){
			fName = fName.toUpperCase();
		}
		for(Field f : fields){
			//转换大写
			String name = f.getName();
			if(isIgnoreCase){
				name = name.toUpperCase();
			}
			//比较
			if(name.equals(fName)){
				return f;
			}
		}
		return null;
	}
	
	
	/**
	 * 获取对象属性值，默认区分大小写。
	 * 
	 * @param obj
	 * @param fieldName
	 * @return Object
	 */
	public static Object getFieldValue(Object obj ,String fieldName){
		if(null == obj) return null;
		Field f = getField(obj.getClass(), fieldName);
		return getFieldValue(obj, f);
	}
	
	
	/**
	 * 获取对象属性值，默认区分大小写。
	 * 
	 * @param <T>
	 * @param obj 对象
	 * @param fieldName 字段名称
	 * @param clazz 结果类型
	 * @return T
	 */
	public static <T> T getFieldValue(Object obj ,String fieldName ,Class<T> clazz){
		return getFieldValue(obj, fieldName, false, clazz);
	}
	
	/**
	 * 获取对象属性值。
	 * 
	 * @param <T>
	 * @param obj 对象
	 * @param fieldName 字段名称
	 * @param isIgnoreCase 是否忽略大小写
	 * @param clazz 结果类型
	 * @return T
	 */
	public static <T> T getFieldValue(Object obj ,String fieldName ,boolean isIgnoreCase ,Class<T> clazz){
		if(null == obj) return null;
		Field f = getField(obj.getClass(), fieldName, isIgnoreCase);
		return getFieldValue(obj, f, clazz);
	}
	
	/**
	 * 获取对象属性值。
	 * 
	 * @param <T>
	 * @param obj 对象
	 * @param f 字段
	 * @param clazz 结果类型
	 * @return T
	 */
	public static <T> T getFieldValue(Object obj ,Field f ,Class<T> clazz){
		return (T) getFieldValue(obj, f);
	}
	
	/**
	 * 获取对象属性值。
	 * 
	 * @param obj 对象
	 * @param f 字段
	 * @return Object
	 */
	public static Object getFieldValue(Object obj ,Field f){
		if(null == obj || null == f) return null;
		Object value = null;
		boolean b = f.isAccessible();
		f.setAccessible(true);
		try{
			value = f.get(obj);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}finally{
			f.setAccessible(b);
		}
		return value;
	}
	
	/**
	 * 设置对象属性值。
	 * 
	 * @param obj 对象
	 * @param f 属性
	 * @param value 属性值
	 */
	public static void setFieldValue(Object obj ,Field f ,Object value){
		if(null == obj || null == f) return ;
		boolean b = f.isAccessible();
		f.setAccessible(true);
		try{
			if(null == value) return ;
			f.set(obj , value);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}finally{
			f.setAccessible(b);
		}
	}
	
	/**
	 * 设置对象属性值。
	 * 
	 * @param obj 对象
	 * @param fieldName 属性名称
	 * @param value 属性值
	 */
	public static void setFieldValue(Object obj ,String fieldName ,Object value){
		Field f = getField(obj.getClass() ,fieldName);
		if(null == f) return ;
		setFieldValue(obj, f, value);
	}
	
	
	/**
	 * 获取类构造器。
	 * 
	 * @param <T>
	 * @param clazz
	 * @param parameterTypes
	 * @return
	 */
	public static <T> Constructor<T> getConstructor(Class<T> clazz ,Class<?>... parameterTypes){
		if(null == clazz) return null;
		Constructor<T> constructor = null;
		try {
			constructor = clazz.getConstructor(parameterTypes);
		} catch (SecurityException e) {
			constructor = null;
		} catch (NoSuchMethodException e) {
			constructor = null;
		}
		return constructor;
	}

	/**
	 * 创建对象。
	 * 
	 * @param <T>
	 * @param clazz
	 * @return T
	 */
	public static <T> T newInstance(Class<T> clazz){
		if(null == clazz) return null;
		try{
			return clazz.newInstance();
		}catch ( InstantiationException e ) {
			throw new RuntimeException(clazz.getName() + "类创建对象失败！注意该类必须有一个公共的无参的构造函数。");
		}catch ( IllegalAccessException e ) {
			throw new RuntimeException(clazz.getName() + "类创建对象失败！注意该类必须有一个公共的无参的构造函数。");
		}
	}

	/**
	 * 通过反射机制克隆一个对象。
	 * 
	 * @param obj 对象
	 * @return 返回克隆对象.
	 * @throws Exception
	 */
	public static Object copyObject(Object obj) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		Object newObj = obj.getClass().newInstance();
		for (int i = 0, j = fields.length; i < j; i++) {
			String propertyName = fields[i].getName();
			Object propertyValue = getProperty(obj, propertyName);
			setProperty(newObj, propertyName, propertyValue);
		}
		return newObj;

	}

	/**
	 * 反射调用setter方法，进行赋值。
	 * @param bean 对象
	 * @param propertyName 属性名称
	 * @param value 属性值
	 */
	private static Object setProperty(Object bean, String propertyName,
			Object value) throws Exception {
		Class<?> clazz = bean.getClass();
		try {
			Field field = clazz.getDeclaredField(propertyName);
			Method method = clazz.getDeclaredMethod(
					getSetterName(field.getName()),
					new Class[] { field.getType() });
			return method.invoke(bean, new Object[] { value });
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 反射调用getter方法，得到field的值。
	 * @param bean 对象
	 * @param propertyName 属性名称
	 * @return 返回属性值
	 * @throws Exception
	 */
	public static Object getProperty(Object bean, String propertyName)
			throws Exception {
		Class<?> clazz = bean.getClass();
		try {
			Field field = clazz.getDeclaredField(propertyName);
			Method method = clazz.getDeclaredMethod(
					getGetterName(field.getName()), new Class[] {});
			return method.invoke(bean, new Object[] {});
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据field名，得到getter方法名。
	 * 
	 * @param propertyName 属性名称
	 * @return 返回属性get方法.
	 */
	private static String getGetterName(String propertyName) {
		String method = "get" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
		return method;
	}

	/**
	 * 根据field名，得到setter方法名。
	 * 
	 * @param propertyName 属性名称
	 * @return 返回属性set方法
	 */
	private static String getSetterName(String propertyName) {
		String method = "set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
		return method;
	}

	/**
	 * 获取对象的指定注解。
	 * 
	 * @param obj 对象
	 * @param clazz 指定注解Class
	 * @return  返回对象的指定注解
	 */
	public static <T extends Annotation> T getAnnotation(Object obj,
			Class<T> clazz) {
		Class<?> objClazz = obj.getClass();
		return objClazz.getAnnotation(clazz);
	}

	/**
	 * 普通的JavaBean对象转换成Map数据类型。
	 * 将普通的JavaBean对象转换成Map数据类型，其中JavaBean声明的变量名作为Map类型的key，
	 * 该变量的值，作为其Map数据类型相应key的值。
	 * 
	 * @param obj
	 *            - 普通JavaBean对象
	 * @return 返回Map数据类类型
	 * 
	 * @return Map<String,Object> 返回Map数据类型
	 */
	public static Map<String, Object> getObjectAsMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}

		Class<?> clazz = obj.getClass();
		Method[] methods = clazz.getMethods();
		
		String methodname = "";
		for (int i = 0; i < methods.length; i++) {
			methodname = methods[i].getName();
			if (methodname.startsWith("get")) {
				try {
					Object value = methods[i].invoke(obj);
					if (value != null && (value instanceof String)) {
						String str = (String) value;
						value = str.trim();
					}
					map.put(getFieldName(methodname), value);
				} catch (IllegalArgumentException e) {
					logger.debug("Convert JavaBean to Map Error!", e);
				} catch (IllegalAccessException e) {
					logger.debug("Convert JavaBean to Map Error!", e);
				} catch (InvocationTargetException e) {
					logger.debug("Convert JavaBean to Map Error!", e);
				}
			}
		}
		return map;
	}

	/**
	 * 获取属性名称。
	 * 
	 * @param str get或set方法
	 * @return String
	 */
	private static String getFieldName(String str) {
		String firstChar = str.substring(3, 4);
		String out = firstChar.toLowerCase() + str.substring(4);
		return out;
	}

	/**
	 * 获取obj对象fieldName的Field。
	 * 
	 * @param obj 对象
	 * @param fieldName 属性名称
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值。
	 * 
	 * @param obj 对象
	 * @param fieldName 属性名称
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			 {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				try {
					value = field.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				field.setAccessible(true);
				try {
					value = field.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 获取某个类的泛型定义的Class。
	 * 
	 * @param o 对象
	 * @return
	 */
	public static Class<?> getGenericToClass(Object o) {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) o.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}


	
	//==================================================
	
	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and no parameters. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and parameter types. Searches all superclasses up to <code>Object</code>.
	 * <p>Returns <code>null</code> if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @param paramTypes the parameter types of the method
	 * (may be <code>null</code> to indicate any signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())
						&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the
	 * supplied <code>name</code>. Searches all superclasses up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the
	 * supplied <code>name</code> and/or {@link Class type}. Searches all superclasses
	 * up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field (may be <code>null</code> if type is specified)
	 * @param type the type of the field (may be <code>null</code> if name is specified)
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	
	/**
	 * Get the field represented by the supplied {@link Field field object} on the
	 * specified {@link Object target object}. In accordance with {@link Field#get(Object)}
	 * semantics, the returned value is automatically wrapped if the underlying field
	 * has a primitive type.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * @param field the field to get
	 * @param target the target object from which to get the field
	 * @return the field's current value
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}
	
	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}
	
	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an IllegalStateException else.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>Rethrows the underlying exception cast to an {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}
	
	/**
	 * Invoke the specified {@link Method} against the supplied target object with no arguments.
	 * The target object can be <code>null</code> when invoking a static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(Method, Object, Object[])
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with the
	 * supplied arguments. The target object can be <code>null</code> when invoking a
	 * static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		}
		catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}
	
	/**
	 * Given an input class object, return a string which consists of the
	 * class's package name as a pathname, i.e., all dots ('.') are replaced by
	 * slashes ('/'). Neither a leading nor trailing slash is added. The result
	 * could be concatenated with a slash and the name of a resource and fed
	 * directly to <code>ClassLoader.getResource()</code>. For it to be fed to
	 * <code>Class.getResource</code> instead, a leading slash would also have
	 * to be prepended to the returned value.
	 * @param clazz the input class. A <code>null</code> value or the default
	 * (empty) package will result in an empty string ("") being returned.
	 * @return a path which represents the package name
	 * @see ClassLoader#getResource
	 * @see Class#getResource
	 */
	public static String classPackageAsResourcePath(Class<?> clazz) {
		if (clazz == null) {
			return "";
		}
		String className = clazz.getName();
		int packageEndIndex = className.lastIndexOf('.');
		if (packageEndIndex == -1) {
			return "";
		}
		String packageName = className.substring(0, packageEndIndex);
		return packageName.replace('.', '/');
	}
	
	/**
	 * Return the qualified name of the given class: usually simply
	 * the class name, but component type class name + "[]" for arrays.
	 * @param clazz the class
	 * @return the qualified name of the class
	 */
	public static String getQualifiedName(Class<?> clazz) {
		if (clazz.isArray()) {
			return getQualifiedNameForArray(clazz);
		}
		else {
			return clazz.getName();
		}
	}

	/**
	 * Build a nice qualified name for an array:
	 * component type class name + "[]".
	 * @param clazz the array class
	 * @return a qualified name for the array class
	 */
	private static String getQualifiedNameForArray(Class<?> clazz) {
		StringBuilder result = new StringBuilder();
		while (clazz.isArray()) {
			clazz = clazz.getComponentType();
			result.append(ARRAY_SUFFIX);
		}
		result.insert(0, clazz.getName());
		return result.toString();
	}
	
	/**
	 * Resolve the given class if it is a primitive class,
	 * returning the corresponding primitive wrapper type instead.
	 * @param clazz the class to check
	 * @return the original class, or a primitive wrapper for the original primitive type
	 */
	public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
		return (clazz.isPrimitive() && clazz != void.class? primitiveTypeToWrapperMap.get(clazz) : clazz);
	}
	
	/**
	 * Make the given field accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param field the field to make accessible
	 * @see Field#setAccessible
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible
	 * if necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param ctor the constructor to make accessible
	 * @see Constructor#setAccessible
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
				&& !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}
	
	/**
	 * Return a public static method of a class.
	 * @param methodName the static method name
	 * @param clazz	the class which defines the method
	 * @param args the parameter types to the method
	 * @return the static method, or <code>null</code> if no static method was found
	 * @throws IllegalArgumentException if the method name is blank or the clazz is null
	 */
	public static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method = clazz.getMethod(methodName, args);
			return Modifier.isStatic(method.getModifiers()) ? method : null;
		}
		catch (NoSuchMethodException ex) {
			return null;
		}
	}
	
	/**
	 * Determine whether the given class has a public constructor with the given signature,
	 * and return it if available (else return <code>null</code>).
	 * <p>Essentially translates <code>NoSuchMethodException</code> to <code>null</code>.
	 * @param clazz	the clazz to analyze
	 * @param paramTypes the parameter types of the method
	 * @return the constructor, or <code>null</code> if not found
	 * @see Class#getConstructor
	 */
	public static <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
		try {
			return clazz.getConstructor(paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return null;
		}
	}
	
	/**
	 * Get the class name without the qualified package name.
	 * @param className the className to get the short name for
	 * @return the class name of the class without the package name
	 * @throws IllegalArgumentException if the className is empty
	 */
	public static String getShortName(String className) {
		int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
		int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
		if (nameEndIndex == -1) {
			nameEndIndex = className.length();
		}
		String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
		shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
		return shortName;
	}
	
	/**
	 * Get the class name without the qualified package name.
	 * @param clazz the class to get the short name for
	 * @return the class name of the class without the package name
	 */
	public static String getShortName(Class<?> clazz) {
		return getShortName(getQualifiedName(clazz));
	}
	

	
//------------------------------------------------------------	
	
	
	/**
     * 取得某个接口下所有实现这个接口的类
     * */
    public static List<Class<?>> getAllClassByInterface(Class<?> c,String packageName) {
            Set<Class<?>>  returnClassList = null;
            
            if(c.isInterface()) {
                // 获取当前包下以及子包下所以的类
                List<Class<?>> allClass = getClasses(packageName);
                if(allClass != null) {
                    returnClassList = new HashSet<Class<?>>();
                    for(Class<?> classes : allClass) {
                        // 判断是否是同一个接口
                        if(c.isAssignableFrom(classes)) {
                            // 本身不加入进去
                            if(!c.equals(classes)) {
                                returnClassList.add(classes);        
                            }
                        }
                    }
                }
            }
            
            return new ArrayList<Class<?>>(returnClassList);
        }

    
    /*
     * 取得某一类所在包的所有类名 不含迭代
     */
    public static String[] getPackageAllClassName(String classLocation, String packageName){
        //将packageName分解
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for(int i = 0; i< packageLength; i++){
            realClassLocation = realClassLocation + File.separator+packagePathSplit[i];
        }
        File packeageDir = new File(realClassLocation);
        if(packeageDir.isDirectory()){
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }
    
    /**
     * 从包package中获取所有的Class
     * @param pack
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){
        
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //如果是jar包文件 
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        if(className.startsWith("com.ylink.core")){
                                        	System.out.println("className");
                                        }
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                      }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        return classes;
    }
    
    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
              public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
              }
            });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                                      file.getAbsolutePath(),
                                      recursive,
                                      classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
}
