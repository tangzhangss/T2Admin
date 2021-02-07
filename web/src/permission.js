import router, {router404} from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import cookie from 'js-cookie' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import Router from "vue-router";
import Layout from "@/layout/index";


NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)
  let userInfo = store.getters.userInfo;
  if (userInfo) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      let userMenu = store.getters.userMenu;
      if(userMenu){
        next();
      }else{
        //去其他页面
        try{
          const routes = await store.dispatch('permission/generateRoutes');
          await router.addRoutes(routes);
          //测试用
          // await router.addRoutes([{
          //   path: '/system',
          //   component:Layout,
          //   redirect: '/Test',
          //   children: [
          //     {path:"menu",name:"Menu",meta:{"title":"左侧菜单","icon":"el-icon-menu"},component:()=>import('@/views/system/menu')}
          //   ],
          //   meta:{"title":"Test","icon":"el-icon-menu"}
          // }]);
          await router.addRoutes([router404]);//404页面放在最后
          next({ ...to, replace: true })
        }catch (error) {
          store.commit("permission/CLEAR_TOKEN");
          Message.error(error.message || '系统错误')
          console.log("ERROR:",error);
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
