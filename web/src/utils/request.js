import axios from 'axios'
import { ElMessageBox, ElMessage,ElNotification} from 'element-plus'
import store from "@/store";

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout:30000 // request timeout,前端超时取消之后后端处理还会继续执行；如果设置建议大于后端的超时时间
})

// request interceptor
service.interceptors.request.use(
  config => {
    //设置token
    let userInfo = store.getters.userInfo;
    config.headers['X-Token'] = userInfo && userInfo.token;

    //设置语言
    config.headers['accept-language']=store.getters.themeLanguage;

    return config
  },
  error => {
    ElMessage({
      message: error,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(

  response => {
    const res = response.data
    //200才表示成功_其他表示业务处理失败
    if(!res.code||res.code!=200){
      ElNotification({
        title:res.message,
        message: res.data,
        type: 'error',
        duration: 5 * 1000,
        showClose:true
      })
      return Promise.reject(res);
    }
    return res;
  }, error => {
    let res = error && error.response && error.response.data;
    //服务器内部错误box包含的状态码
    const handleCode=[500,405];

    res=res||{};//可能不存在

    if(handleCode.indexOf(res.code) >= 0){
      ElMessageBox({
        title: '服务器内部错误',
        dangerouslyUseHTMLString: true,
        message: errorHtml(res),
      }).then(() =>{})
    } else if(res.code==401) {
      ElNotification({
        title:res.message,
        message: res.data,
        type: 'error',
        duration: 5 * 1000,
        showClose:true
      })
      store.commit("permission/CLEAR_TOKEN");
    }else if(res.code==429) {
      ElMessage({
        message:"正在处理中，请勿重复提交!",
        type: 'warning',
        duration: 2 * 1000
      })
    }else {
      ElMessage({
          message: res.message || "服务器加载失败，请刷新页面或稍后重试!",//有可能返回的不是后端Result对象
          type: 'error',
          duration: 5 * 1000
        })
    }
    return Promise.reject(error)
  }
)

service.get=(url,param)=>{
  return service({
    url: url,
    method: 'get',
    params:param
  })
}
service.post=(url,data)=>{
  return service({
    url: url,
    method: 'post',
    data:data
  })
}
service.put=(url,data)=>{
  return service({
    url: url,
    method: 'put',
    data:data
  })
}
service.delete=(url,data)=>{
  return service({
    url: url,
    method: 'delete',
    data:data
  })
}
/*
错误html提示
 */
function errorHtml(res){
  let msg = `
<style scoped>.red{color:red} div{box-sizing: border-box;word-wrap:break-word;word-break:normal;} .el-message-box{width:auto;max-width: 80vw;}</style>
<div><strong>code:</strong>${res.code}</div>
<div><strong>message:</strong>${res.message}</div>
<div><strong>exception:</strong></div>
<div style="margin-left: 10px">
    <div>path:<span class="red">${res.data.path}</span><div>
    <div>method:<span class="red">${res.data.method}</span><div>
    <div>message:<span class="red">${res.data.message}</span><div>
    <div>class:<span class="red">${res.data.class}</span><div>
</div>
`
  if(res.data.trace){
      msg = msg+'<div>trace:<div><div style="margin-left: 10px;max-height:200px;overflow-y: auto">';
      res.data.trace.forEach((item)=>{
        let trace=`<div class="red">at&nbsp;${item.className}.${item.methodName}<span style='color:blue'>(${item.fileName}:${item.lineNumber})</span></div>`;
        msg = msg+trace;
      })
      msg = msg+'</div>';
  }
  return msg;
}

export default service
