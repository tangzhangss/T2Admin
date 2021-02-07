<!--表单组件-->
<!--任何辅助字段，必须要有默认的值，不然监听不到变化-->
<!--
    输入选项参考element表单控件
  iType=输入类型,
    text|password|textarea => input
    select selectItems|selectApi label value => select option
    date datetime
    readonly:只读 showKey:只读时显示的key 默认prop

    rules:表单验证规则

   tip:提示语
   tipPlacement:提示语方向默认顶部

   style:输入框样式，使用场景》》 iSpan会等比例缩放输入框长度，如果想要输入框占一行且宽度短一些，通过该属性设置

   检验：el-form的【props】editColumnRules全局校验-会被每列的rules覆盖，即：如果item.required和rules属性存在，当列的校验不会生效（覆盖）
   rules属性[],如:rules:[{type: 'email',message: '请输入正确的邮箱地址',}],

   isShow:是否显示,这个方法是外部控制只在初始化的时候（外部数据改变的时候）起作用

   isVisibleFunc（from）:该字段是否展示————根据字段名字动态变化 返回true or false

   readonly: 该表单只读状态
-->
<template>
  <div class="tz-form">
      <el-form ref="tzForm" :model="form" :rules="editColumnRules" label-width="auto" :inline="true">
        <el-row :gutter="5" class="row">
          <el-col  v-for="(item,index) in formColumn" :key="item.prop" :span="item.iSpan"  class="col">
            <!--文本输入框-->
            <el-form-item :label="item.label" :rules="item.rules" :prop="item.prop" v-show="item.isShow!==false && (item.isVisibleFunc?item.isVisibleFunc(form):true)">
              <!--仅仅展示-->
              <tz-column-readonly v-if="item.readonly || readonly" :form="form" :item="item"></tz-column-readonly>
              <!--编辑-->
              <tz-column-edit v-else :form="form" :item="item"></tz-column-edit>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
  </div>
</template>

<script>
    import TZUtils from "@/utils/TZUtils";
    import TZColumnEdit from "@/components/TZColumn/edit";
    import TZColumnReadonly from "@/components/TZColumn/readonly";

    export default {
        name: "tz-form",
        props:{
          editColumn:{//表单列
            type:Array,
            default:()=>[]
          },
          editColumnRules:{//表单校验规则
            type:Object,
            default:()=>{return {};}
          },
          //表单编辑页默认的值
          editColumnDefaultValue:{
            type:Object,
            default:()=>{return {};}
          },
          readonly:{//只读--所有列
            type:Boolean,
            default:()=>false
          }
        },
        components: {
          "tz-column-edit":TZColumnEdit,
          "tz-column-readonly":TZColumnReadonly
        },
        data() {
          return {
            form:{},
            formColumn:[],
          }
        },
      created() {

      },
      mounted() {
      },
      methods:{
          requiredRuleHandle(item){//不为空校验规则添加
            let sign="不能为空";
            if(item.rules){
              item.rules.push({required: true, message: item.label+sign,trigger:"blur"})
            }else   item.rules = [{required: true, message: item.label+sign,trigger:"blur"}]
          }
      },
      watch:{
        editColumn:{
          handler(val){
            //初始化formColumn
            this.formColumn=TZUtils.deepClone2Array(val);
            this.formColumn.forEach((item)=>{
              if(item.required){
                //增加不为空校验
                this.requiredRuleHandle(item);
              }
            })
          },
          immediate:true //true就表示会立即执行
        },
        //表单的值初始化
        editColumnDefaultValue:{
          handler(val){
            this.form=TZUtils.deepClone2(val);

            //因为刚开始进来是编辑状态没有加载这个组件
            if(this.$refs["tzForm"]){
              //每次初始值改变都清空校验规则
              this.$refs["tzForm"].clearValidate();
            }
          },
          deep:true,
          immediate:true //true就表示会立即执行
        }
      }
    }
</script>

<style lang="scss">
  $height:30px;

  .tz-form {
    padding-bottom: 20px;
    .row {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
    }
    .col {
      display: flex;
      align-items: center;
      flex-direction: row;
      padding: 8px 10px 8px 0px !important;;
      overflow:visible;
      .el-form-item{
        background-color: #fff;
        width: 100%;
        display: flex;
        align-items: center;
        margin-bottom: 0px;
        .el-form-item__label{
          line-height: inherit;
          white-space: nowrap !important;
          margin-right: 10px;
        }
        .el-form-item__content{
          width: 100%;
        }
      }
    }
  }
</style>
