/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <br>Create on 2016-4-24 下午7:16:46
 *
 * @author fengxuefeng
 */
public class BlockDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<BlockGroupDto> blockGroupList;
	private String summary;
	private String remarkNum;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<BlockGroupDto> getBlockGroupList() {
		return blockGroupList;
	}
	public void setBlockGroupList(List<BlockGroupDto> blockGroupList) {
		this.blockGroupList = blockGroupList;
	}
	
}
