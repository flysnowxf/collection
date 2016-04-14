/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

/**
 * 
 * <br>Create on 2016-4-15 上午1:03:26
 *
 * @author fengxuefeng
 */
public class NoteRequest extends Query {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
