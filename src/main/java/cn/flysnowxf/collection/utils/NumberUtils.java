/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <br>Create on 2016-4-22 下午10:59:30
 *
 * @author fengxuefeng
 */
public class NumberUtils {
	
	private static final Map<String, String> LUOMA_MAP = new HashMap<String, String>();
	
	static {
		LUOMA_MAP.put("I", "1");
		LUOMA_MAP.put("II", "2");
		LUOMA_MAP.put("III", "3");
		LUOMA_MAP.put("IV", "4");
		LUOMA_MAP.put("V", "5");
		LUOMA_MAP.put("VI", "6");
		LUOMA_MAP.put("VII", "7");
		LUOMA_MAP.put("VIII", "8");
		LUOMA_MAP.put("IX", "9");
		LUOMA_MAP.put("X", "0");
	}
	
	/**
	 * 多个数字的排列组合
	 * @param numberList
	 * @return
	 */
	public static String getGroup(List<String> numberList) {
		List<String> result = new ArrayList<String>();
		
		if (CollectionUtils.isNotEmpty(numberList)) {
			for (String number : numberList) {
				result.add(getGroup(number));
			}
		}
		
		return StringUtils.join(result, ",");
	}
	
	/**
	 * 数字的排列组合，比如129，可返回129,192,219,291,912,921
	 * @param number
	 * @return
	 */
	public static String getGroup(String number) {
		List<String> numberList = new ArrayList<String>();
		
		char[] nums = number.toCharArray();
		for (char a : nums) {
			for (char b : nums) {
				for (char c : nums) {
					if (a != b && a != c && b != c) {
						numberList.add(String.valueOf(a) + String.valueOf(b) + String.valueOf(c));
					}
				}
			}
		}
		
		return StringUtils.join(numberList, ",");
	}
	
	public static String getAlaboNumber(String a, String b, String c) {
		if (StringUtils.isNotBlank(a)) {
			if (LUOMA_MAP.containsKey(a)) {
				a = LUOMA_MAP.get(a.toUpperCase());
			}
		}
		if (StringUtils.isNotBlank(b)) {
			if (LUOMA_MAP.containsKey(b)) {
				b = LUOMA_MAP.get(b.toUpperCase());
			}
		}
		if (StringUtils.isNotBlank(c)) {
			if (LUOMA_MAP.containsKey(c)) {
				c = LUOMA_MAP.get(c.toUpperCase());
			}
		}
		
		return notNull(a) + notNull(b) + notNull(c);
	}
	
	private static String notNull(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		
		return text;
	}
}
