package com.jel.tech.model.datatables;

/**
 * 列排序枚举类
 */
public enum OrderDirection {

	Direction_ASC("asc","升序"),
	Direction_DESC("desc","降序");
	/**
	 * ascending ordering OR descending ordering
	 */
	private String order;
	/**
	 * ordering description
	 */
	private String description;
	
	private OrderDirection(String order, String description) {
		this.order = order;
		this.description = description;
	}

	public String getOrder() {
		return order;
	}
	public String getDescription() {
		return description;
	}
}
