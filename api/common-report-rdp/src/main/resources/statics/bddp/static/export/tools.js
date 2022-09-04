$(function () {
    $(".tag-filter-searchbtn").bind("click", function () {
        var params = {};
        $(".tag-filter-input").each(function () {
            var val = $(this).val();
            params[$(this).attr("name")] = val;
        })
        $(".tag-filter-select").each(function () {
            var val = $(this).val();
            params[$(this).attr("name")] = val;
        })

        if ($("#content").attr("filterWay") == "1") {
            changeParamsTag(params);
        } else {
            submitParams(params)
        }
    });
    $(".box-hide").each(function () {
        inithideBoxsEvent($(this));
    })
    setFilterTagVal();
    bdButtonsEvent();

    BD_MODE = paramsMap()["BD_MODE"] || 1;
    initWidgetBtns();
})
var REPLACE_ID = "";
var BD_MODE = 1;//大屏幕模式1-鼠标控制，2-触屏控制
var HideTagsType = ["line",
    "bar",
    "pie",
    "radar",
    "tree",
    "treemap",
    "sunburst",
    "funnel",
    "gauge",
    "map",
    "boxplot",
    "heatmap",
    "graph",
    "parallel",
    "sankey",
    "scatter",
    "candlestick"];
function changeParamsTag(params) {
    var url = window.location.href;
    url = url.split("?")[0];
    history.pushState(params, null, url + "?" + cpParams(params));
    $(".box").each(function () {
        var id = "tag" + $(this).attr("id");
        if (window[id + "Prop"]) {
            var prop = window[id + "Prop"];
            var option = window[id + "Option"];
            var chart = window[id + "Chart"];
            if (prop.type=="table") {
                chart = window[id + "Table"];
            } else if(prop.type=="text"){
                chart = "#tag-"+$(this).attr("id");
            }
            if (prop.params) {
                if (prop.event&&prop.event.bind) {
                   var callback =new Function(prop.event.bind)
                   createTagsBox(prop, option, chart,callback);
                }else{
                    createTagsBox(prop, option, chart);

                }
            }
        }
    })
}

function cpParams(params) {
    var reStr = "";
    $.each(params, function (k, v) {
        reStr += k + "=" + encodeURIComponent(v) + "&";
    })
    return reStr;
}

function setFilterTagVal() {
    var params = paramsMap();
    $.each(params, function (k, value) {
        $("[name=" + k + "]").val(value);
    })
}

function submitParams(params) {
    var url = window.location.href;
    url = url.split("?")[0];
    location.href = url + "?" + $.param(params);
}

function bdButtonsEvent() {
    $(".bd-edit").bind("click", function () {
        if ($(".bd-r-panel").hasClass("on")) {

        } else {
            $(".bd-r-panel").addClass("on");
            resizeContent($("#content")[0].clientWidth + 300, null, false);
            createDragBoxs();
            if (BD_MODE == 1) {

            } else if (BD_MODE == 2) {
                // zoomBody();
            }
            changeBackImage(true);
        }
    })
    $(".bd-zoom").bind("click", function () {
        if ($(".bd-r-panel").hasClass("zoom")) {
            zoomBody("", 1);
            $(".bd-r-panel").removeClass("zoom");
        } else {
            $(".bd-r-panel").addClass("zoom");
            resizeContent($("#content")[0].clientWidth , null, false);
           // createDragBoxs();
            if (BD_MODE == 1) {

            } else if (BD_MODE == 2) {
                 zoomBody();
            }
          //  changeBackImage(true);
        }
    })
    $(".save-panel").bind("click", function () {
        $(".bd-r-panel").removeClass("on");
        removeCloneBoxs();
        resizeContent();
        removeDragBoxs();
        changeBackImage(false);
        if (BD_MODE == 1) {

        } else if (BD_MODE == 2) {
            zoomBody("", 1)
        }
    })
}

function changeBackImage(flag) {
    if (flag) {
        $("#content").css("background-image", $("body").css("background-image"));
        $("body").css("background-image", "");
    } else {
        $("body").css("background-image", $("#content").css("background-image"));
        $("#content").css("background-image", "");
    }
}

function initWidgetBtns(){
    $(".widget-btn").bind("click",function(){
        var name = $(this).attr("name");
        if (typeof (window[name]) == "function") {
            window[name].call(this,this);
        }


    });
    $(".widget-btn").each(function () {
        var name = $(this).attr("name");
        if (name =="openlink"){
            initopenlink(this);
        }
    })
}





