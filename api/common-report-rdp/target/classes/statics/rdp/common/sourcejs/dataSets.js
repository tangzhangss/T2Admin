try {
    document.write("<script language=javascript src=../../../statics/js/t2admin.js></script>");
}catch (e) {
}

var tempTreeNodeForSets, tempCoordsForSets, tempLayeroForSets, tempLayeroForJsonField, dragFlagForSets = !1,
    settingForSets = {
        view: {
            addHoverDom: addHoverDomForSets,
            removeHoverDom: removeHoverDomForSets,
            addDiyDom: addDiyDomForSets
        },
        data: {simpleData: {enable: !0}},
        edit: {
            drag: {
                autoExpandTrigger: !0, prev: function (e, t, a) {
                    return !(!a.getParentNode() || "parms" != a.getParentNode().type)
                }, inner: function (e, t, a) {
                    return a && "parms" == a.type
                }, next: function (e, t, a) {
                    return !(!a.getParentNode() || "parms" != a.getParentNode().type)
                }, isCopy: !0, isMove: !1
            }, enable: !0, showRenameBtn: !1, showRemoveBtn: function (e, t) {
                return 1 == t.level
            }, removeTitle: "删除"
        },
        callback: {
            beforeDrag: beforeDragForSets,
            beforeDrop: beforeDropForSets,
            onDrag: onDragForSets,
            onDrop: onDropForSets,
            onClick: zTreeOnClickForSets,
            beforeRemove: zTreeBeforeRemoveForSets
        }
    }, zNodesForSets = [{id: 1, pId: 0, name: "数据集列表", open: !0}];

function getDataSetsNodesByParam(e, t, a) {
    return $.fn.zTree.getZTreeObj("dataSets").getNodesByParam(e, t, a)
}

function addDiyDomForSets(e, t) {
    0 == t.level && $("#" + t.tId + "_a").append('<button type="button" class="tb_btn" id="addDataSetsBtn" onclick="addDataSets()">新增数据集</button>')
}

function beforeDragForSets(e, t) {
    return "field" == t[0].type || "field" == t[0].getParentNode().type && 3 == t[0].level
}

function beforeDropForSets(e, t, a, o, n) {
    return !0
}

function onDragForSets(e, t, a) {
    dragFlagForSets = !0
}

function onDropForSets(e, t, a, o, n, d) {
    var r, i, s, l;
    dragFlagForSets = !1, a[0].type && "field" == a[0].type ? (r = a[0].getParentNode().name, i = a[0].children, s = Number(tempCoordsForSets.row), l = Number(tempCoordsForSets.col), $.each(i, function (e, t) {
        hot.Methods.setDataAtCell(s, l + e, "=" + r + "." + t.name), hot.Methods.setCellMeta(s, l + e, "columnName", t.columnName), hot.Methods.setCellMeta(s, l + e, "columnType", t.columnType)
    }), tempCoordsForSets = {}, console.log(a[0])) : (r = a[0].getParentNode().getParentNode().name, tempCoordsForSets ? (hot.Methods.setDataAtCell(tempCoordsForSets.row, tempCoordsForSets.col, "=" + r + "." + a[0].name), hot.Methods.setCellMeta(tempCoordsForSets.row, tempCoordsForSets.col, "columnName", a[0].columnName), hot.Methods.setCellMeta(tempCoordsForSets.row, tempCoordsForSets.col, "columnType", a[0].columnType), tempCoordsForSets = {}) : loadcharts.addSetColumn({
        dataSetName: r,
        column: r + "." + a[0].name,
        columnName: a[0].columnName,
        columnType: a[0].columnType
    }))
}

function setCoords(e) {
    tempCoordsForSets = e
}

function zTreeOnClickForSets(e, t, a) {
}

function zTreeBeforeRemoveForSets(e, t) {
    return layer.confirm("确认删除数据集  “" + t.name + "” 吗？", {btn: ["确定", "取消"]}, function (e) {
        $.fn.zTree.getZTreeObj("dataSets").removeNode(t), layer.alert("删除成功!")
    }, function (e) {
        layer.close(e)
    }), !1
}

