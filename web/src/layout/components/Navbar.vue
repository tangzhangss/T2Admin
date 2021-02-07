<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="$store.getters.userInfo && $store.getters.userInfo.avatars" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
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
      </el-dropdown>
    </div>

    <TZFormDialog
      :edit-column-default-value="clientInfo" :edit-column-rules="editColumnRules" submit-api="/service_api/client" :show.sync="showClientDialog" title="企业信息" :edit-column="editColumn"
      :submitName="isCurrentClientAdmin?isEdit?'保存':'修改':''" :submit-before-func="editClientInfo"
      :other-action="[
                        {type:'',title:'取消',isShow:isEdit&&isCurrentClientAdmin,onClick:()=>{this.isEdit=false}}
                      ]"
    ></TZFormDialog>

    <el-dialog
      title="修改密码"
      :visible.sync="modifyPwdDialogVisible"
      :append-to-body='true'
    >
      <el-form label-position="right" label-width="auto" :inline="true">
        <el-form-item label="新密码" prop="pass" style="display: flex;align-items: center">
          <el-input type="password" v-model="tempValue1" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" style="display: flex;align-items: center">
          <el-input type="password"  v-model="tempValue2"  autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button class="tz-btn" @click="modifyPwdDialogVisible = false">取 消</el-button>
        <el-button  class="tz-btn" type="primary" @click="modifyClientUserPwdExec">确 定</el-button>
     </span>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Breadcrumb from '@/components/Breadcrumb';
import Hamburger from '@/components/Hamburger';
import TZFormDialog from "@/components/TZForm/dialog";
import TZUtils from "@/utils/TZUtils";
export default {
  components: {
    Breadcrumb,
    Hamburger,
    TZFormDialog
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
    }
  },
  created() {

  },
  computed: {
    ...mapGetters([
      'sidebar',
    ]),
    editColumn(){
      return [
        {prop:"logo", tip:"点击或拖拽上传，大小不能超过1MB", label:"LOGO", iType:"image",readonly:!this.isEdit, iSpan:24, style:"width:80px;height:80px"},
        {prop:"id",label:"账户",tip:"唯一标识,注册后不可更改",disabled:true,iType:"text",readonly:!this.isEdit, iSpan:24,style:"width:200px",placeholder:"CD1024",},
        {prop:"name",label:"公司",iType:"text",readonly:!this.isEdit,required:true,placeholder:"成都1024科技有限责任公司",iSpan:12,},
        {prop:"phone",label:"手机",iType:"text",readonly:!this.isEdit,placeholder:"15520449931",iSpan:12,},
        {prop:"username",label:"管理姓名",iType:"text",readonly:!this.isEdit,iSpan:12,required:true,placeholder:"唐彰",},
        {prop:"email",label:"管理邮箱",iType:"text",readonly:!this.isEdit,required:true,placeholder:"it_tangzhang@163.com",rules:[{type: 'email',message: '请输入正确的邮箱地址',}], iSpan:12,},
        {prop:"address",label:"地址",iType:"text",readonly:!this.isEdit,required:true,placeholder:"四川省成都市龙泉驿区派瑞国际",iSpan:24},
        {prop:"remark",label:"备注",iType:"text",readonly:!this.isEdit,iSpan:24}
      ];
    },
    isCurrentClientAdmin(){
      let userInfo = this.$store.getters.userInfo;
      return userInfo.clientId==userInfo.username;
    }
  },
  methods: {
    modifyClientUserPwd:function(){
      this.modifyPwdDialogVisible=true;
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
  }
}
</script>

<style lang="scss" scoped>
.user-dropdown{
  top: 50px !important;
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
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;
    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

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

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
