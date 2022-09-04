try{
    document.write("<script language=javascript src=../../statics/js/t2admin.js></script>");
}catch (e) {}
console.log("load: src/main/resources/statics/rdp/js/show.js");

function setpage(t, e) {
    0 < t && t <= e && (1 == t ? ($("#pagefirst").attr("disabled", !0), $("#pageprev").attr("disabled", !0)) : ($("#pagefirst").attr("disabled", !1), $("#pageprev").attr("disabled", !1)), t == e ? ($("#pagenext").attr("disabled", !0), $("#pagelast").attr("disabled", !0)) : ($("#pagenext").attr("disabled", !1), $("#pagelast").attr("disabled", !1)), $("#pageindex").val(t), $("#pagecount").html(e))
}

var scrollBarHeight = 20, scrollBarWidth = 16, getTargetPropertyVal = function (t, e) {
    try {
        if ($(t).css(e)) return parseFloat($(t).css(e).replace("px", ""))
    } catch (t) {
    }
    return 0
}, getSubHeight = function (t, e) {
    var a = 0;
    return t = t || scrollBarHeight, e || (a += $(".rt-parmlist").outerHeight(!0)), a += $("#toolbar").outerHeight(!0), a += getTargetPropertyVal("#datalist", "marginTop"), a += getTargetPropertyVal("#datalist", "marginBottom"), a += getTargetPropertyVal("#datalist", "paddingTop"), (a += getTargetPropertyVal("#datalist", "paddingBottom")) + t
}, targetRealHeight = function (t) {
    return $(t).height() + getTargetPropertyVal(t, "paddingTop") + getTargetPropertyVal(t, "paddingBottom")
}, targetOutHeight = function (t) {
    return getTargetPropertyVal(t, "marginTop") + getTargetPropertyVal(t, "marginBottom") + getTargetPropertyVal(t, "paddingTop") + getTargetPropertyVal(t, "paddingBottom")
};

function initParamsBar() {
    $(".rt-swicth").bind("click", function () {
        function t(t, e, a) {
            a = a || 750, $(t).css({transition: "all " + a / 1e3 + "s"}), $(t).css(e), setTimeout(function () {
                $(t).css({transition: ""})
            }, a)
        }

        var e = $("#toolbar").outerHeight(!0), a = targetRealHeight(".rt-parmlist");
        getSubHeight();
        $(".rt-parmlist").hasClass("on") ? ($(".rt-parmlist").removeClass("on").css("margin-top", -$(".rt-parmlist").outerHeight(!0) + "px"), t("#datalist", {height: "calc(100% - " + (e + targetOutHeight("#datalist") + scrollBarHeight) + "px)"})) : ($(".rt-parmlist").addClass("on").css("margin-top", 0), t("#datalist", {height: "calc(100% - " + (e + targetOutHeight("#datalist") + scrollBarHeight + a) + "px)"}))
    })
}

function first() {
    isJb ? parent.searchpage(1) : init(1)
}

function prep() {
    var t = parseInt($("#pageindex").val());
    t <= parseInt($("#pagecount").html()) && 1 < t && (isJb ? parent.searchpage(t - 1) : init(t - 1))
}

function nextp() {
    var t = parseInt($("#pageindex").val());
    t < parseInt($("#pagecount").html()) && (isJb ? parent.searchpage(t + 1) : init(t + 1))
}

function last() {
    var t = parseInt($("#pagecount").html());
    isJb ? parent.searchpage(t) : init(t)
}

function oninputkeydown(t) {
    13 != t.keyCode || (t = parseInt($("#pageindex").val())) <= parseInt($("#pagecount").html()) && 1 <= t && init(t)
}

function init(i) {
    var t = $("#pagesize").val(),
        e = "../../rdppub/show?uuid=" + uuid + "&currentPage=" + i + "&pageSize=" + t + "&pageType=1&totalRecord=" + totalRecord;
    $.ajax({
        url: e, data: $("#searchaddition").serialize(),headers:{[TOKEN_TAG]:T2Admin_TOKEN}, type: "post", async: !0, success: function (t) {
            var e, a, r;
            0 == t.code ? (e = t.list, $("#pagesize").val(e.pageSize), a = $("<div>" + e.body + "</div>"), r = '<style type="text/css" media="all">' + e.css + "</style>" + a.html(), subreport = e.subreport, fillreport = e.fillreport, fillreport && (a = new RegExp('"../statics/', "g"), r = r.replace(a, '"../../statics/').replace("fillUrl='../rdppub/", "fillUrl='../../rdppub/"), $(".btnexport").hide(), $(".btnfillreport").show()), 1 == i && (isJb || (totalRecord = parseInt(e.totalRecord)), 0 < totalRecord && !subreport && (isJb ? $("#totalrecord").html(totalRecord) : $("#totalrecord").html(e.totalRecord), subreport && $("#totalrecord").parent("li").hide()), -2 != e.pageSize ? subreport || -1 == e.pageSize || $("#pagesize").show() : ispubu = !0), $("#datalist").html('<div id="page1">' + r + "</div>"), isJb ? setpage(i, parseInt((totalRecord + e.pageSize - 1) / e.pageSize)) : setpage(e.currentPage, e.totalPage), 1 == showToolbar && $("#toolbar").show()) : alert("请求失败，" + t.msg)
        }, error: function () {
            alert("数据加载出错，请联系管理员！")
        }, beforeSend: function () {
            layer.load(2, {
                shade: !1, content: "正在加载第" + i + "页", success: function (t) {
                    t.find(".layui-layer-content").css({padding: "9px 0 0 40px", width: "200px", color: "#1E9FFF"})
                }
            })
        }, complete: function () {
            setTimeout("layer.closeAll('loading')", 200)
        }
    })
}

