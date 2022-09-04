$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dtfilter/list',
        datatype: "json",
        colModel: [			
			{ label: '过滤编号', name: 'filterId', index: 'filter_id', width: 50, key: true },
			{ label: '数据集编号', name: 'dtId', index: 'dt_id', width: 80 }, 			
			{ label: '过滤表名', name: 'tableName', index: 'table_name', width: 80 }, 			
			{ label: '过滤字段', name: 'column', index: 'column', width: 80 }, 			
			{ label: '操作符', name: 'colType', index: 'col_type', width: 80 }, 			
			{ label: '', name: 'operator', index: 'operator', width: 80 }, 			
			{ label: '参数数据类型', name: 'paramType', index: 'param_type', width: 80 }, 			
			{ label: '参数', name: 'param', index: 'param', width: 80 }, 			
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
		dtFilter: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dtFilter = {};
		},
		update: function (event) {
			var filterId = getSelectedRow();
			if(filterId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(filterId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dtFilter.filterId == null ? "ser/dtfilter/save" : "ser/dtfilter/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dtFilter),
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
			var filterIds = getSelectedRows();
			if(filterIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dtfilter/delete",
                    contentType: "application/json",
				    data: JSON.stringify(filterIds),
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
		getInfo: function(filterId){
			$.get(baseURL + "ser/dtfilter/info/"+filterId, function(r){
                vm.dtFilter = r.dtFilter;
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