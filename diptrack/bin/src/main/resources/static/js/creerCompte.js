$(function() {
	$('select').material_select();
	hideAttributOnRoleNoEtudiant();
	validateForm($("#formCreerUnCompte"));
})

/**
 * Cache les inputs selon le role choisit et ajoute la classe 'notBind' a l'input numeroEtudiant selon le type de compte
 * @returns
 */
function hideAttributOnRoleNoEtudiant() {
	var currentRole = $('select option:selected').val()
	$("#role").val(currentRole);
	$('select').change(function() {
		
		currentRole = $(this).val();
		
		if (currentRole != "Etudiant") {
			$(".inputNumEtu").hide(function(){
				$("#numeroEtudiant").removeAttr('required');
				$("#numeroEtudiant").removeClass('invalid');
				$("#numeroEtudiant").removeClass('validate');
				$("#numeroEtudiant").addClass('notBind');
			});
		}
		else
		{
			$(".inputNumEtu").show(function(){
				$('#numeroEtudiant').attr('required','true');
				$("#numeroEtudiant").removeClass('notBind');
			})

		}
	});
}
