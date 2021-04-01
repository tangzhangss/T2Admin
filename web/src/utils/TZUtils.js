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
/*
 树型转换辅助方法
 */
function arrayToTreeAssist(res,array,key){
  //没有元素了
  if(array.length==0)return false;
  //这里需要倒叙遍历因为需要删除子元素
  for(let i = array.length - 1; i >= 0; i--){
    let item = array[i];
    res.some(item2=>{
      if(item[key]==item2.id){
        if(!item2.children)item2.children=[];
        item2.children.push(item);
        array.splice(i,1);//删除这个item
        return true;
      }
      if(item2.children){//有至少一个子元素
        //查询children中是否存在当前元素的父级
        let res = arrayToTreeAssist2(item2.children,item,key);
        if (res!==undefined){//这里可能索引是0
          array.splice(i,1);//删除这个item
          return true;
        }
      }
    })
  }
  //一直循环，知道array里面的元素为空
  arrayToTreeAssist(res,array,key);
}
function arrayToTreeAssist2(children,item,key){
  for (let i=0;i<children.length;i++){
    let c = children[i];
    if(c.id==item[key]){
      if(!c.children) c.children=[];
      c.children.push(item);
      return i;
    }
    if(c.children){
      arrayToTreeAssist2(c.children,item,key);
    }
  }
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
  },
  /*
    对象数组转树型
    数组对象array
    用于关联的key 如:parentId
    由底向上
  */
  arrayToTree:function(array,key){
      let res=[];
      //先找出根元素
      for(let i = array.length - 1; i >= 0; i--){
        let item = array[i];
        if(!item[key]){
          res.push(item);
          array.splice(i,1);//删除这个item
        }
      }
      if(res.length!=0){//存在根目录才执行
        arrayToTreeAssist(res,array,key);
      }
    //res是最后的结果
    return res;
  },
  /**
   * 排序(树状结构)
   * @param arr 数组
   * @param key 排序的字段
   * @param isASC 是否从小到大
   * @param childKey 孩子节点的key
   */
  treeSort(arr,key,isASC=true,childKey='children'){
    let sort = function (arr) {
      if(isASC){
        arr = arr.sort((a,b)=>a[key]-b[key]);
      }else{
        arr = arr.sort((a,b)=>b[key]-a[key]);
      }
      arr.forEach(obj=>{
        if(obj[childKey])sort(obj[childKey]);
      })
    }
    return sort(arr);
  }
}
export default TZUtils
