import store from '../store';

/*
业务工具类
 */
const service_tool={
    setImageUploadHeaders:(val)=>{
        val.imageUploadHeaders={"X-Token":store.getters.userInfo&&store.getters.userInfo.token}
        return val;
    }
}
export default service_tool;