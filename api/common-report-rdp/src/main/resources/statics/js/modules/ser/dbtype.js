$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dbtype/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: 'id', width: 40, key: true },
			{ label: '类型名称', name: 'name', index: 'name', width: 50 }, 			
			{ label: '数据库大类', name: 'model', index: 'type', width: 50 }, 			
			{ label: '支持类型', name: 'type', index: 'type', width: 50 }, 			
			{ label: '数据库驱动', name: 'driver', index: 'driver', width: 80 }, 			
			{ label: '数据库地址例子', name: 'addrDemo', index: 'addr_demo', width: 100 , formatter: function(value, options, row){
				return formatterSpecialCol(row.addrDemo);}
			},			
			{ label: '状态', name: 'sts', index: 'sts', width: 40 , formatter: function(value, options, row){
				return formatterSts(value, options, row);}
			}	
			,			
			{ label: '图标', name: 'icon', index: 'icon', width: 40}
        ],
		viewrecords: true,
        height: 700,
        rowNum: 20,
		rowList : [10,20,30,40,50,100],
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
		dbType: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dbType = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.dbType.id == null ? "ser/dbtype/save" : "ser/dbtype/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dbType),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dbtype/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get(baseURL + "ser/dbtype/info/"+id, function(r){
                vm.dbType = r.dbType;
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