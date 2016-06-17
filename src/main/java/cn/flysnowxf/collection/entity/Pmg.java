/**
 * Copyright (c) 2014-2016, Beijing Yunyouhulian Technology Co., Ltd. All rights reserved.
 */
package cn.flysnowxf.collection.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.flysnowxf.collection.dto.BlockDto;
import cn.flysnowxf.collection.dto.DealPrice;
import cn.flysnowxf.collection.dto.GradeCount;
import cn.flysnowxf.collection.dto.KeyValueDto;

/**
 * 
 * <br>Create on 2016-4-12 下午9:54:18
 *
 * @author fengxuefeng
 */
public class Pmg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer noteId;
	private String name;
	private String year;
	private String circulation;
	private String catalog;
	private Integer total;
	private Integer blockCount;
	private String frontImage;
	private String backImage;
	private Date createDate;
	private Date modifyDate;
	private String dealPrice;
	
	private List<GradeCount> gradeCountList;
	private List<KeyValueDto> keyValueList;
	private Integer highScoreRatio;
	private List<BlockDto> blockList;
	private String value;
	private Integer lastMonthAdd;
	private Integer thisMonthAdd;
	private List<DealPrice> dealPriceList;
	
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
	public Integer getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(Integer blockCount) {
		this.blockCount = blockCount;
	}
	public List<BlockDto> getBlockList() {
		return blockList;
	}
	public void setBlockList(List<BlockDto> blockList) {
		this.blockList = blockList;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getLastMonthAdd() {
		return lastMonthAdd;
	}
	public void setLastMonthAdd(Integer lastMonthAdd) {
		this.lastMonthAdd = lastMonthAdd;
	}
	public Integer getThisMonthAdd() {
		return thisMonthAdd;
	}
	public void setThisMonthAdd(Integer thisMonthAdd) {
		this.thisMonthAdd = thisMonthAdd;
	}
	public String getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}
	public List<DealPrice> getDealPriceList() {
		return dealPriceList;
	}
	public void setDealPriceList(List<DealPrice> dealPriceList) {
		this.dealPriceList = dealPriceList;
	}
}
