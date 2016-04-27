/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

import java.io.Serializable;

/**
 * 
 * <br>Create on 2016-4-10 下午11:26:40
 *
 * @author fengxuefeng
 */
public class GradeCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String grade;
	private Integer count;
	private Integer price;
	
	public GradeCount(String grade, Integer count, Integer price) {
		this.grade = grade;
		this.count = count;
		this.price = price;
	}
	
	public GradeCount(String grade, Integer count) {
		this.grade = grade;
		this.count = count;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
