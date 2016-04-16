/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

/**
 * 
 * <br>Create on 2016-4-16 上午11:23:25
 *
 * @author fengxuefeng
 */
public class KeyValueDto {
	private String key;
	private String value;
	
	public KeyValueDto(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
