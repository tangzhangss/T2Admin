<template>
    <el-tabs v-model="tabsValue"  class="tz-route-tabs" closable @tab-click="handleClick"  @tab-remove="removeTab">
        <el-tab-pane
                :key="item.path"
                v-for="(item, index) in $store.getters.routeTabs"
                :label="item.meta && item.meta.title"
                :name="item.path"
        >
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import cookie from "js-cookie";

    const pathToRegexp = require('path-to-regexp');

    export default {
        name: 'tz-table',
        data() {
            return {
                tabsValue:""
            }
        },
        watch: {
            $route() {
                this.updateRouteTabs()
            }
        },
        created() {
            this.updateRouteTabs();
        },
        methods: {
            handleClick(tab,event){
              this.$router.push(tab.props.name);
            },
            removeTab(targetName){
                //如果删除了当前的targetName，路由跳转到{选择的path,当前的path}
                this.$store.commit("permission/DELETE_ROUTE_TAB",targetName);
                if(this.$store.getters.routeTabs.length==0)this.$router.push("/");//跳转首页没有标签了
                else if (targetName==this.tabsValue){
                    //被删除的路径等于当前的路径,返回上一页
                    this.$router.go(-1);
                }
            },
            updateRouteTabs() {
               this.tabsValue=this.$route.fullPath;
               this.$store.commit("permission/UPDATE_ROUTE_TAB",this.$route);
             },
            pathCompile(path) {
                // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
                const { params } = this.$route;
                var toPath = pathToRegexp.compile(path)
                return toPath(params)
            },
            handleLink(item) {
                const { redirect, path } = item
                if (redirect) {
                    this.$router.push(redirect)
                    return
                }
                this.$router.push(this.pathCompile(path))
            }
        }
    }
</script>

<style lang="scss">
    .tz-route-tabs{
        display: inline-block;
        font-size: 14px;
        line-height: 50px;
        .el-tabs__active-bar{
            background: none;
        }
        .el-tabs__item{
            height: 32px;
            line-height:32px;
            padding: 0 5px !important;
            margin-left:5px;
            border: 1px solid #d8dce5;
            &.is-active{
                color: white;
                background-color: #42b983;
                border:1px solid #42b983;
                box-sizing: border-box;
                &::before {
                    content: "";
                    background: #fff;
                    display: inline-block;
                    width: 10px;
                    height: 10px;
                    border-radius: 50%;
                    position: relative;
                    margin-right: 2px;
                }
            }
            &:hover{
            }
        }
        .el-tabs__nav-wrap::after{
            background-color:unset;
        }
    }
</style>
