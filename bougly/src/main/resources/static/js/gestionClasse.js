$(function(){
	deleteClasse();
	getDataFromCurrentRow();
    $('.modal').modal();
    validateForm(insertDataFromTD);
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
			Materialize.toast('Classe supprimée !', 3000, 'rounded');
			});
		}
	)
}

function getDataFromCurrentRow(){
	$(".editIcon").click(function(){
		
		var ligne = $(this).parent().parent().parent();
		
		var id = ligne.find("[data-id]").attr("data-id");
		var nom = ligne.find("[data-nom]").attr("data-nom");
		var formation = ligne.find("[data-formation]").attr("data-formation");
		var niveau = ligne.find("[data-niveau]").attr("data-niveau");
		var moyenne = ligne.find("[data-moyenne]").attr("data-moyenne");
		
		$("#input_id").val(id);
		$("#input_nom").val(nom);
		$("#select_niveau").find('div > input').val(niveau);
		$("#select_formation").find('div > input').val(formation);
		$("#input_moyenne").val(moyenne);
		
		
		});
	}

function insertDataFromTD(){
		
		var id = $("#input_id").val();
		var nom = $("#input_nom").val();
		var niveau = $("#select_niveau").find('div > input').val();
		var formation = $("#select_formation").find('div > input').val();
		var moyenne = $("#input_moyenne").val();
		
		var classeBean = {
				"id" : id,
				"nom": nom,
				"formation": formation,
				"niveau" : niveau,
				"moyenne": moyenne
		}
		
		var url = "/responsable/modifierClasse.html";
		$.post(url,classeBean,function(){
			$('#modalEditClasse').modal('close');

			var ligne = $('tbody').find('[data-id="'+classeBean.id+'"]').parents('tr');
			
			ligne.find('[data-nom]').attr("data-nom",classeBean.nom).text(classeBean.nom);
			ligne.find('[data-formation]').attr("data-formation",classeBean.formation).text(classeBean.formation);
			ligne.find('[data-niveau]').attr("data-niveau",classeBean.niveau).text(classeBean.niveau);
			ligne.find('[data-moyenne]').attr("data-moyenne",classeBean.moyenne).text(classeBean.moyenne);
		});
	}