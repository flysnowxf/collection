package cn.flysnowxf.collection.dto;

public class QueryOrder {
	private String name;
	private QueryOrderType queryOrderType;
	
	public QueryOrder(String name, QueryOrderType queryOrderType) {
		this.name = name;
		this.queryOrderType = queryOrderType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public QueryOrderType getQueryOrderType() {
		return queryOrderType;
	}
	public void setQueryOrderType(QueryOrderType queryOrderType) {
		this.queryOrderType = queryOrderType;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.queryOrderType.name();
	}
}
