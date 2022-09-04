/**
 * 可以外部调用的方法
 * 1.addItem 新增一个选项
 * 2.selectedItem 设置一个选项被选中
 * 3.hideSelect 隐藏
 * 4.selectedTreeItem 设置一个树形选择被选中
 * 5.updateItems 更新选项
 */
(function($, win) {
	var methods = {
		init: function(opts) { //初始化
			var defaults = {
				ajaxUrl: false, //异步url
				multiple: false, //false-单选,true-复选
				inline: true, //true-内联,false-弹出
				valueClass: false, //String类型，自定义显示值class
				selectClass: false, //String类型，自定义选框class
				searchOn: false, //false-不启用搜索，true-启用搜索
				addBtn: false, //false-不启用新增按钮,{"title":"按钮名称","fun":function(){//点击执行}}
				items: [], //[{id:"1",name:"A"},{...}]
				itemsCount: false, //控制显示条数
				labelEdit: false, //选项编辑回调函数function(d){console.log(d)};
				labelShow: true, //是否在选择区域显示，已选择项
				cellCount: 4, //每行显示选项个数
				ztree: false, //是否使用ztree
				ztreeSetting: false, //ztree配置
				title: false, //标题
				changeCallback: false, //回调函数
				handle: false, //触发器
				groupFlag: false, //是否是分组模式
				initFlag: true,
				width: false, //自定义宽度
				height: false, //自定义高度
				top: false, //顶部距离
				splitStr: ",", //分隔符
				parentSelect: false, //父节点是否选择
				dragParams: { //拖拽实现
					left: 0,
					top: 0,
					currentX: 0,
					currentY: 0,
					flag: false
				}
			}
			return this.each(function(index,currElem) {
				var options = $.extend(true,{},defaults, opts);
				var $this = $(currElem);
				options.elem = $this;
				$this.data("options", options);
				if(options.ajaxUrl && typeof(options.ajaxUrl) == "string") {
					$.ajax({
						type: "get",
						url: options.ajaxUrl,
						datatype: "json",
						async: true,
						success: function(data) {
							if(typeof(data) == "string") {
								options.items = JSON.parse(data.items);
							} else {
								options.items = data.items;
							}
							var hideItem = $('<input type="hidden" >');
							hideItem.insertAfter($this);
							$this.data("values", hideItem);
							if($this.attr("name")) {
								hideItem.attr('name', $this.attr("name"));
								$this.attr("name", "pops" + $this.attr("name"));
							}
							if($this.attr("mustinput") && $this.attr("mustinput") != "") {
								hideItem.attr('mustinput', $this.attr("mustinput"));
								hideItem.attr('title', $this.attr("title"));
								$this.removeAttr("mustinput");
							}
							$this.data("items", options.items);
							if(options.ztree) {
								methods.popTreeInit(options, $this);
								methods.showTreeValueDiv($this, options);
								methods.itemsToJson(options);
							} else {
								methods.showValueDiv($this, options);
								methods.buildSelectValue($this, options);
								methods.initEvent($this);
								methods.itemsToJson(options);
								methods.initValue($this, options);
							}

						},
						error: function() {
							console.log("数据源加载失败");
						}
					});
				} else {
					if($this.is('input')) {
						//						if(!$.isArray(options.items) || options.items.length == 0) {
						//							console.log("参数items不是数组或者为空！")
						//							return false;
						//						}
					} else if($this.is('select')) {
						if(!$.isArray(options.items) || options.items.length == 0) {
							options.items = methods.getItemsBySelect($this);
						}
					}
					var hideItem = $('<input type="hidden" >');
					hideItem.insertAfter($this);
					$this.data("values", hideItem);
					if($this.attr("name")) {
						hideItem.attr('name', $this.attr("name"));
						$this.attr("name", "pops" + $this.attr("name"));
					}
					if($this.attr("mustinput") && $this.attr("mustinput") != "") {
						hideItem.attr('mustinput', $this.attr("mustinput"));
						hideItem.attr('title', $this.attr("title"));
						$this.removeAttr("mustinput");
					}
					$this.data("items", options.items);
					if(options.ztree) {
						methods.popTreeInit(options, $this);
						methods.showTreeValueDiv($this, options);
						methods.itemsToJson(options);
					} else {
						methods.showValueDiv($this, options);
						methods.buildSelectValue($this, options);
						methods.initEvent($this);
						methods.itemsToJson(options);
						methods.initValue($this, options);
					}
				}

			});
		},
		destroy: function() { //销毁
			return this.each(function() {})
		},
		initValue: function(elem, options) { //赋初值
			var val = elem.val();
			var arr = [];
			if(val && val.indexOf(options.splitStr) > -1) {
				arr = val.split(options.splitStr);
				arr = $.grep(arr, function(n) {
					return $.trim(n).length > 0;
				})
			} else if(val) {
				arr.push(val);
			}
			var popsparm = elem.data("popsparm");
			for(var i = 0; i < arr.length; i++) {
				if(options.inline) {
					methods.setSelectValue(options, elem, arr[i]);
				} else {
					methods.setChooseInitValue(options, elem, popsparm.find(".option-input[data-id=" + arr[i] + "]"));
					methods.confirmValue(options, elem);
				}
			}
			options.initFlag = false;
		},
		cleanValue: function(elem, options) { //赋初值
			var popsparm = elem.data("popsparm");
			var popsvalue = elem.data("popsvalue");
			if(options.inline) {
				var itemMap = options.itemMap;
				if(options.multiple) {
					var node = popsparm.find(".pops-item");
					node.removeClass("on");
					popsvalue.find(".pops-label").remove();
				} else {
					popsvalue.html("");
				}
			} else {
				popsparm.find(".pops-select-search").find(".pops-label").remove();
				popsparm.find(".option-input").prop("checked", false);
				popsparm.find(".pops-item").removeClass("pitch");
				popsvalue.empty();

			}
			elem.val("");
			elem.data("text", "");
			elem.data("values").val("");
			methods.hideSelect(elem);
			options.initFlag = false;
		},
		showSelectValue: function(elem, options) { //赋初值
			var val = elem.data("values").val();
			var arr = [];
			if(val && val.indexOf(options.splitStr) > -1) {
				arr = val.split(options.splitStr);
			} else if(val) {
				arr.push(val);
			}
			var popsparm = elem.data("popsparm");
			methods.clearValue(options, elem);
			popsparm.find(".pops-item").removeClass("pitch");
			for(var i = 0; i < arr.length; i++) {
				if(arr[i]) {
					var node = popsparm.find(".pops-item[data-id=" + arr[i] + "]");
					node.find(".option-input").prop("checked", true);
					var label = $(methods.getMultipleHtml(node, options));
					label.appendTo(popsparm.find(".pops-select-search"));
					methods.setLabelEvent(label, options);
					popsparm.find(".pops-item[data-id=" + node.data("id") + "]").addClass("pitch");
				}
			}
		},
		getItemsBySelect: function(elem) { //从select获取items
			var items = [];
			elem.find("option").each(function(i, node) {
				var obj = {};
				obj.id = $(node).val();
				obj.name = $(node).text();
				items.push(obj);
			});
			return items;
		},
		showValueDiv: function(elem, options) { //页面显示值区域
			var div = $('<div class="pops-value"></div>');
			if(options.valueClass && typeof(options.valueClass) == "string") {
				div.addClass(options.valueClass);
			}

			/* 解决bootstrap样式表单中列宽问题 modified by LiuYF 20170525 */
			//			div.css("display", "inline-block");
			//			div.css("min-width", elem.outerWidth());
			div.css("min-height", elem.outerHeight()-2);
			elem.css("display", "none");
			div.insertAfter(elem);
			div.bind("click", function() {
				methods.showSelect(this, elem);
			});
			if(options.handle) {
				$(options.handle).bind("click", function() {
					methods.showSelect(this, elem);
					return false;
				});
			}
			elem.data("popsvalue", div);
		},
		buildSelectValue: function(elem, options) { //构建选择区域
			if(options.inline) {
				var html = $(methods.getInlineSelectHtml());
				elem.parent().find(".pops-select").remove();
				html.insertAfter(elem);
				var items = $(methods.getInlineItemsHtml(options.items));
				html.find(".pops-select-body").append(items);
				elem.data("popsparm", html);
				items.find(".pops-item").bind("click", function() {
					methods.setSelectValue(options, elem, $(this).data("id"));
				});
				if(options.searchOn) {
					var search = $(methods.getSearchHtml());
					html.find(".pops-select-head").append(search);
					html.find(".pops-search-input").bind("keyup", function() {
						methods.searchItems(options, $(this).val());
					});
					if(typeof($.fn.placeholder) == "function") {
						search.find("input").placeholder({
							isUseSpan: true,
							all: false
						});
					}
				}
				if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
					var addbtn = $(methods.getAddBtnHtml());
					html.find(".pops-select-foot").append(addbtn);
					addbtn.find(".pops-addbtn-btn").text(options.addBtn.title);
					if(options.addBtn.fun && typeof(options.addBtn.fun) == "function") {
						addbtn.bind("click", function() {
							options.addBtn.fun.call(this, elem.data("values"), elem);
						})
					}
					if(options.addBtn.icon && typeof(options.addBtn.icon) == "string") {
						addbtn.find("i").addClass(options.addBtn.icon);
					} else {
						addbtn.find("i").addClass("pops-addbtn-icon").html("+");
					}
				}
			} else {
				var html = $(methods.getOutlineSelectHtml(options));
				$("body").append(html);
				var items = $(methods.getOutlineItemsHtml(options.items, options));
				html.find(".pops-select-body").append(items);
				elem.data("popsparm", html);
				items.find(".pops-item").bind("click", function() {
					methods.setChooseValue(options, elem, $(this).find(".option-input"));
				});
				if(!options.labelShow) {
					html.find(".pops-select-search").hide();
				}
				if(options.title) {
					html.find(".pops-select-head").append('<span class="pops-select-title">' + options.title + '</span>');
				}
				if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
					var addbtn = $(methods.getTreeAddBtnHtml());
					html.find(".pops-select-head").append(addbtn);
					addbtn.text(options.addBtn.title);
					if(options.addBtn.fun && typeof(options.addBtn.fun) == "function") {
						addbtn.bind("click", function() {
							options.addBtn.fun.call(this, elem, elem.data("values"));
						})
					}
				}
				if(options.searchOn) {
					var search = $(methods.getSearchHtml());
					html.find(".pops-select-head").append(search);
					html.find(".pops-search-input").bind("keyup", function() {
						methods.searchItems(options, $(this).val());
					});
					if(typeof($.fn.placeholder) == "function") {
						search.find("input").placeholder({
							isUseSpan: true,
							all: false
						});
					}
				}
				html.find(".pops-select-head .pops-close").bind("click", function() {
					methods.hideSelect(elem);
				});
				html.find(".pops-select-foot .pops-btn-confirm").bind("click", function() {
					methods.confirmValue(options, elem);
				});
				methods.startDrag(html.find(".pops-select-head")[0], html.find(".pops-select")[0], options);
			}
		},
		showSelect: function(obj, elem) { //显示选择区域
			if (elem.prop("readonly")||elem.attr("readonly")=="readonly") {
				return false;
			}
			
			var popsparm = elem.data("popsparm");
			var options = elem.data("options");
			$(".pops-select").hide();
			if(elem.data("options").inline) {
				var popsvalue = elem.data("popsvalue");
				$(".pops-select").css("z-index", "1");
				//popsparm.css("left", $(obj).offset().left + "px");
				popsparm.css("min-width", elem.outerWidth() + "px");
				popsparm.css("z-index", "19911027");
				if(popsparm.find(".pops-item").not(".on").length != 0) {
					popsparm.show();
					var maxH;
					if(typeof(options.itemsCount) == "number") {
						maxH = popsparm.find(".pops-item").not('[data-id=""]').not(".on").outerHeight(true) * options.itemsCount;
						var tempH = win.innerHeight - popsparm.find(".pops-select-head").outerHeight() - popsparm.find(".pops-select-foot").outerHeight() - $(obj).offset().top - $(obj).outerHeight() - popsparm.find(".pops-item[data-id='']").outerHeight(true) - 5;
						if($(".formRowCenter")) {
							tempH -= $(".formRowCenter").outerHeight(true);
						}
						if(maxH > tempH) {
							popsparm.css("top", "");
							popsparm.css("bottom", $(obj).outerHeight() + "px");
						} else {
							popsparm.css("bottom", "");
							popsparm.css("top", $(obj).offset().top+$(obj).outerHeight() + "px");
						}
					} else {
						maxH = win.innerHeight - popsparm.find(".pops-select-head").outerHeight() - popsparm.find(".pops-select-foot").outerHeight() - $(obj).offset().top - $(obj).outerHeight() - 5;
						if($(".formRowCenter")) {
							maxH -= $(".formRowCenter").outerHeight(true);
						}
						if(maxH < 170) {
							maxH = $(obj).offset().top - popsparm.find(".pops-select-head").outerHeight() - popsparm.find(".pops-select-foot").outerHeight();
							popsparm.css("top", "");
							popsparm.css("bottom", $(obj).outerHeight() + "px");
						} else {
							popsparm.css("bottom", "");
							popsparm.css("top",$(obj).position().top+ $(obj).outerHeight() + "px");
						}
						popsparm.css("left",$(obj).position().left+ "px");
					}
					popsparm.find(".pops-select-body").css("max-height", maxH + "px");
				}
			} else {
				var pop = popsparm.find(".pops-select");
				$(".pops-select").css("z-index", "1");
				$(".pops-select").css("left", "");
				$(".pops-select").css("top", "");
				$(".pops-select").css("margin", "0 auto");
				if(options.width) {
					pop.css("width", options.width);
				}
				if(options.height) {
					pop.css("height", options.height);
				}
				if(options.top) {
					pop.css("top", options.top);
				}
				pop.css("z-index", "19911027");
				popsparm.find(".pops-select").show();
				popsparm.show();
				methods.showSelectValue(elem, elem.data("options"));
			}
		},
		resetSelect: function(elem) { //显示选择区域
			var obj = elem.data("popsvalue");
			if(elem.data("options").inline) {
				var popsparm = elem.data("popsparm");
				var popsvalue = elem.data("popsvalue");
				$(".pops-select").css("z-index", "1");
				$(".pops-select").hide();
				//popsparm.css("left", $(obj).offset().left + "px");
				popsparm.css("min-width", obj.outerWidth() + "px");
				popsparm.css("z-index", "19911027");
				popsparm.show();
				var maxH = win.innerHeight - popsparm.find(".pops-select-head").outerHeight() - popsparm.find(".pops-select-foot").outerHeight() - $(obj).offset().top - $(obj).outerHeight() - 5;
				if($(".formRowCenter")) {
					maxH -= $(".formRowCenter").outerHeight(true);
				}
				if(maxH < 170) {
					maxH = $(obj).offset().top - popsparm.find(".pops-select-head").outerHeight() - popsparm.find(".pops-select-foot").outerHeight();
					popsparm.css("top", "");
					popsparm.css("bottom", $(obj).outerHeight() + "px");
				} else {
					popsparm.css("bottom", "");
					popsparm.css("top", $(obj).position().top+ $(obj).outerHeight() + "px");
				}
				popsparm.css("left",$(obj).position().left+ "px");
				popsparm.find(".pops-select-body").css("max-height", maxH + "px");
			}
		},
		hideSelect: function(elem) { //隐藏选择区域
			var popsparm = elem.data("popsparm");
			var options = elem.data("options");
			popsparm.hide();
			methods.searchItems(elem.data("options"));

		},
		hideAll: function() { //隐藏所有选择区域
			$(".pops-select").each(function() {
				if($(this).parents(".pops-bg").length > 0) {
					$(this).parents(".pops-bg").hide();
				} else {
					$(this).hide();
				}
			});
		},
		getSearchHtml: function() { //获取搜索html结构
			var html = [];
			html.push('<div class="pops-search">');
			html.push('	<input type="text" class="pops-search-input"  placeholder="输入关键字"/>');
			//	html.push('	<button type="button" class="pops-search-btn">搜索</button>');
			html.push('	<i class="i i-fangdajing pops-search-btn"></i>');
			html.push('</div>');
			return html.join(" ");
		},
		getAddBtnHtml: function() { //获取按钮html结构
			var html = [];
			html.push('<div class="pops-addbtn"><i></i>');
			html.push('	<button type="button" class="pops-addbtn-btn"></button>');
			html.push('</div>');
			return html.join(" ");
		},
		getInlineSelectHtml: function() { //获取内联html结构
			var html = [];
			html.push('<div class="pops-select">');
			html.push('	<div class="pops-select-head">');
			html.push('	</div>');
			html.push('	<div class="pops-select-body">');
			html.push('	</div>');
			html.push('	<div class="pops-select-foot">');
			html.push('	</div>');
			html.push('</div>');
			return html.join(" ");
		},
		getInlineItemsHtml: function(items) { //获取内联选择项html结构
			var html = [];
			html.push('	<ul class="pops-items">');
			$.each(items, function(i, node) {
					html.push('	<li class="pops-item" data-id="' + node.id + '">');
					if(typeof(node.id)!="undefined"&&typeof(node.name)!="undefined"){
						html.push('<span>' + node.name + '</span>');
					}else{
						html.push('<span>' + node.id + '</span>');
					}
					html.push('	</li>');
			});
			html.push('	</ul>');
			return html.join(" ");
		},
		getOutlineSelectHtml: function(options) { //获取pophtml结构
			var html = [];
			html.push('<div class="pops-bg">');
			html.push('<div class="pops-select" >');
			html.push('	<div class="pops-select-head">');
			html.push('	<i class="pops-close"></i>');
			html.push('	</div>');
			html.push('	<div class="pops-select-search">');
			html.push('	</div>');
			if(options.multiple) {
				html.push('	<div class="pops-select-body has-confirm">');
				html.push('	</div>');
				html.push('	<div class="pops-select-foot">');
				html.push('	<button type="button" class="pops-btn-confirm">确认</button>');
				html.push('	</div>');
			} else {
				html.push('	<div class="pops-select-body">');
				html.push('	</div>');
			}
			html.push('</div>');
			html.push('</div>');
			return html.join(" ");
		},
		getOutlineItemsHtml: function(items, options) { //获取pop选择项html结构
			var html = [];
			var count = 0;
			html.push('	<table class="pops-items">');
			if(options.groupFlag) {
				$.each(items, function(i, node) {
					$.each(node.items, function(j, subNode) {
						if(j % options.cellCount == 0) {
							html.push('	<tr class="pops-item-tr ');
							if(j == 0) {
								html.push('group-tr');
							}
							html.push('">');
						}
						if(j == 0) {
							html.push('	<td class="group-td" rowspan="' + Math.ceil(node.items.length / options.cellCount) + '">');
							html.push('	<span class="group-td-name">');
							html.push(node.groupName);
							html.push(':	</span>');
							html.push('	</td>');
						}

						count++;
						html.push('	<td class="pops-item" data-id="' + subNode.id + '">');
						if(options.multiple) {
							html.push('<label title="' + subNode.name + '"><input  data-id="' + subNode.id + '" type="checkbox" class="option-input checkbox" /><span class="name-span">' + (typeof(subNode.name)=="undefined"?subNode.id:subNode.name) + '</span><i class="i i-sanjiaoduihao"></i></label>');
						} else {
							html.push('<label title="' + subNode.name + '" class="label-radio"><input  data-id="' + subNode.id + '" type="radio" class="option-input option-radio checkbox" /><span class="radio-span"><span class="i i-radio2"></span><span class="i i-radio1"></span></span><span class="name-span">' + (typeof(subNode.name)=="undefined"?subNode.id:subNode.name) + '</span></label>');
						}
						html.push('	</td>');
						if(count % options.cellCount == 0) {
							count = 0;
							html.push('	</tr>');
						}
					});
				});
			} else {
				$.each(items, function(i, node) {
					if(i % options.cellCount == 0) {
						html.push('	<tr class="pops-item-tr">');
					}
					count++;
					html.push('	<td class="pops-item" data-id="' + node.id + '">');
					if(options.multiple) {
						html.push('<label title="' + node.name + '"><input  data-id="' + node.id + '" type="checkbox" class="option-input checkbox" /><span class="name-span">' + (typeof(node.name)=="undefined"?node.id:node.name) + '</span><i class="i i-sanjiaoduihao"></i></label>');
					} else {
						html.push('<label title="' + node.name + '" class="label-radio"><input  data-id="' + node.id + '" type="radio" class="option-input option-radio checkbox" /><span class="radio-span"><span class="i i-radio2"></span><span class="i i-radio1"></span></span><span class="name-span">' + (typeof(node.name)=="undefined"?node.id:node.name)  + '</span></label>');
					}
					html.push('	</td>');
					if(count % options.cellCount == 0) {
						count = 0;
						html.push('	</tr>');
					}
				});
			}
			html.push('	</table>');
			return html.join(" ");
		},
		initEvent: function(elem) { //初始化事件
			$(win).bind("click", function(e) {
				if(!$(e.target).hasClass("pops-value") && !$(e.target).hasClass("pops-select") && $(e.target).parents(".pops-select").length == 0 && $(e.target).parents(".pops-value").length == 0) {
					methods.hideAll();
				}
			});
		},
		setSelectValue: function(options, elem, id) { //赋值

			var popsvalue = elem.data("popsvalue");
			var popsparm = elem.data("popsparm");
			var itemMap = options.itemMap;
			var changeCallback = options.changeCallback;
			if(options.multiple) {
				var node = popsparm.find(".pops-item[data-id=" + id + "]");
				if(node.hasClass("on")) {
					node.removeClass("on");
					popsvalue.find(".pops-label[data-id=" + id + "]").remove();
				} else {
					node.addClass("on");
					var label = $(methods.getMultipleHtml(node, options));
					label.appendTo(popsvalue);
					methods.setInlineLabelEvent(label, options);
				}
				if(popsparm.find(".pops-item").not(".on").length == 0) {
					methods.hideSelect(elem);
				}else if(!options.initFlag){
					methods.showSelect(popsvalue,elem);
				}
				methods.setElemValue(options, elem);
				//methods.hideSelect(elem);
			} else {
				if(itemMap[id]&&itemMap[id].name){
					popsvalue.html(itemMap[id].name);
					elem.data("text", itemMap[id].name);
				}
				elem.val(id);
				elem.data("values").val(id);
				methods.hideSelect(elem);
				if(!options.initFlag) {
					if(changeCallback && typeof(changeCallback) == "function") {
						changeCallback.call(this, elem);
					}
				}
			}
		},
		clearOneValue: function(options, node) { //清除一个选项
			var popsparm = options.elem.data("popsparm");
			popsparm.find(".pops-label[data-id=" + $(node).data("id") + "]").remove();
		},
		clearValue: function(options, elem) { //清除所有选项
			var popsparm = elem.data("popsparm");
			popsparm.find(".pops-label").remove();
			popsparm.find(".option-input").prop("checked", false);
		},
		getMultipleHtml: function(node, options) { //构建选中选择项label
			var itemMap = options.itemMap;
			var html = [];
			if(options.labelShow) {
				html.push('<div class="pops-label" data-id="' + $(node).data("id") + '">');
			} else {
				html.push('<div class="pops-label off" data-id="' + $(node).data("id") + '">');
			}
			html.push('<div class="pops-label-alt">');
			html.push(itemMap[$(node).data("id")].name);
			html.push('</div>');
			html.push('	<i class="pops-close"></i>');
			if(options.labelEdit && typeof(options.labelEdit) == "function") {
				html.push('	<i class="pops-edit"></i>');
			}
			html.push('</div>');
			return html.join(" ");
		},
		setChooseValue: function(options, elem, node) { //设置pop选中值
			var popsparm = elem.data("popsparm");
			var itemMap = options.itemMap;
			if(options.multiple) {
				if(node.prop("checked")) {
					if(popsparm.find(".pops-select-search .pops-label[data-id=" + node.data("id") + "]").length > 0) return false;
					var label = $(methods.getMultipleHtml(node, options));
					label.appendTo(popsparm.find(".pops-select-search"));
					methods.setLabelEvent(label, options);
					popsparm.find(".pops-item[data-id=" + node.data("id") + "]").addClass("pitch");
				} else {
					methods.clearOneValue(options, node);
					popsparm.find(".pops-item[data-id=" + node.data("id") + "]").removeClass("pitch");
				}
			} else {
				if(node.prop("checked")) {
					methods.clearValue(options, elem);
					popsparm.find(".pops-item").removeClass("pitch");
					var label = $(methods.getMultipleHtml(node, options));
					label.appendTo(popsparm.find(".pops-select-search"));
					methods.setLabelEvent(label, options);
					popsparm.find(".option-input[data-id=" + node.data("id") + "]").prop("checked", true);
					popsparm.find(".pops-item[data-id=" + node.data("id") + "]").addClass("pitch");
				} else {
					methods.clearOneValue(options, node);
				}
				methods.confirmValue(options, elem);
			}
		},
		setChooseInitValue: function(options, elem, node) { //设置pop选中值
			var popsparm = elem.data("popsparm");
			var label = $(methods.getMultipleHtml(node, options));
			label.appendTo(popsparm.find(".pops-select-search"));
			methods.setLabelEvent(label, options);
			popsparm.find(".option-input[data-id=" + node.data("id") + "]").prop("checked", true);
			popsparm.find(".pops-item[data-id=" + node.data("id") + "]").addClass("pitch");
		},
		setInlineLabelEvent: function(label, options) { //label绑定事件
			var changeCallback = options.changeCallback;
			label.find(".pops-close").bind("click", function() {
				var popsparm = options.elem.data("popsparm");
				popsparm.find(".pops-item[data-id=" + $(this).parent().data("id") + "]").removeClass('on').show();
				$(this).parent().remove();
				methods.resetSelect(options.elem);
				methods.setElemValue(options, options.elem);
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, options.itemMap[$(this).parent().data("id")]);
				return false;
			});
		},
		setLabelEvent: function(label, options) { //label绑定事件
			label.find(".pops-close").bind("click", function() {
				var popsparm = options.elem.data("popsparm");
				var dataId = $(this).parent().data("id");
				popsparm.find(".option-input[data-id=" + dataId + "]").prop("checked", false);
				popsparm.find(".pops-label[data-id=" + dataId + "]").remove();
				$(this).parent().remove();
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, options.itemMap[$(this).parent().data("id")]);
				return false;
			});
		},
		setValueLabelEvent: function(label, options) { //label绑定事件
			label.find(".pops-close").bind("click", function() {
				var popsparm = options.elem.data("popsparm");
				var dataId = $(this).parent().data("id");
				popsparm.find(".option-input[data-id=" + dataId + "]").prop("checked", false);
				popsparm.find(".pops-label[data-id=" + dataId + "]").remove();
				$(this).parent().remove();
				methods.confirmValue(options, options.elem);
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, options.itemMap[$(this).parent().data("id")]);
				return false;
			});
		},
		confirmValue: function(options, elem) { //确认pop选择值
			var popsvalue = elem.data("popsvalue");
			var popsparm = elem.data("popsparm");
			var itemMap = options.itemMap;
			popsvalue.empty();
			//if(options.multiple){
			popsparm.find(".pops-label").each(function(i, node) {
				var label = $(methods.getMultipleHtml(node, options));
				label.appendTo(popsvalue);
				methods.setValueLabelEvent(label, options);
			});
			//}else{

			//}
			methods.hideSelect(elem);
			methods.setElemValue(options, elem);
		},
		setElemValue: function(options, elem) { //对象隐藏域赋值
			var popsvalue = elem.data("popsvalue");
			var val = "";

			var changeCallback = options.changeCallback;
			popsvalue.find(".pops-label").each(function(i, node) {
				if(options.multiple) {
					val += $(node).data("id") + options.splitStr;
				} else {
					val = $(node).data("id");
				}
			});
			if(val.length>0){//去掉最后面的逗号
				val=val.substring(0,val.length-1);
			}
			elem.data("values").val(val);
			if(!options.initFlag) {
				if(changeCallback && typeof(changeCallback) == "function") {
					changeCallback.call(this, elem);
				}
			}
		},
		itemsToJson: function(options) { //数组转json
			var tempMap = {};
			var items = options.items;
			if(options.groupFlag) {
				$.each(items, function(i, node) {
					$.each(node.items, function(j, subNode) {
						tempMap[subNode.id] = subNode;
					});
				});
			} else {
				$.each(items, function(i, node) {
					tempMap[node.id] = node;
				});
			}
			options.itemMap = tempMap;
		},
		searchItems: function(options, kw) { //搜索
			var tempItemsMap = {};
			var popsparm = options.elem.data("popsparm");
			if(kw && kw.trim()) {
				$.each(options.items, function(i, node) {
					if(node.name&&node.name.indexOf(kw) > -1) {
						tempItemsMap[node.id] = node;
					}
				});
			} else {
				tempItemsMap = options.itemMap;
				if(popsparm.find(".pops-search-input").length > 0) {
					popsparm.find(".pops-search-input").val("");
				}
			}
			if(options.inline) {
				popsparm.find(".pops-select-body .pops-item").each(function(i, obj) {
					if(tempItemsMap[$(obj).data("id")] && !$(obj).hasClass("on")) {
						$(obj).show();
					} else {
						$(obj).hide();
					}
				})

				options.elem.data("popsparm", popsparm);
			} else {
				popsparm.find(".pops-item-tr").hide();
				popsparm.find(".pops-select-body .pops-item").each(function(i, obj) {

					if(tempItemsMap[$(obj).data("id")]) {
						$(obj).show();
						$(obj).parent().show();
					} else {
						$(obj).hide();
					}
				})
				options.elem.data("popsparm", popsparm);
			}
		},
		addItem: function(node) { //添加一个一选项
			this.each(function() {
				var $this = $(this);
				var options = $this.data("options");
				var popsparm = $this.data("popsparm");
				options.items.push(node);
				methods.itemsToJson(options);
				if(options.inline) {
					var html = [];
					html.push('	<li class="pops-item" data-id="' + node.id + '">');
					html.push('<span>' + node.name + '</span>');
					html.push('	</li>');
					var temp = $(html.join(" "));
					popsparm.find(".pops-items").append(temp);
					temp.bind("click", function() {
						methods.setSelectValue(options, $this, $(this).data("id"));
					});
				} else {
					var html = [];
					var len = options.items.length - 1;
					if(len % 3 == 0) {
						html.push('	<tr class="pops-item-tr">');
						html.push('	<td class="pops-item" data-id="' + node.id + '">');
						if(options.multiple) {
							html.push('<label><input  data-id="' + node.id + '" type="checkbox" class="option-input checkbox" /><span class="name-span">' + node.name + '</span></label>');
						} else {
							html.push('<label class="label-radio"><input  data-id="' + node.id + '" type="radio" class="option-input option-radio checkbox" /><span class="radio-span"><span class="i i-radio2"></span><span class="i i-radio1"></span></span><span class="name-span">' + node.name + '</span></label>');
						}
						html.push('	</td>');
						html.push('	</tr>');
						var temp = $(html.join(" "));
						popsparm.find(".pops-items tbody").append(temp);
						temp.find(".pops-item").bind("click", function() {
							methods.setChooseValue(options, $this, $(this).find(".option-input"));
						});
					} else {
						html.push('	<td class="pops-item" data-id="' + node.id + '">');
						if(options.multiple) {
							html.push('<label><input  data-id="' + node.id + '" type="checkbox" class="option-input checkbox" /><span class="name-span">' + node.name + '</span></label>');
						} else {
							html.push('<label class="label-radio"><input  data-id="' + node.id + '" type="radio" class="option-input option-radio checkbox" /><span class="radio-span"><i class="i i-radio2"></i><i class="i i-radio1"></i></span><span class="name-span">' + node.name + '</span></label>');
						}
						html.push('	</td>');
						var temp = $(html.join(" "));
						popsparm.find(".pops-items .pops-item-tr").eq(popsparm.find(".pops-items .pops-item-tr").length - 1).append(temp);
						temp.bind("click", function() {
							methods.setChooseValue(options, $this, $(this).find(".option-input"));
						});
					}

				}
			});
		},
		updateItems: function(items) { //更新选项
			this.each(function() {
				var $this = $(this);
				var options = $this.data("options");
				var popsparm = $this.data("popsparm");
				options.items = items;
				methods.itemsToJson(options);
				$this.data("items", options.items);
				methods.buildSelectValue($this, options);
				methods.initEvent($this);
				methods.itemsToJson(options);
				methods.clearnValue($this,options);
			});

		},
		clearnValue: function(elem,options) { //清空选项
			var popsvalue = elem.data("popsvalue");
			popsvalue.empty();
			elem.data("values").val("");
		},
		selectedItem: function(item) { //选中一个一选项
			this.each(function() {
				var $this = $(this);
				var options = $this.data("options");
				var popsparm = $this.data("popsparm");
				var popsvalue = $this.data("popsvalue");
				var changeCallback = options.changeCallback;
				if(options.inline) {
					if(options.multiple) {
						var node = popsparm.find(".pops-item[data-id=" + item.id + "]");
						if(node.hasClass("on")) {
							node.removeClass("on");
							popsvalue.find(".pops-label[data-id=" + item.id + "]").remove();
						} else {
							node.addClass("on");
							var label = $(methods.getMultipleHtml(node, options));
							label.appendTo(popsvalue);
							methods.setInlineLabelEvent(label, options);
						}
						methods.setElemValue(options, $this);
					} else {
						popsvalue.html(item.name);
						$this.val(item.id);
						$this.data("text", item.name);
						$this.data("values").val(item.id);
						methods.hideSelect($this);
						if(!options.initFlag) {
							if(changeCallback && typeof(changeCallback) == "function") {
								changeCallback.call(this, $this);
							}
						}
					}
				} else {
					if(options.multiple) {
						var node = popsparm.find('.pops-item input[data-id=' + item.id + ']');
						node.prop("checked", true);
						if(popsparm.find(".pops-select-search .pops-label[data-id=" + node.data("id") + "]").length > 0) return false;
						var label = $(methods.getMultipleHtml(node, options));
						label.appendTo(popsparm.find(".pops-select-search"));
						methods.setLabelEvent(label, options);
						popsparm.find(".pops-item[data-id=" + node.data("id") + "]").addClass("pitch");

					} else {
						var node = popsparm.find('.pops-item input[data-id=' + item.id + ']');
						node.prop("checked", true);
						methods.clearValue(options, $this);
						popsparm.find(".pops-item").removeClass("pitch");
						methods.setSelectValue(options, $this, node.data("id"));
					}
				}

			});
		},
		popTreeInit: function(options, elem) { //ztree初始化
			var poptree = $(methods.popTreeHtml(options));
			elem.data("tree", poptree);
			$("body").append(poptree);
			var defaultsSetting = {
				callback: {
					onClick: methods.zTreeOnClick
				},
				check: {
					enable: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				view: {
					fontCss: methods.getFont
				}
			}
			if(options.multiple) {
				defaultsSetting.check.enable = true;
				poptree.find(".pops-btn-confirm").bind("click", function() {
					methods.setTreeMultiple(options, elem);
				});
			}
			var popSetting = $.extend(defaultsSetting, options.ztreeSetting);
			var ztreeId = elem.attr("id") ? elem.attr("id") : elem.attr("name");
			var timestamp=new Date().getTime();
			poptree.find(".ztree").attr("id", "poptree-" + timestamp);
			elem.data("ztreeId", "poptree-" + timestamp);
			var zNodes = $.extend(true, [], options.items);
			options.zNodes = zNodes;
			options.popSetting = popSetting;
			var ztreeObj = $.fn.zTree.init(poptree.find(".ztree"), popSetting, zNodes);
			elem.data("ztreeObj", ztreeObj);
			if(options.title && typeof(options.title) == "string") {
				poptree.find(".pops-tree-title").html(options.title);
			}
			if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
				var addBtn = $(methods.getTreeAddBtnHtml());
				poptree.find(".pops-tree-foot").append(addBtn);
				addBtn.text(options.addBtn.title);
				if(options.addBtn.fun && typeof(options.addBtn.fun) == "function") {
					addBtn.bind("click", function() {
						options.addBtn.fun.call(this, ztreeObj, elem);
					});
				}
			}
			if(options.searchOn) {
				var search = $(methods.getTreeSearchHtml());
				poptree.find(".pops-tree-search").append(search);
				methods.popTreeInitSearchEvent(options, elem);
				if(typeof($.fn.placeholder) == "function") {
					search.find("input").placeholder({
						isUseSpan: true,
						all: false
					});
				}
			}

			poptree.bind("click", function(e) {
				if(!$(e.target).hasClass("pops-value") && !$(e.target).hasClass("pops-tree") && $(e.target).parents(".pops-tree").length == 0 && $(e.target).parents(".pops-value").length == 0) {
					methods.popTreeHide();
				}
			});
			poptree.find(".pops-close").bind("click", function(e) {
				methods.popTreeHide();
			});
			poptree.data("elem", elem);
			options.initFlag = false;
			methods.startDrag(poptree.find('.pops-tree-head')[0], poptree.find('.pops-tree')[0], options)
		},
		initTreeValue: function(elem, options) { //赋初值
			var val = elem.val();
			var ztreeObj = elem.data("ztreeObj");
			var arr = [];
			if(val && val.indexOf(options.splitStr) > -1) {
				arr = val.split(options.splitStr);
				arr = $.grep(arr, function(n) {
					return $.trim(n).length > 0;
				})
			} else if(val) {
				arr.push(val);
			}
			if(!options.multiple) {
				var nodes = ztreeObj.getNodesByParam("id", arr[0], null);
				if(nodes.length > 0) {
					var treeNode = nodes[0];
					var label = $(methods.getTreeLabelHtml(treeNode, options));
					label.appendTo(elem.data("popsvalue"));
					$(elem).data("treeNode", treeNode);
					methods.setTreeLabelEvent(label, options);
					methods.setElemValue(options, elem);
				}
			} else {
				var zNode = [];
				for(var i = 0; i < arr.length; i++) {
					var nodes = ztreeObj.getNodesByParam("id", arr[i], null);
					if(nodes.length > 0) {
						nodes[0].checked = true;
						ztreeObj.updateNode(nodes[0]); //设置checked属性之后，更新该节点，否则会出现只有鼠标滑过的时候节点才被选中的情况
						zNode.push(nodes[0]);
					}
				}

				$.each(zNode, function(i, node) {
					var label = $(methods.getTreeLabelHtml(node, options));
					label.appendTo(elem.data("popsvalue"));
					methods.setTreeLabelEvent(label, options);
				});
				$(elem).data("treeNode", zNode);
				methods.setElemValue(options, elem);
				methods.popTreeHide(elem);
			}
			options.initFlag = false;
		},
		showTreeValueDiv: function(elem, options) { //页面显示值区域
			var div = $('<div class="pops-value"></div>');
			var poptree = elem.data("tree");
			if(options.valueClass && typeof(options.valueClass) == "string") {
				div.addClass(options.valueClass);
			}
			div.css("min-height", elem.outerHeight());
			div.insertAfter(elem);
			div.bind("click", function() {
				if (elem.prop("readonly")||elem.attr("readonly")=="readonly") {
					return false;
				}
				$(".pops-select").hide();
				poptree.find(".pops-tree").css({
					"left": "",
					"right": "",
					"margin": "0 auto",
					"margin-top": "5%"
				})
				poptree.show();
			});
			if(options.handle) {
				$(options.handle).bind("click", function() {
					$(".pops-select").hide();
					poptree.show();
					return false;
				});
			}
			elem.data("popsvalue", div);
			elem.css("display", "none");
			methods.initTreeValue(elem, options);
			options.initFlag = false;
		},
		popTreeHtml: function(options) {
			var html = [];
			html.push('<div class="pops-bg">');
			html.push('<div class="pops-tree">');
			html.push('	<div class="pops-tree-head">');
			html.push('	<span class="pops-tree-title"></span>');
			html.push('	<i class="pops-close"></i>');
			html.push('	</div>');
			html.push('	<div class="pops-tree-search">');
			html.push('	</div>');
			html.push('	<div class="pops-tree-body">');
			html.push('	<ul class="pops-tree-ztree ztree">');
			html.push('	</ul>');
			html.push('	</div>');
			html.push('	<div class="pops-tree-foot">');
			if(options.multiple) {
				html.push('	<button type="button" class="pops-btn-confirm">确认</button>');
			}
			html.push('	</div>');
			html.push('</div>');
			html.push('</div>');
			return html.join(" ");
		},
		getTreeLabelHtml: function(node, options) {
			var html = [];
			html.push('<div class="pops-label" data-id="' + node.id + '">');
			html.push('<div class="pops-label-alt">');
			html.push(node.name);
			html.push('</div>');
			html.push('	<i class="pops-close"></i>');
			if(options.labelEdit && typeof(options.labelEdit) == "function") {
				html.push('	<i class="pops-edit"></i>');
			}
			html.push('</div>');
			return html.join(" ");
		},
		popTreeHide: function(elem) {
			$(".pops-bg").hide();
		},
		getTreeSearchHtml: function() { //获取搜索html结构
			var html = [];
			html.push('<div class="pops-search">');
			html.push('	<input type="text" class="pops-search-input"  placeholder="输入关键字"/>');
			html.push('	<i class="i i-fangdajing pops-search-btn"></i>');
			html.push('</div>');
			return html.join(" ");
		},
		getTreeAddBtnHtml: function() { //获取搜索html结构
			var html = [];
			html.push('	<button type="button" class="pops-tree-addbtn"></button>');
			return html.join(" ");
		},
		popTreeInitSearchEvent: function(options, elem) {
			var poptree = elem.data("tree");
			poptree.find(".pops-search-btn").bind("click", function() {
				var val = poptree.find(".pops-search-input").val();
				methods.searchTreeNode(options, elem, val);
			});
		},
		popTreeInitAddBtnEvent: function(options, elem) {
			var poptree = elem.data("tree");
			poptree.find(".pops-addbtn").bind("click", function() {
				console.log("新增按钮");
			});
		},
		showTreeNode: function(nodes, poptree) {
			$.each(nodes, function(i, node) {
				var parNode = node.getParentNode();
				poptree.find(".pops-tree-ztree #" + node.tId).show();
				while(parNode) {
					poptree.find(".pops-tree-ztree #" + parNode.tId).show();
					parNode = parNode.getParentNode();
				}
			});
		},
		searchTreeNode: function(options, elem, kw) {
			var poptree = elem.data("tree");
			var ztreeObj = $.fn.zTree.init(poptree.find(".ztree"), options.popSetting, options.zNodes);
			if(!kw || kw == "" || typeof(kw) == "undefined") {
				ztreeObj.expandAll(false);
				elem.data("ztreeObj", ztreeObj)
			} else {
				var nodes = ztreeObj.getNodesByParamFuzzy("name", kw, null);
				var pnodes = [];
				$.each(nodes, function(i, node) {
					node.font = {
						'color': 'red'
					};
					node.open = true;
					//node.children = [];
					var pnode = node.getParentNode();
					if(pnode != null) {
						pnode.open = true;
						pnode.children = [];
						if($.inArray(pnode, pnodes) == -1 && $.inArray(pnode, nodes) == -1) {
							pnodes.push(pnode);
						}
					}

				})
				var zNodes = $.merge(nodes, pnodes);
				ztreeObj = $.fn.zTree.init(poptree.find(".ztree"), options.popSetting, zNodes);
				elem.data("ztreeObj", ztreeObj)
			}
			//poptree.find(".pops-tree-ztree li").hide();

			//methods.showTreeNode(nodes, poptree);
		},
		getFont: function(treeId, node) {
			return node.font ? node.font : {};
		},
		setTreeLabelEvent: function(label, options) { //label绑定事件
			label.find(".pops-close").bind("click", function() {
				$(this).parent().remove();
				methods.setElemValue(options, options.elem);
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, options.itemMap[$(this).parent().data("id")]);
				return false;
			});
		},
		zTreeOnClick: function(event, treeId, treeNode) {
			var poptree = $(event.target).parents(".pops-bg");
			var elem = poptree.data("elem");
			var options = elem.data("options");
			var popsvalue = elem.data("popsvalue");
			if(!treeNode.isParent||options.parentSelect) {
				//		if(!treeNode.children || treeNode.children.length == 0) {
				if(options.multiple) {

				} else {
					popsvalue.empty();
					var label = $(methods.getTreeLabelHtml(treeNode, options));
					label.appendTo(popsvalue);
					$(elem).data("treeNode", treeNode);
					methods.setTreeLabelEvent(label, options);
					methods.setElemValue(options, elem);
					methods.popTreeHide(elem);
				}
			}
		},
		setTreeMultiple: function(options, elem) {
			var treeObj = elem.data("ztreeObj");
			var popsvalue = elem.data("popsvalue");
			var nodes = treeObj.getCheckedNodes(true);
			popsvalue.empty();
			var zNode = [];
			$.each(nodes, function(i, node) {
				if(!node.isParent||options.parentSelect) {
					var label = $(methods.getTreeLabelHtml(node, options));
					zNode.push(node);
					label.appendTo(popsvalue);
					methods.setTreeLabelEvent(label, options);
				}
			});
			$(elem).data("treeNode", zNode);
			methods.setElemValue(options, elem);
			methods.popTreeHide(elem);
		},
		selectedTreeItem: function(zNewNode) {
			this.each(function() {
				var $this = $(this);
				var poptree = $this.data("tree");
				var options = $this.data("options");
				var treeObj = $this.data("ztreeObj");
				var popsvalue = $this.data("popsvalue");
				if(options.multiple) {
					zNewNode.checked = true;
					var nodes = treeObj.getCheckedNodes(true);
					popsvalue.empty();
					var zNode = [];
					$.each(nodes, function(i, node) {
						if(!node.isParent || options.parentSelect) {
							var label = $(methods.getTreeLabelHtml(node, options));
							zNode.push(node);
							label.appendTo(popsvalue);
							methods.setTreeLabelEvent(label, options);
						}
					});
					$this.data("treeNode", zNode);
					methods.setElemValue(options, $this);
				} else {
					popsvalue.empty();
					var label = $(methods.getTreeLabelHtml(zNewNode, options));
					label.appendTo(popsvalue);
					$this.data("treeNode", zNewNode);
					methods.setTreeLabelEvent(label, options);
					methods.setElemValue(options, $this);
				}

			});
		},
		updateTreeItems: function(obj) { //更新树选项
			this.each(function() {
				var $this = $(this);
				var options = $this.data("options");
				var popsparm = $this.data("popsparm");
				var ztreeId  = $this.data("ztreeId")
				var popsvalue = $this.data("popsvalue");
				var popSetting = options.popSetting;
				var poptree = $this.data("tree");
				var ztreeObj;
				if(obj.ajaxUrl && typeof(obj.ajaxUrl) == "string") {
					$.ajax({
						type: "get",
						url: obj.ajaxUrl,
						datatype: "json",
						async: true,
						success: function(data) {
							if(data.items&&typeof(data.items) == "string") {
								options.items = JSON.parse(data.items);
							} else {
								options.items = data.items;
							}
							$this.data("items", options.items);
							//methods.popTreeInit(options, $this);
							$.fn.zTree.destroy(ztreeId);
							ztreeObj = $.fn.zTree.init(poptree.find(".ztree"), popSetting, options.items);
							popsvalue.remove();
							methods.showTreeValueDiv($this, options);
							methods.itemsToJson(options);
							options.zNodes=options.items;
							$this.data("options",  options);
							methods.popTreeInitSearchEvent(options, $this);
						},
						error: function() {
							console.log("数据源加载失败");
						}
					});
				}else if(typeof(obj)=="object"){
					$.fn.zTree.destroy(ztreeId);
					options.items = obj;
					$this.data("items", options.items);
					ztreeObj = $.fn.zTree.init(poptree.find(".ztree"), popSetting, obj);
					popsvalue.remove();
					methods.showTreeValueDiv($this, options);
					methods.itemsToJson(options);
					options.zNodes=options.items;
					$this.data("options",  options);
					methods.popTreeInitSearchEvent(options, $this);
				}
				$this.data("ztreeObj",ztreeObj);
			});

		},
		getCss: function(o, key) {
			return o.currentStyle ? o.currentStyle[key] : document.defaultView.getComputedStyle(o, false)[key];
		},
		startDrag: function(bar, target, options, callback) { //拖拽* bar 触发拖拽对象* target 被拖拽对象
			var oDiv;
			//target是移动对象
			bar.onmousedown = function(event) {
				if(methods.getCss(target, "left") !== "auto") {
					options.dragParams.left = methods.getCss(target, "left");
				}
				if(methods.getCss(target, "top") !== "auto") {
					options.dragParams.top = methods.getCss(target, "top");
				}
				var dragDiv = document.getElementById("dragDivCell");
				if(dragDiv != null) {
					dragDiv.parentNode.removeChild(dragDiv);
				}
				options.dragParams.flag = true;
				if(!event) {
					event = window.event;
					//防止IE文字选中
					bar.onselectstart = function() {
						return false;
					};
					document.body.onselectstart = function() {
						return false;
					};
				}
				var e = event;
				options.dragParams.currentX = e.clientX;
				options.dragParams.currentY = e.clientY;
				document.onmouseup = function() {
					var dragDiv = document.getElementById("dragDivCell");
					if(dragDiv != null) {
						dragDiv.parentNode.removeChild(dragDiv);
					}
					if(target.offsetLeft <= 0) {
						target.style.left = "0px";
						target.style.marginLeft = "0px";
					}
					if(target.offsetTop <= 0) {
						target.style.top = "0px";
						target.style.marginTop = "0px";
					}
					if((document.body.clientHeight - target.offsetTop) <= bar.offsetHeight) {
						target.style.marginTop = "0px";
						target.style.top = (document.body.clientHeight - bar.offsetHeight) + "px";
					}
					if((document.body.clientWidth - target.offsetLeft) <= 50) {
						target.style.marginLeft = "0px";
						target.style.left = (document.body.clientWidth - 50) + "px";
					}
					options.dragParams.flag = false;
					if(methods.getCss(target, "left") !== "auto") {
						options.dragParams.left = methods.getCss(target, "left");
					}
					if(methods.getCss(target, "top") !== "auto") {
						options.dragParams.top = methods.getCss(target, "top");
					}
				};
				document.onmousemove = function(event) {
					var e = event ? event : window.event;
					if(options.dragParams.flag) {
						var nowX = e.clientX,
							nowY = e.clientY;
						var disX = nowX - options.dragParams.currentX,
							disY = nowY - options.dragParams.currentY;
						var returnX = 0,
							returnY = 0;
						target.style.left = parseInt(options.dragParams.left) + disX + "px";
						returnX = parseInt(options.dragParams.left) + disX;

						target.style.top = parseInt(options.dragParams.top) + disY + "px";
						returnY = parseInt(options.dragParams.top) + disY;
					}

					if(typeof callback == "function") {
						callback(returnX, returnY);
					}
				};
			};
		}
	};
	$.fn.popupSelection = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.popupSelection');
		}
	};
})(jQuery, window);
var popupSelectionJsPath = document.scripts[document.scripts.length - 1].src;
var popupSelectionPath = popupSelectionJsPath.substring(0, popupSelectionJsPath.lastIndexOf("/") + 1);
var popupSelectionlink = document['createElement']('link');
popupSelectionlink.type = 'text/css';
popupSelectionlink.rel = 'stylesheet';
popupSelectionlink.href = popupSelectionPath + "popupSelection.css";
document['getElementsByTagName']('head')[0].appendChild(popupSelectionlink);
popupSelectionlink = null;