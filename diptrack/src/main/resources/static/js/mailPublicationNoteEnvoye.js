$(function(){
	 //var
	 var gjCountAndRedirectStatus = false; //prevent from setting multiple Interval

	 //call
	 gjCountAndRedirect(5, "/enseignant/homePageTeacher.html");
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