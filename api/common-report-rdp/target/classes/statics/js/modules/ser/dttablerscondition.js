$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dttablerscondition/list',
        datatype: "json",
        colModel: [			
			{ label: '条件编号', name: 'cdId', index: 'cd_id', width: 50, key: true },
			{ label: '关系类型', name: 'cdType', index: 'cd_type', width: 80 }, 			
			{ label: '输出名称', name: 'rename', index: 'rename', width: 80 }, 			
			{ label: '左侧表', name: 'leftTable', index: 'left_table', width: 80 }, 			
			{ label: '左侧字段', name: 'leftCol', index: 'left_col', width: 80 }, 			
			{ label: '操作符', name: 'operator', index: 'operator', width: 80 }, 			
			{ label: '右侧表', name: 'rightTable', index: 'right_table', width: 80 }, 			
			{ label: '右侧字段', name: 'rightCol', index: 'right_col', width: 80 }, 			
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
		dtTableRsCondition: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dtTableRsCondition = {};
		},
		update: function (event) {
			var cdId = getSelectedRow();
			if(cdId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(cdId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dtTableRsCondition.cdId == null ? "ser/dttablerscondition/save" : "ser/dttablerscondition/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dtTableRsCondition),
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
			var cdIds = getSelectedRows();
			if(cdIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dttablerscondition/delete",
                    contentType: "application/json",
				    data: JSON.stringify(cdIds),
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
		getInfo: function(cdId){
			$.get(baseURL + "ser/dttablerscondition/info/"+cdId, function(r){
                vm.dtTableRsCondition = r.dtTableRsCondition;
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