function addHoverDomForSets(e, t) {
    if (1 != t.level) return removeSql(), !1;
    showSql(t);
    var a, o = $("#" + t.tId + "_span");
    t.editNameFlag || 0 < $("#" + t.tId + "_editBtn").length || (a = "<span class='button edit' id='" + t.tId + "_editBtn' title='修改' onfocus='this.blur();'></span>", o.after(a), (a = $("#" + t.tId + "_editBtn")) && a.bind("click", function () {
        return addDataSets(t), !1
    }))
}

function removeHoverDomForSets(e, t) {
    if (removeSql(t), 1 != t.level) return !1;
    $("#" + t.tId + "_editBtn").unbind().remove()
}

function addParmItem(e) {
    var t = $('<div class="parm-item"><select></select><div class="btn-group"><i class="icon icon-insert"onclick="addParmItem(this);">+</i><i class="icon icon-del"onclick="delParmItem(this)">-</i></div></div>'),
        a = $.fn.zTree.getZTreeObj("dataParms").getNodesByParam("level", "1", null), o = t.find("select");
    $.each(a, function (e, t) {
        o.append('<option value="' + t.parmName + '">' + t.parmCName + "</option>")
    }), e ? $(e).parents(".parm-item").after(t) : tempLayeroForSets.find(".parm-list").append(t)
}

function addParmItemForEdit(e, t, a) {
    var o = $.fn.zTree.getZTreeObj("dataParms").getNodesByParam("level", "1", null), n = "";
    $.each(o, function (e, t) {
        n += "<option value='" + t.parmName + "'>" + (t.parmCName || t.parmName) + "</option>"
    });
    var t = $.fn.zTree.getZTreeObj("dataSets").getNodesByParam("type", "parms", t)[0].children,
        d = "json" == a ? e.find("#jsonlist .parm-list") : e.find("#ddslist .parm-list");
    t && 0 < t.length && $.each(t, function (e, t) {
        var a = $('<div class="parm-item"><select></select><div class="btn-group"><i class="icon icon-insert"onclick="addParmItem(this);">+</i><i class="icon icon-del"onclick="delParmItem(this)">-</i></div></div>'),
            o = a.find("select");
        o.append(n), o.val(t.parmName), d.append(a)
    })
}

function delParmItem(e) {
    $(e).parents(".parm-item").remove()
}

var editor,
    formatterOption = {language: "pl/sql", customField: ["\\$\\{[A-Za-z0-9_.-]*\\}", "\\#\\{[A-Za-z0-9_.-]*\\}"]};

function showSql(e) {
    null == e.commandText && (e.commandText = "");
    var t = sqlFormatter.format(e.commandText, formatterOption), a = new RegExp("\n|\r\n", "g"),
        o = t.replace(a, "</br>").replace(/\s/g, "&nbsp;&nbsp;");
    $(".showsql").unbind().remove();
    t = $('<div class="showsql" id="' + e.tId + '_showsql"></div>'), a = $("#" + e.tId + "_a"), e = rdpConfig.getConfigParm("dataSet");
    t.append("&nbsp;&nbsp;&nbsp;&nbsp;" + o), hljs.configure({useBR: !0, languages: ["sql"]}), t.each(function (e, t) {
        hljs.highlightBlock(t)
    }), t.appendTo("body"), "left" == e ? t.css({
        "min-width": "200px",
        "min-height": "200px",
        top: "10px",
        right: "410px",
        position: "absolute",
        border: "1px solid #b4b4b4",
        "border-radius": "5px",
        "z-index": 999
    }) : (t.css({
        "min-width": "200px",
        "min-height": "200px",
        left: a.offset().left + "px",
        position: "absolute",
        border: "1px solid #b4b4b4",
        "border-radius": "5px",
        "z-index": 999
    }), t.css("top", a.offset().top - t.outerHeight(!0) + "px")), t.bind("click", function () {
        return !1
    })
}

