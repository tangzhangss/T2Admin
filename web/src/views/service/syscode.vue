<template>
  <div>
    <tz-table  ref="tzTable"
               :api-url="api"
               :table-column="tableColumn" showIndex
               :edit-column="editColumn"
    >
    </tz-table>
  </div>
</template>

<script>
  export default {
    name: 'SysCode',
    components: {
    },
    data:function(){
      return {
        api:"/sys_api/sys_code",
      }
    },
    created(){},
    computed: {
      tableColumn(){
        return [
          {prop:"noName",label:"编码名称"},
          {prop:"noCode",label:"编码代码"},
          {prop:"noGroup",label:"分组名"},
          {prop:"formula",label:"编码规则"},
          {prop:"currentNo",label:"当前序号"},
        ]
      },
      editColumn(){
        return [
          {prop:"noName", label:"名称", iType:"text",required:true,iSpan:12},
          {prop:"noCode", label:"代码",iType:"text",required:true, iSpan:12},
          {prop:"noGroup", label:"分组名",iType:"text",required:true, iSpan:12},
          {prop:"formula", label:"规则",iType:"text",required:true, iSpan:12},
        ];
      }
    },
    methods:{

    }
  }
</script>

<style lang="scss">


</style>
