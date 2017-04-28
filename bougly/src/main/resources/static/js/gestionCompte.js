$(function(){
	disabledClickOnElement();
	deleteCompteByMailOnClick();
	fillModalWithData();
	//Init modal
	$('.modal').modal();
	validateForm(postDataForEdit);
	
})

function disabledClickOnElement()
{
	$('.disabled').click(function(e){
	     e.preventDefault();
	  })
}

function deleteCompteByMailOnClick()
{
	$(".deleteIcon").click(function(){
		var htmlElement=$(this).parents('tr');
		var mail = $(this).data("mail");
		var url = "/administrateur/supprimerCompte.html?mail="+mail;
		var currentUser = $("#currentUser").data("user");
		if(currentUser != mail)
		{
			$.post(url, function(data, status){
				htmlElement.remove();
				Materialize.toast('Compte supprimé !', 3000, 'rounded')
		    });
		}
		else
		{
			alert("Vous ne pouvez pas supprimer votre propre compte !");
		}

	})	
	
}
function resetModalValid()
{
	$("input").each(function(){
		$(this).removeClass("valid");
	})
}
function fillModalWithData()
{
	$(".editIcon").click(function(){
		resetModalValid();
		var role = $(this).parents('tr').find('[data-role]').attr("data-role");
		var mail = $(this).parents('tr').find('[data-mail]').attr("data-mail");
		var nom = $(this).parents('tr').find('[data-nom]').attr("data-nom");
		var prenom = $(this).parents('tr').find('[data-prenom]').attr("data-prenom");
		var numeroEtudiant = $(this).parents('tr').find('[data-numero_etudiant]').attr("data-numero_etudiant");
		
		$("#roleInput").val(role);
		$("#email").text(mail);
		$("#emailInput").val(mail);
		$("#lastName").val(nom);
		$("#firstName").val(prenom);
		if(numeroEtudiant == null)
		{
			$("#inputNumEtu").hide();
			$("#numeroEtudiant").addClass('notBind');
			$("#numeroEtudiant").removeAttr('required');
			$("#numeroEtudiant").removeClass('invalid');
			$("#numeroEtudiant").removeClass('validate');
		}
		else
		{
			$("#inputNumEtu").show();
			$("#numeroEtudiant").val(numeroEtudiant);
		}
		
	})
}

function postDataForEdit(form)
{
		var $form = $(form);
		var compteBean = {
				"mail" : $form.find("input[name='email']").val(),
				"role" : $form.find("input[name='role']").val(),
				"lastName" : $form.find("input[name='lastName']").val(),
				"firstName" : $form.find("input[name='firstName']").val(),
				"studentNumber" : $form.find("input[name='studentNumber']").val()
		};
		$.post("/administrateur/editerCompte.html",compteBean ,function(data){
			if(data == "ERREUR")
			{
				Materialize.toast('Erreur lors de la modification !', 3000, 'rounded');
			}
			else
			{
				var ligneEdit = $('tbody').find('[data-mail="'+compteBean.mail+'"]').parents('tr');
				ligneEdit.find('[data-nom]').attr("data-nom", compteBean.lastName).text(compteBean.lastName);
				ligneEdit.find('[data-prenom]').attr("data-prenom", compteBean.firstName).text(compteBean.firstName);
				ligneEdit.find('[data-numero_etudiant]').attr("data-numero_etudiant", compteBean.studentNumber).text(compteBean.studentNumber);
				Materialize.toast('Modification(s) effectuée(s)', 3000, 'rounded');
			}
		})
		$('#modalEditCompte').modal('close');
}
