$(function(){
	/*
	confirmPassword();
	var input = document.getElementById('password');
	input.oninvalid = function(event) {
	    event.target.setCustomValidity('Le mot de passe doit contenir 8 caractères min (sans accents).');
	}
	*/
                
})

function confirmPassword()
{
	
	$("#confirm_password").focusout(function(){
		var password = $("#password").val();
		var confirm_password = $("#confirm_password").val();
		if(password != confirm_password || confirm_password == '')
		{
			$(this).removeClass("valid");
			$(this).addClass("invalid");
		}
		else
		{
			$(this).removeClass("invalid");
			$(this).addClass("valid");
		}
	})
}