function removeSql(e) {
    $(".showsql").remove()
}

function addDataSets(a) {
    function e(e) {
        e.find("#commandText").height(e.height() - 370), editor.resize()
    }

    layer.open({
        type: 1,
        area: ["860px", "520px"],
        maxmin: !0,
        title: "数据集配置",
        content: $("#dataSetsInfo"),
        cancel: function () {
        },
        resizing: e,
        full: e,
        min: e,
        restore: e,
        success: function (o, e) {
            (tempLayeroForSets = o).find("#DataSourceName").empty(), o.find("#hostName").empty(), o.find("#pageprop").hide(), o.find("#pageParam").val(""), o.find("#recordName").val(""), o.find("#DataSourceName").append('<option value=""></option><option value="javabean">javabean</option><option value="json">API请求</option>'), $(document).queue("dsQuery", function () {
                $.rdp.ajax({
                    url: "../../rdp/selectAllDataSourceName",headers:{[TOKEN_TAG]:T2Admin_TOKEN}, type: "post", async: !0, success: function (e) {
                        for (var t = e.list, a = 0; a < t.length; a++) o.find("#DataSourceName").append("<option value='" + t[a].dataSourceName + "'>" + t[a].dataSourceName + "</option>");
                        $(document).dequeue("dsQuery")
                    }
                })
            }), $(document).queue("dsQuery", function () {
                $.rdp.ajax({
                    url: "../../rdp/selectAllJSONName", type: "post",headers:{[TOKEN_TAG]:T2Admin_TOKEN}, async: !0, success: function (e) {
                        for (var t = e.list, a = 0; a < t.length; a++) o.find("#hostName").append("<option value='" + t[a].dataSourceName + "'>" + t[a].dataSourceName + "</option>");
                        $(document).dequeue("dsQuery")
                    }
                })
            }), $(document).queue("dsQuery", function () {
                o.find("#DataSourceName").unbind().bind("change", function () {
                    $(o).find("#jblist").hide(), $(o).find("#ddslist").hide(), $(o).find("#jsonlist").hide(), ("javabean" == $(this).val() ? $(o).find("#jblist") : "json" == $(this).val() ? $(o).find("#jsonlist") : $(o).find("#ddslist")).show()
                }), o.find("#pageType").unbind().bind("change", function () {
                    0 == $(this).val() ? $(o).find("#pageprop").hide() : $(o).find("#pageprop").show()
                }), o.find(".formatterBtn").unbind().bind("click", function () {
                    var e = editor.getValue(), e = sqlFormatter.format(e, formatterOption);
                    editor.setValue(e)
                }), o.find("#jsonFieldBtn").unbind().bind("click", function () {
                    jsonFieldLayer()
                }), o.find(".parm-list").empty(), a ? (setDomAllVals(o[0], a), editor.setValue(a.commandText), addParmItemForEdit(tempLayeroForSets, a, o.find("#DataSourceName").val())) : (setDomAllVals(o[0], {
                    isvalid: !0,
                    isparmdfu: !0,
                    pageType: 0,
                    method: 0,
                    cate: 0
                }), editor.setValue("")), $(o).find("#jblist").hide(), $(o).find("#ddslist").hide(), $(o).find("#jsonlist").hide(), "javabean" == o.find("#DataSourceName").val() ? ($(o).find("#jblist").show(), o.find("#FactoryClass").val(a.commandText)) : "json" == o.find("#DataSourceName").val() ? ($(o).find("#jsonlist").show(), a && (1 == a.pageType && $("#pageprop").show(), getJsonfieldsByTree(o, a))) : $(o).find("#ddslist").show(), $(document).dequeue("dsQuery")
            }), $(document).dequeue("dsQuery")
        },
        end: function () {
            tempLayeroForSets = null
        },
        btn: ["保存", "取消"],
        yes: function (e, t) {
            (a ? ("json" == $(t).find("#DataSourceName").val() ? editSetsJSONNode : editSetsNode)(t, a) : ("json" == $(t).find("#DataSourceName").val() ? addSetsJSONNode : addSetsNode)(t)) && layer.close(e)
        },
        btn2: function (e, t) {
            layer.close(e)
        }
    })
}

