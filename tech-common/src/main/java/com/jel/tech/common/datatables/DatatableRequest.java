package com.jel.tech.common.datatables;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Jelex.xu DataTables' server-side processing:Request Model
 *
 */
public class DatatableRequest {

	/**
	 * Draw counter. This is used by DataTables to ensure that the Ajax returns
	 * from server-side processing requests are drawn in sequence by DataTables
	 */
	private Integer draw;
	/**
	 * Paging first record indicator. This is the start point in the current
	 * data set (0 index based, i.e. 0 is the first record)
	 */
	private Integer start;
	/**
	 * Number of records that the table can display in the current draw. It is
	 * expected that the number of records returned will be equal to this
	 * number, unless the server has fewer records to return. Note that this can
	 * be -1 to indicate that all records should be returned(although that
	 * negates any benefits of server-side processing!)
	 */
	private Integer length;
	/**
	 * Means global search function model here.
	 */
	DatatableSearch search;
	/**
	 * an array defining all columns in the table
	 */
	private List<DatatableColumn> columns = Collections.<DatatableColumn>emptyList();
	/**
	 * an array defining how many columns are being ordered upon - i.e. if the
	 * array length is 1, then a single column sort is being performed,
	 * otherwise a multi-column sort is being performed.
	 */
	private List<DatatableOrder> order = Collections.<DatatableOrder>emptyList();

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public DatatableSearch getSearch() {
		return search;
	}

	public void setSearch(DatatableSearch search) {
		this.search = search;
	}

	public List<DatatableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DatatableColumn> columns) {
		this.columns = columns;
	}

	public List<DatatableOrder> getOrder() {
		return order;
	}

	public void setOrder(List<DatatableOrder> order) {
		this.order = order;
	}

}
