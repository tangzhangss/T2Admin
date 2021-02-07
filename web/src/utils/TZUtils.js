/*
对象拷贝
 */
function deepCopy(target, source) {
  Object.keys(source).forEach((key) => {
    if (getType(source[key]) === 'Object') {
      target[key] = {};
      deepCopy(target[key],source[key]);
    } else if (getType(source[key]) === 'Array') {
      target[key] = [];
      deepCopy(target[key],source[key])
    } else {
      target[key] = source[key];
    }
  })
  return target;
}
//获取对象类型
function getType(val){
  let reg = /^\[object\s(\w*)\]$/;
  return reg.exec(Object.prototype.toString.call(val))[1];
}



const TZUtils={
  formatDate:function(date, fmt) {
    if(!date)return "";
    date=new Date(date);
    if(fmt == undefined){
      fmt = "yyyy-MM-dd HH:mm";
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'H+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds()
    };
    for (let k in o) {
      if (new RegExp(`(${k})`).test(fmt)) {
        let str = o[k] + '';
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
      }
    }
    return fmt;
  },
  //友好时间转换
  friendlyTime: function(time1){
    var time1 = new Date(time1);//需要转换的时间

    var time2 = new Date();//当前时间
    var time = 0;//时间差
    //需要转换的不可能比当前时间大..如果有 自行添加判断
    time = time2 - time1;
    if (time < 1000) return "刚刚";
    time = parseInt(time / 1000);
    if (time > 86400) {
      var day = parseInt(time / (24 * 60 * 60));
      if (day == 1) {
        let min = time1.getMinutes();
        min = min > 9 ? min : '0' + min;
        return "昨天(" + time1.getHours() + ":" + min + ")";
      } else if (day < 30) {
        return day + "天前";
      } else if (day < 360) {
        var moth = parseInt(day / 30);
        return moth + "个月前";
      } else {
        var year = parseInt(day / 360);
        return year + "年前";
      }
      ;
    } else if (time > 3600) {
      var hour = parseInt(time / (60 * 60));
      return hour + "小时前";
    } else if (time > 60) {
      var hour = parseInt(time / 60);
      return hour + "分钟前";
    } else {
      return time + "秒前";
    }
  },
  /*
  深拷贝
    1.空数组会拷贝成空串
    2.不能复制function
   */
  deepClone:function (obj) {
    if (!obj)return obj;
    return JSON.parse(JSON.stringify(obj));
  },
  //深拷贝非json方式
  deepClone2:function(obj,target={}){
    return deepCopy(target,obj);
  },
  deepClone2Array:function(array){
    let target=[];
    array.forEach(obj=>{
      target.push(deepCopy({},obj))
    })
    return target;
  },
  getType:function(obj){
    return getType(obj);
  },
  trim:function(str,option){
    if(typeof str != "string")return str;//不是String类型直接返回
    if(option=="S"){
      //去除开头空白
      return str.replace(/(^\s*)/g, "");
    }
    if(option=="E"){
      //去除结尾空白
      return str.replace(/(\s*$)/g, "");
    }
    //去除首位空白
    return str.replace(/(^\s*)|(\s*$)/g, "");
  },
  fullLoading(_this,title){
    return _this.$loading({
      lock: true,
      text: title || '处理中',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.6)'
    });
  }
}
export default TZUtils
