/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.entity;

import java.util.Date;

/**
 * 
 * <br>Create on 2016-4-23 下午6:16:12
 *
 * @author fengxuefeng
 */
public class Block {
	private Integer id;
	private Integer noteId;
	private String name;
	private String value;
	private Integer isGroup;
	private String summary;
	private String remarkNum;
	private Integer pmgId;
	private Date createDate;
	private Date modifyDate;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRemarkNum() {
		return remarkNum;
	}
	public void setRemarkNum(String remarkNum) {
		this.remarkNum = remarkNum;
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
	public Integer getPmgId() {
		return pmgId;
	}
	public void setPmgId(Integer pmgId) {
		this.pmgId = pmgId;
	}
	
}
