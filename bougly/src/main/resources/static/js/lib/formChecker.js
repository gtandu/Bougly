$(function() {
	checkErrorOnSubmit();
	checkEmailFormatOnInputFocusOut();
	addMethodRegexInApiValidate();
	validateForm()
});

/**
 * Test le format de la date de l'input
 * @param date
 * @returns
 */
function isValidDate(date)
{
	var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;

	return reg.test(date);
	
}

/**
 * Verifie le format de l'email en sortie d'input
 * @returns
 */
function checkEmailFormatOnInputFocusOut()
{
	$("#email").focusout(function(){
		var inputEmail = $(this).val();
		var emailValide = isValidEmail(inputEmail);
		if(emailValide == false)
		{
			$(this).addClass('invalid');
		}
		else
		{
			$(this).removeClass('invalid').addClass('valid');
		}
	});
}

/**
 * Test le format de l'email
 * @param email
 * @returns
 */
function isValidEmail(email)
{
	var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return reg.test(email);
}

/**
 * Verifie lors du submit que tous les champs soient valides et ignore le champ numero etudiant selon le type de compte
 * @returns
 */
function checkErrorOnSubmit()
{
	$("#submitButton").click(function(event){
		event.preventDefault();
		var error = false;
		$('input').each(function(){
			var currentInput = $(this);
			
			if(currentInput.hasClass('notBind') == false)
			{
				if(currentInput.val()=='' || currentInput.hasClass('invalid'))
				{
					error = true
					currentInput.addClass('invalid');
				}
			}
		})
		if(error == false)
		{
			$("form").submit();
		}
	})
	
}
function addMethodRegexInApiValidate()
{
	jQuery.validator.addMethod(
			  "regex",
			   function(value, element, regexp) {
			       if (regexp.constructor != RegExp)
			          regexp = new RegExp(regexp);
			       else if (regexp.global)
			          regexp.lastIndex = 0;
			          return this.optional(element) || regexp.test(value);
			   },"erreur expression reguliere"
			);
}

function validateForm()
{
	$("form").validate({
	      rules: {
	         "password":{
	            "required": true,
	            "minlength": 8,
	            "regex": /[A-z0-9]{8,}/
	         },
	         "confirm_password": {
	            "required": true,
	            "equalTo": '#password'
	         },
	  },
	//For custom messages
    messages: {
    	password:{
            required: "Saisissez un mot de passe",
            minlength: "Saisissez au moins 8 caractères (sans accents)"
        },
        confirm_password:{
        	required:	"Resaisissez un mot de passe",
        	equalTo: "Les mots de passes doivent être identiques"
        }
    },
    errorElement : 'div',
    errorPlacement: function(error, element) {
      var placement = $(element).data('error');
      if (placement) {
        $(placement).append(error)
      } else {
        error.insertAfter(element);
      }
      
    }
	});
}
