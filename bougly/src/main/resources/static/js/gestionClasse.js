$(function(){
	deleteClasse();
})

function deleteClasse(){
	$(".deleteIcon").click(function(){
		var id = $(this).parent().parent().find(".id").text();
		var ligne = $(this).parent().parent();
		var url = "/responsable/supprimerClasse.html?id="+id;
		
		console.log(ligne);
		
		$.post(url, function(id, status){
			ligne.remove();
			});
		}
	)
}
