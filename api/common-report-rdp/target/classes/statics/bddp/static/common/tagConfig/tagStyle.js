function setTableStyle(prop, box) {
    var styles = prop.table;
    if (!styles) {
        return false;
    }
    $.each(styles, function (k, val) {
        switch (k) {
            case "titlefontsize":
                if (prop.type == "table") {
                    box.find(".kgo-scroll-head").css("font-size", val + "px");
                } else {
                    box.find("#autohead_thead").css("font-size", val + "px");
                }

                break;
            case "bodyfontsize":
                if (prop.type == "table") {
                    box.find(".baseStyle-data").css("font-size", val + "px");
                } else {
                    box.find("#autohead_tbody > tr >td").css("font-size", val + "px");
                }
                break;
            case "titlefontcolor":
                if (prop.type == "table") {
                    box.find(".baseStyle").css("color", val);
                } else {
                    box.find("#autohead_thead > tr >th").css("color", val);
                }
                break;
            case "bodyfontcolor":
                if (prop.type == "table") {
                    box.find(".baseStyle-data").css("color", val);
                } else {
                    box.find("#autohead_tbody > tr >td").css("color", val);
                }
                break;
            case "titlebgcolor":
                if (prop.type == "table") {
                    box.find(".kgo-scroll-head").css("background-color", val);
                } else {
                    box.find("#autohead_thead").css("background-color", val);
                }
                break;
            case "bodybgcolor":
                if (prop.type == "table") {
                    box.find(".kgo-scroll-body").css("background-color", val);
                } else {
                    box.find("#autohead_tbody").css("background-color", val);
                }
                break;
            case "bordercolor":
                if (prop.type == "table") {
                    box.find(".kgo-scroll-sty").css("border-color", val);
                    box.find(".kgo-scroll-body li").css("border-color", val);
                    box.find(".kgo-scroll-head").css("border-bottom-color", val);
                } else {
                    box
                        .find("#autohead_tbody > tr >td")
                        .css("border", "1px solid " + val);
                    box
                        .find("#autohead_thead > tr >th")
                        .css("border", "1px solid " + val);
                }
                break;
            default:
                break;
        }
    });
}

function setTriangleStyle(prop, box) {
    var styles = prop.triangle;
    if (!styles) {
        return false;
    }
    $.each(styles, function (k, val) {
        switch (k) {
            case "color":
                box.find("path").css("fill", val);
                break;
            default:
                break;
        }
    });
}

function setProgressStyle(prop, box) {
    var styles = prop.progress;
    if (!styles) {
        return false;
    }
    $.each(styles, function (k, val) {
        switch (k) {
            case "bgcolor":
                box.find(".tag-progress").css("background-color", val);
                break;
            case "foreColor":
                box.find(".tag-progress-bar").css("background-color", val);
                break;
            case "borderRadius":
                box.find(".tag-progress").css("border-radius", val + "px");
                box.find(".tag-progress-bar").css("border-radius", val + "px");
                break;
            case "textShow":
                if (val) {
                    box.find(".tag-progress-percent").show();
                } else {
                    box.find(".tag-progress-percent").hide();
                }
                break;
            default:
                break;
        }
    });
}

function setTabPageStyle(prop, box) {
    var styles = prop.tabpageStyle;
    if (!styles) {
        return false;
    }
    box.find(".tag-tabpage").rdpTabs(styles);
}

function setTagFilterSelect(prop, box) {
    var select = prop.select;
    if (!!select && select.opts) {
        box.empty();
        if (select.opts.indexOf("|")>-1){
            var opts = select.opts.split("|");
            for (let i = 0; i <opts.length; i++) {
                if (opts[i]&&opts[i].indexOf("-")>-1){
                    var arr = opts[i].split("-");
                    var opt = $('<option value="'+arr[1]+'">'+arr[0]+'</option>');
                    box.append(opt);
                }
            }
        }
        return false;
    }
}

//通过地图名称找到坐标
function convertMapData(data,mapName) {
    var geoCoordMap = {};
    var mapFeatures = echarts.getMap(mapName).geoJson.features;
    mapFeatures.forEach(function(v) {
        // 地区名称
        var name = v.properties.name;
        if (name){
            let tempname = name.replace(/省|市/g,"")
            // 地区经纬度
            geoCoordMap[tempname] = {
                center:v.properties.center,
                name:name
            };
        }


    });
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name.replace(/省|市/g,"")];
        if (geoCoord&&geoCoord.center) {
            var center  = geoCoord.center;
            if (!Array.isArray(data[i].value) ){
                var targetdata = $.extend({},data[i], {
                    name: geoCoord.name,
                    value: center.concat(data[i].value),
                });
                // console.log(targetdata);
                res.push(targetdata);
            }else{
                res.push(data[i]);
            }
        }else{
            res.push(data[i]);
        }
    }
    return res;
};