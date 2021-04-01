<template>
  <div class="tz-hamburger">
    <svg
      :class="{'is-active':isActive}"
      class="hamburger pointer"
      viewBox="0 0 1024 1024"
      xmlns="http://www.w3.org/2000/svg"
      width="64"
      height="64"
      @click="toggleClick"
    >
      <path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM142.4 642.1L298.7 519a8.84 8.84 0 0 0 0-13.9L142.4 381.9c-5.8-4.6-14.4-.5-14.4 6.9v246.3a8.9 8.9 0 0 0 14.4 7z" />
    </svg>
    <i class="el-icon-refresh hamburger-refresh pointer" title="刷新" @click="refreshCurrentPage"></i>
  </div>
</template>

<script>
  export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    toggleClick() {
      this.$emit('toggleClick')
    },
    refreshCurrentPage(){
      //刷新当前路由组件
      window.APP_MAIN_PAGE.reload();
      //下面这种不好用，每次刷新之后页面会出现空白然后从新点击进入才会刷新出来
      //有空再研究
      // this.$router.replace("/refresh")
    }
  }
}
</script>

<style scoped lang="scss">
.tz-hamburger{
  width:60px;display:flex;align-items: center;justify-content:flex-end;margin-right:14px;
  .hamburger {
    display: inline-block;
    vertical-align: middle;
    width: 20px;
    height: 20px;
  }
  .hamburger-refresh{
    width: 20px;
    height: 20px;
    margin-left: 5px;
    font-size: 20px;
  }

  .hamburger.is-active {
    transform: rotate(180deg);
  }
}

</style>
