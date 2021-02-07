<template>
  <div style="padding:5px 40px">
    <p style="font-size: 18px">欢迎使用 TZCC-REN ADMIN SYSTEM（v2.0.0版本）</p>
    <el-divider class="divider-title" content-position="left">常见问题</el-divider>
    <ul>
      <li>待完善</li>
    </ul>
    <el-divider class="divider-title" content-position="left">更新日志</el-divider>
    <ul>
      <li class="bold">v2.0.0(2020-01-17)</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Home',
  computed: {
  }
}
</script>

<style lang="scss" scoped>
.divider-title{
  .el-divider__text{
    font-size: 16px;
    color: gray;
  }
}
ul{
 font-size: 14px;
}
</style>