function inithideBoxsEvent(box) {
    var shade = $('<div class="box-shade"></div>');
    box.append(shade);
    shade.bind("click", function () {
        return false;
    });
    shade.bind("mousedown", function () {
        return false;
    });
    var replacebtn = $('<div class="hide-replace hide-btn"></div>');
    var addbtn = $('<div class="hide-add hide-btn"></div>');
    shade.append(replacebtn).append(addbtn);
    replacebtn.bind("click", function () {

        if ($(this).hasClass("on")) {
            removeCloneBoxs();
            showDragBoxs();
            $(this).removeClass("on");
        } else {
            REPLACE_ID = $(this).parents(".box-hide").attr("id");
            removeCloneBoxs();
            boxsShake();
            hideDragBoxs();
            $(this).addClass("on");
        }

    });
    addbtn.bind("click", function () {
        removeDragBoxs();
        boxMoveToContent($(this).parents(".box-hide"));
        createDragBoxs();

    });
}

function boxsShake() {
    var boxs = $(".content").find(".box");
    $.each(boxs, function () {
        if (HideTagsType.indexOf($(this).attr("tag-type")) > -1) {
            var box = $(this);
            var div = $('<div class="box-clone"></div>');
            div.attr("style", box.attr("style"));
            div.attr("boxid", box.attr("id"));
            div.bind("click", function () {
                boxMoveToHide($(this).attr("boxid"));
                removeCloneBoxs();
                showDragBoxs();
                // $(this).remove();
            });

            div.addClass("shake");

            div.appendTo("#content");
        }

    })
}
function boxMoveToContent(box) {
    box.attr("class", "box");
    var style = box.data("style");
    if (style) {
        box.attr("style", style);
    } else {

        box.css({
            left: 0,
            top: 0,
            width: "300px",
            height: "200px"
        });
    }
    box.find(".box-shade").remove();
    box.appendTo("#content");
    chartResize(box);
}
function boxRecoveryToHide(id) {
    var box = $("#content").find("#" + id);
    box.attr("class", "box-hide");
    box.data("style", box.attr("style"));
    box.attr("style", "");
    box.appendTo(".bd-boxs");
    inithideBoxsEvent(box);
    removeDragBoxs();
    createDragBoxs();
    chartResize(box)
}
function boxMoveToHide(id) {
    var box = $("#content").find("#" + id);
    box.attr("class", "box-hide");
    box.data("style", box.attr("style"));

    replaceBoxBuild(box.attr("style"));
    box.attr("style", "");
    box.appendTo(".bd-boxs");
    inithideBoxsEvent(box);
    removeDragBoxs();
    createDragBoxs();
    chartResize(box)
}
function replaceBoxBuild(style) {
    var box = $("#" + REPLACE_ID);
    box.appendTo("#content").attr("style", style).attr("class", "box");
    box.find(".box-shade").remove();
    chartResize(box)

}

function removeCloneBoxs() {
    $(".box-clone").remove();
}



function zoomBody(pos, zoom) {
    let content = document.body;

    zoom = zoom || 0.5;
    pos = pos || "rb";
    let pw = document.documentElement.clientWidth;
    let ph = document.documentElement.clientHeight;
    content.style.setProperty('transform', "scale(" + zoom + ")");
    if (pos == "rb") {

        content.style.setProperty('margin-left', ((pw - pw * zoom) / 2) + "px");
        content.style.setProperty('margin-top', ((ph - ph * zoom) / 2) + "px");

    } else {
        content.style.setProperty('margin-left', "0px");
        content.style.setProperty('margin-top', "0px");
    }
}
var resizableStr =
    '<span class="resizable-handle tl" draggable="true"></span>' +
    '<span class="resizable-handle t" draggable="true"></span>' +
    '<span class="resizable-handle tr" draggable="true"></span>' +
    '<span class="resizable-handle r" draggable="true"></span>' +
    '<span class="resizable-handle br" draggable="true"></span>' +
    '<span class="resizable-handle b" draggable="true"></span>' +
    '<span class="resizable-handle bl" draggable="true"></span>' +
    '<span class="resizable-handle l" draggable="true"></span>';
