package cn.flysnowxf.collection.dto;

public enum QueryOrderType {
	ASC(1),
	DESC(2);
	
	private Integer code;

	private QueryOrderType(Integer code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
