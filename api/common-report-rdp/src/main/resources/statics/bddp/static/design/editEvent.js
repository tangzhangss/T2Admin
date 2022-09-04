function editEvent(eventValue, callback) {
    layx.html('eventEdit', 'js编辑器', layx.multiLine(function () {
        /*
                     
        <style type="text/css">
            #eventEditor {
            margin: 0;
            position: absolute;
            top: 0;
            bottom: 40px;
            left: 0;
            right: 180px;
            font-size:16px;
        }
        #eventParams{
             margin: 0;
            position: absolute;
            top: 0;
            bottom: 40px;
            right: 0;
            width: 180px;
            border-left: 1px solid #495057;
            background-color:rgb(0, 34, 64);
        }
        #eventCtrl{
             margin: 0;
            position: absolute;
            height:40px;
            bottom: 0;
            right: 0;
            left:0;
            text-align:center;
            background-color:rgb(0, 34, 64);
        }
        #saveEventBtn{
            border: 1px solid #495057;
            width: 80px;
            font-size: 12px;
            border-radius: 5px;
            background-color: #263954;
        }
        #commonEvents{
            overflow: auto;
            height: 49%;
        }
        #eventParams li{
            font-size: 12px;
            border-radius: 10px;
            border: 1px solid;
            margin: 10px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            padding: 0 10px;
            cursor: pointer;
        }
        #eventParams li:hover{
            background-color: #6c757d;
            color: #fff;
        }
        #eventsBoxs{
            overflow: auto;
            height: 49%;
        }
        #eventsBoxs li{
            margin:0px;
            border-radius: 0px;
            border: 0px solid;
            border-bottom: 1px solid;
        }
        </style>

        <pre id="eventEditor"></pre>
        <div id="eventParams">
            <ul id="commonEvents"></ul>
            <ul id="eventsBoxs"></ul>
        </div>
        <div id="eventCtrl">
            <button type="button" id="saveEventBtn">保存</button>
        </div>
        */
    }), {
            skin: "asphalt",
            storeStatus: false,
            shadable: true,
            width: 705,
            height: 805,
            minMenu: false,
            maxMenu: false,
            event: {
                onload: {
                    // 加载之前，return false 不执行
                    before: function (layxWindow, winform) {

                    },
                    // 加载之后
                    after: function (layxWindow, winform) {
                        var eventEditor = ace.edit($(layxWindow).find("#eventEditor")[0]);
                        eventEditor.setTheme("ace/theme/cobalt");
                        eventEditor.session.setMode("ace/mode/javascript");
                        if (eventValue) {
                            eventEditor.setValue(eventValue);
                        }

                        $(layxWindow).find("#saveEventBtn").bind("click", function () {
                            eventEditor.setTheme("ace/theme/cobalt");
                            if (callback) {
                                callback.call(this, eventEditor.getValue());
                            }
                            layx.destroy(winform.id);
                        });
                        var prop = currBox.data("prop");
                        var type = prop.type;
                        var bigType = prop.bigType;

                        $.getJSON("../../statics/bddp/static/common-event/common.json", {},
                            function (data, textStatus, jqXHR) {
                                var commonEvents = $(layxWindow).find("#commonEvents");
                                var eventsBoxs = $(layxWindow).find("#eventsBoxs");
                                $.each(data.events, function (i, event) {
                                 //   if (event.type=="all"||event.type==type||event.type==bigType) {
                                        var li = $('<li title="' + event.desc + '">' + event.name + '</li>');
                                        li.data(event);
                                        $(commonEvents).append(li);
                                        li.bind("click", function () {
                                            var fun =  $(this).data("fun");
                                            var param = $(this).data("param");
                                            if (param) {
                                                fun = getParamsForEvent(param,fun,prop);
                                            }
                                            eventEditor.navigateFileEnd();
                                            eventEditor.insert("\n//" + $(this).data("desc") + "\n" + fun + ";");
                                            
                                        })
                                  //  }
                                });
                                $(".box").each(function() {
                                    var boxId =$(this).attr("id");
                                    var id = $(this).data("prop").id;
                                    var li = $('<li title="' + boxId+ '">' + boxId + '</li>');
                                    eventsBoxs.append(li);
                                    li.bind("click",function() {
                                        eventEditor.insert("'"+id+"'");
                                    })
                                    li.hover(function () {
                                        // 鼠标移入时添加hover类
                                        var box = $("#" + $(this).attr("title"));
                                        box.addClass("temp-sm-selected");
                                        $("#layx-eventEdit").css("opacity","0.3");
                                        $("#rightnav").hide().addClass("off");
                                    }, function () {
                                        // 鼠标移出时移出hover类
                                        var box = $("#" + $(this).attr("title"));
                                        box.removeClass("temp-sm-selected");
                                        $("#layx-eventEdit").css("opacity","1");
                                    });
                                })
                            }
                        );
                       
                        console.log(bigType, type);


                    }
                }
            }
        });
}   



function getParamsForEvent(param,fun,prop){
    switch (param) {
        case "id":
            var regs = new RegExp('\\$\\{(.*?)\\}', 'gi');
            fun = fun.replace(regs,prop[param]);
            break;
        default:
            break;
    }

    return fun;
}