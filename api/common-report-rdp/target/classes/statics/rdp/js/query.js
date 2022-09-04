function initform(){
    $('#searchaddition input[rel=data]').each(function (index, element) {
        var format=$(element).attr('format');
        if(format==undefined||format==''){
            format='yyyy-MM-dd';
        }
        laydate.render({
            elem : '#'+$(element).attr("id"),
            format : format,
            type : $(element).attr("showtype")
        });
    });
	$('#searchaddition select[rel=multiple]').each(function (index, element) {
		console.log($(element).attr("id")+'-'+$(element).prev().text());
		$('#'+$(element).attr("id")).popupSelection({
			searchOn: true, //启用搜索
			inline: true, //弹出层
			multiple: true, //多选
			title:$(element).prev().text(),
			cellCount: 4, //每行选项个数
			labelShow:true //是否在选择区域显示label标签
		});
	});
}
$(function () {
    initform();
})