//全局配置信息
import {myApp} from "../config/global";

import ElementPlus from 'element-plus';
import 'dayjs/locale/zh-cn';
import locale from 'element-plus/lib/locale/lang/zh-cn';

import 'element-plus/lib/theme-chalk/index.css';
import './styles/index.scss' // global css
import store from './store'
import {router} from './router'
import http from './utils/request';
import TZUtils from './utils/TZUtils';
import './icons' // icon
import './permission'
import cookie from "js-cookie";


//全局工具方法
myApp.config.globalProperties.$cookie = cookie;
myApp.config.globalProperties.$http = http;
myApp.config.globalProperties.TZUtils = TZUtils;

myApp.use(router).use(store).use(ElementPlus,{locale}).mount('#app')
