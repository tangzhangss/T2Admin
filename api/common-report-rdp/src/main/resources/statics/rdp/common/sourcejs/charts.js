try{
    document.write("<script language=javascript src=../../statics/js/t2admin.js></script>");
}catch (e) {}
console.log("load:src/main/resources/static/rdp/common/sourcejs/charts.js");

var getAjaxJsonFile = function (e) {
    var t;
    return $.ajax({
        url: e, type: "GET", async: !1, dataType: "json", success: function (e) {
            t = e
        }, error: function () {
            console.log("文件加载失败！")
        }
    }), t
}, registerTheme = function (e) {
    var t = getAjaxJsonFile("../../statics/rdp/common/sourcejs/charts_theme/" + e + ".json");
    echarts.registerTheme(e, t)
};
$(function () {
    registerTheme("westeros"), registerTheme("dark"), registerTheme("halloween"), registerTheme("walden"), registerTheme("wonderland")
});
var delDataTypeData = function (e) {
    e.unbind(), e.bind("click", function () {
        var e = hot.Methods.getSelected(), t = hot.Methods.getCellMeta(e[0][0], e[0][1]).charts,
            a = $(this).parent().index();
        $(this).parent().remove();
        var s = $(this).parent().attr("data-type");
        t.data[s].splice(a, 1), hot.Methods.setCellMeta(e[0][0], e[0][1], "charts", t)
    })
}, confirmData = function (e) {
    e.unbind(), e.bind("click", function () {
        var e = hot.Methods.getSelected(), s = hot.Methods.getCellMeta(e[0][0], e[0][1]),
            n = hot.Methods.getCell(e[0][0], e[0][1]);
        $.ajax({
            url: "../../rdppage/getChartsData",
            type: "post",
            headers:{[TOKEN_TAG]:T2Admin_TOKEN},
            data: {uuid: uuid, data: JSON.stringify(s.charts.data), type: s.charts.type},
            success: function (e) {
                var t, a;
                e.data ? "line" == (a = (t = s.charts).type) ? createLineTag(n, t, e.data) : "bar" == a ? createBarTag(n, t, e.data) : "gauge" == a ? createGaugeTag(n, t, e.data) : "radar" == a ? createRadarTag(n, t, e.data) : "pie" == a ? createPieTag(n, t, e.data) : "barline" == a && createBarlineTag(n, t, e.data) : layer.msg("数据未获取到！")
            },
            error: function () {
                layer.msg("数据加载失败！")
            }
        })
    })
}, moveTarg = function (e) {
    var a = e.parents(".charts_setting")[0], t = e[0], s = 0, n = 0, i = 0, r = 0, d = !1;
    t.onmousedown = function (e) {
        s = e.clientX, n = e.clientY, i = a.offsetLeft, r = a.offsetTop, d = !0, a.style.cursor = "move"
    }, window.onmousemove = function (e) {
        var t;
        0 != d && (t = e.clientX, e = e.clientY - (n - r), a.style.left = t - (s - i) + "px", a.style.top = e + "px")
    }, e[0].onmouseup = function () {
        d = !1, a.style.cursor = "default"
    }
}, loadcharts = {
    beforeKeyDown: {
        del: function () {
            var e = hot.Methods.getSelected();
            delete hot.Methods.getCellMeta(e[0][0], e[0][1]).charts
        }
    }, chartsModelTool: function (e) {
        var a = getAjaxJsonFile("../../statics/rdp/common/sourcejs/charts_config/model.json");
        return {
            initChartsModel: function (e, t, n) {
                var i, r = t.id;
                r && a[r] && (t = a[r], i = e.find("div.addcharts"), $.each(t, function (e, t) {
                    !function (e) {
                        var t = e.type, a = e.option,
                            s = $('<div class="chars_dom"><div class="container" style="width: 300px;height: 200px;"></div><div class="charts_shade"></div></div>');
                        i.append(s);
                        e = echarts.init(s.find(".container")[0], "westeros");
                        (a = $.extend(!0, {}, a)) && "object" == typeof a && e.setOption(a), s.bind("click", function () {
                            n && n.call(this, r, {type: t, options: a})
                        })
                    }(t)
                }))
            }
        }
    }, initChartsAttribute: function (e, t) {
        function a(e) {
            var t = hot.Methods.getSelected(), d = hot.Methods.getCellMeta(t[0][0], t[0][1]).charts,
                a = $(".charts_data_dimension").find(".data_tag_input").html(""),
                s = $(".charts_data_series").find(".data_tag_input").html(""),
                n = $(".charts_data_classify").find(".data_tag_input").html(""),
                o = $(".charts_data_orderby").find(".data_tag_input").html("");
            d.data && $.each(d.data, function (i, e) {
                var r;
                i && e && ("dimension" == i ? r = a : "series" == i ? r = s : "classify" == i ? r = n : "orderby" == i && (r = o), r && $.each(e, function (e, t) {
                    var a, s, n;
                    a = r, s = i, n = t, (t = $("<span></span>")).attr("data-type", s), t.html(n.displayname + '<i class="fa fa-close"></i>'), a.append(t), loadcharts.editDisplayName(t, d, s)
                }))
            }), delDataTypeData($(".data_tag_input .fa-close")), confirmData($(".load_data_btn")), (e = e.find(".attr_context")).html(""), chartstool(e, {
                callback: function (e) {
                }
            })
        }

        var s, n;
        hot.Methods.getCellMeta(e, t).charts ? ($(".charts_setting").show(), a($(".charts_setting .setting_context .right_attr")), n = $(".tb-foot_left").css("right"), s = $(".tb-foot_left").width(), n = parseInt(n.split("px")[0]), $(".charts_setting").css({
            right: n + s + "px",
            left: "",
            top: ""
        }), moveTarg($(".setting_title"))) : $(".charts_setting").hide(), $(".charts_setting .left_title ul.title_menu li").bind("click", function () {
            $(this).siblings().removeClass("selected"), $(this).addClass("selected");
            var e = $(this).index();
            $(".charts_setting .right_attr ul").eq(e).show(), $(".charts_setting .right_attr ul").eq(e).siblings().hide()
        }), $(".charts_setting .setting_title .fa.btn_close").unbind().bind("click", function () {
            $(".charts_setting").hide()
        })
    }, editDisplayName: function (n, i, r) {
        n.bind("dblclick", function () {
            var s = $(n).html(), e = $('<input type="text">');
            $(n).html(e), e.bind("keyup", function (e) {
                var t = n.index(), a = $(this).val();
                13 == e.keyCode && (i.data = i.data || {}, i.data[r] = i.data[r] || [], null != a && "" != a && null != a ? (i.data[r][t].displayname = a, $(n).html(a + '<i class="fa fa-close"></i>')) : $(n).html(s))
            })
        })
    }, showCharts: function (e, t) {
        var a;
        t ? (a = t.option) && (t = "westeros", a.theme && (t = a.theme), a = a.options, $(e).html('<div class="charts-tag" style="height: 100%;"></div>'), e = $(e).find("div")[0], t = echarts.init(e, t), a && "object" == typeof a && (a = setOptionFunObj(a), t.setOption(a, !0))) : console.error("charts option is undefined")
    }, addSetColumn: function (e) {
        var t, a, s = hot.Methods.getSelected(), n = hot.Methods.getCellMeta(s[0][0], s[0][1]).charts;
        n.data || (n.data = {}), $(".charts_data_dimension .data_tag_input").is(":hover") ? (t = $('<span data-type="dimension">' + e.column + '<i class="fa fa-close"></i></span>'), $(".charts_data_dimension .data_tag_input").append(t), n.data.dimension || (n.data.dimension = []), a = {
            keyname: e.column,
            displayname: e.column
        }, n.data.dimension.push(a), loadcharts.editDisplayName(t, n, "dimension")) : $(".charts_data_series .data_tag_input").is(":hover") ? (t = $('<span data-type="series">' + e.column + '<i class="fa fa-close"></i></span>'), $(".charts_data_series .data_tag_input").append(t), n.data.series || (n.data.series = []), a = {
            keyname: e.column,
            displayname: e.column
        }, n.data.series.push(a), loadcharts.editDisplayName(t, n, "series")) : $(".charts_data_classify .data_tag_input").is(":hover") ? (t = $('<span data-type="classify">' + e.column + '<i class="fa fa-close"></i></span>'), $(".charts_data_classify .data_tag_input").append(t), n.data.classify || (n.data.classify = []), a = {
            keyname: e.column,
            displayname: e.column
        }, n.data.classify.push(a), loadcharts.editDisplayName(t, n, "classify")) : $(".charts_data_orderby .data_tag_input").is(":hover") && (t = $('<span data-type="orderby">' + e.column + '<i class="fa fa-close"></i></span>'), $(".charts_data_orderby .data_tag_input").append(t), n.data.orderby || (n.data.orderby = []), a = {
            keyname: e.column,
            displayname: e.column
        }, n.data.orderby.push(a), loadcharts.editDisplayName(t, n, "orderby")), $(".data_tag_input .fa-close").unbind(), $(".data_tag_input .fa-close").bind("click", function () {
            var e = $(this).parent().index();
            $(this).parent().remove();
            var t = $(this).parent().attr("data-type");
            n.data[t].splice(e, 1), hot.Methods.setCellMeta(s[0][0], s[0][1], "charts", n)
        }), hot.Methods.setCellMeta(s[0][0], s[0][1], "charts", n)
    }
}, getChartDataOpt = function (e) {
    var s = [];
    return $.each(e.series, function (e, t) {
        var a = {};
        a.keyname = t.keyname.split(".")[1], a.displayname = t.displayname, s.push(a)
    }), s
}, getArrayJson = function (e, a) {
    var s = [];
    return $.each(e, function (e, t) {
        s[e] = t[a]
    }), s
}, createLineTag = function (e, t, s) {
    var n = t.option.options, a = getArrayJson(s, t.data.dimension[0].keyname.split(".")[1]);
    return $.each(getChartDataOpt(t.data), function (e, t) {
        var a = {data: getArrayJson(s, t.keyname), type: "line", name: t.displayname};
        n.series[e] ? n.series[e] = $.extend({}, n.series[e], a) : n.series.push(a), n.legend && (n.legend = n.legend || {data: []}, n.legend.data[e] ? n.legend.data[e] = t.displayname : n.legend.data.push(t.displayname))
    }), n.xAxis = n.xAxis || {}, n.xAxis.data = a, n.series = n.series.slice(0, t.data.series.length), createChartsTag(e, t.option.options, t.option.theme), t
}, createBarTag = function (e, s, n) {
    var i = s.option.options, t = getArrayJson(n, s.data.dimension[0].keyname.split(".")[1]);
    return $.each(getChartDataOpt(s.data), function (e, t) {
        var a = {data: getArrayJson(n, t.keyname), type: "bar", name: t.displayname};
        i.series[e] ? i.series[e] = $.extend({}, i.series[e], a) : (2 == s.option.type && (a.stack = "堆叠标识"), i.series.push(a)), i.legend && (i.legend = i.legend || {data: []}, i.legend.data[e] ? i.legend.data[e] = t.displayname : i.legend.data.push(t.displayname))
    }), i.series = i.series.slice(0, s.data.series.length), 3 == s.option.type ? (i.yAxis = i.yAxis || {}, i.yAxis.data = t) : (i.xAxis = i.xAxis || {}, i.xAxis.data = t), createChartsTag(e, i, s.option.theme), s
}, createRadarTag = function (e, t, s) {
    var n = t.option.options || {};
    n.legend = n.legend || {}, n.legend.data = n.legend.data || [];
    var i = getArrayJson(s, t.data.dimension[0].keyname.split(".")[1]), a = t.data.series;
    n.series = n.series || [], n.radar = n.radar || {}, n.radar.indicator = n.radar.indicator || [];
    var r = 0, d = [];
    $.each(a, function (e, t) {
        var a = getArrayJson(s, t.keyname.split(".")[1]);
        $.each(a, function (e, t) {
            d[e] = d[e] || 0, d[e] = Number(r[e]) > Number(t) ? r[e] : Number(t), r = Number(r) > Number(t) ? r : Number(t)
        }), n.series[0] = n.series[0] || {data: [], type: "radar"};
        a = {name: t.keyname, value: a};
        n.series[0].data[e] ? n.series[0].data[e] = a : n.series[0].data.push(a), n.legend && (n.legend.data[e] ? n.legend.data[e] = i[e] : n.legend.data.push(i[e]))
    }), n.series[0].data = n.series[0].data.slice(0, a.length), n.legend.data && (n.legend.data = n.legend.data.slice(0, i.length));
    d.sort(function (e, t) {
        return e - t
    }), $.each(d, function (e, t) {
        n.radar.indicator[e] ? n.radar.indicator[e] = $.extend({}, n.radar.indicator[e], {
            name: i[e],
            max: r
        }) : n.radar.indicator.push({name: i[e], max: r})
    }), n.radar.indicator = n.radar.indicator.slice(0, r.length), createChartsTag(e, n, t.option.theme)
}, createGaugeTag = function (e, a, t) {
    var s = a.option.options || {}, t = getArrayJson(t, a.data.series[0].keyname.split(".")[1]);
    s.series = s.series || [], $.each(t, function (e, t) {
        0 < e || ((t = {data: [{value: t}]}).type = a.type, s.series[e] ? s.series[e] = $.extend({}, s.series[e], t) : s.series.push(t))
    }), s.series = s.series.slice(0, t.length), createChartsTag(e, s, a.option.theme)
}, createPieTag = function (e, t, a) {
    var s = t.option.options || {};
    s.legend = s.legend || {}, s.legend.data = s.legend.data || [], s.series = s.series || [];
    for (var n = getArrayJson(a, t.data.dimension[0].keyname.split(".")[1]), i = getArrayJson(a, t.data.series[0].keyname.split(".")[1]), r = [], d = 0; d < n.length; d++) {
        var o = {name: n[d], value: i[d]};
        s.legend.data[d] ? s.legend.data[d] = n[d] : s.legend.data.push(n[d]), r.push(o)
    }
    s.legend.data = s.legend.data.slice(0, n.length);
    a = {data: r, type: "pie"};
    s.series[0] ? s.series[0] = $.extend({}, s.series[0], a) : s.series.push(a), createChartsTag(e, s, t.option.theme)
}, createBarlineTag = function (e, n, i) {
    var r = n.option.options, d = n.data.series;
    return n.data.classify && 0 < n.data.classify.length ? ($.each(n.data.classify, function (e, t) {
        if (!d[e]) return !0;
        var a = t.keyname.split(".")[1], s = (t.displayname, getArrayJson(i[a], d[e].keyname.split(".")[1])),
            a = getArrayJson(i[a], n.data.dimension[e].keyname.split(".")[1]), s = {data: s, name: t.displayname};
        r.series[e] ? r.series[e] = $.extend({}, r.series[e], s) : (s.type = "bar", r.series.push(s));
        a = {boundaryGap: !0, type: "category", data: a};
        r.xAxis[e] ? r.xAxis[e] = $.extend({}, r.xAxis[e], a) : r.xAxis.push(a), r.legend && (r.legend = r.legend || {data: []}, r.legend.data[e] ? r.legend.data[e] = t.displayname : r.legend.data.push(t.displayname))
    }), r.series = r.series.slice(0, n.data.classify.length), createChartsTag(e, n.option.options, n.option.theme)) : console.log("无分类，无法加载数据"), n
}, setOptionFunObj = function (options) {
    return $.each(options, function (key, cOption) {
        "formatter" == key && cOption ? options[key] = eval("(" + cOption + ")") : "[object Array]" === Object.prototype.toString.apply(cOption) ? options[key] = setOptionFunArray(cOption) : "[object Object]" === Object.prototype.toString.apply(cOption) && (options[key] = setOptionFunObj(cOption))
    }), options
}, setOptionFunArray = function (a) {
    return $.each(a, function (e, t) {
        "[object Object]" === Object.prototype.toString.apply(t) && (a[e] = setOptionFunObj(t))
    }), a
}, createChartsTag = function (e, t, a) {
    a = a || "westeros", $(e).html('<div class="charts-tag" style="height: 100%;"></div>');
    e = $(e).find("div")[0], a = echarts.init(e, a);
    t && "object" == typeof t && (t = setOptionFunObj(t), a.setOption(t, !0))
};

