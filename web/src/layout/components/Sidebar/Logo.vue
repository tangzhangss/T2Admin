<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 v-else class="sidebar-title">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 class="sidebar-title">{{ title }}</h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  created() {
     //获取当前租户的信息
    this.$http.get('/service_api/client/get/'+this.$store.getters.userInfo.clientId).then((res) => {
     if(res.data){
        this.title=res.data.name;
        this.logo=res.data.logo;
      }else{
       this.title='TZCC-REN ADMIN';
       this.logo=require("@/assets/logo.jpg");
      }
    })
  },
  data() {
    return {
      title: '',
      logo: '',
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  background: #2b2f3a;
  /*padding-left:20px;*/
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    display: flex !important;
    align-items: center;
    justify-content: center;
    flex-wrap: nowrap;
    height: 100%;
    width: 100%;
    & .sidebar-logo {
      width: 30px;
      height: 30px;
      margin:0 5px;
    }

    & .sidebar-title {
      display: inline-block;
      color: #fff;
      font-weight: 600;
      font-size: 14px;
      text-align: left;
      box-sizing: border-box;
      margin:0 5px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
