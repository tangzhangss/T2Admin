/* 
 * @discription 用于前端页面缓存JSON格式的数据
 * @author huangshipiao      
 * @created 2017年6月22日 下午6:26:59     
 */
(function (window, undefined) {
    var config = {
        expire: 1440, //缓存数据默认十分钟失效
        debug: false
    }


    function setCache(key, obj) {
        var data = JSON.stringify(obj);
        var time = new Date().getTime();
        var jsonData = {
            "data": obj,
            "cacheTime": time
        };
        if (config.debug) {
            console.log("设置缓存数据：" + data);
        }
        return localStorage.setItem(key, JSON.stringify(jsonData));
    }

    function getCache(key) {
        if (!key) {
            if (config.debug) {
                console.log("key为空，key=【" + key + "】");
            }
            return "";
        }
        var jsonData = JSON.parse(localStorage.getItem(key));
        if (!jsonData) {
            if (config.debug) {
                console.log("【" + key + "】缓存的数据不存在");
            }
            return "";
        }
        var nowDate = new Date().getTime();
        var cacheTime = new Date(jsonData.cacheTime).getTime();
        if (apartMinutes(cacheTime, nowDate) > config.expire) {
            if (config.debug) {
                console.log("超过" + config.expire + "分钟 ,【" + key + "】缓存失效!");
            }
            return "";
        }
        if (config.debug) {
            console.log("【" + key + "】获取到的缓存数据：" + jsonData.data);
        }
        return jsonData.data;
    }


    function apartMinutes(date1, date2) {
        var date3 = date2 - date1;
        var minutes = Math.floor(date3 / (60 * 1000));
        if (config.debug) {
            console.log("数据已缓存时间：" + minutes + "分钟");
        }
        return minutes;
    }

    var cache = {
        getCache: getCache,
        setCache: setCache,
        configCache: function (obj) {
            $.extend(config, obj);
        }
    };

    window.jQuery && ($.extend(window.jQuery, cache));

})(window);