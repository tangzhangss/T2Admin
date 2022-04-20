<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    <breadcrumb class="breadcrumb-container" />
    <div class="right-menu">

      <!--换肤-->
      <el-popover
              :visible="themeColorVisible"
              placement="bottom"
              title="更换主题色彩"
              :width="400"
              trigger="manual"
      >
        <template #reference>
          <div title="更换主题色彩" @click="themeColorVisible=true" style="display: inline-block;height:100%">
            <svg-icon icon-class="skin" class="right-menu-item hover-effect"></svg-icon>
          </div>
        </template>

        <div class="select-color-box">
          <div class="menu-text">
            <span>menu text</span>
            <el-color-picker v-model="themeColor['--menu-text']" />
          </div>
          <div class="menu-active-text">
            <span>menu active text</span>
            <el-color-picker v-model="themeColor['--menu-active-text']" />
          </div>
          <div class="sub-menu-active-text">
            <span>sub menu active text</span>
            <el-color-picker v-model="themeColor['--sub-menu-active-text']" />
          </div>
          <div class="menu-bg">
            <span>menu bg</span>
            <el-color-picker v-model="themeColor['--menu-bg']" />
          </div>
          <div class="menu-hover">
            <span>menu hover</span>
            <el-color-picker v-model="themeColor['--menu-hover']" />
          </div>
          <div class="sub-menu-bg">
            <span>submenu bg</span>
            <el-color-picker v-model="themeColor['--sub-menu-bg']" />
          </div>
          <div class="menu-hover">
            <span>submenu hover</span>
            <el-color-picker v-model="themeColor['--sub-menu-hover']" />
          </div>
          <div>
            <span>表格Header背景颜色</span>
            <el-color-picker v-model="themeColor['--tz-table-header-bg']" />
          </div>
          <div class="menu-hover">
            <el-button type="text" @click="themeDefault">恢复默认</el-button>
          </div>
          <el-icon title="关闭" size="20" class="pointer" style="position: absolute;right: 5px;top: 5px"  @click="themeColorVisible=false">
            <Close></Close>
          </el-icon>
        </div>
      </el-popover>

      <div  title="刷新" style="display: inline-block;height:100%">
         <svg-icon icon-class="refresh" class="right-menu-item hover-effect" @click="refreshCurrentPage"></svg-icon>
