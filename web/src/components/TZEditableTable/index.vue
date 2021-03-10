<template>
  <div class="tz-editable-table" style="height:100%;overflow: auto">
      <!--顶部操作-->
    <div class="operation">
          <div class="left">
              <el-button v-if="operation.includes('create')" type="primary" plain @click="editData()">新建</el-button>
              <el-button v-if="operation.includes('batchSave') && isShowBatchSave" type="primary" plain @click="batchSaveData">保存</el-button>
              <el-popconfirm
                      v-if="operation.includes('batchDelete') && currentSelectRowData.length>1"
                      title="您正在批量删除选择的记录？"
                      @confirm="batchDeleteData"
                      icon="el-icon-warning"
              >
                  <template  #reference>
                      <el-button type="primary" plain>删除</el-button>
                  </template>
              </el-popconfirm>

              <template v-for="item in operationOthers">
                  <el-button v-if="item.isShow!==false" :type="item.type||'primary'" plain @click="item.onClick()">{{item.title}}</el-button>
              </template>
          </div>
          <div class="right">
          </div>
      </div>
    <el-table
      ref="elEditableTable"
      :header-cell-class-name="headerStyleClass"
      class="table-class"
      v-loading="loading"
      :data="tableData"
      :style="cStyle" :width="width"
      :max-height="maxHeightValue"
      :row-class-name="tableRowClassName"
      :highlight-current-row="highlightCurrentRow"
      @current-change="handleCurrentChange"
      @selection-change="handleSelectionChange"
    >
    <!--多选时显示-->
    <el-table-column v-if="selection"
                     type="selection"
                     fixed="left"
                     width="55">
    </el-table-column>

      <!--自定义索引-->
      <el-table-column v-if="showIndex"
                     label="序号" type="index"
                     fixed="left">
      </el-table-column>


      <!--cType是数据类型-->
      <template v-for="(item,index) in tableColumn">
          <el-table-column
                  :label="item.label"
                  :width="item.width||'unset'"
                  :fixed="item.fixed"
          >
              <template #default="scope">
                  <!--仅仅展示-->
                  <tz-column-readonly v-if="scope.row.readonly!==false" :form="scope.row" :item="item"></tz-column-readonly>
                  <!--编辑-->
                  <tz-column-edit  v-else :form="scope.row" :item="item"></tz-column-edit>
              </template>
          </el-table-column>
      </template>
      <!--操作-->
      <el-table-column v-if="action.length>0 || actionOthers.length>0" label="操作" fixed="right">
        <template #default="scope">
          <div class="action">
          <span class="edit" v-show="action.includes('edit') && scope.row.readonly!==false"  title="查看|修改" @click="editData(scope.row)">
            <i class="el-icon-edit-outline pointer"/>
          </span>
           <span class="save" v-show="action.includes('save') && scope.row.readonly===false"  title="保存" @click="saveData(scope.row,scope.$index,scope)">
                <i class="el-icon-circle-check pointer"/>
           </span>
           <span class="cancel" v-show="action.includes('cancel') && scope.row.readonly===false"  title="取消" @click="cancelEditData(scope.row,scope.$index)">
                <i class="el-icon-circle-close pointer"/>
           </span>
           <span class="delete" v-show="action.includes('delete') && scope.row.readonly!==false"  title="删除这条记录">
                       <el-popconfirm
                               title="您正在删除这条记录？"
                               @confirm="deleteData([scope.row])"
                               icon="el-icon-warning"
                       >
                         <template  #reference>
                               <i class="el-icon-delete pointer"/>
                         </template>
                        </el-popconfirm>
            </span>
            <span  v-for="action in actionOthers" v-show="!(action.isShow&&!action.isShow(scope.row))"  :title="action.title" @click="action.onClick(scope.row)">

                        <i v-if="action.icon.includes('el-icon')" :class="action.icon+' pointer'"/>
                        <svg-icon v-else :icon-class="action.icon" class="pointer"/>
                   </span>&nbsp;
          </div>
        </template>
      </el-table-column>
   </el-table>
    <!--分页-->
    <div class="pagination" v-if="pagination">
        <el-pagination
          background
          :layout="paginationLayout"
          :total="paginationData.totalElements"
          :page-count="paginationData.totalPages"
          :current-page="paginationData.number"
          :page-size="paginationData.size"
          @size-change="paginationSizeChange"
          @current-change="paginationCurrentChange"
        >
        </el-pagination>
      </div>
  </div>

