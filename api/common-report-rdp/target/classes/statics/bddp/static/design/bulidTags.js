function createTagsBox(prop, res, oelem) {
    var box;
    switch (prop.type) {
        case "line":
            box = bulidLineChart(createChart(prop, oelem), res);
            break;
        case "bar":
            box = bulidBarChart(createChart(prop, oelem), res);
            break;
        case "pie":
            box = bulidPieChart(createChart(prop, oelem), res);
            break;
        case "radar":
            box = bulidRadarChart(createChart(prop, oelem), res);
            break;
        case "tree":
            box = bulidTreeChart(createChart(prop, oelem), res);
            break;
        case "treemap":
            box = bulidTreemapChart(createChart(prop, oelem), res);
            break;
        case "sunburst":
            box = bulidSunburstChart(createChart(prop, oelem), res);
            break;
        case "funnel":
            box = bulidFunnelChart(createChart(prop, oelem), res);
            break;
        case "gauge":
            box = bulidGaugeChart(createChart(prop, oelem), res);
            break;
        case "boxplot":
            box = bulidBoxplotChart(createChart(prop, oelem), res);
            break;
        case "heatmap":
            box = bulidHeatmapChart(createChart(prop, oelem), res);
            break;
        case "graph":
            box = bulidGraphChart(createChart(prop, oelem), res);
            break;
        case "parallel":
            box = bulidParallelChart(createChart(prop, oelem), res);
            break;
        case "sankey":
            box = bulidSankeyChart(createChart(prop, oelem), res);
            break;
        case "scatter":
            box = bulidScatterChart(createChart(prop, oelem), res);
            break;
        case "map":
            box = bulidMapChart(createChart(prop, oelem), res);
            break;
        case "k":
            box = bulidCandkestickChart(createChart(prop, oelem), res);
            break;
        case "text":
            box = bulidText(prop, res);
            break;
        case "rect":
            box = bulidRect(prop, res);
            break;
        case "circle":
            box = bulidCircle(prop, res);
            break;
        case "image":
            box = bulidImage(prop, res);
            break;
        case "table":
            box = bulidTable(prop, res);
            break;
        case "headtable":
            box = bulidHeadtable(prop, res);
            break;
        case "time":
            box = bulidTime(prop, res);
            break;
        case "triangle":
            box = bulidTriangle(prop, res);
            break;
        case "progress":
            box = bulidProgress(prop, res);
            break;
        case "iframe":
            box = bulidIframe(prop, res);
            break;
        case "swiper":
            box = bulidSwiper(prop, res);
            break;
        case "tabpage":
            box = bulidtabpage(prop, res);
            break;
        case "filter-input":
            box = bulidFilterInput(prop, res);
            break;
        case "filter-y":
            box = bulidFilterY(prop, res);
            break;
        case "filter-ym":
            box = bulidFilterYM(prop, res);
            break;
        case "filter-date":
            box = bulidFilterDate(prop, res);
            break;
        case "filter-y-rang":
            box = bulidFilterYRang(prop, res);
            break;
        case "filter-ym-rang":
            box = bulidFilterYMRang(prop, res);
            break;
        case "filter-date-rang":
            box = bulidFilterDateRang(prop, res);
            break;
        case "filter-select":
            box = bulidFilterSelect(prop, res);
            break;
        case "filter-searchbtn":
            box = bulidFilterSearchbtn(prop, res);
            break;
        case "detail-nolabel":
            box = bulidDetailNolabel(prop, res);
            break;
        case "detail":
            box = bulidDetail(prop, res);
            break;
        case "detail-icon":
            box = bulidDetailIcon(prop, res);
            break;
        case "detail-img":
            box = bulidDetailImg(prop, res);
            break;
        case "detail-h":
            box = bulidDetailH(prop, res);
            break;
        case "detail-time":
            box = bulidDetailTime(prop, res);
            break;
        default:
            break;
    }
    return box;
}

function setRectP(box, prop) {
    var rectP = prop.rectP;
    box.css({
        left: rectP.x,
        top: rectP.y,
        width: rectP.width + "px",
        height: rectP.height + "px",
        zIndex: rectP.zIndex,
        transform: "rotate(" + rectP.rotate + "deg)",
    });
}

