window.addEventListener('message',function(event){
    if(self==top)return false;//顶层不需要
    console.log("接收message：",event);
    //此处执行事件
    let data = event.data;
    if(data.key=="USER_INFO"){
        //用户信息
        sessionStorage.setItem("userInfo",data.value);
    }
},false)

const userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
/**
 * token组装
 */
const TOKEN_TAG = "X-Token";
const T2Admin_TOKEN=userInfo.token;


const ClientId=userInfo.clientId;
//document.write("<script language=javascript src=../../statics/js/hf.js></script>");
const t2AdminApi={};
t2AdminApi.getJSON=function(url,func){
    $.ajax({
        type: "GET",
        url: url,
        headers:{"X-Token":T2Admin_TOKEN},
        dataType: "json",
        success:func
        }
    );
};
t2AdminApi.ajax=function(url,func){
    $.ajax({
            url: url,
            headers:{"X-Token":T2Admin_TOKEN},
            dataType: "json",
            success:func
        }
    );
};
