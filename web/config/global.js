//用户信息cookie的key
import { createApp } from 'vue';
import App from '@/App.vue'
export const myApp = createApp(App);

window.userInfoCookieKey="userInfo";
window.userMenuCookieKey="userMenu";
window.superAdminClientId="tzcc_ren";//超级管理员客户ID

window.addEventListener('message',function(event){
    if(self==top)return false;//顶层不需要
    //此处执行事件
    let data = event.data;
    if(data.key=="USER_INFO"){
        //用户信息
        sessionStorage.setItem(window.userInfoCookieKey,data.value);
    }
},false)