function setTagsParts(box, parts, prop) {
    currBox = box;
    if (parts) {
        $.each(parts, function (k, val) {
            switch (k) {
                case "fontSize":
                    box.find(".tag-" + prop.type).css("font-size", val + "px");
                    break;
                case "fontWeight":
                    box.find(".tag-" + prop.type).css("font-weight", val);
                    break;
                case "textShadow":
                    box.find(".tag-" + prop.type).css("text-shadow", val);
                    break;
                case "color":
                    box.find(".tag-" + prop.type).css("color", val);
                    break;
                case "backgroundImage":
                    var strurl = val;
                    strurl = strurl.replace(/\\/g, "/");
                    box.find(".tag-" + prop.type).css({
                        "background-image": "url('" + strurl + "')",
                        "background-repeat": "no-repeat",
                        "background-size": "100% 100%",
                    });
                    break;
                case "backgroundColor":
                    box.find(".tag-" + prop.type).css({
                        "background-color": val,
                    });
                    break;
                case "text":
                    box.find(".tag-" + prop.type).html(val);
                    break;
                case "borderRadius":
                    box.find(".tag-" + prop.type).css({
                        "border-radius": val + "px",
                    });
                    break;
                case "iframeUrl":
                    box.find(".tag-" + prop.type).attr("src", val);
                    break;
                case "imgUrl":
                    box.find(".tag-" + prop.type).attr("src", val);
                    break;
                case "fontFamily":
                    box.find(".tag-" + prop.type).css("font-family", val);
                    break;
                case "borderWidth":
                    box.find(".tag-" + prop.type).css("border-width", val);
                    break;
                case "borderStyle":
                    box.find(".tag-" + prop.type).css("border-style", val);
                    break;
                case "borderColor":
                    box.find(".tag-" + prop.type).css("border-color", val);
                    break;
                case "textAlign":
                    box.find(".tag-" + prop.type).css("text-align", val);
                    break;
                case "lineHeight":
                    box.find(".tag-" + prop.type).css("line-height", val + "px");
                    break;
                default:
                    break;
            }
        });
    }
}

function bulidText(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><div class="tag-text" style="width: 100%;height: 100%;"></div></div>'
    );
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    if (res) {
        var data = prop.data;
        var textVal = getJsonValue(res, data.dimension[0].keyname);
        if (textVal.constructor == Array) {
            textVal = textVal[0];
        }
        if (prop.parts && prop.parts.format) {
            if (prop.parts.format == "number") {
                prop.parts.text = formatNum(textVal);
            } else if (prop.parts.format == "money") {
                prop.parts.text = formatMoney(textVal);
            } else {
                prop.parts.text = prop.parts.format.replace(/{val}/g, textVal);
            }
        } else if (prop.parts) {
            prop.parts.text = textVal;
        } else {
            prop.parts = {
                text: textVal,
            };
        }
    }
    setTagsParts(box, prop.parts, prop);
    return box;
}

function bulidRect(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><div class="tag-rect" style="width:100%;height:100%;background-color:#ffffff;"></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    setTagsParts(box, parts, prop);
    return box;
}

function bulidImage(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><img class="tag-image" width="100%" height="100%" src="static/img/default.jpg" /></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    setTagsParts(box, parts, prop);
    return box;
}

function bulidIframe(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><iframe class="tag-iframe" style="width:100%;height:100%;"></iframe></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    return box;
}

function bulidFilterInput(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-input" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    return box;
}

function bulidFilterY(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-y" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-y")[0],
        type: "year",
    });
    return box;
}

function bulidFilterYM(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-ym" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-ym")[0],
        type: "month",
    });
    return box;
}

function bulidFilterDate(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-date" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-date")[0],
    });
    return box;
}

function bulidFilterYRang(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-y-rang" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
        type: "year",
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-y-rang")[0],
        range: true,
    });
    return box;
}

function bulidFilterYMRang(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-ym-rang" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
        type: "month",
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-ym-rang")[0],
        range: true,
    });
    return box;
}

function bulidFilterDateRang(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><input type="text" class="tag-filter-date-rang" style="width: 100%;height: 100%;"/></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    laydate.render({
        elem: box.find(".tag-filter-date-rang")[0],
        range: true,
    });
    return box;
}

