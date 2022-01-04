export const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  userInfo: state => state.permission.userInfo, //用户信息
  userMenu: state => state.permission.userMenu, //用户菜单
  routes: state => state.permission.routes, //用户路由
  routeTabs: state => state.permission.routeTabs, //用户路由数组
  externalServiceMap: state => state.permission.externalServiceMap, //外部服务的菜单id->url键值对
  // hasAuth: state => state.permission.hasAuth,//用户是否具备token__不仅用token还需要有菜单
  themeColor:state=>state.settings.themeColor,//主题颜色
  themeLanguage:state=>state.settings.themeLanguage,//主题语言
}
