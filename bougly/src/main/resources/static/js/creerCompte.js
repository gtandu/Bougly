$(function() {
	$('select').material_select();
	hideAttributOnRoleNoEtudiant();
});

function hideAttributOnRoleNoEtudiant() {
	var currentRole = $('select option:selected').val()
	console.log(currentRole);
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