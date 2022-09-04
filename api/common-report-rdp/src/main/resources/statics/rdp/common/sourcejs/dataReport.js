try{
    document.write("<script language=javascript src=../../statics/js/t2admin.js></script>");
}catch (e) {}
console.log("load: src/main/resources/statics/rdp/common/sourcejs/dataReport.js");

var tempDataReportLayerIndex;

function setFrameId(e) {
    var t = hot.Methods.getSelected();
    t && (t = t[0], hot.Methods.getCellMeta(t[0], t[1]), hot.Methods.setCellMeta(t[0], t[1], "frameid", $(e).val()), hot.Methods.setDataAtCell(t[0], t[1], "subreport:" + $(e).find("option:selected").text())), layer.close(tempDataReportLayerIndex)
}

function removeReport() {
    var e, t = hot.Methods.getSelected();
    t && (e = t[0], (t = hot.Methods.getCellMeta(e[0], e[1])).frameid = "", delete t.frameid, hot.Methods.setDataAtCell(e[0], e[1], ""))
}

function addSubReport() {
    var e, t, d, o = hot.Methods.getSelected();
    o && (e = o[0], t = hot.Methods.getCell(e[0], e[1]), o = $(t).offset().left + $(t).width(), t = $(t).offset().top + $(t).height(), d = hot.Methods.getCellMeta(e[0], e[1]).frameid, tempDataReportLayerIndex = layer.open({
        type: 1,
        shade: .01,
        title: !1,
        shadeClose: !0,
        closeBtn: 0,
        offset: [t + "px", o + "px"],
        resize: !1,
        success: function (e, t) {
            e.find("#temlist").empty(), e.find("#temlist").append("<option value=''>请选择子报表</option>"), $.rdp.ajax({
                url: "../../rdp/selectAllReportFile?kw=",
                beforeSend: function(jqXHR) {
                    jqXHR.setRequestHeader(TOKEN_TAG,T2Admin_TOKEN);
                    console.log("selectAllReportFile rq:",TOKEN_TAG,T2Admin_TOKEN);
                },
                type: "post",
                success: function (e) {
                    var t, o = e.list;
                    for (t in o) {
                        var a = "";
                        d == o[t].uuid && (a = " selected = 'selected'"), $("#temlist").append("<option value='" + o[t].uuid + "' " + a + ">" + o[t].name + "</option>")
                    }
                }
            })
        },
        content: $("#dataReports")
    }))
}