function bulidFilterSelect(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;">' +
        '<select name=""  class="tag-filter-select" style="width: 100%;height: 100%;">' +
        '<option value="">请选择</option>' +
        '</select>' +
        '</div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    setTagFilterSelect(prop,box.find(".tag-filter-select"));
    return box;
}

function bulidFilterSearchbtn(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><button class="tag-filter-searchbtn"  style="width: 100%;height: 100%;">查询</button></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    return box;
}

function bulidSwiper(prop, res) {
    var box = $(
        '<div class="box" style="width: 480px;height: 320px;"><div class="tag-swiper swiper-container"><div class="swiper-wrapper"></div><div class="swiper-pagination"></div></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    if (prop.slides.length > 0) {
        $.each(prop.slides, function (i, slide) {
            var slideDiv = $('<div class="swiper-slide"></div>');
            if (!slide.id) {
                slide.id = guid();
            }
            slideDiv.attr("id", "slide-" + slide.id);
            slideDiv.appendTo(box.find(".swiper-wrapper"));
            createTagsBox(slide, false, slideDiv);
        });
    }
    var mySwiper = new Swiper(box.find(".tag-swiper"), prop.swiper);
    box.data("prop").mySwiper = mySwiper;
    return box;
}

function bulidtabpage(prop, res) {
    var box = $(
        '<div class="box" style="width: 400px;height: 300px;"><div class="tabsbox tag-tabpage"><nav class="fisrstnav"><ul></ul><div class="tabadd"><span>+</span></div></nav><div class="tabscon"></div></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    if (prop.tabpage&&prop.tabpage.length > 0) {
        $.each(prop.tabpage, function (i, tabName) {
            var slideDiv = $("<section ></section>");
            slideDiv.appendTo(box.find(".tabscon"));
            if (prop.slides[i]) {
                let slide = prop.slides[i];
                slideDiv.attr("id", "slide-" + slide.id);
                if (!slide.id) {
                    slide.id = guid();
                }
                createTagsBox(slide, false, slideDiv);

            }

            box
                .find(".fisrstnav ul")
                .append(
                    '<li class="liactive"><span class="tab-name">' +
                    tabName +
                    '</span><span class="fa fa-times"></span></li>'
                );
        });
    }

    setTagsParts(box, parts, prop);
    var styles = prop.tabpageStyle||{};
    box.find(".tag-" + prop.type).rdpTabs(styles);
    return box;
}

function bulidCircle(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><div class="tag-circle" style="width:100%;height:100%;border-radius: 100%;background-color:#ffffff"></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    setTagsParts(box, parts, prop);
    return box;
}

function bulidTable(prop, res) {
    var box = $(
        '<div class="box" style="width: 480px;height: 320px;"><div class="tag-table kgo-scroll-sty " style="width:100%;height:100%;overflow:hidden;"><div style="width:100%;height:auto;"><div class="kgo-scroll-head"></div><div class="kgo-scroll-body"><ul class="kgo-scroll-body-ul"></ul></div></div></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    delete prop.id;
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    var table = box.find(".tag-" + prop.type);
    //table.setAttribute("grid-manager", "table" + prop.id);
    var tableHeight =
        prop.parts === undefined ? null : prop.parts.tableHeight || null;
    if (res) {
        var data = prop.data;
        var columnData = getJsonValue(res, data.dimension[0].keyname);
        var responseData = getJsonValue(res, data.series[0].keyname);
        var gmOptions = {
            ajax_data: responseData,
            ajax_type: "POST",
            supportCheckbox: false,
            supportAutoOrder: false,
            height: "100%",
            width: "100%",
            tableHeight: tableHeight,
            columnData: columnData,
        };
        try {
            table.initScroll(gmOptions);
        } catch (error) {
            console.log(error);
        }
        box.data("prop").gmOptions = gmOptions;
    } else {
        prop.gmOptions.tableHeight = tableHeight;
        table.initScroll(prop.gmOptions);
    }
    if (prop.effect && prop.effect.autoscroll) {
        table.autoScroll();
    }

    setTagsParts(box, parts, prop);
    setTableStyle(prop, box);
    return box;
}

function bulidHeadtable(prop, res) {
    var box = $(
        '<div class="box" style="width: 480px;height: 320px;"><table class="tag-headtable" style="width:100%;height:100%;margin:0; padding:0;"></table></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    delete prop.id;
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });

    var table = box.find(".tag-" + prop.type);
    if (res) {
        var data = prop.data;
        var columnData = getJsonValue(res, data.dimension[0].keyname);
        var responseData = getJsonValue(res, data.series[0].keyname);
        var htoptions = {
            laynum: 5,
            headJson: columnData,
            dataJson: responseData,
            needsort: true,
        };
        box.data("prop").htoptions = htoptions;

        $.fn.autoHeader.init($.extend({tableid: table}, htoptions));
    } else {
        $.fn.autoHeader.init($.extend({tableid: table}, prop.htoptions));
    }
    setTagsParts(box, parts, prop);
    return box;
}

