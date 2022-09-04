
function bulidDetailNolabel(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		detailBulid(dataobj, "", box, type)
		data.dataobj = dataobj;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}
function bulidDetail(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		var series = data.series;
		if (series && series.length > 0) {
			var seriesVals = getJsonValue(res, data.series[0].keyname);
			detailBulid(dataobj, seriesVals, box, type)
		}
		data.dataobj = dataobj;
		data.seriesVals = seriesVals;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}
function bulidDetailIcon(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		var series = data.series;
		if (series && series.length > 0) {
			var seriesVals = getJsonValue(res, data.series[0].keyname);
			detailBulid(dataobj, seriesVals, box, type)
		}
		data.dataobj = dataobj;
		data.seriesVals = seriesVals;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}
function bulidDetailImg(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		var series = data.series;
		if (series && series.length > 0) {
			var seriesVals = getJsonValue(res, data.series[0].keyname);
			detailBulid(dataobj, seriesVals, box, type)
		}
		data.dataobj = dataobj;
		data.seriesVals = seriesVals;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}
function bulidDetailH(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		var series = data.series;
		if (series && series.length > 0) {
			var seriesVals = getJsonValue(res, data.series[0].keyname);
			detailBulid(dataobj, seriesVals, box, type)
		}
		data.dataobj = dataobj;
		data.seriesVals = seriesVals;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}
function bulidDetailTime(prop, res) {
	var box = initDetailTag(prop, res);
	var data = prop.data;
	var type = prop.type;
	if (res) {
		var dataobj = getDetailData(res, data);
		var series = data.series;
		if (series && series.length > 0) {
			var seriesVals = getJsonValue(res, data.series[0].keyname);
			detailBulid(dataobj, seriesVals, box, type)
		}
		data.dataobj = dataobj;
		data.seriesVals = seriesVals;
	} else {
		detailBulid(data.dataobj, data.seriesVals, box, type);
	}
	var dtStyle = prop.dt;
	if (dtStyle) {
		$.each(dtStyle, function (k, v) {
			setDetailProp(k, v, box, prop);
		})
	}
	return box;
}



function detailBulid(dataobj, seriesVals, box, type) {

	var div = $('<div class="dt-body"></div>');
	switch (type) {
		case "detail-nolabel":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group"></div>');
				$.each(dataobj.data, function (key, value) {
					var item = $('<div class="dt-item"></div>');
					var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
					item.append(desc);
					itemGroup.append(item);
				})
				div.append(itemGroup);
			}
			box.append(div);
			break;
		case "detail":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group"></div>');
				$.each(dataobj.data, function (key, value) {
					var item = $('<div class="dt-item"></div>');
					var label = $('<label class="dt-item-label">' + seriesVals[key] + '</label>');
					item.append(label);
					var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
					item.append(desc);
					itemGroup.append(item);
				})
				div.append(itemGroup);
			}
			box.append(div);
			break;
		case "detail-icon":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group"></div>');
				var itemGroupL = $('<div class="dt-group-l"></div>');
				var itemGroupR = $('<div class="dt-group-r"></div>');
				var icon = $('<i class="' + seriesVals[i] + '"></i>');
				itemGroupL.append(icon);
				$.each(dataobj.data, function (key, value) {
					var item = $('<div class="dt-item"></div>');
					var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
					item.append(desc);
					itemGroupR.append(item);
				});
				itemGroup.append(itemGroupL).append(itemGroupR)
				div.append(itemGroup);
			}
			box.append(div);
			break;
		case "detail-img":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group"></div>');
				var itemGroupL = $('<div class="dt-group-l"></div>');
				var itemGroupR = $('<div class="dt-group-r"></div>');
				var imgStr = seriesVals[i];
				// if (imgStr.indexOf("base64") > -1) {
					var img = $('<img src="' + seriesVals[i] + '"/>');
					itemGroupL.append(img);
					$.each(dataobj.data, function (key, value) {
						var item = $('<div class="dt-item"></div>');
						var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
						item.append(desc);
						itemGroupR.append(item);
					});
					itemGroup.append(itemGroupL).append(itemGroupR)
					div.append(itemGroup);
				// } else {
				// 	getURLBase64(imgStr).then(function (res) {
				// 		var img = $('<img src="' + res + '"/>');
				// 		itemGroupL.append(img);
				// 		$.each(dataobj.data, function (key, value) {
				// 			var item = $('<div class="dt-item"></div>');
				// 			var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
				// 			item.append(desc);
				// 			itemGroupR.append(item);
				// 		});
				// 		itemGroup.append(itemGroupL).append(itemGroupR)
				// 		div.append(itemGroup);
				// 	},function(err) {
				// 		console.log("请求失败");
				// 	})
				// }

			}
			box.append(div);
			break;
		case "detail-h":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group"></div>');
				$.each(dataobj.data, function (key, value) {
					var item = $('<div class="dt-item dt-item-h"></div>');
					var label = $('<label class="dt-item-label">' + seriesVals[key] + '</label>');
					item.append(label);
					var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
					item.append(desc);
					itemGroup.append(item);
				})
				div.append(itemGroup);
			}
			box.append(div);
			break;
		case "detail-time":
			for (var i = 0; i < dataobj.len; i++) {
				var itemGroup = $('<div class="dt-group dt-time"></div>');
				var itemGroupL = $('<div class="dt-group-l"></div>');
				var itemGroupR = $('<div class="dt-group-r"></div>');
				var icon = $('<div class="dt-time-info">' + seriesVals[i] + '</div>');
				itemGroupL.append(icon);
				$.each(dataobj.data, function (key, value) {
					var item = $('<div class="dt-item"></div>');
					var desc = $('<div class="dt-item-desc">' + value[i] + '</div>');
					item.append(desc);
					itemGroupR.append(item);
				});
				itemGroup.append(itemGroupL).append(itemGroupR)
				div.append(itemGroup);
			}
			box.append(div);
			break;

		default:
			break;
	}


}




