function importData(path) {
    getAjaxData("../../bddp/getJSONFoldersContent", {
        path: path
    }, function (result) {
        if (result.code == 0) {
            emptyContent();
            var data = JSON.parse(result.res);
            var contentData = data.content;
            var boxsData = data.boxs;
            var rulersData = data.ruler;
            setContentData(contentData);
            setBoxsData(boxsData);
            setRulers(rulersData);
            $(".file-content").fadeOut(function () {
                resizeContent();
            });
            $(".box").removeClass("box-selected");
            stack.commands = [];
            stack.savePosition = -1;
            stack.stackPosition = -1;
            undoData = $.extend(true, {}, data);
            undoRecord(data);
            currBox = null;
        }
    });
}

function importDataId(id) {
    getAjaxData("../../bddp/getJSONFoldersContentById", {
        id: id
    }, function (result) {
        if (result.code == 0) {
            emptyContent();
            var data = JSON.parse(result.res);
            console.log(data);
            var contentData = data.content;
            var boxsData = data.boxs;
            var hideboxsData = data.hideboxs;
            var rulersData = data.ruler;
            setContentData(contentData);
            setBoxsData(boxsData);
            setHideBoxsData(hideboxsData);
            setRulers(rulersData);
            $(".file-content").fadeOut(function () {
                resizeContent();
            });
            $(".box").removeClass("box-selected");
            stack.commands = [];
            stack.savePosition = -1;
            stack.stackPosition = -1;
            undoData = $.extend(true, {}, data);
            undoRecord(data);
            currBox = null;
        }
    });
}

function copyData(path, sourceId) {
    getAjaxData("../../bddp/getJSONFoldersContentForCopy", {
        path: path,
        sourceId: sourceId,
        id: $("#content").data("id")
    }, function (result) {
        if (result.code == 0) {
            var data = JSON.parse(result.res);
            var boxsData = data.boxs;
            setCopyBoxsData(boxsData);
            $(".file-content").fadeOut(function () {
                resizeContent();
            });
            $(".box").removeClass("box-selected");
            stack.commands = [];
            stack.savePosition = -1;
            stack.stackPosition = -1;
            //	undoData = $.extend(true, {}, data);
            //	undoRecord(data);
            currBox = null;
        }
    });
}

function importDataOld(path) {
    getAjaxData("../../bddp/getJSONFileContent", {
        path: path
    }, function (result) {
        if (result.code == 0) {
            var data = JSON.parse(result.res);
            var contentData = data.content;
            var boxsData = data.boxs;
            var rulersData = data.ruler;
            setContentData(contentData);
            setBoxsData(boxsData);
            setRulers(rulersData);
            $(".file-content").fadeOut(function () {
                resizeContent();
            });
            $(".box").removeClass("box-selected");
            stack.commands = [];
            stack.savePosition = -1;
            stack.stackPosition = -1;
            undoData = $.extend(true, {}, data);
            undoRecord(data);
            currBox = null;
        }
    });
}

function importDataForUndo(data) {
    if (data) {
        var contentData = data.content;
        var boxsData = data.boxs;
        var rulersData = data.ruler;
        setContentData(contentData);
        setBoxsData(boxsData);
        setRulers(rulersData);
        // $(".file-content").fadeOut(function () {
        //     resizeContent();
        // });
        currBox = null;
        $(".box").removeClass("box-selected");
    }
}

function emptyContent() {
    var content = $("#content");
    content.empty();
    content.append('<div id="contentHandle"></div>');
    content.append('<div id="guide-h" class="guide"></div>');
    content.append('<div id="guide-v" class="guide"></div>');
    $("#sceneW").val(1920);
    $("#sceneH").val(1080);
    $("#scenebgcolor").spectrum("set", "");
    $("#sceneName").val("");
    $("#sitemap").empty();
    content.css({
        width: "1920px",
        height: "1080px",
        "background-color": "",
        "background-image": ""
    });
    content.data("width", 1920);
    content.data("height", 1080);
    content.data("backgroundColor", "");
    content.data("backgroundImage", "");
    content.data("sceneName", "");
    content.data("dataFrom", "");
    //content.attr("data-id", guid());
    content.data("id", guid());

    $(".zxxRefLine_v").remove();
    $(".zxxRefLine_h").remove();
    content.data("url", "");
    content.data("data", "");
    globalDataBase = null;
    globalChartTheme = "default";
    resizeContent();
}


