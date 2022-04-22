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
    <el-divider class="divider-title"  content-position="left">
      服务监视
      <el-popover width="50%"  placement="bottom-start" :visible="serviceShow">
        <template #reference>
          <span style="color:#67c23a;" class="pointer" @click="serviceShow=!serviceShow">[说明]</span>
        </template>
        <p>配置【系统设置-系统字典】，新增一个system-service（系统服务）的字典类型即可使用服务监视功能</p>
        <p>格式说明:</p>
        <div>
          <p>[</p>
          <p style="margin-left: 10px">{</p>
          <p style="margin-left: 20px">"label":"服务的名称,Mysql",</p>
          <p style="margin-left: 20px">"value":"服务的进程名,mysql"</p>
          <p style="margin-left: 10px">}</p>
          <p>]</p>
        </div>
      </el-popover>
    </el-divider>
    <div class="service" v-loading="serviceLoading">
      <el-table :data="serviceList" style="width: 100%">
        <el-table-column prop="label" label="服务"  align="center"/>
        <el-table-column prop="status" label="状态"  align="center">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.status">正常</el-tag>
            <el-tag type="danger" v-else>异常</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="ramUse" label="内存占用大小"  align="center"/>
        <el-table-column prop="ramUseRate" label="内存占用率"  align="center"/>
        <el-table-column prop="cpuUseRate" label="CPU占用率"  align="center"/>
      </el-table>
    </div>
  </div>
</template>

<script>
import {Decimal} from 'decimal.js';
//引入
import JsonViewer from "vue3-json-viewer"

export default {
  name: 'Home',
  components:{
    "json-viewer":JsonViewer
  },
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
        jvmIntervalId:0,
        serviceList:[],
        serviceShow:false,
        serviceLoading:true,
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
    this.$http.get("/service_api/monitor/service").then(res=>{
        this.serviceList=res.data;
        if(res.data.length==0)this.serviceShow=true;
        this.serviceLoading=false;
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
.service{
  border-radius: 10px;
  border: 5px solid #f7f7f7;
  box-shadow: 5px 5px 5px #f7f7f7;
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
