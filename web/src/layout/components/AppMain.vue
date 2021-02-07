<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header+.app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
//当页面有滚动条且弹出el-dialog的时候右侧会被覆盖15px
//下面这种方式会使得没有滚动条的时候弹出el-dialog页面右边空出15px-这里不使用
.el-popup-parent--hidden {
  .fixed-header {
    /*padding-right: 15px;*/
  }
}
</style>