function bulidTime(prop, res) {
    var box = $(
        '<div class="box" style="width: 100px;height: 40px;"><div class="clock tag-time"></div></div>'
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    box.find(".tag-" + prop.type).ledTime();
    setTagsParts(box, parts, prop);
    return box;
}

function bulidTriangle(prop, res) {
    var box = $(
        `<div class="box" style="width: 100px;height: 100px;">
      <div class="tag-triangle">
        <svg width="100%" height="100%" version="1.1" viewBox="0 0 300 300" xmlns="https://www.w3.org/2000/svg">
          <path d="M 0 240 L 300 240 L150 0 Z" fill="red" />
        </svg>
      </div>
    </div>`
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    setTagsParts(box, parts, prop);
    setTriangleStyle(prop, box);
    return box;
}

function bulidProgress(prop, res) {
    var box = $(
        `<div class="box" style="width: 400px;height: 30px;">
        <div class="tag-progress">
        <div class="tag-progress-bar" style="width:70%;">
        </div>
          <span class="tag-progress-percent">70%</span>
        </div>
    </div>`
    );
    var parts = prop.parts;
    setRectP(box, prop);
    box.appendTo("#content");
    box.data("prop", prop);
    box.initBox({
        tagType: prop.type,
    });
    if (res) {
        var data = prop.data;
        if (data.dimension && data.dimension.length > 0) {
            var textVal = getJsonValue(res, data.dimension[0].keyname);
            box.find(".tag-progress-bar").css("width",textVal+"%");
            box.find(".tag-progress-percent").text(textVal+"%");
        }
    }
    setTagsParts(box, parts, prop);
    setProgressStyle(prop, box);
    return box;
}

function createChart(prop, oelem) {
    if (prop.myChart) {
        prop.myChart.dispose();
    }
    if (typeof prop.options == "string") {
        prop.options = eval("(" + prop.options + ")");
    }
    if (prop.optionsText && typeof prop.optionsText == "string") {
        prop.options = eval("(" + prop.optionsText.replace(/\\"/g, '"') + ")");
    }
    var html = $(
        '<div class="box" style="height:100%;width:100%;"><div class="tag-charts" style="height:100%;width:100%"></div></div>'
    );
    html.data("prop", prop);
    if (prop.slide) {
        html.appendTo(oelem);
        html.find(".tag-charts").bind("click", function () {
            $(".box").removeClass("box-selected");
            html.addClass("box-selected");
            $("#rightnav").addClass("on");
            getProp($(this).parent(), true);
        });
    } else {
        html.appendTo("#content");
        setRectP(html, prop);
        html.initBox({
            tagType: prop.type,
        });
    }
    return html;
}

function bulidChartOther(elem) {
    var prop = elem.data("prop");
    var other = prop.other;
    var options = prop.options || {};

    if (other.axis) {
        options.xAxis = other.sYAxis;
        options.yAxis = other.sXAxis;
    } else {
        options.xAxis = other.sXAxis || options.xAxis;
        options.yAxis = other.sYAxis || options.yAxis;
    }
    setChartTheme(elem, prop);
}

//初始化折线图
function bulidLineChart(elem, res) {
    var prop = elem.data("prop");
    var subtype = prop.subtype;
    var data = prop.data;
    var other = prop.other;
    var options = prop.options || {};
    if (res) {
        options.legend = options.legend || {};
        options.legend.data = options.legend.data || [];
        options.series = options.series || [];
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);

        $.each(data.series, function (i, node) {
            var seriesVals = getJsonValue(res, node.keyname);

            var objectData =[];
            try {
                objectData = getJsonValue(res, node.keyname.substring(0,node.keyname.lastIndexOf(".")));
            }catch (e) {

            }
            var sortvalue = dataSort(dimensionVals, seriesVals, data.sort,objectData);
            dimensionVals = sortvalue.dimensionVals;
            seriesVals = sortvalue.seriesVals;
            var series = {
                data: seriesVals,
                type: prop.type,
                name: node.displayname,
            };
            if (subtype == 2) {
                series.areaStyle = {};
            } else if (subtype == 3) {
                series.areaStyle = {};
                series.stack = "堆叠标识";
            }
            if (options.series[i]) {
                options.series[i] = $.extend({}, options.series[i], series);
            } else {
                options.series.push(series);
            }
            if (options.legend.data[i]) {
                options.legend.data[i] = node.displayname;
            } else {
                options.legend.data.push(node.displayname);
            }
        });
        options.series = options.series.slice(0, data.series.length);
        options.legend.data = options.legend.data.slice(0, data.series.length);

        options.xAxis.data = dimensionVals;

        other.sXAxis = options.xAxis;
        other.sYAxis = options.yAxis;
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidBarChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;

    var options = prop.options || {};
    var other = prop.other || {};

    if (res) {
        options.legend = options.legend || {};
        options.legend.data = options.legend.data || [];
        options.series = options.series || [];
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        $.each(data.series, function (i, node) {
            var seriesVals = getJsonValue(res, node.keyname);

            var objectData =[];
            try {
                objectData = getJsonValue(res, node.keyname.substring(0,node.keyname.lastIndexOf(".")));
            }catch (e) {

            }
            var sortvalue = dataSort(dimensionVals, seriesVals, data.sort,objectData);
            dimensionVals = sortvalue.dimensionVals;
            seriesVals = sortvalue.seriesVals;

            var series = {
                data: seriesVals,
                type: prop.type,
                name: node.displayname,
            };

            if (options.series[i]) {
                options.series[i] = $.extend({}, options.series[i], series);
            } else {
                options.series.push(series);
            }
            if (options.legend.data[i]) {
                options.legend.data[i] = node.displayname;
            } else {
                options.legend.data.push(node.displayname);
            }
        });
        options.series = options.series.slice(0, data.series.length);
        options.legend.data = options.legend.data.slice(0, data.series.length);
        if (other.axis) {
            options.yAxis.data = dimensionVals;
            // options.xAxis={}
        } else {
            options.xAxis.data = dimensionVals;
            // options.yAxis={}
        }
        other.sXAxis = options.xAxis;
        other.sYAxis = options.yAxis;
    }

    setChartTheme(elem, prop);
    return elem;
}

function bulidPieChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        options.legend = options.legend || {};
        options.legend.data = options.legend.data || [];
        options.series = options.series || [];
        // debugger
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        $.each(data.series, function (j, serie) {
            var seriesVals = getJsonValue(res, serie.keyname);
            var objectData =[];
            try {
                objectData = getJsonValue(res, serie.keyname.substring(0,serie.keyname.lastIndexOf(".")));
            }catch (e) {

            }
            var seriesdata = [];
            for (var i = 0; i < dimensionVals.length; i++) {
                var obj = {
                    name: dimensionVals[i],
                    value: seriesVals[i],
                    data:objectData
                };
                if (options.legend.data[i]) {
                    options.legend.data[i] = dimensionVals[i];
                } else {
                    options.legend.data.push(dimensionVals[i]);
                }
                seriesdata.push(obj);
            }
            options.legend.data = options.legend.data.slice(0, dimensionVals.length);
            var series = {
                data: seriesdata,
                type: prop.type,
            };
            if (options.series[j]) {
                options.series[j] = $.extend({}, options.series[j], series);
            } else {
                options.series.push(series);
            }
        });
    }

    setChartTheme(elem, prop);
    return elem;
}

function bulidRadarChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        options.legend = options.legend || {};
        options.legend.data = options.legend.data || [];
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        var seriesVals = getJsonValue(res, data.series[0].keyname);

        options.series = options.series || [];
        options.radar = options.radar || {};
        options.radar.indicator = options.radar.indicator || [];

        var tempObj = {};
        var max = 0;

        $.each(seriesVals, function (i, node) {
            var series = {
                data: [],
                type: prop.type,
            };
            var obj = {
                name: dimensionVals[i],
                value: [],
            };
            if (options.legend.data[i]) {
                options.legend.data[i] = dimensionVals[i];
            } else {
                options.legend.data.push(dimensionVals[i]);
            }

            $.each(node, function (j, sub) {
                tempObj[sub.indicator] = sub.value;
                max = Number(max) > Number(sub.value) ? max : Number(sub.value);
                obj.value.push(sub.value);
            });

            series.data.push(obj);
            if (options.series[i]) {
                options.series[i] = $.extend({}, options.series[i], series);
            } else {
                options.series.push(series);
            }
        });
        options.series = options.series.slice(0, seriesVals.length);
        options.legend.data = options.legend.data.slice(0, dimensionVals.length);
        var count = 0;
        $.each(tempObj, function (k, v) {
            if (options.radar.indicator[count]) {
                options.radar.indicator[count] = $.extend(
                    {},
                    options.radar.indicator[count],
                    {
                        name: k,
                        max: max,
                    }
                );
            } else {
                options.radar.indicator.push({
                    name: k,
                    max: max,
                });
            }
            count++;
        });
        options.radar.indicator = options.radar.indicator.slice(0, count);
        //console.log(options);
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidTreeChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = [];
        var series = {
            data: seriesVals,
            type: prop.type,
        };
        options.series.push(series);
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidTreemapChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = [];
        var series = {
            data: seriesVals,
            type: prop.type,
        };
        options.series.push(series);
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidSunburstChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = [];
        var series = {
            data: seriesVals,
            type: prop.type,
        };
        options.series.push(series);
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidFunnelChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = [];
        $.each(seriesVals, function (i, node) {
            var series = node;
            series.type = prop.type;
            options.series.push(series);
        });
    }
    setChartTheme(elem, prop);
    return elem;
}

