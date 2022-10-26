<template>
    <tz-table ref="tzTable"
              :api-url="api"
              :table-column="tableColumn"
              showIndex :edit-column="editColumn"
    >
    </tz-table>
</template>

<script>
    import {h} from 'vue';
    export default {
        name: "Dict",
        components: {
        },
        data:function(){
            return {
                api:"/service_api/dict",
            }
        },
        methods:{
            dataColRenderFunction:function(data,column,index){
                return h('div',{
                    style:"overflow:auto;max-height:80px;white-space:pre-wrap",
                },data.data);
            }
        },
        computed: {
            tableColumn() {
                return [
                    {prop: "user", label: "使用者", filterable: true,iType:"select",selectData:[
                            {label:"开发",value:"开发"},{label:"运维",value:"运维"},{label:"用户",value:"用户"}
                        ], isShowFilter: true, iSpan: 4},
                    {prop: "name", label: "字典名称", filterable: true, isShowFilter: true, iSpan: 4},
                    {prop: "type", label: "字典类型", filterable: true, isShowFilter: true, iSpan: 4},
                    // {prop: "data", label: "字典值", iSpan: 6, renderData:this.dataColRenderFunction},
                    {prop: "usable", label: "是否启用", cType:"switch",iSpan: 4},
                    {prop: "remark", label: "备注"}
                ]
            },
            editColumn() {
                return [
                    {prop: "user", label: "使用者", iType: 'select',options:[{value:"用户"},{value:"开发"},{value:"运维"}],selectKey:"value",selectValue:"value",required: true, iSpan: 12},
                    {prop: "name", label: "字典名称", iType: 'text', required: true, iSpan: 12},
                    {prop: "type", label: "字典类型", iType: 'text', iSpan: 12, required: true},
                    {prop: "data", label: "字典值", iType: 'textarea',style:"height:500px;min-width:400px", required: true, iSpan: 24, },
                    {prop: "usable", label: "是否启用", iType: 'switch', iSpan: 24},
                    {prop: "remark", label: "备注", iType: 'text', iSpan: 24}
                ]
            }
        }
    }
</script>

<style scoped>

</style>
