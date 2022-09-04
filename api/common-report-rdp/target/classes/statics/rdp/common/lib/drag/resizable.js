
/*$(function() {
	$(".box").dragDivResize();
});*/

(function($) {
	
	$.fn.dragDivResize = function(elem,resizeEnd) {
		
		return $(this).each(function() {
			var $box = $(this)
			var parent = elem||document;
			var coor = $('<div class="coor"></div>');
			$box.append(coor);
			coor.css({
				width : '10px',
				height : '10px',
				overflow : 'hidden',
				cursor : 'se-resize',
				position : 'absolute',
				right : 0,
				bottom : 0,
				backgroundColor : '#09C',
				display : "none"
			});
			$box.hover(function() {
				$(this).find(".coor").show();
			}, function() {
				$(this).find(".coor").hide();
			});
			$box.on('mousedown', '.coor', function(e) {
				var posix = {
					'w' : $box.width(),
					'h' : $box.height(),
					'x' : e.pageX,
					'y' : e.pageY
				};

				$.extend(parent, {
					'move' : true,
					'call_down' : function(e) {
						$box.css({
							'width' : Math.max(30, e.pageX - posix.x + posix.w),
							'height' : Math.max(30, e.pageY - posix.y + posix.h),
							'background-size':Math.max(30, e.pageX - posix.x + posix.w)+'px '+Math.max(30, e.pageY - posix.y + posix.h)+"px"
						});
					}
				});
				return false;
			});
			
			$(parent).mousemove(function(e) {
				if (!!this.move) {
					var posix = !parent.move_target ? {
							'x' : 0,
							'y' : 0
						} : parent.move_target.posix,
						callback = parent.call_down || function() {
							$(this.move_target).css({
								'top' : e.pageY - posix.y,
								'left' : e.pageX - posix.x
							});
						};

					callback.call(this, e, posix);
				}
			}).mouseup(function(e) {
				if (!!this.move) {
					var callback = parent.call_up || function() {};
					callback.call(this, e);
					$.extend(this, {
						'move' : false,
						'move_target' : null,
						'call_down' : false,
						'call_up' : false
					});
					if(resizeEnd){
						resizeEnd.call(this,$box,$box.width(),$box.height());
					}
				}
			});
		});
	}
}(jQuery));