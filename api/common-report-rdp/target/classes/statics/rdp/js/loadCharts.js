try{
    document.write("<script language=javascript src=../../statics/js/t2admin.js></script>");
}catch (e) {}
console.log("load:src/main/resources/statics/rdp/js/locaCharts...")

var loadCharts = function (id, option) {
    var dom = $("#" + id).html('<div style="height: 100%;"></div>');
    option = eval("(" + option + ")"), option.theme || (option.theme = "westeros"), loadGridCharts(dom, option)
};
$(function () {
    registerTheme("westeros"), registerTheme("dark"), registerTheme("halloween"), registerTheme("walden"), registerTheme("wonderland")
});
var getAjaxJsonFile = function (e) {
    var a;
    return $.ajax({
        url: e, type: "GET", async: !1, dataType: "json", success: function (e) {
            a = e
        }, error: function () {
            console.log("文件加载失败！")
        }
    }), a
}, registerTheme = function (e) {
    var a = getAjaxJsonFile("../../statics/rdp/common/sourcejs/charts_theme/" + e + ".json");
    echarts.registerTheme(e, a)
}, loadGridCharts = function (n, s) {
    $.ajax({
        url: "../../rdppage/getChartsData",
        type: "post",
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        data: {uuid: uuid, data: JSON.stringify(s.data), type: s.type},
        success: function (e) {
            var a, t;
            e.data ? (a = s, t = e.data, "line" == (e = a.type) ? createLineTag(n, a, t) : "bar" == e ? createBarTag(n, a, t) : "gauge" == e ? createGaugeTag(n, a, t) : "radar" == e ? createRadarTag(n, a, t) : "pie" == e ? createPieTag(n, a, t) : "barline" == e && createBarlineTag(n, a, t)) : console.log("数据未获取到！")
        },
        error: function () {
            layer.msg("数据加载失败！")
        }
    })
}, getChartDataOpt = function (n) {
    var s = [];
    return $.each(n.series, function (e, a) {
        var t = {};
        t.keyname = a.keyname.split(".")[1], t.displayname = n.dimension[0].displayname.split(".")[1], s.push(t)
    }), s
}, getArrayJson = function (e, t) {
    var n = [];
    return $.each(e, function (e, a) {
        n[e] = a[t]
    }), n
}, createLineTag = function (e, a, n) {
    var s = a.option.options, t = getArrayJson(n, a.data.dimension[0].keyname.split(".")[1]);
    return $.each(getChartDataOpt(a.data), function (e, a) {
        var t = {data: getArrayJson(n, a.keyname), type: "line", name: a.displayname};
        s.series[e] ? s.series[e] = $.extend({}, s.series[e], t) : s.series.push(t), s.legend && (s.legend = {data: []}, s.legend.data[e] ? s.legend.data[e] = a.displayname : s.legend.data.push(a.displayname))
    }), s.xAxis = s.xAxis || {}, s.xAxis.data = t, s.series = s.series.slice(0, a.data.series.length), createChartsTag(e, a.option.options, a.option.theme), a
}, createBarTag = function (e, n, s) {
    var r = n.option.options, a = getArrayJson(s, n.data.dimension[0].keyname.split(".")[1]);
    return $.each(getChartDataOpt(n.data), function (e, a) {
        var t = {data: getArrayJson(s, a.keyname), type: "bar", name: a.displayname};
        r.series[e] ? r.series[e] = $.extend({}, r.series[e], t) : (2 == n.option.type && (t.stack = "堆叠标识"), r.series.push(t)), r.legend && (r.legend = {data: []}, r.legend.data[e] ? r.legend.data[e] = a.displayname : r.legend.data.push(a.displayname))
    }), r.series = r.series.slice(0, n.data.series.length), 3 == n.option.type ? (r.yAxis = r.yAxis || {}, r.yAxis.data = a) : (r.xAxis = r.xAxis || {}, r.xAxis.data = a), console.log(r), createChartsTag(e, r, n.option.theme), n
}, createRadarTag = function (e, a, n) {
    var s = a.option.options || {};
    s.legend = s.legend || {}, s.legend.data = s.legend.data || [];
    var r = getArrayJson(n, a.data.dimension[0].keyname.split(".")[1]), t = a.data.series;
    s.series = s.series || [], s.radar = s.radar || {}, s.radar.indicator = s.radar.indicator || [];
    var i = 0, o = [];
    $.each(t, function (e, a) {
        var t = getArrayJson(n, a.keyname.split(".")[1]);
        $.each(t, function (e, a) {
            o[e] = o[e] || 0, o[e] = Number(i[e]) > Number(a) ? i[e] : Number(a), i = Number(i) > Number(a) ? i : Number(a)
        }), s.series[0] = s.series[0] || {data: [], type: "radar"};
        t = {name: a.keyname, value: t};
        s.series[0].data[e] ? s.series[0].data[e] = t : s.series[0].data.push(t), s.legend && (s.legend.data[e] ? s.legend.data[e] = r[e] : s.legend.data.push(r[e]))
    }), s.series[0].data = s.series[0].data.slice(0, t.length), s.legend.data && (s.legend.data = s.legend.data.slice(0, r.length));
    o.sort(function (e, a) {
        return e - a
    }), $.each(o, function (e, a) {
        s.radar.indicator[e] ? s.radar.indicator[e] = $.extend({}, s.radar.indicator[e], {
            name: r[e],
            max: i
        }) : s.radar.indicator.push({name: r[e], max: i})
    }), s.radar.indicator = s.radar.indicator.slice(0, i.length), createChartsTag(e, s, a.option.theme)
}, createGaugeTag = function (e, t, a) {
    var n = t.option.options || {}, a = getArrayJson(a, t.data.series[0].keyname.split(".")[1]);
    n.series = n.series || [], $.each(a, function (e, a) {
        0 < e || ((a = {data: [{value: a}]}).type = t.type, n.series[e] ? n.series[e] = $.extend({}, n.series[e], a) : n.series.push(a))
    }), n.series = n.series.slice(0, a.length), createChartsTag(e, n, t.option.theme)
}, createPieTag = function (e, a, t) {
    var n = a.option.options || {};
    n.legend = n.legend || {}, n.legend.data = n.legend.data || [], n.series = n.series || [];
    for (var s = getArrayJson(t, a.data.dimension[0].keyname.split(".")[1]), r = getArrayJson(t, a.data.series[0].keyname.split(".")[1]), i = [], o = 0; o < s.length; o++) {
        var d = {name: s[o], value: r[o]};
        n.legend.data[o] ? n.legend.data[o] = s[o] : n.legend.data.push(s[o]), i.push(d)
    }
    n.legend.data = n.legend.data.slice(0, s.length);
    t = {data: i, type: "pie"};
    n.series[0] ? n.series[0] = $.extend({}, n.series[0], t) : n.series.push(t), createChartsTag(e, n, a.option.theme)
}, createBarlineTag = function (e, s, r) {
    var i = s.option.options, o = s.data.series;
    return s.data.classify && 0 < s.data.classify.length ? ($.each(s.data.classify, function (e, a) {
        if (!o[e]) return !0;
        var t = a.keyname.split(".")[1], n = (a.displayname, getArrayJson(r[t], o[e].keyname.split(".")[1])),
            t = getArrayJson(r[t], s.data.dimension[e].keyname.split(".")[1]), n = {data: n, name: a.displayname};
        i.series[e] ? i.series[e] = $.extend({}, i.series[e], n) : (n.type = "bar", i.series.push(n));
        t = {boundaryGap: !0, type: "category", data: t};
        i.xAxis[e] ? i.xAxis[e] = $.extend({}, i.xAxis[e], t) : i.xAxis.push(t), i.legend && (i.legend = {data: []}, i.legend.data[e] ? i.legend.data[e] = a.displayname : i.legend.data.push(a.displayname))
    }), i.series = i.series.slice(0, s.data.classify.length), createChartsTag(e, s.option.options, s.option.theme)) : console.log("无分类，无法加载数据"), s
}, htmlDecodeByRegExp = function (e) {
    return 0 == e.length ? "" : e.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/&#39;/g, "'").replace(/&quot;/g, '"')
}, setOptionFunObj = function (options) {
    return $.each(options, function (key, cOption) {
        "formatter" == key && cOption ? options[key] = eval("(" + htmlDecodeByRegExp(cOption) + ")") : "[object Array]" === Object.prototype.toString.apply(cOption) ? options[key] = setOptionFunArray(cOption) : "[object Object]" === Object.prototype.toString.apply(cOption) && (options[key] = setOptionFunObj(cOption))
    }), options
}, setOptionFunArray = function (t) {
    return $.each(t, function (e, a) {
        "[object Object]" === Object.prototype.toString.apply(a) && (t[e] = setOptionFunObj(a))
    }), t
}, createChartsTag = function (e, a, t) {
    t = t || "westeros", $(e).html('<div class="charts-tag" style="height: 100%;"></div>');
    e = $(e).find("div")[0], t = echarts.init(e, t);
    a && "object" == typeof a && (console.log(a), a = setOptionFunObj(a), t.setOption(a, !0))
};
