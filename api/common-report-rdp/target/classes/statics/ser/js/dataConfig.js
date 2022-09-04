var dataSourceInputs
$(function () {
    var loadingIndex = layer.msg('加载中', {
        icon: 16
        ,shade: 0.01,
        time:0
      });
    reqServerController("ex/server/testconnection", {}, function (res) {
        if (res.code==0) {
            $("#addbtn-datasource").bind("click", function () {
                location.href = "dataSources.html";
            });
            $("#addbtn-datasets").bind("click", function () {
                location.href = "dataSetsSQL.html";

            });

            reqServerController("ex/ser/datasource/getList", {}, function (data) {
                createDatasourcesList(data.list);
            });
            reqServerController("ex/ser/dataset/list", {}, function (data) {
                createDataSetsList(data.list);
            });


            dataSourceInputs = $('#dataSourceForm').inputs();
        } else {
            layer.msg('请求'+getConfigProp("rdpserver")+'服务失败！请确认服务已启动', {time: 5000, icon:5});
        }
        layer.close(loadingIndex);
    });

});




function createDatasourcesList(data) {
    $.each(data, function (i, node) {
        var itemGroupDiv = $('<div class="dataSourceItem"><div class="iconContainer"></div><div class="text"><span class="title">Oracle</span></div><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
        itemGroupDiv.find(".iconContainer").addClass(node.icon);
        itemGroupDiv.find(".title").html(node.name);
        $("#datasoucre-items").append(itemGroupDiv);
        itemGroupDiv.data(node);
        itemGroupDiv.bind("click", function () {
            editDataSource($(this));
        });
        itemGroupDiv.find(".close").bind("click", function () {
            var _this = this;
            layer.confirm("确认删除“"+$(this).parents(".dataSourceItem").data("name")+"”数据源吗？", {btn: ['确定','取消']},
            function(index){//确定事件
                reqServerController("ex/ser/datasource/delete", [$(_this).parents(".dataSourceItem").data("id")], function (data) {
                    $(_this).parents(".dataSourceItem").remove();
                });
                layer.close(index);
            });
            return false;
        })
    });
}
function createDataSetsList(data) {
    if (!data) return false;
    $.each(data, function (i, node) {
        var itemGroupDiv = $('<div class="dataSourceItem"><div class="iconContainer"></div><div class="text"><span class="title">Oracle</span></div><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
        //itemGroupDiv.find(".iconContainer").addClass(node.icon);
        itemGroupDiv.find(".title").html(node.dtName);
        $("#datasets-items").append(itemGroupDiv);
         itemGroupDiv.data(node);
        itemGroupDiv.bind("click", function () {
            location.href = "dataSetsSQL.html?dtId="+$(this).data("dtId");
        });
        itemGroupDiv.find(".close").bind("click", function () {
            var _this = this;
            reqServerController("ex/ser/dataset/delete", [$(this).parents(".dataSourceItem").data("dtId")], function (data) {
                $(_this).parents(".dataSourceItem").remove();
            });
            return false;
        })
    });
}

function editDataSource(elem) {
    var data =elem.data();
    var content ;
    if (data.type.toLowerCase()=="json") {
        content = $('#jsonForm');
        dataSourceInputs = $('#jsonForm').inputs();
    } else if(data.model.toLowerCase()=="jndi"){
        dataSourceInputs = $('#jndiForm').inputs();
        content = $('#jndiForm');
    }else {
        dataSourceInputs = $('#dataSourceForm').inputs();
        content = $('#dataSourceForm');
    }
    layer.open({
        area: ['700px', '440px'],
        type: 1,
        shade: 0.3,
        title: "数据源配置", //不显示标题
        content:content , //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        btn: ['保存', '测试'],
        yes: function (index, layero) {
            //	console.log(inputs.data());
            layer.msg('保存中', {
                icon: 16,
                shade: 0.01
            });
            reqServerController("ex/ser/datasource/update",dataSourceInputs.data(), function (res) {
                console.log(res);
                layer.closeAll('loading');
                if (res.code == 0) {
                    elem.data(res.dataSource);
                    elem.find(".title").html(res.dataSource.name);
                    layer.msg('保存成功');
                    layer.close(index);
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


            if (data.type.toLowerCase()=="json") {
                layer.msg("JSON数据源无需测试");
            } else {
                reqServerController("ex/ser/datasource/conntest", dataSourceInputs.data(), function (res) {
                    layer.closeAll('loading');
                    layer.msg(res.msg);
                });
            }

            return false;
        },
        success: function (layero, index) {
            dataSourceInputs.set({
                name: data.name,
                driver: data.driver,
                addr: data.addr,
                usr: data.usr,
                password: data.password,
                model: data.model,
                type: data.type,
                icon: data.icon,
                readonly: data.readonly,
                id: data.id,
                sts: "1"
            });
            //	console.log(layero, index);
            //layer.close(index)
        },
        cancel: function (index, layero) {}
    });
}
