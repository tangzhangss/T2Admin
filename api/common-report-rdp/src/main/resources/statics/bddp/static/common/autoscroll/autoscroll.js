/**
 * 滚动组件的实现
 * @param {Object} $
 */
(function ($) {
	/**
	 * 实现滚动方法
	 * @param {Object} options
	 */
	$.fn.beginScroll = function (options) {
		//默认配置
		var defaults = {
			speed: 40, //滚动速度,值越大速度越慢
			rowHeight: 24 //每行的高度
		};
		//对象赋值
		var opts = $.extend({}, defaults, options),
			intId = [];

		function marquee(obj, step) {
			obj.find("ul").animate({
				marginTop: '-=1'
			}, 0, function () {
				var s = Math.abs(parseInt($(this).css("margin-top")));
				if (s >= step) {
					$(this).find("li").slice(0, 1).appendTo($(this));
					$(this).css("margin-top", 0);
				}
			});
		}
		this.each(function (i) {
			var sh = opts["rowHeight"],
				speed = opts["speed"],
				_this = $(this);
			intId[i] = setInterval(function () {
				if (_this.find("ul").height() <= _this.height()) {
					clearInterval(intId[i]);
				} else {
					marquee(_this, sh);
				}
			}, speed);

			_this.hover(function () {
				clearInterval(intId[i]);
			}, function () {
				intId[i] = setInterval(function () {
					if (_this.find("ul").height() <= _this.height()) {
						clearInterval(intId[i]);
					} else {
						marquee(_this, sh);
					}
				}, speed);
			});

		});

	}

	/**
	 * 数据表格
	 * @param {Object} options
	 */
	$.fn.initScroll = function (options) {
		var columnData = options.columnData;
		var data = options.ajax_data.data;
		var _this =$(this);
		var w = 100/columnData.length;
		_this.find('.kgo-scroll-head').empty();
		_this.find('.kgo-scroll-body-ul').empty();
		$.each(columnData, function (index, item) {
			_this.find('.kgo-scroll-head').append('<div class="baseStyle croll-width" style="width:'+w+'%;" name="'+item.key+'">'+item.text+'</div>');
		});
		$.each(data, function (index, item) {
			var $li = $('<li></li>');
			if(options["tableHeight"]){
				$li.css("height",options["tableHeight"]);
			}
			_this.find('.kgo-scroll-body-ul').append($li);
			$.each(_this.find('.kgo-scroll-head > div'),function(i,th){
				var key = $(th).attr("name");
				var span_dom =  $(th).clone();
				span_dom.removeClass("baseStyle").addClass("baseStyle-data");
				span_dom.text(item[key]);
				_this.find('.kgo-scroll-body-ul').find('li:last-child').append(span_dom);
			})
		});
	}
	/**
	 * 行点击事件
	 * @param {Object} callback
	 */
	$.fn.rowOnclick = function (callback) {
		var _this =$(this);
		_this.on('click','.kgo-scroll-body>ul>li', function () {
			var scroll_obj = {};
			$(this).find('div').each(function (index, item) {
				scroll_obj[$(this).attr('name')] = $(this).text();
			});

			callback(scroll_obj);
		});
	}
	/**
	 * 行双击事件
	 * @param {Object} callback
	 */
	$.fn.rowOnDbclick = function (callback) {
		var _this =$(this);
		_this.on('dblclick','.kgo-scroll-body>ul>li', function () {
			var scroll_obj = {};
			$(this).find('div').each(function (index, item) {
				scroll_obj[$(this).attr('name')] = $(this).text();
			});

			callback(scroll_obj);
		});
	}
	/**
	 * 偶数行样式
	 * @param {Object} styleName
	 */
	$.fn.addClassForEven = function (styleName) {
		$('.kgo-scroll-sty>ul>li:even').addClass(styleName);
	}
	/**
	 * 奇数行样式
	 * @param {Object} styleName
	 */
	$.fn.addClassForOdd = function (styleName) {
		$('.kgo-scroll-sty>ul>li:odd').addClass(styleName);
	}
	/**
	 * 访问样式
	 * @param {Object} styleName
	 */
	$.fn.addClassForHover = function (styleName) {
		$('.kgo-scroll-sty>ul>li').hover(function () {
			$('.kgo-scroll-sty>ul>li').each(function () {
				$(this).removeClass(styleName);
			});
			$(this).addClass(styleName);
		});
		$('.kgo-scroll-sty>ul>li').mouseleave(function () {
			$(this).removeClass(styleName);
		});

	}


})(jQuery);