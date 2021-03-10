<template>
  <el-tooltip  class="tz-column-edit" :style="item.style"  :disabled="!item.tip" :content="item.tip" :placement="item.tipPlacement?item.tipPlacement:'right'">
    <div>
      <el-input  :disabled="item.disabled" :placeholder="item.placeholder" :style="item.style" clearable :type="item.iType" class="input-text"  v-model="form[item.prop]"  v-if="['TEXT','PASSWORD','TEXTAREA','NUMBER'].indexOf(item.iType.toUpperCase())>-1"></el-input>
      <!--图片类型-->
      <el-upload ref="elUpLoad" v-else-if='item.iType.toUpperCase() == "IMAGE"'
                 :action="item.imageUploadApi || '/service_api/aliyunoss/upload_picture'"
                 :before-upload="beforeImageUpload"
                 :on-success="successImageUpload"
                 :on-error="errorImageUpload"
                 accept="image/*"
                 :limit="1"
                 :headers="{'X-Token':$store&&$store.getters&&$store.getters.userInfo&&$store.getters.userInfo.token}"
                 :show-file-list="false"
                 drag :style="item.style?item.style:'width:80px;height:80px'" v-loading="imageLoading">
        <el-image
                :style="item.style"
                v-if="form[item.prop]" :src="form[item.prop]" class="image pointer"
                :fit="item.fit?item.fit:'fill'"></el-image>
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <!--switch-->
      <el-switch :disabled="item.disabled"  :style="item.style"  v-model="form[item.prop]"  v-else-if="['SWITCH'].indexOf(item.iType.toUpperCase())>-1">
      </el-switch>
      <el-select v-model="form[item.prop]"  :multiple="item.multiple"  filterable clearable placeholder="请选择" v-else-if="['SELECT'].indexOf(item.iType.toUpperCase())>-1">
        <el-option
                v-for="option in item.options"
                :key="option[item.selectKey]"
                :label="option[item.selectLabel]"
                :value="option[item.selectValue]">
        </el-option>
      </el-select>

      <!--富文本类型 VUE3.0版本的后面再弄-->
<!--      <quill-editor-->
<!--              ref="tzQuillEditor"-->
<!--              v-model="form[item.prop]"-->
<!--              :options="editorOption"-->
<!--              @change="onEditorChange($event)"-->
<!--              v-else-if='item.iType.toUpperCase() == "QUILL-EDITOR"'-->
<!--      />-->
    </div>
  </el-tooltip>


</template>

<script>
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import { quillEditor } from 'vue-quill-editor';
    import {ImageDrop}  from 'quill-image-drop-module';
    import {ImageResize} from 'quill-image-resize-module';
    import { setQuillTitle } from "@/utils/vue-quill-title";
    Quill.register('modules/imageDrop', ImageDrop);
    Quill.register('modules/imageResize', ImageResize);

    export default {
        name: "tz-column-edit",
        props:{
          item:{//这个只读
            type:Object,
            required: true
          },
          form:{//这个对象采用引用绑定==》这个组件可以改变父组件的属性值
            type:Object,
            required: true
          },
        },
        components: {
          quillEditor
        },
        data(){
          return {
            editorOption: {
              modules: {
                // 拖拽上传和调整图片大小
                imageDrop: true,
                imageResize: {
                  displayStyles: {
                    backgroundColor: 'black',
                    border: 'none',
                    color: 'white'
                  },
                  modules: [ 'Resize', 'DisplaySize', 'Toolbar' ]
                },
                toolbar: [
                  ['bold', 'italic', 'underline', 'strike'],
                  ['blockquote', 'code-block'],
                  [{ 'header': 1 }, { 'header': 2 }],
                  [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                  [{ 'script': 'sub' }, { 'script': 'super' }],
                  [{ 'indent': '-1' }, { 'indent': '+1' }],
                  [{ 'direction': 'rtl' }],
                  [{ 'size': ['small', false, 'large', 'huge'] }],
                  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                  [{ 'color': [] }, { 'background': [] }],
                  [{ 'font': [] }],
                  [{ 'align': [] }],
                  ['clean'],
                  ['link', 'image', 'video']
                ]
              },
              placeholder: '请输入编辑内容',
              theme: 'snow', //主题风格
            },
            imageLoading:false
          }
        },
        created() {
          //设置富文本title
          setQuillTitle();
        },
        methods:{
          onEditorChange({ quill, html, text }) {
          },
          errorImageUpload(){
            this.imageLoading=false;
            this.$message.error("图片上传失败");
          },
          successImageUpload(response){
            this.imageLoading=false;
            if (response.code!=200){
              this.$message.error("图片上传失败");
            }else{
              this.$refs.elUpLoad.clearFiles(); //上传成功之后清除历史记录,不然再次点击修改会报错
              this.form[this.item.prop]=response.data;
            }
          },
          beforeImageUpload(file){
            this.imageLoading=true;
            const isJPG = /^image\/.*/.test(file.type);
            const isLt2M = file.size/1024/1024 < 1;
            if (!isJPG) {
              this.$message.error('选择的图片格式错误!');
            }
            if (!isLt2M) {
              this.$message.error('图片大小不能超过 1MB!');
            }
            if(!isJPG&&isLt2M)this.imageLoading=false;
            return isJPG&&isLt2M;
          },
        }
    }
</script>

<style lang="scss">
   $height:30px;
  .tz-column-edit{
    .el-textarea__inner{
      padding: 6px;
      height: 100%;

    }
    .el-upload{
      height: 100%;
      width:  100%;
    }
    .el-upload-dragger,.el-upload--picture-card{
      display: flex;
      align-items: center;
      justify-content: center;
      width:100%;
      height:100%;
    }
    //富文本
    .ql-snow .ql-picker{
      height: 24px;
      line-height: 24px;
    }
    .ql-container{
      .ql-editor{
        max-height: 240px;
      }
    }
    //----------------------
    .input-text {
      .el-input__inner {
        height: $height;
        line-height: $height;
        padding: 0 6px;
      }
    }
    .input-date {
      display: flex;
      align-items: center;
      height: $height;

      .el-input__inner {
        height: $height;
        line-height: $height;
      }

      .el-range-separator {
        line-height: 23px;
      }
    }

    .el-input__icon {
      height: $height;
      line-height: $height;
    }
  }
</style>
