/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

/**
 * 
 * <br>Create on 2016-4-10 下午11:26:40
 *
 * @author fengxuefeng
 */
public class GradeCount {
	private String grade;
	private Integer count;
	
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
	
}