function addSetsJSONNode(e) {
    var t = getDomAllVals(e.find(".form-info")[0]), a = t.dataSetName, o = t.hostName, n = e.data("jsonfields");
    if ("" == o) return window.top.layer.tips("请选择数据源", $(e).find("#hostName"), {tipsMore: !0}), !1;
    if ("" == a) return window.top.layer.tips("数据集名称不能为空", $(e).find("#DataSetName"), {tipsMore: !0}), !1;
    var d = $.fn.zTree.getZTreeObj("dataSets"), r = d.getNodes();
    (o = $.extend(!0, {}, t)).name = t.dataSetName;
    var i, o = d.addNodes(r[0], o), a = {name: "参数", type: "parms", open: !1},
        r = d.addNodes(o[0], r = {name: "字段", type: "field", open: !1}), a = d.addNodes(o[0], a),
        e = getParmListVals(e.find("#jsonlist .parm-list")[0]);
    return d.addNodes(a[0], e), n && 0 < n.length ? (i = [], $.each(n, function (e, t) {
        t.columnComments ? t.name = t.columnComments : t.name = t.columnName, i.push(t)
    }), d.addNodes(r[0], i)) : d.addNodes(r[0], []), t.dic && settingDic(r[0]), !0
}

function addSetsNode(e) {
    var d = getDomAllVals(e.find(".form-info")[0]), t = d.dataSetName, a = d.dataSourceName;
    d.commandText = editor.getValue();
    var r, i = !0;
    if ("" == a) return window.top.layer.tips("请选择数据源", $(e).find("#DataSourceName"), {tipsMore: !0}), !1;
    if ("" == t) return window.top.layer.tips("数据集名称不能为空", $(e).find("#DataSetName"), {tipsMore: !0}), !1;
    "javabean" == a && (d.commandText = d.factoryClass, editor.setValue(d.factoryClass));
    for (var s = getParmListVals(e.find("#ddslist .parm-list")[0]), o = "", n = 0; n < s.length; n++) o += s[n].parmName + ",";
    return "" != o && (o = o.substring(0, o.length - 1)), $(document).queue("addDsQuery", function () {
        var e = $.fn.zTree.getZTreeObj("dataParms").getNodesByParam("level", "1", null);
        (d.dic || d.isvalid || "javabean" == a) && $.rdp.ajax({
            url: "../../rdp/parFieldsForJSON",
            type: "post",
            headers:{[TOKEN_TAG]:T2Admin_TOKEN},
            async: !1,
            data: {
                dataSourceName: d.dataSourceName,
                dataSetType: d.dataSetType,
                parms: JSON.stringify(e),
                parm: o,
                value: Base64Util.encode64(editor.getValue())
            },
            success: function (e) {
                0 == e.code ? (r = e.list, layer.msg("校验通过，保存成功", {time: 2e3})) : (layer.msg(e.msg), layer.closeAll("loading"), i = !1), $(document).dequeue("addDsQuery")
            },
            error: function () {
                layer.tips("sql错误", $(tempLayeroForSets).find("#commandText"), {tipsMore: !0}), layer.closeAll("loading"), i = !1
            },
            beforeSend: function () {
                layer.load(1, {shade: [.1, "#fff"]})
            },
            complete: function () {
                layer.closeAll("loading")
            }
        })
    }), $(document).queue("addDsQuery", function () {
        var e, t, a, o, n;
        i && (o = (e = $.fn.zTree.getZTreeObj("dataSets")).getNodes(), (t = $.extend(!0, {}, d)).name = d.dataSetName, t = e.addNodes(o[0], t), o = e.addNodes(t[0], o = {
            name: "字段",
            type: "field",
            open: !(n = {name: "参数", type: "parms", open: !1})
        }), n = e.addNodes(t[0], n), e.addNodes(n[0], s), r && 0 < r.length ? (a = [], $.each(r, function (e, t) {
            t.columnComments ? t.name = t.columnComments : t.name = t.columnName, a.push(t)
        }), e.addNodes(o[0], a)) : e.addNodes(o[0], []), d.dic && settingDic(o[0])), $(document).dequeue("addDsQuery")
    }), $(document).dequeue("addDsQuery"), i
}

