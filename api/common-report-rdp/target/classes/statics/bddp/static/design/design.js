var globalChartTheme = "default";
var globalDataBase;
var jsonExtentEditor;
$(function () {
    $(window).resize(function () {
        resizeContent();
    });

    $("#tools-sitemap,#sitemapSwitch").bind("click", function () {
        var sitemap = $(".stiemap");
        if (sitemap.hasClass("on")) {
            sitemap.removeClass("on");
        } else {
            sitemap.addClass("on");
        }
    });
    $("#tools-hidepanel").bind("click", function () {
        var panel = $(".hide-panel");
        hidePanelCtrl(panel.hasClass("on"));
        if (panel.hasClass("on")) {
            panel.removeClass("on");
        } else {
            panel.addClass("on");
        }
    });
    $("#hidePanelBtn").bind("click", function () {
		$(".hide-panel").removeClass("on");
		hidePanelCtrl(true);
    });
    $("#tools-clearCache").bind("click", function () {
        localStorage.clear();
        if (localStorage.length == 0) {
            layx.msg("本地缓存清理成功!", {
                dialogIcon: "success",
                position: "ct",
            });
        }
    });
    $("#tools-scene").bind("click", function () {
        var sitemap = $("#scene");
        if (sitemap.hasClass("on")) {
            sitemap.removeClass("on");
        } else {
            sitemap.addClass("on");
            getSceneConfig();
        }
    });
    $("#tags-props").bind("click", function () {
        $("#rightnav").show().removeClass("off");
        return false;
    });
    $(".rightnav-close").bind("click", function () {
        $("#rightnav").hide().addClass("off");
    });
    $("#tools-new").bind("click", function () {
        newbd();
    });
    $("#tools-open").bind("click", function () {
        openbd();
    });
    $("#tools-open-old").bind("click", function () {
        openOldbd();
    });
    $("#tools-save").bind("click", function () {
        savebd();
    });
    $("#tools-test").bind("click", function () {
        console.log($("#content").data("id"));
    });
    $("#tools-shortcutkey").bind("click", function () {
        layx.iframe("localsite", "快捷键说明", "./component/shortcutKey.html");
        // layx.iframe('localsite', '开发说明', './component/about.html');
    });
    $("#tools-dataSetsConfig").bind("click", function () {
        layx.iframe("datasets", "数据集配置", "./dataSetsConfig/dataConfig.html", {
            minMenu: false,
            maxMenu: false,
            movable: false,
            event: {
                onload: {
                    after: function (layxWindow, winform) {
                        layx.max(winform.id);
                    },
                },
            },
        });
        // layx.iframe('localsite', '开发说明', './component/about.html');
    });
    $("#tools-publish").bind("click", function () {
        dialog.showOpenDialog(
            {
                title: "选择发布路径",
                buttonLabel: "确认发布",
                properties: ["openDirectory"],
            },
            function (filePaths) {
                if (filePaths) {
                    exportbd(filePaths[0]);
                    layx.msg("发布成功!", {
                        dialogIcon: "success",
                        position: "ct",
                    });
                }
            }
        );
    });
    $("#tools-view").bind("click", function () {
        $(".layout-Header").addClass("view");
        $(".layout-Sider").addClass("view");
        $(".layout-Content").addClass("view");
        $("#rightnav").addClass("view");
        // ipcRenderer.send('fullScreen-window');
        requestFullScreen();
    });
    $("#tools-phoneview").bind("click", function () {
        openPhonePanel();
    });

    $("#selectJSONFile").bind("click", function () {
        layx.iframe(
            "localsiteforjson",
            "JSON数据文件选择",
            "./component/BddpDataPage.html",
            {
                event: {
                    ondestroy: {
                        before: function (layxWindow, winform, params, inside, escKey) {
                            if (params.name) {
                                $("#datalink").val(
                                    "../../bddpConfig/" + params.id + "/data/" + params.name
                                );
                            }
                        },
                    },
                },
            }
        );
    });
    $("#selectGlobalJSONFile").bind("click", function () {
        layx.iframe(
            "localsiteforjson",
            "JSON数据文件选择",
            "./component/BddpDataPage.html",
            {
                event: {
                    ondestroy: {
                        before: function (layxWindow, winform, params, inside, escKey) {
                            if (params.name) {
                                $("#globalDataUrl").val(
                                    "../../bddpConfig/" + params.id + "/data/" + params.name
                                );
                            }
                        },
                    },
                },
            }
        );
    });

    $(".close-file ").bind("click", function () {
        $(".file-content").fadeOut();
        var uuid = getQueryString("uuid");
        if (uuid && uuid != "undefined") {
        } else {
            createbd();
        }
    });

    initRightnav();
    initTabsTags();
    initSceneConfig();
    initDataConfig();
    initDataBaseConfig();
    // initGlobalConfig();
    initSelectBox(".layout-Content");
    // $.pageRuler();//初始化就显示辅助线
    initAceEditer();

    editBddpByParams();
});

function editBddpByParams() {
    var uuid = getQueryString("uuid");
    if (uuid && uuid != "undefined") {
        importDataId(uuid);
    } else {
        createbd();
    }
}

function newbd() {
    layx.confirm("系统提示", "是否保存当前场景", null, {
        dialogIcon: "help",
        buttons: [
            {
                label: "是",
                callback: function (id, button, event) {
                    savebd(function () {
                        emptyContent();
                    });
                    layx.destroy(id);
                    createbd();
                },
            },
            {
                label: "否",
                callback: function (id, button, event) {
                    emptyContent();
                    layx.destroy(id);
                    createbd();
                },
            },
        ],
    });
}

function createbd() {
    layx.prompt(
        "新建大屏幕报表",
        "请输入大屏幕报表名称",
        function (id, value, textarea, button, event) {
        },
        null,
        {
            skin: "asphalt",
            storeStatus: false,
            shadable: true,
            width: 360,
            height: 150,
            buttons: [
                {
                    label: "确定",
                    callback: function (id, button, event) {
                        var value = $(event).val();
                        if (value) {
                            var bdData = {
                                content: {
                                    width: 1920,
                                    height: 1080,
                                    globalChartTheme: "default",
                                    id: guid(),
                                    sceneName: value,
                                },
                                boxs: [],
                                ruler: {
                                    v: [],
                                    h: [],
                                },
                            };
                            saveBddpData(bdData, function (res) {
                                if (res.code == 0) {
                                    console.log("新建成功");
                                    history.pushState(
                                        {},
                                        value,
                                        "design.html?uuid=" + bdData.content.id
                                    );
                                    importDataId(bdData.content.id);
                                    layx.destroy(id);
                                } else {
                                    console.log("新建失败");
                                }
                            });
                        } else {
                            $(event).focus();
                        }
                    },
                },
                {
                    label: "取消",
                    callback: function (id, button, event) {
                        openbd();
                        layx.destroy(id);
                    },
                },
            ],
            event: {
                ondestroy: {
                    // 关闭之前，return false 不执行，inside
                    // 区分用户点击内置关闭按钮还是自动调用，用户关闭之前传递的参数，escKey表示是否是按下esc触发
                    before: function (layxWindow, winform, params, inside, escKey) {
                        if (inside) {
                            openbd();
                        }
                    },
                    // 关闭之后
                    after: function () {
                    },
                },
            },
        }
    );
}