function initDetailTag(prop, res) {
	var id = prop.id;
	var box;
	if($("#"+id).length >0){
		box = $("#"+id);
		box.data("prop", prop);
	}else{

		box = $('<div class="box" style="width:480px;height: 320px;"></div>');
		var parts = prop.parts;
		setRectP(box, prop);
		box.appendTo("#content");
		box.data("prop", prop);
		box.initBox({
			tagType: prop.type
		});
		setTagsParts(box, parts, prop);
	}
	return box;
}

function getDetailData(res, data) {
	var dataobj = {};
	if (res) {
		var arrdata = {};
		var dimension = data.dimension;
		$.each(dimension, function (i, node) {
			var keys = node.keyname.split(".");
			var lastkn = keys[keys.length - 1];
			var tempdata = getJsonValue(res, node.keyname);

			arrdata[lastkn] = tempdata;
			dataobj.len = tempdata.length;
		})
		dataobj.data = arrdata;
	}
	return dataobj;
}


function setDetailProp(key, val, box, prop) {
	switch (key) {
		case "labelwidth":
			box.find(".dt-item-label").css("width", val + "px");
			break;
		case "labelfontsize":
			box.find(".dt-item-label").css("font-size", val + "px");
			break;
		case "labelbackgroundcolor":
			box.find(".dt-item-label").css("background-color", val);
			break;
		case "labelcolor":
			box.find(".dt-item-label").css("color", val);
			break;
		case "labelfontweight":
			box.find(".dt-item-label").css("font-weight", val);
			break;
		case "labelshadow":
			box.find(".dt-item-label").css("text-shadow", val);
			break;
		case "labelborderwidth":
			box.find(".dt-item-label").css("border-width", val + "px");
			break;
		case "labelbordercolor":
			box.find(".dt-item-label").css("border-color", val);
			break;
		case "labelborderstyle":
			box.find(".dt-item-label").css("border-style", val);
			break;
		case "labelborderradius":
			box.find(".dt-item-label").css("border-radius", val + "px");
			break;
		case "labeltextalign":
			box.find(".dt-item-label").css("text-align", val);
			break;
		case "labellineheight":
			box.find(".dt-item-label").css("line-height", val + "px");
			break;
		case "labelpadding":
			box.find(".dt-item-label").css("padding", val + "px");
			break;
		case "descwidth":
			box.find(".dt-item-desc").css("width", val + "px");
			break;
		case "descfontsize":
			box.find(".dt-item-desc").css("font-size", val + "px");
			break;
		case "descbackgroundcolor":
			box.find(".dt-item-desc").css("background-color", val);
			break;
		case "desccolor":
			box.find(".dt-item-desc").css("color", val);
			break;
		case "descfontweight":
			box.find(".dt-item-desc").css("font-weight", val);
			break;
		case "descshadow":
			box.find(".dt-item-desc").css("text-shadow", val);
			break;
		case "descborderwidth":
			box.find(".dt-item-desc").css("border-width", val + "px");
			break;
		case "descbordercolor":
			box.find(".dt-item-desc").css("border-color", val);
			break;
		case "descborderstyle":
			box.find(".dt-item-desc").css("border-style", val);
			break;
		case "descborderradius":
			box.find(".dt-item-desc").css("border-radius", val + "px");
			break;
		case "desctextalign":
			box.find(".dt-item-desc").css("text-align", val);
			break;
		case "desclineheight":
			box.find(".dt-item-desc").css("line-height", val + "px");
			break;
		case "descpadding":
			box.find(".dt-item-desc").css("padding", val + "px");
			break;
		case "itembackgroundcolor":
			box.find(".dt-item").css("background-color", val);
			break;
		case "itemborderwidth":
			box.find(".dt-item").css("border-width", val + "px");
			break;
		case "itembordercolor":
			box.find(".dt-item").css("border-color", val);
			break;
		case "itemborderstyle":
			box.find(".dt-item").css("border-style", val);
			break;
		case "itemborderradius":
			box.find(".dt-item").css("border-radius", val + "px");
			break;
		case "itempadding":
			box.find(".dt-item").css("padding", val + "px");
			break;
		case "groupbackgroundcolor":
			box.find(".dt-group").css("background-color", val);
			break;
		case "groupborderwidth":
			box.find(".dt-group").css("border-width", val + "px");
			break;
		case "groupbordercolor":
			box.find(".dt-group").css("border-color", val);
			break;
		case "groupborderstyle":
			box.find(".dt-group").css("border-style", val);
			break;
		case "groupborderradius":
			box.find(".dt-group").css("border-radius", val + "px");
			break;
		case "grouppadding":
			box.find(".dt-group").css("padding", val + "px");
			break;
		case "timewidth":
			box.find(".dt-time-info").css("width", val + "px");
			break;
		case "timeheight":
			box.find(".dt-time-info").css("height", val + "px");
			break;
		case "timelineheight":
			box.find(".dt-time-info").css("line-height", val + "px");
			break;
		case "timebgcolor":
			box.find(".dt-time-info").css("background-color", val);
			break;
		case "timecolor":
			box.find(".dt-time-info").css("color", val);
			break;
		case "timepadding":
			box.find(".dt-time-info").css("padding", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "timemargin":
			box.find(".dt-time-info").css("margin", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "timefontsize":
			box.find(".dt-time-info").css("font-size", val + "px");
			break;
		case "timeweight":
			box.find(".dt-time-info").css("font-weight", val);
			break;
		case "timelineleft":
			box.find(".dt-time .dt-group-r").css("margin-left", val + "px");
			break;
		case "timelineright":
			box.find(".dt-time .dt-group-r").css("padding-left", val + "px");
			break;
		case "timelinesize":
			box.find(".dt-time .dt-group-r").css("border-left-width", val + "px");
			break;
		case "timelinecolor":
			box.find(".dt-time .dt-group-r").css("border-left-color", val);
			break;
		case "timelinestyle":
			box.find(".dt-time .dt-group-r").css("border-left-style", val);
			break;
		case "timeborderradius":
			box.find(".dt-time-info").css("border-radius", val + "px");
			break;
		case "timebordersize":
			box.find(".dt-time-info").css("border-width", val + "px");
			break;
		case "timebordercolor":
			box.find(".dt-time-info").css("border-color", val);
			break;
		case "timeborderstyle":
			box.find(".dt-time-info").css("border-style", val);
			break;
		case "timedescmarginleft":
			box.find(".dt-time .dt-group-r").css("padding-right", val + "px");
			break;

		case "iconwidth":
			box.find(".dt-group-l ").css("width", val + "px");
			break;
		case "iconheight":
			box.find(".dt-group-l ").css("height", val + "px");
			break;
		case "iconlineheight":
			box.find(".dt-group-l ").css("line-height", val + "px");
			break;
		case "iconbgcolor":
			box.find(".dt-group-l ").css("background-color", val);
			break;
		case "iconpadding":
			box.find(".dt-group-l").css("padding", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "iconmargin":
			box.find(".dt-group-l").css("padding", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "iconborderradius":
			box.find(".dt-group-l ").css("border-radius", val + "px");
			break;
		case "iconbordersize":
			box.find(".dt-group-l ").css("border-width", val + "px");
			break;
		case "iconbordercolor":
			box.find(".dt-group-l ").css("border-color", val);
			break;
		case "iconborderstyle":
			box.find(".dt-group-l ").css("border-style", val);
			break;
		case "iconcolor":
			box.find(".dt-group-l i").css("color", val);
			break;
		case "iconfontsize":
			box.find(".dt-group-l i").css("font-size", val + "px");
			break;
		case "imgwidth":
			box.find(".dt-group-l ").css("width", val + "px");
			break;
		case "imgheight":
			box.find(".dt-group-l ").css("height", val + "px");
			break;
		case "imgbgcolor":
			box.find(".dt-group-l ").css("background-color", val);
			break;
		case "imgpadding":
			box.find(".dt-group-l").css("padding", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "imgmargin":
			box.find(".dt-group-l").css("padding", (val[0] ? val[0] : 0) + "px " + (val[1] ? val[1] : 0) + "px");
			break;
		case "imgborderradius":
			box.find(".dt-group-l ").css("border-radius", val + "px");
			break;
		case "imgbordersize":
			box.find(".dt-group-l ").css("border-width", val + "px");
			break;
		case "imgbordercolor":
			box.find(".dt-group-l ").css("border-color", val);
			break;
		case "imgborderstyle":
			box.find(".dt-group-l ").css("border-style", val);
			break;
		default:
			break;
	}
}


function getURLBase64(url) {
	return new Promise((resolve, reject) => {
		var xhr = new XMLHttpRequest()
		xhr.open('get', url, true)
		xhr.responseType = 'blob'
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response
				var fileReader = new FileReader()
				fileReader.onloadend = function (e) {
					var result = e.target.result
					resolve(result)
				}
				fileReader.readAsDataURL(blob)
			}
		}
		xhr.onerror = function () {
			reject()
		}
		xhr.send()
	})
}