$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dttable/list',
        datatype: "json",
        colModel: [			
			{ label: '引用编号', name: 'useId', index: 'use_id', width: 50, key: true },
			{ label: '数据集编号', name: 'dtId', index: 'dt_id', width: 80 }, 			
			{ label: '表名', name: 'tableName', index: 'table_name', width: 80 }, 			
			{ label: '表说明', name: 'tableComments', index: 'table_comments', width: 80 }, 			
			{ label: '层级', name: 'level', index: 'level', width: 80 }, 			
			{ label: '显示名称', name: 'showName', index: 'show_name', width: 80 }, 			
			{ label: '横向位置', name: 'px', index: 'px', width: 80 }, 			
			{ label: '纵向位置', name: 'py', index: 'py', width: 80 }, 			
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
		dtTable: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dtTable = {};
		},
		update: function (event) {
			var useId = getSelectedRow();
			if(useId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(useId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dtTable.useId == null ? "ser/dttable/save" : "ser/dttable/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dtTable),
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
			var useIds = getSelectedRows();
			if(useIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dttable/delete",
                    contentType: "application/json",
				    data: JSON.stringify(useIds),
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
		getInfo: function(useId){
			$.get(baseURL + "ser/dttable/info/"+useId, function(r){
                vm.dtTable = r.dtTable;
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