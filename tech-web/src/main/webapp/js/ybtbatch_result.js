$(document).ready(function() {
	/*
	 初始化银行和任务数据
	 */
	maintenanceSearch.createBanks();
	// 对表单进行验证操作
	$('#ybtbatchResultForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			bank: {
				validators: {
					notEmpty: {
						message: '银行为必输项'
					}
				}
			},
			taskDate : {
				validators : {
					notEmpty : {
						message : '跑批日期为必输项'
					},
					regexp : {
						regexp : /^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$/,
						message : '跑批日期格式不正确，必须为yyyy-MM-dd格式，例如：2011-11-11'
					}
				}
			}
		}
	});

	$("#taskDate").datetimepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		minView: "month",
		maxView: "decade",
		language: 'zh-CN',
		todayBtn: true,
		forceParse : false,
		pickerPosition: "bottom-left",
		//startDate:'2016-11-11',
		endDate:new Date()
	}).change(function(){
		$('#ybtbatchResultForm').bootstrapValidator('updateStatus', "taskDate", 'NOT_VALIDATED');
	});

});

var maintenanceSearch = {

	banks:[{'请选择':''},{'农业银行':'abc'},{'民生银行':'cmbc'},{'江苏银行':'jsbc'},
		{'北农商':'brcb'},{'广发银行':'cgb'},{'邮储银行':'psbc'},{'建设银行':'ccb'}],

	createBanks : function() {
		var bank = document.getElementById('bank');
		var banks = maintenanceSearch.banks;
		console.log(banks);
		for(var i in banks) {
			var op;
			for(var key in banks[i]) {
				op = new Option(key,banks[i][key]);
			}
			bank.options.add(op);
		}
	}
};