function getJsonfieldsByTree(e, t) {
    t = $.fn.zTree.getZTreeObj("dataSets").getNodesByParam("type", "field", t);
    e.data("jsonfields", t[0].children)
}

function editSetsJSONNode(e, t) {
    var a = $.fn.zTree.getZTreeObj("dataSets"), o = getDomAllVals(e.find(".form-info")[0]), n = o.dataSetName,
        d = o.hostName, r = e.data("jsonfields");
    if ("" == d) return window.top.layer.tips("请选择数据源", $(e).find("#hostName"), {tipsMore: !0}), !1;
    if ("" == n) return window.top.layer.tips("数据集名称不能为空", $(e).find("#DataSetName"), {tipsMore: !0}), !1;
    (t = $.extend(!0, t, o)).name = o.dataSetName, t.commandText = editor.getValue(), a.updateNode(t);
    var i, o = a.getNodesByParam("type", "field", t);
    a.removeChildNodes(o[0]), r && 0 < r.length ? (i = [], $.each(r, function (e, t) {
        t.columnComments ? t.name = t.columnComments : t.name = t.columnName, i.push(t)
    }), a.addNodes(o[0], i)) : a.addNodes(o[0], []);
    t = a.getNodesByParam("type", "parms", t);
    a.removeChildNodes(t[0]);
    e = getParmListVals(e.find("#jsonlist .parm-list")[0]);
    return a.addNodes(t[0], e), !0
}

function editSetsNode(e, o) {
    var n, d = $.fn.zTree.getZTreeObj("dataSets"), r = getDomAllVals(e.find(".form-info")[0]), i = !0,
        t = r.dataSetName, a = r.dataSourceName;
    if ("" == a) return window.top.layer.tips("请选择数据源", $(layero).find("#DataSourceName"), {tipsMore: !0}), !1;
    if ("" == t) return window.top.layer.tips("数据集名称不能为空", $(layero).find("#DataSetName"), {tipsMore: !0}), !1;
    "javabean" == a && (r.commandText = r.factoryClass), (o = $.extend(!0, o, r)).name = r.dataSetName, o.commandText = editor.getValue(), d.updateNode(o);
    for (var s = getParmListVals(e.find("#ddslist .parm-list")[0]), l = "", m = 0; m < s.length; m++) l += s[m].parmName + ",";
    "" != l && (l = l.substring(0, l.length - 1)), (r.dic || r.isvalid || "javabean" == a) && (c = $.fn.zTree.getZTreeObj("dataParms").getNodesByParam("level", "1", null), $.rdp.ajax({
        url: "../../rdp/parFieldsForJSON",
        type: "post",
        headers:{[TOKEN_TAG]:T2Admin_TOKEN},
        async: !1,
        data: {
            dataSourceName: r.dataSourceName,
            dataSetType: r.dataSetType,
            parms: JSON.stringify(c),
            parm: l,
            value: Base64Util.encode64(editor.getValue())
        },
        success: function (e) {
            var t, a;
            0 == e.code ? (n = e.list, t = d.getNodesByParam("type", "field", o), d.removeChildNodes(t[0]), n && 0 < n.length ? (a = [], $.each(n, function (e, t) {
                t.columnComments ? t.name = t.columnComments : t.name = t.columnName, a.push(t)
            }), d.addNodes(t[0], a)) : d.addNodes(t[0], []), r.dic && settingDic(t[0]), layer.msg("校验通过，保存成功", {time: 2e3})) : (layer.alert(e.msg), i = !1)
        },
        error: function () {
            layer.tips("sql错误", $(tempLayeroForSets).find("#CommandText"), {tipsMore: !0}), i = !1
        },
        beforeSend: function () {
            layer.load(1, {shade: [.1, "#fff"]})
        },
        complete: function () {
            layer.closeAll("loading")
        }
    }));
    var c = d.getNodesByParam("type", "parms", o);
    return d.removeChildNodes(c[0]), d.addNodes(c[0], s), i
}