function openbd() {
    getJSONFileData("../../bddp/getBddpFolders", function (res) {
        console.log(res)
        if (res.code == 0) {
            $("#file-list").empty();
            var fileArrs = res.res;
            for (var i = 0; i < fileArrs.length; i++) {
                var file = fileArrs[i];
                var li = $(
                    '<li class="list-item"><div class="list-item-image"></div><span class="list-item-name" title="' +
                    file.name +
                    '">' +
                    file.bdname +
                    '</span>' +
                    '<div>'+
                    '<span class="list-item-time">'+timesFun(file.time).timesString+'</span>' +
                    '<span class="list-item-btn list-item-del">删除</span>' +
                    '<span class="list-item-btn list-item-copy">复制</span>' +
                    '<span class="list-item-btn list-item-eidt">编辑</span>' +
                    '<span class="list-item-btn list-item-view">查看</span>' +
                    '</div>'+
                    '</li>'
                );
                //<div class="fire"></div> <a href="#" class="x fa fa-pencil"></a><a href="#" class="y fa fa-share-alt"></a>
                // li.append('<i class="list-item-del fa fa-close"></i>');
                // li.append('<i class="list-item-copy fa fa-copy"></i>');

                li.find(".list-item-image").css(
                    "background-image",
                    "url(../../bddpshow/bgi/" +
                    file.name +
                    "?date=" +
                    new Date().getTime() +
                    ")"
                );
                $("#file-list").append(li);
                li.data(file);
                li.find(".list-item-eidt").bind("click", function () {
                    history.pushState(
                        {},
                        $(this).parents(".list-item").data("bdname"),
                        "design.html?uuid=" + $(this).parents(".list-item").data("name")
                    );
                    importData($(this).parents(".list-item").data("path"));
                });
                li.find(".list-item-view").bind("click", function () {
                    window.open(
                        "../../bddpshow/show/" + $(this).parents(".list-item").data("name")
                    );
                });
                li.find(".list-item-del").bind("click", function () {
                    var path = $(this).parents(".list-item").data("path");
                    var pObj = $(this).parents(".list-item");
                    layx.confirm(
                        "消息提醒",
                        "确认要删除“" + $(this).parents(".list-item").data("bdname") + "”？",
                        function (id) {
                            getAjaxData(
                                "../../bddp/deleteBddpConfig",
                                {
                                    path: path,
                                },
                                function (res) {
                                    if (res.code == 0) {
                                        pObj.remove();
                                    }
                                }
                            );
                            layx.destroy(id);
                        }
                    );
                });
                li.find(".list-item-copy").bind("click", function () {
                    copyData(
                        $(this).parents(".list-item").data("path"),
                        $(this).parents(".list-item").data("name")
                    );
                });
            }
            $(".file-content").fadeIn();
        }
    });
}

function openOldbd() {
    getJSONFileData("../../bddp/getBddpFiles", function (res) {
        if (res.code == 0) {
            $("#file-list").empty();
            var fileArrs = res.res;
            for (var i = 0; i < fileArrs.length; i++) {
                var file = fileArrs[i];
                var li = $(
                    '<li class="list-item"><div class="list-item-image"></div><span class="list-item-name" title="' +
                    file.name +
                    '">' +
                    file.name +
                    "</span></li>"
                );
                $("#file-list").append(li);
                li.data("path", file.path);
                li.bind("click", function () {
                    importDataOld($(this).data("path"));
                });
            }
            $(".file-content").fadeIn();
        }
    });
}
function getDesBddpData(){
    var bdData = {};
    bdData.content = {
        width: $("#content").data("width") || 1920,
        height: $("#content").data("height") || 1080,
        backgroundColor: $("#content").data("backgroundColor"),
        backgroundImage: $("#content").data("backgroundImage"),
        globalZoom: $("#content").data("globalZoom"),
        url: $("#content").data("url"),
        globalChartTheme: globalChartTheme,
        dataFrom: $("#content").data("dataFrom"),
        phonepanel: $("#content").data("phonepanel"),
        filterWay: $("#content").data("filterWay") || 1,
        id: $("#content").data("id"),
    };
    if (
        $("#content").data("sceneName") &&
        $("#content").data("sceneName") != "undefined"
    ) {
        bdData.content.sceneName = $("#content").data("sceneName");
    } else {
        var sitemap = $("#scene");
        sitemap.addClass("on");
        getSceneConfig();
        layx.msg("场景名称不能为空！", {
            dialogIcon: "warn",
        });
        return false;
    }

    bdData.boxs = [];
    bdData.hideboxs = [];
    $("#content")
        .children(".box")
        .each(function () {
            var prop = $(this).data("prop");
            var box = {
                options: prop.options,
                optionsText: prop.optionsText,
                other: prop.other,
                rectP: prop.rectP,
                dt: prop.dt,
                data: prop.data,
                event: prop.event,
                type: prop.type,
                parts: prop.parts,
                filter: prop.filter,
                params: prop.params,
                effect: prop.effect,
                id: prop.id,
                bigType: prop.bigType,
                gmOptions: prop.gmOptions,
                htoptions: prop.htoptions,
                swiper: prop.swiper,
                table: prop.table,
                slides: prop.slides,
                progress:prop.progress,
                triangle:prop.triangle,
                tabpageStyle:prop.tabpageStyle,
                select:prop.select,
                tabpage:getTagPageConfig(prop.id),
            };
            if (prop.type == "swiper"||prop.type == "tabpage") {
                box.slides = [];
                $(this)
                    .find(".box")
                    .each(function () {
                        var slideProp = $(this).data("prop");
                        var slide = {
                            options: slideProp.options,
                            optionsText: slideProp.optionsText,
                            other: slideProp.other,
                            rectP: slideProp.rectP,
                            dt: slideProp.dt,
                            event: slideProp.event,
                            data: slideProp.data,
                            params: slideProp.params,
                            filter: slideProp.filter,
                            type: slideProp.type,
                            effect: slideProp.effect,
                            parts: slideProp.parts,
                            id: slideProp.id,
                            bigType: slideProp.bigType,
                            gmOptions: slideProp.gmOptions,
                            table: slideProp.table,
                            htoptions: slideProp.htoptions,
                            slide: slideProp.slide,
                        };
                        box.slides.push(slide);
                    });
            }
            bdData.boxs.push(box);
        });
    $("#hide-boxs")
        .children(".box-hide")
        .each(function () {
            var prop = $(this).data("prop");
            var style = $(this).data("style");
            var box = {
                options: prop.options,
                optionsText: prop.optionsText,
                other: prop.other,
                rectP: prop.rectP,
                dt: prop.dt,
                data: prop.data,
                event: prop.event,
                type: prop.type,
                parts: prop.parts,
                filter: prop.filter,
                params: prop.params,
                effect: prop.effect,
                id: prop.id,
                bigType: prop.bigType,
                gmOptions: prop.gmOptions,
                swiper: prop.swiper,
                slides: prop.slides,
                style: style,
            };
            bdData.hideboxs.push(box);
        });
    bdData.ruler = {
        v: [],
        h: [],
    };
    $(".zxxRefLine_v").each(function () {
        bdData.ruler.v.push(this.offsetLeft);
    });
    $(".zxxRefLine_h").each(function () {
        bdData.ruler.h.push(this.offsetTop);
    });
    // console.log(bdData);
    return bdData;
}
function savebd(callback) {
    var bdData = getDesBddpData();
   
    layx.confirm(
        "保存提示",
        "是否生成缩略图？<br>生成缩略图比较耗内存，容易造成浏览器卡死，<br>等待一会儿即可，建议不要频繁生成",
        null,
        {
            skin: "asphalt",
            height: 170,
            buttons: [
                {
                    label: "是",
                    callback: function (id, button, event) {
                        layx.load("loadId", "正在生成缩略图，请稍后");
                        $("#content").css("transform", "scale(1)");
                        $.pageRulerHide();
                        var html2canvasname = bdData.content.id;
                        html2canvas($("#content")[0], {
                            allowTaint: true,
                            taintTest: false,
                        }).then(function (canvas) {
                            layx.destroy("loadId");
                            try {
                                var thumbnail = canvas.toDataURL("image/jpeg", 0.4);
                                $("#content").css(
                                    "transform",
                                    "scale(" + $("#content").data("zoom") + ")"
                                );
                                saveBddpBgi(html2canvasname, thumbnail, function (res) {
                                    if (res.code == 0) {
                                        layx.msg("缩略图生成成功 ", {
                                            dialogIcon: "success",
                                        });
                                    } else {
                                        layx.msg("缩略图生成失败", {
                                            dialogIcon: "warn",
                                        });
                                    }
                                });
                            } catch (error) {
                                layx.msg("缩略图生成失败,请检查是否有图片引用存在跨域问题", {
                                    dialogIcon: "warn",
                                });
                            }
                        });
                        saveBddpData(bdData, function (res) {
                            if (res.code == 0) {
                                layx.msg("保存成功", {
                                    dialogIcon: "success",
                                });
                            } else {
                                layx.msg("保存失败", {
                                    dialogIcon: "error",
                                });
                            }
                        });
                        layx.destroy(id);
                    },
                },
                {
                    label: "否",
                    callback: function (id, button, event) {
                        saveBddpData(bdData, function (res) {
                            if (res.code == 0) {
                                layx.msg("保存成功", {
                                    dialogIcon: "success",
                                });
                            } else {
                                layx.msg("保存失败", {
                                    dialogIcon: "error",
                                });
                            }
                        });
                        layx.destroy(id);
                    },
                },
            ],
        }
    );
}

