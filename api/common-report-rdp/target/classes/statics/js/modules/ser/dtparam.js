$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dtparam/list',
        datatype: "json",
        colModel: [			
			{ label: '数据集参数', name: 'paramId', index: 'param_id', width: 50, key: true },
			{ label: '参数名称', name: 'paramName', index: 'param_name', width: 80 }, 			
			{ label: '数据集编号', name: 'dtId', index: 'dt_id', width: 80 }, 			
			{ label: '参数类型', name: 'paramType', index: 'param_type', width: 80 }, 			
			{ label: '数据类型', name: 'dataType', index: 'data_type', width: 80 }, 			
			{ label: '是否多值', name: 'ifValues', index: 'if_values', width: 80 }, 			
			{ label: '登记日期', name: 'txTime', index: 'tx_time', width: 80 }, 			
			{ label: '更新日期', name: 'upTime', index: 'up_time', width: 80 }, 			
			{ label: '登记人', name: 'txOp', index: 'tx_op', width: 80 }, 			
			{ label: '更新人', name: 'upOp', index: 'up_op', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		dtParam: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dtParam = {};
		},
		update: function (event) {
			var paramId = getSelectedRow();
			if(paramId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(paramId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dtParam.paramId == null ? "ser/dtparam/save" : "ser/dtparam/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dtParam),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var paramIds = getSelectedRows();
			if(paramIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dtparam/delete",
                    contentType: "application/json",
				    data: JSON.stringify(paramIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(paramId){
			$.get(baseURL + "ser/dtparam/info/"+paramId, function(r){
                vm.dtParam = r.dtParam;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});