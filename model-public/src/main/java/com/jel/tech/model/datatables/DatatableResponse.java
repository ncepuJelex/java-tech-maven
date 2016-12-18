package com.jel.tech.model.datatables;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Jelex.xu DataTables Response Model
 * 
 *	The actually response data might be like this:
 {
    "draw": 1,
    "recordsTotal": 57,
    "recordsFiltered": 57,
    "data": [
        [
            "Angelica",
            "Ramos",
            "System Architect",
            "London",
            "9th Oct 09",
            "$2,875"
        ],
        [
            "Ashton",
            "Cox",
            "Technical Author",
            "San Francisco",
            "12th Jan 09",
            "$4,800"
        ],
        ...
    ]
}
 */
public class DatatableResponse<T> {

	/**
	 * The draw counter that this object is a response to - from the draw
	 * parameter sent as part of the data request. Note that it is strongly
	 * recommended for security reasons that you cast this parameter to an
	 * integer, rather than simply echoing back to the client what it sent in
	 * the draw parameter, in order to prevent Cross Site Scripting (XSS)
	 * attacks.
	 */
	private Integer draw;
	/**
	 * Total records, before filtering (i.e. the total number of records in the
	 * database)
	 */
	private Integer recordsTotal;
	/**
	 * Total records, after filtering (i.e. the total number of records after
	 * filtering has been applied - not just the number of records being
	 * returned for this page of data).
	 */
	private Integer recordsFiltered;
	/**
	 * The data to be displayed in the table. This is an array of data source
	 * objects, one for each row, which will be used by DataTables. Note that
	 * this parameter's name can be changed using the ajax option's dataSrc
	 * property.
	 */
	private List<T> data = Collections.<T>emptyList();
	/**
	 * Optional: If an error occurs during the running of the server-side
	 * processing script, you can inform the user of this error by passing back
	 * the error message to be displayed using this parameter. Do not include if
	 * there is no error.
	 */
//	private String error;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	/*public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}*/

}