function addcharts(a) {
    layer.open({
        type: 1,
        area: ["640px", "520px"],
        maxmin: !1,
        resize: !1,
        title: "选择图表",
        content: '<div id="chars" class="addcharts"></div>',
        success: function (e, n) {
            var t = loadcharts.chartsModelTool(), t = {
                tab: [{id: "pie", text: "饼状图", fun: t.initChartsModel}, {
                    id: "bar",
                    text: "柱状图",
                    fun: t.initChartsModel
                }, {id: "line", text: "折线图", fun: t.initChartsModel}, {
                    id: "gauge",
                    text: "仪表盘",
                    fun: t.initChartsModel
                }, {id: "radar", text: "雷达图", fun: t.initChartsModel}, {
                    id: "barline",
                    text: "柱状折线图",
                    fun: t.initChartsModel,
                    css: {width: "85px"}
                }], class: "addcharts", callback: function (e, t) {
                    var a, s = hot.Methods.getSelected();
                    s ? (a = hot.Methods.getCellMeta(s[0][0], s[0][1]), s = hot.Methods.getCell(s[0][0], s[0][1]), a.charts = {
                        type: e,
                        option: t
                    }, loadcharts.showCharts(s, a.charts), layer.close(n)) : layer.alert("请选择单元格")
                }, index: a
            };
            layerTab(e, t).loadtabs()
        },
        btn: ["保存", "取消"],
        yes: function (e, t) {
            layer.close(e)
        },
        btn2: function (e, t) {
        },
        cancel: function () {
        }
    })
}