function createDragBoxs() {
    var boxs = $(".content").find(".box");
    $.each(boxs, function () {
        if (HideTagsType.indexOf($(this).attr("tag-type")) > -1) {
            var box = $(this);
            var div = $('<div class="box-drag"></div>');
            div.attr("style", box.attr("style"));
            div.attr("boxid", box.attr("id"));
            div.css("z-index", 1008);
            div.bind("click", function () {
                $(this).css("z-index", 1009);
                $(".box-drag").removeClass("on");
                $(this).addClass("on");
            });
            div.appendTo("#content");
            div.append(resizableStr);
            if (BD_MODE == 1) {
                div.boxDrag();
                // div.boxTouchmove();
            } else if (BD_MODE == 2) {
                div.boxTouchmove();
            } else if (BD_MODE == 3) {
                div.boxDrag();
                div.boxTouchmove();

            }
            var recoverybtn = $('<div class="hide-recovery"></div>');
            div.append(recoverybtn);
            recoverybtn.bind("click", function () {
                boxRecoveryToHide($(this).parents(".box-drag").attr("boxid"));
            })
        }

    })
}

function hideDragBoxs() {
    $(".box-drag").hide();
}
function showDragBoxs() {
    $(".box-drag").show();
}
function removeDragBoxs() {
    $(".box-drag").remove();
}
function chartResize(box) {
    var chart = echarts.getInstanceByDom(box.find(".tag-charts")[0]);
    if (chart) {
        chart.resize();
    }
}

