/**
 * 启动状态 状态
 */
window.formatterSts = function(value, options, row){
	if(value == "0"){
		return '<span class="label label-danger">停用</span>';
	}else{
		return '<span class="label label-success">启用</span>';
	}
}
/**
 * 是否状态 状态
 */
window.formatterYN = function(value, options, row){
	if(value == "0"){
		return '<span class="label label-danger">否</span>';
	}else{
		return '<span class="label label-success">是</span>';
	}
}
/**
 * 自定义类型
 */
window.formatterCustom = function(value){
	return '<span class="label label-success">'+value+'</span>';
}

/**
 * 处理特殊字段显示问题
 * 特殊符号转换
 */
window.formatterSpecialCol = function (str){
	if(str==null||str ===undefined){
		return "";
	}
    var s = "";
    if(str.length == 0) return "";
    s = str.replace(/&/g,"&amp;");
    s = s.replace(/</g,"&lt;");
    s = s.replace(/>/g,"&gt;");
    s = s.replace(/ /g,"&nbsp;");
    s = s.replace(/\'/g,"&#39;");
    s = s.replace(/\"/g,"&quot;");
    return s;
}
/**
 * 列表按钮
 * btns = [{“fun”:“function”,lable:"按钮"},..]
 */
window.formatterBtn = function(btns){
	var btn_html="",
	$btn = $('<button style="margin-left: 10px;" class="btn btn-primary btn-xs" onclick=""></button>');
	$.each(btns,function(index,obj){
		$btn.attr("onclick",obj.fun);
		$btn.text(obj.lable);
		btn_html += $btn.prop("outerHTML");
	});
	return btn_html;
}

/**
 * jqery post 异步请求
 * url 相对地址
 * param 参数
 * sCallback 成功回调
 * eCallback 失败回调
 */
window.jqPostEntity = function(url,param,sCallback,eCallback){
	$.ajax({
		headers: {'Content-Type': 'application/json;charset=utf-8', [TOKEN_TAG]:T2Admin_TOKEN},
        url: baseURL + url,
        type: 'post',
        data: param,
        dataType: 'json',
        success: function (data) {
            if (sCallback && typeof (sCallback) == "function") {
                sCallback.call(this, data);
            }
        },
        error: function (e) {
            if (eCallback && typeof (eCallback) == "function") {
                eCallback.call(this, e);
            }
        }
    });

}
/**
 * jqery post 异步请求
 * url 相对地址
 * param 参数
 * sCallback 成功回调
 * eCallback 失败回调
 */
window.jqPost = function(url,param,sCallback,eCallback){
	$.ajax({
        url: baseURL + url,
        type: 'post',
        dataType: 'json',
        data: param,
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        success: function (data) {
            if (sCallback && typeof (sCallback) == "function") {
                sCallback.call(this, data);
            }
        },
        error: function (e) {
            if (eCallback && typeof (eCallback) == "function") {
                eCallback.call(this, e);
            }
        }
    });

}
