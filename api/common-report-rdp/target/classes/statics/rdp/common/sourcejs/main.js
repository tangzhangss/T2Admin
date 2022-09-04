try{
    document.write("<script language=javascript src=../../statics/js/t2admin.js></script>");
}catch (e) {}
console.log("load:src/main/resources/static/rdp/common/sourcejs/main.js");


var hot, selectedCell, tempStyle, tempTableData, uuid = "", tableInitFlag = !1, TableData = {
    mergeCells: [],
    colWidths: [],
    rowHeights: [],
    rowCategory: {},
    overlapping: [],
    toolbar: "top",
    page: 10,
    pageorder: 1,
    autoQuery: 1,
    customExport: 1,
    customPage: 1,
    customPagesize: 0,
    customQuery: 1,
    customQueryOpen: 1,
    customQueryPosition: "top",
    customToolbar: 1,
    maxR: 0,
    maxC: 0,
    skinType: 0,
    uuid: "",
    reportStyle: "D",
    reportDescription: "",
    reportVersion: "1.0",
    reportMemo: "",
    mainUuid: ""
};

function initRowType(e, t) {
    function o(e, t) {
        a.eq(e).find("th a.rowtype").remove(), a.eq(e).find("th").append('<a class="rowtype rowtype_' + t + '"></a>')
    }

    var a = $(".ht_clone_left .wtHolder table.htCore tbody tr");
    null != e && null != e ? o(e, t) : $.each(TableData.row, function (e, t) {
        o(e, t.rowCategory)
    })
}

function init() {
    $.fn.zTree.init($("#fillReports"), settingForFillReport, zNodesForFillReport), $.fn.zTree.init($("#dataSets"), settingForSets, zNodesForSets), $.fn.zTree.init($("#dataParms"), settingForParms, zNodesForParms);
    $.fn.zTree.init($("#dataExpertree"), dataExprSetting, dataExprZNodes);
    $("#czz input").click(function () {
        filld(" " + $(this).val() + " ")
    })
}

function initColWidthAndHeight() {
    for (var e = hot.Methods.countCols() - 1, t = [], o = 0; o <= e; o++) t.push(hot.Methods.getColWidth(o));
    TableData.colWidths = t;
    for (var a = hot.Methods.countRows() - 1, r = [], o = 0; o <= a; o++) r.push(hot.Methods.getRowHeight(o));
    TableData.rowHeights = r
}

