import {router} from './router'
import store from './store'
import cookie from 'js-cookie';
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import getPageTitle from "@/utils/get-page-title"

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login','/test'] // no redirect whitelist

router.beforeEach(async(to, from) => {

  // start progress bar
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)
  let userInfo = store.getters.userInfo;

  if(!userInfo&&top!=self){
    let getUserInfo = function () {
      return new Promise((resolve)=>{
        let intervalId = setInterval(()=>{
          userInfo = JSON.parse(sessionStorage.getItem(window.userInfoCookieKey));
          if(userInfo) {
            resolve(userInfo);
            clearInterval(intervalId);
          }
        },400)
      });
    }
    await getUserInfo();
    console.log(userInfo,".........");
    store.commit("permission/SET_USER_INFO",userInfo);
  }

  if (userInfo) {
    if (to.path === '/login') {
      NProgress.done()
    } else {
      let userMenu = store.getters.userMenu;
      if(!userMenu){
        try{
          const routes = await store.dispatch('permission/generateRoutes');
          for (let i=0;i<routes.length;i++){
             await router.addRoute(routes[i]);
          }
          // await router.addRoute(router404);//404页面放在最后-vue-router4不需要了
          // console.log("routers:",routes,router.getRoutes());
          if(!from.name){//表示是刷新进入的
            let latestRoute = cookie.get("latestRoute");
            if(latestRoute && latestRoute!='/404'){
              return latestRoute
            }
            //刷新进入，如果没有路由缓存就直接去首页
            return `/home`;
          }
        }catch (error) {
          store.commit("permission/CLEAR_TOKEN");
          ElMessage.error(error.message || '系统错误')
          console.log("ERROR:",error);
          return `/login?redirect=${to.path}`;
        }
      }

      //存储最后一个路由刷新使用
      cookie.set("latestRoute",to.path);
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) == -1) {
      return `/login?redirect=${to.path}`;
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
