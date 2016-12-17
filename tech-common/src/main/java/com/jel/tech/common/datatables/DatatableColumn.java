package com.jel.tech.common.datatables;

/**
 * 
 * @author Jelex.xu
 *  Column Model in the table.
 *  a request example at the front-end might be like this:
 	var t = $('#example').DataTable( {
        "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        } ],
        "order": [[ 1, 'asc' ]]
    } );

 */
public class DatatableColumn {

	/**
	 * Column's data source,as defined by columns.data. Which can be used to
	 * read and write data to and from any data source property, including
	 * deeply nested objects / properties.
	 */
	private String data;
	/**
	 * Column's name, as defined by columns.name. Since: DataTables 1.10 Set a
	 * descriptive name for a column.
	 */
	private String name;
	/**
	 * Flag to indicate if this column is searchable (true) or not (false). This
	 * is controlled by columns.searchable.
	 */
	private boolean searchable;
	/**
	 * Flag to indicate if this column is orderable (true) or not (false). This
	 * is controlled by columns.orderable :Enable or disable ordering on this
	 * column
	 */
	private boolean orderable;
	/**
	 * Here used as a model to search specific column, which is
	 * different to the usage situation in DatatableReq class,
	 * and I don't think you would like to use it for performance reason.
	 */
	private DatatableSearch search;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isOrderable() {
		return orderable;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	public DatatableSearch getSearch() {
		return search;
	}

	public void setSearch(DatatableSearch search) {
		this.search = search;
	}

}
