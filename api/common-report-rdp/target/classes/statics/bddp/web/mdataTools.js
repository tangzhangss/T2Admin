function executeSQL(sqlStr) {
	var res ="";
	$.ajax({
		type : "POST",
		async:false,
		url : "../../bddpchart/executeSQL", 
		data : {
			"sqlStr" : sqlStr
		},
		dataType: "json", 
		success : function(result) {
			if(result.code==0){
				res = result.res;
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}
function executeSQLAsObject(sqlStr) {
	var res ="";
	$.ajax({
		type : "POST",
		async:false,
		url : "../../bddpchart/executeSQLAsObject", 
		data : {
			"sqlStr" : sqlStr
		},
		dataType: "json", 
		success : function(result) {
			if(result.code==0){
				res = result.res;
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}
function executeUpdateSQL(sqlStr) {
	var res ="";
	$.ajax({
		type : "POST",
		async:false,
		url : "../../bddpchart/executeUpdateSQL", 
		data : {
			"sqlStr" : sqlStr
		},
		dataType: "json", 
		success : function(result) {
			if(result.code==0){
				res = result;
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}
function executeUpdateSQLStr(sqlStr) {
	var res ="";
	$.ajax({
		type : "POST",
		async:false,
		url : "../../bddpchart/executeUpdateSQLStr", 
		data : {
			"sqlStr" : sqlStr
		},
		dataType: "json", 
		success : function(result) {
			if(result.code==0){
				res = result;
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}
function executeInsertSQL(sqlStr) {
	var res ="";
	$.ajax({
		type : "POST",
		async:false,
		url : "../../bddpchart/executeInsertSQL", 
		data : {
			"sqlStr" : sqlStr
		},
		dataType: "json", 
		success : function(result) {
			if(result.code==0){
				res = result;
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}

function getJSONFileData(path,callback){
	$.ajax({
		type : "get",
		url : path, 
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function getJSONFileDataSync(path){
	var res = "";
	$.ajax({
		type : "get",
		url : path, 
		async:false,
		dataType: "json", 
		success : function(result) {
			res =result;
		},
		error:function(e){
			console.log(e);
		}
	});
	return res;
}

function getAjaxData(path,data,callback){
	data = data||{};
	$.ajax({
		type : "get",
		url : path, 
		data:data,
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function saveBddpData(data,callback){
	var bddata = Base64Util.encode64(JSON.stringify(data));
	$.ajax({
		type : "post",
		url : "../../bddpchart/saveBddpDataForFolder", 
		// url : "../../bddp/saveBddpData", 
		data:{
			name:data.content.id,
			data:bddata
		},
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function saveBddpDiyTagsData(data,callback){
	$.ajax({
		type : "post",
		url : "../../bddpchart/saveDiyTags", 
		// url : "../../bddp/saveBddpData", 
		data:data,
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function saveBddpBgi(name,bgi,callback){
	$.ajax({
		type : "post",
		url : "../../bddpchart/saveBddpBgi", 
		data:{
			name:name,
			bgi:bgi
		},
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function getAjaxMapData(data,callback){
	data = data||{};
	$.ajax({
		type : "get",
		url : "../../bddpchart/getJSONMapContent", 
		data:data,
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}
function getAjaxMapDataSync(data,callback){
	data = data||{};
	$.ajax({
		type : "get",
		async:false,
		url : "../../bddpchart/getJSONMapContent", 
		data:data,
		dataType: "json", 
		success : function(result) {
			if(callback){
				callback.call(this,result);
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}

function requestFullScreen(element) {
	if(!element){
		element = document.body;
	}
    // 判断各种浏览器，找到正确的方法
    var requestMethod = element.requestFullScreen || //W3C
    element.webkitRequestFullScreen ||    //Chrome等
    element.mozRequestFullScreen || //FireFox
    element.msRequestFullScreen; //IE11
    if (requestMethod) {
        requestMethod.call(element);
    }
    else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
        }
    }
}

//退出全屏 判断浏览器种类
function exitFull() {
    // 判断各种浏览器，找到正确的方法
    var exitMethod = document.exitFullscreen || //W3C
    document.mozCancelFullScreen ||    //Chrome等
    document.webkitExitFullscreen || //FireFox
    document.webkitExitFullscreen; //IE11
    if (exitMethod) {
        exitMethod.call(document);
    }
    else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
        }
    }
}