//仪表盘
function bulidGaugeChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = options.series || [];
        $.each(seriesVals, function (i, node) {
            var series = {};
            if (other.dataFrom == 4) {
                series = {
                    data: [
                        {
                            value: node,
                        },
                    ],
                };
            } else {
                series = $.extend({}, series, node);
            }
            series.type = prop.type;
            if (options.series[i]) {
                options.series[i] = $.extend({}, options.series[i], series);
            } else {
                options.series.push(series);
            }
        });
        options.series = options.series.slice(0, seriesVals.length);
    }
    setChartTheme(elem, prop);
    return elem;
}

//散点图
function bulidScatterChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
        options.series = options.series.slice(0, seriesVals.length);
    }
    setChartTheme(elem, prop);
    return elem;
}

//K线图
function bulidCandkestickChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
        options.series = options.series.slice(0, seriesVals.length);
        if (other.axis) {
            options.yAxis.data = dimensionVals;
        } else {
            options.xAxis.data = dimensionVals;
        }
        other.sXAxis = options.xAxis;
        other.sYAxis = options.yAxis;
    }
    setChartTheme(elem, prop);
    return elem;
}

//关系图
function bulidGraphChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
            links: dimensionVals,
            layout: "force",
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidSankeyChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
            links: dimensionVals,
            layout: "none",
            focusNodeAdjacency: "allEdges",
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
    }
    setChartTheme(elem, prop);
    return elem;
}

