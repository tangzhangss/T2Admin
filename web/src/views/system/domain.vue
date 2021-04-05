<template>
  <div>
    <tz-table ref="tzTable"
              :api-url="api"
              :table-column="tableColumn"
              showIndex :edit-column="editColumn"
             >
    </tz-table>

  </div>
</template>

<script>
  // import TZTable from "@/components/TZTable/index";
  import {arrayToTree} from "@/utils/treeUtils";
  import TZUtils from "@/utils/TZUtils";

  export default {
    name: 'Domain',
    components: {
      // "tz-table":TZTable,
    },
    data:function(){
      return {
        api:"/service_api/domain",
      }
    },
    created() {
    },
    computed: {
      tableColumn(){
        return [
          {prop:"name",label:"服务",filterable:true,isShowFilter:true,iSpan:6},
          {prop:"proAddress",label:"生产环境地址",filterable:true,isShowFilter:true,iSpan:8,width:"200"},
          {prop:"devAddress",label:"开发环境地址",filterable:true,isShowFilter:true,iSpan:8,width:"200"},
          {prop:"usable",label:"是否启用",cType:"switch",filterable:true,iSpan:6,width:"80"},
          {prop:"createTime",label:"创建时间",cType:'dateTime',width:"200",hide:true,iSpan:18},
          {prop:"updateTime",label:"更新时间",cType:'dateTime',width:"200",hide:true,iSpan:18},
          {prop:"remark",label:"备注"}
        ]
      },
      editColumn(){
        return [
          {prop:"name",label:"服务",iType:'text',required:true,iSpan:24},
          {prop:"proAddress",label:"生产环境地址",iType:'text',iSpan:12},
          {prop:"devAddress",label:"开发环境地址",iType:'text',required: true,iSpan:12,},
          {prop:"usable",label:"是否启用",iType:'switch',iSpan:24},
          {prop:"remark",label:"备注",iType:'text',iSpan:24}
        ]
      }
    },
    methods:{

    }
  }
</script>

<style lang="scss">

</style>
