<template>
  <tz-table  ref="tzTable"
             :api-url="api" :filter-tabs="filterTabs" :action="['delete','edit']"
             :table-column="tableColumn" showIndex
             :edit-column="editColumn"
             :edit-column-rules="editColumnRules"
             :action-others='[{title:"通过审核",icon:"el-icon-circle-check",onClick:passApprove,isShow:isShowAction}]'
             @after-delete-data-handle="()=>{this.updateFilterTabsStatus()}"
             :edit-column-default-value="client"
             @edit-data-handle="editDataHandle"
  >
  </tz-table>
</template>

<script>
  // import TZTable from "@/components/TZTable/index";
  import TZUtils from "@/utils/TZUtils";

  export default {
    name: 'Client',
    components: {
      // "tz-table":TZTable,
    },
    data:function(){
      return {
        api:"/service_api/client",
        editColumnRules:{
          id:[{required:true,pattern:/^[0-9a-zA-Z_]+$/,message: '只能包含数字、字符和_(下划线)',trigger:"blur"}],
          phone:[{required:true,pattern:/^1[3456789]\d{9}$/,message: '请输入正确手机号码',trigger:"blur"}],
        },
        showEditDialog:false,
        client:{
          approved:true,//直接编辑的默认审核通过
        },
        filterTabs:[
          {
            label:"全部",
            badge:0,//右上角提示的值
            key:"approved",
            value:undefined,
          },
          {
            label:"已审核",
            badge:0,//右上角提示的值
            key:"approved",
            value:true,
          },
          {
            label:"待审核",
            badge:0,//右上角提示的值
            key:"approved",
            value:false,
            apiKey:0//后端返回属性名-与后端对应-不重复
          }
        ],
        isNew:false,//是否正在新建数据
      }
    },
    created(){
      //更新tabs状态
      this.updateFilterTabsStatus();
    },
    computed: {
      tableColumn(){
        return [
          {prop:"logo",label:"头像",cType:'image',width:"60"},
          {prop:"id",label:"账户(ID)",filterable:true,isShowFilter:true,iSpan:6},
          {prop:"name",label:"公司",filterable:true,isShowFilter:true,iSpan:8},
          {prop:"phone",label:"手机",filterable:true,iSpan:8},
          {prop:"username",label:"管理姓名",filterable:true,iSpan:8},
          {prop:"email",label:"管理邮箱",filterable:true,iSpan:6,},
          {prop:"address",label:"地址",filterable:true,iSpan:6,width:"80"},
          {prop:"usable",label:"账号状态",cType:"switch",filterable:true,iSpan:6,width:"80"},
          {prop:"createTime",hide:true,label:"创建时间",cType:'dateTime',filterable:true,iType:"datetimeRange",iSpan:18,sortable:true},
          {prop:"updateTime",hide:true,label:"更新时间",cType:'dateTime',filterable:true,iType:"datetimeRange",iSpan:18},
          {prop:"remark",label:"备注"}
        ]
      },
      editColumn(){
        return [
          this.$service_tool.setImageUploadHeaders({prop:"logo",tip:"点击或拖拽上传，大小不能超过1MB", label:"LOGO", iType:"image", iSpan:24, style:"width:80px;height:80px"}),
          {prop:"id",label:"账户",tip:"唯一标识,注册后不可更改",disabled:!this.isNew,iType:"text", iSpan:24,style:"width:200px",placeholder:"CD1024",},
          {prop:"name",label:"公司",iType:"text",required:true,placeholder:"成都1024科技有限责任公司",iSpan:12,},
          {prop:"phone",label:"手机",iType:"text",placeholder:"15520449931",iSpan:12,},
          {prop:"username",label:"管理姓名", iType:"text",iSpan:12,required:true,placeholder:"唐彰",},
          {prop:"email",label:"管理邮箱",iType:"text",required:true,placeholder:"it_tangzhang@163.com",rules:[{type: 'email',message: '请输入正确的邮箱地址',}], iSpan:12,},
          {prop:"address",label:"地址",iType:"text",required:true,placeholder:"四川省成都市龙泉驿区派瑞国际",iSpan:24},
          {prop:"remark",label:"备注",iType:'text',iSpan:24}
        ];
      }
    },
    methods:{
      editDataHandle(row){
         this.isNew=!!!row;
      },
      updateFilterTabsStatus:function(){
        this.$http.get("/service_api/client/get_approved_status").then(res=>{
            this.filterTabs.forEach(item=>{
                item.badge = res.data[item.apiKey];
            })
        });
      },
      passApprove:function(rowData) {
         const loading = TZUtils.fullLoading(this);
         this.$http.put("/service_api/client/pass_approve/"+rowData.id).then(()=>{
            //审核成功
           this.$refs.tzTable.getTableData();//刷新数据
           this.updateFilterTabsStatus();//刷新状态
           this.$message.success("操作成功");
         }).finally(()=>{loading.close()})
      },
      //只有未审核的才展示此操作
      isShowAction:function(rowData){
        return !rowData.approved;
      },
    }
  }
</script>

<style scoped>

</style>
