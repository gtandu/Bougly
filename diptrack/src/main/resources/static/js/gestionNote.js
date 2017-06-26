$(function(){
    validateForm(formEditNote);
})

 $(document).ready(function() {
    $('select').material_select();
  });


function insertDataFromTD(){
		
		var ligne = $('tbody').find('[data-mail]').val();
	
		var id = $("#input_id").val();
		var name = $("#input_name").val();
		var level = $("#select_level").find('div > input').val();
		var formation = $("#select_formation").find('div > input').val();
		
		var gradeDto = {
				"id" : id,
				"name": name,
				"formation": formation,
				"level" : level,
				//"subjectList" : subjects
		}
		
		var url = "/enseignant/noteGradeManagement.html";
		
		$.post(url,gradeDto,function(){
			$('#modalEditNote').modal('close');

			var ligne = $('tbody').find('[data-id="'+gradeDto.id+'"]').parents('tr');
			
			ligne.find('[data-name]').attr("data-name",gradeDto.name).text(gradeDto.name);
			ligne.find('[data-formation]').attr("data-formation",gradeDto.formation).text(gradeDto.formation);
			ligne.find('[data-level]').attr("data-level",gradeDto.level).text(gradeDto.level);
		});
	}