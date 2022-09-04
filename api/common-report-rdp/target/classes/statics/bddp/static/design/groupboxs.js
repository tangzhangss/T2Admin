function groupBoxs() {
    createGroupBox();
}

function dissolveBoxs() {
    var groupbox = $(".groupbox.selected");
    selectableItmes = [];
    $.each(groupbox.data("boxs"), function (i, box) {
        $(box).removeData("groupbox").addClass("selected");
        selectableItmes.push(box);
    });
    groupbox.removeData("boxs");
    groupbox.remove();
}

function createGroupBox() {
    var cssobj = {
        "z-index": 999
    };
    var boxs = [];
    var groupbox = $('<div class="groupbox"></div>');
    $(".box").each(function () {
        if ($(this).hasClass("selected") || $(this).hasClass("box-selected")) {
            var rectP = $(this).data("prop").rectP;
            if (!cssobj.left || rectP.x < cssobj.left) {
                cssobj.left = rectP.x;
            }
            if (!cssobj.top || rectP.y < cssobj.top) {
                cssobj.top = rectP.y;
            }
            if (!cssobj.width || (rectP.x + rectP.width) > cssobj.width) {
                cssobj.width = rectP.x + rectP.width;
            }
            if (!cssobj.height || (rectP.y + rectP.height) > cssobj.height) {
                cssobj.height = rectP.y + rectP.height;
            }
            boxs.push(this);
            $(this).data("groupbox", groupbox);
        }
    });
    cssobj.width = cssobj.width - cssobj.left;
    cssobj.height = cssobj.height - cssobj.top;
    groupbox.css(cssobj);
    groupbox.appendTo("#content");
    groupbox.data("boxs", boxs);
    groupbox.addClass("selected");
    groupbox.dragBox({
        parent: $(".layout-Content")[0],
        dragStart: function (b, p, o, event) {
            $(".groupbox").show();
            guides = getGuides(b);
            boxsOriginalPosition(b);
        },
        dragMove: function (b, p, o, event, pos) {
            if (event.altKey) {
                searchGuides(b, event);
            }
        },
        dragEnd: function (b, p, o, event, pos) {
            hideGuides();
            $(".groupbox").not($(b)).hide();
            pos.devX = p.x - o.x;
            pos.devY = p.y - o.y;
            pos.zoom=1;
            boxsMovePosition(b, pos);
        }
    });
}

function createGroupBoxByDiyTags(nodes) {
    var cssobj = {
        "z-index": 999
    };
    var boxs = [];
    var groupbox = $('<div class="groupbox"></div>');
    $.each(nodes, function () {
        var rectP = $(this).data("prop").rectP;
        if (!cssobj.left || rectP.x < cssobj.left) {
            cssobj.left = rectP.x;
        }
        if (!cssobj.top || rectP.y < cssobj.top) {
            cssobj.top = rectP.y;
        }
        if (!cssobj.width || (rectP.x + rectP.width) > cssobj.width) {
            cssobj.width = rectP.x + rectP.width;
        }
        if (!cssobj.height || (rectP.y + rectP.height) > cssobj.height) {
            cssobj.height = rectP.y + rectP.height;
        }
        boxs.push(this);
        $(this).data("groupbox", groupbox);
    });
    cssobj.width = cssobj.width - cssobj.left;
    cssobj.height = cssobj.height - cssobj.top;
    groupbox.css(cssobj);
    groupbox.appendTo("#content");
    groupbox.data("boxs", boxs);
    groupbox.addClass("selected");
    groupbox.dragBox({
        parent: $(".layout-Content")[0],
        dragStart: function (b, p, o, event) {
            $(".groupbox").show();
            guides = getGuides(b);
            boxsOriginalPosition(b);
        },
        dragMove: function (b, p, o, event, pos) {
            if (event.altKey) {
                searchGuides(b, event);
            }
        },
        dragEnd: function (b, p, o, event, pos) {
            hideGuides();
            $(".groupbox").not($(b)).hide();
            pos.devX = p.x - o.x;
            pos.devY = p.y - o.y;
            pos.zoom=1;
            boxsMovePosition(b, pos);
        }
    });
    return groupbox;
}

function showGroup(box) {
    if ($(box).data("groupbox")) {
        $(".groupbox").hide();
        $(box).data("groupbox").show().addClass("selected");
        $(".box").removeClass("box-selected");
        $(box).addClass("box-selected");
        currBox = null;
        $("#config-panel").empty();
        $(".sitemap-item").removeClass("on");
        $("#rightnav").removeClass("on").hide();
        return true;
    } else {
        return false;
    }
}

function boxsOriginalPosition(b) {
    $.each($(b).data("boxs"), function (i, node) {
        $(node).data("originalPosition", {
            x: $(node).data("prop").rectP.x,
            y: $(node).data("prop").rectP.y
        });
    });
}

