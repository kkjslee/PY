//author:rqshao ,514407363@qq.com, 2013

(function($) {
	$.fn.tableSelect = function(options) {
		if (!options) {
			var options = {};
		}
		var table = $(this);
		var tableRows = $(table).find('li');
		
		$(table).addClass('tableSelect');

		var clickedClass = 'ui-selected';
		var tableFocus = 'focused';
		
		$(table).click(function() {
			$(table).focus();
		});
					
		$(table).focusin(function() {
			$('table.' + tableFocus).removeClass(tableFocus);
			$(table).addClass(tableFocus);
		});
			
		$.each(tableRows, function() {
//			/$(this).children('td').attr('class', '');
			$(this).click(function(ev) {
				
				$.each(tableRows, function() {
					$(this).removeClass(clickedClass);
				});
				$(this).addClass(clickedClass);
				if (options.onClick) {
					options.onClick(this);
				}
			
			});
			$(this).dblclick(function() {
				if (options.onDoubleClick) {
					var thisRow = {};
					thisRow.row = [];
					thisRow.row.push(this);
					options.onDoubleClick(thisRow.row);
				}
			});
		});
	};
	
})(jQuery);