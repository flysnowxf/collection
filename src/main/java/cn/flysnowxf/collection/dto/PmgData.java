/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

import java.util.List;

/**
 * 
 * <br>Create on 2016-4-10 下午11:25:42
 *
 * @author fengxuefeng
 */
public class PmgData {
	private String catalog;
	private Integer total;
	private List<GradeCount> gradeCountList;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<GradeCount> getGradeCountList() {
		return gradeCountList;
	}
	public void setGradeCountList(List<GradeCount> gradeCountList) {
		this.gradeCountList = gradeCountList;
	}
	
}
