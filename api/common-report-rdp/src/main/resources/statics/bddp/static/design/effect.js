(function ($) {
    $.fn.extend({
        autoScroll: function (time) {
            var timecount = time || 1500;
            return this.each(function () {
                var tablebody = $(this).find(".kgo-scroll-body-ul");
                var z = 0;//向上滚动top值
                function up() {//向上滚动
                    tablebody.animate({//中奖结果
                        'margin-top': (-tablebody.find("li:first").height()) + 'px',
                        'margin-bottom': (tablebody.find("li:first").height()) + 'px'
                    }, timecount, 'linear', function () {
                        tablebody.css({
                            'margin-top': '0px',
                            'margin-bottom': '0px'
                        }).find("li:first").appendTo(tablebody);
                        up();
                    });
                }

                up();
            });
        }, stopScroll: function () {
            var tablebody = $(this).find(".kgo-scroll-body-ul");
            tablebody.stop(true);
        }
    })
})(jQuery);


function dataSort(dimensionVals, seriesVals, datasort,objectData) {
    var data = [];
    $.each(dimensionVals, function (index, dv) {
        data.push({
            name: dv,
            value: seriesVals[index],
            data:(objectData?objectData[index]:{})
        })
    });
    if (datasort && datasort != 0) {

        data.sort(compareSort("value", datasort));
    }
    dimensionVals = data.map(function (d) {
        return d.name;
    });
    seriesVals = data;
    return {
        dimensionVals: dimensionVals,
        seriesVals: seriesVals
    }
}

//数据排序
var compareSort = function (prop, datasort) {
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
            val1 = Number(val1);
            val2 = Number(val2);
        }
        if (datasort != 1) {
            return val2 - val1; //升序
        } else {
            return val1 - val2;//
        }


    }
};

function formatNum(str){
    str+="";
    return str.replace(/[0-9]+?(?=(?:([0-9]{3}))+$)/g,function(a){return a+','})
}

function formatMoney(str) {
    var newStr = "";
    var count = 0;
    str+="";
    if (str.indexOf(".") == -1) {
        for (var i = str.length - 1; i >= 0; i--) {
            if (count % 3 == 0 && count != 0) {
                newStr = str.charAt(i) + "," + newStr;
            } else {
                newStr = str.charAt(i) + newStr;
            }
            count++;
        }
        str = newStr + ".00"; //自动补小数点后两位
        // console.log(str)
    }
    else {
        for (var i = str.indexOf(".") - 1; i >= 0; i--) {
            if (count % 3 == 0 && count != 0) {
                newStr = str.charAt(i) + "," + newStr; //碰到3的倍数则加上“,”号
            } else {
                newStr = str.charAt(i) + newStr; //逐个字符相接起来
            }
            count++;
        }
        str = newStr + (str + "00").substr((str + "00").indexOf("."), 3);
        //  console.log(str)
    }

    return str;
}