function initRightnav() {
    $("#rightnav").tabs({
            activate: function (event, ui) {
                // console.log(ui);
                if ($("#rightnav").hasClass("on")) {
                } else {
                    $("#rightnav").addClass("on");
                }
                // getProp(currBox,true);
            },
        })
        .addClass("ui-tabs-vertical ui-helper-clearfix");
    $("#rightnav li").removeClass("ui-corner-top").addClass("ui-corner-left");
    // $(".mcuScroll").mCustomScrollbar({advanced:{
    // updateOnContentResize:Boolean }});
}

function initDataConfig() {
    var datalink = $("#datalink");
    var datalinkBtn = $("#datalinkBtn");
    $("#data-box").find("input[type=radio]").checkboxradio({
        icon: false,
    });
    $("#data-box [name='dataFromRadio']").on("change", function (e) {
        var prop = $(".box-selected").data("prop");
        $("#dataSetsRow").hide();
        $("#server-classify").hide();
        if ($(e.target).val() == 1) {
            $("#data-show").JSONView(globalDataBase, {
                collapsed: true,
            });
            $("#data-show").find(".prop").draggable({
                scroll: false,
                helper: "clone",
            });
            $("#datalinkRow").hide();
        } else if ($(e.target).val() == 2) {
            $("#datalinkRow").show();
            $("#selectJSONFile").hide();
        } else if ($(e.target).val() == 3) {
            $("#datalinkRow").show();
            $("#selectJSONFile").show();
        } else if ($(e.target).val() == 4) {
            $("#datalinkRow").hide();
            $("#selectJSONFile").hide();
            $("#dataSetsRow").show();
            // $("#server-classify").show();
        }else if ($(e.target).val() == 5) {
            $("#datalinkRow").hide();
            $("#selectJSONFile").hide();
            // $("#server-classify").show();
        }
        prop.other = prop.other || {};
        prop.other.dataFrom = $(e.target).val();
    });
    datalinkBtn.bind("click", function () {
        var linkUrl = datalink.val();
        var prop = $(".box-selected").data("prop");
        var other = prop.other;
        var params = prop.params;
        getJSONData(other.dataFrom, linkUrl, params, function (res) {
            $("#data-show").JSONView(res, {
                collapsed: true,
            });
            $("#data-show").find(".prop").draggable({
                scroll: false,
                helper: "clone",
            });
        });
    });
    $("#datalinkSaveBtn").bind("click", function () {
        var dataFormVal = $("#data-box [name='dataFromRadio']:checked").val();
        var dataSortVal = $("#data-box [name='dataSortRadio']:checked").val();
        if ($(".box-selected").length > 0) {
            var data = {};
            data.dimension = [];
            data.series = [];
            data.classify = [];
            $(".data-dimension > .data-item").each(function () {
                data.dimension.push({
                    keyname: $(this).data("keyname"),
                    displayname: $(this).data("displayname"),
                });
            });
            $(".data-series > .data-item").each(function () {
                data.series.push({
                    keyname: $(this).data("keyname"),
                    displayname: $(this).data("displayname"),
                });
            });
            $(".data-classify > .data-item").each(function () {
                data.classify.push({
                    keyname: $(this).data("keyname"),
                    displayname: $(this).data("displayname"),
                });
            });
            var prop = $(".box-selected").data("prop");
            if (dataFormVal == 4) {
                data.link =
                    getConfigProp("rdpserver") + "ex/ser/dataset/sqlgroupresult?";
                var columns = data.series
                    .map(function (obj) {
                        return obj.keyname.split("data.")[1];
                    })
                    .join(",");
                var groups = data.dimension
                    .map(function (obj) {
                        return obj.keyname.split("data.")[1];
                    })
                    .join(",");
                var series = data.classify
                    .map(function (obj) {
                        return obj.keyname.split("data.")[1];
                    })
                    .join(",");
                var dtId = $("#dataSetsSelect").select2("data")[0].id;
                data.dtId = dtId;
                data.link += "columns=" + columns;
                data.link += "&groups=" + groups;
                data.link += "&series=" + series;
                data.link += "&dtId=" + dtId;
                data.link += "&type=" + prop.type;
            }else {
                data.link = $("#datalink").val();
            }
            data.sort = dataSortVal;
            prop.data = data;
            initTagData($(".box-selected"));
        }
    });
    $(".data-dimension,.data-series,.data-classify").droppable({
        accept: function (ui) {
            // console.log(ui);
            return true;
        },
        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        drop: function (event, ui) {
            // console.log(ui);
            var level = ui.draggable.data("level");
            var key = ui.draggable.data("key");

            var keyName = getJSONNodeName(level, key, ui.draggable);
            var tempHtml = $(
                '<div class="data-item" title="' +
                keyName +
                '" data-keyname="' +
                keyName +
                '" data-displayname="' +
                keyName +
                '"><span class="desc">' +
                keyName +
                '</span><i class="fa fa-edit "></i><i class="fa fa-close"></i></div>'
            );
            tempHtml.find("i.fa-edit").bind("click", function () {
                var input = $('<input type="text" class="data-item-input" />');
                $(this).parent().append(input);
                input.bind("blur", function () {
                    $(this).parent().attr("data-displayname", $(this).val());
                    $(this).parent().find(".desc").html($(this).val());
                    $(this).unbind().remove();
                });
            });
            tempHtml.find("i.fa-close").bind("click", function () {
                $(this).unbind().parent().remove();
            });
            $(this).append(tempHtml);
        },
    });

    reqServerController("ex/ser/dataset/list", {}, function (res) {
        console.log(res);
        var list = res.list;
        if (list) {
            var items = [];
            $.each(list, function (i,obj) {
                items.push({
                    text : obj.dtName,
                    id : obj.dtId
                })
            })
            $("#dataSetsSelect")
                .select2({
                    placeholder: "请选择数据源",
                    containerCssClass: "bd-select2-container",
                    dropdownCssClass: "bd-select2-dropdown",
                    allowClear: true,
                    data: items,
                })
                .val(null)
                .trigger("change")
                .on("select2:select", function (e) {
                    var data = e.params.data;
                    if (!data.dtId) {
                        data.dtId = data.id;
                    }
                    getDataSetsData(data);
                });
        }
    });

    $("#refreshDataSetsListBtn").bind("click", function () {
        reqServerController("ex/ser/dataset/list", {}, function (res) {
            var list = res.list;
            if (list){
                var items = [];
                $.each(list, function (i,obj) {
                    items.push({
                        text : obj.dtName,
                        id : obj.dtId
                    })
                })
                console.log(res)
                $("#dataSetsSelect").select2("destroy")
                $("#dataSetsSelect").empty();
                $("#dataSetsSelect").select2({
                        placeholder: "请选择数据源",
                        containerCssClass: "bd-select2-container",
                        dropdownCssClass: "bd-select2-dropdown",
                        allowClear: true,
                        data:items,
                    })
                    .val(null)
                    .trigger("change")
                    // .on("select2:select", function (e) {
                    //     var data = e.params.data;
                    //     if (!data.dtId) {
                    //         data.dtId = data.id;
                    //     }
                    //     getDataSetsData(data);
                    // });
            }
        });
    });

}

