package cn.flysnowxf.collection.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class QueryUtils {
	private static final String PAGE_SIZE = "pageSize";
	private static final String PAGE_NO = "pageNo";

	public static Map<String, Object> getQueryMap(Object o, boolean isPaging) {
		if (null == o) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> resultMap = null;

		try {
			resultMap = BeanUtils.describe(o);
			Class<?> cls = o.getClass();
			Object queryOrderList = getQueryOrderList(o, cls);
			resultMap.put("queryOrderList", queryOrderList);

			if (isPaging) {
				if (null != resultMap.get(PAGE_SIZE)
						&& null != resultMap.get(PAGE_NO)) {
					Integer pageSize = Integer.parseInt(resultMap
							.get(PAGE_SIZE).toString());
					Integer pageNo = Integer.parseInt(resultMap.get(PAGE_NO)
							.toString());
					Integer start = (pageNo - 1) * pageSize;
					resultMap.put("start", start);
					resultMap.put(PAGE_SIZE, pageSize);
					resultMap.put(PAGE_NO, pageNo);
				}
			} else {
				if (resultMap.containsKey(PAGE_SIZE))
					resultMap.remove(PAGE_SIZE);
				if (resultMap.containsKey(PAGE_NO))
					resultMap.remove(PAGE_NO);
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}

	/**
	 * 默认分页
	 * 
	 * @param o
	 * @return
	 */
	public static Map<String, Object> getQueryMap(Object o) {
		return getQueryMap(o, true);
	}

	private static Object getQueryOrderList(Object o, Class<?> cls) {
		List<Field> fieldList = ReflectionUtils.getAllDeclaredFields(cls,
				new ArrayList<Field>());
		try {
			for (int i = 0; i < fieldList.size(); i++) {
				String name = fieldList.get(i).getName();
				if ("queryOrderList".equals(name)) {
					boolean isStatic = Modifier.isStatic(fieldList.get(i)
							.getModifiers());
					if (isStatic)
						continue;
					return ReflectionUtils.invokeGetMethod(o, name);
				}

			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
