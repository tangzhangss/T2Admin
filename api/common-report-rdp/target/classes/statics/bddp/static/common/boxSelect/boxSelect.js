var initSelectBox = function (selector, selectCallback) {
    function clearBubble(e) {
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        if (e.preventDefault) {
            e.preventDefault();
        } else {
            e.returnValue = false;
        }
    }
    var $container = $(selector);
    //  框选事件
    $container.on('mousedown', function (eventDown) {
        if (eventDown.shiftKey) {
            selectableItmes =[];
            $(selector).find('.box').removeClass('temp-selected selected');
            //  设置选择的标识
            var isSelect = true;
            //  创建选框节点
            var $selectBoxDashed = $('<div class="select-box-dashed"></div>');
            $('body').append($selectBoxDashed);
            //  设置选框的初始位置
            var startX = eventDown.x || eventDown.clientX;
            var startY = eventDown.y || eventDown.clientY;
            $selectBoxDashed.css({
                left: startX,
                top: startY
            });
            //  根据鼠标移动，设置选框宽高
            var _x = null;
            var _y = null;
            //  清除事件冒泡、捕获
            clearBubble(eventDown);
            //  监听鼠标移动事件
            $(selector).on('mousemove', function (eventMove) {
                var zoom = $("#content").data("zoom") || 1;
                var pleft = $("#content").offset().left;
                var ptop = $("#content").offset().top;
                //  设置选框可见
                $selectBoxDashed.css('display', 'block');
                //  根据鼠标移动，设置选框的位置、宽高
                _x = eventMove.x || eventMove.clientX;
                _y = eventMove.y || eventMove.clientY;
                //  暂存选框的位置及宽高，用于将 select-item 选中
                var _left = Math.min(_x, startX);
                var _top = Math.min(_y, startY);
                var _width = Math.abs(_x - startX);
                var _height = Math.abs(_y - startY);
                $selectBoxDashed.css({
                    left: _left,
                    top: _top,
                    width: _width,
                    height: _height
                });
                //  遍历容器中的选项，进行选中操作
                $(selector).find('.box').each(function () {
                    var $item = $(this);
                    if ($item.parents(".swiper-slide").length <= 0) {

                        var itemX_pos = $item.prop('offsetWidth') * zoom + $item.offset().left;
                        var itemY_pos = $item.prop('offsetHeight') * zoom + $item.offset().top;
                        //  判断 select-item 是否与选框有交集，添加选中的效果（ temp-selected ，在事件 mouseup 之后将 temp-selected 替换为 selected）
                        var condition1 = itemX_pos > _left;
                        var condition2 = itemY_pos > _top;
                        var condition3 = $item.offset().left < (_left + _width);
                        var condition4 = $item.offset().top < (_top + _height);
                        if (condition1 && condition2 && condition3 && condition4) {
                            $item.addClass('temp-selected');
                        } else {
                            $item.removeClass('temp-selected');
                        }
                    }
                });
                //  清除事件冒泡、捕获
                clearBubble(eventMove);
            });

            $(document).on('mouseup', function (e) {
                $(selector).off('mousemove');
                if (e.shiftKey) {
                    
                    var boxSelecteds = $(selector).find('.box.temp-selected').addClass('selected');
                    //     console.log(boxSelecteds);
                    $(".sitemap-item").removeClass("on");
                    $.each(boxSelecteds, function () {
                        selectableItmes.push(this);
                        $("#sm-"+this.id).addClass("on");
                    });
                    
                    if (selectCallback) {
                        selectCallback(boxSelecteds);
                    }
                }
                $selectBoxDashed.remove();
                $(selector).find('.box.temp-selected').removeClass('temp-selected');
            });
        }

    });
    $(selector).on("click",".box",function(e){
        if (e.ctrlKey) {
            console.log("select ctrl");
            $(this).addClass('selected');
            selectableItmes.push(this);
            clearBubble(e);
        }
    })
};