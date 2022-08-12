<template>
    <div v-for="msg in message">
        <p>{{msg}}</p>
    </div>
</template>

<script>
    import SockJS from "sockjs-client";
    import Stomp from 'stompjs';

    export default {
        name: "test",
        data:function () {
            return {
                message:[]
            }
        },
        created(){
            let socket = new SockJS("https://localhost:4454/api", null , { timeout: 15000 })
            let stompClient = Stomp.over(socket)
            stompClient.debug = true
            stompClient.connect(
                {
                    "Authorization":"sssssssssssssssssss"
                },
                () => {
                    let uuid =  new Date().getTime();
                    //发送消息
                    stompClient.send("/app/kafka-topic-monitor/1", {}, JSON.stringify(
                        {
                            tag:"kafka-topic-monitor",//固定
                            content:{
                                topic:"bak_file_topic1",//topic英文名
                                maxMessage:10,//消息条数--Int类型
                                cmd:"start",//start/stop/status
                                grep:"",//string 过滤条件
                            }
                        }
                    ),(e)=>{
                        this.message.push("发送消息错误:"+e);
                    });

                    //订阅--接收消息
                    stompClient.subscribe('/topic/kafka-topic-monitor/1', (message) => {
                        this.message.push("收到消息:"+message.body);
                    })

                    this.message.push(`Websocket is Connected`)
                },
                (err) => {
                    this.message.push("connected fail:"+err)
                }
            );
        },
    }
</script>

<style scoped>

</style>