$(function () {
    getJSONFileData("../../bddp/getBddpFolders", function (res) {
        if (res.code == 0) {
            var fileArrs = res.res;
            fileArrs.sort(function (a, b) {
                return a.time < b.time ? 1 : -1;
            });
            for (var i = 0; i < fileArrs.length; i++) {
                var file = fileArrs[i];
                var ul = $('<ul class="lt-tr"><li class="lt-td uuid"></li><li class="lt-td image"></li><li class="lt-td filename"></li></ul>');
                ul.data("path", file.path);
                ul.data("name", file.name);
                ul.data("bdname", file.bdname);
                ul.find(".uuid").html(file.name);
                ul.find(".filename").html(file.bdname);
                ul.find(".image").css("background-image", "url('../../bddpshow/bgi/" + file.name + "')");
                ul.find(".lasttime").html(timesFun(file.time).timesString);

                ul.bind("click", function () {
                    window.open("../../bddpshow/show/" + $(this).data("name"));
                });
                $(".hl-body").append(ul);
            }
        }
    });




    $(".fa-th").bind("click", function () {
        hidelist();
    });
    $(".fa-list-ul").bind("click", function () {
        showlist();
    });
    $(".hl-search").bind("input propertychange", function () {
        var kw = this.value;
        //console.log(this.value);
        var display = !$.getCache("home-list") ? "flex" : "inline-block";
        if (kw) {

            $.each($(".lt-tr"), function (i, ul) {
                var bdname = $(ul).data("bdname");
                if (bdname.indexOf(kw) > -1) {
                    $(ul).show().css("display", display);
                } else {
                    $(ul).hide();
                }
            });
        } else {
            $(".lt-tr").show().css("display", display);
        }
    })
    $("#newBddpBtn").bind("click", function () {
        layx.prompt('新建大屏幕报表', '请输入大屏幕报表名称', function (id, value, textarea, button, event) {
            var bdData = {
                "content": {
                    "width": 1920,
                    "height": 1080,
                    "globalChartTheme": "default",
                    "id": guid(),
                    "sceneName": value
                },
                "boxs": [],
                "ruler": {
                    "v": [],
                    "h": []
                }
            };
            saveBddpData(bdData, function (res) {
                if (res.code == 0) {
                    console.log("新建成功");
                    window.open("design.html?uuid=" + bdData.content.id);
                    location.reload();
                } else {
                    console.log("新建失败");
                }
            });
        }, null, {
            skin: 'asphalt',
            storeStatus: false,
            shadable: true,
            width: 300,
            height: 100
        });
    });
    //if (!$.getCache("home-list")) {
     //   showlist();
    //} else {
        hidelist();
    //}


    $("#importBddpBtn").bind("click", function () {
        layx.iframe('localsiteforfile', '模版文件上传', './component/BddpFilePage.html', {
            event: {
                ondestroy: {
                    before: function (layxWindow, winform, params, inside, escKey) {
                    },
                    after: function () {
                        location.reload();
                    }
                }
            }
        });
    })




});

function showlist() {
    $(".hl-body").removeClass("on");
    $(".fa-th").removeClass("on");
    $(".fa-list-ul").addClass("on");
    $.setCache("home-list", false);
    $(".lt-tr").not(":hidden").css("display", "flex");
}

function hidelist() {
    $(".hl-body").addClass("on");
    $(".fa-list-ul").removeClass("on");
    $(".fa-th").addClass("on");
    $.setCache("home-list", true);
    $(".lt-tr").not(":hidden").css("display", "inline-block");
}