function resizeContent(w, h, flag, type) {
    let content = document.getElementById("content");

    let pw = document.documentElement.clientWidth;
    let ph = document.documentElement.clientHeight;
    let cw = w || content.offsetWidth;
    let ch = h || content.offsetHeight;
    let wp = pw / cw;
    let hp = ph / ch;
    let zoom = 1;
    $(".bd-buttons").hide();
    if (type == 3) {//不缩放
        document.body.style.setProperty("overflow", "auto");
        content.style.setProperty('top', "0px");
        content.style.setProperty('left', "0px");
        content.style.setProperty('transform', "scale(1)");
        $(content).data("zoom", 1);
        return false;
    } else if (type == 2) {//按照高度缩放
        zoom = hp;
        content.style.setProperty('margin-top', -((ch - ch * zoom) / 2) + "px");
        content.style.setProperty('margin-left', -((cw - cw * zoom) / 2) + "px");
        document.body.style.setProperty("overflow-x", "auto");
    } else if (type == 1) {//按照宽度缩放
        zoom = wp;
        content.style.setProperty('margin-left', -((cw - cw * zoom) / 2) + "px");
        content.style.setProperty('margin-top', -((ch - ch * zoom) / 2) + "px");
        document.body.style.setProperty("overflow-y", "auto");
    } else {//按屏幕比例缩放
        if (flag) {
            zoom = hp;
            content.style.setProperty('margin-top', -((ch - ch * zoom) / 2) + "px");
            content.style.setProperty('margin-left', ((pw - cw * zoom) / 2) - ((cw - cw * zoom) / 2) + "px");
            document.body.style.setProperty("overflow", "auto");
        } else {
            if (wp < hp) {
                zoom = wp;
                content.style.setProperty('margin-left', -((cw - cw * zoom) / 2) + "px");
                content.style.setProperty('margin-top', ((ph - ch * zoom) / 2) - ((ch - ch * zoom) / 2) + "px");
            } else {
                zoom = hp;
                content.style.setProperty('margin-top', -((ch - ch * zoom) / 2) + "px");
                content.style.setProperty('margin-left', ((pw - cw * zoom) / 2) - ((cw - cw * zoom) / 2) + "px");
            }
        }
        $(".bd-buttons").show();
    }
    content.style.setProperty('top', "0px");
    content.style.setProperty('left', "0px");
    content.style.setProperty('transform', "scale(" + zoom + ")");
    $(content).data("zoom", zoom);
}

window.onresize = function () {
    if (globalZoom == 0 || !globalZoom) {
        resizeContent();
    }
};
window.onload = function () {
    // setTimeout(function () {
        $(".bg").fadeOut(function () {
            initCreateTags()
        });
    // }, 1000);
};
resizeContent(0, 0, false, globalZoom);
contentMove();

/**
 * 拖拽、滚动缩放
 *
 */
function contentMove() {
    var content = $("#content");
    $("body").on('mousewheel', function (event) {
        if (event.altKey) {
            var val = Math.abs(event.deltaFactor * event.deltaY) / 1000;
            console.log(val);
            var zoom = content.data("zoom") || 1
            if (event.deltaY > 0) { // 放大
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
        }
    });

    content.draggable({
        handle: "#contentHandle",
        scroll: false,
        snap: ".layout-Content",
        snapMode: "inner"
    });
    $(document).bind("keydown", function (e) {
        if (e.keyCode == 18) {
            content.addClass("on");
        }
    });
    $(document).bind("keyup", function (e) {
        if (e.keyCode == 18) {
            content.removeClass("on");
        }
    });

    $(document).keyup(function (event) {
        if (event.keyCode == 27 || event.keyCode == 96) {
            resizeContent(0, 0, false, globalZoom);
        }
    });
}



function getFeaturesNames(data) {
    var features = data.features;
    var mapdata = features.map(function (feature) {
        var name = feature.properties.name;
        return {
            name: name
        }
    });

    return mapdata;
}
function getFeaturesNamesValues(data) {
    var features = data.features;
    var mapdata = features.map(function (feature) {
        var name = feature.properties.name;
        return {
            name: name,
            "value|100-2000": 100
        }
    });

    return Mock.mock({
        "data": mapdata
    });
}