function initTagData(elem) {
    var prop = elem.data("prop");
    var data = prop.data;
    var params = prop.params;
    var other = prop.other;
    if (data) {
        if (other&&other.dataFrom == 1) {
            createTagsBox(prop, globalDataBase, elem.parent());
            elem.remove();
            getProp(currBox, true);
            currBox.addClass("box-selected");
            layx.destroy("initTagData-layx");
        } else {
            if (data.link) {
                getJSONData(other.dataFrom, data.link, params, function (res) {
                    createTagsBox(prop, res, elem.parent());
                    elem.remove();
                    getProp(currBox, true);
                    currBox.addClass("box-selected");
                });
            } else {
                layx.msg("连接地址不能为空，或当前组件不支持配置数据！", {dialogIcon: "warn"});
            }
        }
    }
}

function getJSONData(type, url, params, callback) {
    var urlParams = getParamsStr(params);
    layx.load("initTagData-layx", "数据正在加载中，请稍后");
    if (type == 2) {
        if (url.indexOf("?") > -1) {
            url = url + "&" + urlParams;
        } else {
            url = url + "?" + urlParams;
        }
        $.ajax({
            url: "../../bddpshow/getJSONDataByUrl",
            type: "post",
            data: {
                url: url,
            },
            // timeout: 3000,
            dataType: "json",
            success: function (res, status) {
                // console.log(res);
                if (res.code == 0) {
                    layx.destroy("initTagData-layx");
                    callback.call(this, res);
                } else {
                    layx.msg(res.msg, {
                        dialogIcon: "error",
                    });
                }
            },
            complete: function (err, status) {
                layx.destroy("initTagData-layx");
                //   console.log(err);
            },
            error: function (xhr, status, error) {
                layx.msg("数据加载失败", {
                    dialogIcon: "error",
                });
            },
        });
    } else if (type == 3) {
        $.ajax({
            url: url,
            type: "get",
            timeout: 3000,
            dataType: "json",
            success: function (res, status) {
                layx.destroy("initTagData-layx");
                callback.call(this, res);
            },
            complete: function (err, status) {
                layx.destroy("initTagData-layx");
            },
            error: function (xhr, status, error) {
                layx.msg("数据加载失败", {
                    dialogIcon: "error",
                });
            },
        });
    } else if (type == 4||type == 5) {
        $.ajax({
            url: url,
            type: "post",
            data: {
                params: JSON.stringify(params),
            },
            timeout: 3000,
            dataType: "json",
            success: function (res, status) {
                if (res.code == 0) {
                    layx.destroy("initTagData-layx");
                    callback.call(this, res);
                } else {
                    layx.msg(res.msg, {
                        dialogIcon: "error",
                    });
                }
            },
            complete: function (err, status) {
                layx.destroy("initTagData-layx");
                console.log(err);
            },
            error: function (xhr, status, error) {
                layx.msg("数据加载失败", {
                    dialogIcon: "error",
                });
            },
        });
    }
}

/**
 * 序列化对象
 * @param {*} params
 */
function getParamsStr(params) {
    if (params) {
        return $.param(params);
    }
    return "";
}

function getJSONNodeName(level, key, elem) {
    if (level > 0) {
        var p;
        if (elem.closest(".array.level" + --level).length > 0) {
            p = elem.closest(".array.level" + level).siblings(".prop");
        } else {
            p = elem.closest(".obj").siblings(".prop");
        }
        if (p.length > 0) {
            key = p.data("key") + "." + key;
        }
        return getJSONNodeName(level, key, p);
    } else {
        return key;
    }
}

