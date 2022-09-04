(function ($) {
    $.fn.extend({
        initProp: function (method) {
            var methods = {
                init: function (options) {
                    var settings = $.extend({

                    }, options);
                    //console.log(settings);
                    return this.each(function () {
                        var elem = $(this);

                        elem.empty();
                        var data = settings.data;
                        $.each(data, function (i, proptag) {

                            addPropDom(elem, proptag);
                        });
                    });

                }
            };
            //新增组件
            function addPropDom(elem, opt) {
                var type = opt.type;
                if (type == "driver") {
                    addDriver(elem, opt);
                } else if (type == "title") {
                    addTitle(elem, opt);
                } else if (type == "btn") {
                    addBtn(elem, opt);
                } else if (type == "tag") {
                    addPropTag(elem, opt);
                } else if (type == "tabs") {
                    addTabs(elem, opt);
                }
            }
            //新增line
            function addDriver(elem, opt) {
                elem.append('<div class="prop-driver"></div>');
            }
            //新增标题
            function addTitle(elem, opt) {
                elem.append('<div class="prop-title">' + opt.name + '</div>');
            }
            //新增按钮
            function addBtn(elem, opt) {
                var div = $('<div class="prop-btn"></div>');
                var btn = $('<button type="button">' + opt.name + '</button>');
                btn.bind("click", function () {
                    if (opt.keyName) {
                        window[opt.keyName]();
                    }
                });
                div.append(btn);
                elem.append(div);
            }
            //新增控件
            function addPropTag(elem, opt) {
                var tag = $('<div class="prop-tag"></div>');
                var checkbox = $('<div class="tag-checkbox fa fa-square-o"></div>');
                var title = $('<div class="tag-title">' + opt.name + '</div>');
                var widget = $('<div class="prop-widget"></div>');
                var over = $('<div class="prop-over"></div>');
                tag.append(checkbox).append(title).append(widget).append(over);

                elem.append(tag);
                checkbox.bind("click", function () {
                    if ($(this).parent().hasClass("on")) {
                        $(this).removeClass('fa-check-square-o').addClass('fa-square-o');
                        $(this).parent().removeClass("on");
                        over.show();
                        delBoxPropVal(widget, opt);
                    } else {
                        $(this).addClass('fa-check-square-o').removeClass('fa-square-o');
                        $(this).parent().addClass("on");
                    }
                });
                over.bind("click", function () {
                    if ($(this).parent().hasClass("on")) {

                    } else {
                        checkbox.addClass('fa-check-square-o').removeClass('fa-square-o');
                        $(this).parent().addClass("on");
                        $(this).hide();
                    }
                });
                getTag(widget, opt);
            }

            function getTag(elem, opt) {
                switch (opt.tag) {
                    case "input":
                        createInput(elem, opt);
                        break;
                    case "radio":
                        ctreateRadio(elem, opt);
                        break;
                    case "slider":
                        createSlider(elem, opt);
                        break;
                    case "doubleSlider":
                        createDoubleSlider(elem, opt);
                        break;
                    case "color":
                        createColorPicker(elem, opt);
                        break;
                    case "inputBtn":
                        createInputBtn(elem, opt);
                        break;
                    case "button":
                        createButton(elem, opt);
                        break;
                    default:
                        break;
                }
            }

            function createInput(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div = $('<div class="prop-widget-input"></div>');
                var input = $('<input type="text" />');
                div.append(input);
                elem.append(div);
                input.bind("change", function () {
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, $(this).val(), panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, $(this).val(), false);
                    }
                });
                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                var propval = val;
                if (isArray(val)) {
                    propval = val[0];
                }
                if (!!propval) {
                    propval = typeof (propval) == "function" ? propval.toString() : propval;
                    input.val(propval);
                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }
            }

            function ctreateRadio(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div = $('<div class="prop-widget-radio"></div>');
                var ul = $('<ul></ul>');
                if (typeof (opt.items) == "string") {
                    var opts = opt.items.split("|");
                    $.each(opts, function (i, str) {
                        var node = str.split("-");
                        var li = $('<li data-value="' + node[1] + '">' + node[0] + '</li>');
                        li.appendTo(ul);
                    });
                } else {
                    $.each(opt.items, function (i, node) {
                        var li = $('<li data-value="' + node.value + '">' + node.text + '</li>');
                        li.appendTo(ul);
                    });
                }
                ul.children("li").bind("click", function () {
                    if (!$(this).parents(".prop-tag").hasClass("on")) {
                        return false;
                    }
                    $(this).addClass("on").siblings("li").removeClass("on");

                    var val = $(this).data("value");
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, val, panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, val, false);
                    }
                });
                div.append(ul);
                elem.append(div);

                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                var propval = val;
                if (isArray(val)) {
                    propval = val[0];
                }
                if (!!propval) {

                    ul.find("li[data-value=" + propval + "]").addClass("on").siblings("li").removeClass("on");
                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }
            }

            function createSlider(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div = $('<div class="prop-widget-slider"></div>');
                var slider = $('<div></div>');
                var input = $('<input class="slider-input" type="number" />');
                div.append(slider);
                elem.append(div).append(input);
                var options = $.extend({
                    change: function (event, ui) {
                        //input.val(ui.value);
                        //setBoxPropVal(opt.keyName,ui.value);
                    },
                    slide: function (event, ui) {
                        input.val(ui.value);
                        var tempVal = Number(ui.value);
                        if (opt.items) {
                            tempVal += opt.items
                        }
                        if (panel.keyname == "series") {

                            setBoxPropVal(opt.keyName, tempVal, panel.keyname, panel.index);
                        } else {
                            setBoxPropVal(opt.keyName, tempVal, false);
                        }

                    }
                }, {
                        min: 0,
                        max: 100
                    });
                slider.slider(
                    options
                );

                input.bind("change", function () {
                    var tempVal = Number($(this).val());
                    if (opt.items) {
                        tempVal += opt.items
                    }
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, tempVal, panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, tempVal, false);
                    }
                    var max = slider.slider("option", "max");
                    if ($(this).val() > max) {
                        slider.slider("option", "max", $(this).val());
                    }
                    slider.slider("value", $(this).val());
                });



                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                var propval = val;
                if (isArray(val)) {
                    propval = val[0];
                }
                if (!!propval) {
                    if (opt.items != "") {
                        propval = (propval + '').split(opt.items)[0];
                    }

                    input.val(propval);
                    var max = slider.slider("option", "max");
                    if (propval > max) {
                        slider.slider("option", "max", propval);
                    }
                    slider.slider("value", propval);

                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }
            }

            function createDoubleSlider(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div1 = $('<div class="prop-widget-slider"></div>');
                var slider1 = $('<div></div>');
                var input1 = $('<input class="slider-input" type="number" />');
                var div2 = $('<div class="prop-widget-slider"></div>');
                var slider2 = $('<div></div>');
                var input2 = $('<input class="slider-input" type="number" />');
                div1.append(slider1);
                div2.append(slider2);
                elem.append(div1).append(input1).append(div2).append(input2);
                var options = $.extend({
                    change: function (event, ui) {
                        //input.val(ui.value);
                        //setBoxPropVal(opt.keyName,ui.value);
                    },
                    slide: function (event, ui) {
                        input.val(ui.value);
                        if (panel.keyname == "series") {
                            setBoxPropVal(opt.keyName, Number(ui.value) + opt.items, panel.keyname, panel.index);
                        } else {
                            setBoxPropVal(opt.keyName, Number(ui.value) + opt.items, false);
                        }

                    }
                }, {
                        min: -100,
                        max: 100
                    });
                slider1.slider({
                    slide: function (event, ui) {
                        input1.val(ui.value);
                        var arr = [];
                        if (opt.items) {
                            arr.push(ui.value + opt.items);
                            arr.push(slider2.slider("value") + opt.items);
                        } else {
                            arr.push(ui.value);
                            arr.push(slider2.slider("value"));
                        }
                        if (panel.keyname == "series") {
                            setBoxPropVal(opt.keyName, arr, panel.keyname, panel.index);
                        } else {
                            setBoxPropVal(opt.keyName, arr, false);
                        }
                    }
                });
                slider2.slider({
                    slide: function (event, ui) {
                        input2.val(ui.value);
                        var arr = [];
                        if (opt.items) {
                            arr.push(slider1.slider("value") + opt.items);
                            arr.push(ui.value + opt.items);
                        } else {
                            arr.push(slider1.slider("value"));
                            arr.push(ui.value);
                        }
                        if (panel.keyname == "series") {
                            setBoxPropVal(opt.keyName, arr, panel.keyname, panel.index);
                        } else {
                            setBoxPropVal(opt.keyName, arr, false);
                        }
                    }
                });

                input1.bind("change", function () {
                    var max = slider1.slider("option", "max");
                    var input1Val = $(this).val();
                    if (input1Val > max) {
                        slider1.slider("option", "max", input1Val);
                    }
                    slider1.slider("value", input1Val);
                    var arr = [];
                    if (opt.items) {
                        arr.push(input1Val + opt.items);
                        arr.push(slider2.slider("value") + opt.items);
                    } else {
                        arr.push(input1Val);
                        arr.push(slider2.slider("value"));
                    }
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, arr, panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, arr, false);
                    }

                });
                input2.bind("change", function () {
                    var max = slider2.slider("option", "max");
                    var input2Val = $(this).val();
                    if (input2Val > max) {
                        slider2.slider("option", "max", input2Val);
                    }
                    slider2.slider("value", input2Val);
                    var arr = [];
                    if (opt.items) {
                        arr.push(slider1.slider("value") + opt.items);
                        arr.push(input2Val + opt.items);
                    } else {
                        arr.push(slider1.slider("value"));
                        arr.push(input2Val);
                    }
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, arr, panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, arr, false);
                    }
                });



                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                if (isArray(val)) {
                    var propval1 = val[0];
                    var propval2 = val[1];
                    if (!isNaN(propval1) && !isNaN(propval2)) {
                        if (opt.items) {
                            propval1 = propval1.split(opt.items)[0];
                            propval2 = propval2.split(opt.items)[0];
                        }

                        input1.val(propval1);
                        var max1 = slider1.slider("option", "max");
                        if (propval1 > max1) {
                            slider1.slider("option", "max", propval1);
                        }
                        slider1.slider("value", propval1);

                        input2.val(propval2);
                        var max2 = slider2.slider("option", "max");
                        if (propval2 > max2) {
                            slider2.slider("option", "max", propval2);
                        }
                        slider2.slider("value", propval2);

                        elem.parent().addClass("on");
                        elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                        elem.parent().find(".prop-over").hide();
                    }

                }
            }

            function createColorPicker(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div = $('<div class="prop-widget-color"></div>');
                var colorPicker = $('<div class="colorPicker"></div>');
                div.append(colorPicker);
                elem.append(div);
                colorPicker.spectrum({
                    showPalette: true,
                    showInput: true,
                    allowEmpty: true,
                    showAlpha: true,
                    showSelectionPalette: true,
                    showInitial: true,
                    preferredFormat: "rgba",
                    palette: [
                        ["#000000", "#434343", "#666666", "#999999", "#b7b7b7", "#cccccc", "#d9d9d9", "#efefef", "#f3f3f3", "#ffffff"],
                        ["#980000", "#ff0000", "#ff9900", "#ffff00", "#00ff00", "#00ffff", "#4a86e8", "#0000ff", "#9900ff", "#ff00ff"],
                        ["#e6b8af", "#f4cccc", "#fce5cd", "#fff2cc", "#d9ead3", "#d9ead8", "#c9daf8", "#cfe2f3", "#d9d2e9", "#ead1dc"],
                        ["#dd7e6b", "#ea9999", "#f9cb9c", "#ffe599", "#b6d7a8", "#a2c4c9", "#a4c2f4", "#9fc5e8", "#b4a7d6", "#d5a6bd"],
                        ["#cc4125", "#e06666", "#f6b26b", "#ffd966", "#93c47d", "#76a5af", "#6d9eeb", "#6fa8dc", "#8e7cc3", "#c27ba0"],
                        ["#a61c00", "#cc0000", "#e69138", "#f1c232", "#6aa84f", "#45818e", "#3c78d8", "#3d85c6", "#674ea7", "#a64d79"],
                        ["#85200c", "#990000", "#b45f06", "#bf9000", "#38761d", "#134f5c", "#1155cc", "#0b5394", "#351c75", "#741b47"],
                        ["#5b0f00", "#660000", "#783f04", "#7f6000", "#274e13", "#0c343d", "#1c4587", "#073763", "#20124d", "#4c1130"]
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
                        if (!color) {
                            color = "auto";
                        } else {
                            color = color.toRgbString()
                        }
                        colorPicker.css("background-color", color);
                        if (panel.keyname == "series") {
                            setBoxPropVal(opt.keyName, color, panel.keyname, panel.index);
                        } else {
                            setBoxPropVal(opt.keyName, color, false);
                        }
                    }
                });
                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                var propval = val;
                if (isArray(val)) {
                    propval = val[0];
                }
                if (!!propval) {
                    colorPicker.spectrum("set", propval);
                    colorPicker.css("background-color", propval);
                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }
            }


            function createInputBtn(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                var div = $('<div class="prop-widget-input"></div>');
                var input = $('<input class="input-btn-input" type="text" />');
                var btn = $('<button class="input-btn-btn" type="button">...</button>');
                btn.bind("click", function () {
                    if (opt.items) {
                        window[opt.items](input);
                    }
                });
                div.append(input).append(btn);
                if (opt.items == "getImagePath") {
                    var selectbtn = $('<button class="input-btn-btn" type="button"><i class="fa fa-object-group"></i></button>');
                    selectbtn.bind("click", function () {
                        window["selectImagePath"](input);
                    });
                    div.append(selectbtn);
                }
                elem.append(div);

                input.bind("change blur", function () {
                    if (panel.keyname == "series") {
                        setBoxPropVal(opt.keyName, $(this).val(), panel.keyname, panel.index);
                    } else {
                        setBoxPropVal(opt.keyName, $(this).val(), false);
                    }
                });
                var val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                var propval = val;
                if (isArray(val)) {
                    propval = val[0];
                }
                if (!!propval) {

                    input.val(propval);
                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }
            }
            function createButton(elem, opt) {
                var panel = elem.closest(".ui-tabs-panel").data();
                //  var div = $('<div class="prop-widget-input"></div>');
                var btn = $('<button class="button-btn" type="button"></button>');
                var propval = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                btn.html(opt.value);
                btn.bind("click", function () {
                    if (opt.items) {
                        var val ;
                        if(opt.keyName=="elem"){
                            val = elem;
                        }else{

                            val = getBoxPropVal(opt.keyName, panel.keyname, panel.index);
                        }
                        window[opt.items](val, function (eventValue) {
                            setBoxPropVal(opt.keyName, eventValue, false);
                        });
                    }
                });
                //  div.append(btn);
                elem.append(btn);


                if (!!propval) {
                    elem.parent().addClass("on");
                    elem.parent().find(".tag-checkbox").addClass('fa-check-square-o').removeClass('fa-square-o');
                    elem.parent().find(".prop-over").hide();
                }

            }



            function addTabs(elem, opts) {
                var div = $('<div class="prop-tabs"></div>');
                var tabs = opts.items;
                var ul = $('<ul></ul>');
                div.append(ul);
                $.each(tabs, function (i, tab) {
                    var li = $('<li class="prop-tabs-item">' + tab.text + '</li>');
                    li.appendTo(ul);
                    var tdiv = $('<div class="prop-tabs-page"></div>');
                    div.append(tdiv);
                    var data = tab.items;
                    $.each(data, function (i, proptag) {
                        addPropDom(tdiv, proptag);
                    });

                });
                elem.append(div);
                div.propTabs();
            }

            function getBoxPropVal(name, keyname, pindex) {
                var box = currBox;
                var prop = box.data("prop");
                return getJsonValue(prop, name, keyname, pindex);

            }

            function getJsonValue(json, key, keyname, pindex) {
                var rjson;
                var keys = key.split(".");
                var obj = json[keys[0]];
                if (Object.prototype.toString.apply(obj) === "[object Array]") {
                    rjson = getJsonArray(obj, 0, keys, keyname, pindex);
                } else if (Object.prototype.toString.apply(obj) === "[object Object]") {
                    rjson = getJsonObject(obj, 0, keys, keyname, pindex);
                } else {
                    rjson = obj;
                }
                return rjson;
            }


            function getJsonObject(json, index, keys, keyname, pindex) {
                if (index < keys.length) {
                    var obj = json[keys[index + 1]];
                    if (Object.prototype.toString.apply(obj) === "[object Array]") {
                        return getJsonArray(obj, index + 1, keys, keyname, pindex);
                    } else if (Object.prototype.toString.apply(obj) === "[object Object]") {
                        return getJsonObject(obj, index + 1, keys, keyname, pindex);
                    } else {
                        return obj;
                    }
                }
            }

            function getJsonArray(json, index, keys, keyname, pindex) {
                if (keyname == keys[index]) {
                    var node = json[pindex];
                    if (index < keys.length) {
                        var obj = node[keys[index + 1]];
                        if ((index + 1) == (keys.length - 1)) {
                            return obj;
                        }
                        if (Object.prototype.toString.apply(obj) === '[object Array]') {
                            return getJsonArray(obj, index + 1, keys, keyname, pindex);
                        } else if (Object.prototype.toString.apply(obj) === '[object Object]') {
                            return getJsonObject(obj, index + 1, keys, keyname, pindex);
                        } else {
                            return obj;
                        }
                    }
                } else {
                    var rjson = [];
                    $.each(json, function (i, node) {
                        if (index < keys.length) {
                            var obj = node[keys[index + 1]];
                            if (Object.prototype.toString.apply(obj) === '[object Array]') {
                                rjson.push(getJsonArray(obj, index + 1, keys, keyname, pindex));
                            } else if (Object.prototype.toString.apply(obj) === '[object Object]') {
                                rjson.push(getJsonObject(obj, index + 1, keys, keyname, pindex));
                            } else if (Object.prototype.toString.apply(node) === '[string String]') {
                                rjson.push(node);
                            } else {
                                rjson.push(obj);
                            }
                        }
                    });
                    return rjson;
                }
            }

            function setBoxPropVal(name, val, flag, pindex) {
                var box = currBox;
                var prop = box.data("prop");
                setJsonValue(prop, name, val, flag, pindex);
                var keys = name.split(".");
                if (keys[0] == "rectP") {
                    box.rotateResize("setBoxProp", prop.rectP);
                    var myChart = box.data("prop").myChart;
                    if (myChart) {
                        myChart.resize();
                    }
                } else if (keys[0] == "options") {
                    var myChart = box.data("prop").myChart;
                    if (myChart) {
                        var tempOptions = prop.options;//$.extend(true, {}, eval("(" + prop.optionsText + ")"), prop.options);
                        prop.optionsText = JSON.stringify(tempOptions, function (k, v) {
                            if (typeof (v) == "function") {
                                return funconvertStr(v);
                            }
                            return v;
                        }, 4);
                        prop.optionsText = prop.optionsText.replace(/"&/g, "").replace(/&"/g, "");
                        jsonExtentEditor.setValue(prop.optionsText);
                        myChart.setOption(prop.options);

                    }
                } else if (keys[0] == "other") {
                    if (keys[1] == "theme" || keys[1] == "axis") {
                        bulidChartOther(currBox);
                    }
                } else if (keys[0] == "parts") {
                    switch (keys[1]) {
                        case "fontSize":
                            box.find(".tag-" + prop.type).css("font-size", val + "px");
                            break;
                        case "fontWeight":
                            box.find(".tag-" + prop.type).css("font-weight", val);
                            break;
                        case "textShadow":
                            box.find(".tag-" + prop.type).css("text-shadow", val);
                            break;
                        case "color":
                            box.find(".tag-" + prop.type).css("color", val);
                            break;
                        case "backgroundImage":
                            var strurl = val;
                            strurl = strurl.replace(/\\/g, "\/");
                            box.find(".tag-" + prop.type).css({
                                "background-image": "url('" + strurl + "')",
                                "background-repeat": "no-repeat",
                                "background-size": "100% 100%"
                            });
                            break;
                        case "backgroundColor":
                            box.find(".tag-" + prop.type).css({
                                "background-color": val
                            });
                            break;
                        case "backgroundSize":
                            var sizeVal = "";
                            if (val && val.length == 2 && val[0] > 0 && val[1] > 0) {
                                sizeVal = val[0] + "% " + val[1] + "%";
                            };
                            box.find(".tag-" + prop.type).css({
                                "background-size": sizeVal
                            });
                            break;
                        case "backgroundRepeat":
                            box.find(".tag-" + prop.type).css({
                                "background-repeat": val.replace("_", "-")
                            });
                            break;
                        case "imgUrl":
                            box.find(".tag-" + prop.type).attr("src", val);
                            break;
                        case "text":
                            box.find(".tag-" + prop.type).html(val);
                            break;
                        case "borderRadius":
                            box.find(".tag-" + prop.type).css({
                                "border-radius": val + "px"
                            });
                            break;
                        case "iframeUrl":
                            if (val.indexOf("http://") == -1) {
                                val = "http://" + val;
                            }
                            box.find(".tag-" + prop.type).attr("src", val);
                            break;
                        case "fontFamily":
                            box.find(".tag-" + prop.type).css("font-family", val);
                            break;
                        case "borderWidth":
                            box.find(".tag-" + prop.type).css("border-width", val);
                            break;
                        case "borderStyle":
                            box.find(".tag-" + prop.type).css("border-style", val);
                            break;
                        case "borderColor":
                            box.find(".tag-" + prop.type).css("border-color", val);
                            break;
                        case "textAlign":
                            box.find(".tag-" + prop.type).css("text-align", val);
                            break;
                        case "lineHeight":
                            box.find(".tag-" + prop.type).css("line-height", val + "px");
                            break;
                        case "tableHeight":
                            box.find(".tag-" + prop.type).find("ul.kgo-scroll-body-ul li").css("height", val + "px");
                            break;
                        case "padding":
                            box.find(".tag-" + prop.type).css("padding", val);
                            break;
                        default:
                            break;
                    }
                } else if (keys[0] == "swiper") {
                    box.data("prop").mySwiper.destroy(true, true);
                    var swiper = box.data("prop").swiper;
                    var mySwiper = new Swiper(box.find(".tag-swiper"), swiper);
                    box.data("prop").mySwiper = mySwiper;
                } else if (keys[0] == "effect") {
                    switch (keys[1]) {
                        case "autoscroll":
                            if (val) {
                                box.autoScroll();
                            } else {
                                box.stopScroll();
                            }
                            break;
                        default:
                            break;
                    }
                } else if (keys[0] == "table") {
                    switch (keys[1]) {
                        case "titlefontsize":
                            if (prop.type == "table") {
                                box.find(".kgo-scroll-head").css("font-size", val + "px");
                            } else {
                                box.find("#autohead_thead").css("font-size", val + "px");

                            }

                            break;
                        case "bodyfontsize":
                            if (prop.type == "table") {
                                box.find(".baseStyle-data").css("font-size", val + "px");
                                
                            } else {
                                box.find("#autohead_tbody > tr >td").css("font-size", val + "px");

                            }
                            break;
                        case "titlefontcolor":
                            if (prop.type == "table") {
                                box.find(".baseStyle").css("color", val);
                            } else {
                                box.find("#autohead_thead > tr >th").css("color", val);

                            }
                            break;
                        case "bodyfontcolor":
                            if (prop.type == "table") {
                                box.find(".baseStyle-data").css("color", val);
                            } else {
                                box.find("#autohead_tbody > tr >td").css("color", val);

                            }
                            break;
                        case "titlebgcolor":
                            if (prop.type == "table") {
                                box.find(".kgo-scroll-head").css("background-color", val);
                            } else {
                                box.find("#autohead_thead").css("background-color", val);

                            }
                            break;
                        case "bodybgcolor":
                            if (prop.type == "table") {
                                box.find(".kgo-scroll-body").css("background-color", val);
                            } else {
                                box.find("#autohead_tbody").css("background-color", val);

                            }
                            break;
                        case "bordercolor":
                            if (prop.type == "table") {
                                box.find(".kgo-scroll-sty").css("border-color", val);
                                box.find(".kgo-scroll-body li").css("border-color", val);
                                box.find(".kgo-scroll-head").css("border-bottom-color", val);

                            } else {
                                box.find("#autohead_tbody > tr >td").css("border", "1px solid "+val);
                                box.find("#autohead_thead > tr >th").css("border", "1px solid "+val);
                            }
                            break;
                        default:
                            break;
                    }
                }else if (keys[0] == "dt") {
                    setDetailProp(keys[1],val,box,prop);
                }else if (keys[0] == "progress") {
                    setProgressStyle(prop,box);
                }else if (keys[0] == "triangle") {
                    setTriangleStyle(prop,box);
                }else if (keys[0] == "tabpageStyle") {
                    setTabPageStyle(prop,box);
                }else if (keys[0] == "select") {
                    setTagFilterSelect(prop,box.find(".tag-filter-select"));
                }
                undoRecord();
            }

            function setJsonValue(json, key, val, flag, pindex) {
                var keys = key.split(".");
                if (!json.hasOwnProperty(keys[0])) {
                    json[keys[0]] = {};
                }
                var obj = json[keys[0]];
                if (Object.prototype.toString.apply(obj) === "[object Array]") {
                    setJsonArray(obj, 0, keys, val, flag, pindex);
                } else if (Object.prototype.toString.apply(obj) === "[object Object]") {
                    setJsonObject(obj, 0, keys, val, flag, pindex);
                } else {
                    if (val == "delObj") {
                        delete json[keys[0]];
                    } else {
                        json[keys[0]] = val;
                    }
                }

            }


            function setJsonObject(json, index, keys, val, flag, pindex) {
                if (index < keys.length - 1) {
                    if (!json.hasOwnProperty(keys[index + 1]) && (index + 1) != (keys.length - 1)) {
                        json[keys[index + 1]] = {};
                    }
                    var obj = json[keys[index + 1]];
                    if (Object.prototype.toString.apply(obj) === "[object Array]") {
                        setJsonArray(obj, index + 1, keys, val, flag, pindex);
                    } else if (Object.prototype.toString.apply(obj) === "[object Object]") {
                        setJsonObject(obj, index + 1, keys, val, flag, pindex);
                    } else {
                        if (val == "delObj") {
                            delete json[keys[index + 1]];
                        } else {
                            json[keys[index + 1]] = val;
                        }
                    }
                }
            }

            function setJsonArray(json, index, keys, val, flag, pindex) {
                if (flag && index < keys.length - 1) {
                    if (flag == keys[index]) {
                        var node = json[pindex];
                        if (!node.hasOwnProperty(keys[index + 1]) && (index + 1) != (keys.length - 1)) {
                            node[keys[index + 1]] = {};
                        } else if ((index + 1) == (keys.length - 1)) {
                            node[keys[index + 1]] = val;
                        }
                        var obj = node[keys[index + 1]];
                        if (Object.prototype.toString.apply(obj) === '[object Array]') {
                            setJsonArray(obj, index + 1, keys, val, flag, pindex);
                        } else if (Object.prototype.toString.apply(obj) === '[object Object]') {
                            setJsonObject(obj, index + 1, keys, val, flag, pindex);
                        } else {
                            if (val == "delObj") {
                                delete node[keys[index + 1]];
                            } else {
                                node[keys[index + 1]] = val;
                            }
                        }
                    }

                } else {
                    $.each(json, function (i, node) {
                        if (index < keys.length - 1) {
                            if (!node.hasOwnProperty(keys[index + 1]) && (index + 1) != (keys.length - 1)) {
                                node[keys[index + 1]] = {};
                            }
                            var obj = node[keys[index + 1]];
                            if (Object.prototype.toString.apply(obj) === '[object Array]') {
                                setJsonArray(obj, index + 1, keys, val, flag, pindex);
                            } else if (Object.prototype.toString.apply(obj) === '[object Object]') {
                                setJsonObject(obj, index + 1, keys, val, flag, pindex);
                            } else {
                                if (val == "delObj") {
                                    delete node[keys[index + 1]];
                                } else {
                                    node[keys[index + 1]] = val;
                                }
                            }
                        } else {
                            json[i] = val[i];
                        }
                    });
                }
            }

            function delBoxPropVal(elem,opt){
                var panel = elem.closest(".ui-tabs-panel").data();
                if (panel.keyname == "series") {
                    setBoxPropVal(opt.keyName, "delObj", panel.keyname, panel.index);
                } else {
                    setBoxPropVal(opt.keyName,"delObj", false);
                }
            }
            function isEmptyObject(e) {
                var t;
                for (t in e)
                    return !1;
                return !0
            }




            function isArray(obj) {
                return Object.prototype.toString.apply(obj) === '[object Array]';
            }

            function isObjecty(obj) {
                return Object.prototype.toString.apply(obj) === '[object Object]';
            }

            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if (typeof method === 'object' || !method) {
                return methods.init.apply(this, arguments);
            } else {
                $.error('Method ' + method + ' does not exist on jQuery.popupSelection');
            }
        },
        propTabs: function (options) {
            var settings = $.extend({
                width: 150
            }, options);
            return this.each(function () {
                var elem = $(this);
                elem.find(".prop-tabs-item").each(function () {
                    var flag = 0;
                    var i = 0;
                    var width = settings.width;
                    $(this).css("width", width + "px");
                    elem.find(".prop-tabs-item").click(function () {
                        var index = $(this).index();
                        elem.find('.prop-tabs-item').removeClass('show');
                        $(this).addClass('show');
                        elem.find('.prop-tabs-page').removeClass('show');
                        elem.find('.prop-tabs-page').eq(index).addClass('show');
                    });
                });
                elem.find('.prop-tabs-item').eq(0).addClass("show");
                elem.find('.prop-tabs-page').eq(0).addClass('show');
            });
        }
    })
})(jQuery)