</template>

<script>
  import "../dialogDrag";
  import TZSearch from "./search"
  import TZFormDialog from "@/components/TZForm/dialog";
  import TZUtils from "../../utils/TZUtils";
  import TZColumnEdit from "@/components/TZColumn/edit";
  import TZColumnReadonly from "@/components/TZColumn/readonly";

  export default {
      name: 'tz-table',
      components:{
        'tz-search':TZSearch,
        "tz-form-dialog":TZFormDialog,
        "tz-column-readonly":TZColumnReadonly,
        "tz-column-edit":TZColumnEdit,
      },
      props: {
        //是否选择高亮（单选+currentChange使用）
        highlightCurrentRow:{
            type:Boolean,
            default:false
        },
        //表单编辑页默认的值
        editColumnDefaultValue:{
            type:Object,
            default:()=>null
        },
        editTitle:{
          type:String,
          default:()=>"编辑"
        },
        apiUrl:[String],//url请求地址
        dataList:[Array],//自己传入的数据地址--不能有默认值--同时并继续监听[@]get-table-data-list时间
        headerStyleClass:{
          type:String,
          default:()=>"headerStyleClass"
        },
        renderHeader:[Function],//表头自定义
        tableColumn:{
          type:Array,
          default: ()=>[]
        },//表的列
        width:[String],//表格的列宽
        selection:[Boolean],//是否多选
        showIndex:[Boolean],//是否展示索引
        cStyle: {
          type:String,
          default:()=>""
        },//表格的style,
        indexMethod:{//自定义索引，需要默认行为=》属性
          type:Function,
          default:null
        },
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
        //顶部左侧的操作按钮
        operation:{
          type:Array,
          default:()=>['create','batchSave','batchDelete']
        },
        operationOthers:{
          type:Array,
          default:()=>[]
        },
        //操作选项如：edit,delete
        action:{
          type:Array,
          default:()=>[]
        },
        actionOthers:{
          type:Array,
          default:()=>[]
        },
        otherHeight:{
            type:Number,
            default:()=>0
        },
        idKey:{//表格对象的主键key默认'id'
           type:String,
           default:()=>'id'
        },
        beforeDeleteData:{
            type:Function,
            default:async(dataArr)=>true
        }
      },
      data(){
        return {
          loading:false,
          tableData:[],
          tableFilterColumn:[],//表格过滤条件
          selectedTableFilterColumn:[],//被选中的筛选条件
          tableColumnData:[],
          searchApiUrl:this.apiUrl,//当前的搜索的url-查询条件之后的，比如用于翻页的时候带上
          paginationData:null,
          maxHeightValue:undefined,//自动计算的表格最大高度
          filterTabsQuery:'',
          editDataOldValueMap:new Map(),//记录被编辑对象在编辑之前的值（撤销修改）
          currentSelectRowData:[],//多选时当前选择的数据
        }
      },
      created(){
        this.initPaginationData();//初始化分页数据
        this.init();//数据初始化
        let func = ()=>{
           let clientHeight= document.documentElement.clientHeight;
           this.maxHeightValue =clientHeight-110-2-this.otherHeight; //操作40 分页50  table margin上下10 其他的高度 2容错
        };
        func();//先执行一次
        window.onresize = func;
      },
      mounted(){

      },
      methods: {
          //批量保存
          batchSaveData(){
              //获取表格数据中删除状态的数据
              let dataList = this.tableData;
              let data = dataList.filter(d=> d.readonly===false);
              for(let i=0;i<data.length;i++){
                  if(!this.saveValidator(data[i]))return false;
              }
              let loading = TZUtils.fullLoading(this);
              this.$http.put(this.apiUrl,data).then((res) => {
                  //全部变成可读
                  data.forEach(d=>{
                      d.readonly=true;
                  })
                  this.$message.success("保存成功");
              }).finally(()=>loading.close())
          },
          //批量删除
          batchDeleteData(){
            let deleteData = this.currentSelectRowData;
            this.deleteData(deleteData);
          },
          saveData(data,index,scope){
            let loading = TZUtils.fullLoading(this);
            if(this.saveValidator(data)){
                this.$http.post(this.apiUrl,data).then((res) => {
                    this.tableData[index] = res.data;//老的值替换
                    res.data.readonly=true;
                }).finally(()=>loading.close())
            }else{
                loading.close();
            }

        },
        saveValidator(data){
            //遍历tableColumn验证器
            for (let i = 0; i < this.tableColumn.length; i++) {
                let item = this.tableColumn[i];
                //不能为空判断
                if(item.required && !data[item.prop]){
                    this.$message.error(item.label+"不能为空!");
                    return false;
                }
                //验证器判断
                if(!item.validator)continue;
                if(item.validator(data[item.prop])===false){return false}
            }
            return true;
        },
        cancelEditData(data,index){
            //查询改对象是否新建
            let obj = this.editDataOldValueMap.get(data[this.idKey]);
            if(obj){
                this.tableData[index] = obj;//老的值替换
                obj.readonly=true;
                this.editDataOldValueMap.delete(data[this.idKey]);
            }else{
                this.tableData.splice(index,1);//删除
            }
        },
        //新建更新数据
        editData(data){
          //这是一个异步的func
          this.$emit("edit-data-handle",data);
          if(data){
              //编辑
              data.readonly=false;
              this.editDataOldValueMap.set(data[this.idKey],TZUtils.deepClone(data));
          }else{
              //新建（新建不需要保留旧的对象）
              let editObjectData = data || TZUtils.deepClone(this.editColumnDefaultValue) ||{};
              editObjectData.readonly=false;
              this.tableData.unshift(editObjectData);
          }
        },
        deleteData(dataArr){
          this.loading=true;
          this.beforeDeleteData(dataArr).then((res)=>{
              if(res!==false) {
                  //删除操作
                  this.$http.delete(this.apiUrl,dataArr).then((res)=>{
                      this.tableData.splice(0,1);//随便删除一个，避免抖动
                      this.getTableData();
                      this.$message.success("删除成功");
                      //删除数据之后
                      this.$emit("after-delete-data-handle",dataArr);
                  }).finally(()=>{this.loading=false;})
              }else{
                  this.loading=false;
              }
          })
        },
        init(){
          /*
            1.初始化tableData
           */
          //dataList为主
          if (this.dataList){
            this.tableData = this.dataList;
          }else if(this.apiUrl){
              //---------------------------------
              //如果没有传入数据且有apiUrl就自己请求
              this.getTableData();
          }
          //复制tableCloumn并获取需要的信息
          /*
          由于需要计算属性监控hide的值，所以必须有一个值（true|false）,
          没有值得计算属性不会重新计算。原因暂时不知(可能是最开始没有值得监听不到)
           */
          //过滤列
         let filterColumn=[];
         this.tableColumn.forEach((item,index)=>{
            if(!item.hide)item.hide=false;
            if(item.filterable){
              //拷贝一份不然操作的是同一个对象-会出现一系列问题
              let filterItem = TZUtils.deepClone(item);
              if(!filterItem.isShowFilter)filterItem.isShowFilter=false;//默认都不进入选中状态
              if(filterItem.cType && !filterItem.iType)filterItem.iType=filterItem.cType;//筛选类型默认就是cType
              filterColumn.push(filterItem);
            }
          })
          //筛选条件可选择的列
          this.tableFilterColumn=filterColumn;
        },
        initPaginationData(){
            this.paginationData=TZUtils.deepClone(this.pagination);
        },
        //清除所有查询条件
        searchClear(){
          this.tableFilterColumn.forEach((item)=>{
            item.isShowFilter=false;
          })
        },
        //接受到的是搜索之后的数组
        search(items){
           let queryParam=[];
           items.forEach(item=>{
             switch (typeof item.value){
               case "string":{
                 item.value=TZUtils.trim(item.value);
                 if(!item.value)return;//中断本次循环
                 break;
               }
               case "undefined":{//因为存在没有填的情况是undefined
                 if(item.iType && item.iType.toUpperCase()=="SWITCH"){
                   item.value=false
                 }else{
                   return;
                 }
                 break;
               }
               default:{
                   if(!item.value)return;//中断本次循环
               }
             }

             //这里特别处理
             if(item.prop == "usable"){
               queryParam.push("queryAll=true");
             }
             if(!item.iType){
               queryParam.push(item.prop+"@LIKE="+item.value);
             }else if(item.iType.toUpperCase() == "DATERANGE" ||item.iType.toUpperCase() == "DATETIMERANGE"){
               let dateArr = item.value;
               queryParam.push(item.prop+"@GTE="+TZUtils.formatDate(dateArr[0]));
               queryParam.push(item.prop+"@LTE="+TZUtils.formatDate(dateArr[1]));
             }else if(["DATE","DATETIME"].indexOf(item.iType.toUpperCase())>-1) {
               queryParam.push(item.prop+"@EQ="+TZUtils.formatDate(item.value));
             }else{
               queryParam.push(item.prop+"@EQ="+item.value);
             }
           })
          let queryStr = queryParam.join("&");
          if(/.*\?+.*/.test(this.apiUrl))queryStr="&"+queryStr
          else queryStr="?"+queryStr
          //遍历items数组获取键值对
          let url = this.apiUrl+(queryStr.length>1?queryStr:'');//需要正则匹配是否需要再后面添加& or ?

          //改变当前url的值
          this.searchApiUrl=url;
          // console.log("查询条件:",url)
          //查询之前总是第一页
          if(this.paginationData)this.paginationData.number=1;
          this.getTableData(url);

          //20210206 搜索之后关闭
          this.filterModalVisible=false;
        },
        getTableData:function(url){
          if(!url)url=this.apiUrl;
          this.loading=true;

          let paginationQuery ="";
          if(/.*\?+.*/.test(url)){
            paginationQuery+="&"+this.paginationQuery;
          }else{
            paginationQuery+="?"+this.paginationQuery;
          }
          let tUrl=url+paginationQuery;
          //加上顶部过滤条件
          if(this.filterTabsQuery){
            tUrl+="&"+this.filterTabsQuery;
          }
          // console.log("表格查询url:",tUrl);
          this.$http.get(tUrl).then((res)=>{
            if(this.dataList){
              //重新处理数据
              this.$emit("get-table-data-list",res.data.content);
            }else{
              this.tableData=res.data.content;
            }
            this.paginationData={
              totalPages:10,//总页数
              totalElements:res.data.totalElements,//总记录数
              size:res.data.size,//每页显示的行数
              number:res.data.number+1,//当前页
            }
          }).finally(()=>{this.loading=false;})
        },
        handleSelectionChange(val){
          //多选事件
          this.$emit("selection-change",val);

          this.currentSelectRowData = val;

        },
        //单选当前行改变
        handleCurrentChange(nval,oval) {
          //单选事件
          this.$emit("current-change",{newValue:nval,oldValue:oval})
        },
        tableRowClassName({row, rowIndex}){
          //注册事件
          this.$emit("row-class-name",{row, rowIndex})
        },
        //筛选静态框---条件选择
        tableFilterColumnCheckboxChange(val){
          //val是当前选中的数组元素
          this.tableFilterColumn.some((item)=>{
            if(item.label==val){
              item.isShowFilter=!item.isShowFilter;
              return true;
            }
          })
        },
        //分页大小改变
        paginationSizeChange(val){
          if(this.paginationData.size==val)return;
          this.paginationData.size=val;
          this.paginationData.number=1;
          this.getTableData()//重新获取数据
        },
        paginationCurrentChange(val){
          if(this.paginationData.number==val)return;
          this.paginationData.number=val;
          this.getTableData()//重新获取数据
        },
        editSuccessHandle2(data){
          if(this.editSuccessHandle()!==undefined){
            this.editSuccessHandle(data);
          }else{
            this.$message.success("保存成功")
            //id不存在表示新建-操作完成之后需要将数据加入到表格中
            // if (data.isNew){
            //   this.tableData.unshift(data);
            // }
            //v2.刷新表格 分页数据需要重新加载
            this.getTableData();
          }
          return true;
        },
        //清空table数据
        clearTableData(){
            this.loading=true;
            //获取请求地址，不带参数即没有 ?即后面的东西
            let api = this.apiUrl.split("?")[0];
            this.$http.delete(api+"/clean",this.tableData).then((res)=>{
                this.tableData=[];
                this.getTableData();
                this.$message.success("清空数据成功");
            }).finally(()=>{this.loading=false;})
         }
      },
      watch:{
          apiUrl:{
              handler(val){
                  this.filterTabsQuery='';
                  this.paginationData={
                      totalPages:1,
                      totalElements:0,
                      size:10,//每页显示的行数
                      number:1,//当前页
                  };
                  this.getTableData();
              }
          }
      },
      computed:{
        isShowBatchSave(){
          let dataList = this.tableData;
          let data = dataList.filter(d=> d.readonly===false);
          return  data.length>1;
        },
        paginationQuery(){
          let pagination=this.paginationData;
          if(!pagination) return '';
          return "pageIndex="+pagination.number+"&pageSize="+pagination.size
        },
        tableFilterColumnCheckbox(){
          let arr = this.tableFilterColumn;
          let group=[];
          let model=[];
          let selected=[]
          arr.forEach((item)=>{
            if(item.isShowFilter){
              model.push(item.label);
              selected.push(item);
            }
            group.push(item.label);
          })
          return {
            group:group,
            model:model,
            selectedItem:selected
          };
        },
      },
    }
