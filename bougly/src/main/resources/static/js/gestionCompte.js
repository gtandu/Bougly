$(function(){
	disabledClickOnElement();
	deleteCompteByMailOnClick();
	fillModalWithData();
	//Init modal
	$('.modal').modal();
	postDataForEdit();
	
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
		console.log(currentUser);
		if(currentUser != mail)
		{
			$.post(url, function(data, status){
				htmlElement.remove();
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
		var dateDeNaissance = $(this).parents('tr').find('[data-date_de_naissance]').attr("data-date_de_naissance");
		var numeroEtudiant = $(this).parents('tr').find('[data-numero_etudiant]').attr("data-numero_etudiant");
		
		$("#roleInput").val(role);
		$("#email").text(mail);
		$("#emailInput").val(mail);
		$("#nom").val(nom);
		$("#prenom").val(prenom);
		$("#dateDeNaissance").val(dateDeNaissance);
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

function postDataForEdit()
{
	$("#formEditCompte").submit(function(event){
		console.log("test");
		event.preventDefault();
		var $form = $( this );
		var compteBean = {
				"mail" : $form.find("input[name='email']").val(),
				"role" : $form.find("input[name='role']").val(),
				"nom" : $form.find("input[name='nom']").val(),
				"prenom" : $form.find("input[name='prenom']").val(),
				"dateDeNaissance" : $form.find("input[name='dateDeNaissance']").val(),
				"numeroEtudiant" : $form.find("input[name='numeroEtudiant']").val()
		};
		$.post("/administrateur/editerCompte.html",compteBean ,function(data){
			if(data == "ERREUR")
			{
				Materialize.toast('Erreur lors de la modification !', 3000, 'rounded')
			}
			else
			{
				var ligneEdit = $('tbody').find('[data-mail="'+compteBean.mail+'"]').parents('tr');
				ligneEdit.find('[data-nom]').attr("data-nom", compteBean.nom).text(compteBean.nom);
				ligneEdit.find('[data-prenom]').attr("data-prenom", compteBean.prenom).text(compteBean.prenom);
				ligneEdit.find('[data-date_de_naissance]').attr("data-date_de_naissance", compteBean.dateDeNaissance).text(compteBean.dateDeNaissance);
				ligneEdit.find('[data-numero_etudiant]').attr("data-numero_etudiant", compteBean.numeroEtudiant).text(compteBean.numeroEtudiant);
				Materialize.toast('Modification(s) effectuée(s)', 3000, 'rounded')
			}
		})
		$('#modalEditCompte').modal('close');
	})
}