function getDataSetsData(data) {
    reqServerControllerParms(
        "ex/ser/dataset/result",
        {
            dtId: data.dtId,
        },
        function (res) {
            console.log(res);
            if (res.code == 0) {
                dataHandler(res);
            }
        }
    );
}


//数据处理
function dataHandler(res) {
    var type = currBox.data("prop").type;
    var tableData = {};
    if (type == "table") {
        tableData = tableColsValsToNodeData(res.data);
    } else {
        tableData = tableValsToNodeData(res.data);
    }
    $("#data-show").JSONView(
        {
            data: tableData,
        },
        {
            collapsed: true,
        }
    );
    $("#data-show").find(".prop").draggable({
        scroll: false,
        helper: "clone",
    });
}

function initDataBaseConfig() {
    $("#tools-database").bind("click", function () {
        var database = $(".database-content");
        if (database.hasClass("on")) {
            database.removeClass("on");
        } else {
            database.addClass("on");
        }
    });
    $(".database-content").find("input[type=radio]").checkboxradio({
        icon: false,
    });
    $("input[name=globalDataFrom]").bind("click", function () {
        $(".globalDataFromDiv").hide();
        $("#globalDataFromDiv-" + $(this).val()).show();
        if ($(this).val() == 1) {
        } else {
            $("#globalDataFromDiv-1").show();
        }
        $("#content").data("dataFrom", $(this).val());
    });
    $("#databaseBtn").bind("click", function () {
        $(".database-content").removeClass("on");
    });
    $("#getGlobalDataBtn").bind("click", function () {
        var linkUrl = $("#globalDataUrl").val();
        layx.load("initGlobalData-layx", "数据正在加载中，请稍后");
        var type = $("input[name='globalDataFrom']:checked").val();
        if (type == "1") {
            $.ajax({
                url: "../../bddpshow/getJSONDataByUrl",
                type: "post",
                data: {
                    url: linkUrl,
                },
                // timeout: 3000,
                dataType: "json",
                success: function (res, status) {
                    $("#globalJSONShow").JSONView(res, {
                        collapsed: true,
                    });
                    globalDataBase = res;
                    $("#content").data("url", linkUrl);
                    $("#content").data("data", res);
                },
                complete: function (err, status) {
                    layx.destroy("initGlobalData-layx");
                    //   console.log(err);
                },
                error: function (xhr, status, error) {
                    layx.msg("数据加载失败", {
                        dialogIcon: "error",
                    });
                },
            });
        } else {
            $.ajax({
                url: linkUrl,
                type: "get",
                dataType: "json",
                timeout: 3000,
                success: function (data, status) {
                    // console.log(data);
                    $("#globalJSONShow").JSONView(data, {
                        collapsed: true,
                    });
                    globalDataBase = data;
                    $("#content").data("url", linkUrl);
                    $("#content").data("data", data);
                },
                complete: function (err, status) {
                    console.log(err);
                    layx.destroy("initGlobalData-layx");
                },
                error: function (xhr, status, error) {
                    layx.msg("数据加载失败", {
                        dialogIcon: "error",
                    });
                },
            });
        }
    });
}

function initGlobalConfig() {
    $("#design-config").bind("click", function () {
        var globalConfigDiv = $(".global-config");
        if (globalConfigDiv.hasClass("on")) {
            globalConfigDiv.removeClass("on");
        } else {
            globalConfigDiv.addClass("on");
        }
    });
    $("#globalConfigBtn").bind("click", function () {
        $(".global-config").removeClass("on");
    });
    var globalConfigInputs = $(".global-config").inputs();
    var configFile = getConfigAllProp();
    if (!configFile.jarpath) {
        configFile.jarpath = path.join(__dirname, "../report-admin.jar");
    }
    globalConfigInputs.set(configFile);
    $("#jarpath").bind("click", function () {
        dialog.showOpenDialog(
            {
                title: "选择JAR文件",
                buttonLabel: "打开文件",
                properties: ["openFile "],
                filters: [
                    {
                        name: "JAR",
                        extensions: ["jar"],
                    },
                ],
            },
            function (filePaths) {
                if (filePaths) {
                    globalConfigInputs.set({
                        jarpath: filePaths[0],
                    });
                }
            }
        );
    });
    $("#saveGlobalConfigBtn").bind("click", function () {
        console.log(globalConfigInputs.data());
        savefile("./config.json", globalConfigInputs.data());
        layx.msg("保存成功", {
            dialogIcon: "success",
        });
    });
}

