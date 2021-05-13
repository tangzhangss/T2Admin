"use strict"
const serviceApi="http://localhost:10082";
// const serviceApi="http://47.93.202.202:10082";
const dataApi="http://localhost:10083";
const utilApi="http://localhost:10081";

/**
 * 注意：代理地址不能和页面路由地址一样！！！
 */

module.exports= {
  '/service_api':{
    target:serviceApi,
    changeOrigin: true,
    pathRewrite:{
      '^/service_api':''
    }
  },
  '/data_api':{
    target:dataApi,
    changeOrigin: true,
    pathRewrite:{
      '^/data_api':''
    }
  },
  '/sys_api':{
    target:utilApi,
    changeOrigin: true,
    pathRewrite:{
      '^/sys_api':''
    }
  }
}

