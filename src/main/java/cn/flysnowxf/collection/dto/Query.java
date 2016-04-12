package cn.flysnowxf.collection.dto;

import java.io.Serializable;
import java.util.List;

public class Query implements Serializable {
	private static final long serialVersionUID = 1L;
	// 每页显示条数
	private Integer pageSize;
	// 页码 ，从1开始
	private Integer pageNo;
	// 排序列表
	private List<QueryOrder> queryOrderList;
	private static final int DEAFULT_PAGESIZE = 25;
	private static final int DEAFULT_PAGENO = 1;

	public Integer getPageSize() {
		return pageSize == null ? DEAFULT_PAGESIZE : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo == null || pageNo == 0 ? DEAFULT_PAGENO : pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public List<QueryOrder> getQueryOrderList() {
		return queryOrderList;
	}

	public void setQueryOrderList(List<QueryOrder> queryOrderList) {
		this.queryOrderList = queryOrderList;
	}
}
