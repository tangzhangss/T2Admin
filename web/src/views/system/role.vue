<template>
  <div>
    <tz-table
              :api-url="api" :action="['delete','edit']"
              :action-others='[
                {"title":"菜单管理",onClick:updateMenuMethod,icon:"el-icon-menu"}
              ]'
              :table-column="tableColumn" showIndex :edit-column="editColumn" >
    </tz-table>


    <el-drawer class="system-role-menu-drawer"
               :with-header="false"
               :visible.sync="menuDrawerShow"
               direction="rtl">
      <div style="text-align: right">
        <el-button type="primary" class="tz-btn" @click="updateMenuIds">保存</el-button>
      </div>
      <el-tree
        ref="tzTree"
        v-loading="menuDrawerLoading"
        :data="menuList"
        show-checkbox
        node-key="id"
        @check="check"
        :props="{children: 'children',label: 'title'}">
      </el-tree>
    </el-drawer>
  </div>
</template>

<script>
  import TZTable from "@/components/TZTable/index";
  import {arrayToTree,getLeafNode} from "@/utils/treeUtils";
  export default {
    name: 'Role',
    components: {
      "tz-table":TZTable,
    },
    data:function(){
      return {
        api:"/service_api/role",
        menuList:[],
        menuDrawerShow:false,
        menuDrawerLoading:false,
        menuCheckedKeys:[],//被选中的菜单
        currentData:{},
      }
    },
    created() {
      //获取当前租户所有的菜单
      this.$http.get("/service_api/menu/get_client_menu").then((res)=>{
        this.menuList=arrayToTree(res.data,"parentId");
      })
    },
    computed: {
      tableColumn(){
        return [
          {prop:"name",label:"角色名",filterable:true,isShowFilter:true,iSpan:8},
          {prop:"creator.name",label:"创建人",filterable:true,isShowFilter:true,iSpan:8},
          {prop:"createTime",label:"创建时间",cType:'dateTime',iSpan:18},
          {prop:"updateTime",label:"更新时间",cType:'dateTime',iSpan:18},
          {prop:"remark",label:"备注"}
        ]
      },
      editColumn(){
        return [
          {prop:"name",label:"角色名",iType:'text',required: true,iSpan:8,},
          {prop:"remark",label:"备注",iType:'text',iSpan:24}
        ]
      }
    },
    methods:{
      //保存角色对应的菜单
      updateMenuIds(){
        this.menuDrawerLoading=true;
        this.currentData.menuIds = this.menuCheckedKeys.join(",");
        this.$http.post("/service_api/role",this.currentData).then((res)=>{
          this.$message.success("保存成功");
          this.menuDrawerShow=false;
        }).finally(()=>{
          this.menuDrawerLoading=false;
        })
      },
      check(data,status){
        this.menuCheckedKeys = status.checkedKeys.concat(status.halfCheckedKeys);
      },
      updateMenuMethod(rowData){
        this.currentData=rowData;
        this.menuDrawerShow=true;
        //更新菜单
        let checkedKeys = rowData.menuIds?rowData.menuIds.split(","):[];

        if(checkedKeys.length>0){
          //获取this.menuList中的叶子节点
          let leafs = getLeafNode(this.menuList);
          let keys = [];
          //过滤掉非叶子节点
          leafs.forEach(item=>{
            checkedKeys.some(key=>{
              if(key==item.id){
                keys.push(key);
                return true;
              }
            })
          })
          checkedKeys  = keys;
        }
        this.$nextTick(() => {
          this.$refs.tzTree.setCheckedKeys(checkedKeys);
        })
        this.menuDrawerLoading=false;
      }
    }
  }
</script>

<style lang="scss">

  .system-role-menu-drawer{
    .el-drawer{
      padding: 10px;
    }
  }
</style>