document.onkeydown = function () {
    var e;
    1 == event.ctrlKey && 83 == event.keyCode ? event.preventDefault() : 1 == event.ctrlKey && 192 == event.keyCode && (e = htmlDocx.asBlob($(".wtSpreader").html()), saveAs(e, "test.docx"))
}, $(function () {
    (hot = new Hot).init(), initColWidthAndHeight(), initSkin(), $("#cellval").bind("keyup", function () {
        tb_cellval($(this).val())
    }), $("#cell-width").bind("input", function () {
        tb_width($(this).val())
    }), $("#cell-height").bind("input", function () {
        tb_height($(this).val())
    }), $("#bgColor").spectrum({
        showInput: !0,
        allowEmpty: !0,
        showAlpha: !0,
        showPalette: !0,
        togglePaletteOnly: !0,
        hideAfterPaletteSelect: !0,
        showSelectionPalette: !0,
        cancelText: "取消",
        chooseText: "确认",
        togglePaletteMoreText: "更多",
        togglePaletteLessText: "简略",
        palette: [["#000", "#444", "#666", "#999", "#ccc", "#eee", "#f3f3f3", "#fff"], ["#f00", "#f90", "#ff0", "#0f0", "#0ff", "#00f", "#90f", "#f0f"], ["#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d0e0e3", "#cfe2f3", "#d9d2e9", "#ead1dc"], ["#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#9fc5e8", "#b4a7d6", "#d5a6bd"], ["#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6fa8dc", "#8e7cc3", "#c27ba0"], ["#c00", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3d85c6", "#674ea7", "#a64d79"], ["#900", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#0b5394", "#351c75", "#741b47"], ["#600", "#783f04", "#7f6000", "#274e13", "#0c343d", "#073763", "#20124d", "#4c1130"]],
        change: function (e) {
            setBgColor(e ? e.toRgbString() : ""), selectedCell = []
        },
        show: function (e) {
            selectedCell = hot.Methods.getSelected()
        },
        move: function (e) {
        },
        choose: function (e) {
            setBgColor(e ? e.toRgbString() : ""), selectedCell = []
        }
    }), $("#fontColor").spectrum({
        showInput: !0,
        allowEmpty: !0,
        showAlpha: !1,
        showPalette: !0,
        togglePaletteOnly: !0,
        hideAfterPaletteSelect: !0,
        showSelectionPalette: !0,
        cancelText: "取消",
        chooseText: "确认",
        togglePaletteMoreText: "更多",
        togglePaletteLessText: "简略",
        palette: [["#000", "#444", "#666", "#999", "#ccc", "#eee", "#f3f3f3", "#fff"], ["#f00", "#f90", "#ff0", "#0f0", "#0ff", "#00f", "#90f", "#f0f"], ["#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d0e0e3", "#cfe2f3", "#d9d2e9", "#ead1dc"], ["#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#9fc5e8", "#b4a7d6", "#d5a6bd"], ["#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6fa8dc", "#8e7cc3", "#c27ba0"], ["#c00", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3d85c6", "#674ea7", "#a64d79"], ["#900", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#0b5394", "#351c75", "#741b47"], ["#600", "#783f04", "#7f6000", "#274e13", "#0c343d", "#073763", "#20124d", "#4c1130"]],
        change: function (e) {
            setFontColor(e ? e.toRgbString() : ""), selectedCell = []
        },
        show: function (e) {
            selectedCell = hot.Methods.getSelected()
        },
        move: function (e) {
        },
        choose: function (e) {
            setFontColor(e ? e.toRgbString() : ""), selectedCell = []
        }
    }), $(".rows-btn").bind("click", function () {
        $(".select-div").hide();
        var e = $(this).position().top, t = $(this).position().left + $(this).width();
        return $(".select-ctrl-rows").css({top: e + "px", left: t + "px"}), $(".select-ctrl-rows").show(), !1
    }), $(".select-ctrl-rows li").bind("click", function () {
        var e, t = hot.Methods.getSelected();
        t && ("insertRowAbove" == $(this).data("ctrl") ? hot.Methods.alert("insert_row", t[0][0]) : "insertRowBelow" == $(this).data("ctrl") ? hot.Methods.alert("insert_row", t[0][0] + 1) : "delRow" == $(this).data("ctrl") ? hot.Methods.alert("insert_row", t[0][0], t[0][2] - t[0][0] + 1) : -1 != $(this).data("ctrl").indexOf("insertRowBelow") && (e = $(this).data("ctrl").split("-")[1], console.log(e), hot.Methods.alert("insert_row", t[0][0] + e)))
    }), $(".cols-btn").bind("click", function () {
        $(".select-div").hide();
        var e = $(this).position().top, t = $(this).position().left + $(this).width();
        return $(".select-ctrl-cols").css({top: e + "px", left: t + "px"}), $(".select-ctrl-cols").show(), !1
    }), $(".select-ctrl-cols li").bind("click", function () {
        var e = hot.Methods.getSelected();
        e && ("insertColLeft" == $(this).data("ctrl") ? hot.Methods.alert("insert_col", e[0][1]) : "insertColRight" == $(this).data("ctrl") ? hot.Methods.alert("insert_col", e[0][1] + 1) : "delCol" == $(this).data("ctrl") && hot.Methods.alert("remove_col", e[0][1], e[0][3] - e[0][1] + 1))
    }), $(".select-ctrl-border li").bind("click", function () {
        setBorder($(this).data("ctrl"))
    }), $(".cells a.corner_mark").bind("click", function () {
        var r = hot.Methods.getSelected();
        null != r ? layer.open({
            type: 1,
            area: ["700px", "520px"],
            maxmin: !1,
            resize: !1,
            title: "设置单元格格式",
            content: $("#cell_setting")[0].outerHTML,
            success: function (e, t) {
                var o = getRange(r),
                    a = (r[0], {row: parseInt(o[2]) - parseInt(o[0]) + 1, col: parseInt(o[3]) - parseInt(o[1]) + 1});
                e.find("#cell_setting").show(), e.borderSettings(a)
            },
            btn: ["保存", "取消"],
            yes: function (e, t) {
                var a = t.find(".border_setting").data("border-data"), t = hot.Methods.getSelected();
                if (t) {
                    function r(e, t, o) {
                        return e + "px " + t + " " + o
                    }

                    function o(e, t, o) {
                        void 0 === o && (o = t);
                        t = t.replace("-", "_");
                        return e[t + "_width"] = a[o + "-width"], e[t + "_style"] = a[o + "-style"], e[t + "_color"] = a[o + "-color"], e[t + "_type"] = a[o + "-type"], e[t] = r(a[o + "-width"], a[o + "-style"], a[o + "-color"]), e
                    }

                    function l(e, t, o) {
                        void 0 === o && (o = t), $(e).css(t, r(a[o + "-width"], a[o + "-style"], a[o + "-color"]))
                    }

                    var n = getRange(t);
                    if (a["border-top-type"]) for (var s = n[0]; s <= n[0]; s++) for (var i = n[1]; i <= n[3]; i++) {
                        var c = hot.Methods.getCellMeta(s, i).style || {};
                        hot.Methods.setCellMeta(s, i, "style", o(c, "border-top"));
                        var d = hot.Methods.getCell(s, i);
                        l($(d), "border-top")
                    }
                    if (a["border-bottom-type"]) for (s = n[2]; s <= n[2]; s++) for (i = n[1]; i <= n[3]; i++) {
                        c = hot.Methods.getCellMeta(s, i).style || {};
                        hot.Methods.setCellMeta(s, i, "style", o(c, "border-bottom"));
                        d = hot.Methods.getCell(s, i);
                        l($(d), "border-bottom")
                    }
                    if (a["border-left-type"]) for (s = n[0]; s <= n[2]; s++) for (i = n[1]; i <= n[1]; i++) {
                        c = hot.Methods.getCellMeta(s, i).style || {};
                        hot.Methods.setCellMeta(s, i, "style", o(c, "border-left"));
                        d = hot.Methods.getCell(s, i);
                        l($(d), "border-left")
                    }
                    if (a["border-right-type"]) for (s = n[0]; s <= n[2]; s++) for (i = n[3]; i <= n[3]; i++) {
                        c = hot.Methods.getCellMeta(s, i).style || {};
                        hot.Methods.setCellMeta(s, i, "style", o(c, "border-right"));
                        d = hot.Methods.getCell(s, i);
                        l($(d), "border-right")
                    }
                    if (a["border-center-type"]) for (var h = "border-center", s = n[0]; s <= n[2]; s++) for (i = n[1]; i <= n[3]; i++) {
                        c = hot.Methods.getCellMeta(s, i).style || {}, d = hot.Methods.getCell(s, i);
                        s == n[0] && (hot.Methods.setCellMeta(s, i, "style", o(c, "border-bottom", h)), l($(d), "border-bottom", h)), s == n[2] && (hot.Methods.setCellMeta(s, i, "style", o(c, "border-top", h)), l($(d), "border-top", "border-center")), s != n[0] && s != n[2] && (c = o(c = o(c, "border-top", h), "border-bottom", h), hot.Methods.setCellMeta(s, i, "style", c), l($(d), "border-bottom", h), l($(d), "border-top", h))
                    }
                    if (a["border-middle-type"]) for (h = "border-middle", s = n[0]; s <= n[2]; s++) for (i = n[1]; i <= n[3]; i++) {
                        c = hot.Methods.getCellMeta(s, i).style || {}, d = hot.Methods.getCell(s, i);
                        i == n[1] && (hot.Methods.setCellMeta(s, i, "style", o(c, "border-right", h)), l($(d), "border-right", h)), i == n[3] && (hot.Methods.setCellMeta(s, i, "style", o(c, "border-left", h)), l($(d), "border-left", h)), i != n[1] && i != n[3] && (c = o(c = o(c, "border-left", h), "border-right", h), hot.Methods.setCellMeta(s, i, "style", c), l($(d), "border-left", h), l($(d), "border-right", h))
                    }
                }
                layer.close(e)
            },
            btn2: function (e, t) {
            },
            cancel: function () {
            }
        }) : layer.alert("请选择单元格")
    }), $(".toolbars .charts a.corner_mark,.toolbars .charts li").bind("click", function () {
        addcharts($(this).index())
    }), $(".pagesetting a.corner_mark").bind("click", function () {
        layer.open({
            type: 1,
            area: ["600px", "400px"],
            maxmin: !1,
            resize: !1,
            title: "页面扩展配置",
            content: $("#pages_setting")[0].outerHTML,
            success: function (e, t) {
                1 == TableData.customQuery ? $(e).find("#customQuery").attr("checked", !0) : $(e).find("#customQuery").attr("checked", !1), 1 == TableData.customQueryOpen ? $(e).find("#customQueryOpen").attr("checked", !0) : $(e).find("#customQueryOpen").attr("checked", !1), $(e).find("#customQueryPosition").val(TableData.customQueryPosition), 1 == TableData.autoQuery ? $(e).find("#autoQuery").attr("checked", !0) : $(e).find("#autoQuery").attr("checked", !1), 1 == TableData.customToolbar ? $(e).find("#customToolbar").attr("checked", !0) : $(e).find("#customToolbar").attr("checked", !1), 1 == TableData.customExport ? $(e).find("#customExport").attr("checked", !0) : $(e).find("#customExport").attr("checked", !1), 1 == TableData.customPage ? $(e).find("#customPage").attr("checked", !0) : $(e).find("#customPage").attr("checked", !1), $(e).find("#customPagesize").val(TableData.customPagesize), e.find("#pages_setting").show()
            },
            btn: ["保存", "取消"],
            yes: function (e, t) {
                $(t).find("#customQuery").prop("checked") ? TableData.customQuery = 1 : TableData.customQuery = 0, $(t).find("#customQueryOpen").prop("checked") ? TableData.customQueryOpen = 1 : TableData.customQueryOpen = 0, TableData.customQueryPosition = $(t).find("#customQueryPosition").val(), $(t).find("#autoQuery").prop("checked") ? TableData.autoQuery = 1 : TableData.autoQuery = 0, $(t).find("#customToolbar").prop("checked") ? TableData.customToolbar = 1 : TableData.customToolbar = 0, $(t).find("#customExport").prop("checked") ? TableData.customExport = 1 : TableData.customExport = 0, $(t).find("#customPage").prop("checked") ? TableData.customPage = 1 : TableData.customPage = 0, TableData.customPagesize = parseInt($(t).find("#customPagesize").val()), layer.close(e)
            },
            btn2: function (e, t) {
            },
            cancel: function () {
            }
        })
    }), $(document.body).bind("click", function (e) {
        $(".select-div").hide(), $(".showsql").remove(), 0 == $(e.target).parents(".dataExpr").length && $(".select-current").removeClass("select-current")
    }), $(".foot-switch").bind("click", function () {
        var e = $(".tb-foot").hasClass("tb-foot_left");
        $(".tb-foot").hasClass("on") ? ($(".tb-foot").removeClass("on"), e || $(".tb-center").removeClass("on")) : ($(".tb-foot").addClass("on"), e || $(".tb-center").addClass("on"))
    }), $(".box ul li").bind("click", function () {
        $(this).addClass("on").siblings().removeClass("on"), $(".box .ct").eq($(".box ul li").index(this)).addClass("on").siblings().removeClass("on"), $(".tb-foot").hasClass("on") || ($(".tb-foot").addClass("on"), $(".tb-center").addClass("on"))
    }), init(), (uuid = getQueryString("uuid")) && "" != uuid && "null" != uuid ? getTableDataByUUID(uuid) : cPush("init"), $("#fontsize").select2({tags: !0}), $("#fontsize").on("select2:select", function (e) {
        setFontSize(e.params.data.id)
    }), $("#tdchangeColor").spectrum({
        showInput: !0,
        allowEmpty: !0,
        showAlpha: !0,
        showPalette: !0,
        togglePaletteOnly: !0,
        hideAfterPaletteSelect: !0,
        showSelectionPalette: !0,
        cancelText: "取消",
        chooseText: "确认",
        togglePaletteMoreText: "更多",
        togglePaletteLessText: "简略",
        palette: [["#000", "#444", "#666", "#999", "#ccc", "#eee", "#f3f3f3", "#fff"], ["#f00", "#f90", "#ff0", "#0f0", "#0ff", "#00f", "#90f", "#f0f"], ["#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d0e0e3", "#cfe2f3", "#d9d2e9", "#ead1dc"], ["#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#9fc5e8", "#b4a7d6", "#d5a6bd"], ["#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6fa8dc", "#8e7cc3", "#c27ba0"], ["#c00", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3d85c6", "#674ea7", "#a64d79"], ["#900", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#0b5394", "#351c75", "#741b47"], ["#600", "#783f04", "#7f6000", "#274e13", "#0c343d", "#073763", "#20124d", "#4c1130"]],
        change: function (e) {
            $("#tdchangeColor").parents(".attrspan").find("input").val(e ? e.toRgbString() : ""), e ? ($("#tdchangeColor").css("background-color", e.toRgbString()), $("#tdchangeColor").css("background-image", "")) : ($("#tdchangeColor").css("background-color", ""), $("#tdchangeColor").css("background-image", "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAGUlEQVQYV2M4gwH+YwCGIasIUwhT25BVBADtzYNYrHvv4gAAAABJRU5ErkJggg==)"))
        },
        show: function (e) {
            var t = $(".layui-layer").css("z-index");
            $("#tdchangeColor").spectrum("container").css("z-index", t + 1)
        },
        move: function (e) {
        }
    }), $("#tdchangeBgColor").spectrum({
        showInput: !0,
        allowEmpty: !0,
        showAlpha: !0,
        showPalette: !0,
        togglePaletteOnly: !0,
        hideAfterPaletteSelect: !0,
        showSelectionPalette: !0,
        cancelText: "取消",
        chooseText: "确认",
        togglePaletteMoreText: "更多",
        togglePaletteLessText: "简略",
        palette: [["#000", "#444", "#666", "#999", "#ccc", "#eee", "#f3f3f3", "#fff"], ["#f00", "#f90", "#ff0", "#0f0", "#0ff", "#00f", "#90f", "#f0f"], ["#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d0e0e3", "#cfe2f3", "#d9d2e9", "#ead1dc"], ["#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#9fc5e8", "#b4a7d6", "#d5a6bd"], ["#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6fa8dc", "#8e7cc3", "#c27ba0"], ["#c00", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3d85c6", "#674ea7", "#a64d79"], ["#900", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#0b5394", "#351c75", "#741b47"], ["#600", "#783f04", "#7f6000", "#274e13", "#0c343d", "#073763", "#20124d", "#4c1130"]],
        change: function (e) {
            $("#tdchangeBgColor").parents(".attrspan").find("input").val(e ? e.toRgbaString() : ""), e ? ($("#tdchangeBgColor").css("background-color", e.toRgbaString()), $("#tdchangeBgColor").css("background-image", "")) : ($("#tdchangeBgColor").css("background-color", ""), $("#tdchangeBgColor").css("background-image", "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAIAAADZF8uwAAAAGUlEQVQYV2M4gwH+YwCGIasIUwhT25BVBADtzYNYrHvv4gAAAABJRU5ErkJggg==)"))
        },
        show: function (e) {
            var t = $(".layui-layer").css("z-index");
            $("#tdchangeBgColor").spectrum("container").css("z-index", t + 1)
        },
        move: function (e) {
        }
    }), $(".table-warning .dataExprAttrAdd").bind("click", function () {
        warningTree.addLevel_1("condition_tree")
    }), $(".table-warning .dataExprAttrSymbol").bind("click", function () {
        null == $.fn.zTree.getZTreeObj("condition_tree") ? warningTree.addLevel_1("condition_tree") : 0 < warningTree.selectedNode().length ? warningTree.add("condition_tree") : layer.msg("未选中的条件项！")
    }), $(".table-warning .dataExprAttrUpdate").bind("click", function () {
        null != $.fn.zTree.getZTreeObj("condition_tree") ? warningTree.update("condition_tree") : layer.msg("未发现可删除条件项！")
    }), $(".table-warning .dataExprAttrDel").bind("click", function () {
        null != $.fn.zTree.getZTreeObj("condition_tree") ? warningTree.del("condition_tree") : layer.msg("未发现可删除条件项！")
    }), loadToolsBar(), $("#reportTitle").bind("click", function () {
        $("#reportTitleInput").attr("type", "text"), $(this).hide()
    }), $("#reportTitleInput").bind("blur", function () {
        TableData.reportDescription = $(this).val(), $("#reportTitle").html($(this).val()), $("#reportTitle").show(), $("#reportTitleInput").attr("type", "hidden")
    })
}), window.addEventListener("load", function (e) {
    console.log("All resources finished loading!")
});
var tb_copy_data, setOptionFunToString = function (o) {
    return o && $.each(o, function (t, e) {
        if ("formatter" == t && e) try {
            o[t] = e.toString().replace(/\s+/g, " ").replace(/\n/g, "")
        } catch (e) {
            o[t] = ""
        } else "[object Array]" === Object.prototype.toString.apply(e) ? o[t] = setOptionFunArrayToString(e) : "[object Object]" === Object.prototype.toString.apply(e) && (o[t] = setOptionFunToString(e))
    }), o
}, setOptionFunArrayToString = function (o) {
    return $.each(o, function (e, t) {
        "[object Object]" === Object.prototype.toString.apply(t) && (o[e] = setOptionFunToString(t))
    }), o
};

function getTabelData(e) {
    e || hot.Methods.deselectCell();
    var t = 0, o = 0, a = 1, r = 1;
    0 < hot.Methods.countRows() && (a = hot.Methods.countRows() - 1), 0 < hot.Methods.countCols() && (r = hot.Methods.countCols() - 1);
    try {
        for (var l = 0; l <= a; l++) for (var n = 0; n <= r; n++) {
            var s = hot.Methods.getCellMeta(l, n);
            (s.fill || s.value || s.image || s.dic || s.overlapping || s.link || s.exptext) && (t = t < l ? l : t, o = o < n ? n : o)
        }
    } catch (e) {
        console.error(e)
    }
    TableData.mergeCells = getMergeCells(TableData);
    for (var i = TableData.mergeCells, l = 0; l < i.length; l++) t = (c = (g = i[l]).row + g.rowspan - 1) < t ? t : c, o = (d = g.col + g.colspan - 1) < o ? o : d;
    TableData.maxR = t, TableData.maxC = o, TableData.skinType = $("#skinType").val(), TableData.row = [];
    for (l = 0; l <= TableData.maxR; l++) {
        for (var c = {col: []}, n = 0; n <= TableData.maxC; n++) {
            var d, h = hot.Methods.getCellMeta(l, n), p = h.value;
            (d = {}).value = p, d.width = hot.Methods.getColWidth(n), d.colwidth = getTDWidth(l, n, i), d.height = hot.Methods.getRowHeight(l), d.col = h.col, d.row = h.row, d.exptext = h.exptext, d.ds = h.ds, d.frameid = h.frameid, d.link = h.link, d.columnType = h.columnType, d.columnName = h.columnName, d.dic = h.dic, d.image = h.image, d.bgimage = h.bgimage, d.overlapping = h.overlapping, d.warnings = h.warnings, d.barCode = h.barCode, d.fill = h.fill, d.charts = setOptionFunToString(h.charts), d.style = $.extend(!0, {font_family: "SimSun"}, h.style), d.style.font_family && "null" != d.style.font_family || (d.style.font_family = "SimSun"), d.style.font_size && "null" != d.style.font_size || (d.style.font_size = "12px"), d.className = h.className, c.col.push(d), TableData.colWidths[n] = hot.Methods.getColWidth(n), TableData.rowHeights[l] = hot.Methods.getRowHeight(l)
        }
        var u = hot.Methods.getCellMeta(l, 0);
        c.rowCategory = u.rowCategory || "reporthead", TableData.row.push(c)
    }
    for (l = 0; l < i.length; l++) {
        var g = i[l];
        TableData.row[g.row].rowspan = g.rowspan, TableData.row[g.row].col[g.col].rowspan = g.rowspan, TableData.row[g.row].col[g.col].colspan = g.colspan
    }
    var f = $.fn.zTree.getZTreeObj("fillReports"), b = f.transformTozTreeNodes(f.getNodes()),
        e = $.fn.zTree.getZTreeObj("dataSets"), f = e.transformTozTreeNodes(e.getNodes()),
        e = $.fn.zTree.getZTreeObj("dataParms"), e = e.transformTozTreeNodes(e.getNodes());
    return TableData.fillReports = getFillReportNodesVal(b), TableData.dataSets = getDataSetsNodesVal(f), TableData.dataParms = getDataParmsNodesVal(e), TableData.reportDescription = $("#reportTitleInput").val(), TableData.custom = panel.getPanelVal(), tempTableData = $.extend(!0, {}, TableData), TableData.queryArea = panel.getEncodeVal(), tempTableData
}
function tb_modif() {
    getTabelData(), uuid && "null" != uuid ? (tempTableData = $.extend(!0, {}, TableData), $.rdp.ajax({
        url: "../../rdp/saveReport",
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        data: {uuid: uuid, report: Base64Util.encode64(JSON.stringify(tempTableData))},
        type: "post",
        success: function (e) {
            layer.msg(e.msg)
        },
        error: function () {
            layer.msg("保存失败!")
        }
    })) : layer.prompt({title: "请输入报表名称"}, function (r, e, t) {
        null != r && "" != r && layer.prompt({value: r, title: "请输入简要的描述"}, function (e, t, o) {
            var a;
            null != e && "" != e && (a = guid(), uuid = a, TableData.uuid = a, TableData.mainUuid = a, TableData.reportDescription = r, TableData.reportMemo = e, TableData.reportVersion = "1.0", TableData.reportStyle = "D", TableData.fileName = a + ".xml", tempTableData = $.extend(!0, {}, TableData), $.rdp.ajax({
                url: "../../rdp/saveReport",
                headers:{[TOKEN_TAG]:T2Admin_TOKEN},
                data: {uuid: uuid, report: Base64Util.encode64(JSON.stringify(tempTableData))},
                type: "post",
                success: function (e) {
                    layer.msg(e.msg);
                    history.pushState({}, r, "../rdp/rdpDesign.html?uuid=" + uuid), $("#reportTitle").html(r), $("#reportTitleInput").val(r), panel.pboxInit(TableData.dataParms), panel.init(TableData.queryArea)
                }
            })), layer.close(t)
        }), layer.close(e)
    })
}

function tb_save() {
    getTabelData(), layer.prompt({title: "请输入报表名称"}, function (r, e, t) {
        null != r && "" != r && layer.prompt({value: r, title: "请输入简要的描述"}, function (e, t, o) {
            var a;
            null != e && "" != e && (a = guid(), uuid = a, TableData.uuid = a, TableData.mainUuid = a, TableData.reportDescription = r, TableData.reportMemo = e, TableData.reportVersion = "1.0", TableData.reportStyle = "D", TableData.fileName = a + ".xml", tempTableData = $.extend(!0, {}, TableData), $.rdp.ajax({
                url: "../../rdp/saveReport",
                headers:{[TOKEN_TAG]:T2Admin_TOKEN},
                data: {report: Base64Util.encode64(JSON.stringify(tempTableData)), uuid: uuid},
                type: "post",
                success: function (e) {
                    layer.msg(e.msg);
                    history.pushState({}, r, "../rdp/rdpDesign.html?uuid=" + uuid), $("#reportTitle").html(r), $("#reportTitleInput").val(r)
                }
            })), layer.close(t)
        }), layer.close(e)
    })
}

function tb_view() {
    var e = $("<form>");
    e.attr("id", "tempForm1"), e.attr("style", "display:none"), e.attr("method", "post"), e.attr("target", "preview"), e.attr("action", "../../rdppage/main/0?client_id="+ClientId);
    var t = $("<textarea name='reportJson'>" + Base64Util.encode64(JSON.stringify(getTabelData())) + "</textarea>");
    e.append(t), e.on("submit", function () {
        window.open("about:blank", "preview", "left=0,top=0,width=" + (screen.availWidth - 10) + ",height=" + (screen.availHeight - 50) + ",toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no")
    }), e.trigger("submit"), $("body").append(e), e.submit(), $("tempForm").remove()
}

function tb_upload() {
    layer.msg("导入"), $("#uploadCells").click()
}

function tb_paste() {
    var e = hot.Methods.getSelected();
    if (e) {
        var t = e[0];
        if (tb_copy_data) for (var o = t[0]; o <= t[2]; o++) for (var a = t[1]; a <= t[3]; a++) hot.Methods.setCellMeta(o, a, "style", tb_copy_data.style), hot.Methods.setDataAtCell(o, a, tb_copy_data.value);
        hot.Methods.render()
    }
}

function tb_copy() {
    var e = hot.Methods.getSelected();
    e && (e = e[0], e = hot.Methods.getCellMeta(e[0], e[1]), tb_copy_data = e)
}

function tb_cut() {
    var e, t = hot.Methods.getSelected();
    t && (e = t[0], t = hot.Methods.getCellMeta(e[0], e[1]), tb_copy_data = $.extend({}, t), hot.Methods.setCellMeta(e[0], e[1], "style", {}), hot.Methods.setDataAtCell(e[0], e[1], ""))
}

function tb_fontfamily() {
}

function tb_merge() {
    selectedCell = hot.Methods.getSelected(), hot.Methods.mergeSelection()
}

function tb_split() {
    selectedCell = hot.Methods.getSelected(), hot.Methods.unmergeSelection()
}

function tb_wrap() {
    var e = getRange(hot.Methods.getSelected());
    if (e) {
        hot.Methods.deselectCell();
        for (var t = e, o = t[0]; o <= t[2]; o++) for (var a = t[1]; a <= t[3]; a++) {
            var r = hot.Methods.getCellMeta(o, a).style || {}, l = hot.Methods.getCell(o, a);
            r.white_space && "normal" == r.white_space ? (delete r.white_space, $(l).css("white-space", ""), $(".toolbars .group .tag-btn.wrap-btn").removeClass("on")) : (r.white_space = "normal", $(l).css("white_space", "normal"), $(".toolbars .group .tag-btn.wrap-btn").addClass("on")), hot.Methods.setCellMeta(o, a, "style", r)
        }
        hot.Methods.selectCell(t[0], t[1], t[2], t[3])
    }
}

function tb_category(e) {
    var t = hot.Methods.getSelectedRange();
    if (t) for (var o = t[0], a = o.from, t = o.to, o = (a.row < t.row ? a : t).row, r = (a.row > t.row ? a : t).row, l = o; l <= r; l++) console.log(e.value), hot.Methods.setCellMeta(l, 0, "rowCategory", e.value), initRowType(l, e.value)
}

function tb_toolbarpos(e) {
    TableData.toolbar = e.value
}

function tb_frame() {
}

function tb_exp() {
}

function tb_width(e) {
    var t = hot.Methods.getSelected();
    t && (t = t[0], hot.Methods.disablePlugin(), TableData.colWidths[t[1]] = Number(e), hot.Methods.setCellMeta(t[0], t[1], "width", Number(e)), tb_updateSettings(), hot.Methods.enablePlugin())
}

function tb_height(e) {
    var t = hot.Methods.getSelected();
    t && (t = t[0], hot.Methods.disablePlugin(), TableData.rowHeights[t[0]] = Number(e), hot.Methods.setCellMeta(t[0], t[1], "height", Number(e)), tb_updateSettings(), hot.Methods.enablePlugin())
}

function tb_updateSettings() {
    var e = getMergeCells(TableData);
    TableData.mergeCells = [], hot.Methods.updateSettings({
        rowHeights: TableData.rowHeights,
        colWidths: TableData.colWidths,
        mergeCells: e
    }), hot.Methods.render()
}

function tb_orientation(e) {
    TableData.pageorder = $(e).val()
}

function tb_paging(e) {
    TableData.page = $(e).val()
}

function tb_cellval(e) {
    var t = hot.Methods.getSelected();
    t && hot.Methods.setDataAtCell(t[0][0], t[0][1], e)
}

function tb_parmdic() {
}

function getRange(e) {
    var t = hot.Methods.getSelected();
    if (0 < e.length && t) {
        var o = e[0], a = t[0];
        if (o[0] != a[0] || o[1] != a[1]) return null;
        var r = [], e = o[0] < o[2] ? o[0] : o[2], t = o[0] > o[2] ? o[0] : o[2], a = o[1] < o[3] ? o[1] : o[3],
            o = o[1] > o[3] ? o[1] : o[3];
        return r.push(e), r.push(a), r.push(t), r.push(o), r
    }
    return null
}

function setCellval(e) {
    $("#cellval").val(e)
}

function getBgColor(e) {
    $("#bgColor").spectrum("set", e)
}

function getFontColor(e) {
    $("#fontColor").spectrum("set", e)
}

function setBgColor(e) {
    var t = getRange(selectedCell);
    if (t) {
        for (var o = t[0] < t[2] ? t[0] : t[2], a = t[0] > t[2] ? t[0] : t[2], r = t[1] < t[3] ? t[1] : t[3], l = t[1] > t[3] ? t[1] : t[3], n = o; n <= a; n++) for (var s = r; s <= l; s++) {
            var i = hot.Methods.getCellMeta(n, s).style || {};
            i.background_color = e, hot.Methods.setCellMeta(n, s, "style", i);
            i = hot.Methods.getCell(n, s);
            $(i).css("background-color", e)
        }
        cPush("setBgColor")
    }
}

function setFontColor(e) {
    var t = getRange(selectedCell);
    if (t) {
        for (var o = t, a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l = hot.Methods.getCellMeta(a, r).style || {};
            l.color = e, hot.Methods.setCellMeta(a, r, "style", l);
            l = hot.Methods.getCell(a, r);
            $(l).css("color", e)
        }
        cPush("setFontColor")
    }
}

function hotbeforeRenderer(e, t, o, a, r, l) {
    var n = l.style || {};
    l.className = "", $.each(n, function (e, t) {
        switch (e = e.replace(/_/g, "-")) {
            case"vertical-align":
                "middle" == t ? l.className += " htMiddle" : "top" == t ? l.className += " htTop" : "bottom" == t && (l.className += " htBottom");
                break;
            case"text-align":
                "center" == t ? l.className += " htCenter" : "left" == t ? l.className += " htLeft" : "right" == t && (l.className += " htRight")
        }
    })
}

function serMaxRC(e, t) {
    TableData.maxR = e > TableData.maxR ? e : TableData.maxR, TableData.maxC = t > TableData.maxC ? t : TableData.maxC
}

function hotafterRenderer(o, a, r, e, t, l) {
    var n = l.style || {}, s = l.fill || {}, i = l.charts || {};
    $.each(n, function (e, t) {
        switch (serMaxRC(a, r), e = e.replace(/_/g, "-")) {
            case"vertical-align":
            case"text-align":
                break;
            case"font-style":
                "italic" == t && $(o).addClass("italic");
                break;
            case"font-weight":
                "700" == t && $(o).addClass("bold");
                break;
            case"text-decoration":
                -1 < $.inArray("underline", n.text_decoration) && $(o).addClass("underline"), -1 < $.inArray("strike", n.text_decoration) && $(o).addClass("strike"), -1 < $.inArray("underline", n.text_decoration) && -1 < $.inArray("strike", n.text_decoration) && $(o).addClass("strike-underline");
                break;
            default:
                $(o).css(e, t)
        }
    }), $.each(s, function (e, t) {
        "ctType" == e && "" != t && (serMaxRC(a, r), $(o).addClass("fillclass"))
    }), i && i.type && i.option && loadcharts.showCharts($(o), i);
    i = l.overlapping;
    i && i.base64 && (serMaxRC(a, r), o.style.backgroundImage = 'url("' + i.base64 + '")', o.style.backgroundRepeat = "no-repeat");
    l = l.bgimage;
    l && (serMaxRC(a, r), o.style.backgroundImage = 'url("' + l + '")', o.style.backgroundRepeat = "no-repeat")
}

function hotafterColumnResize(e, t, o) {
    console.log(TableData.overlapping), $.each(TableData.overlapping, function (o, e) {
        e && $.each(e, function (e, t) {
            t && createLine(null, o, e)
        })
    })
}

function hotafterRowResize(e, t, o) {
    o || (TableData.rowHeights[e] = Number(t), tb_updateSettings()), $.each(TableData.overlapping, function (o, e) {
        e && $.each(e, function (e, t) {
            t && createLine(null, o, e)
        })
    })
}

function hotafterSelection(e, t, o, a, r, l) {
    var n = hot.Methods.getCellMeta(e, t);
    n.exptext ? (setCellval(n.exptext), $("#exptext").val(n.exptext.replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&amp;/g, "&"))) : (setCellval(n.value), $("#exptext").val(""));
    var s = n.style || {};
    s.background_color || (s.background_color = "rgba(255,255,255,1)"), s.color || (s.color = "rgba(0,0,0,1)");
    var i = (n.className || "").split(" ");
    $(".toolbars .group .tag-btn").removeClass("on");
    for (var c = 0; c < i.length; c++) $(".btn-" + i[c]).addClass("on");
    $("#fontfamily").val(""), $("#fontsize").val("12px").trigger("change"), $.each(s, function (e, t) {
        switch (e = e.replace(/_/g, "-")) {
            case"vertical-align":
            case"text-align":
                break;
            case"font-family":
                $("#fontfamily").val(t);
                break;
            case"white-space":
                "normal" == t && $(".toolbars .group .tag-btn.wrap-btn").addClass("on");
                break;
            case"font-size":
                $("#fontsize").val(t).trigger("change");
                break;
            case"font-style":
                "italic" == t && $(".toolbars .group .tag-btn.btn-italic").addClass("on");
                break;
            case"font-weight":
                "700" == t && $(".toolbars .group .tag-btn.btn-bold").addClass("on");
                break;
            case"text-decoration":
                -1 < $.inArray("underline", s.text_decoration) && $(".toolbars .group .tag-btn.btn-underline").addClass("on"), -1 < $.inArray("strike", s.text_decoration) && $(".toolbars .group .tag-btn.btn-strike").addClass("on");
                break;
            case"background-color":
                getBgColor(t);
                break;
            case"color":
                getFontColor(t)
        }
    });
    var d = hot.Methods.getColWidth(t);
    $("#cell-width").val(d);
    d = hot.Methods.getRowHeight(e);
    $("#cell-height").val(d);
    hot.Methods.getCell(e, t, !0);
    d = hot.Methods.getCellMeta(e, 0);
    $("#category").val(d.rowCategory), $("#parmdic").val(n.dic), loadcharts.initChartsAttribute(e, t)
}

function contextMenuCallback(e, t) {
    switch (e) {
        case"alignment:left":
            setAlignmentH("htLeft");
            break;
        case"alignment:center":
            setAlignmentH("htCenter");
            break;
        case"alignment:right":
            setAlignmentH("htRight");
            break;
        case"alignment:top":
            setAlignmentV("htTop");
            break;
        case"alignment:middle":
            setAlignmentV("htMiddle");
            break;
        case"alignment:bottom":
            setAlignmentV("htBottom");
            break;
        case"hot:subreport":
            addSubReport();
            break;
        case"hot:removesubreport":
            removeReport();
            break;
        case"hot:link":
            addLink();
            break;
        case"hot:removelink":
            removeLink();
            break;
        case"hot:removeDataDic":
            removeDataDic();
            break;
        case"copyStyle":
            copyStyle();
            break;
        case"pasteStyle":
            pasteStyle();
            break;
        case"hot:overlapping":
            setOverlapping();
            break;
        case"hot:importimage":
            importImage();
            break;
        case"hot:clearimage":
            clearImage();
            break;
        case"hot:addImage":
            addImage();
            break;
        case"hot:addcharts":
            addcharts();
            break;
        case"hot:clearoverlapping":
            clearoverlapping()
    }
}

function dataType(e) {
    return null === e ? "Null" : void 0 === e ? "Undefined" : Object.prototype.toString.call(e).slice(8, -1)
}

function dealObjectValue(e) {
    var t, o = {};
    if (null == e || "" === e) return o;
    for (t in e) "Object" === dataType(e[t]) ? o[t] = dealObjectValue(e[t]) : null !== e[t] && void 0 !== e[t] && "" !== e[t] && (o[t] = e[t]);
    return o
}

function loadData(e) {
    if (!e) return console.info("数据为空"), !1;
    e = dealObjectValue(e), (TableData = $.extend(!0, TableData, e)).row = e.row, e.mergeCells && 0 != e.mergeCells.length ? TableData.mergeCells = e.mergeCells : TableData.mergeCells = [], $("#reportTitle").html(TableData.reportDescription), $("#reportTitleInput").val(TableData.reportDescription);
    for (var t = e.row, o = [], a = 0; a < t.length; a++) {
        o[a] || (o[a] = []);
        for (var r = 0; r < t[a].col.length; r++) o[a][r] = t[a].col[r].value
    }
    0 == o.length && (o[0] = [""]), hot.Methods.loadData(o);
    for (a = 0; a < t.length; a++) {
        for (r = 0; r < t[a].col.length; r++) {
            var l = $.extend(!0, {}, t[a].col[r]);
            hot.Methods.setCellMetaObject(a, r, l);
            l = l.image;
            l && cellCreateImage(l, a, r)
        }
        hot.Methods.setCellMeta(a, 0, "rowCategory", t[a].rowCategory)
    }
    hot.Methods.render(), tb_updateSettings(), $("#orientation").val(TableData.pageorder), $("#paging").val(TableData.page), $("#toolbarpos").val(TableData.toolbar), $("#skinType").val(TableData.skinType), e.fillReports && 0 < e.fillReports.length && $.fn.zTree.init($("#fillReports"), settingForFillReport, setFillReportNodesVal(e.fillReports)), e.dataSets && 0 < e.dataSets.length && $.fn.zTree.init($("#dataSets"), settingForSets, setDataSetsNodesVal(e.dataSets)), e.dataParms && 0 < e.dataParms.length && $.fn.zTree.init($("#dataParms"), settingForParms, setDataParmsNodesVal(e.dataParms)), initRowType(), panel.pboxInit(TableData.dataParms), panel.init(TableData.queryArea)
}

function loadCellData(e) {
    if (!e) return console.info("数据为空"), !1;
    TableData = $.extend(!0, TableData, e), $("#reportTitle").html(TableData.reportDescription), $("#reportTitleInput").val(TableData.reportDescription), hot.Methods.disablePlugin();
    for (var t = e.row, o = [], a = 0; a < t.length; a++) {
        o[a] || (o[a] = []);
        for (var r = 0; r < t[a].col.length; r++) o[a][r] = t[a].col[r].value
    }
    var l = getMergeCells(e);
    TableData.mergeCells = [], hot.Methods.loadData(o), hot.Methods.updateSettings({
        colWidths: e.colWidths,
        rowHeights: e.rowHeights,
        mergeCells: l
    });
    for (a = 0; a < t.length; a++) {
        for (r = 0; r < t[a].col.length; r++) {
            var n = $.extend(!0, {}, t[a].col[r]);
            hot.Methods.setCellMetaObject(a, r, n)
        }
        hot.Methods.setCellMeta(a, 0, "rowCategory", t[a].rowCategory)
    }
    hot.Methods.enablePlugin(), hot.Methods.render()
}

function mergeCells(e, t, o) {
    var a = TableData.mergeCells;
    if (t) a.push(o); else {
        for (var r = [], l = 0; l < a.length; l++) {
            var n = a[l];
            e.from.row == n.row && e.from.col == n.col && e.to.row == n.row + n.rowspan - 1 && e.to.col == n.col + n.colspan - 1 && r.push(l)
        }
        for (var s = r.length - 1; 0 <= s; s--) a.splice(r[s], 1)
    }
    cPush("mergeCells")
}

function getMergeCells(e) {
    var t = [], o = e.mergeCells;
    if (o) for (var a = 0; a < o.length; a++) 0 <= o[a].row && 0 <= o[a].col && (1 < o[a].colspan || 1 < o[a].rowspan) && t.push(o[a]);
    return t
}

function setFontFamily(e) {
    var t = e.value, e = hot.Methods.getSelected();
    if (e) {
        for (var o = e[0], a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l = hot.Methods.getCellMeta(a, r).style || {}, n = hot.Methods.getCell(a, r);
            l.font_family = t, $(n).css("font-family", t), hot.Methods.setCellMeta(a, r, "style", l)
        }
        cPush("setFontFamily")
    }
}

function setFontSize(e) {
    var t = e, e = hot.Methods.getSelected();
    if (e) {
        for (var o = e[0], a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l = hot.Methods.getCellMeta(a, r).style || {}, n = hot.Methods.getCell(a, r);
            l.font_size = t, $(n).css("font-size", t), hot.Methods.setCellMeta(a, r, "style", l)
        }
        cPush("setFontSize")
    }
}

function setFontStyle(e) {
    var t = hot.Methods.getSelected();
    if (t) {
        for (var o = t[0], a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l, n = hot.Methods.getCellMeta(a, r).style || {}, s = hot.Methods.getCell(a, r);
            "italic" == e ? n.font_style && "italic" == n.font_style ? (n.font_style = "normal", $(s).removeClass("italic"), $(".toolbars .group .tag-btn.btn-italic").removeClass("on")) : (n.font_style = "italic", $(s).addClass("italic"), $(".toolbars .group .tag-btn.btn-italic").addClass("on")) : "700" == e ? n.font_weight && "700" == n.font_weight ? (n.font_weight = "normal", $(s).removeClass("bold"), $(".toolbars .group .tag-btn.btn-bold").removeClass("on")) : (n.font_weight = "700", $(s).addClass("bold"), $(".toolbars .group .tag-btn.btn-bold").addClass("on")) : "underline" == e ? (n.text_decoration && -1 < $.inArray("underline", n.text_decoration) ? (l = $.inArray("underline", n.text_decoration), n.text_decoration.splice(l, 1), $(s).removeClass("underline"), $(".toolbars .group .tag-btn.btn-underline").removeClass("on")) : (n.text_decoration || (n.text_decoration = []), n.text_decoration.push("underline"), $(s).addClass("underline"), $(".toolbars .group .tag-btn.btn-underline").addClass("on")), -1 < $.inArray("underline", n.text_decoration) && -1 < $.inArray("strike", n.text_decoration) ? $(s).addClass("strike-underline") : $(s).removeClass("strike-underline")) : "strike" == e && (n.text_decoration && -1 < $.inArray("strike", n.text_decoration) ? (l = $.inArray("strike", n.text_decoration), n.text_decoration.splice(l, 1), $(s).removeClass("strike"), $(".toolbars .group .tag-btn.btn-strike").removeClass("on")) : (n.text_decoration || (n.text_decoration = []), n.text_decoration.push("strike"), $(s).addClass("strike"), $(".toolbars .group .tag-btn.btn-strike").addClass("on")), -1 < $.inArray("underline", n.text_decoration) && -1 < $.inArray("strike", n.text_decoration) ? $(s).addClass("strike-underline") : $(s).removeClass("strike-underline")), hot.Methods.setCellMeta(a, r, "style", n)
        }
        cPush("setFontStyle")
    }
}

function setAlignmentV(e) {
    $(".btn-av").removeClass("on"), $(".btn-" + e).addClass("on");
    var t = hot.Methods.getSelected();
    if (t) {
        for (var o = getRange(t), a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l = hot.Methods.getCellMeta(a, r), n = l.className || "", s = hot.Methods.getCell(a, r),
                i = l.style || {};
            switch ($(s).removeClass("htTop").removeClass("htMiddle").removeClass("htBottom"), n = n.replace(/htTop|htMiddle|htBottom/, ""), e) {
                case"htTop":
                    n += " htTop", $(s).addClass("htTop"), i.vertical_align = "top";
                    break;
                case"htMiddle":
                    n += " htMiddle", $(s).addClass("htMiddle"), i.vertical_align = "middle";
                    break;
                case"htBottom":
                    n += " htBottom", $(s).addClass("htBottom"), i.vertical_align = "bottom"
            }
            hot.Methods.setCellMeta(a, r, "className", n), hot.Methods.setCellMeta(a, r, "style", i)
        }
        cPush("setAlignmentV")
    }
}

function setAlignmentH(e) {
    $(".btn-ah").removeClass("on"), $(".btn-" + e).addClass("on");
    var t = hot.Methods.getSelected();
    if (t) {
        for (var o = getRange(t), a = o[0]; a <= o[2]; a++) for (var r = o[1]; r <= o[3]; r++) {
            var l = hot.Methods.getCellMeta(a, r), n = l.className || "", s = l.style || {},
                i = hot.Methods.getCell(a, r);
            switch ($(i).removeClass("htLeft").removeClass("htCenter").removeClass("htRight"), n = n.replace(/htLeft|htCenter|htRight/, ""), e) {
                case"htLeft":
                    n += " htLeft", $(i).addClass("htLeft"), s.text_align = "left";
                    break;
                case"htCenter":
                    n += " htCenter", $(i).addClass("htCenter"), s.text_align = "center";
                    break;
                case"htRight":
                    n += " htRight", $(i).addClass("htRight"), s.text_align = "right"
            }
            hot.Methods.setCellMeta(a, r, "className", n), hot.Methods.setCellMeta(a, r, "style", s)
        }
        cPush("setAlignmentH")
    }
}

function getDomAllVals(e) {
    for (var t = e.getElementsByTagName("INPUT"), o = e.getElementsByTagName("TEXTAREA"), a = e.getElementsByTagName("SELECT"), r = {}, l = 0; l < t.length; l++) t[l].name && ("checkbox" == t[l].type ? r[t[l].name] = t[l].checked : r[t[l].name] = t[l].value);
    for (l = 0; l < o.length; l++) o[l].name && (r[o[l].name] = o[l].value);
    for (var n, l = 0; l < a.length; l++) a[l].name && (r[a[l].name] = a[l].value);
    return 0 < $("#columu_cell tr").length && (n = [], $("#columu_cell tr").each(function (e, t) {
        var o = {};
        o.name = $(this).find('td[name="column"]').text() + "<--" + $(this).find('td[name="cell"]').text(), o.primary = $(this).find('input[name="primary"]').is(":checked") ? "1" : "0", o.foreign = $(this).find('input[name="foreign"]').is(":checked") ? "1" : "0", o.column = $(this).find('td[name="column"]').text(), o.cell = $(this).find('td[name="cell"]').text(), o.remark = $(this).find('td[name="remark"]').text(), n.push(o)
    }), r.fields = n), r
}

function setDomAllVals(o, e) {
    for (var t = o.getElementsByTagName("INPUT"), a = o.getElementsByTagName("TEXTAREA"), r = o.getElementsByTagName("SELECT"), l = 0; l < t.length; l++) t[l].name && ("checkbox" == t[l].type ? t[l].checked = !1 : t[l].value = "");
    for (l = 0; l < a.length; l++) a[l].name && (a[l].value = "");
    for (l = 0; l < r.length; l++) r[l].name && (r[l].value = "");
    e && ($.each(e, function (e, t) {
        "checkbox" == $(o).find("[name=" + e + "]").attr("type") ? $(o).find("[name=" + e + "]")[0].checked = t : ($(o).find("[name=" + e + "]").val(t), "dataSourceName" == e || "tbName" == e || "btnType" == e ? $(o).find("[name=" + e + "]").val(t).change() : $(o).find("[name=" + e + "]").val(t))
    }), e.fields && ($.each(e.fields, function (e, t) {
        $("#columu_cell").append('<tr name="' + t.column + '" align="center"><td><input type="checkbox" name="primary"' + ("1" == t.primary ? ' checked="checked"' : "") + '></td><td><input type="checkbox" name="foreign"' + ("1" == t.foreign ? ' checked="checked"' : "") + '></td><td name="column">' + t.column + '</td><td name="cell">' + t.cell + '</td><td name="remark">' + t.remark + "</td></tr>")
    }), bindFillReport()))
}

function setBorder(e) {
    var t = hot.Methods.getSelected();
    if (t) {
        var o = getRange(t);
        switch (e) {
            case"borderLeft":
                for (var a = o[0]; a <= o[2]; a++) {
                    (n = hot.Methods.getCellMeta(a, o[1]).style || {}).border_left = "1px solid rgb(0,0,0)", n.border_left_type = "SOLID", n.border_left_color = "rgb(0,0,0)", hot.Methods.setCellMeta(a, o[1], "style", n);
                    var r = hot.Methods.getCell(a, o[1]);
                    $(r).css("border-left", "1px solid rgb(0,0,0)")
                }
                break;
            case"borderRight":
                for (a = o[0]; a <= o[2]; a++) {
                    (n = hot.Methods.getCellMeta(a, o[3]).style || {}).border_right = "1px solid rgb(0,0,0)", n.border_right_type = "SOLID", n.border_right_color = "rgb(0,0,0)", hot.Methods.setCellMeta(a, o[3], "style", n);
                    r = hot.Methods.getCell(a, o[3]);
                    $(r).css("border-right", "1px solid rgb(0,0,0)")
                }
                break;
            case"borderTop":
                for (a = o[1]; a <= o[3]; a++) {
                    (n = hot.Methods.getCellMeta(o[0], a).style || {}).border_top = "1px solid rgb(0,0,0)", n.border_top_type = "SOLID", n.border_top_color = "rgb(0,0,0)", hot.Methods.setCellMeta(o[0], a, "style", n);
                    r = hot.Methods.getCell(o[0], a);
                    $(r).css("border-top", "1px solid rgb(0,0,0)")
                }
                break;
            case"borderBottom":
                for (a = o[1]; a <= o[3]; a++) {
                    (n = hot.Methods.getCellMeta(o[2], a).style || {}).border_bottom = "1px solid rgb(0,0,0)", n.border_bottom_type = "SOLID", n.border_bottom_color = "rgb(0,0,0)", hot.Methods.setCellMeta(o[2], a, "style", n);
                    r = hot.Methods.getCell(o[2], a);
                    $(r).css("border-bottom", "1px solid rgb(0,0,0)")
                }
                break;
            case"borderOut":
                setBorder("borderLeft"), setBorder("borderRight"), setBorder("borderTop"), setBorder("borderBottom");
                break;
            case"borderIn":
                setBorder("borderAll");
                break;
            case"borderAll":
                for (a = o[0]; a <= o[2]; a++) for (var l = o[1]; l <= o[3]; l++) {
                    (n = hot.Methods.getCellMeta(a, l).style || {}).border_top = "1px solid rgb(0,0,0)", n.border_top_type = "SOLID", n.border_top_color = "rgb(0,0,0)", n.border_bottom = "1px solid rgb(0,0,0)", n.border_bottom_type = "SOLID", n.border_bottom_color = "rgb(0,0,0)", n.border_left = "1px solid rgb(0,0,0)", n.border_left_type = "SOLID", n.border_left_color = "rgb(0,0,0)", n.border_right = "1px solid rgb(0,0,0)", n.border_right_type = "SOLID", n.border_right_color = "rgb(0,0,0)", hot.Methods.setCellMeta(a, l, "style", n);
                    r = hot.Methods.getCell(a, l);
                    $(r).css({
                        "border-top": "1px solid rgb(0,0,0)",
                        "border-bottom": "1px solid rgb(0,0,0)",
                        "border-left": "1px solid rgb(0,0,0)",
                        "border-right": "1px solid rgb(0,0,0)"
                    })
                }
                break;
            case"borderNode":
                for (a = o[0]; a <= o[2]; a++) for (var n, l = o[1]; l <= o[3]; l++) {
                    delete (n = hot.Methods.getCellMeta(a, l).style || {}).border_top, delete n.border_top_type, delete n.border_top_color, delete n.border_bottom, delete n.border_bottom_type, delete n.border_bottom_color, delete n.border_left, delete n.border_left_type, delete n.border_left_color, delete n.border_right, delete n.border_right_type, delete n.border_right_color, hot.Methods.setCellMeta(a, l, "style", n);
                    r = hot.Methods.getCell(a, l);
                    $(r).css({"border-top": "", "border-bottom": "", "border-left": "", "border-right": ""})
                }
        }
    }
}

function copyStyle() {
    var e = hot.Methods.getSelected();
    e && (e = e[0], e = hot.Methods.getCellMeta(e[0], e[1]), tempStyle = e.style)
}

function pasteStyle() {
    var e = hot.Methods.getSelected();
    if (e) for (var t = e[0], o = t[0]; o <= t[2]; o++) for (var a = t[1]; a <= t[3]; a++) hot.Methods.setCellMeta(o, a, "style", tempStyle);
    hot.Methods.render()
}

function getFillReportNodesVal(e) {
    var o = [], e = e[0].children;
    return e && $.each(e, function (e, t) {
        var a = {};
        a.name = t.name, a.btnType = t.btnType, a.actionurl = t.actionurl, a.dataSourceName = t.dataSourceName, a.dbMode = t.dbMode, a.tbName = t.tbName, a.fields = [], t.fields && "actionurl" != t.btnType && $.each(t.fields, function (e, t) {
            var o = {};
            o.primary = t.primary, o.foreign = t.foreign, o.column = t.column, o.cell = t.cell, o.remark = t.remark, a.fields.push(o)
        }), o.push(a)
    }), o
}

function setFillReportNodesVal(e) {
    var o = {name: "填报配置列表", open: !0, children: []};
    return $.each(e, function (e, t) {
        var a = {children: []};
        a.name = t.name, a.btnType = t.btnType, a.actionurl = t.actionurl, a.dataSourceName = t.dataSourceName, a.dbMode = t.dbMode, a.tbName = t.tbName, a.fields = [], t.fields && "actionurl" != t.btnType && $.each(t.fields, function (e, t) {
            var o = {};
            o.name = t.column + "<--" + t.cell, o.primary = t.primary, o.foreign = t.foreign, o.column = t.column, o.cell = t.cell, o.remark = t.remark, a.fields.push(o), a.children.push(o)
        }), o.children.push(a)
    }), o
}

function getDataSetsNodesVal(e) {
    var o = [], e = e[0].children;
    return e && $.each(e, function (e, t) {
        var a = {};
        a.dic = t.dic, a.isvalid = t.isvalid, a.dataSourceName = t.dataSourceName, a.dataSetName = t.dataSetName, a.dataSetType = t.dataSetType, a.commandText = t.commandText, a.keyName = t.keyName, a.optCode = t.optCode, a.optName = t.optName, a.path = t.path, a.method = t.method, a.cate = t.cate, a.node = t.node, a.hostName = t.hostName, a.pageType = t.pageType, a.pageParam = t.pageParam, a.pageSizeParam = t.pageSizeParam, a.recordName = t.recordName, $.each(t.children, function (e, t) {
            var o;
            "field" == t.type ? (o = t.children, a.field = [], $.each(o, function (e, t) {
                var o = {};
                o.columnComments = t.columnComments, o.columnName = t.columnName, o.columnType = t.columnType, o.isauto = t.isauto, a.field.push(o)
            })) : "parms" == t.type && (t = t.children, a.parms = [], $.each(t, function (e, t) {
                var o = {};
                o.parmName = t.parmName, o.parmCName = t.parmCName, a.parms.push(o)
            }))
        }), o.push(a)
    }), o
}

function setDataSetsNodesVal(e) {
    var l = {name: "数据集列表", children: []};
    return $.each(e, function (e, t) {
        var o = {children: []};
        o.name = t.dataSetName, o.dic = t.dic, o.isvalid = t.isvalid, o.dataSourceName = t.dataSourceName, o.dataSetName = t.dataSetName, o.dataSetType = t.dataSetType, o.commandText = t.commandText, o.keyName = t.keyName, o.optCode = t.optCode, o.optName = t.optName, o.path = t.path, o.method = t.method, o.cate = t.cate, o.node = t.node, o.hostName = t.hostName, o.pageType = t.pageType, o.pageParam = t.pageParam, o.pageSizeParam = t.pageSizeParam, o.recordName = t.recordName;
        var a = {type: "field", name: "字段", children: []}, r = {type: "parms", name: "参数", children: []};
        $.each(t.field, function (e, t) {
            var o = {};
            o.columnComments = t.columnComments, o.columnName = t.columnName, o.columnType = t.columnType, o.isauto = t.isauto, o.name = t.columnComments || t.columnName, a.children.push(o)
        }), $.each(t.parms, function (e, t) {
            var o = {};
            o.parmName = t.parmName, o.parmCName = t.parmCName, o.name = t.parmCName || t.parmName, r.children.push(o)
        }), o.children.push(a), o.children.push(r), l.children.push(o)
    }), l
}

function getDataParmsNodesVal(e) {
    var a = [], e = e[0].children;
    return e && 0 < e.length && $.each(e, function (e, t) {
        var o = {};
        o.dbType = t.dbType, o.parmCName = t.parmCName, o.parmName = t.parmName, o.parmType = t.parmType, o.parmvl = t.parmvl, o.isnull = t.isnull, o.showType = t.showType, o.format = t.format, o.isnull = t.isnull, o.selRange = t.selRange, o.parmEvent = t.parmEvent, o.position = t.position, o.mustInput = t.mustInput, o.readonly = t.readonly, o.labelAlign = t.labelAlign, o.fieldAlign = t.fieldAlign, o.labelStyle = t.labelStyle, o.fieldStyle = t.fieldStyle, o.width = t.width, o.widthL = t.widthL, o.widthR = t.widthR, o.height = t.height, o.fontSize = t.fontSize, o.fieldSize = t.fieldSize, o.verticalAlign = t.verticalAlign, o.maxLength = t.maxLength, t.puuid ? o.puuid = t.puuid : o.puuid = getPuuid("parm"), o.custom = t.custom, a.push(o)
    }), a
}

function getPuuid(e) {
    for (var t = 0; t < 5; t++) e = e + "-" + (65536 * (1 + Math.random()) | 0).toString(16).substring(1);
    return e
}

function setDataParmsNodesVal(e) {
    var a = {name: "参数列表", children: []};
    return $.each(e, function (e, t) {
        var o = {};
        o.dbType = t.dbType, o.parmCName = t.parmCName, o.parmName = t.parmName, o.parmType = t.parmType, o.parmvl = t.parmvl, o.isnull = t.isnull, o.format = t.format, o.showType = t.showType, o.name = t.parmCName || t.parmName, o.isnull = t.isnull, o.selRange = t.selRange, o.parmEvent = t.parmEvent, o.position = t.position, o.mustInput = t.mustInput, o.readonly = t.readonly, o.labelAlign = t.labelAlign, o.fieldAlign = t.fieldAlign, o.labelStyle = t.labelStyle, o.fieldStyle = t.fieldStyle, o.width = t.width, o.widthL = t.widthL, o.widthR = t.widthR, o.height = t.height, o.fontSize = t.fontSize, o.fieldSize = t.fieldSize, o.verticalAlign = t.verticalAlign, o.maxLength = t.maxLength, o.puuid = t.puuid, o.custom = t.custom, a.children.push(o)
    }), a
}

function getTableDataByUUID(e) {
    $.rdp.ajax({
        url: "../../rdp/selectReport", data: {uuid: e},
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        type: "post", success: function (e) {
            0 == e.code ? (loadData(e.json), tableInitFlag = !0, cPush("getTableDataByUUID")) : e.msg ? layer.msg(e.msg) : e && -1 != e.indexOf("报表管理") && layer.msg("Session失效，请重新登陆")
        }
    })
}

function pasteSetValue(e) {
    for (var t = 0; t < e.length; t++) hot.Methods.setCellMeta(e[t][0], e[t][1], "value", e[t][3])
}

function hotafterCreateRow(e, t, o) {
    if ("auto" != o) {
        for (var a = getMergeCells(TableData), r = 0; r < a.length; r++) a[r].row >= e ? a[r].row += t : a[r].row + a[r].rowspan >= e + t && (a[r].rowspan += t);
        TableData.rowHeights.splice(e, 0, 23), tb_updateSettings()
    }
}

function hotafterCreateCol(e, t, o) {
    if ("auto" != o) {
        for (var a = getMergeCells(TableData), r = 0; r < a.length; r++) a[r].col >= e ? a[r].col += t : a[r].col + a[r].colspan >= e + t && (a[r].colspan += t);
        TableData.colWidths.splice(e, 0, 100), tb_updateSettings()
    }
}

function hotBeforeRemoveRow(e, t) {
    for (var o, a = getTabelData(), r = a.mergeCells, l = {}, n = [], s = 0; s < r.length; s++) r[s].row < e && r[s].row + r[s].rowspan >= e + t ? r[s].rowspan -= t : r[s].row <= e && r[s].row + r[s].rowspan > e && r[s].row + r[s].rowspan < e + t ? r[s].rowspan -= r[s].row + r[s].rowspan - e : r[s].row <= e && r[s].row + r[s].rowspan > e + t ? (r[s].rowspan -= t, 0 < r[s].row && r[s].row != e && (l[r[s].row] = !0)) : r[s].row >= e && r[s].row + r[s].rowspan <= e + t ? (n.push(s), l[r[s].row] = !0) : r[s].row > e && e + t - 1 > r[s].row ? (o = r[s].row + r[s].rowspan - 1, r[s].rowspan = o - (e + t - 1), r[s].row = o - t - r[s].rowspan + 1, l[r[s].row] = !0) : r[s].row > e && (r[s].row < e + t && e + t <= r[s].row + r[s].rowspan && (o = r[s].row + r[s].rowspan - 1, r[s].rowspan = o - (e + t - 1), r[s].row = o - t - r[s].rowspan + 1), l[r[s].row] = !0), l[r[s].row] && (r[s].row -= t);
    for (s = n.length - 1; 0 <= s; s--) r.splice(n[s], 1);
    var i = a.row;
    i.length >= e && i.splice(e, t);
    i = a.rowHeights;
    i.length >= e && i.splice(e, t), loadData(a)
}

function hotBeforeRemoveCol(e, t) {
    for (var o, a = getTabelData(), r = a.mergeCells, l = {}, n = [], s = 0; s < r.length; s++) r[s].col < e && r[s].col + r[s].colspan >= e + t ? r[s].colspan -= t : r[s].col <= e && r[s].col + r[s].colspan > e && r[s].col + r[s].colspan < e + t ? r[s].colspan -= r[s].col + r[s].colspan - e : r[s].col <= e && r[s].col + r[s].colspan > e + t ? (r[s].colspan -= t, 0 < r[s].col && r[s].col != e && (l[r[s].col] = !0)) : r[s].col >= e && r[s].col + r[s].colspan <= e + t ? (n.push(s), l[r[s].col] = !0) : r[s].col > e && e + t - 1 > r[s].col ? (o = r[s].col + r[s].colspan - 1, r[s].colspan = o - (e + t - 1), r[s].col = o - t - r[s].colspan + 1, l[r[s].col] = !0) : r[s].col > e && (r[s].col < e + t && e + t <= r[s].col + r[s].colspan && (o = r[s].col + r[s].colspan - 1, r[s].colspan = o - (e + t - 1), r[s].col = o - t - r[s].colspan + 1), l[r[s].col] = !0), l[r[s].col] && (r[s].col -= t);
    for (s = n.length - 1; 0 <= s; s--) r.splice(n[s], 1);
    for (var i = a.row, s = 0; s < i.length; s++) {
        var c = i[s].col;
        c.length >= e && c.splice(e, t)
    }
    var d = a.colWidths;
    d.length >= e && d.splice(e, t), loadData(a)
}

function hotafterRemoveRow(e, t) {
}

function hotafterRemoveCol(e, t) {
}

function getQueryString(e) {
    e = new RegExp("(^|&)" + e + "=([^&]*)(&|$)", "i"), e = window.location.search.substr(1).match(e);
    return null != e ? unescape(e[2]) : null
}

function getTDWidth(o, a, e) {
    var r, t = 0;
    if ($.each(e, function (e, t) {
        t.row == o && t.col == a && (r = t)
    }), r) for (var l = 0; l < r.colspan; l++) t += hot.Methods.getColWidth(a + l); else t = hot.Methods.getColWidth(a);
    return t
}

function S4() {
    return (65536 * (1 + Math.random()) | 0).toString(16).substring(1)
}

function guid() {
    return S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4()
}

function guide_line(e) {
    var t, o = $(e).val(), a = $(e).find("option:selected").text();
    $(".wtSpreader .guide_line").remove(), null != o && "" != o && null != o && (t = $(".htCore").height(), e = $(".htCore").offset().top, (a = $('<line class="guide_line ' + o + '_guide" title="' + a + '"></line>')).css({
        height: t,
        top: e
    }), $(".wtSpreader").append(a))
}

function loadToolsBar() {
    $(".toolbars").mCustomScrollbar({
        theme: "3d-dark",
        axis: "x",
        autoHideScrollbar: !0,
        scrollButtons: {enable: !0},
        advanced: {updateOnBrowserResize: !0, autoExpandHorizontalScroll: !0}
    }), $(window).resize(function () {
        $(".toolbars").mCustomScrollbar("update")
    });
    var e = document.documentElement.clientWidth;
    $(".toolbars .group .bar_shrink").each(function (e, t) {
        var o = $(t), t = o.parents(".group").width();
        o.css({left: t - 20 + "px"})
    }), $(".toolbars .group .bar_shrink").bind("click", function () {
        var e, t = $(this), o = t.parents(".group");
        o.hasClass("group_hidden") ? (o.removeClass("group_hidden"), e = t.parents(".group").width(), t.css({left: e - 20 + "px"})) : (o.addClass("group_hidden"), t.removeAttr("style")), initBarWidth()
    }), e < 1500 && $(".toolbars .group .bar_shrink").click()
}

function initBarWidth(e) {
    var o = 0;
    $(".toolbars .group").each(function (e, t) {
        t = $(t).width();
        o += t
    }), o += 10, $(".customScrollbar").css({
        "min-width": o + "px",
        width: o + "px"
    }), $(".toolbars").mCustomScrollbar("update")
}

function initSkin() {
    $.ajax({
        url: "../../rdp/customSkinList", cache: !1,
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        type: "post", success: function (e) {
            e.list && $.each(e.list, function (e, t) {
                $("#skinType").append('<option value="' + t + '">' + t + "</option>")
            })
        }
    })
}

function skinCustomShow() {
    layer.open({
        type: 2, area: ["960px", "600px"], title: "自定义皮肤设置", content: "skin.html", cancel: function () {
        }, success: function (e, t) {
        }, end: function () {
        }, btn: ["保存", "取消"], yes: function (e, t) {
            var o = $(t).find("iframe")[0].contentWindow, t = o.curSkin, o = JSON.stringify(o.curJsonObj);
            $.ajax({
                url: "../../rdp/saveCustomSkin",
                cache: !1,
                data: {fileName: t, content: o},
                type: "post",
                success: function (e) {
                    alert("成功")
                }
            })
        }, btn2: function (e, t) {
            layer.close(e)
        }
    })
}
