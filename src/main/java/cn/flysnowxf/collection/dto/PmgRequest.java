/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

import java.util.List;

/**
 * 
 * <br>Create on 2016-4-12 下午9:50:56
 *
 * @author fengxuefeng
 */
public class PmgRequest extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String catalog;
	private List<Integer> noteIds;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public List<Integer> getNoteIds() {
		return noteIds;
	}
	public void setNoteIds(List<Integer> noteIds) {
		this.noteIds = noteIds;
	}
	
}
