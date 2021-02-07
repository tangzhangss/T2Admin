import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementPlus from 'element-plus';
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import App from './App'
import store from './store'
import router from './router'
import http from '@/utils/request'
import cookie from 'js-cookie'
import '@/icons' // icon
import '@/permission' // permission control


//全局js文件--常量配置
import "../config/global"
import TZUtils from "@/utils/TZUtils"
import {createApp} from "vue";
const app = createApp(App);
app.use(store);
app.use(router);
app.use(ElementPlus);
//全局工具方法
app.config.globalProperties.$cookie = cookie;
app.config.globalProperties.$http = http;
app.config.globalProperties.TZUtils = TZUtils;

const vm = app.mount('#app')