function setContentData(data) {
    var content = $("#content");
    content.empty();
    content.append('<div id="contentHandle"></div>');
    content.append('<div id="guide-h" class="guide"></div>');
    content.append('<div id="guide-v" class="guide"></div>');
    $("#sceneW").val(data.width);
    $("#sceneH").val(data.height);
    $("#scenebgcolor").spectrum("set", data.backgroundColor);
    $("#sceneName").val(data.sceneName);
    $("#bdbgPath").val(data.backgroundImage);
    $("#globalDataUrl").val(data.url);
    $(".database-content").find('input[type=radio]').removeProp("checked");
    $("#globalDataFrom-" + data.dataFrom).prop("checked", true);
    $("#filterWayRadio-" + data.filterWay).prop("checked", true);
    $("#globalZoomRadio-" + (data.globalZoom ? data.globalZoom : 0)).prop("checked", true);
    $("#scene").find('input[type=radio]').checkboxradio({
        icon: false
    });
    $(".database-content").find('input[type=radio]').checkboxradio({
        icon: false
    });
    $(".globalDataFromDiv").hide();
    $("#globalDataFromDiv-" + data.dataFrom).show();
    if (data.dataFrom == 2) {

    }
    $("#globalDataFromDiv-1").show();

    var strurl = data.backgroundImage;
    if (strurl) {
        if (strurl.indexOf('url(') > -1) {
        } else {
            strurl = strurl.replace(/\\/g, "\/");
            strurl = "url('" + strurl + "')";
        }
    } else {
        strurl = "";
    }
    content.css({
        width: data.width + "px",
        height: data.height + "px",
        "background-color": data.backgroundColor,
        "background-image": strurl
    });
    content.data("width", data.width);
    content.data("height", data.height);
    content.data("backgroundColor", data.backgroundColor);
    content.data("backgroundImage", data.backgroundImage || false);
    content.data("sceneName", data.sceneName);
    content.data("url", data.url);
    content.data("globalChartTheme", data.globalChartTheme);
    content.data("filterWay", data.filterWay);
    content.data("dataFrom", data.dataFrom);
    content.data("phonepanel", data.phonepanel);
    if (!data.id) {
        data.id = guid();
    }
    //content.attr("data-id", data.id);
    content.data("id", data.id);

    if (data.url) {
        if (data.dataFrom == "1") {
            $.ajax({
                url: "../../bddpshow/getJSONDataByUrl",
                type: "post",
                data: {
                    url: data.url,
                },
                // timeout: 3000,
                dataType: "json",
                success: function (res, status) {
                    $("#globalJSONShow").JSONView(res, {
                        collapsed: true,
                    });
                    globalDataBase = res;
                    $("#content").data("url", data.url);
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
                url: data.url,
                type: "get",
                dataType: "json",
                timeout: 3000,
                success: function (data, status) {
                    // console.log(data);
                    $("#globalJSONShow").JSONView(data, {
                        collapsed: true,
                    });
                    globalDataBase = data;
                    $("#content").data("url", data.url);
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
    } else {
        globalDataBase = null;
        $("#globalJSONShow").JSONView({}, {
            collapsed: true
        });
    }
    if (!data.globalChartTheme) {
        globalChartTheme = data.globalChartTheme;
    } else {
        globalChartTheme = "default";
    }
}

function setBoxsData(data) {
    var minZIndex = 50;
    var maxZIndex = 50;
    $.each(data, function (i, node) {
        createTagsBox(node);
        maxZIndex = node.rectP.zIndex > maxZIndex ? node.rectP.zIndex : maxZIndex;
        minZIndex = node.rectP.zIndex < minZIndex ? node.rectP.zIndex : minZIndex;
    });
    zIndexProp = {
        max: maxZIndex,
        min: minZIndex
    };
}

function setHideBoxsData(data) {
    if (data) {
        var minZIndex = 50;
        var maxZIndex = 50;
        $.each(data, function (i, node) {
            createTagsBox(node);
            moveBoxToHidePanel("box" + node.id);
            maxZIndex = node.rectP.zIndex > maxZIndex ? node.rectP.zIndex : maxZIndex;
            minZIndex = node.rectP.zIndex < minZIndex ? node.rectP.zIndex : minZIndex;
        });
        zIndexProp = {
            max: maxZIndex,
            min: minZIndex
        };
    }

}

function restBoxUUID() {
    $("#content").children(".box").each(function () {
        var prop = $(this).data("prop");
        prop.id = guid();

        if (prop.type == "swiper") {
            $(this).find(".box").each(function () {
                var slideProp = $(this).data("prop");
                slideProp.id = guid();
            });
        }
    })
}

function setCopyBoxsData(data) {
    var minZIndex = 50;
    var maxZIndex = 50;
    $.each(data, function (i, node) {
        node.id = guid();
        createTagsBox(node);
        maxZIndex = node.rectP.zIndex > maxZIndex ? node.rectP.zIndex : maxZIndex;
        minZIndex = node.rectP.zIndex < minZIndex ? node.rectP.zIndex : minZIndex;
    });
    zIndexProp = {
        max: maxZIndex,
        min: minZIndex
    };
}

function setRulers(data) {
    $.pageRuler(data);
    $.pageRulerHide();
}