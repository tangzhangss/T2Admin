try{
    document.write("<script language=javascript src=../../../statics/js/t2admin.js></script>");
}catch (e) {}

function getConfigProp(name) {
    var config = {
        rdpserver: "../../../"
    };
    return config[name];
}

function reqServerController(path, dataparm, callback) {
    var rdpserver = getConfigProp("rdpserver");
    $.ajax({
        url: rdpserver + path,
        type: 'post',
        data: JSON.stringify(dataparm),
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            [TOKEN_TAG]:T2Admin_TOKEN
        },
        success: function (data) {
            if (callback && typeof (callback) == "function") {
                callback.call(this, data);
            }
        },
        error: function (e) {
            if (callback && typeof (callback) == "function") {
                callback.call(this, e);
            }
        }
    });
}

function reqServerControllerParms(path, dataparm, callback) {
    var rdpserver = getConfigProp("rdpserver");
    $.ajax({
        url: rdpserver + path,
        type: 'post',
        data: dataparm,
        headers:{
            [TOKEN_TAG]:T2Admin_TOKEN
        },
        success: function (data) {
            if (callback && typeof (callback) == "function") {
                callback.call(this, data);
            }
        },
        error: function (e) {
            if (callback && typeof (callback) == "function") {
                callback.call(this, e);
            }
        }
    });
}

function tableValsToNodeData(tableData) {
    var zNodes = [];
    var columns = tableData.columns;
    var values = tableData.rows;
    if (columns) {

        var len = columns.length;
        $.each(values, function (i, node) {
            var temp = {};
            for (var j = 0; j < len; j++) {
                temp[columns[j].columnName] = node[j];
            }
            zNodes.push(temp);
        });
    }else{
        layer.msg("获取数据错误，检查SQL");
    }
    return zNodes;
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg); //search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}
