$(function() {
	$('select').material_select();
	$('#numeroEtudiant').characterCounter();
	hideAttributOnRoleNoEtudiant();
	checkDateFormatOnInputFocusOut();
	checkErrorOnSubmit();
});

/**
 * Cache les inputs selon le role choisit
 * @returns
 */
function hideAttributOnRoleNoEtudiant() {
	var currentRole = $('select option:selected').val()
	$("#role").val(currentRole);
	$('select').change(function() {
		
		currentRole = $(this).val();
		
		if (currentRole != "Etudiant") {
			$(".inputNumEtu").toggle(function(){
				$("#role").val(currentRole);
			});
		}
	});
}

/**
 * Verifie le format de la date en sortie d'input
 * dd/MM/YYYY Ex : 01/01/2017
 * @returns
 */
function checkDateFormatOnInputFocusOut()
{
	$("#dateDeNaissance").focusout(function(){
		var inputDateDeNaissance = $(this).val();
		var dateValide = isValidDate(inputDateDeNaissance);
		if(dateValide == false)
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
 * Verifie lors du submit que tous les champs soient valides
 * @returns
 */
function checkErrorOnSubmit()
{
	$("#submitButton").click(function(event){
		event.preventDefault();
		var error = false;
		$('input').each(function(){
			var currentInput = $(this);
			
			if(currentInput.val()=='' || currentInput.hasClass('invalid'))
			{
				error = true
				currentInput.addClass('invalid');
			}
		})
		if(error == false)
		{
			$("form").submit();
		}
	})
	
}
