function timesFun (timesData) {
    //如果时间格式是正确的，那下面这一步转化时间格式就可以不用了
    var dateBegin = new Date(Number(timesData));//将-转化为/，使用new Date
    // var dateBegin = new Date(timesData.replace(/-/g, "/"));//将-转化为/，使用new Date
    var dateEnd = new Date();//获取当前时间
    var dateDiff = dateEnd.getTime() - dateBegin.getTime();//时间差的毫秒数
    var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
    var leave1 = dateDiff % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000))//计算出小时数
    //计算相差分钟数
    var leave2 = leave1 % (3600 * 1000)    //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000))//计算相差分钟数
    //计算相差秒数
    var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000);
    var timesString = '';

    if (dayDiff != 0) {
        if (dayDiff>30) {
            
            timesString = (dayDiff/30).toFixed(0) + '月之前';
        } else {
            timesString = dayDiff + '天之前';
        }
    } else if (dayDiff == 0 && hours != 0) {
        timesString = hours + '小时之前';
    } else if (dayDiff == 0 && hours == 0) {
        if (minutes<=0) {
            timesString = '刚刚';
            
        } else {
            timesString = minutes + '分钟之前';
            
        }
    }

    return {
        timesString: timesString
    }
}
function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}

function guid() {
    return (S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
}
//获取URL餐宿
// function getQueryString(name) {
// 	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
// 	var r = window.location.search.substr(1).match(reg);
// 	if (r != null) {
// 		return unescape(r[2]);
// 	}
// 	return null;
// }