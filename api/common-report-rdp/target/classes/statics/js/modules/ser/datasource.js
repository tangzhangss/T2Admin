$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/datasource/list',
        datatype: "json",
        colModel: [			
			{ label: '数据源编号', name: 'id', index: 'id', width: 50, key: true},
			{ label: '数据源名称', name: 'name', index: 'name', width: 50 }, 			
			{ label: '数据源大类', name: 'model', index: 'type', width: 50 }, 			
			{ label: '数据源类型', name: 'type', index: 'type', width: 50 }, 			
			{ label: '数据源版本', name: 'version', index: 'version', width: 30 }, 	
			{ label: '驱动', name: 'driver', index: 'driver', width: 80 }, 		
			{ label: '地址', name: 'addr', index: 'addr', width: 140 }, 			
			{ label: '用户', name: 'usr', index: 'usr', width: 40 }, 			
			{ label: '密码', name: 'password', index: 'password', width: 50 }, 			
			{ label: '图标', name: 'icon', index: 'icon', width: 40 }, 			
			{ label: '状态', name: 'sts', index: 'sts',width: 30  , formatter: function(value, options, row){
				return formatterSts(value, options, row);}
			},
			{ label: '只读', name: 'readonly', index: 'readonly',width: 30  , formatter: function(value, options, row){
				return formatterYN(value, options, row);}
			},
			{ label: '登记日期', name: 'txTime', index: 'tx_time', width: 80 }, 			
			{ label: '更新日期', name: 'upTime', index: 'up_time', width: 80 }, 			
			{ label: '登记人', name: 'txOp', index: 'tx_op', width: 40 }, 			
			{ label: '更新人', name: 'upOp', index: 'up_op', width: 40 },
			{ label: '操作', name: '', width: 80, formatter: function(value, options, row){
				//var btns = [{"lable":"参数","btn":"getDtParam("+row.id+")"},{"lable":"参数2","btn":"getDtParam2("+row.id+")"}];
				var btns = [{"lable":"测试连接","fun":"conntest("+row.id+")"}];
				return formatterBtn(btns);
			}}
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
/**
 * 测试数据源连接
 * @param data
 * @returns
 */
function conntest(id){
	jqPost("ser/datasource/conntest/"+id,"",
	function(data){
		layer.msg(data.msg);
	},function(e){
		layer.msg(e.statusText);
	});
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		dataSource: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dataSource = {};
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
			var url = vm.dataSource.id == null ? "ser/datasource/save" : "ser/datasource/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dataSource),
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
				    url: baseURL + "ser/datasource/delete",
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
			$.get(baseURL + "ser/datasource/info/"+id, function(r){
                vm.dataSource = r.dataSource;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	},mounted:function(){
        laydate.render({
            elem: '#upTime',
            type: 'datetime',
            done: function(date){
                vm.dataSource.upTime = date;
            }
        });
        laydate.render({
            elem: '#txTime',
            type: 'datetime',
            done: function(date){
                vm.dataSource.txTime = date;
            }
        });
    }
});