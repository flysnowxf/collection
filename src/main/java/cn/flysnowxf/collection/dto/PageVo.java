package cn.flysnowxf.collection.dto;

import java.util.List;

public class PageVo<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageSize;// 每页条数

	private int pageNo;// 当前页

	private int recordSize;// 查询的总条数
	
	private Boolean hasNextPage;

	private List<T> data;

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public boolean isHasNext() {
		return pageNo < getPageTotal();
	}

	public boolean isHasPrev() {
		return pageNo > 1;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public List<T> getData() {
		return data;
	}

	public int getPageTotal() {
		return (recordSize - 1) / pageSize + 1;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
}