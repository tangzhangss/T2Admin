<template>
  <div>
    <tz-table ref="tzTable"
              :api-url="api" :action="['delete','edit']"
              :dataList="dataList"
              @get-table-data-list="getTableDataList"
              :table-column="tableColumn" showIndex :edit-column="editColumn"
              :edit-column-rules="editColumnRules"
              :edit-column-default-value="menu"
              @edit-data-handle="editDataHandle"
              :action-others='[{title:"创建子菜单",icon:"add",onClick:createDataHandle}]'
              :pagination="null"
              row-key="id"
              :saveForceFlush="true"
              :showIndex="false"
    >
    </tz-table>
  </div>
</template>

<script>
  import TZUtils from "../../utils/TZUtils";

  export default {
    name: 'Menu',
    components: {
    },
    data:function(){
      return {
        api:"/service_api/menu?queryAll=true&orderBy=orderNo@ASC",
        editColumnRules:{},
        showEditDialog:false,
        menu:{
          redirect:"noRedirect",
          systemic:true,
          usable:true,
          parentDesc:'',
          externalService:false
        },
        dataList:[],
        isRoot:false,//是否是一级菜单
        isExternalService:false,//是否外部服务菜单
        clientList:[],//所有客户的列表（不包括超级管理员）
        domainList:[],//所有服务域名
      }
    },
    created() {
        let loading = TZUtils.fullLoading(this);
        this.$http.get("/service_api/client?id@NEQ="+window.superAdminClientId).then(res=>{
           this.clientList=res.data.content;
        })
        this.$http.get("/service_api/domain").then(res=>{
           this.domainList=res.data.content;
        })
        this.$http.get(this.api).then(res=>{
            let menu = res.data.content;
            //将菜单拼接成属性的结构
            this.setDataList(menu);
            loading.close();
        })
    },
    computed: {
      tableColumn(){
        return [
          // {prop:"icon",label:"icon",iSpan:8},
          {prop:"title",label:"菜单名",filterable:true,isShowFilter:true,iSpan:8,width:"200"},
          // {prop:"parent.title",label:"上级菜单",filterable:true,isShowFilter:true,iSpan:8,},
          {prop:"path",label:"path",iSpan:6,width:"200"},
          {prop:"url",label:"url",width:"200"},
          {prop:"orderNo",label:"排序"},
          {prop:"systemic",label:"系统创建",cType:"switch",filterable:true,iSpan:6,width:"80"},
          {prop:"usable",label:"是否启用",cType:"switch",filterable:true,iSpan:6,width:"80"},
          {prop:"createTime",hide:true,label:"创建时间",width:"200",cType:'dateTime',filterable:true,iType:"datetimeRange",iSpan:18},
          {prop:"updateTime",hide:true,label:"更新时间",width:"200",cType:'dateTime',iSpan:18},
          // {prop:"remark",label:"备注"},
        ]
      },
      editColumn(){
        return [
          {prop:"parentId",label: "上级菜单",iType:'text',readonly:true,showKey:"parentDesc",isShow:!this.isRoot,iSpan:20},
          {prop:"externalService", label:"外部服务", iType:'switch', iSpan:24,onChange:this.externalServiceChange,style:"width:200px"},
          {prop:"title", label:"菜单名", required:true,iType:'text',iSpan:12},
          {prop:"name",tip:"路由name需要保证唯一性", label:"name", iType:'text',iSpan:12},
          {prop:"icon", label:"icon",tip:"仅支持项目icons目录下的svg图标",  iType:'text', iSpan:12},
                //disabled:this.isExternalService,
          {prop:"path",label:"path",iType:'text',tip:"仅且仅需要一级菜单以/开头，不能以/结尾", required:true, iSpan:12},
          {prop:"redirect",tip:"一级菜单:当设置 noRedirect 的时候该路由在面包屑导航中不可被点击;其他:重定向",label:"redirect",iType:'text',iSpan:12},
          {prop:"domainId",isVisibleFunc:(item)=> item.externalService,label:"服务域名", iType:'select',iSpan:12,style:"width:200px",options:this.domainList,selectKey:"id",selectLabel:"name",selectValue:"id"},
          {prop:"url",isShow:(!this.isRoot)||this.isExternalService,tip:"仅路由页面填写", label:"页面路径", iType:'text',iSpan:12},
          {prop:"orderNo", label:"排序", iType:'number', iSpan:12},
          {prop:"systemic", label:"系统菜单", iType:'switch', iSpan:6,},
          {prop:"usable", label:"是否启用", iType:'switch', iSpan:6},
          {prop:"clientIdsArray",tip:"非系统菜单有效", label:"所属客户",isVisibleFunc:(item)=> !item.systemic,iType:'select',multiple:true,iSpan:24,options:this.clientList,selectKey:"id",selectLabel:"name",selectValue:"id"},
        ]
      }
    },
    methods:{
      setDataList(data){
        let list = TZUtils.arrayToTree(data,"parentId");
        TZUtils.treeSort(list,"orderNo",true);
        this.dataList=list;
      },
      getTableDataList(data){
        //这个只有搜索调用-搜索之后不需要组成树状结构
        if(data.url.indexOf("title")>-1){
          this.dataList=data.dataList;
        }else{
          this.setDataList(data.dataList);
        }
      },
      //创建 回调 这里data表示父级菜单信息或则undefined
      editDataHandle(data){
        if(data && data.parentId){
          this.isRoot=false;
          data.parentDesc = this.getParentDesc(data).reverse().join('/');
        }else{
          //表示是一级菜单
          this.isRoot=true;
        }
        if(data && data.domainId){
          data.externalService=true;
          this.externalServiceInit(data);
        }

        if(data && data.externalService){
          this.isExternalService=true;
        }else{this.isExternalService=false}

        //给到记录值，地址编辑之后--由查看变成编辑--状态不变
        if (!data) data = {};
        data.externalService=this.isExternalService;
      },
      externalServiceInit(data){
        // data.path="iFrame/${id}";
        // data.name=data.name||"IFrame";
        //不给默认值
      },
      externalServiceChange(val,data){
        this.isExternalService=val;
        if(this.isExternalService)this.externalServiceInit(data);
      },
      //这个是创建子菜单使用-需要配合editDataHandle
      createDataHandle(data){
        //表示是子菜单
        this.menu.parentId=data.id;
        this.menu.parent=data;
        let initData = this.menu;
        //打开编辑框
        this.$refs.tzTable.openEdit(initData,true);
      },
      getParentDesc(data,res=[]){
        let parent = data.parent;
        if(!parent){
          return res;
        }
        res.push(parent.title);
        return this.getParentDesc(parent,res);
      }
    }
  }
</script>

<style scoped>

</style>
