<template>
  <div class="tz-table">
    <!--顶部标签页-->
    <div class="tabs" v-if="filterTabs.length>0">
      <el-tabs type="border-card" @tab-click="filterTabsClick">
          <el-tab-pane v-for="item in filterTabs">
             <template #label>
              <el-badge :value="item.badge" class="item" :hidden="item.badge==0||item.badge==''||item.badge==undefined||item.badge==null">
                <span>{{item.label}}</span>
              </el-badge>
            </template>
          </el-tab-pane>
      </el-tabs>
    </div>
    <!--顶部操作-->
    <div class="operation">
      <div class="left">
        <el-tabs type="border-card" v-if="tabs.length==3">
          <el-tab-pane :label="tabs[0]">{{tabs[0]}}</el-tab-pane>
          <el-tab-pane :label="tabs[1]">{{tabs[1]}}</el-tab-pane>
          <el-tab-pane :label="tabs[2]">{{tabs[2]}}</el-tab-pane>
        </el-tabs>
        <div class="search" v-if="tableFilterColumn.length>0">
          <el-badge value="✔" :hidden="searchApiUrl==apiUrl" class="item">
            <el-button type="primary" plain @click="filterModalVisible=true">筛选</el-button>
          </el-badge>
          <div   v-dialogDrag>
            <el-dialog title="筛选" v-model="filterModalVisible">
              <div class="search-modal">
                <label>选择<span style="font-weight: 200;font-size:12px;color: gray">(每次选择条件之后，输入都会被清空，请在一开始就选择好需要的条件)</span></label>
                <el-checkbox-group v-model="tableFilterColumnCheckbox.model" class="tz-flex-row">
                  <el-checkbox v-for="(item,index) in tableFilterColumnCheckbox.group" :label="item" @change="tableFilterColumnCheckboxChange(item)" ></el-checkbox>
                </el-checkbox-group>
                <label v-if="tableFilterColumnCheckbox.model.length>0">输入</label>
                <tz-search :column-list="tableFilterColumnCheckbox.selectedItem" @search-method="search" @search-clear-method="searchClear"></tz-search>
              </div>
            </el-dialog>
          </div>
        </div>
        <el-button v-if="operation.includes('create')" type="primary" plain @click="editData()">新建</el-button>

        <template v-for="item in operationOthers">
          <el-button v-if="item.isShow!==false" :type="item.type||'primary'" plain @click="item.onClick()">{{item.title}}</el-button>
        </template>
      </div>
      <div class="right">
        <!--清空-->
        <el-popconfirm
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-warning"
          icon-color="red"
          title="您正在删除（清空）当前所有的数据？"
          v-if="clear.show"
          @confirm="clearTableData"
        >
            <template #reference>
                <el-button type="info" plain>清空</el-button>
            </template>
        </el-popconfirm>
        <el-popover
          placement="bottom"
          trigger="click">
            <el-checkbox-group v-model="customShowColumnCheckbox.model" style="display: flex;flex-direction: column">
              <el-checkbox v-for="(item,index) in customShowColumnCheckbox.group" :label="item" @change="customShowColumnCheckboxChange(item)" ></el-checkbox>
            </el-checkbox-group>
          <template #reference>
            <el-button type="info" plain >自定义展示列</el-button>
          </template>
        </el-popover>
      </div>
    </div>
    <el-table
      ref="elTable"
      :header-cell-class-name="headerStyleClass"
      class="table-class"
      v-loading="loading"
      :data="tableData"
      :style="cStyle" :width="width"  :height="height"
      :row-class-name="tableRowClassName"
      :max-height="maxHeightValue"
      :highlight-current-row="highlightCurrentRow"
      @current-change="handleCurrentChange"
      @selection-change="handleSelectionChange"
      @sort-change="handleSortChange"
      :tree-props="treeProps"
      :row-key="rowKey"
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
                       fixed="left" :index="indexHandle">
      </el-table-column>

      <!--cType是数据类型-->
      <template v-for="(item,index) in tableColumnData">
          <template  v-if="!item.hide" >
            <!--文本格式-->
            <el-table-column v-if="!item.cType || item.cType.toUpperCase()=='TEXT'"
                             :key="item.id"
                             :prop="item.prop"
                             :label="item.label"
                             :min-width="item.width||0"
                             :fixed="item.fixed"
                             :data-type="item.dataType"
                             :formatter="item.formatter"
                             :render-header="renderHeader"
            ></el-table-column>
            <!--日期时间-->
            <!--自定义列的width属性必须不能为undefined-->
            <el-table-column v-else-if="item.cType.toUpperCase()=='DATE' || item.cType.toUpperCase()=='DATETIME'"
                             :key="item.id"
                             :prop="item.prop"
                             :label="item.label"
                             :min-width="item.width||0"
                             :fixed="item.fixed"
                             :style="item.cStyle"
            >
              <template #default="scope">
                <span v-if="item.dateFormat">{{TZUtils.formatDate(scope.row[item.prop],item.dateFormat)}}</span>
                <span v-else-if="item.cType.toUpperCase()=='DATE'">{{TZUtils.formatDate(scope.row[item.prop],'yyyy-MM-dd')}}</span>
                <span v-else-if="item.cType.toUpperCase()=='DATETIME'">{{TZUtils.formatDate(scope.row[item.prop],'yyyy-MM-dd HH:mm')}}</span>
              </template>
            </el-table-column>
            <!--图片-->
            <el-table-column v-else-if="item.cType.toUpperCase()=='IMAGE'"
                             :key="item.id"
                             :prop="item.prop"
                             :label="item.label"
                             :width="item.width"
                             :fixed="item.fixed"
                             :style="item.cStyle"
            >
              <template #default="scope">

                <el-image  :preview-src-list="new Array(scope.row[item.prop])" :style="'cursor:pointer;'+item.cStyle" :src="scope.row[item.prop]" :fit="scope.row[item.fit] || 'contain'+
  'cover' " >
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </template>
            </el-table-column>
            <!--开关-->
            <el-table-column v-else-if="item.cType.toUpperCase()=='SWITCH'"
                             :key="item.id"
                             :prop="item.prop"
                             :label="item.label"
                             :min-width="item.width||0"
                             :fixed="item.fixed"
                             :style="item.cStyle"
            >
              <template #default="scope">
                <el-switch
                        v-model="scope.row[item.prop]"
                        disabled>
                </el-switch>
              </template>
            </el-table-column>
           </template>
      </template>
      <!--操作-->
      <el-table-column v-if="action.length>0 || actionOthers.length>0" label="操作" fixed="right">
        <template #default="scope">
          <div class="action">
                  <span class="edit" v-if="action.includes('edit')"  title="查看|修改" @click="editData(scope.row,scope.$index)">
                    <i class="el-icon-edit-outline pointer"/>
                  </span>
            <span class="delete" v-if="action.includes('delete')"  title="删除这条记录">
                       <el-popconfirm
                               title="您正在删除这条记录？"
                               @confirm="deleteData(scope.row,scope.$index)"
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

    <!--编辑框-->
    <div class="edit-dialog">
      <tz-form-dialog :submit-api="apiUrl" :submit-before-func="editSubmitBeforeFunc" :edit-column-rules="editColumnRules"
                      :submit-after-func="editSuccessHandle2" v-model:show="showEditDialog" :title="editTitle"
                      :edit-column="editColumn" :edit-column-default-value="editObjectData"
                      :readonly="tzFormReadonly"
                      :is-show-submit-btn="!tzFormReadonly"
                      :other-action="[
                        {type:'primary',title:'修改',isShow:tzFormReadonly&&!tzFormIsNewCreate,onClick:()=>{this.tzFormReadonly=false}},
                        {type:'',title:'取消',isShow:!tzFormReadonly&&!tzFormIsNewCreate,onClick:()=>{this.tzFormReadonly=true}}
                      ]"
      ></tz-form-dialog>
    </div>
  </div>