function getParmListVals(e) {
    for (var t = [], a = e.getElementsByTagName("SELECT"), o = 0; o < a.length; o++) {
        var n = {}, d = a[o].selectedIndex, r = "";
        a[o].options[d] && (r = a[o].options[d].text), n.name = r, n.parmCName = r, n.parmName = a[o].value, t.push(n)
    }
    return t
}

function settingDic(n) {
    layer.open({
        type: 1, area: ["540px", "164px"], title: "数据集字典项配置", content: $("#dataSetsDic"), cancel: function () {
        }, success: function (e, t) {
            e.find("#dicType").empty(), e.find("#dicID").empty(), e.find("#dicText").empty(), e.find("#dicType").append('<option value="">请选择字典类型</option>'), e.find("#dicID").append('<option value="">请选择字段名称</option>'), e.find("#dicText").append('<option value="">请选择值名称</option>');
            var a = n.children;
            $.each(a, function (e, t) {
                $("#dicType").append('<option value="' + t.columnName + '">' + t.columnName + "</option>"), $("#dicID").append('<option value="' + t.columnName + '">' + t.columnName + "</option>"), $("#dicText").append('<option value="' + t.columnName + '">' + t.columnName + "</option>")
            });
            a = n.getParentNode();
            e.find("#dicType").val(a.keyName), e.find("#dicText").val(a.optName), e.find("#dicID").val(a.optCode)
        }, end: function () {
        }, btn: ["保存", "取消"], yes: function (e, t) {
            var a = $.fn.zTree.getZTreeObj("dataSets"), o = n.getParentNode();
            o.keyName = t.find("#dicType").val(), o.optName = t.find("#dicText").val(), o.optCode = t.find("#dicID").val(), a.updateNode(o), layer.close(e)
        }, btn2: function (e, t) {
            layer.close(e)
        }
    })
}

function jsonFieldLayer() {
    layer.open({
        type: 1, area: ["540px", "90%"], title: "JSON数据字段配置", content: $("#jsonFieldLayer"), cancel: function () {
        }, success: function (e, t) {
            tempLayeroForJsonField = e, tempLayeroForSets.find("#node").val(), e.find("#jsonFieldList").empty(), tempLayeroForSets.data("jsonfields") && $.each(tempLayeroForSets.data("jsonfields"), function (e, t) {
                addJSONfieldItem(tempLayeroForJsonField, t)
            }), e.find("#EditJsonFieldBtn").bind("click", function () {
                var e = getDomAllVals(tempLayeroForJsonField.find("#jsonFieldParm")[0]);
                if ("" == e.columnName || "" == e.columnType) return window.top.layer.msg("字段名称和字段类型不能为空！"), !1;
                addJSONfieldItem(tempLayeroForJsonField, e)
            }), e.find("#GetJsonFieldBtn").bind("click", function () {
                var e, t = tempLayeroForSets.find("#cate").val(), a = tempLayeroForSets.find("#path").val();
                "1" == tempLayeroForSets.find("#pageType").val() && ("" != (e = tempLayeroForSets.find("#pageParam").val()) && -1 != a.indexOf("?") && (a += "&" + e + "=1"), "" != (e = tempLayeroForSets.find("#pageSizeParam").val()) && -1 != a.indexOf("?") && (a += "&" + e + "=10")), $.rdp.ajax({
                    url: "../../rdp/getJSONDataByUrl",
                    type: "get",
                    headers:{[TOKEN_TAG]:T2Admin_TOKEN},
                    async: !1,
                    data: {
                        dataSourceName: tempLayeroForSets.find("#hostName").val(),
                        path: a,
                        method: tempLayeroForSets.find("#method").val()
                    },
                    success: function (e) {
                        0 == e.code ? addJSONfieldItemByData(tempLayeroForJsonField, e, t) : window.top.layer.msg("获取数据发生错误！")
                    }
                })
            }), e.find("#node").val(tempLayeroForSets.find("#node").val())
        }, end: function () {
        }, btn: ["保存", "取消"], yes: function (e, t) {
            tempLayeroForSets.data("jsonfields", getJsonFields(t)), console.log(tempLayeroForSets.data()), layer.close(e)
        }, btn2: function (e, t) {
            layer.close(e)
        }
    })
}

