<template>
  <div>
    <tz-table  ref="tzTable"
               :api-url="api" :action="['delete','edit']"
               :table-column="tableColumn" showIndex
               :edit-column="editColumn"
               :action-others='[
                 {title:"预览",icon:"el-icon-view",onClick:openDataModal},
               ]'
    >
    </tz-table>


    <div class="data-album-el-dialog">
      <el-drawer
              :title="album.title"
              v-model="isDrawerShow"
              direction="rtl"
              size="50%"
      >
        <tz-editable-table  ref="tzEditableTable"
                   selection
                   :api-url="picApi" :action="['delete','edit','save','cancel']"
                   :table-column="picEditColumn"
                   :other-height="50"
                   :edit-column-default-value="{'albumId':album.id}"
                   :operation-others="[
                           {title:'预览',onClick:()=>this.previewPic()},
                   ]"
                   :beforeDeleteData="beforeDeleteDataAlbum"
                   paginationLayout="total"
                   v-on:selection-change="selectChange"
        >
        </tz-editable-table>


        <div>
          <el-image-viewer v-show="showViewer" :url-list="previewPicArr" @close="()=>this.showViewer=false"></el-image-viewer>
        </div>
      </el-drawer>
    </div>
  </div>
</template>

<script>

  import {ElImageViewer} from 'element-plus'
  import TZUtils from "../../utils/TZUtils";

  export default {
    name: 'Album',
    components: {
      "el-image-viewer":ElImageViewer
    },
    data:function(){
      return {
        api:"/data_api/album",
        picApi:"",
        isDrawerShow:false,
        album:{},//当前选中的album
        previewPicArr:[],//预览图片
        showViewer:false,
        isBatch:false
      }
    },
    created(){},
    computed: {
      tableColumn(){
        return [
          {prop:"title",label:"标题",filterable:true},
        ]
      },
      editColumn(){
        return [
          {prop:"title", label:"标题", iType:"text",required:true,iSpan:24},
        ];
      },
      picEditColumn(){
        return [
          this.$service_tool.setImageUploadHeaders({
            prop:"url", label:"图片",imageUploadApi:"/service_api/aliyunoss/upload_picture/deletable"
            ,iType:"image",
            width:"100px",height:"50px",style:"width:40px;height:40px",
            required: true,
          }),
        ];
      },
    },
    methods:{

      selectChange(rows){
        if(rows.length>1){
           this.isBatch=true;
        }else{
          this.isBatch=false;
        }
      },
      //设置预览图片
      previewPic(){
        let data = this.$refs.tzEditableTable.tableData;
        if(data.length==0){
          this.$message.warning("无可预览的图片");
          return false;
        }
        let imageArr = [];
        data.forEach(item=>imageArr.push(item.url))
        this.previewPicArr=imageArr;
        this.showViewer=true;
      },
      openDataModal(row){
        this.isDrawerShow=true;
        this.album=row;
        this.picApi="/data_api/album_pic?albumId@EQ="+row.id;
      },
      beforeDeleteDataAlbum(dataArr){
        return new Promise((resolve, reject)=>{
          let urls=[];
          dataArr.forEach(data=>{
            if(data.url){
              urls.push(data.url);
            }
          })
          let loading = TZUtils.fullLoading(this,"正在删除云端图片")
          //删除数据之前
          this.$http.delete("/service_api/aliyunoss/delete_by_urls",urls)
                  .then(()=>resolve(true)).catch(()=>resolve(false)).finally(()=>{            loading.close();
          })
        })
      }
    }
  }
</script>

<style lang="scss">
  .data-album-el-dialog{
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
