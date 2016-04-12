package cn.flysnowxf.collection.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

/**
 * 反射工具类
 */
public class ReflectionUtils {
	
	public static List<Field> getAllDeclaredFields(Class<?> clazz,
			List<Field> fields) {
		while (clazz != Object.class) {
			getAllDeclaredFields(clazz.getSuperclass(), fields);
			break;
		}
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return fields;
	}

	public static Object invokeGetMethod(Object o, String name)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method method = o.getClass().getMethod("get" + upperCaseField(name));
		if (null == method)
			return null;
		return method.invoke(o);
	}

	public static Object invokeSetMethod(Object o, String name, Object value)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(
					upperCaseField(name), o.getClass());
			Method writeMethod = pd.getWriteMethod();
			if (null == writeMethod)
				return null;
			Class clazz[] = writeMethod.getParameterTypes();
			for (Class _clazz : clazz) {
				if (_clazz == String.class) {
					writeMethod.invoke(o, (String) value);
				} else if (_clazz == int.class) {
					writeMethod.invoke(o, (Integer) value);
				} else if (_clazz == float.class) {
					writeMethod.invoke(o, (Float) value);
				} else if (_clazz == long.class) {
					writeMethod.invoke(o, (Long) value);
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 转化首字母大写
	public static String upperCaseField(String fieldName) {
		return fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
				.substring(0, 1).toUpperCase());
	}

	public static Object getFieldValue(final Object o, final String fieldName) {
		Field field = getDeclaredField(o, fieldName);
		if (null == field)
			throw new IllegalArgumentException("Can not find field ["
					+ fieldName + " ] on target class [" + o + "]");
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(o);
		} catch (IllegalAccessException e) {
			// 不可能抛出的异常
		}
		return result;
	}

	public static Field getDeclaredField(final Object o, final String fieldName) {
		Assert.assertNotNull("object不能为空", o);
		return getDeclaredField(o.getClass(), fieldName);
	}

	public static Field getDeclaredField(final Class<?> clazz,
			final String fieldName) {
		Assert.assertNotNull("clazz不能为空", clazz);
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// field不在当前类 继续向上
			}
		}
		return null;
	}

	/**
	 * 强制转换fileld可访问
	 * 
	 * @param field
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

}
