<template>
  <div style="padding:5px 40px">
    <p style="font-size: 18px">欢迎使用 TZCC-REN ADMIN SYSTEM（v2.x版本）</p>
<!--    <el-divider class="divider-title" content-position="left">常见问题</el-divider>-->
<!--    <ul>-->
<!--      <li>待完善</li>-->
<!--    </ul>-->
    <el-divider class="divider-title" content-position="left">更新日志</el-divider>
    <ul class="update-log">
      <li>
        <p class="bold">v2.0.3(2021-11-20)</p>
        <div class="text">
          <p>vue升级到3.2.0</p>
          <p>element-plus升级到1.2.0-beta.3</p>
          <p>优化菜单配置，支持多级菜单（含外部网页）</p>
          <p>新增自动换肤功能</p>
          <p>表格支持排序，排序的优先级:第一次进行排序的字段具有最高优先级（不管升序和降序和多次改变都一样，如需改变优先级请先清空排序）</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.2(2021-04-01)</p>
        <div class="text">
          <p>顶部功能优化</p>
          <p>优化组件样式，新增卡片式路由导航</p>
          <p>引入tinymce富文本编辑器</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.1(2021-03-05)</p>
        <div class="text">
          <p>前端技术框架升级vue3.0</p>
        </div>
      </li>
      <li>
        <p class="bold">v2.0.0(2021-01-17)</p>
        <div class="text">
          <p>Init</p>
        </div>
      </li>
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
.update-log{
  font-size: 14px;
  .text{
    padding: 0 5px;
    color: gray;
  }
}
</style>
