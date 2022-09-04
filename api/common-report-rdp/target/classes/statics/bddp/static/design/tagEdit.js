window.editSwiper = function () {
    // console.log("1111");
}
window.getImagePath = function (inputElem) {
    layx.iframe('localsiteforImage', '图片文件选择', './component/BddpImagesPage.html', {
        event: {
            ondestroy: {
                before: function (layxWindow, winform, params, inside, escKey) {
                    if (params.name) {
                        inputElem.val("../../bddpConfig/" + params.id + "/images/" + params.name);
                        inputElem.change();
                    }
                }
            }
        }
    });
}
window.selectImagePath = function (inputElem) {
    layx.iframe('localsiteforMaterial', '图片文件选择', '../../modules/material/materialShow.html', {
        event: {
            ondestroy: {
                before: function (layxWindow, winform, params, inside, escKey) {
                    // console.log(params);
                    inputElem.val("../../" + params.materialRelativePath);
                    inputElem.change();
                }
            }
        }
    });
}
window.getSelectOpts = function (inputElem) {
    var val = inputElem.val();
    layx.iframe('localsiteforOpts', '选项配置', './component/BddpOptsPage.html?optsStr='+window.btoa(encodeURIComponent(val)), {
        event: {
            ondestroy: {
                before: function (layxWindow, winform, params, inside, escKey) {
                    // console.log(params);
                    if (params.optsStr){
                        inputElem.val(decodeURIComponent(window.atob(params.optsStr)));
                        inputElem.change();
                    }
                }
            }
        }
    });
}

function tagChangeSize(elem) {
    var prop = elem.data("prop");
    var type = prop.type;
    if (type == "swiper") {
        var mySwiper = prop.mySwiper;
        var rectP = prop.rectP;
        // console.log(prop);
        $.each(mySwiper.slides, function (i, slide) {
            $(slide).css({
                "height": rectP.height,
                "width": rectP.width
            });
        });
        mySwiper.updateSize();
        mySwiper.update();
        elem.find(".box").each(function () {
            var myChart = $(this).data("prop").myChart;
            if (myChart) {
                myChart.resize();
            }
        });
    }
}

function addSlide(elem, curr) {
    // console.log(elem, curr);
    layx.html('dom', '可以添加的组件', document.getElementById('charts'), {
        event: {
            // 加载事件
            onload: {
                // 加载之前，return false 不执行
                before: function (layxWindow, winform) {
                    $(layxWindow).css("background-color", "#263954");
                    // console.log(layxWindow, winform);
                },
                // 加载之后
                after: function (layxWindow, winform) {
                    var nav = $(layxWindow).find(".tags-navs");
                    // console.log(nav);
                    nav.find(".tags-nav.off").remove();
                    nav.find(".tags-nav").bind("click", function () {
                        initChartsTag($(this), curr.parent());
                    });
                }
            }
        }

    });
}

function initChartsTag(elem, slide) {
    var type = elem.attr("tag-type");
    var html = $('<div class="box" style="width: 100%;height: 100%;"><div class="tag-charts" style="height:100%;width:100%"></div></div>');
    if (type == "map") {

        layx.iframe('localsiteformap', '选择地图文件', './component/BddpMapPage.html', {
            event: {
                ondestroy: {
                    before: function (layxWindow, winform, params, inside, escKey) {
                        var mappath = params.path;
                        if (!mappath){
                            return true
                        }
                        if (mappath.indexOf('geographic') > -1) {
                            mappath = mappath.substring(mappath.indexOf('geographic') + 'geographic'.length, mappath.length);
                        }
                        getAjaxMapData({
                            mappath: mappath
                        }, function (result) {
                            if (result.code == 0) {
                                data = result.res;
                                var md5Value = hex_md5(data);
                                var mapjson = data;
                                echarts.registerMap(md5Value, mapjson);
                                swiperCreatMapChart(html, md5Value, mappath);
                            }
                        });
                    }
                }
            }
        });
    } else {
        swiperCreateCharts(type, html);
    }
    slide.empty().append(html);
    html.find(".tag-charts").bind("click", function () {
        $(".box").removeClass("box-selected");
        html.addClass("box-selected");
        $("#rightnav").addClass("on");
        getProp($(this).parent(), true);
    });
    layx.destroy("dom");
}

