<template>
  <div>
    <tz-table ref="tzTable"
              :action-others='[
                {title:"角色管理",icon:"el-icon-s-custom",onClick:updateUserRole},
                {title:"修改密码",icon:"el-icon-key",onClick:modifyUserPwd},
              ]'
              :api-url="api"
              :action="['delete','edit']"
              :table-column="tableColumn"
              showIndex :edit-column="editColumn"
              :edit-column-rules="editColumnRules"
              :edit-column-default-value="user">
    </tz-table>

    <el-dialog
      title="选择角色"
      v-model="roleDrawerShow">
      <div v-if="roleList&&roleList.length>0">
        <el-checkbox-group v-model="checkRoleList" @change="checkRoleChange">
          <el-checkbox v-for="item in roleList" :label="item.name"></el-checkbox>
        </el-checkbox-group>
        <div style="text-align: right">
          <el-button type="primary" class="tz-btn" @click="updateRoleIds">保存</el-button>
        </div>
      </div>
      <div v-else style="color: gray">[暂无定义角色]</div>
    </el-dialog>

    <el-dialog
      title="修改密码"
      v-model="modifyPwdDialogVisible"
      >
       <el-form label-position="right" label-width="auto" :inline="true">
         <el-form-item label="新密码" prop="pass" style="display: flex;align-items: center" required>
           <el-input type="password" v-model="tempValue1" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="确认密码" prop="checkPass" style="display: flex;align-items: center" required>
           <el-input type="password"  v-model="tempValue2"  autocomplete="off"></el-input>
         </el-form-item>
       </el-form>
      <template #footer>
        <div  class="dialog-footer">
          <el-button class="tz-btn" @click="modifyPwdDialogVisible = false">取 消</el-button>
          <el-button  class="tz-btn" type="primary" @click="modifyUserPwdExec">确 定</el-button>
        </div>
     </template>
    </el-dialog>
  </div>
</template>

<script>
  import TZTable from "@/components/TZTable/index";
  import {arrayToTree} from "@/utils/treeUtils";
  import TZUtils from "@/utils/TZUtils";

  export default {
    name: 'User',
    components: {
      "tz-table":TZTable,
    },
    data:function(){
      return {
        //不能查询出当前租户的管理账号(登录账号同企业ID)--统一在企业信息里面修改（密码一样）
        api:"/service_api/user?username@NEQ="+this.$store.getters.userInfo.clientId,
        editColumnRules:{phone:[{required:true,pattern:/^1[3456789]\d{9}$/,message: '请输入正确手机号码',trigger:"blur"}],},
        showEditDialog:false,
        user:{
          password:"0b008dc06f6c9097ac9c9fcb194482ba",//123456的加密值
        },
        roleDrawerShow:false,
        roleList:[],
        checkRoleList:[],
        currentUser:{},
        modifyPwdDialogVisible:false,

        tempValue1:"",//临时值1
        tempValue2:"",//临时值2
      }
    },
    created() {
      //获取当前租户所有的菜单
      this.$http.get("/service_api/role/get_all").then((res)=>{
        this.roleList=res.data.content;
      })
    },
    computed: {
      tableColumn(){
        return [
          {prop:"avatars",label:"头像",cType:'image',width:"60"},
          {prop:"name",label:"用户名",filterable:true,isShowFilter:true,iSpan:6},
          {prop:"username",label:"登录账号",filterable:true,isShowFilter:true,iSpan:8},
          {prop:"phone",label:"联系",filterable:true,iSpan:8},
          {prop:"email",label:"邮箱",filterable:true,iSpan:8},
          {prop:"usable",label:"账号状态",cType:"switch",filterable:true,iSpan:6,width:"80"},
          {prop:"createTime",label:"创建时间",cType:'dateTime',filterable:true,iType:"datetimeRange",iSpan:18},
          {prop:"updateTime",label:"更新时间",cType:'dateTime',iSpan:18},
          {prop:"remark",label:"备注"}
        ]
      },
      editColumn(){
        return [
          {
            prop:"avatars",
            tip:"点击或拖拽上传，大小不能超过1MB",
            label:"用户头像",
            iType:"image",
            iSpan:24,
            style:"width:80px;height:80px"
          },
          {prop:"name",label:"用户名",iType:'text',required:true,iSpan:12},
          {prop:"phone",label:"手机",iType:'text',iSpan:12},
          {prop:"email",label:"邮箱",iType:'text',required: true, rules:[{type: 'email',message: '请输入正确的邮箱地址',trigger:"blur"}],iSpan:12,},
          {prop:"username",label:"登录账号",placeholder:"如果不填，将默认为手机号",iType:'text',iSpan:12,tip:"密码默认123456,请保存用户信息之后再修改"},
          {prop:"usable",label:"账号状态",iType:'switch',iSpan:24},
          {prop:"remark",label:"备注",iType:'text',iSpan:24}
        ]
      }
    },
    methods:{
      updateRoleIds(){
        let loading = TZUtils.fullLoading(this,"处理中");
        let user = this.currentUser;
        let checkRoleList = this.checkRoleList;
        user.roleIds=checkRoleList.join(",");
        this.$http.post("/service_api/user",user).then((res)=>{
          this.roleDrawerShow=false;
          this.$message.success("保存成功");
        }).finally(()=>{
          loading.close();
        });
      },
      updateUserRole(data){
        this.currentUser=data;
        this.checkRoleList = data.roleIds?data.roleIds.split(","):[];
        this.roleDrawerShow=true;
      },
      checkRoleChange(val){
        this.checkRoleList = val;
      },
      //修改用户密码
      modifyUserPwd(data){
        this.currentUser=data;
        this.modifyPwdDialogVisible=true;
      },
      modifyUserPwdExec(){
        let currentUser = this.currentUser;
        let pwd = this.tempValue2;
        if(TZUtils.trim(this.tempValue1).length<6){
          this.$message.error("密码长度不能小于6位");
          return false;
        }
        if(this.tempValue1!=this.tempValue2){
          this.$message.error("密码两次密码不一致,请检查");
          return false;
        }
        let loading = TZUtils.fullLoading(this);
        currentUser.password=pwd;
        this.$http.post("/service_api/user/modify_pwd",currentUser).then(res=>{
           this.currentUser=res.data;
           this.$message.success("操作成功");
          this.modifyPwdDialogVisible=false;
        }).finally(()=>{
          loading.close();
        })
      },
    }
  }
</script>

<style lang="scss">

</style>
