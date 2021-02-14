<template>
  <div class="tz-column-readonly">
    <div v-if='item.iType.toUpperCase() == "IMAGE"'>
      <el-image
        :style="item.style"
        v-if="form[item.prop]" :src="form[item.prop]" class="image"
        :fit="item.fit?item.fit:'fill'"></el-image>
    </div>
    <el-select class="select-show-text" v-model="form[item.prop]" disabled  :multiple="item.multiple"  placeholder="请选择" v-else-if="['SELECT'].indexOf(item.iType.toUpperCase())>-1">
      <el-option
        v-for="option in item.options"
        :key="option[item.selectKey]"
        :label="option[item.selectLabel]"
        :value="option[item.selectValue]">
      </el-option>
    </el-select>
    <el-switch disabled  v-model="form[item.prop]"  v-else-if="['SWITCH'].indexOf(item.iType.toUpperCase())>-1"></el-switch>
    <div class="select-show-quill-editor" v-else-if='item.iType.toUpperCase() == "QUILL-EDITOR"' v-html="form[item.prop]"></div>

    <div :style="item.style" class="select-show-textarea" v-else-if='item.iType.toUpperCase() == "TEXTAREA"'  style="white-space: pre-wrap;">
      {{form[item.showKey?item.showKey:item.prop]}}
    </div>
    <div v-else class="input-show-text">
      {{form[item.showKey?item.showKey:item.prop]}}
    </div>
  </div>
</template>

<script>

    export default {
      name: "tz-column-readonly",
      props:{
        item:{//这个只读
          type:Object,
          required: true
        },
        form:{//这个对象采用引用绑定==》这个组件可以改变父组件的属性值
          type:Object,
          required: true
        }
      },
    }
</script>

<style lang="scss">
  $tz_input_border_color:#DCDFE6;
  $height:40px;
  .tz-column-readonly{
    .input-show-text{
      border-bottom: 1px solid $tz_input_border_color;
      min-height: $height;
      line-height: $height;
    }
    .select-show-text{
      .el-input.is-disabled .el-input__inner{
        background-color: unset;
      }
    }
    .image{
      border: 1px dashed #777;
    }
    .select-show-quill-editor,.select-show-textarea{
      border: 1px solid $tz_input_border_color;
      padding: 5px;
      max-height: 320px;
      line-height: initial;
      overflow: auto;
      *{
        padding: 0px;
        margin: 0px;
      }
    }
  }
</style>
