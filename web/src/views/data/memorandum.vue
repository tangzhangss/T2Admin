<template>
  <div>
    <tz-table  ref="tzTable"
               :api-url="api" :action="isShowClearBtn?['delete']:['delete','edit']"
               :table-column="tableColumn" showIndex
               :edit-column="editColumn"
               :action-others='[
                       {title:"查看",icon:"el-icon-view",onClick:openDataModal},
                       {title:"撤回",icon:"recall",onClick:updateWithUsable,isShow:()=>isShowClearBtn}
               ]'
               :operation="isShowClearBtn?[]:['create']"
               :clear="{
                 show:isShowClearBtn,
                 param:{
                   'usable@EQ':false
                 }
               }"
               :filter-tabs="filterTabs"

    >
    </tz-table>

    <div class="data-memorandum-el-dialog">
      <el-drawer
        :title="memorandum.title"
        v-model="isDrawerShow"
        direction="rtl"
        size="50%"
        >
        <div v-html="memorandum.content"  style="white-space: pre-wrap;"></div>
      </el-drawer>
    </div>
  </div>
</template>

<script>
  import TZUtils from "@/utils/TZUtils";

  export default {
    name: 'Memorandum',
    components: {
    },
    data:function(){
      return {
        api:"/data_api/memorandum",
        isDrawerShow:false,
        isShowClearBtn:false,
        memorandum:{},//当前选中的memorandum
        filterTabs:[
          {
            label:"正常记录",
            badge:0,//右上角提示的值
            key:"usable",
            value:true,
            onClick:()=>{this.isShowClearBtn=false}
          },
          {
            label:"回收站",
            badge:0,//右上角提示的值
            key:"usable",
            value:false,
            onClick:()=>{this.isShowClearBtn=true}
          },
        ],
      }
    },
    created(){},
    computed: {
      tableColumn(){
        return [
          {prop:"title",label:"标题"},
          {prop:"createTime",label:"创建时间",cType:'dateTime',iType:"datetimeRange",iSpan:18},
          {prop:"updateTime",label:"更新时间",cType:'dateTime',iType:"datetimeRange",iSpan:18},
        ]
      },
      editColumn(){
        return [
          {prop:"title", label:"标题", iType:"text",required:true,iSpan:24},
          {prop:"content", label:"内容",iType:"editor", iSpan:24},
        ];
      }
    },
    methods:{
      updateWithUsable(row){
        row.usable=true;
        let loading = TZUtils.fullLoading(this);
        this.$http.post(this.api,row).then((data)=>{
           loading.close();
           this.$refs.tzTable.getTableData();
           this.$message.success("撤回成功");
        })
      },
      openDataModal(row){
        this.isDrawerShow=true;
        this.memorandum=row;
      }
    }
  }
</script>

<style lang="scss">
  .data-memorandum-el-dialog{
      .el-drawer{
        padding: 10px;
        .el-drawer__header{
          padding: 0px;
          margin-bottom: 10px;
          font-weight: bold;
        }
      }
  }

</style>