//平行坐标系
function bulidParallelChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
        options.parallelAxis = dimensionVals;
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidHeatmapChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        var classifyVals = getJsonValue(res, data.classify[0].keyname);
        options.series = options.series || [];
        var series = {
            data: classifyVals,
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
        var xAxis = {
            type: "category",
            data: dimensionVals,
            splitArea: {
                show: true,
            },
        };
        var yAxis = {
            type: "category",
            data: seriesVals,
            splitArea: {
                show: true,
            },
        };
        options.xAxis = $.extend({}, options.xAxis, xAxis);
        options.yAxis = $.extend({}, options.yAxis, yAxis);
    }
    setChartTheme(elem, prop);
    return elem;
}

//盒须图
function bulidBoxplotChart(elem, res) {
    var prop = elem.data("prop");
    var data = prop.data;
    var options = prop.options || {};
    var other = prop.other;

    if (res) {
        var seriesVals = getJsonValue(res, data.series[0].keyname);
        var dimensionVals = getJsonValue(res, data.dimension[0].keyname);
        options.series = options.series || [];
        var series = {
            data: seriesVals,
        };
        series.type = prop.type;
        if (options.series[0]) {
            options.series[0] = $.extend({}, options.series[0], series);
        } else {
            options.series.push(series);
        }
        options.xAxis.data = dimensionVals;
        options.yAxis.type = "value";
    }
    setChartTheme(elem, prop);
    return elem;
}