<!--         <svg class="right-menu-item hover-effect refresh" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-394d1fd8=""><path fill="currentColor" d="M771.776 794.88A384 384 0 0 1 128 512h64a320 320 0 0 0 555.712 216.448H654.72a32 32 0 1 1 0-64h149.056a32 32 0 0 1 32 32v148.928a32 32 0 1 1-64 0v-50.56zM276.288 295.616h92.992a32 32 0 0 1 0 64H220.16a32 32 0 0 1-32-32V178.56a32 32 0 0 1 64 0v50.56A384 384 0 0 1 896.128 512h-64a320 320 0 0 0-555.776-216.384z"></path></svg>-->
      </div>
      <screenfull  class="right-menu-item hover-effect screenfull"/>

      <!--中英文-->
      <div style="display: inline-block;line-height: 100%" class="pointer">
        <svg @click="this.$store.commit('settings/UPDATE_THEME_LANGUAGE','zh-CN')" v-if="this.$store.getters.themeLanguage=='en-US'" t="1641276328769" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2244" width="32" height="32"><path d="M229.248 704V337.504h271.744v61.984h-197.76v81.28h184v61.76h-184v99.712h204.768V704h-278.72z m550.496 0h-70.24v-135.488c0-28.672-1.504-47.232-4.48-55.648a39.04 39.04 0 0 0-14.656-19.616 41.792 41.792 0 0 0-24.384-7.008c-12.16 0-23.04 3.328-32.736 10.016-9.664 6.656-16.32 15.488-19.872 26.496-3.584 11.008-5.376 31.36-5.376 60.992V704h-70.24v-265.504h65.248v39.008c23.168-30.016 52.32-44.992 87.488-44.992 15.52 0 29.664 2.784 42.496 8.352 12.832 5.6 22.56 12.704 29.12 21.376 6.592 8.672 11.2 18.496 13.76 29.504 2.56 11.008 3.872 26.752 3.872 47.264V704z" p-id="2245" fill="#1296db"></path><path d="M160 144a32 32 0 0 0-32 32V864a32 32 0 0 0 32 32h688a32 32 0 0 0 32-32V176a32 32 0 0 0-32-32H160z m0-64h688a96 96 0 0 1 96 96V864a96 96 0 0 1-96 96H160a96 96 0 0 1-96-96V176a96 96 0 0 1 96-96z" p-id="2246" fill="#1296db"></path></svg>
        <svg @click="this.$store.commit('settings/UPDATE_THEME_LANGUAGE','en-US')" v-if="this.$store.getters.themeLanguage=='zh-CN'" t="1641276943230" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3077" width="32" height="32"><path d="M160 144a32 32 0 0 0-32 32V864a32 32 0 0 0 32 32h688a32 32 0 0 0 32-32V176a32 32 0 0 0-32-32H160z m0-64h688a96 96 0 0 1 96 96V864a96 96 0 0 1-96 96H160a96 96 0 0 1-96-96V176a96 96 0 0 1 96-96z" p-id="3078" fill="#1afa29"></path><path d="M482.176 262.272h59.616v94.4h196v239.072h-196v184.416h-59.616v-184.416H286.72v-239.04h195.456V262.24z m-137.504 277.152h137.504v-126.4H344.64v126.4z m197.12 0h138.048v-126.4H541.76v126.4z" p-id="3079" fill="#1afa29"></path></svg>
      </div>

      <div style="margin-right: 10px;"></div>

      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="$store.getters.userInfo && $store.getters.userInfo.avatars" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <template #dropdown>
          <el-dropdown-menu class="user-dropdown">
            <el-dropdown-item @click.native="openClientInfo">
              企业信息
            </el-dropdown-item>
            <el-dropdown-item divided @click.native="modifyClientUserPwd"  v-if="isCurrentClientAdmin">
              修改密码
            </el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">
              <span style="display:block;">退出系统</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <tz-form-dialog
      :readonly="!isEdit"
      :edit-column-default-value="clientInfo" :edit-column-rules="editColumnRules" submit-api="/service_api/client" v-model:show="showClientDialog" title="企业信息" :edit-column="editColumn"
      :submitName="isCurrentClientAdmin?isEdit?'保存':'修改':''" :submit-before-func="editClientInfo"
      :other-action="[
                        {type:'',title:'取消',isShow:isEdit&&isCurrentClientAdmin,onClick:()=>{isEdit=false}}
                      ]"
    ></tz-form-dialog>

    <el-dialog
      title="修改密码"
      v-model="modifyPwdDialogVisible"
      :append-to-body='true'
    >
      <el-form label-position="right" label-width="auto" :inline="true">
        <el-form-item label="新密码" prop="pass" style="display: flex;align-items: center" required>
          <el-input type="password" v-model="tempValue1" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" style="display: flex;align-items: center" required>
          <el-input type="password"  v-model="tempValue2"  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div  class="dialog-footer">
          <el-button class="tz-btn" @click="modifyPwdDialogVisible = false">取 消</el-button>
          <el-button  class="tz-btn" type="primary" @click="modifyClientUserPwdExec">确 定</el-button>
        </div>
      </template>
    </el-dialog>

  </div>

</template>

<script>
import { mapGetters } from 'vuex';
import Breadcrumb from '@/components/Breadcrumb';
import Hamburger from '@/components/Hamburger';
import TZUtils from "@/utils/TZUtils";
import Screenfull from '@/components/Screenfull'
import SvgIcon from "../../components/SvgIcon/index";
import {Close} from "@element-plus/icons"

