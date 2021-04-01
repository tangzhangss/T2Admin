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

### TZEditableTable(可编辑表格-使用方式和表格一致，这里仅仅罗列注意事项)
    根据表格列的readony属性判断该行是否可以编辑（所以编辑对象不能有这个属性）  
 :table-column(表格的列)   
   > required: true,(不能为空)
   > validator: (value)=>{} 保存验证器，返回false不保存 true保存
>
### TZTable(表格)

    表格的删除是单独保存和修改删除，可编辑的表格是批量删除和保存修改
      
“自定义展示列”=》通过列的名字（label）来区分=》请确保唯一
  hide:判定该列是否展示,

##### “筛选”=》
    需要对tableColumn给予fliterable（表示可作为筛选条件）
    iType(选填，筛选类型(对饮input类型)，如果没有就跟cType属性一直,如:时间段（dateRange datetimeRange）等需要自定义)
    iSpan:筛选条件占用总比例（24）的长度
    isShowFilter（默认展示该条件）

##### 主要属性:
 api-url 配合后端框架使用(
        拉取提交删除的utl,对应post get delete请求
    )
 dataList 表格数据（优先使用dataList的数据，所以getTableData拉取数据之后会调用回调处理数据@get-table-data-list（data））   
    =>自己传入dataList之后请注意监听@get-table-data-list事件
 edit-data-handle(data) 编辑时候数据处理
##### 表格清空功能(prop)
param的构造同后端get请求查询条件map结构
```
:clear="{
   show:isShowClearBtn,
   param:{
       'usable@EQ':false
        }
}"
``` 
##### 操作行为:
表格右上角的操作行为 :operation-others(prop)
表格中每一行记录后面的操作信息如下，两者结构相同
```
:action-others='[{title:"创建子菜单",icon:"el-icon-circle-plus",onClick:editDataHandle(row),isShow:isShow(row)}]'
```
同时还有系统默认的一些行为可通过一下方式使用
```
:action=['create','delete',....]
```
##### 分页:
pagination==null不显示分页数据
```
     paginationLayout:{
                type:String,
                default:"total, sizes, prev, pager, next, jumper"
            },
            pagination:{
              type:Object,
              default:{
                  totalPages:1,
                  totalElements:0,
                  size:10,//每页显示的行数
                  number:1,//当前页
            }
     },
```
##### 顶部tabs: (点击之后查询的时候会拼接此查询条件，默认第一个)
    :filter-tabs="filterTabs"
    ```
    filterTabs:[
              {
                label:"全部",
                badge:0,//右上角提示的值
                key:"usable",
                value:true,
                onClick:()=>{this.isShowClearBtn=false}//回调
              },
              {
                label:"回收站",
                badge:0,//右上角提示的值
                key:"usable",
                value:false,
                onClick:()=>{this.isShowClearBtn=true}//回调
                apiKey:0//后端返回属性名-与后端对应-不重复（如设置badge的值）
              },
            ],
    ```   

##### 成功回调 editSuccessHandle
##### 提交之前回调 editSubmitBeforeFunc
##### 提交之后回调 editSubmitAfterFunc
##### 删除之前的prop(异步方法，返回false===,取消删除)   
```
beforeDeleteData:{
    type:Function,
    default:async(data)=>true
  }
```

##### 示例(部分代码,20201205版):
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
      
      
         