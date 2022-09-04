function openPhonePanel() {
	layx.load('loadId', '正在加载，请稍后');
	layx.html('PhonePanel', '移动端布局', '', {
		statusBar: true,
		skin: "asphalt",
		width: 870,
		height: 600,
		minimizable: false,
		maximizable: false,
		shadable: true,
		// movable: false,
		resizable: false,
		buttons: [
			{
				label: '确认',
				callback: function (id) {

					var winform = layx.getWindow(id);
					var layxWindow = winform.layxWindow;
					var boxIds = [];
					$(layxWindow).find("#sortable-phone > li").each(function () {
						boxIds.push($(this).attr("id"));
					})
					$("#content").data("phonepanel", boxIds);
					layx.destroy(id);
				}
			},
			{
				label: '取消',
				callback: function (id) {
					layx.destroy(id);
				}
			}
		],
		event: {
			onload: {
				before: function (layxWindow, winform) {
				},
				after: function (layxWindow, winform) {
					$(layxWindow).find("#layx-PhonePanel-html").load("component/phoneview.html", function () {
						layx.destroy('loadId');
						createHidePhonebox(layxWindow);
					});

				}
			}
		}
	})
}


function getPhoneViewBoxs() {
	var boxs = {};
	$("#content").find(".box").each(function () {
		var type = $(this).attr("tag-type");
		if (HideTagsType.indexOf(type) > -1) {
			var prop = $(this).data("prop");
			var boxid = prop.id;
			var myChart = prop.myChart;
			if (!!myChart) {
				boxs[boxid] = {
					img:myChart.getDataURL(),
					text:"图表"
				};
			} else if (type == "image") {
				boxs[boxid] ={
					img:"'" + prop.parts["imgUrl"] + "'",
					text:"图片"
				}; 
			} else if (type == "swiper") {
				var slider = $($(this).find(".box")[0])
				var sliderprop = slider.data("prop");
				var myChart = sliderprop.myChart;
				if (!!myChart) {
					boxs[boxid] = {
						img:myChart.getDataURL(),
						text:"轮播"
					};
				}
			}
		}
	})
	return boxs;
}

function createHidePhonebox(layxWindow) {
	var sortablehide = $(layxWindow).find("#sortable-hide");
	var sortablephone = $(layxWindow).find("#sortable-phone");
	var boxs = getPhoneViewBoxs();
	var phonepanel = $("#content").data("phonepanel");
	var sortarr = [];
	$.each(boxs, function (boxid, data) {
		var itemDiv = $('<li class="ph-hide-view"></li>');
		itemDiv.attr("id", boxid);
		itemDiv.text(data.text);
		itemDiv.css("background-image", "url(" + data.img + ")");
		if (phonepanel && phonepanel.indexOf(boxid) > -1) {
			sortarr[phonepanel.indexOf(boxid)] = itemDiv;
		} else {
			sortablehide.append(itemDiv);
		}
	});
	for (var i = 0; i < sortarr.length; i++) {
		sortablephone.append(sortarr[i]);
	}
	$("#sortable-phone, #sortable-hide").sortable({
		connectWith: ".connectedSortable"
	}).disableSelection();
}