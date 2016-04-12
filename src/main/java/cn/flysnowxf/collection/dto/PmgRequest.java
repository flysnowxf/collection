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
public class PmgRequest extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String catalog;
	private String noteId;
	
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
}
