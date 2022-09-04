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
    "candlestick",
    "image",
    "swiper"];
function hidePanelCtrl(flag) {
    if (flag) {
        // $(".hide-shade").remove();
        removeCloneBoxs();
    } else {
        // var shade = $('<div class="hide-shade"></div>');
        // shade.appendTo("body");
        getShowBoxs();
    }
}
function getShowBoxs() {
    var boxs = [];
    $("#content").find(".box").each(function () {
        if (HideTagsType.indexOf($(this).attr("tag-type")) > -1) {
            var box = $(this);
            var div = $('<div class="box-clone"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i></div>');
            div.attr("style", box.attr("style"));
            div.attr("boxid", box.attr("id"));
            div.css("z-index", "1010");
            div.bind("click", function () {
                moveBoxToHidePanel($(this).attr("boxid"));
                $(this).remove();
            });
           

            div.appendTo("#content");
            boxs.push(div);
        }
    })
    return boxs;
}

function removeCloneBoxs() {
    $("#content").find(".box-clone").remove();
}

function moveBoxToHidePanel(id) {
    var box = $("#content").find("#" + id);
    box.attr("class", "box-hide");
    box.data("style",box.attr("style"));
    box.attr("style","");
    box.appendTo("#hide-boxs");
    var shade = $('<div class="box-shade"></div>');
    box.append(shade);
    shade.bind("click",function(){
        return false;
    });
    shade.bind("mousedown",function(){
        return false;
    });
    var delbtn = $('<div class="hide-del hide-btn"><i class="fa fa-trash-o" aria-hidden="true"></i></div>');
    var backbtn = $('<div class="hide-back hide-btn"><i class="fa fa-chevron-circle-right" aria-hidden="true"></i></div>');
    shade.append(delbtn).append(backbtn);
    delbtn.bind("click",function(){
        $(this).parents(".box-hide").remove();
    });
    backbtn.bind("click",function(){
       var tempbox =  $(this).parents(".box-hide");
       tempbox.find(".box-shade").remove();
       tempbox.attr("class", "box");
       tempbox.appendTo("#content");
       tempbox.attr("style",box.data("style"));
       var chart = echarts.getInstanceByDom(tempbox.find(".tag-charts")[0]);
       chart.resize();
       removeCloneBoxs();
       getShowBoxs()
    });

    var chart = echarts.getInstanceByDom(box.find(".tag-charts")[0]);
    if (chart) {
        chart.resize();
    }
}