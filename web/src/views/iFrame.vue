<template>
    <div class="tz-iframe-page">
        <iframe id="TzIframe" :height="height"  :src="src" @load="sendMessage()"></iframe>
    </div>
</template>

<script>
    import cookie from 'js-cookie';
    import {nextTick} from 'vue';
    export default {
        name: "IFrame",
        watch:{
            $route(){
                this.setSrc();
            }
        },
        data(){
            return{
                height:document.documentElement.clientHeight,
                src:""
            }
        },
        created() {
            this.adjustTableHeight();//调整iframe动态高度

            this.setSrc();
        },
        methods:{
            setSrc(){
                let id = this.$route.meta.id;
                this.src=this.$store.getters.externalServiceMap.get(id);
            },
            sendMessage(){
                let frame = document.getElementById('TzIframe');
                //发送cookie
                let userInfo = cookie.get(window.userInfoCookieKey);

                let msg={
                    key:"USER_INFO",
                    value:userInfo
                }
                //iframe加载完立即发送一条消息
                frame.contentWindow.postMessage(msg
                    ,'*');

            },
            //调整表格的高度
            adjustTableHeight(){
                let func = ()=>{
                    let clientHeight= document.documentElement.clientHeight;
                    this.height =clientHeight-50; // 顶部50 2容错
                };
                func();//先执行一次
                window.onresize = func;
            },
        }
    }
</script>

<style scoped>
    .tz-iframe-page iframe {
        display: block; /* iframes are inline by default */
        border: none; /* Reset default border */
        width: 100%;
        box-sizing: border-box;
    }
</style>