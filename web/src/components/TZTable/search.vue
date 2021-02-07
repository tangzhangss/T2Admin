<!--
  表格搜索组件
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
              :picker-options="datePickerOptions">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATETIME'"
                             type="datetime"
                             v-model="item.value"
                             class="input-date"
                             format="yyyy-MM-dd HH:mm"
                             placeholder="选择时间"
                             :picker-options="datePickerOptions">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATERANGE'"
                             class="input-date"
                             type="daterange"
                             v-model="item.value"
                             range-separator="至"
                             start-placeholder="开始日期"
                             end-placeholder="结束日期"
                             :picker-options="dateRangePickerOptions">
            </el-date-picker>
            <el-date-picker  v-else-if="item.iType.toUpperCase() == 'DATETIMERANGE'"
                             class="input-date"
                             type="datetimerange"
                             format="yyyy-MM-dd HH:mm"
                             v-model="item.value"
                             range-separator="至"
                             start-placeholder="开始时间"
                             end-placeholder="结束时间"
                             :picker-options="dateRangePickerOptions">
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
                onClick(picker) {
                  picker.$emit('pick', new Date());
                }
              }, {
                text: '昨天',
                onClick(picker) {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24);
                  picker.$emit('pick', date);
                }
              }, {
                text: '一周前',
                onClick(picker) {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                  picker.$emit('pick', date);
                }
              }]
            },
            //日期范围选择快捷方式
            dateRangePickerOptions: {
              shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                  picker.$emit('pick', [start, end]);
                }
              }, {
                text: '最近一个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                  picker.$emit('pick', [start, end]);
                }
              }, {
                text: '最近三个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                  picker.$emit('pick', [start, end]);
                }
              }]
            },
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
      .el-input__icon{
        height:$height;
        line-height: $height;
      }
    }
  }

</style>
