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
		$("#select_niveau").find('div > input').val(niveau);
		$("#select_formation").find('div > input').val(formation);
		$("#input_moyenne").val(moyenne);
		
		});
	}

function insertDataFromTD(){
	$("#formEditClasse").submit(function(event){
		
		event.preventDefault();
		
		var id = $("#input_id").val();
		var nom = $("#input_nom").val();
		var niveau = $("#select_niveau").find('div > input').val();
		var formation = $("#select_formation").find('div > input').val();
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

			var ligne = $('tbody').find('[data-id="'+compteBean.id+'"]').parents('tr');
			
			ligne.find('[data-nom]').attr("data-nom",compteBean.nom).text(compteBean.nom);
			ligne.find('[data-formation]').attr("data-formation",compteBean.formation).text(compteBean.formation);
			ligne.find('[data-niveau]').attr("data-niveau",compteBean.niveau).text(compteBean.niveau);
			ligne.find('[data-moyenne]').attr("data-moyenne",compteBean.moyenne).text(compteBean.moyenne);
		});
		});
	}