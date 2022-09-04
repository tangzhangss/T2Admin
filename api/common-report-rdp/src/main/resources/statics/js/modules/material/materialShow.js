var MT_PAGE = { currPage: 1 }, MT_GROUP = "";
$(function () {
  //  initMaterial();
    initGroups();
    $("#mt-items").on("click",".mt-box",function(){
        var params = $(this).data();
        top.layx.destroy("localsiteforMaterial", params);
    })
});

function initMaterial() {
    $("#mt-items").empty();
    $.ajax({
        type: "get",
        url: baseURL + "material/material/list",
        data: { page: MT_PAGE.currPage, group: MT_GROUP },
        dataType: "json",
        success: function (response) {
            createMtBox(response.page.list);
            MT_PAGE = response.page;
            waitImgsLoaded(function () {
                initwaterfall();
            });
            if (MT_PAGE.currPage == MT_PAGE.totalPage) {
                $("#mt-info").html("没有更多了");
            } else {
                $("#mt-info").html("");
            }
        }
    });



}
function initGroups() {
    $.ajax({
        type: "get",
        url: baseURL + "material/material/groups",
        data: { page: MT_PAGE.currPage, group: MT_GROUP },
        dataType: "json",
        success: function (response) {
            createGroups(response.list);
        }
    });
}

function openUpload() {
    top.layer.open({
        type: 2,
        title: '上传',
        // shadeClose: true,
        shade: 0.8,
        area: ['80%', '80%'],
        content: './modules/material/upload.html', //iframe的url
        cancel: function (index, layero) {
            location.reload();
        }
    });
}

function createGroups(data) {

    $.each(data, function (i, node) {
        var radio;
        if (i == 0) {
            radio = $('<input value="' + node.materialGroup + '" type="radio" id="group' + i + '" name="group"  checked="checked"><label for="group' + i + '" >' + node.materialGroup + '</label>');
            MT_GROUP = node.materialGroup;
        } else {
            radio = $('<input value="' + node.materialGroup + '" type="radio" id="group' + i + '" name="group"><label for="group' + i + '">' + node.materialGroup + '</label>');
        }
        $("#mt-group").append(radio);
    });

    $("input[name=group]").checkboxradio({
        icon: false
    });
    $("input[name=group]").on("change", function (e) {
        var target = $(e.target);
        var checked = target.is(":checked");
        // console.log(target, checked);
        // console.log($(e.target).val());
        MT_PAGE.currPage = 1;
        MT_GROUP = $(e.target).val();
        initMaterial();


    });
    $("#mt-group").controlgroup();
    initMaterial();


}
function createMtBox(data) {
    //     console.log(data);
    $.each(data, function (i, node) {
        var box = $('<div class="mt-box" style="opacity:0;filter:alpha(opacity=0);"><img onerror="javascript:this.src=\'../../statics/images/upload/image.png\'"  class="cover" src="../../' + node.materialRelativePath + '" alt=""></div>')
        box.data(node);
        $("#mt-items").append(box);
    })
}



function initwaterfall() {
    $("#mt-items").waterfall({
        itemClass: ".mt-box",
        minColCount: 2,
        spacingHeight: 10,
        resizeable: true,
        ajaxCallback: function (success, end) {
            if ((MT_PAGE.currPage + 1) <= MT_PAGE.totalPage) {
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    type: "get",
                    url: baseURL + "material/material/list",
                    data: { page: (MT_PAGE.currPage + 1), group: MT_GROUP },
                    dataType: "json",
                    success: function (response) {
                        console.log(response);
                        createMtBox(response.page.list);
                        MT_PAGE = response.page;
                        waitImgsLoaded(function () {
                            success();
                            end();
                        });
                    },
                    complete:function(response,code){
                        layer.close(index);
                    }
                });
                $("#mt-info").html("");
            } else {
                $("#mt-info").html("没有更多了");
            }

        }
    });
}

function waitImgsLoaded(func) {
    var t_img; // 定时器
    var isLoad = true; // 控制变量

    // 判断图片加载状况，加载完成后回调
    isImgLoad(function () {
        func();
    });

    // 判断图片加载的函数
    function isImgLoad(callback) {
        // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
        // 查找所有封面图，迭代处理
        $('.cover').each(function () {
            // 找到为0就将isLoad设为false，并退出each
            if (this.height === 0) {
                isLoad = false;
                return false;
            }
        });
        // 为true，没有发现为0的。加载完毕
        if (isLoad) {
            clearTimeout(t_img); // 清除定时器
            // 回调函数
            callback();
            // 为false，因为找到了没有加载完成的图，将调用定时器递归
        } else {
            isLoad = true;
            t_img = setTimeout(function () {
                isImgLoad(callback); // 递归扫描
            }, 500); // 我这里设置的是500毫秒就扫描一次，可以自己调整
        }
    }
}