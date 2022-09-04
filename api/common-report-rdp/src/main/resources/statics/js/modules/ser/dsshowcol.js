$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dsshowcol/list',
        datatype: "json",
        colModel: [			
			{ label: '显示编号', name: 'showId', index: 'show_id', width: 50, key: true },
			{ label: '数据集编号', name: 'dtId', index: 'dt_id', width: 80 }, 			
			{ label: '引用表编号', name: 'useId', index: 'use_id', width: 80 }, 			
			{ label: '是否显示', name: 'isShow', index: 'is_show', width: 80 }, 			
			{ label: '数据类型', name: 'dataType', index: 'data_type', width: 80 }, 			
			{ label: '别名', name: 'alias', index: 'alias', width: 80 }, 			
			{ label: '描述', name: 'describe', index: 'describe', width: 80 }, 			
			{ label: '创建时间', name: 'txTime', index: 'tx_time', width: 80 }, 			
			{ label: '登记人', name: 'txOp', index: 'tx_op', width: 80 }			
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
		dsShowcol: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dsShowcol = {};
		},
		update: function (event) {
			var showId = getSelectedRow();
			if(showId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(showId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dsShowcol.showId == null ? "ser/dsshowcol/save" : "ser/dsshowcol/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dsShowcol),
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
			var showIds = getSelectedRows();
			if(showIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dsshowcol/delete",
                    contentType: "application/json",
				    data: JSON.stringify(showIds),
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
		getInfo: function(showId){
			$.get(baseURL + "ser/dsshowcol/info/"+showId, function(r){
                vm.dsShowcol = r.dsShowcol;
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