(function ($) {
    $.fn.extend({
        boxDrag: function (method) {
            var methods = {
                initBox: function (options) {
                    var settings = $.extend({}, options);
                    return this.each(function () {
                        var box = $(this);
                        box.mousedown(function () {
                            box.css("z-index", 1009);
                            var zoom = $("#content").data("zoom") || 1;
                            var event = window.event,
                                deltaX = event.pageX,
                                deltaY = event.pageY;
                            var orgX = parseInt($(this).css("left"));
                            var orgY = parseInt($(this).css("top"));
                            var orgBox = $("#" + box.attr("boxid"));
                            document.onmousemove = function () {
                                var event = window.event;
                                var devX = event.pageX - deltaX;
                                var devY = event.pageY - deltaY;
                                var newX = devX / zoom + orgX;
                                var newY = devY / zoom + orgY;
                                box.css({
                                    left: newX + "px",
                                    top: newY + "px"
                                });
                                orgBox.css({
                                    left: newX + "px",
                                    top: newY + "px"
                                });

                                event.preventDefault();
                                return false;
                            }
                            document.onmouseup = function () {
                                var event = window.event;
                                document.onmousemove = null;
                                document.onmouseup = null;
                            }
                        });
                        box.on("mousedown", ".resizable-handle", function () {
                            box.css("z-index", 1009);
                            var pointIndex = $(this).index();
                            var zoom = $("#content").data("zoom") || 1;
                            var event = window.event,
                                deltaX = event.pageX,
                                deltaY = event.pageY;
                            var orgX = parseInt(box.css("left"));
                            var orgY = parseInt(box.css("top"));
                            var orgW = parseInt(box.css("width"));
                            var orgH = parseInt(box.css("height"));
                            var orgBox = $("#" + box.attr("boxid"));
                            document.onmousemove = function () {
                                var event = window.event;
                                var devX = event.pageX - deltaX;
                                var devY = event.pageY - deltaY;
                                var newX = devX / zoom;
                                var newY = devY / zoom;
                                var cssObj = {};

                                switch (pointIndex) {
                                    case 0:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            top: orgY + newY + "px",
                                            width: orgW - newX + "px",
                                            height: orgH - newY + "px"
                                        }
                                        break;
                                    case 1:
                                        cssObj = {
                                            top: orgY + newY + "px",
                                            height: orgH - newY + "px"
                                        }
                                        break;
                                    case 2:
                                        cssObj = {
                                            top: orgY + newY + "px",
                                            height: orgH - newY + "px",
                                            width: orgW + newX + "px"
                                        }
                                        break;
                                    case 3:
                                        cssObj = {
                                            width: orgW + newX + "px"
                                        }
                                        break;
                                    case 4:
                                        cssObj = {
                                            width: orgW + newX + "px",
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 5:
                                        cssObj = {
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 6:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            width: orgW - newX + "px",
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 7:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            width: orgW - newX + "px",
                                        }
                                        break;

                                    default:
                                        break;
                                }
                                box.css(cssObj);
                                orgBox.css(cssObj);

                                event.preventDefault();
                                return false;
                            }
                            document.onmouseup = function () {
                                var event = window.event;
                                document.onmousemove = null;
                                document.onmouseup = null;
                                chartResize(orgBox);
                            }
                            return false;
                        })
                    });
                }

            };

            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if (typeof method === 'object' || !method) {
                return methods.initBox.apply(this, arguments);
            } else {
                $.error('Method ' + method + ' does not exist on jQuery.popupSelection');
            }
        },
        boxTouchmove: function (method) {
            var methods = {
                initBox: function (options) {
                    var settings = $.extend({}, options);
                    return this.each(function () {
                        var box = $(this);
                        box.bind("touchstart", function () {
                            box.css("z-index", 1009);
                            var zoom = $("#content").data("zoom") || 1;
                            var event = window.event.touches[0],
                                deltaX = event.pageX,
                                deltaY = event.pageY;
                            var orgX = parseInt($(this).css("left"));
                            var orgY = parseInt($(this).css("top"));
                            var orgBox = $("#" + box.attr("boxid"));
                            $(document).bind("touchmove", function () {
                                var event = window.event.touches[0]
                                var devX = event.pageX - deltaX;
                                var devY = event.pageY - deltaY;
                                var newX = devX / zoom + orgX;
                                var newY = devY / zoom + orgY;
                                box.css({
                                    left: newX + "px",
                                    top: newY + "px"
                                });
                                orgBox.css({
                                    left: newX + "px",
                                    top: newY + "px"
                                });

                                return false;
                            })
                            $(document).bind("touchend", function () {
                                $(document).unbind("touchend touchmove");
                            })
                        });
                        box.find(".resizable-handle").addClass("touch");
                        box.on("touchstart", ".resizable-handle", function () {
                            box.css("z-index", 1009);
                            var pointIndex = $(this).index();
                            var zoom = $("#content").data("zoom") || 1;
                            var event = window.event.touches[0],
                                deltaX = event.pageX,
                                deltaY = event.pageY;
                            var orgX = parseInt(box.css("left"));
                            var orgY = parseInt(box.css("top"));
                            var orgW = parseInt(box.css("width"));
                            var orgH = parseInt(box.css("height"));
                            var orgBox = $("#" + box.attr("boxid"));
                            $(document).bind("touchmove", function () {
                                var event = window.event.touches[0];
                                var devX = event.pageX - deltaX;
                                var devY = event.pageY - deltaY;
                                var newX = devX / zoom;
                                var newY = devY / zoom;
                                var cssObj = {};

                                switch (pointIndex) {
                                    case 0:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            top: orgY + newY + "px",
                                            width: orgW - newX + "px",
                                            height: orgH - newY + "px"
                                        }
                                        break;
                                    case 1:
                                        cssObj = {
                                            top: orgY + newY + "px",
                                            height: orgH - newY + "px"
                                        }
                                        break;
                                    case 2:
                                        cssObj = {
                                            top: orgY + newY + "px",
                                            height: orgH - newY + "px",
                                            width: orgW + newX + "px"
                                        }
                                        break;
                                    case 3:
                                        cssObj = {
                                            width: orgW + newX + "px"
                                        }
                                        break;
                                    case 4:
                                        cssObj = {
                                            width: orgW + newX + "px",
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 5:
                                        cssObj = {
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 6:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            width: orgW - newX + "px",
                                            height: orgH + newY + "px"
                                        }
                                        break;
                                    case 7:
                                        cssObj = {
                                            left: orgX + newX + "px",
                                            width: orgW - newX + "px",
                                        }
                                        break;

                                    default:
                                        break;
                                }
                                box.css(cssObj);
                                orgBox.css(cssObj);
                                return false;
                            });

                            $(document).bind("touchend", function () {
                                $(document).unbind("touchend touchmove");
                                chartResize(orgBox);
                            });
                            return false;
                        })
                    });
                }

            };
            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if (typeof method === 'object' || !method) {
                return methods.initBox.apply(this, arguments);
            } else {
                $.error('Method ' + method + ' does not exist on jQuery.popupSelection');
            }
        }
    })
})(jQuery)