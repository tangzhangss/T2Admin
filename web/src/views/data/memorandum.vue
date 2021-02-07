<template>
  <div>
    <tz-table  ref="tzTable"
               :api-url="api" :action="['delete','edit']"
               :table-column="tableColumn" showIndex
               :edit-column="editColumn"
               :action-others='[{title:"查看",icon:"el-icon-view",onClick:openDataModal}]'
               @after-delete-data-handle="()=>{this.updateFilterTabsStatus()}"
    >
    </tz-table>

    <div class="data-memorandum-el-dialog">
      <el-drawer
        :title="memorandum.title"
        :visible.sync="isDrawerShow"
        direction="rtl"
        size="50%"
        >
        <div v-html="memorandum.content"></div>
      </el-drawer>
    </div>
  </div>
</template>

<script>
  import TZTable from "@/components/TZTable/index";
  import TZUtils from "@/utils/TZUtils";

  export default {
    name: 'Memorandum',
    components: {
      "tz-table":TZTable,
    },
    data:function(){
      return {
        api:"/data_api/memorandum",
        isDrawerShow:false,
        memorandum:{},//当前选中的memorandum
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
          {prop:"content", label:"内容", iType:"quill-editor", iSpan:24},
        ];
      }
    },
    methods:{
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
