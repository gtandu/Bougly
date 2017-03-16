$(function(){
	 //var
	  var gjCountAndRedirectStatus = false; //prevent from seting multiple Interval

	  //call
	      gjCountAndRedirect(10, "/login.html");
});

function gjCountAndRedirect(secounds, url) {

	  $('#gj-counter-num').text(secounds);

	  $('#gj-counter-box').show();

	  var interval = setInterval(function() {

	    secounds = secounds - 1;

	    $('#gj-counter-num').text(secounds);

	    if (secounds == 0) {

	      clearInterval(interval);
	      window.location = url;

	    }

	  }, 1000);

	  $('#gj-counter-box').click(function() {
	    clearInterval(interval);
	    window.location = url;

	  });
	}