function addJSONfieldItem(e, t) {
    var a = e.find("#jsonFieldList"), o = a.find("li[keyName=" + MD5Util.hex_md5(t.columnName) + "]");
    o && 0 < o.length || (o = $('<li class="field-item"><span class="field-name"></span> <span class="field-type"></span> <span class="field-comment"></span><button class="DelJsonFieldBtn" type="button" ><i class="fa fa-minus-circle"></i></button></li>'), a.prepend(o), o.find(".DelJsonFieldBtn").bind("click", function () {
        $(this).parent().remove()
    }), o.attr("keyName", MD5Util.hex_md5(t.columnName)), o.bind("click", function () {
        setDomAllVals(e.find("#jsonFieldParm")[0], o.data())
    })), o.data(t), o.find(".field-name").html(t.columnName), o.find(".field-type").html(t.columnType), o.find(".field-comment").html(t.columnComments)
}

function addJSONfieldItemByData(o, e, t) {
    var a = o.find("#node").val(), e = e.list;
    try {
        if (a) for (var n = -1 < a.indexOf("[") ? (a = a.replace(/]/g, "")).split("[") : [a], d = e, r = 0; r < n.length; r++) {
            if (!d.hasOwnProperty(n[r])) return window.top.layer.msg("未找到匹配的KEY！"), !1;
            d = d[n[r]]
        } else d = e
    } catch (o) {
        window.top.layer.msg("匹配KEY发生错误，请确认KEY无误！")
    }
    e = Type.IsArray(d) ? d[0] : d, $.each(e, function (e, t) {
        var a = {};
        a.columnName = e, a.columnComments = "", a.isauto = 1, Type.IsString(t) || null == t || "null" == t ? (a.columnType = "string", addJSONfieldItem(o, a)) : Type.IsNumber(t) ? (a.columnType = "number", addJSONfieldItem(o, a)) : Type.IsBoolean(t) ? (a.columnType = "boolean", addJSONfieldItem(o, a)) : Type.IsArray(t) && (a.columnType = "array", addJSONfieldItem(o, a))
    })
}

function getJsonFields(e) {
    var t = [];
    return $.each(e.find("#jsonFieldList > .field-item"), function () {
        t.push($(this).data())
    }), t
}

$(function () {
    editor = ace.edit("commandText"), ace.require("ace/ext/error_marker"), ace.require("ace/ext/spellcheck"), ace.require("ace/ext/options"), editor.setOptions({
        spellcheck: !0,
        enableBasicAutocompletion: !0,
        enableLiveAutocompletion: !0,
        fontSize: 14
    }), editor.session.setMode("ace/mode/sql")
});
var Type = function () {
    for (var e = {}, t = ["String", "Object", "Number", "Array", "Undefined", "Function", "Null", "Symbol", "Boolean"], a = 0; a < t.length; a++) !function (t) {
        e["Is" + t] = function (e) {
            return Object.prototype.toString.call(e) == "[object " + t + "]"
        }
    }(t[a]);
    return e
}();
