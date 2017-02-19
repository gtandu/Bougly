$(function(){
	disabledClickOnElement();
	deleteCompteByMailOnClick();
	
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