</template>

<script>
  import "../dialogDrag";
  import TZSearch from "./search"
  import TZFormDialog from "@/components/TZForm/dialog";
  import TZUtils from "../../utils/TZUtils";
  import {nextTick} from "vue";

  export default {
      name: 'tz-table',
      components:{
        'tz-search':TZSearch,
        "tz-form-dialog":TZFormDialog
      },
      props: {
          //是否选择高亮（单选+currentChange使用）
          highlightCurrentRow:{
              type:Boolean,
              default:false
          },
        editTitle:{
          type:String,
          default:()=>"编辑"
        },
        tabs:{
          type:Array,
          default:()=>[]
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
        editColumn:{
          type:Array,
          default: ()=> []
        },//表的编辑对象
        width:[String],//表格的列宽
        stripe:{
          type:Boolean,
          default:()=>false
        },//是否有斑马线
        border:{
          type:Boolean,
          default:()=>true
        },//带边框
        height:[Number],//固定表头
        maxHeight:[Number],//最大高度
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
        //编辑---全局表单校验规则
        editColumnRules:{
          type:Object,
          default:()=>{}
        },
        //表单编辑页默认的值
        editColumnDefaultValue:{
          type:Object,
          default:()=>null
        },
        editSubmitBeforeFunc:{
          type:Function,
          default:()=>true
        },
        //成功之后的回调函数--如果想要生效不能返回null
        editSuccessHandle:{
          type:Function,
          default:()=>undefined
         },
        //顶部左侧的操作按钮
        operation:{
          type:Array,
          default:()=>['create']
        },
        operationOthers:{
          type:Array,
          default:()=>[]
        },
        //操作选项如：edit,delete
        action:{
          type:Array,
          default:()=>["edit","delete"]
        },
        actionOthers:{
          type:Array,
          default:()=>[]
        },
        filterTabs:{
          type:Array,
          default:()=>[]
        },
        clear:{//是否展示清空按钮
            type:Object,
            default:{
                show:false,
                param:{}//清空的条件格式去后端get请求的map
            }
        },
        beforeDeleteData:{
            type:Function,
            default:async(data)=>true
        },
        saveForceFlush:{//保存之后强制刷新数据,默认只有新建的时候才会刷新
            type:Boolean,
            default:false,
        },
        //树型结构的row-key
        rowKey:{
          type:String,
          default:undefined,
        },
        treeProps:{
            type:Object,
            default:{children: 'children', hasChildren: 'hasChildren'},
        }
      },
      data(){
        return {
          loading:false,
          tableData:[],
          tableFilterColumn:[],//表格过滤条件
          selectedTableFilterColumn:[],//被选中的筛选条件
          tableColumnData:[],
          editObjectData:{},//新建-编辑对象
          filterModalVisible:false,//筛选静态框
          searchApiUrl:this.apiUrl,//当前的搜索的url-查询条件之后的，比如用于翻页的时候带上
          paginationData:null,
          maxHeightAuto:undefined,//自动计算的表格最大高度
          showEditDialog:false,
          filterTabsQuery:'',
          tzFormReadonly:false,//表单是否只读
          tzFormIsNewCreate:true,//表单是否是新建
          tzFormIsIndex:true,//当前编辑选择的index(编辑回写不刷新表格)
        }
      },
      created(){
          this.initPaginationData();//初始化分页数据
          this.init();//数据初始化
      },
      mounted(){
        //没有设定最大高度就进行自适应操作
        if(!this.maxHeight){
          this.adjustTableHeight();//调整表格动态高度
        }
      },
      methods: {
        sortMethod(a,b){
          console.log(a,b)
        },
        //顶部tabs筛选
        filterTabsClick(data){
          let item = this.filterTabs[data.index];
          let query = item.value!=undefined?item.key+"@EQ="+item.value:'';
          if(this.filterTabsQuery != query){
              this.filterTabsQuery=query;
              if(item.onClick)item.onClick();
              this.getTableData();
          }
        },
        openEdit(data,isNew){
            this.editData(data);
            this.tzFormReadonly=false;//表单是否只读
            this.tzFormIsNewCreate=isNew;//表单是否是新建
        },
        //新建更新数据
        editData(data,index){
          //这是一个异步的func_可能会修改页面渲染，等页面修改完成再执行以下操作
          this.$emit("edit-data-handle",data);

          this.editObjectData = data || TZUtils.deepClone(this.editColumnDefaultValue) ||{};
          //编辑状态点开时只读
          this.tzFormReadonly=data?true:false;
          //是否新建
          this.tzFormIsNewCreate=data?false:true;
          //当前编辑索引
          this.tzFormIsIndex=index;
          this.showEditDialog=true;
        },
        deleteData(data,index){
          this.loading=true;
          this.beforeDeleteData(data).then((res)=>{
              if(res!==false) {
                  //删除操作
                  this.$http.delete(this.apiUrl,[data]).then((res)=>{
                      this.tableData.splice(index,1);//随便删除一个，避免抖动
                      this.getTableData();
                      this.$message.success("删除成功");
                      //删除数据之后
                      this.$emit("after-delete-data-handle",data);
                  }).finally(()=>{this.loading=false;})
              }else{
                  this.loading=false;
              }
          })
        },
        init(){
          //2.将tableColumn的最后一个放在第一个（自定义组件时 el-table标签不接column的bug__第一个永远是最后一个）
          // let endElement=this.tableColumn.pop();
          // this.tableColumn.unshift(endElement);
          //column紧跟table标签没有这个问题---------
          /*
            1.初始化tableData
           */
          //dataList为主
          if (this.dataList){
            this.tableData = this.dataList;
          }else if(this.apiUrl){
              /*
                如果存在过滤条件就默认带上第一个
                -- 20210227 by tangzhangss
              */
              let item = this.filterTabs.length>0?this.filterTabs[0]:undefined;
              if(item && item.value!=undefined){
                  this.filterTabsQuery = item.key+"@EQ="+item.value;
              }
              //---------------------------------
              //如果没有传入数据且有apiUrl就自己请求
              this.getTableData();
          }
          //复制tableCloumn并获取需要的信息
          /*
          由于需要计算属性监控hide的值，所以必须有一个值（true|false）,
          没有值得计算属性不会重新计算。原因暂时不知(可能是最开始没有值得监听不到)
           */
          let columnData = TZUtils.deepClone(this.tableColumn);
          //过滤列
          let filterColumn=[];
          columnData.forEach((item,index)=>{
            if(!item.hide)item.hide=false;
            if(item.filterable){
              //拷贝一份不然操作的是同一个对象-会出现一系列问题
              let filterItem = TZUtils.deepClone(item);
              if(!filterItem.isShowFilter)filterItem.isShowFilter=false;//默认都不进入选中状态
              if(filterItem.cType && !filterItem.iType)filterItem.iType=filterItem.cType;//筛选类型默认就是cType
              filterColumn.push(filterItem);
            }
          })
          //表格展示列
          this.tableColumnData=columnData;
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
        //调整表格的高度
        adjustTableHeight(){
          let func = ()=>{
            let clientHeight= document.documentElement.clientHeight;
            let filterTabsHeight=this.filterTabs.length>0?50:0;//顶部tabs50
            this.maxHeightAuto =clientHeight-160-2-filterTabsHeight; //操作40 分页50 顶部50 table margin上下10 2容错
          };
          func();//先执行一次
          window.onresize = func;
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
          if(this.paginationQuery){
             if(/.*\?+.*/.test(url)){
                paginationQuery+="&"+this.paginationQuery;
             }else{
                paginationQuery+="?"+this.paginationQuery;
             }
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
        indexHandle:function(index){
          if(this.indexMethod){
            //排序事件
            return this.indexMethod(index);
          }else{
            return index+1
          }
        },
        handleSortChange(columnName,order){
          //排序事件
          this.$emit("sort-change",columnName,order)
        },
        handleSelectionChange(val){
          //多选事件
          this.$emit("select-change",{val})
        },
        //单选当前行改变
        handleCurrentChange(val) {
          //单选事件
          this.$emit("current-change",{val})
        },
        tableRowClassName({row, rowIndex}){
          //注册事件
          this.$emit("row-class-name",{row, rowIndex})
        },
        customShowColumnCheckboxChange(val){
          //val是当前选中的数组元素
          this.tableColumnData.some((item)=>{
            if(item.label==val){
              item.hide=!item.hide;
              return true;
            }
          })
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
            // console.log("是否强制刷新条件:",this.tzFormIsIndex,this.saveForceFlush);
            if(this.tzFormIsIndex!==undefined && !this.saveForceFlush){
                this.tableData[this.tzFormIsIndex]=data;
            }else{
                //刷新数据
                this.getTableData();
            }
          }
          return true;
        },
        //清空table数据
        clearTableData(){
            this.loading=true;
            //获取请求地址，不带参数即没有 ?即后面的东西
            let api = this.apiUrl.split("?")[0];
            this.$http.delete(api+"/clean",this.clear.param).then((res)=>{
                this.tableData=[];
                this.getTableData();
                this.$message.success("清空数据成功");
            }).finally(()=>{this.loading=false;})
        }
       },
      computed:{
        maxHeightValue(){
          if(this.maxHeight)return this.maxHeight;
          return this.maxHeightAuto;
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
        customShowColumnCheckbox(){
            let arr = this.tableColumnData;
            let group=[];
            let model=[];
            arr.forEach((item)=>{
              if(!item.hide)model.push(item.label);
              group.push(item.label);
            })
          return {
              group:group,
              model:model
          };
        }
      },
      watch:{
          //表格数据改变
          dataList(newVal){
            this.tableData=newVal;
          }
      }
    }
</script>

<style lang="scss">
.tz-table {
  margin: 5px auto;
  width: 95%;
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
