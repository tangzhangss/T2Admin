import axios from 'axios'
import { MessageBox, Message,Notification} from 'element-ui'
import cookie from 'js-cookie'
import store from "@/store";

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000 // request timeout,
})

// request interceptor
service.interceptors.request.use(
  config => {
    let userInfo = store.getters.userInfo;
    config.headers['X-Token'] = userInfo && userInfo.token;
    return config
  },
  error => {
    Message({
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
    if(res.code!=200){
      Notification({
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
    const res = error && error.response && error.response.data;
    //服务器内部错误box包含的状态码
    const handleCode=[500,405];
    if(handleCode.indexOf(res.code) >= 0){
        MessageBox({
          title: '服务器内部错误',
          dangerouslyUseHTMLString: true,
          message:errorHtml(res),
        }).then(r => {})
    }else if(res.code==401) {
      Notification({
        title:res.message,
        message: res.data,
        type: 'error',
        duration: 5 * 1000,
        showClose:true
      })
      store.commit("permission/CLEAR_TOKEN");
    }else {
        Message({
          message: res.message || res,//有可能返回的不是后端Result对象
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
