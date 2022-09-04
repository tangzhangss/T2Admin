$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dttablers/list',
        datatype: "json",
        colModel: [			
			{ label: '关系编号', name: 'rsId', index: 'rs_id', width: 50, key: true },
			{ label: '引用编号', name: 'useId', index: 'use_id', width: 80 }, 			
			{ label: '关系类型(union/join)', name: 'rsType', index: 'rs_type', width: 80 }, 			
			{ label: '关系模式', name: 'rsModel', index: 'rs_model', width: 80 }, 			
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
		dtTableRs: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dtTableRs = {};
		},
		update: function (event) {
			var rsId = getSelectedRow();
			if(rsId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(rsId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dtTableRs.rsId == null ? "ser/dttablers/save" : "ser/dttablers/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dtTableRs),
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
			var rsIds = getSelectedRows();
			if(rsIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dttablers/delete",
                    contentType: "application/json",
				    data: JSON.stringify(rsIds),
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
		getInfo: function(rsId){
			$.get(baseURL + "ser/dttablers/info/"+rsId, function(r){
                vm.dtTableRs = r.dtTableRs;
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