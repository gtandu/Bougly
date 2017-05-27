$(function(){
	$.ajaxSetup ({
	    // Disable caching of AJAX responses
	    cache: false
	});
	setTimeout(function(){
		$('body').addClass('loaded');
	}, 200);
	/*
	$(window).on('load',function() {
        $('body').addClass('loaded');
    });
    */
})