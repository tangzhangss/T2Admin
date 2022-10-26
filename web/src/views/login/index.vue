<template>
  <div class="login-container">
    <div class="title-admin">T2Admin System</div>
    <div class="login-box">
       <el-image class="mJpg" :src="mJpg"></el-image>
       <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
         <div class="title-container">
           <h3 class="title">账号登录</h3>
         </div>
         <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
          <el-input class="el-input-login"
                    ref="username"
                    v-model="loginForm.username"
                    placeholder="用户名"
                    name="username"
                    type="text"
                    tabindex="1"
                    auto-complete="on"
          />
        </el-form-item>

        <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
          <el-input class="el-input-login"
                    :key="passwordType"
                    ref="password"
                    v-model="loginForm.password"
                    :type="passwordType"
                    placeholder="密码"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                    @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
        <el-button :loading="loading" type="success" style="width:100%;" @click.native.prevent="handleLogin">登录</el-button>
        <hr style="opacity:0.1"/>
        <div class="operation">
          <el-tag @click="handleRegister">注册</el-tag>
          <el-tag @click="$message.info('暂未提供修改密码功能')">忘记密码</el-tag>
          <el-tag @click="openSystemIntroducePage">系统框架介绍</el-tag>
        </div>

       </el-form>
    </div>
    <p class="copyright">Copyright &copy; 2020 - {{nowYear}} 版权所有| by <a href="#">it_tangzhang@163.com</a></p>

    <!--注册弹窗-->
    <!--:submit-func="submitFunc"-->
    <tz-form-dialog :editColumnRules="editColumnRules" submit-api="/service_api/client/no_auth" :submit-after-func="registerSuccessHandle" v-model:show="showRegisterDialog" title="客户注册" :edit-column="registerColumn" submit-name="提交"></tz-form-dialog>
  </div>
</template>

<script>
import TZUtils from "@/utils/TZUtils";

export default {
  name: 'Login',
  components:{
  },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (/^\s*$/.test(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      editColumnRules:{
          id:[{required:true,pattern:/^[0-9a-zA-Z_]+$/,message: '只能包含数字、字符和_(下划线)',trigger:"blur"}],
          phone:[{required:true,pattern:/^1[3456789]\d{9}$/,message: '请输入正确手机号码',trigger:"blur"}],
      },
      nowYear:new Date().getFullYear(),
      mJpg:require("@/assets/login/m.jpg"),
      loginForm: {
        username: 'T2Admin',
        password: '123456'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      showRegisterDialog:false,
      registerColumn:[
       this.$service_tool.setImageUploadHeaders({
          prop:"logo",
          tip:"点击或拖拽上传，大小不能超过1MB",
          tipPlacement:"left",
          label:"LOGO",
          iType:"image",
          iSpan:24,
          style:"width:80px;height:80px",
          imageUploadApi:"/service_api/aliyunoss/upload_picture/no_auth",
        }),
        {
          prop:"id",
          label:"账户",
          tip:"唯一标识,注册后不可更改",
          iType:"text",
          iSpan:12,
          placeholder:"CD1024",
        },
        {
          prop:"name",
          label:"企业",
          iType:"text",
          required:true,
          placeholder:"成都1024科技有限责任公司",
          iSpan:12,
        },
        {
          prop:"username",
          label:"管理员姓名",
          iType:"text",
          iSpan:12,
          required:true,
          placeholder:"唐彰",
        },
        {
          prop:"phone",
          label:"手机",
          iType:"text",
          placeholder:"15520449931",
          iSpan:12,
        },
        {
          prop:"email",
          label:"管理员邮箱",
          iType:"text",
          required:true,
          placeholder:"it_tangzhang@163.com",
          rules:[{type: 'email',message: '请输入正确的邮箱地址',}],
          iSpan:12,
        },
        {
          prop:"address",
          label:"地址",
          iType:"text",
          required:true,
          placeholder:"四川省成都市龙泉驿区派瑞国际",
          iSpan:24,
        },
        {
          prop:"remark",
          label:"备注",
          iType:"text",
          required:true,
          placeholder:"",
          iSpan:24,
        },
      ],
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    openSystemIntroducePage(){
      window.open('https://blog.csdn.net/qq_34813134/article/details/126674977')
    },
    //注册成功方法
    registerSuccessHandle(data){
      this.$notify({
        title: '提示',
        message: '注册信息提交成功，请等待管理员审核通知',
        type: 'success',
        duration: 0
      });
    },
    submitFunc(res){
      console.log("form:",res);
      return true;
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.$http.post('/service_api/user/login', this.loginForm).then((res) => {
            this.$store.commit("permission/SET_USER_INFO",TZUtils.deepClone(res.data));
            this.$router.push({ path: this.redirect || '/' })
          }).finally((res)=>{
            this.loading = false;
          })
        } else {
          return false
        }
      })
    },
    handleRegister(){
      this.showRegisterDialog=true
    }
  },
  created() {},
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#aaa;
$light_gray:#000;
$cursor: #000;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input-login input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input-login {
    display: inline-block;
    height: 48px;
    width: 85%;
    .el-input__wrapper {
        height: 40px;
        margin:4px 0% 4px 4%;
        width:100%;
    }
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      color: $light_gray;

      caret-color: $cursor;

      &:-webkit-autofill {
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 5px;
    background-color: #eee;
    .el-form-item__label-wrap{
      margin-left: 0 !important;
    }
  }
}
</style>

<style lang="scss" scoped>
$bg:#aaa;
$dark_gray:#889aa4;
$light_gray:#000;
$opacity:0.85;

.login-container {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;
  background-repeat: no-repeat;
  background-position: center;
  background-image: url("https://picsum.photos/1920/1080");
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  -ms-background-size: cover;
  .title-admin{
    color: white;margin-top:40px;font-size:30px;letter-spacing: 3px;font-weight: bold;
  }
  .login-box{
    display: flex;
    opacity: $opacity;
    .mJpg{
      width: 260px;
    }
  }
  .login-form {
    position: relative;
    min-width: 420px;
    padding:50px 40px;
    margin: 0 auto;
    overflow: hidden;
    background: #fff;
    .operation{
      display: flex;
      margin: 10px 0;
      .el-tag{
        cursor: pointer;
        margin-right: 8px;
        color: gray;
        &:hover{
          filter: brightness(80%);
        }
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 24px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: 400
    }
  }

  .show-pwd {
    position: absolute;
    right: 15px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .copyright{
    color:#fff;font-weight: bold;
  }
  @media screen and (max-width: 600px) {
    .mJpg{
      display: none;
    }
    .title-admin{
      font-size: 22px;
    }
    .copyright{
      font-size: 14px;
    }
  }
}
</style>
