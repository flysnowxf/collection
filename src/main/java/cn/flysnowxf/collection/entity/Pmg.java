/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.entity;

import java.util.Date;
import java.util.List;

import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.KeyValueDto;

/**
 * 
 * <br>Create on 2016-4-12 下午9:54:18
 *
 * @author fengxuefeng
 */
public class Pmg {
	private Integer id;
	private Integer noteId;
	private String name;
	private String year;
	private String circulation;
	private String catalog;
	private Integer total;
	private String frontImage;
	private String backImage;
	private Date createDate;
	private Date modifyDate;
	
	private List<GradeCount> gradeCountList;
	private List<KeyValueDto> keyValueList;
	private Integer highScoreRatio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCirculation() {
		return circulation;
	}
	public void setCirculation(String circulation) {
		this.circulation = circulation;
	}
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
	public String getFrontImage() {
		return frontImage;
	}
	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}
	public String getBackImage() {
		return backImage;
	}
	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public List<GradeCount> getGradeCountList() {
		return gradeCountList;
	}
	public void setGradeCountList(List<GradeCount> gradeCountList) {
		this.gradeCountList = gradeCountList;
	}
	public List<KeyValueDto> getKeyValueList() {
		return keyValueList;
	}
	public void setKeyValueList(List<KeyValueDto> keyValueList) {
		this.keyValueList = keyValueList;
	}
	public Integer getHighScoreRatio() {
		return highScoreRatio;
	}
	public void setHighScoreRatio(Integer highScoreRatio) {
		this.highScoreRatio = highScoreRatio;
	}
}
