# TZCC-REN ADMIN STSREM 管理后台(前端)

基础框架：element-template 
UI框架:element-plus
后端配合框架:my_admin_api(自制)

## 开发规范:
    1.所有组件prop属性type=Function：
        默认都是返回undefined;
        如果需要自定义，请定义返回值（非undefined）;
        如果返回true(==),才会进行后续操作（如果有的话）;
    
## 说明文档（所有组件都是基于element-ui,使用习惯几乎一样）

### TZTable(表格)
      
“自定义展示列”=》通过列的名字（label）来区分=》请确保唯一
  hide:判定该列是否展示,

“筛选”=》
    需要对tableColumn给予fliterable（表示可作为筛选条件）
    iType(选填，筛选类型(对饮input类型)，如果没有就跟cType属性一直,如:时间段（dateRange datetimeRange）等需要自定义)
    iSpan:筛选条件占用总比例（24）的长度
    isShowFilter（默认展示该条件）

编辑（新建）:
 submit-api 配合后端框架使用
 edit-data-handle(data) 编辑时候数据处理
操作行为:
action-others='[{title:"创建子菜单",icon:"el-icon-circle-plus",onClick:editDataHandle(row),isShow:isShow(row)}]'

成功回调 editSuccessHandle
提交之前回调 editSubmitBeforeFunc
提交之后回调 editSubmitAfterFunc

示例(部分代码):
```
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
  </div>
</template>

<script>
  export default {
    data:function(){
      return {
        //不能查询出当前租户的管理账号(登录账号同企业ID)--统一在企业信息里面修改（密码一样）
        api:"/service/user?username@NEQ="+this.$store.getters.userInfo.clientId,
        editColumnRules:{phone:[{required:true,pattern:/^1[3456789]\d{9}$/,message: '请输入正确手机号码',trigger:"blur"}],},
      }
    },
    created() {
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
          {prop:"usable",label:"账号状态",iType:'switch'},
          {prop:"remark",label:"备注",iType:'text',iSpan:24}
        ]
      }
    },
```      
      
      
         