/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

/**
 * 
 * <br>Create on 2016-4-12 下午9:50:56
 *
 * @author fengxuefeng
 */
public class PmgLogRequest extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pmgId;
	private String date;
	private String month;
	
	public Integer getPmgId() {
		return pmgId;
	}
	public void setPmgId(Integer pmgId) {
		this.pmgId = pmgId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