function bulidMapChart(elem, res) {
    var prop = elem.data("prop");
    var mappath = prop.other.mappath;
    if (mappath.indexOf("geographic") > -1) {
        mappath = mappath.substring(
            mappath.indexOf("geographic") + "geographic".length,
            mappath.length
        );
    }
    getAjaxMapDataSync(
        {
            mappath: mappath,
        },
        function (result) {
            if (result.code == 0) {
                data = result.res;
                var md5Value = hex_md5(JSON.stringify(data));
                var mapjson = data;
                echarts.registerMap(md5Value, mapjson);
                bulidMapData(elem, md5Value, mappath, res);
            }
        }
    );
    return elem;
}



function bulidMapData(elem, md5, mappath, res) {
    var prop = elem.data("prop");
    var options = $.extend({}, prop.options);
    options.geo.map = md5;
    var data = prop.data;
    // console.log(data);
    if (res) {
        options.series = options.series || [];
        options.legend = options.legend || {};
        options.legend.data = [];
        var count = 0;
        $.each(data.dimension, function (i, node) {
            var seriesVals = getJsonValue(res, node.keyname);
            var name = node.displayname;
            if ($.inArray(node.displayname, options.legend.data) > -1) {
                options.legend.data.push(node.displayname + count);
                name += count;
            } else {
                options.legend.data.push(node.displayname);
            }
            var series = {
                name: name,
                data: convertMapData(seriesVals,md5),
                type: "scatter",
                coordinateSystem: "geo",
            };
            if (options.series[count]) {
                options.series[count] = $.extend({}, options.series[count], series);
            } else {
                options.series.push(series);
            }
            count++;
        });
        $.each(data.series, function (i, node) {
            var seriesVals = getJsonValue(res, node.keyname);
            var name = node.displayname;
            if ($.inArray(node.displayname, options.legend.data) > -1) {
                options.legend.data.push(node.displayname + count);
                name += count;
            } else {
                options.legend.data.push(node.displayname);
            }
            var series = {
                name: name,
                data: convertMapData(seriesVals,md5),
                type: "effectScatter",
                coordinateSystem: "geo",
            };
            if (options.series[count]) {
                options.series[count] = $.extend({}, options.series[count], series);
            } else {
                options.series.push(series);
            }
            count++;
        });
        $.each(data.classify, function (i, node) {
            var seriesVals = getJsonValue(res, node.keyname);
            var name = node.displayname;
            if ($.inArray(node.displayname, options.legend.data) > -1) {
                options.legend.data.push(node.displayname + count);
                name += count;
            } else {
                options.legend.data.push(node.displayname);
            }
            var series = {
                name: name,
                data: convertMapData(seriesVals,md5),
                type: "map",
                geoIndex: 0,
            };
            if (options.series[count]) {
                options.series[count] = $.extend({}, options.series[count], series);
            } else {
                options.series.push(series);
            }
            count++;
        });
        options.series = options.series.slice(0, count);
        options.legend.data = options.legend.data.slice(0, count);
    }

    var dom = elem.find(".tag-charts")[0];
    var myChart = echarts.init(dom);
    myChart.setOption(options, true);
    prop.options = options;
    prop.myChart = myChart;
    prop.other.mappath = mappath;
    prop.optionsText = JSON.stringify(
        options,
        function (k, v) {
            if (typeof v == "function") {
                return funconvertStr(v);
            }
            return v;
        },
        4
    );
    prop.optionsText = prop.optionsText.replace(/"&/g, "").replace(/&"/g, "");
    elem.data("prop", prop);
    //	elem.sitemap();
    elem.initdata();
    $(".box").removeClass("box-selected");
    // elem.addClass("box-selected");
    currBox = elem;
}



