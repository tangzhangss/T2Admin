var MSG_UNLOAD = "如果你此时离开报表设计器，所做操作信息将全部丢失，是否离开?";
var UnloadConfirm = {};
//启用监听浏览器刷新、关闭的方法
UnloadConfirm.set = function (confirm_msg) {
    window.onbeforeunload = function (event) {
        event = event || window.event;
        event.returnValue = confirm_msg;
    }
}
//关闭监听浏览器刷新、关闭的方法
UnloadConfirm.clear = function () {
    window.onbeforeunload = function () {};
}
UnloadConfirm.set(MSG_UNLOAD);