export default {
  components: {
    SvgIcon,
    Breadcrumb,
    Hamburger,
    Screenfull,
    Close
  },
  data(){
    return {
      isEdit:false,
      showClientDialog:false,
      editColumnRules:{
        id:[{required:true,pattern:/^[0-9a-zA-Z_]+$/,message: '只能包含数字、字符和_(下划线)',trigger:"blur"}],
        phone:[{required:true,pattern:/^1[3456789]\d{9}$/,message: '请输入正确手机号码',trigger:"blur"}],
      },
      clientInfo:null,
      showClientDialogLoading:false,
      modifyPwdDialogVisible:false,
      tempValue1: '',
      tempValue2: '',
      themeColor:this.$store.getters.themeColor,
      themeColorVisible:false
    }
  },
  created() {},
  computed: {
    ...mapGetters([
      'sidebar',
    ]),
    editColumn(){
      return [
        this.$service_tool.setImageUploadHeaders({prop:"logo", tip:"点击或拖拽上传，大小不能超过1MB", label:"LOGO", iType:"image",iSpan:24, style:"width:80px;height:80px"}),
        {prop:"id",label:"账户",tip:"唯一标识,注册后不可更改",disabled:true,iType:"text", iSpan:24,style:"width:200px",placeholder:"CD1024",},
        {prop:"name",label:"公司",iType:"text",required:true,placeholder:"成都1024科技有限责任公司",iSpan:12,},
        {prop:"phone",label:"手机",iType:"text",placeholder:"15520449931",iSpan:12,},
        {prop:"username",label:"管理姓名",iType:"text",iSpan:12,required:true,placeholder:"唐彰",},
        {prop:"email",label:"管理邮箱",iType:"text",required:true,placeholder:"it_tangzhang@163.com",rules:[{type: 'email',message: '请输入正确的邮箱地址',}], iSpan:12,},
        {prop:"address",label:"地址",iType:"text",required:true,placeholder:"四川省成都市龙泉驿区派瑞国际",iSpan:24},
        {prop:"remark",label:"备注",iType:"text",iSpan:24}
      ];
    },
    isCurrentClientAdmin(){
      let userInfo = this.$store.getters.userInfo;
      return userInfo&&userInfo.clientId==userInfo.username;
    }
  },
  methods: {
    themeDefault(){
      this.themeColor={};
      this.$store.commit("settings/CLEAR_THEME_COLOR");
    },
    refreshCurrentPage(){
      //刷新当前路由组件
      window.APP_MAIN_PAGE.reload();
      //下面这种不好用，每次刷新之后页面会出现空白然后从新点击进入才会刷新出来
      //有空再研究
      // this.$router.replace("/refresh")
    },
    modifyClientUserPwd:function(){
      this.modifyPwdDialogVisible=true;
      this.isEdit=false;
    },
    modifyClientUserPwdExec(){
      let pwd = this.tempValue2;
      if(TZUtils.trim(this.tempValue1).length<6){
        this.$message.error("密码长度不能小于6位");
        return false;
      }
      if(this.tempValue1!=this.tempValue2){
        this.$message.error("两次密码不一致,请检查");
        return false;
      }
      let loading = TZUtils.fullLoading(this);
      this.$http.post("/service_api/user/modify_pwd_by_client",{"password":pwd}).then(res=>{
        this.$message.success("操作成功");
        this.modifyPwdDialogVisible=false;
      }).finally(()=>{
        loading.close();
      })
    },
    editClientInfo:function(){
      if(this.isEdit)return true;
      this.isEdit=true;
      return  false;
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    logout() {
      this.$store.commit("permission/CLEAR_TOKEN");
      this.$router.push(`/login`)
    },
    openClientInfo(){
      this.isEdit=false;
      if(!this.clientInfo){
         let loading = TZUtils.fullLoading(this);
         //获取用户企业信息
         this.$http.get("/service_api/client/get/"+this.$store.getters.userInfo.clientId).then(res=>{
            this.clientInfo = res.data;
         }).finally(()=>{
           loading.close();
           this.showClientDialog=true;
         })
      }else{
        this.showClientDialog=true;
      }
    }
  },
  watch:{
    themeColor:{
      handler:function(val){
        this.$store.commit("settings/UPDATE_THEME_COLOR",val);
      },
      deep: true
    }
  }
}
</script>

<style lang="scss" scoped>
.user-dropdown{
  /*top: 50px !important;*/
}
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;
    &:hover {
      /*background: rgba(0, 0, 0, .025)*/
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    .screenfull{
      display: inline-block;
      height: 28px;
    }
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;
        width: 32px;
        font-size: 18px;
        padding: 0 4px !important;
        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      /*margin-right: 30px;*/
      .avatar-wrapper {
        margin-top: 5px;
        margin-right: 20px;
        position: relative;
        text-align: right;
        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.select-color-box{
  display: flex;
  flex-wrap: wrap;
  &>div{
    width: 180px;
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    align-content: center;
  }
  &:deep(.el-color-picker){
    height: 20px;
    line-height: 20px;
  }
  &:deep(.el-color-picker__trigger){
    width: 20px;
    height: 20px;
  }
}
</style>
