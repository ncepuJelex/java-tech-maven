package com.jel.tech.model.datatables;

/**
 * 
 * @author Jelex.xu 
 *  DataTables Ordering Model
 *
 */
public class DatatableOrder {

	/**
	 * Column to which ordering should be applied. This is an index reference to
	 * the columns array of information that is also submitted to the server.
	 */
	private Integer column;
	/**
	 * Ordering direction for this column. It will be asc or desc to indicate
	 * ascending ordering or descending ordering, respectively. You may want to
	 * get the value through the use of OrderDirection class.
	 */
	private String dir;

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

}
