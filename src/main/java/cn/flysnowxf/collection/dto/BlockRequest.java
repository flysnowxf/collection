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
public class BlockRequest extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer noteId;
	private Integer pmgId;

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public Integer getPmgId() {
		return pmgId;
	}

	public void setPmgId(Integer pmgId) {
		this.pmgId = pmgId;
	}
	
}