</script>

<style lang="scss">
.tz-editable-table {
  margin: 5px auto;
  width: 100%;
  box-sizing: border-box;
   .el-button+.el-button{
        margin-left: 5px;
   }
  .tabs{
    height: 50px;
    box-sizing: border-box;
    .el-tabs--border-card>.el-tabs__content{
      padding: 0px;
    }
    .el-badge__content.is-fixed{
      top: 10px;
    }
  }
  .table-class {
    .headerStyleClass{
      background-color: #f9f9f9;
      .cell{
        font-weight: bold;
        text-overflow: unset!important;
        white-space:nowrap!important
      }
    }
    .action{
      color:#409eff;
      >span{
        margin-right: 5px;
      }
    }
  }
  .operation {
    height:40px;
    align-items: center;
    display: flex;
    justify-content: space-between;
    button{
      padding: 5px 10px;
      border-radius: 0;
      min-height:unset;
    }
    .left{
       display: flex;
       align-items: center;
      .search{
        margin-right:5px;
        .el-dialog__header{
          padding: 10px 20px;
          display: flex;
          align-items: center;
          justify-content: space-between;
          button{
            padding:0px;
            top: 0;
            right: 0;
            font-size: 24px;
            position:relative;
          }
        }
        .el-dialog__body{
          padding: 5px 20px;
        }
      }
    }
  }
  .pagination{
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .el-pagination{
      padding: 0;
      .btn-prev{
        margin-left: 0;
      }
      .btn-next{
        margin-right: 0;
      }
    }
  }
}

</style>