function swiperCreateCharts(type, elem) {

    if (!globalChartTheme) {
        globalChartTheme = 'default';
    }
    var file = "../../statics/bddp/static/charts-config/" + type + ".json";
    var themeFile = "../../statics/bddp/static/charts-theme/" + globalChartTheme + ".json";
    getJSONFileData(themeFile, function (themedata) {
        var theme = themedata;
        echarts.registerTheme(globalChartTheme, theme);
        getJSONFileData(file, function (data) {
            var tempProp = elem.data("prop");
            var options = data;
            var dom = elem.find(".tag-charts")[0];
            var myChart = echarts.init(dom, globalChartTheme);
            myChart.setOption(options, true);
            var prop = $.extend({}, tempProp, {
                options: options,
                myChart: myChart,
                type: type,
                other: {},
                bigType: "chart",
                slide: true,
                other: {
                    theme: globalChartTheme
                },
                id: guid()
            })
            elem.data("prop", prop);
        });
    });
}

function swiperCreatMapChart(elem, md5, mappath) {
    var options = {
        geo: {
            show: true,
            map: md5,
            label: {
                normal: {
                    show: true,
                    color: "#ffffff"
                },
                emphasis: {
                    show: true,
                }
            },
            roam: true,
            itemStyle: {
                normal: {
                    areaColor: 'transparent',
                    borderColor: '#3fdaff',
                    borderWidth: 2,
                },
                emphasis: {
                    areaColor: '#2B91B7',
                }
            }
        }
    };
    var dom = elem.find(".tag-charts")[0];
    var myChart = echarts.init(dom);
    myChart.setOption(options, true);
    var tempProp = elem.data("prop");
    var prop = $.extend({}, tempProp, {
        options: options,
        myChart: myChart,
        type: "map",
        slide: true,
        other: {
            mappath: mappath
        },
        id: guid()
    })
    elem.data("prop", prop);
}



window.prependSlide = function (elem) {
    var mySwiper =  currBox.data("prop")["mySwiper"];
    var div = $('<div class="swiper-slide"><div class="fa fa-plus">new</div></div>');
    mySwiper.prependSlide(div);
    div.find(".fa.fa-plus").bind("click", function () {
        addSlide(currBox, $(this));
    })
}
window.appendSlide = function (elem) {
    var mySwiper =  currBox.data("prop")["mySwiper"];
    var div = $('<div class="swiper-slide"><div class="fa fa-plus">new</div></div>');
    mySwiper.appendSlide(div);
    div.find(".fa.fa-plus").bind("click", function () {
        addSlide(currBox, $(this));
    })
}
window.removeSlide = function (elem) {
    console.log(currBox);
    var mySwiper =  currBox.data("prop")["mySwiper"];
    var slides =  currBox.data("prop")["slides"];
    layx.html('delswiper','选择要删除的轮播页面','<div class="swiper-items-view"></div>',{
        statusBar:true,
        skin: "asphalt",
        width:670,
        buttons:[
            {
                label:'确认',
                callback:function(id){
                    console.log(id);
                    var winform = layx.getWindow(id);
                    var layxWindow = winform.layxWindow;
                    var indexs = $(layxWindow).find(".swiper-slide-view.on").map(function(index,d){
                        return $(d).index();
                    });
                    mySwiper.removeSlide(indexs);
                        indexs.sort(function(a, b) {
                            return b-a;
                        })
                    for (var i =0;i < indexs.length;i++){
                        slides.splice(indexs[i],1);
                    }
                    console.log(indexs);
                    layx.destroy(id);    
                }
            },
            {
                label:'取消',
                callback:function(id){
                    layx.destroy(id);
                }
            }
        ],
        event:{
            onload:{
                before:function(layxWindow,winform){
                    slides =[];
                    currBox.find(".box").each(function () {
                        var slideProp = $(this).data("prop");
                        slides.push(slideProp);
                      });
                },
                after: function (layxWindow, winform) {
                    $(layxWindow).find(".swiper-items-view").on("click",".swiper-slide-view",function(){
                        if($(this).hasClass("on")){
                            $(this).removeClass("on");
                        }else{
                            $(this).addClass("on");
                        }
                    })
                    $.each(slides, function(index, slide) {
                        var myChart = slide.myChart;
                        var itemDiv = $('<div class="swiper-slide-view"></div>');
                        itemDiv.css("background-image","url("+myChart.getDataURL()+")");
                        $(layxWindow).find(".swiper-items-view").append(itemDiv);
                    })
                }
            }
        }
    });
}