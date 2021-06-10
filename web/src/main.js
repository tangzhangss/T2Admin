//全局配置信息
import {myApp} from "../config/global";
//前端组件库
console.log("---------")
import webComponent from 'tangzhangss-web-component';
import 'tangzhangss-web-component/dist/tangzhangss-web-component.css';
console.log("已引入ＴＺＣＣ＿ＲＥＮ　ＡＤＭＩＮ　ＳＹＳＴＥＭ前端组件:",webComponent)
console.log("---------")
import ElementPlus from 'element-plus';
import 'dayjs/locale/zh-cn';
import locale from 'element-plus/lib/locale/lang/zh-cn';
import 'element-plus/lib/theme-chalk/index.css';
import './styles/index.scss'; // global css
import store from './store';
import {router} from './router';
import './icons'; // icon
import './permission';
import cookie from "js-cookie";

import http from './utils/request';

//业务工具类
import service_tool from "./utils/service_tool";


//全局工具方法
myApp.config.globalProperties.$cookie = cookie;
myApp.config.globalProperties.$http = http;
myApp.config.globalProperties.TZUtils = webComponent.TZUtils;
myApp.config.globalProperties.$service_tool = service_tool;

//设置vue指令 框架中用到的所有
webComponent.setAllVueDirective(myApp);
myApp.use(router).use(store).use(webComponent).use(ElementPlus,{locale}).mount('#app')
