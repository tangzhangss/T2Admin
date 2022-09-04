var settingForParms={view:{showLine:!1,addDiyDom:addDiyDomForParms,addHoverDom:addHoverDomForParms,removeHoverDom:removeHoverDomForParms},data:{simpleData:{enable:!0}},edit:{enable:!0,showRenameBtn:!1,showRemoveBtn:function(e,a){return 1==a.level},removeTitle:"删除",drag:{autoExpandTrigger:!0,prev:!1,inner:!1,next:!1,isCopy:!0,isMove:!1}},callback:{beforeDrag:beforeDragForParms,onClick:zTreeOnClickForParms,onDrop:onDropForParams,beforeRemove:zTreeBeforeRemoveForParms}},parmvlList=[{label:"当前时间",value:"[now()]"},{label:"当月 第一天",value:"[monthfrist]"},{label:"当月 最后一天",value:"[monthlast]"},{label:"当年 第一天",value:"[yearfrist]"},{label:"当年 最后一天",value:"[yearlast]"},{label:"当前时间 后30天(30)",value:"${addday(30)}"},{label:"当前时间 前30天(30)",value:"${addday(-30)}"},{label:"当前时间 后一个月(1)",value:"${addmonth(1)}"},{label:"当前时间 前二个月(2)",value:"${addmonth(-2)}"},{label:"当前时间 一年前(1)",value:"${addyear(-1)}"},{label:"当前时间 上周(周一)",value:"${lastweek(1)}"},{label:"当前时间 本周 (周二)",value:"${thisweek(2)}"},{label:"当前时间 下周 (周六)",value:"${nextweek(6)}"}];function onDropForParams(e,a,t,n,r,o){panel.parmSetting(e,t,n)}var tempLayeroForParms,zNodesForParms=[{id:0,name:"参数列表",open:!0}];function getDataParmsNodesByParam(e,a,t){return $.fn.zTree.getZTreeObj("dataParms").getNodesByParam(e,a,t)}function addDiyDomForParms(e,a){0==a.level&&$("#"+a.tId+"_a").append('<button type="button" class="tb_btn" id="addParmBtn" onclick="addDataParms()">添加参数</button>')}function zTreeOnClickForParms(e,a,t){}function beforeDragForParms(e,a){return 1==a[0].level}function zTreeBeforeRemoveForParms(e,a){return layer.confirm("确认删除参数  “"+a.name+"” 吗？",{btn:["确定","取消"]},function(e){$.fn.zTree.getZTreeObj("dataParms").removeNode(a),layer.alert("删除成功!")},function(e){layer.close(e)}),!1}function addHoverDomForParms(e,a){if(1!=a.level)return!1;var t,n=$("#"+a.tId+"_span");a.editNameFlag||0<$("#"+a.tId+"_editBtn").length||(t="<span class='button edit' id='"+a.tId+"_editBtn' title='修改' onfocus='this.blur();'></span>",n.after(t),(t=$("#"+a.tId+"_editBtn"))&&t.bind("click",function(){return addDataParms(a),!1}))}function removeHoverDomForParms(e,a){if(1!=a.level)return!1;$("#"+a.tId+"_editBtn").unbind().remove()}function addDataParms(r){console.log(r),layer.open({type:1,area:["535px","510px"],title:"参数配置",content:$("#dataParmsInfo"),cancel:function(){},success:function(e,a){r?setDomAllVals(e[0],r):setDomAllVals(e[0]);function t(e){$("#parmvl").unbind("click"),$("#fieldSize").unbind("click"),"date"==e||"year"==e||"month"==e||"time"==e||"datetime"==e?$("#parmvl").inputSelect({data:parmvlList,callback:function(e){}}):"radio-pop-tree"!=e&&"checkbox-pop-tree"!=e||$("#fieldSize").inputSelect({data:[{label:"行政区域",value:"SYS_AREA"},{label:"行业分类",value:"SYS_WAY"}],callback:function(e){}})}t($("#showType").val()),$("#showType").bind("change",function(){t($(this).val())})},end:function(){},btn:["保存","取消"],yes:function(e,a){var t=$("#parmName").val(),n=$("#parmCName").val();""!=t&&""!=n?(r?editParmNode(a,r):addParmNode(a),layer.close(e)):alert("请输入参数名称和说明")},btn2:function(e,a){layer.close(e)}})}function addParmNode(e){var a=getDomAllVals(e[0]),t=$.fn.zTree.getZTreeObj("dataParms"),n=t.getNodes(),e=$.extend(!0,{},a);e.name=a.parmCName,e.puuid=getPuuid("parm"),e=t.addNodes(n[0],e)}function editParmNode(e,a){var t=getDomAllVals(e[0]),e=$.fn.zTree.getZTreeObj("dataParms");(a=$.extend(!0,a,t)).name=t.parmCName,e.updateNode(a)}function addParmNodeForVal(e){var a=$.fn.zTree.getZTreeObj("dataParms"),t=a.getNodes(),n=$.extend(!0,{},e);return n.name=e.parmCName,n.puuid=getPuuid("parm"),n.custom=1,a.addNodes(t[0],n)}function editParmNodeForNode(e){var a=$.fn.zTree.getZTreeObj("dataParms");e.name=e.parmCName,a.updateNode(e)}function getParmNodeForNodeTId(e){return $.fn.zTree.getZTreeObj("dataParms").getNodeByParam("puuid",e)}function removeForParms(e){$.fn.zTree.getZTreeObj("dataParms").removeNode(e),layer.alert("删除成功!")}!function(l){function d(n,e,a,r){var o=l('<ul class="inputSelect-box"></ul>');o.css(a),o.appendTo("body"),o.data("inputelem",n),l.each(e,function(e,a){var t;a.hasOwnProperty("value")&&(t=l('<li class="inputSelect-item"></li>'),a.hasOwnProperty("label")?t.append('<span class="item-label">'+a.label+'</span>|<span class="item-value">'+a.value+"</span>"):t.text('<span class="item-label">'+a.id+'</span>|<span class="item-value">'+a.value+"</span>"),t.data("node",a),t.appendTo(o),t.bind("click",function(){return n.val(t.data("node").value),"function"==typeof r&&r.call(this,t.data("node")),t.unbind(),o.remove(),!1}))})}l("body").bind("click",function(e){0==l(e.target).parents(".inputSelect-box").length&&-1==e.target.className.indexOf("inputSelect-box")&&-1==e.target.className.indexOf("inputSelect-elem")&&l(".inputSelect-box").remove()}),l(window).resize(function(){l(".inputSelect-box").each(function(e,a){l(this).data("inputelem")&&l(l(this).data("inputelem")).click()})}),l.fn.inputSelect=function(e){var r=l.extend({},l.fn.inputSelect.defaults,e),o=l(this);o.addClass("inputSelect-elem"),o.bind("click",function(){var e=o[0].offsetHeight,a=o[0].offsetWidth;r.left=function(e){for(var a=e.offsetLeft,t=e.offsetParent;null!==t;)a+=t.offsetLeft,t=t.offsetParent;return a}(o[0])-2,r.top=function(e){for(var a=e.offsetTop,t=e.offsetParent;null!==t;)a+=t.offsetTop,t=t.offsetParent;return a}(o[0])+e;var t,n={"min-width":a,left:r.left,top:r.top};l(".inputSelect-box").remove(),"object"==typeof r.data?(t=r.data,d(o,t,n,r.callback)):l.getJSON(r.data,function(e){t=e.data,d(o,t,n,r.callback)})})},l.fn.inputSelect.defaults={data:[],callback:function(e){console.log(e)}}}(jQuery);