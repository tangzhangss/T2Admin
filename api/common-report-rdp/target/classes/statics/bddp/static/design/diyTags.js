function layxDiyTagsForm(){layx.html("diyTagsForm","新建组件",document.getElementById("diyTagsForm"),{cloneElementContent:!0,skin:"asphalt",storeStatus:!1,shadable:!0,width:490,height:295,minMenu:!1,maxMenu:!1,resizable:!1,movable:!1,event:{onload:{before:function(t,e){},after:function(i,o){$(i).find("#saveDiyTagsBtn").unbind().bind("click",function(){var t=getDiyTagsBox($(i).find("form").inputs().data()),e={id:t.id,formId:t.fromId,data:JSON.stringify(t)};saveBddpDiyTagsData(e,function(t){0==t.code?(layx.msg("保存成功!"),layx.destroyInlay(o.id)):layx.msg("保存失败！!")})})}}}})}function getDiyTagsBox(t){var e={};return t=t||{},e.id=guid(),e.fromId=$("#content").data("id"),e.name=t.groupName||e.id,e.icon=t.groupIcon||"fa fa-file-code-o",e.boxs=[],$.each(selectableItmes,function(){var t=$(this).data("prop"),i={options:t.options,optionsText:t.optionsText,other:t.other,rectP:t.rectP,data:t.data,type:t.type,parts:t.parts,effect:t.effect,id:t.id,bigType:t.bigType,gmOptions:t.gmOptions,swiper:t.swiper,slides:t.slides};"swiper"==t.type&&(i.slides=[],$(this).find(".box").each(function(){var t=$(this).data("prop"),e={options:t.options,optionsText:t.optionsText,other:t.other,rectP:t.rectP,data:t.data,type:t.type,effect:t.effect,parts:t.parts,id:t.id,bigType:t.bigType,gmOptions:t.gmOptions,slide:t.slide};i.slides.push(e)})),e.boxs.push(i)}),console.log(e),e}