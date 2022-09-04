var curDragNodes;
$(function() {
	$("#goBack").bind("click",function(){
		location.href="dataConfig.html";
	});
	$("#dsc-tabs").tabs();

	var treeSetting = {
		edit: {
			drag: {
				isCopy: true,
				autoExpandTrigger: true,
				prev: false,
				inner: false,
				next: false
			},
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeDrag: beforeDrag,
			beforeDrop: beforeDrop,
			onDrop: onDrop

		}
	};
	var tableSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeDrag: function() {
				return false;
			},
			beforeDrop: beforeDrop,
			onDrop: onDrop
			//			onDrag: onDrag,
		},
		view: {
			showIcon: false,
			showLine: false,
			addDiyDom: addDiyDom
		}
	};


	function beforeDrag(treeId, treeNodes) {

		if(treeNodes[0].level == 2) {
			curDragNodes = treeNodes[0];

			return true;
		} else {
			return false;
		}
	}

	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		console.log(treeId);
		if(treeId == "tableTree") {
			if(!targetNode) {
				var treeObj = $.fn.zTree.getZTreeObj("tableTree");
				var nodes = treeObj.getNodes();
				if(nodes.length > 0) {
					var levelNodes = treeObj.getNodesByParam("level", 0, null);
					var pNode = levelNodes[0];
					treeObj.addNodes(pNode, treeNodes);
					dropEnd();
					return false;
				} else {
					return true;
				}
			}
			var aObj = $("#" + targetNode.tId + "_a");
			var joinObj = aObj.find(".tag-cptype-join");
			var unionObj = aObj.find(".tag-cptype-union");
			if(joinObj.hasClass("on")) {
				console.log("连接");
				return true;
			} else if(unionObj.hasClass("on")) {
				console.log("联合");
				return false;
			} else {
				console.log("啥也没做");
				dropEnd();
				return false;
			}

		} else {
			dropEnd();
			return false;
		}
	}

	function onDrop(event, treeId, treeNodes, targetNode, moveType) {

	}

	function addDiyDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		aObj.addClass("table-tag");
		$("#" + treeNode.tId + "_switch").hide();
		aObj.append('<div class="tag-cp"><span class="tag-cptype tag-cptype-union">联合</span><span class="tag-cptype tag-cptype-join">连接</span></div></div>');
		tagEventInit(aObj);
	};
	$.fn.zTree.init($("#dsl-tables"), treeSetting, datasource);
	$.fn.zTree.init($("#tableTree"), tableSetting);
	$(".ec-scene").hover(function() {
		if(curDragNodes) {
			$(this).addClass("onhover");
			$(".tag-cp").show();
		}
	}, function() {
		$(this).removeClass("onhover");
	});

});

function tagEventInit(tag) {
	tag.find(".tag-cptype").on("mouseover", function() {
		$(this).addClass("on");
	});
	tag.find(".tag-cptype").on("mouseout", function() {
		$(this).removeClass("on");
	});
}

function addTableTag(node, event, pNode) {
	var tag = $('<div class="table-tag"><span class="tag-name">' + node.name + '</span><div class="tag-cp"><span class="tag-cptype tag-cptype-union">联合</span><span class="tag-cptype tag-cptype-join">连接</span></div></div>');
	if($(".table-tag").length == 0) {
		tag.attr("data-level", 0);
		tag.attr("data-id", "tag-0-0");
	} else if(pNode) {
		var level = pNode.data("level") + 1;
		var index = $('[data-ptag="' + pNode.data("id") + '"]').length;
		tag.attr("data-level", pNode.data("level") + 1);
		tag.attr("data-ptag", pNode.data("id"));
		tag.attr("data-id", "tag-" + level + "-" + index);
	} else {
		var index = $('[data-ptag="tag-0-0"]').length;
		tag.attr("data-level", 1);
		tag.attr("data-ptag", "tag-0-0");
		tag.attr("data-id", "tag-1-" + index);
	}

	tag.appendTo($(".ec-scene"));
	tag.css({
		"top": $(".ec-scene").height() / 2 - tag.height() / 2
	});
	tagEventInit(tag);
	drawTableTags();
	return tag;
}

function dropEnd() {
	curDragNodes = null;
	$(".tag-cp").hide();
}

function drawTableTags() {
	var maxLevel = 0;
	$('.table-tag').each(function() {
		var tagLeft = $(this).data("level") * 240;
		$(this).css({
			"left": tagLeft
		});
		maxLevel = maxLevel > $(this).data("level") ? maxLevel : $(this).data("level");
	});
	setTagTop('tag-0-0');
	getTagSubs('tag-0-0');
}

function setTagTop(pId) {
	var count = $('.table-tag[data-ptag="' + pId + '"]').length;
	var p1Top = $('.table-tag[data-id="' + pId + '"]').position().top - 50 * (count - 1);
	$('.table-tag[data-ptag="' + pId + '"]').each(function(i) {
		$(this).css("top", (p1Top + 100 * i) + "px");
		if($('.table-tag[data-ptag="' + $(this).data("id") + '"]').length > 0) {
			setTagTop($(this).data("id"));
		}
	})
}

function getTagSubs(id) {
	var count = 0;
	if($('.table-tag[data-ptag="' + id + '"]').length > 0) {
		count += $('.table-tag[data-ptag="' + id + '"]').length;
		$('.table-tag[data-ptag="' + id + '"]').each(function() {
			var temp = getTagSubs($(this).data("id"));
			if(temp > 0) {
				count += temp - 1;
			}
		})
	}
	console.log(id, count);
	return count;
}

function guid() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random() * 16 | 0,
			v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function addSVGLine(start, end) {
	var startRect = getDOMRectProp(start);
	var endRect = getDOMRectProp(end);
	var path = draw.path(createPathStr(startRect, endRect));
	var lineId = path.attr("id");
	var startLineIds = start.data("lineId") || [];
	var endLineIds = end.data("lineId") || [];
	startLineIds.push(lineId);
	endLineIds.push(lineId);
	start.data("lineId", startLineIds);
	end.data("lineId", endLineIds);
}

function updataSVGLine(start, end) {
	var startRect = getDOMRectProp(start);
	var endRect = getDOMRectProp(end);
	var endLineIds = end.data("lineId");
}

function createPathStr(startRect, endRect) {
	var splitNum = 4;
	var str = "M";
	str += startRect.cx + " " + startRect.cy + " ";
	var x1 = (endRect.left - startRect.left) / splitNum + startRect.left;
	var y1 = (endRect.top - startRect.top) / splitNum + startRect.top;
	var x2 = endRect.left - (endRect.left - startRect.left) / splitNum;
	var y2 = endRect.top - (endRect.top - startRect.top) / splitNum;
	var x = endRect.cx;
	var y = endRect.cy;
	str += "C" + x1 + " " + y1 + " " + x2 + " " + y2 + " " + x + " " + y;
	return str;
}

function getDOMRectProp(tag) {
	return {
		"left": tag.position().left,
		"top": tag.position().top,
		"width": tag.width(),
		"height": tag.height(),
		"cx": tag.position().left + tag.width() / 2,
		"cy": tag.position().top + tag.height() / 2
	}
}