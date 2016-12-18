/*
 two ways to add your params to request the data you need:

 1.Add data to the request, returnng an object by extending the default data:

	 $('#example').dataTable( {
	 "ajax": {
	 "url": "data.json",
	 "data": function ( d ) {
	 return $.extend( {}, d, {
	 "extra_search": $('#extra').val()
	 } );
	 }
	 }
	 } );

2.Add data to the request by manipulating the data object:
	 $('#example').dataTable( {
	 "ajax": {
	 "url": "data.json",
	 "data": function ( d ) {
	 d.extra_search = $('#extra').val();
	 }
	 }
	 } );
*/

/*note:
	the js is used to make it convenient to fire an ajax request to get data so as to
	show it in the table in a jQuery-DataTables way.

	tableId: the document id element name;
		for example:
 			<table id="exampleTableId" class="table table-bordered table-hover">
			   <thead>
				 <tr>
					 <th>Property1</th>
					 <th>Property2</th>
					 <th>Property3</th>
					 <th>Property4</th>
					 <th>Property5</th>
					 <th>Operation</th>
				 </tr>
			   </thead>
				 <tbody></tbody>
			</table>
		That's it,a typical table, and the id attribute value 'exampleTableId' is what we need.

	url: the url to which you post the request;

	paramMap: request params you pass:
		think a situation like this,
		if you want to query your physical exercise(running) plan at day 2016-12-18,and you can compose
		you request param in this way:
			var paramMap = {taskName:'running',taskDate:'2016-12-18'};
		in which, taskName and taskDate are the input query condition,
		with 'running' and '2016-12-18' be their values respectively,
		by the way,the taskDate can be a datetimepicker selected value and that's the sugguested way;

	dataColumns: the columns you would like to show in the DataTable,actually,they are corresponding to the position
 		at the table->thread->tr>th element and it's an array filled with js object. below is an example:

			 columns = [
				 {'data':'deptId'},
				 {'data':'deptName'},
				 {'data':'parentId'},
				 {'data':'icon'},
				 {'data':'rank'},
				 {'data':"deptId","render": function (data, type, row) {
					 return '<div class="dropdown text-center">'
					 +'  <span class="glyphicon glyphicon-cog" data-toggle="dropdown"'
					 +'	aria-haspopup="true" aria-expanded="false" style="cursor:pointer;"></span>'
					 +'  <ul class="dropdown-menu" aria-labelledby="dLabel">'
					 +'  <li><a href="#" data-toggle="modal" data-target="#deptModal" onclick="deptMain.edit(\''+row.deptId+'\')">修改</a></li> '
					 +'  <li><a href="#" onclick="deptMain.deleteInfo(\''+row.deptId+'\')">删除</a></li> '
					 +'  </ul>'
					 +'</div>';
					 }
				 }
			 ];

	dataColumnDefs: user's own columns' settings can be set here,for example:

	 var columnDefs = [
		 {
			 "targets": 2,
			 "bSortable": false,
			 "searchable": false,
 			 "render": function ( data, type, row ) {
					//you extra process goes here.
			 }
		 },
		 {
			targets: [3,5],
			visible: false
		 }
	 ];

	callback: as the name says,it's a callback function.
 		If you want to run some javascript on the contents of the table after its initial draw,
 		and after every redraw / page, try using draw Event or drawCallback options.
 			draw Event:
				 var table = $('#example').DataTable();

				 table.on( 'draw.dt', function () {
				 console.log( 'Table draw event' );
				 })

 			drawCallback:
				 $('#example').dataTable( {
				 "drawCallback": function( settings ) {
				 var api = this.api();

				 // Output the data for the visible rows to the browser's console
				 console.log( api.rows( {page:'current'} ).data() );
				 }
				 } );

 	finally,important:
 		for it to be working,the language.json comes with this js,you should bind them together,put them in the same position.

 */
var dataTable = {
	initTable : function(tableId,url,paramMap,dataColumns,dataColumnDefs,callback) {
		//get the DataTable object
		var otable =$("#"+tableId).dataTable();
		//destroy the DataTable if exists so as to reinitialize it.
		if(otable) {
			otable.fnDestroy();
		}
		/*$("#"+tableId).on('order.dt', callback())
		 .on('page.dt', callback())*/

		$("#"+tableId).dataTable({
			"processing": true,
			'serverSide': true,
			"pagingType": "full_numbers",
			"searching":false,
			"lengthMenu": [ [10,15,20,50,100], [10,15,20,50,100] ],
			"language": {
				"url": "js/luaguage.json"
			},
			'ajax' : {
				'url' : url,
				'dataType': 'jsonp',
				'type': 'POST',
				'contentType': 'application/json; charset=utf-8',
				'data': function (d) {
					console.log(d);
					d.paramMap = paramMap;
					return JSON.stringify(d);
				}
			},
			columns:dataColumns,
			columnDefs:dataColumnDefs,
			order: [[ 0, 'asc' ]],
			"drawCallback": function( settings ) {
				callback();
			}
		});
		/*.on( 'draw.dt', callback());*/
  	}
};