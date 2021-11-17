import {constantRoutes} from '@/router';
import {importPage404,importComponent} from '@/../config/asyncRoute';
import cookie from 'js-cookie';
import TZUtils from "@/utils/TZUtils";
import Layout from "@/layout/index";
import http from '@/utils/request'
import store from "../index";


function getUserCookie(key){
  let jsonStr = cookie.get(key);
  let json = jsonStr && JSON.parse(jsonStr);
  return json;
}
const state = {
  routes: [],
  routeTabs:[],
  addRoutes: [],
  userMenu:null,
  userInfo:getUserCookie(window.userInfoCookieKey),
  externalServiceMap:new Map(),
}

const mutations = {
  SET_ROUTES: (state,routes) => {
    state.addRoutes = routes;
    let routes_temp = constantRoutes.concat(routes);
    state.routes = routes_temp;
  },
  SET_USER_MENU:(state,menus)=>{
    //菜单不存入cookie（数据量太大了存不进去，每次刷新都重新拉取菜单,且，组装之后的数据存进去报错）
    // cookie.set(window.userMenuCookieKey,menus);
    // localStorage.setItem(window.userMenuCookieKey,menus);
    state.userMenu = menus;
  },
  SET_USER_INFO:(state,userInfo)=>{
    cookie.set(window.userInfoCookieKey,userInfo);
    state.userInfo = userInfo;
  },
  CLEAR_TOKEN:(state)=>{
    //用户信息
    cookie.remove(window.userInfoCookieKey);
    //菜单信息
    cookie.remove(window.userMenuCookieKey);
    state.userInfo=null;
    state.userMenu=null;

  },
  UPDATE_ROUTE_TAB:(state,route)=>{
    //存在才缓存
    if(!(route.meta && route.meta.title)){
        return false;
    }
    //遍历路由表看存在当前路由，如果不存在就添加存在就不管
    let oldRoute = state.routeTabs.filter(r=>r.path==route.path);
    if(oldRoute.length==0){
      let maxTabCount=8;//最大的tab数量
      if(state.routeTabs.length>=maxTabCount){
        //最多8个
        state.routeTabs.splice(0,state.routeTabs.length-(maxTabCount-1));
      }
      if(!route.meta.noCache){
        state.routeTabs.push(route);
      }
    }
  },
  DELETE_ROUTE_TAB:(state, deletedPath)=>{
    //遍历路由表看存在当前路由，如果不存在就添加存在就不管
    let tabs = state.routeTabs;
    let index = -1;
    for(let i =0;i<tabs.length;i++){
      if(tabs[i].path==deletedPath){
        index=i;
        break;
      }
    }
    if(index!=-1)tabs.splice(index,1);
  },
  UPDATE_EXTERNAL_SERVICE:(state,obj)=>{
     state.externalServiceMap.set(obj.key,obj.value);
  }
}
function setRoutes(commit,menu) {
  //菜单存入缓存
  commit("SET_USER_MENU",TZUtils.deepClone2Array(menu));
  commit('SET_ROUTES', menu);
  return true;
}
//菜单处理,如排序
function handleMenu(menu) {
  //去掉Id字段
  // menu.forEach(item=>{
  //   delete item.id;
  //   if(item.children){
  //     handleMenu(item.children);
  //   }
  // })
  //还需要做一些处理不然显示不出来

  //排序
  let menuSort = function (menus) {
    menus = menus.sort((a,b)=>a.orderNo-b.orderNo);
    menus.forEach(menu=>{
      if(menu.children)menuSort(menu.children);
    })
  }
  menuSort(menu);
  //-------------------------------------------

  //设置setAlwaysShow属性（叶子节点 这个属性 必须为false）
  let setAlwaysShow = function(menus){
    menus.forEach(menu=>{
      if(!menu.children){
         menu.alwaysShow = false;
      }else{
        setAlwaysShow(menu.children);
      }
    })
  }
  setAlwaysShow(menu);
  //-------------------------------------------

  return menu;
}
const actions = {
  generateRoutes({ commit }) {
    return new Promise(resolve =>{
      //获取用户菜单
      http.get('/service_api/menu/get_user_menu').then((res) => {
        let menuList = res.data || [];
        //将数据拼接成树形结构
        let menu = arrayToTree(menuList,"parentId");
        //再次处理
        handleMenu(menu);
        //菜单存入缓存
        setRoutes(commit,menu);
        resolve(menu);
      });
    })
  },
}
//--------------------------------------------------------------------------------------------------------
//获取菜单 辅助方法
/*
对象数组转树型数组对象
array 用于连接的key
由底向上（parent_id）
*/
function  arrayToTree(array,key){
  let res=[];
  //先找出根元素
  for(let i = array.length - 1; i >= 0; i--){
    let item = array[i];
    if(!item[key]){
      res.push(parseRoute(item,true));
      array.splice(i,1);//删除这个item
    }
  }
  if(res.length!=0){//存在根目录才执行
    arrayToTreeAssist(res,array,key);
  }
  //res是最后的结果
  return res;
}
//转成路由的格式
function parseRoute(item,isRoot){
  let route={
    id:item.id,//需要临时用到
    path:item.path,
    meta: {
      title:item.title,
      icon:item.icon,
      noCache:item.noCache,
      id:item.id,
    },
    orderNo:item.orderNo,
    alwaysShow:item.alwaysShow,
    name:item.name
  }
  //根目录
  if(isRoot){
    route.component=Layout;
    route.redirect=item.redirect;
  }else {
      //子菜单重定向---一般不会用到
      if(TZUtils.trim(item.redirect)!="noRedirect"){
         route.redirect=item.redirect;
      }
      //由于加载机制的问题必须在外部调用
      try {
        if(item.domainId && item.domain){
          route.component=require('@/views/iFrame.vue').default;
          let addr = item.domain.devAddress;
          if(process.env.IS_PROD){
            addr=item.domain.proAddress;
          }
          store.commit('permission/UPDATE_EXTERNAL_SERVICE',
              {key:item.id,value:handleUrl(addr,item.url)});
          // route.path= route.path.replace("${id}",item.id);
          route.path= route.path+"/"+item.id;
        }else if(item.url&&TZUtils.trim(item.url)!=""){
          route.component=importComponent(item.url);
        }
      }catch (e) {
        if(e.message && e.message.includes('Cannot find module')){
           console.error("页面加载失败:",e.message);
          //页面不存在，给予404页面
          route.component = importPage404();
        }else{
          console.log(e);
        }
      }
  }
  //多级路由 中间的路由页面
  if(!route.component){
    route.component=require('@/views/router.vue').default;
    route.redirect="noRedirect";
  }
  return route;
}
function handleUrl(domain,path){
  if(!/.*?\/$/.test(domain))domain += "/";
  if(!/^\/.*/.test(path))path="/"+path;
  return domain+"#"+path;
}
function arrayToTreeAssist(res,array,key){
  //没有元素了
  if(array.length==0)return false;
  //这里需要倒叙遍历因为需要删除子元素
  for(let i = array.length - 1; i >= 0; i--){
    let item = array[i];
    res.some(item2=>{
      if(item[key]==item2.id){
        if(!item2.children)item2.children=[];
        item2.children.push(parseRoute(item));
        array.splice(i,1);//删除这个item
        return true;
      }
      if(item2.children){//有至少一个子元素
        //查询children中是否存在当前元素的父级
        let res = arrayToTreeAssist2(item2.children,item,key);
        if (res!==undefined){//这里可能索引是0
          array.splice(i,1);//删除这个item
          return true;
        }
      }
    })
  }
  //一直循环，知道array里面的元素为空
  arrayToTreeAssist(res,array,key);
}
function arrayToTreeAssist2(children,item,key){

  for (let i=0;i<children.length;i++){
    let c = children[i];
    if(c.id==item[key]){
      if(!c.children) c.children=[];
      c.children.push(parseRoute(item));
      return i;
    }
    if(c.children){
      arrayToTreeAssist2(c.children,item,key);
    }
  }
}
//--------------------------------------------------------------------------------------------------------

export default{
  namespaced: true,
  state,
  mutations,
  actions
}
