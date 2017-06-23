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
		var name = ligne.find("[data-name]").attr("data-name");
		var formation = ligne.find("[data-formation]").attr("data-formation");
		var level = ligne.find("[data-level]").attr("data-level");
		
		$("#input_id").val(id);
		$("#input_name").val(name);
		$("#select_level").find('div > input').val(level);
		$("#select_formation").find('div > input').val(formation);
		
		
		});
	}

function insertDataFromTD(){
		
		var id = $("#input_id").val();
		var name = $("#input_name").val();
		var level = $("#select_level").find('div > input').val();
		var formation = $("#select_formation").find('div > input').val();
		var subjects = $("#select_subject").find('div > input').val();
		
		var gradeDto = {
				"id" : id,
				"name": name,
				"formation": formation,
				"level" : level,
				"subjectList" : subjects,
		}
		
		var url = "/responsable/modifierClasse.html";
		$.post(url,gradeDto,function(){
			$('#modalEditClasse').modal('close');

			var ligne = $('tbody').find('[data-id="'+gradeDto.id+'"]').parents('tr');
			
			ligne.find('[data-name]').attr("data-name",gradeDto.name).text(gradeDto.name);
			ligne.find('[data-formation]').attr("data-formation",gradeDto.formation).text(gradeDto.formation);
			ligne.find('[data-level]').attr("data-level",gradeDto.level).text(gradeDto.level);
		});
	}