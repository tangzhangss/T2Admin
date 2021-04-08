<template>
    <section class="app-main">
        <router-view v-if="isRouteAlive" v-slot="{ Component }">
            <transition  name="fade-transform" mode="out-in">
                <keep-alive :include="keepAlive">
                    <component :is="Component" />
                </keep-alive>
            </transition>
        </router-view>
    </section>
</template>

<script>
import {nextTick } from 'vue'

export default {
  name: 'AppMain',
  data(){
    return {
        keepAlive:[],
        isRouteAlive:true
    }
  },
  created() {
      window.APP_MAIN_PAGE=this;
  },
  watch: {
    $route() {
        let routes = this.$store.getters.routeTabs;
        let arr = [];
        routes.forEach(r=>{
            if(!r.noCache)arr.push(r.name);
        });
        arr.push("Home");//Home页面需要缓存
        this.keepAlive=arr;
    }
  },
  methods:{
      reload(){
          this.isRouteAlive=false;
          nextTick(()=>{
              this.isRouteAlive=true;
          })
      }
  },
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
  padding-top: 90px;
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