function exportFile(a) {
    function t() {
        $.ajax({
            url: "../../rdppub/exportFlag?uuid=" + uuid, type: "post",
                beforeSend: function(jqXHR) {
                    jqXHR.setRequestHeader(TOKEN_TAG,T2Admin_TOKEN);
                },
                headers:{[TOKEN_TAG]:T2Admin_TOKEN},
                async: !0, success: function (t) {
                var e;
                0 == t.code ? (layer.load(2, {
                    shade: [.5, "black"], content: "导出中……", success: function (t) {
                        t.find(".layui-layer-content").css({padding: "9px 0 0 40px", width: "60px", color: "#1E9FFF"})
                    }
                }), $(".queryview-export").hide(), e = "../../rdppub/exportFile?export_type=" + a + "&uuid=" + uuid, subreport && (e = "../../rdppub/exportSubFile?export_type=" + a + "&uuid=" + uuid), (t = (t = $("#searchaddition").serialize()) || {}).chartsImges = JSON.stringify(l), $.fileDownload(e, {
                    data: t,
                    httpMethod: "POST",
                    successCallback: function (t) {
                        layer.closeAll("loading")
                    },
                    failCallback: function (t, e) {
                        $.ajax({
                            url: "../../rdppub/exportFlag?uuid=" + uuid + "&stat=1",
                            type: "get",
                            headers:{[TOKEN_TAG]:T2Admin_TOKEN},
                            beforeSend: function(jqXHR) {
                                jqXHR.setRequestHeader(TOKEN_TAG,T2Admin_TOKEN);
                            },
                        });
                        layer.closeAll("loading");
                        alert("导出失败！")
                    }
                })) : alert("当前报表正在导出或导出已达最大数目，请稍候再来导出")
            }
        })
    }

    var e, r, i, n, o, l = {};
    0 < $(".charts-tag canvas").length ? (e = layer.load(2, {
        shade: [.5, "black"],
        content: "图表图形绘制中……",
        success: function (t) {
            t.find(".layui-layer-content").css({padding: "9px 0 0 40px", width: "60px", color: "#1E9FFF"})
        }
    }), r = $(".charts-tag canvas").length, i = new Array(r), $(".charts-tag canvas").each(function (a, t) {
        var r = $(this).parents("td").attr("id");
        html2canvas($(this)[0], {allowTaint: !0, taintTest: !1}).then(function (t) {
            try {
                var e = t.toDataURL("image/png", 1);
                l[r] = e, i[a] = !0
            } catch (t) {
                console.log("导出生成图片失败")
            }
        })
    }), n = 0, (o = function () {
        setTimeout(function () {
            var a = !1;
            $.each(i, function (t, e) {
                e || (a = !0)
            }), a && n < 25 ? o() : (layer.close(e), t()), console.log(n), n++
        }, 200)
    })()) : t()
}

function openlink(t) {
    if ("" != t && -1 != t.indexOf("url")) {
        for (var e = t.split(","), a = "", r = "", i = "", n = 0; n < e.length; n++) -1 != e[n].indexOf("url:") ? 0 == (a = e[n].split(":")[1]).indexOf("show/") && (a = a.substring(5)) : -1 != e[n].indexOf("parms:") ? i = $("#" + e[n].split(":")[1]).val() : r += e[n].split(":")[0] + "=" + encodeURIComponent(e[n].split(":")[1]) + "&";
        "" != a && "" != r ? "" == i ? window.open(a + "?" + r.substring(0, r.length - 1)) : window.open(a + "?" + r + i) : window.open(a)
    } else alert("链接不合法，请重新配置模板！")
}

$(function () {
    $("#pageindex").bind("keydown", function (t) {
        oninputkeydown(t)
    }), $("#pagesize").change(function () {
        init(page)
    })
});
