<template>
  <div style="padding:5px 40px">
    <p style="font-size: 18px">欢迎使用 TZCC-REN ADMIN SYSTEM（v2.x版本）</p>
    <el-divider class="divider-title" content-position="left">系统概括</el-divider>
    <div class="sys-info">
      <div class="box"  v-loading="serverLoading">
        <p class="bold">服务器</p>
        <div class="text">
          <p>主机名：{{server.sys.computerName}}</p>
          <p>主机IP：{{server.sys.computerIp}}</p>
          <p>用户目录：{{server.sys.userDir}}</p>
          <p>操作系统：{{server.sys.osName}}</p>
          <p>osArch：{{server.sys.osArch}}</p>
          <div v-for="file in server.sysFiles">
              <p>
                <span >{{file.dirName}}</span>&nbsp;
                <span class="sign">{{file.free}}可用,</span>&nbsp;
                <span class="sign">共{{file.total}}</span>&nbsp;
                <span class="sign">{{file.sysTypeName}}</span>&nbsp;
                <span class="sign">{{file.typeName}}</span>
              </p>
            <el-progress
                    :status="file.usage<50?'success':file.usage<90?'warning':'exception'"
                    :percentage="file.usage">
            </el-progress>
          </div>
        </div>
      </div>
      <div class="box"  v-loading="serverLoading">
        <p  class="bold">资源</p>
        <div class="text">
           <div class="resource-one">
             <el-progress
                     :status="getProgressStatus(server.mem.usage)"
                     type="circle"
                     :percentage="server.mem.usage">
               <template #default>
                 <span>{{server.mem.usage}}%</span>
               </template>
             </el-progress>
             <div class="info">
               <div class="title">内存使用率</div>
               <br/>
               <div class="sign">总量：{{server.mem.total}}</div>
               <div class="sign">已用：{{server.mem.used}}</div>
               <div class="sign">剩余：{{server.mem.free}}</div>
             </div>
           </div>
          <el-divider />
          <div class="resource-one">
            <el-progress
                    :status="getProgressStatus(server.cpu.used)"
                    type="circle"
                    :percentage="server.cpu.used">
              <template #default>
                <span>{{server.cpu.used}}%</span>
              </template>
            </el-progress>
            <div class="info">
              <div class="title">CPU使用率</div>
              <br/>
              <div class="sign">数量    ：{{server.cpu.cpuNum}}</div>
              <div class="sign">核心数：{{server.cpu.cpuCoreNum}}</div>
              <div class="sign">线程数：{{server.cpu.cpuProcessorNum}}</div>
              <div class="sign">主频：{{server.cpu.maxFrequency/1000/1000}} Mhz</div>
            </div>
          </div>
        </div>
      </div>
      <div class="box" v-loading="serverLoading">
        <p  class="bold">运行环境（JVM）</p>
        <div class="text">
          <el-progress
                  :status="getProgressStatus(jvmUsage)"
                  :percentage="jvmUsage" >
          <!--  <template #default>
              <span>{{jvmUsage}}%</span>
            </template>-->
          </el-progress>
          <p>占用内存：{{server.jvm.total}} M</p>
          <p>空闲内存：{{server.jvm.free}} M</p>
          <p>最大可用内存：{{server.jvm.max}} M</p>
          <p>JDK版本：{{server.jvm.version}}</p>
          <p>启动时间：{{server.jvm.startTime}}</p>
          <p>运行时长：{{server.jvm.runTime}}</p>
        </div>
      </div>
    </div>
<!--    <el-divider class="divider-title" content-position="left">常见问题</el-divider>-->
<!--    <ul>-->
<!--      <li>待完善</li>-->
<!--    </ul>-->
    <el-divider class="divider-title" content-position="left">更新日志</el-divider>
    <ul class="update-log">
      <li>
        <p class="bold">v2.0.5(2022-04-20)</p>
        <div class="text">
          <p>首页系统概括</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.4(2022-03-29)</p>
        <div class="text">
          <p>优化系统代码</p>
          <p>新增后端国际化配置</p>
          <p>新增系统字典</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.3(2021-11-20)</p>
        <div class="text">
          <p>vue升级到3.2.0</p>
          <p>element-plus升级到1.2.0-beta.3</p>
          <p>优化菜单配置，支持多级菜单（含外部网页）</p>
          <p>新增自动换肤功能</p>
          <p>表格支持排序，排序的优先级:第一次进行排序的字段具有最高优先级（不管升序和降序和多次改变都一样，如需改变优先级请先清空排序）</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.2(2021-04-01)</p>
        <div class="text">
          <p>顶部功能优化</p>
          <p>优化组件样式，新增卡片式路由导航</p>
          <p>引入tinymce富文本编辑器</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.1(2021-03-05)</p>
        <div class="text">
          <p>前端技术框架升级vue3.0</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.0(2021-01-17)</p>
        <div class="text">
          <p>Init</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import {Decimal} from 'decimal.js';
export default {
  name: 'Home',
  data:function(){
    return {
        serverLoading:true,
        //服务器信息
        server: {
          sys:{},
          jvm:{},
          cpu:{},
          mem:{},
        },
        //jvm使用率
        jvmUsage:0,
        jvmIntervalId:0
    }
  },
  mounted(){
     this.$http.get("/service_api/monitor/system").then(res=>{
       if(res.data){
         this.server = res.data;
         this.serverLoading=false;
         this.startJvmProgress(this.server.jvm.usage);
       }
     })
  },
  methods:{
    getProgressStatus(val){
      return val<50?'success':val<90?'warning':'exception';
    },
    startJvmProgress(usage){
      usage = Number(usage);
      let addV = new Decimal(0.55);
      let that = this;
      let jvmIntervalId = setInterval(function(){
        that.jvmUsage = Number(new Decimal(that.jvmUsage).add(addV));
        if(that.jvmUsage>=usage){
          that.jvmUsage=usage;
          clearInterval(jvmIntervalId);
        }
      },10);
    }
  },
  computed: {
  }
}
</script>

<style lang="scss" scoped>

.divider-title{
  .el-divider__text{
    font-size: 16px;
    color: gray;
  }
}
ul{
  font-size: 14px;
  .text{
    padding: 0 5px;
    color: gray;
  }
}
.sys-info{
   display: flex;
   justify-content: space-between;
   .box{
     font-size: 14px;
     box-sizing: border-box;
     padding:2px 20px 10px 20px;
     width: 33%;
     border-radius: 10px;
     border: 5px solid #f7f7f7;
     box-shadow: 5px 5px 5px #f7f7f7;
     .sign{
       color: #aaa;font-size: 12px
     }
     &:deep(.el-progress__text){
       min-width: unset;
     }
     .resource-one{
         .title{
           font-size: 18px;
         }
         width: 100%;
         display: flex;
         align-items: flex-start;
         flex-wrap: nowrap;
         justify-content: space-around;
     }
   }
}
</style>