function setChartTheme(elem, prop) {
    var options = prop.options;
    var other = prop.other;
    var data = prop.data;

    if (prop.myChart) {
        prop.myChart.dispose();
    }
    if (other && other.theme) {
        var file =
            "../../statics/bddp/static/charts-theme/" + other.theme + ".json";

        getJSONFileData(file, function (data) {
            var theme = data;
            echarts.registerTheme(other.theme, theme);
            var tempCahrt = echarts.init(
                elem.find(".tag-charts").empty()[0],
                other.theme
            );
            tempCahrt.setOption(options);
            prop.myChart = tempCahrt;

            prop.options = options;
            prop.optionsText = JSON.stringify(
                options,
                function (k, v) {
                    if (typeof v == "function") {
                        return funconvertStr(v);
                    }
                    return v;
                },
                4
            );
            prop.optionsText = prop.optionsText.replace(/"&/g, "").replace(/&"/g, "");
            elem.data("prop", prop);
        });
    } else {
        var tempCahrt = echarts.init(elem.find(".tag-charts")[0]);
        tempCahrt.setOption(options);
        prop.myChart = tempCahrt;
        prop.options = options;
        prop.optionsText = JSON.stringify(
            options,
            function (k, v) {
                if (typeof v == "function") {
                    return funconvertStr(v);
                }
                return v;
            },
            4
        );
        prop.optionsText = prop.optionsText.replace(/"&/g, "").replace(/&"/g, "");
        elem.data("prop", prop);
    }
    currBox = elem;
}

function getJsonValue(json, key) {
    var rjson;
    var keys = key.split(".");
    var obj = json[keys[0]];
    if (
        Object.prototype.toString.apply(obj) === "[object Array]" &&
        keys.length > 1
    ) {
        rjson = getJsonArray(obj, 0, keys);
    } else if (
        Object.prototype.toString.apply(obj) === "[object Object]" &&
        keys.length > 1
    ) {
        rjson = getJsonObject(obj, 0, keys);
    } else {
        rjson = obj;
    }
    return rjson;
}

function getJsonObject(json, index, keys) {
    if (index < keys.length) {
        var obj = json[keys[index + 1]];
        if (
            Object.prototype.toString.apply(obj) === "[object Array]" &&
            index + 1 < keys.length - 1
        ) {
            return getJsonArray(obj, index + 1, keys);
        } else if (
            Object.prototype.toString.apply(obj) === "[object Object]" &&
            index + 1 < keys.length - 1
        ) {
            return getJsonObject(obj, index + 1, keys);
        } else {
            return obj;
        }
    }
}

function getJsonArray(json, index, keys) {
    var rjson = [];
    $.each(json, function (i, node) {
        if (index < keys.length - 1) {
            var obj = node[keys[index + 1]];
            if (Object.prototype.toString.apply(obj) === "[object Array]") {
                rjson.push(getJsonArray(obj, index + 1, keys));
            } else if (Object.prototype.toString.apply(obj) === "[object Object]") {
                rjson.push(getJsonObject(obj, index + 1, keys));
            } else {
                rjson.push(obj);
            }
        } else {
            rjson.push(node);
        }
    });
    return rjson;
}

function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function guid() {
    return S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4();
}

function uniq(array) {
    var temp = [];
    //一个新的临时数组
    for (var i = 0; i < array.length; i++) {
        if (temp.indexOf(array[i]) == -1) {
            temp.push(array[i]);
        }
    }
    return temp;
}



function funconvertStr(v){
    return (
        "&" + v.toString().replace(/\s+/g, " ").replace(/\n/g, "").replace(/\\"/g, '"') + "&"
    )
}