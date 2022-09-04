$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ser/dataset/list',
        datatype: "json",
        colModel: [			
			{ label: '数据集编号', name: 'dtId', index: 'dt_id', width: 50, key: true },
			{ label: '数据集名称', name: 'dtName', index: 'dt_name', width: 80 }, 			
			{ label: '数据源编号', name: 'dsId', index: 'ds_id', width: 40 }, 			
			{ label: '数据集分类', name: 'type', index: 'type', width: 40 }, 			
			{ label: '数据集SQL', name: 'sql', index: 'sql', width: 180 }, 			
			{ label: '数据来源类型', name: 'dataType', index: 'data_type',align:"center", width: 45, function(value, options, row){
				return formatterCustom(value);}
			}, 			 			
			{ label: '数据集状态', name: 'sts', index: 'sts', width: 40 ,align:"center", formatter: function(value, options, row){
				return formatterSts(value, options, row);}
			}, 			
			{ label: '登记日期', name: 'txTime', index: 'tx_time', width: 65 }, 			
			{ label: '更新日期', name: 'upTime', index: 'up_time', width: 65 }, 			
			{ label: '登记人', name: 'txOp', index: 'tx_op', width: 40 }, 			
			{ label: '更新人', name: 'upOp', index: 'up_op', width: 40 },
			{ label: '操作', name: '', width: 40, formatter: function(value, options, row){
				var btns = [{"lable":"测试连接","fun":"conntest("+row.id+","+row.dsId+")"}];
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
function conntest(id,dsId){
	jqPost("ex/ser/dataset/sqlgroupresult/series",{"dtId":"4","columns":"sts","groups":"icon,type","series":"type"},
	function(data){
		layer.msg(data.msg);
		console.log(data.data);
	},function(e){
		layer.msg(e.statusText);
	});
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		dataSet: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dataSet = {};
		},
		update: function (event) {
			var dtId = getSelectedRow();
			if(dtId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(dtId)
		},
		saveOrUpdate: function (event) {
			var url = vm.dataSet.dtId == null ? "ser/dataset/save" : "ser/dataset/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dataSet),
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
			var dtIds = getSelectedRows();
			if(dtIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ser/dataset/delete",
                    contentType: "application/json",
				    data: JSON.stringify(dtIds),
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
		getInfo: function(dtId){
			$.get(baseURL + "ser/dataset/info/"+dtId, function(r){
                vm.dataSet = r.dataSet;
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