function initAceEditer() {
    jsonExtentEditor = ace.edit("senior-panel");
    // jsonExtentEditor.setValue(dataSet.sql);

    $("#jsoneditorSaveBtn").bind("click", function () {
        var prop = currBox.data("prop");
        var options = eval("(" + jsonExtentEditor.getValue().replace(/\\"/g, '"')  + ")");
        var optionsText = jsonExtentEditor.getValue().replace(/\\"/g, '"') ;
        var myChart = prop.myChart;
        if (myChart) {
            myChart.setOption(options);
        }
        prop.options = options;
        prop.optionsText = optionsText;
        currBox.data("prop", prop);
        undoRecord();
    });
}

function initSeniorConfig() {
}

function initSceneConfig() {
    var content = $("#content");
    var scene = $("#scene");
    content.data("id", guid());
    // $("#scene").find('input[type=radio]').checkboxradio({
    //   icon: false
    // });

    $(".bgimglist li").bind("click", function () {
        if ($(this).hasClass("on")) {
            content.css("background-image", "");
            content.data("backgroundImage", "");
            $(this).removeClass("on");
        } else {
            $(".bgimglist li").removeClass("on");
            $(this).addClass("on");
            content.css("background-image", this.style.backgroundImage);
            content.data("backgroundImage", this.style.backgroundImage);
            $("#bdbgPath").val(this.style.backgroundImage);
        }
    });
    $(".layout-Content").on("mousewheel", function (event) {
        var val = Math.abs(event.deltaFactor * event.deltaY) / 1000;
        var zoom = content.data("zoom") || 1;
        if (event.deltaY > 0) {
            // 放大
            var temp = Number(zoom) + val;
            temp = temp > 5 ? 5 : temp;
            temp = temp.toFixed(1);
            content.css("transform", "scale(" + temp + ")");
            content.data("zoom", temp);
        } else {
            var temp = Number(zoom) - val;
            temp = temp < 0.2 ? 0.2 : temp;
            temp = temp.toFixed(1);
            content.css("transform", "scale(" + temp + ")");
            content.data("zoom", temp);
        }
    });
    content.draggable({
        handle: "#contentHandle",
        scroll: false,
        snap: ".layout-Content",
        snapMode: "inner",
    });
    $("#sceneBtn").bind("click", function () {
        var sitemap = $("#scene");
        sitemap.removeClass("on");
    });
    $(document).bind("keydown", function (e) {
        if (e.keyCode == 32) {
            $("#contentHandle").addClass("on");
        }
    });
    $(document).bind("keyup", function (e) {
        if (e.keyCode == 32) {
            $("#contentHandle").removeClass("on");
        }
    });

    $("#sceneW").bind("change", function () {
        content.css("width", $(this).val() + "px");
        content.data("width", $(this).val());
    });
    $("#sceneH").bind("change", function () {
        content.css("height", $(this).val() + "px");
        content.data("height", $(this).val());
    });
    $("input[name=sceneSizeRadio]").bind("click", function () {
        var size = $(this).val().split("*");
        content.css("width", size[0] + "px");
        content.data("width", size[0]);
        content.css("height", size[1] + "px");
        content.data("height", size[1]);
        $("#sceneW").val(size[0]);
        $("#sceneH").val(size[1]);
        resizeContent(size[0], size[1]);
    });
    $("input[name=globalTheme]").bind("click", function () {
        globalChartTheme = $(this).val();
    });
    $("input[name=globalZoom]").bind("click", function () {
        content.data("globalZoom", $(this).val());
    });
    $("#sceneName").bind("change blur", function () {
        content.data("sceneName", $(this).val());
    });
    $("input[name=filterWayRadio]").bind("click", function () {
        content.data("filterWay", $(this).val());
    });
    $(".scene-addBgBtn").bind("click", function () {
        getImagePath($("#bdbgPath"));
    });
    $(".scene-selectBgBtn").bind("click", function () {
        selectImagePath($("#bdbgPath"));
    });
    $("#bdbgPath").bind("change blur", function () {
        var strurl = this.value;
        strurl = strurl.replace(/\\/g, "/");
        content.css("background-image", "url('" + strurl + "')");
        content.data("backgroundImage", strurl);
        this.value = strurl;
    });
}

// 重置场景位置

function resizeContent(w, h) {
    var content = document.getElementById("content");

    var pw = $(content).parent()[0].offsetWidth;
    var ph = $(content).parent()[0].clientHeight;
    var cw = w || content.offsetWidth;
    var ch = h || content.offsetHeight;
    var wp = pw / cw;
    var hp = ph / ch;

    var zoom = 1;

    if (wp < hp) {
        zoom = wp;
        content.style.setProperty("margin-left", -((cw - cw * zoom) / 2) + "px");
        content.style.setProperty(
            "margin-top",
            (ph - ch * zoom) / 2 - (ch - ch * zoom) / 2 + "px"
        );
    } else {
        zoom = hp;
        content.style.setProperty("margin-top", -((ch - ch * zoom) / 2) + "px");
        content.style.setProperty(
            "margin-left",
            (pw - cw * zoom) / 2 - (cw - cw * zoom) / 2 + "px"
        );
    }
    content.style.setProperty("top", "0px");
    content.style.setProperty("left", "0px");
    content.style.setProperty("transform", "scale(" + zoom + ")");
    $(content).data("zoom", zoom);
    // content.style.setProperty('transform-origin', "left top 0");
}

function initTabsTags() {
    // var res = executeSQL("SELECT * FROM tags");
    // var res = db.exec("SELECT * FROM sysinfo");
    // var nodes = tableValsToNode(res);
    // var node = nodes[0];
    // if (node.initSts == 0) {
    // introJs().start().oncomplete(function () {}).onexit(function () {
    // $("#rightnav").removeClass("on");
    // db.run("UPDATE sysinfo SET initSts=1");
    // var data = db.export();
    // var buffer = new Buffer(data);
    // fs.writeFileSync(path.join(__dirname, './static/bddp.db'), buffer);
    // console.log("save");
    // });
    // $("#rightnav").addClass("on");
    // }
}

function getProp(elem, flag) {
    // console.log("getProp");
    if (currBox && currBox.is(elem) && !flag) {
    } else {
        if (!elem) {
            return false;
        }
        $(".sp-container").remove();
        currBox = elem;
        // console.time("getProp");
        var prop = elem.data("prop");
        var type = prop.type;
        var data = prop.data;
        var other = prop.other || {};
        changeDataInput(type, other.dataFrom);
        var result = $.getCache("tag-" + type);
        if (!result) {
            result = executeSQLAsObject(
                "SELECT * FROM tags WHERE tagName='" + type + "'"
            );
            $.setCache("tag-" + type, result);
        }

        // var res = db.exec("SELECT pId,id,name,keyName,tagprop.tagId FROM
        // propitems,tagprop where propitems.type ISNULL and tagprop.propId =
        // propitems.id AND tagprop.tagId =" + result.id);

        var res = $.getCache(result.id);
        if (!res) {
            res = executeSQL(
                "SELECT pId,id,name,keyName,tagprop.tagId FROM	propitems,tagprop where propitems.type ISNULL and tagprop.propId = propitems.id AND tagprop.tagId =" +
                result.id
            );
            $.setCache(result.id, res);
        }

        var zNodes = tableValsToNode(res);
        zNodes = transformTozTreeFormat(zNodes);
        bulidTabs($("#config-panel"), zNodes, 1, prop);
        bulidParams(prop);
        // console.timeEnd("getProp");

        $(".data-dimension").empty();
        $(".data-series").empty();
        $(".data-classify").empty();

        $("#datalink").val("");
        $("#data-show").JSONView(
            {},
            {
                collapsed: true,
            }
        );
        $("#data-show").find(".prop").draggable({
            scroll: false,
            helper: "clone",
        });

        $("#data-box [name='dataFromRadio']").removeProp("checked");
        $("#data-box #dataFromRadio-" + other.dataFrom).prop("checked", true);
        if (data) {
            $("#data-box #dataSortRadio-" + data.sort).prop("checked", true);
        }
        if (other.dataFrom) {
            $("#dataSetsRow").hide();
            if (other.dataFrom == 1) {
                $("#datalinkRow").hide();
                if (globalDataBase) {
                    $("#data-show").JSONView(globalDataBase, {
                        collapsed: true,
                    });
                    $("#data-show").find(".prop").draggable({
                        scroll: false,
                        helper: "clone",
                    });
                }
            } else if (other.dataFrom == 2) {
                $("#datalinkRow").show();
                $("#selectJSONFile").hide();
            } else if (other.dataFrom == 3) {
                $("#datalinkRow").show();
                $("#selectJSONFile").show();
            } else if (other.dataFrom == 4) {
                $("#datalinkRow").hide();
                $("#selectJSONFile").hide();
                $("#dataSetsRow").show();
            }else if (other.dataFrom == 5) {
                $("#datalinkRow").hide();
                $("#selectJSONFile").hide();
            }
        } else {
            $("#datalinkRow").hide();
            if (globalDataBase) {
                $("#data-show").JSONView(globalDataBase, {
                    collapsed: true,
                });
                $("#data-show").find(".prop").draggable({
                    scroll: false,
                    helper: "clone",
                });
            }
            other.dataFrom = 1;
        }
        $("#data-box").find("input[type=radio]").checkboxradio({
            icon: false,
        });

        if (data) {
            var link = data.link || "";
            $("#datalink").val(link);
            var dimension = data.dimension;
            $.each(dimension, function (i, dm) {
                var tempHtml = $(
                    '<div class="data-item" title="' +
                    dm.keyname +
                    '" data-keyname="' +
                    dm.keyname +
                    '" data-displayname="' +
                    dm.displayname +
                    '"><span class="desc">' +
                    dm.displayname +
                    '</span><i class="fa fa-edit "></i><i class="fa fa-close"></i></div>'
                );
                tempHtml.find("i.fa-edit").bind("click", function () {
                    var input = $('<input type="text" class="data-item-input" />');
                    $(this).parent().append(input);
                    input.bind("blur", function () {
                        $(this).parent().attr("data-displayname", $(this).val());
                        $(this).parent().find(".desc").html($(this).val());
                        $(this).unbind().remove();
                    });
                });
                tempHtml.find("i.fa-close").bind("click", function () {
                    $(this).unbind().parent().remove();
                });
                $(".data-dimension").append(tempHtml);
            });
            var series = data.series;
            $.each(series, function (i, dm) {
                var tempHtml = $(
                    '<div class="data-item" title="' +
                    dm.keyname +
                    '" data-keyname="' +
                    dm.keyname +
                    '" data-displayname="' +
                    dm.displayname +
                    '"><span class="desc">' +
                    dm.displayname +
                    '</span><i class="fa fa-edit "></i><i class="fa fa-close"></i></div>'
                );
                tempHtml.find("i.fa-edit").bind("click", function () {
                    $(this).parent().find(".data-item-input").remove();
                    var input = $('<input type="text" class="data-item-input" />');
                    $(this).parent().append(input);
                    input.bind("blur", function () {
                        $(this).parent().attr("data-displayname", $(this).val());
                        $(this).parent().find(".desc").html($(this).val());
                        $(this).unbind().remove();
                    });
                });
                tempHtml.find("i.fa-close").bind("click", function () {
                    $(this).unbind().parent().remove();
                });
                $(".data-series").append(tempHtml);
            });
            var classify = data.classify || {};
            $.each(classify, function (i, dm) {
                var tempHtml = $(
                    '<div class="data-item" title="' +
                    dm.keyname +
                    '" data-keyname="' +
                    dm.keyname +
                    '" data-displayname="' +
                    dm.displayname +
                    '"><span class="desc">' +
                    dm.displayname +
                    '</span><i class="fa fa-edit "></i><i class="fa fa-close"></i></div>'
                );
                tempHtml.find("i.fa-edit").bind("click", function () {
                    var input = $('<input type="text" class="data-item-input" />');
                    $(this).parent().append(input);
                    input.bind("blur", function () {
                        $(this).parent().attr("data-displayname", $(this).val());
                        $(this).parent().find(".desc").html($(this).val());
                        $(this).unbind().remove();
                    });
                });
                tempHtml.find("i.fa-close").bind("click", function () {
                    $(this).unbind().parent().remove();
                });
                $(".data-classify").append(tempHtml);
            });
            if (other.dataFrom == 4&&data.dtId) {
                $("#dataSetsSelect").select2().val([data.dtId]).trigger("change");
                getDataSetsData(data);
            }
        }
        // jsoneditor.set(prop.options);
        if (prop.optionsText) {
            jsonExtentEditor.setValue(prop.optionsText.replace(/\\"/g, '"'));
        } else if (prop.options) {
            jsonExtentEditor.setValue(JSON.stringify(prop.options, null, 4));
        }
    }
}

function changeDataInput(type, dataFrom) {
    var dimension = $("#server-dimension").hide();
    var series = $("#server-series").hide();
    var classify = $("#server-classify").hide();
    var dataset = $(".datafrom-dataset").show();
    if (type == "line" || type == "bar") {
        dimension.find(".lead").html("X轴");
        series.find(".lead").html("Y轴");
        dimension.show();
        series.show();
    } else if (type == "map") {
        dimension.find(".lead").html("数据点");
        series.find(".lead").html("波纹数据点");
        classify.find(".lead").html("区域");
        dimension.show();
        series.show();
        classify.show();
    } else if (type == "pie" || type == "radar") {
        dimension.find(".lead").html("标签名称");
        series.find(".lead").html("值");
        dimension.show();
        series.show();
    } else if (type == "gauge" || type == "scatter") {
        series.find(".lead").html("值");
        dataset.hide();
        series.show();
    } else if (type == "k") {
        dataset.hide();
        series.show();
        dimension.show();
    } else if (type == "table") {
        dimension.find(".lead").html("表头数据");
        series.find(".lead").html("列表数据");
        dimension.show();
        series.show();
    } else if (type == "text"||type == "progress") {
        dimension.find(".lead").html("值");
        dimension.show();
    }else if (type == "graph"||type=="sankey") {
        series.find(".lead").html("节点");
        dimension.find(".lead").html("连线");
        series.show();
        dimension.show();
        dataset.hide();
    } else if (type == "boxplot") {
        dimension.find(".lead").html("X轴");
        series.find(".lead").html("Y轴");
        series.show();
        dimension.show();
        dataset.hide();
    } else if (type == "parallel") {
        dimension.find(".lead").html("维度");
        series.find(".lead").html("数据项");
        series.show();
        dimension.show();
        dataset.hide();
    }else if (type == "heatmap") {
        dimension.find(".lead").html("X轴");
        series.find(".lead").html("Y轴");
        classify.find(".lead").html("数据");
        series.show();
        dimension.show();
        classify.show();
        dataset.hide();
    } else {
        dimension.find(".lead").html("维度");
        series.find(".lead").html("系列");
        dimension.show();
        series.show();
    }
    // if (dataFrom == 4) {

    //   classify.show();
    // } else {
    //
// function bulidConfigBox(zNodes){
}

// }

// $('#config-box').append(box,1);
// }
function bulidTabs(elem, data, lv, prop) {
    elem.find(".colorPicker").spectrum("destroy");
    elem.empty();
    var box = $('<div class="box-' + lv + '"></div>');
    var ul = $("<ul></ul>");
    box.append(ul);
    $.each(data, function (i, node) {
        if (node.keyName == "series") {
            var data = prop.data;
            var options = prop.options;
            if (data && options.series) {
                $.each(options.series, function (j, sub) {
                    var li = $(
                        ' <li><a href="#' +
                        node.keyName +
                        "-" +
                        j +
                        '">' +
                        node.name +
                        j +
                        "</a></li>"
                    );
                    li.data("node", node);
                    li.data("lv", lv);
                    ul.append(li);
                    var div = $(
                        '<div data-keyname="' +
                        node.keyName +
                        '" data-index="' +
                        j +
                        '" id="' +
                        node.keyName +
                        "-" +
                        j +
                        '"></div>'
                    );
                    if (node.children && node.children.length > 0) {
                        bulidTabs(div, node.children, lv + 1, prop);
                    }
                    box.append(div);
                });
            }
        } else {
            var li = $(
                ' <li><a href="#' + node.keyName + '">' + node.name + "</a></li>"
            );
            li.data("node", node);
            li.data("lv", lv);
            ul.append(li);
            var div = $(
                '<div data-keyname="' +
                node.keyName +
                '"  id="' +
                node.keyName +
                '"></div>'
            );
            if (node.children && node.children.length > 0) {
                bulidTabs(div, node.children, lv + 1, prop);
            }
            box.append(div);
        }
    });
    elem.append(box);
    box
        .tabs({
            activate: function (event, ui) {
                if (ui.newTab.data("lv") == 2) {
                    var node = ui.newTab.data("node");
                    bulidProp(ui.newPanel, node.id, node.tagId);
					ui.newPanel.show().siblings(".ui-tabs-panel").hide();
                }
            },
            beforeActivate: function (event, ui) {
            },
            beforeLoad: function (event, ui) {
            },
            create: function (event, ui) {
                if (ui.tab.data("lv") == 2) {
                    var node = ui.tab.data("node");
                    bulidProp(ui.panel, node.id, node.tagId);
                }
            },
            load: function (event, ui) {
            },
        })
        .addClass("ui-tabs-vertical ui-helper-clearfix ui-tabs-lv" + lv);
    box.removeClass("ui-corner-top").addClass("ui-corner-left");
}

function bulidProp(elem, propId, tagId) {
    // console.time("bulidProp");
    var res = $.getCache(propId + "-" + tagId);
    if (!res) {
        var sqlStr =
            "select propitems.*,a.tagId FROM propitems LEFT JOIN (SELECT * FROM tagprop WHERE tagId=" +
            tagId +
            ") a ON a.propId =propitems.id WHERE a.tagId NOTNULL and propitems.pId = '" +
            propId +
            "'" +
            " ORDER BY sqen";
        res = executeSQL(sqlStr);
        $.setCache(propId + "-" + tagId, res);
    }
    var zNodes = tableValsToNode(res);
    // console.log(zNodes);
    if (zNodes) {
        elem.initProp({
            data: zNodes,
        });
    }
    // console.timeEnd("bulidProp");
}

function getSceneConfig() {
    var content = $("#content");
    var colorPicker = $("#scenebgcolor");
    colorPicker.spectrum("destroy");
    colorPicker.spectrum({
        // color: tinycolor,
        showPalette: true,
        // maxPaletteSize: 10,
        showInput: true,
        allowEmpty: true,
        showAlpha: true,
        showSelectionPalette: true,
        showInitial: true,
        preferredFormat: "rgb",
        palette: [
            [
                "#000000",
                "#434343",
                "#666666",
                "#999999",
                "#b7b7b7",
                "#cccccc",
                "#d9d9d9",
                "#efefef",
                "#f3f3f3",
                "#ffffff",
            ],
            [
                "#980000",
                "#ff0000",
                "#ff9900",
                "#ffff00",
                "#00ff00",
                "#00ffff",
                "#4a86e8",
                "#0000ff",
                "#9900ff",
                "#ff00ff",
            ],
            [
                "#e6b8af",
                "#f4cccc",
                "#fce5cd",
                "#fff2cc",
                "#d9ead3",
                "#d9ead3",
                "#c9daf8",
                "#cfe2f3",
                "#d9d2e9",
                "#ead1dc",
            ],
            [
                "#dd7e6b",
                "#ea9999",
                "#f9cb9c",
                "#ffe599",
                "#b6d7a8",
                "#a2c4c9",
                "#a4c2f4",
                "#9fc5e8",
                "#b4a7d6",
                "#d5a6bd",
            ],
            [
                "#cc4125",
                "#e06666",
                "#f6b26b",
                "#ffd966",
                "#93c47d",
                "#76a5af",
                "#6d9eeb",
                "#6fa8dc",
                "#8e7cc3",
                "#c27ba0",
            ],
            [
                "#a61c00",
                "#cc0000",
                "#e69138",
                "#f1c232",
                "#6aa84f",
                "#45818e",
                "#3c78d8",
                "#3d85c6",
                "#674ea7",
                "#a64d79",
            ],
            [
                "#85200c",
                "#990000",
                "#b45f06",
                "#bf9000",
                "#38761d",
                "#134f5c",
                "#1155cc",
                "#0b5394",
                "#351c75",
                "#741b47",
            ],
            [
                "#5b0f00",
                "#660000",
                "#783f04",
                "#7f6000",
                "#274e13",
                "#0c343d",
                "#1c4587",
                "#073763",
                "#20124d",
                "#4c1130",
            ],
        ],

        cancelText: "取消",
        chooseText: "确认",
        change: function (color) {
        },
        show: function (color) {
        },
        move: function (color) {
        },
        hide: function (color) {
        },
        choose: function (color) {
            content.css("background-color", color.toRgbString());
            content.data("backgroundColor", color.toRgbString());
        },
    });
    $("#sceneW").val($("#content").width());
    $("#sceneH").val($("#content").height());
    $("#scenebgcolor").spectrum("set", $("#content").css("background-color"));
    $("#sceneName").val($("#content").data("sceneName"));
}

function tableValsToNode(data) {
    if (data && data.length > 0) {
        var zNodes = [];
        var tableData = data[0];
        if (tableData) {
            var columns = tableData.columns;
            var values = tableData.values;
            var len = columns.length;
            $.each(values, function (i, node) {
                var temp = {};
                for (var j = 0; j < len; j++) {
                    temp[columns[j]] = node[j];
                }
                zNodes.push(temp);
            });
            return zNodes;
        }
        return null;
    } else {
        return null;
    }
}

function transformTozTreeFormat(sNodes) {
    var i,
        l,
        key = "id",
        parentKey = "pId";
    if (!key || key == "" || !sNodes) return [];

    if (isArray(sNodes)) {
        var r = [];
        var tmpMap = {};
        for (i = 0, l = sNodes.length; i < l; i++) {
            tmpMap[sNodes[i][key]] = sNodes[i];
        }
        for (i = 0, l = sNodes.length; i < l; i++) {
            var p = tmpMap[sNodes[i][parentKey]];
            if (p && sNodes[i][key] != sNodes[i][parentKey]) {
                var children = nodeChildren(p);
                if (!children) {
                    children = nodeChildren(p, []);
                }
                children.push(sNodes[i]);
            } else {
                r.push(sNodes[i]);
            }
        }
        return r;
    } else {
        return [sNodes];
    }

    function nodeChildren(node, newChildren) {
        if (!node) {
            return null;
        }
        var key = "children";
        if (typeof newChildren !== "undefined") {
            node[key] = newChildren;
        }
        return node[key];
    }

    function isArray(arr) {
        return Object.prototype.toString.apply(arr) === "[object Array]";
    }
}
