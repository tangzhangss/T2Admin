import {router,router404} from './router'
import store from './store'
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import getPageTitle from "@/utils/get-page-title"


NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from) => {
  // start progress bar
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)
  let userInfo = store.getters.userInfo;
  if (userInfo) {
    if (to.path === '/login') {
      NProgress.done()
    } else {
      let userMenu = store.getters.userMenu;
      if(!userMenu){
        //去其他页面
        try{
          const routes = await store.dispatch('permission/generateRoutes');
          for (let i=0;i<routes.length;i++){
             await router.addRoute(routes[i]);
          }
          // await router.addRoute(router404);//404页面放在最后-vue-router4不需要了
          // console.log("routers:",routes,router.getRoutes());
          return to.path;
        }catch (error) {
          store.commit("permission/CLEAR_TOKEN");
          ElMessage.error(error.message || '系统错误')
          console.log("ERROR:",error);
          return `/login?redirect=${to.path}`;
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) == -1) {
      NProgress.done()
      return `/login?redirect=${to.path}`;
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
