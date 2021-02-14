<template>
  <div  class="tz-form-dialog">
    <el-dialog top="50px" :append-to-body='true' :width="width" v-model="showValue" :title="title" :before-close="beforeClose">
      <tz-form  v-loading="dialogLoading"  ref="tzForm"  :readonly="readonly" :edit-column="editColumn" :edit-column-rules="editColumnRules" :edit-column-default-value="editColumnDefaultValue"></tz-form>
      <div style="display: flex;justify-content: flex-end">
        <el-button class="tz-btn" type="primary" @click="submit" v-show="isShowSubmitBtn">{{submitName}}</el-button>
        <template  v-for="item in otherAction">
          <el-button v-show="item.isShow" class="tz-btn" :type="item.type||'primary'" @click="item.onClick($refs['tzForm'].form)">{{item.title}}</el-button>
        </template>
      </div>
    </el-dialog>
  </div>
</template>
<script>
    import TZForm from "@/components/TZForm/index"
    export default {
      name: "tz-form-dialog",
      components:{
        "tz-form":TZForm
      },
      props:{
        width:{
          type:String,
          default:()=>"60%"
        },
        show:[Boolean],//是否显示
        editColumnRules:{
          type:Object,
          default:()=>{}
        },
        title:{
          type:String,
          default:()=>"编辑"
        },
        submitName:{
          type:String,
          default:()=>"保存"
        },
        isShowSubmitBtn:{
          type:Boolean,
          default:()=>true
        },
        submitApi:{
          type:String,
          default:()=>""
        },
        //自定义提交操作--必须要有返回值且为true
        submitFunc:{
          type:Function,
          default:()=> {
            return false
          }
        },
        //api提交方式可用，提交之前-返回false将不会执行
        submitBeforeFunc:{
          type:Function,
          default:()=> {
            return true
          }
        },
        //api提交方式可用，提交成功之后
        submitAfterFunc:{
          type:Function,
          default:()=> {
            return undefined
          }
        },
        editColumn:{
          type:Array,
          default:()=>[]
        },
        //表单编辑页默认的值
        editColumnDefaultValue:{
          type:Object,
          default:()=> {return {}}
        },
        //其他的操作
        otherAction:{
          type:Array,
          default:()=>[]
        },
        readonly:{//只读--所有列
          type:Boolean,
          default:()=>false
        }
      },
      data(){
        return {
          dialogLoading:false
        }
      },
      created() {},
      methods:{
        submitHandle(){
          //优先使用自定义的处理时间
          let form = this.$refs["tzForm"].form;
          if(!this.submitFunc()){
            //使用api方式保存
            if(this.submitBeforeFunc(form)!==false){
              if(!this.submitApi){
                this.$message.error("请求地址错误，请检查");
                return false;
              }
              this.dialogLoading=true;
              this.$http.post(this.submitApi,this.$refs["tzForm"].form).then((res) => {
                this.dialogLoading=false;//关闭加载
                this.closeDialog();//关闭dialog
                //可能返回的是数组类型和对象类型---不允许返回其他类型
                let data= res.data || {};
                if(!form.id)data.isNew=true;//表示是新增
                if(this.submitAfterFunc(data)===undefined){//没有定义结束方法默认（定义之后不能返回undefined）
                  this.$message.success("保存成功");
                }
                // this.$refs.tzForm.form={};//清空表单值
              }).catch(()=>{
                this.dialogLoading=false;
              })
            }
          }
        },
        submit(){
          this.$refs["tzForm"].$refs["tzForm"].validate((valid)=>{
            // console.log(this.$refs.tzForm.form);
            if(valid)this.submitHandle();
          })
        },
        closeDialog(){
          this.showValue=false;
        },
        beforeClose(done){
          //done(false);取消子组件关闭，由父组件关闭__!!!__elementplus不调用则不关闭
          this.closeDialog();
        }
      },
      computed:{
        showValue:{
          get(){
            return this.show;
          },
          set(v){
            this.$emit('update:show',v);
          }
        }
      }
    }
</script>

<style lang="scss">
  .tz-form-dialog{
    .el-dialog__body{
      padding-top: 0 !important;
    }
  }

</style>
