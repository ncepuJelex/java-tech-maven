package com.jel.tech.model.datatables;

/**
 * 
 * @author Jelex.xu
 *DataTables Search function model
 */
public class DatatableSearch {

	/**
	 * Search value to apply to this specific column
	 */
    private String value;
    /**
     * Flag to indicate if the search term for this column
     * should be treated as regular expression (true) or not (false).
     * As with global search, normally server-side processing scripts
     * will not perform regular expression searching for performance reasons
     * on large data sets, but it is technically possible
     * and at the discretion of your script.
     */
    private boolean regex;
    
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isRegex() {
		return regex;
	}
	public void setRegex(boolean regex) {
		this.regex = regex;
	}

    
}
