<!--
  表格搜索组件

  disabledDate:日期时间类型是否禁止选择未来的日期
-->
<template>
  <div class="tz-search">
        <el-row :gutter="5" class="row">
          <el-col  v-for="(item,index) in obj" :key="item.id" :span="item.iSpan"  class="col">
            <span class="label">{{item.label}}</span>
            <el-input clearable class="input-text"  v-model="item.value"  v-if="!item.iType || item.iType.toUpperCase() == 'TEXT'"></el-input>
            <el-switch v-else-if="item.iType.toUpperCase() == 'SWITCH'"
              v-model="item.value">
            </el-switch>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATE'"
              type="date"
              class="input-date"
              placeholder="选择日期"
              v-model="item.value"
              :disabled-date="item.disabledDate&&datePickerOptions.disabledDate"
              :shortcuts="datePickerOptions.shortcuts">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATETIME'"
                             type="datetime"
                             v-model="item.value"
                             class="input-date"
                             format="YYYY-MM-DD HH:mm"
                             placeholder="选择日期时间"
                             :disabled-date="item.disabledDate&&datePickerOptions.disabledDate"
                             :shortcuts="datePickerOptions.shortcuts">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATERANGE'"
                             class="input-date"
                             type="daterange"
                             v-model="item.value"
                             range-separator="至"
                             start-placeholder="开始日期"
                             end-placeholder="结束日期"
                             unlink-panels
                             :shortcuts="shortcuts">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATETIMERANGE'"
                             class="input-date"
                             type="datetimerange"
                             format="YYYY-MM-DD HH:mm"
                             v-model="item.value"
                             range-separator="至"
                             unlink-panels
                             start-placeholder="开始时间"
                             end-placeholder="结束时间"
                             :shortcuts="shortcuts">
            </el-date-picker>
          </el-col>
        </el-row>

      <div  style="text-align:right;margin: 20px 0">
        <el-button type="primary" @click="clear">{{clearName}}</el-button>
        <el-button type="primary" @click="submit">{{submitName}}</el-button>
      </div>
  </div>
</template>

<script>
   import TZUtils from "@/utils/TZUtils"
   export default {
        name: "tz-search",
        props:{
            columnList:{
              type: Array,
              default:()=>[]
            },
            submitName:{//提交按钮的名字
              type:String,
              default:()=>"搜索"
            },
            clearName:{//提交按钮的名字
            type:String,
            default:()=>"清除"
          },
        },
        data() {
          return {
            //日期选择快捷方式
            datePickerOptions: {
              disabledDate(time) {
                return time.getTime() > Date.now();
              },
              shortcuts: [{
                text: '今天',
                value:new Date()
              }, {
                text: '昨天',
                value:(()=> {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24);
                  return date;
                })()
              }, {
                text: '一周前',
                value:(()=> {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                  return date;
                })()
              }]
            },
            //范围选择快捷方式
            shortcuts: [{
              text: '最近一周',
              value:(()=> {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                return [start, end];
              })()
            }, {
              text: '最近一个月',
              value:(()=> {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                return [start, end];
              })()
            }, {
              text: '最近三个月',
              value:(()=> {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                return [start, end];
              })()
            }],
            obj:[]//存储对象
          }
        },
        created(){
          this.obj=TZUtils.deepClone(this.columnList);
        },
        computed:{
        },
        watch:{
          columnList: {
            handler(newVal, oldVal) {
              //每次添加或则新增条件之后-输入被清空
              this.obj = TZUtils.deepClone(newVal);
            },
          }
        },
        methods:{
          clear(){
            this.$emit("search-clear-method")
          },
          submit(){
            this.$emit("search-method",this.obj);
          }
        }
    }
</script>

<style  lang="scss">
  $height:30px;
  .tz-search{
    .row{
      display: flex;
      align-items: center;
      flex-wrap: wrap;
    }
    .col{
      display: flex;
      align-items: center;
      flex-direction: row;
      padding:8px 10px 8px 0px !important;;
      .label{
        white-space:nowrap!important;
        margin-right:10px;
      }
      .input-text{
        .el-input__inner{
          height:$height;
          line-height: $height;
          padding:0 6px;
        }
      }
      .input-date{
        display: flex;
        align-items: center;
        height:$height;
        .el-input__inner{
          height:$height;
          line-height: $height;
        }
        .el-range-separator{
          line-height: 23px;
        }
      }
   /*   .el-input__icon,.el-input__prefix,.el-input__suffix{
        height:$height;
        line-height: $height;
      }*/
    }
  }

</style>
