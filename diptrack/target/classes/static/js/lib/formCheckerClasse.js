$(function() {
	addMethodRegexInApiValidate();
});

function addMethodRegexInApiValidate() {
	jQuery.validator.addMethod("regex", function(value, element, regexp) {
		if (regexp.constructor != RegExp)
			regexp = new RegExp(regexp);
		else if (regexp.global)
			regexp.lastIndex = 0;
		return this.optional(element) || regexp.test(value);
	}, "erreur expression reguliere");
}

function validateForm(functionCallBackValidate) {
	return $("form").validate({
		rules : {
			"name" : {
				"required" : true,
				"regex" : /^([A-Za-z0-9])+$/
			}
		},

		messages : {
			name : {
				required : "Saisissez l'intitulé de votre classe",
				regex	:	"Le format saisie est incorrecte",
			}
		},
		errorElement : 'div',
		errorPlacement : function(error, element) {
			var placement = $(element).data('error');
			if (placement) {
				$(placement).append(error)
			} else {
				error.insertAfter(element);
			}

		},
		
		submitHandler: function(form) {
		    // do other things for a valid form
			if(functionCallBackValidate != undefined)
			{
				functionCallBackValidate();				
			}
			else
			{
				form.submit();
			}
		  }
		});
}
