getmove(0)
function getmove(moveid){
	$.ajax({
//		type:"POST",
		url:"movefirst",
		data:{id:moveid,loginPassword:8},
		dataType:"text",
		success:function(data){
	        var arr= data;
			json = eval("(" +arr +")");
			usermanager(json);
			star()
		},
		error:function(data){
			console.log(3)
		}
	})
}

$(function() {
    $("#pagination2").pagination({
      currentPage: 1,
      totalPage: 50,
      isShow: false,
      count: 6,
      prevPageText: "< 上一页",
      nextPageText: "下一页 >",
      callback: function(current) {
        $("#current2").text(current)
        $("#movie").empty()
        getmove(current)
      }
    });
  });
	

function usermanager(json){
	for(var i=0;i < json.length;i++){
		var usermanager='<li>'+
        '<img class="am-img-thumbnail am-img-bdrs" src="static/img/img/'+json[i].photo+'" alt=""/>'+
        '<div class="gallery-title">'+json[i].name+'</div>'+
        '<div class="gallery-desc">'+json[i].filetype+'</div>'+
      '<div class="gallery-desc"><form data-am-rating><a class="" role="button"><input type="hidden" name="score">'+
          '<i class="am-icon-star-o"></i>'+
          '<i class="am-icon-star-o"></i>'+
          '<i class="am-icon-star-o"></i>'+
          '<i class="am-icon-star-o"></i>'+
          '<i class="am-icon-star-o"></i>'+
       '</a><span></span><input type="hidden" name="moveid" value="'+json[i].MOVIE_ID+'"></form></div></li>'
		$("#movie").append(usermanager)
	}
}
function star(){
	/*! Amaze UI Plugin ~ rating */

	/* global window, document, define, jQuery, setInterval, clearInterval */
	(function(factory) {
	  'use strict';

	  if (typeof define === 'function' && define.amd) {
	    define(['jquery'], factory);
	  } else if (typeof exports !== 'undefined') {
	    module.exports = factory(require('jquery'));
	  } else {
	    factory(jQuery);
	  }

	}(function($) {
	  'use strict';

	  // add method to jQuery prototype
	  $.fn.rating = function(options) {
	    // iterate over the current set of matched elements
	    return this.each(function() {
	      var $form = $(this);
	      var $icons = $form.find('a > i');
	      var $parent = $icons.parent();
	      var $data = $parent.data();
	      var defauls = {
	        icons: ['star-o', 'star'],
	        scale: 5,
	        score: 0
	      };

	      var settings = {};
	      if ($data.amIcons) {
	        var icons = $data.amIcons.split(/\s*\,\s*/);
	        var length = icons.length;
	        if (length >= 2) {
	          settings.icons = icons;
	        }
	        if (length === 3) {
	          settings.scale = 10;
	        }
	      }
	      if ($data.amScore) {
	        settings.score = $data.amScore;
	      }
	      var $options = $.extend({}, defauls, settings, options);
	      var icons = $options.icons.map(function (icon) {
	        return 'am-icon-' + icon;
	      });
	      var score = $options.score;
	      var integer = Math.round(score);
	      var rounding = Math.abs(score - integer);
	      var all = icons.join(' ');
	      var empty = icons.shift();
	      var full = icons.pop();
	      var half = icons.pop();

	      $icons.each(function(index) {
	        var $icon = $(this);
	        $icon.on('mouseenter', function() {
	         $icons.removeClass(all);
	         $icon.prevAll().addBack().addClass(full);
	         $icon.nextAll().addClass(empty);
	        });
	        $icon.on('click', function() {
	         $form.find('input[name="score"]').val(index + 1);
	         var res = $form.find('input[name="score"]').val();
	         $icons.removeClass(all);
	         $icon.parent().next().html(res)
	         var moveid=$icon.parent().next().next().attr("value")
	         console.log(moveid)
	          $.ajax({
				type:"POST",
				url:"addsuggest",
				data:{moveid:moveid,score:res},
				dataType:"text",
				success:function(data){
					console.log(0)
				},
				error:function(data){
					console.log(1)
				}
				
			});
	        });
	        $parent.on('mouseleave', function() {
		        $icons.removeClass(all);
		        $icons.slice(integer).addClass(empty);
		        if (integer > 0) {
		          $icons.slice(0, integer).addClass(full);
		          if (half && Math.abs(rounding) > 0.25) {
		            $icons.eq(Math.floor(score)).addClass(half);
		          }
		        }
		      });
	      });
	      
	    });

	  };

	  // $(function() {
	    $('form[data-am-rating]').rating();
	  // });

	}));

}
