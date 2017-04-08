$(function(){
	deleteClasse();
	getDataFromCurrentRow();
	insertDataFromTD();
    $('.modal').modal();
})

 $(document).ready(function() {
    $('select').material_select();
  });

function deleteClasse(){
	$(".deleteIcon").click(function(){
		var id = $(this).parent().parent().find(".id").text();
		var ligne = $(this).parent().parent();
		var url = "/responsable/supprimerClasse.html?id="+id;
		
		$.post(url, function(id, status){
			ligne.remove();
			});
		}
	)
}

function getDataFromCurrentRow(){
	$(".editIcon").click(function(){
		
		var ligne = $(this).parent().parent().parent();
		
		var id = ligne.find("[data-id]").data("id");
		var nom = ligne.find("[data-nom]").data("nom");
		var formation = ligne.find("[data-formation]").data("formation");
		var niveau = ligne.find("[data-niveau]").data("niveau");
		var moyenne = ligne.find("[data-moyenne]").data("moyenne");
		
		$("#input_id").val(id);
		$("#input_nom").val(nom);
		$("#input_formation").val(formation);
		$("#input_niveau").val(niveau);
		$("#input_moyenne").val(moyenne);
		
		});
	}

function insertDataFromTD(){
	$("#formEditClasse").submit(function(event){
		
		event.preventDefault();
		
		var id = $("#input_id").val();
		var nom = $("#input_nom").val();
		var niveau = $("#input_niveau").val();
		var formation = $("#input_formation").val();
		var moyenne = $("#input_moyenne").val();
		
		var compteBean = {
				"id" : id,
				"nom": nom,
				"formation": formation,
				"niveau" : niveau,
				"moyenne": moyenne
		}
		
		var url = "/responsable/modifierClasse.html";
		$.post(url,compteBean,function(){
			$('#modalEditClasse').modal('close');
		});
		});
	}