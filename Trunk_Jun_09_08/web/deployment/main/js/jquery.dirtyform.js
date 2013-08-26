/*!
* jQuery Plugin: Are-You-Sure (Dirty Form Detection)
* https://github.com/codedance/jquery.AreYouSure/
*
* Copyright (c) 2012-2013, Chris Dance and PaperCut Software http://www.papercut.com/
* Dual licensed under the MIT or GPL Version 2 licenses.
* http://jquery.org/license
*
* Author: chris.dance@papercut.com
* Version: 1.1.0
* Date: 3rd Feb 2013
*/
(function($) {
  $.fn.areYouSure = function(options) {
    var settings = $.extend(
          {
            'message' : 'You have unsaved changes!',
            'dirtyClass' : 'dirty'
          }, options);

    $(window).bind('beforeunload', function() {
        $form = $("#dirtyCheck-form");
        if ($form.length > 0) {
	      $dirtyForms = $form.filter('.' + settings.dirtyClass);
	      $submitDirty = $form.filter(".submitDirty");
	      if ($dirtyForms.length > 0) {
	        return settings.message;
	      } else if ($submitDirty.length <= 0) {
		    var formData = $form.serialize();
		    var startData = $form.data('ays-form');
		    if (formData != startData) {
		      return settings.message;
		    }
	      }
        }
    });

    return this.each(function(elem) {
      if (!$(this).is('form')) {
        return;
      }
      
      $(this).submit(function() {
        $(this).removeClass(settings.dirtyClass);
        $(this).addClass("submitDirty");
      });
      
      var formData = $(this).serialize();
      $(this).data('ays-form', formData);
    });
  };
})(jQuery);