var inputs
$(function () {
	reqServerController("../ex/ser/dbtype/list", {}, function (data) {
		createDatasourcesList(data.list);
	});



	$("#goBack").bind("click", function () {
		location.href = "dataConfig.html";
	});



	inputs = $('#dataSourceForm').inputs();

});

function createDatasourcesList(data) {
	$.each(data, function (i, node) {
		var itemDiv = $('<div class="datasource-item child-node "><div class="datasource-item-icon"></div><div class="datasource-item-name">Oracle</div></div>');
		itemDiv.find(".datasource-item-icon").addClass(node.icon);
		itemDiv.find(".datasource-item-name").html(node.name);
		$("#item-" + node.model).append(itemDiv);
		itemDiv.data(node);

		var itemGroupDiv = $('<div class="dataSourceItem"><div class="iconContainer"></div><div class="text"><span class="title">Oracle</span></div></div>');
		itemGroupDiv.find(".iconContainer").addClass(node.icon);
		itemGroupDiv.find(".title").html(node.name);
		$("#itemGroup-" + node.model).append(itemGroupDiv);
		itemGroupDiv.data(node);

		itemDiv.bind("click", function () {
			editDataSource($(this).data());
		});
		itemGroupDiv.bind("click", function () {
			editDataSource($(this).data());
		});
	});
}


function editDataSource(data) {
	//console.log(data);
	if (data.type.toLowerCase() == "json") {
		layer.open({
			area: ['700px', '400px'],
			type: 1,
			shade: 0.3,
			title: "数据源配置", //不显示标题
			content: $('#jsonForm'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
			btn: ['保存'],
			yes: function (index, layero) {
				//	console.log(inputs.data());
				console.log(inputs.data());
				layer.msg('保存中', {
					icon: 16,
					shade: 0.01
				});
				reqServerController("../ex/ser/datasource/save", inputs.data(), function (res) {
					console.log(res);
					layer.closeAll('loading');
					if (res.code == 0) {
						layer.msg('保存成功');
						layer.close(index);
					} else {
						layer.msg(res.msg);
					}
				});
				return false;
			},
			// btn2: function (index, layero) {
			// 	//按钮【按钮二】的回调
			// 	layer.msg('加载中', {
			// 		icon: 16,
			// 		shade: 0.01
			// 	});
			// 	reqServerController("ex/ser/datasource/conntest", inputs.data(), function (res) {
			// 		layer.closeAll('loading');
			// 		if (res.code == 0) {
			// 			layer.msg('连接成功');

			// 		}
			// 		console.log(res);
			// 	});
			// 	return false;
			// },
			success: function (layero, index) {
				inputs = $('#jsonForm').inputs();
				inputs.set({
					name: "",
					addr: "",
					usr: "",
					password: "",
					model: "json",
					type: data.type,
					sts: "1",
					icon: data.icon
				});
				//	console.log(layero, index);
				//layer.close(index)
			},
			cancel: function (index, layero) {}
		});
	}else if(data.type.toLowerCase() == "jndi"){
		layer.open({
			area: ['700px', '400px'],
			type: 1,
			shade: 0.3,
			title: "数据源配置", //不显示标题
			content: $('#jndiForm'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
			btn: ['保存', '测试'],
			yes: function (index, layero) {
				//	console.log(inputs.data());
				console.log(inputs.data());
				layer.msg('保存中', {
					icon: 16,
					shade: 0.01
				});
				reqServerController("../ex/ser/datasource/save", inputs.data(), function (res) {
					console.log(res);
					layer.closeAll('loading');
					if (res.code == 0) {
						layer.msg('保存成功');
						layer.close(index);
					} else {
						layer.msg(res.msg);
					}
				});
				return false;
			},
			btn2: function (index, layero) {
				//按钮【按钮二】的回调
				layer.msg('加载中', {
					icon: 16,
					shade: 0.01
				});
				reqServerController("../ex/ser/datasource/conntest", inputs.data(), function (res) {
					layer.closeAll('loading');
					layer.msg(res.msg);
					//console.log(res);
				});
				return false;
			},
			success: function (layero, index) {
				inputs = $('#jndiForm').inputs();
				inputs.set({
					driver: data.driver,
					addr: data.addrDemo,
					readonly: 1,
					usr: "",
					password: "",
					model: data.model,
					name: "",
					sts: "1",
					icon: data.icon
				});
				//	console.log(layero, index);
				//layer.close(index)
			},
			cancel: function (index, layero) {}
		});
	} else {
		layer.open({
			area: ['700px', '400px'],
			type: 1,
			shade: 0.3,
			title: "数据源配置", //不显示标题
			content: $('#dataSourceForm'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
			btn: ['保存', '测试'],
			yes: function (index, layero) {
				//	console.log(inputs.data());
				console.log(inputs.data());
				layer.msg('保存中', {
					icon: 16,
					shade: 0.01
				});
				reqServerController("../ex/ser/datasource/save", inputs.data(), function (res) {
					console.log(res);
					layer.closeAll('loading');
					if (res.code == 0) {
						layer.msg('保存成功');
						layer.close(index);
					} else {
						layer.msg(res.msg);
					}
				});
				return false;
			},
			btn2: function (index, layero) {
				//按钮【按钮二】的回调
				layer.msg('加载中', {
					icon: 16,
					shade: 0.01
				});
				reqServerController("../ex/ser/datasource/conntest", inputs.data(), function (res) {
					layer.closeAll('loading');
					layer.msg(res.msg);
					//console.log(res);
				});
				return false;
			},
			success: function (layero, index) {
				inputs = $('#dataSourceForm').inputs();
				inputs.set({
					name: data.name,
					driver: data.driver,
					addr: data.addrDemo,
					readonly: 1,
					usr: "",
					password: "",
					model: data.model,
					type: data.type,
					sts: "1",
					icon: data.icon
				});
				//	console.log(layero, index);
				//layer.close(index)
			},
			cancel: function (index, layero) {}
		});
	}



}