function boxsMovePosition(b, pos) {
    $.each($(b).data("boxs"), function (i, node) {
        var nodeOriginalPosition = $(node).data("originalPosition");
        var newX = pos.devX / pos.zoom + nodeOriginalPosition.x;
        var newY = pos.devY / pos.zoom + nodeOriginalPosition.y;
        $(node).rotateResize("setBoxProp", {
            x: newX,
            y: newY
        });
    });
}


(function ($) {
    $.fn.extend({
        dragBox: function (method) {
            var methods = {
                initBox: function (options) {
                    var settings = $.extend({
                        parent: document,
                        dragStart: false,
                        dragMove: false,
                        dragEnd: false,
                    }, options);


                    return this.each(function () {
                        var box = this;
                        var parent = settings.parent;
                        // 定义一个Rect
                        var prop = $(this).data("prop") || {};
                        if (!prop.rectP) {
                            prop.rectP = {};
                        }
                        var rectP = {
                            x: box.offsetLeft,
                            y: box.offsetTop
                        };
                        prop.rectP = rectP;
                        if (!prop.id || settings.newId) {
                            prop.id = guid();
                        }
                        $(box).attr("id", "groupbox" + prop.id);
                        $(this).data("prop", prop);

                        function bindMoveEvents(box) {
                            box.onmousedown = function () {
                                var content = $("#content");
                                var zoom = $("#content").data("zoom") || 1;
                                $(box).removeData("rulerspos");
                                var event = window.event,
                                    deltaX = event.pageX,
                                    deltaY = event.pageY;
                                var devX, devY;
                                var original = {
                                    x: Number($(box).data("prop").rectP.x),
                                    y: Number($(box).data("prop").rectP.y)
                                };
                                document.onmousemove = function () {
                                    $(box).addClass("groupBox-move");
                                    var event = window.event;
                                    devX = event.pageX - deltaX;
                                    devY = event.pageY - deltaY;
                                    devX = Math.round(devX / 10) * 10;
                                    devY = Math.round(devY / 10) * 10;
                                    var newX = devX / zoom + original.x;
                                    var newY = devY / zoom + original.y;
                                    $(box).data("prop").rectP.x = newX;
                                    $(box).data("prop").rectP.y = newY;
                                    draw();
                                    if (typeof (settings.dragMove) == "function") {
                                        settings.dragMove.call(this, box, $(box).data("prop").rectP, original, event, {
                                            devX: devX,
                                            devY: devY,
                                            zoom: zoom
                                        });
                                    }
                                    event.preventDefault();
                                    return false;
                                }
                                document.onmouseup = function () {
                                    $(box).removeClass("groupBox-move");
                                    var event = window.event;
                                    document.onmousemove = null;
                                    document.onmouseup = null;
                                    if (typeof (settings.dragEnd) == "function") {
                                        settings.dragEnd.call(this, box, $(box).data("prop").rectP, original, event, {
                                            devX: devX,
                                            devY: devY,
                                            zoom: zoom
                                        });
                                    }
                                }
                                if (typeof (settings.dragStart) == "function") {
                                    settings.dragStart.call(this, box, $(box).data("prop").rectP, original, event);
                                }

                            }
                            box.ondragstart = function (event) {
                                event.preventDefault();
                                return false;
                            }
                        }

                        /**
                         * 重绘视图
                         * @return {[type]} [description]
                         */
                        function draw() {
                            css(box, {
                                left: $(box).data("prop").rectP.x + 'px',
                                top: $(box).data("prop").rectP.y + 'px',
                            });
                        }


                        function init() {

                            draw();
                            bindMoveEvents(box);

                        }
                        init();
                    });

                },
                getBoxProp: function () {
                    return this.data("prop").rectP;
                },
                setBoxProp: function (p, z) {
                    var ox = this.data("prop").rectP.x;
                    var oy = this.data("prop").rectP.y;
                    var rectP = $.extend({}, this.data("prop").rectP, p);
                    css(this[0], {
                        left: rectP.x + 'px',
                        top: rectP.y + 'px'
                    });
                    var prop = this.data("prop");
                    prop.rectP = rectP;
                    this.data("prop", prop);
                    var devX = rectP.x - ox;
                    var devY = rectP.y - oy;
                    var pos = {
                        zoom: 1,
                        devX: devX,
                        devY: devY
                    };
                    if (z) {
                        boxsOriginalPosition(this);
                        boxsMovePosition(this, pos);
                    } else {
                        this.data("rulerspos", pos);
                    }

                }
            };
            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if (typeof method === 'object' || !method) {
                return methods.initBox.apply(this, arguments);
            } else {
                $.error('Method ' + method + ' does not exist on jQuery.popupSelection');
            }

            function css(node, ops) {
                for (var index in ops) {
                    node['style'][index] = ops[index];
                }
            }

        }
    })
})(jQuery)