/**
 * 
 */
$(function() {

	initJsGrid();
	deleteUeOnClick();
	deleteSemeterOnClick();
	addSemestreOnClick();
	
})

function addSemestreOnClick() {

	$("#btn-addSemestre")
			.click(
					function() {
						if ($(".div-ue").length == 0) {
							/*
							 * Pas de bloc semestre existant
							 */
							$(".row-filiere")
									.after(
											"<div class='row row-semestre'>"
													+ "<div class='card-panel'>"
													+ "<div class='semestre'>"
													+ "<div class='card-content card-content-semester'>"
													+ "<div class='row'>"
													+ "<div class='col s2'><span class='card-title card-title-semester'>Semestre 1</span></div>"
													+ "<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons toggleIcon reduceIcon btn-reduceSemestre'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteSemestre'>clear</i></span></div>"
													+ "<div class='input-field col s12 div-ue'><a class='waves-effect waves-light btn btn-addUe'><i class='material-icons left'>add</i>Ajouter un UE</a></div>"
													+ "</div>"
													+ "</div></div></div>"
													+ "</div>");
						} else {
							/*
							 * On ajoute un bloc semestre apres le dernier
							 * existant
							 */
							$(".row-semestre")
									.last()
									.after(
											"<div class='row row-semestre'>"
													+ "<div class='card-panel'>"
													+ "<div class='semestre'>"
													+ "<div class='card-content card-content-semester'>"
													+ "<div class='row'>"
													+ "<div class='col s2'><span class='card-title card-title-semester'>Semestre 1</span></div>"
													+ "<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons toggleIcon reduceIcon btn-reduceSemestre'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteSemestre'>clear</i></span></div>"
													+ "<div class='input-field col s12 div-ue'><a class='waves-effect waves-light btn btn-addUe'><i class='material-icons left'>add</i>Ajouter un UE</a></div>"
													+ "</div>"
													+ "</div></div></div>"
													+ "</div>");
						}
						addUeOnClick();
						deleteSemeterOnClick();
						resetSemesterNumber();
						toggleIconOnClick();
					})

}
function addUeOnClick() {
	$(".btn-addUe")
			.off()
			.click(
					function() {
						if ($(this).find(".row-Ue").length == 0) {
							$(this)
									.parents(".row-semestre .card-content")
									.append(
											"<div class='row row-Ue scale-transition'>"
													+ "<div class='card-panel'>"
													+ "<div class='UE'>"
													+ "<div class='card-content card-content-ue'>"
													+ "<div class='row'>"
													+ "<div class='col s2'><span class='card-title card-title-ue'>UE 1</span></div>"
													+ "<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons toggleIcon reduceIcon btn-reduceSemestre'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteUe'>clear</i></span></div></div>"
													+ "<div class='jsGrid'></div></div></div></div></div>");
						} else {
							$(this)
									.parents(".row")
									.find(".row-Ue")
									.last()
									.after(
											"<div class='row row-Ue scale-transition'>"
													+ "<div class='card-panel'>"
													+ "<div class='UE'>"
													+ "<div class='card-content card-content-ue'>"
													+ "<div class='row'>"
													+ "<div class='col s2'><span class='card-title card-title-ue'></span></div>"
													+ "<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons toggleIcon reduceIcon btn-reduceSemestre'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteUe'>clear</i></span></div></div>"
													+ "<div class='jsGrid'></div></div></div></div></div>");
						}
						initJsGridLast($(this));
						resetUeNumber($(this).parents(".card-content-semester"));
						deleteUeOnClick();
					})
}

function resetUeNumber(element) {
	$.each($(element).find(".row-Ue"), function(index, value) {
		var ueNumber = index + 1;
		$(value).find(".card-title-ue").text("UE " + ueNumber);
	});

}

function resetSemesterNumber() {
	$.each($(".row-semestre"), function(index, value) {
		var semesterNumber = index + 1;
		$(value).find(".card-title-semester")
				.text("Semestre " + semesterNumber);
	});

}
function deleteUeOnClick() {
	$(".btn-deleteUe").off().click(function() {
		var test = $(this).parents(".card-content-semester");
		$(this).parents(".row-Ue").remove();
		console.log(test);
		resetUeNumber(test);
	})
}

function deleteSemeterOnClick() {
	$(".btn-deleteSemestre").off().click(function() {
		$(this).parents(".row-semestre").remove();
		resetSemesterNumber();
	})
}

function toggleIconOnClick() {
	$(".toggleIcon").off().click(
			
			function() {
				var btnHtml = $(this);
				$.each($(this).parents(".card-content-semester").find(
						".scale-transition"), function(index, value) {
					$(value).toggle();
					console.log(btnHtml);
					console.log(btnHtml.hasClass("extendIcon"));
					if(btnHtml.hasClass("extendIcon"))
					{
						btnHtml.parent().html("<i class='small material-icons toggleIcon reduceIcon btn-reduceSemestre'>remove</i>");
						toggleIconOnClick();
					}
					else
					{
						btnHtml.parent().html("<i class='small material-icons toggleIcon extendIcon btn-extendSemestre'>crop_3_2</i>");
						toggleIconOnClick();
					}
					
				});

			})

}

function initJsGridLast(element) {
	/*
	 * var matiere = [ { "Nom" : "Java", "Description" : "Programmation J2EE",
	 * "Coefficient" : 2, "Seuil de compensation" : 7, "Rattrapable" : 0 } ];
	 */

	var reponse = [ {
		Valeur : "Non",
		Id : 0
	}, {
		Valeur : "Oui",
		Id : 1
	}, ];

	$(element).parents(".card-content-semester").find(".jsGrid").last().jsGrid(
			{
				width : "100%",
				height : "300px",

				inserting : true,
				editing : true,
				sorting : true,
				paging : true,

				noDataContent : "",

				/* data : matiere, */

				fields : [ {
					name : "Nom",
					type : "text",
					align : "center",
					width : 100,
					validate : "required"
				}, {
					name : "Description",
					type : "text",
					align : "center",
					width : 200
				}, {
					name : "Coefficient",
					type : "number",
					align : "center",
					width : 50
				}, {
					name : "Seuil de compensation",
					type : "number",
					align : "center",
					width : 50
				}, {
					name : "Rattrapable",
					type : "select",
					align : "center",
					items : reponse,
					selectedIndex : 0,
					valueField : "Id",
					textField : "Valeur"
				}, {
					type : "control"
				} ]
			});

}

function initJsGrid() {
	/*
	 * var matiere = [ { "Nom" : "Java", "Description" : "Programmation J2EE",
	 * "Coefficient" : 2, "Seuil de compensation" : 7, "Rattrapable" : 0 } ];
	 */

	var reponse = [ {
		Valeur : "Non",
		Id : 0
	}, {
		Valeur : "Oui",
		Id : 1
	}, ];

	$(".jsGrid").jsGrid({
		width : "100%",
		height : "300px",

		inserting : true,
		editing : true,
		sorting : true,
		paging : true,

		/* data : matiere, */

		noDataContent : "",

		fields : [ {
			name : "Nom",
			type : "text",
			align : "center",
			width : 100,
			validate : "required"
		}, {
			name : "Description",
			type : "text",
			align : "center",
			width : 200
		}, {
			name : "Coefficient",
			type : "number",
			align : "center",
			width : 50
		}, {
			name : "Seuil de compensation",
			type : "number",
			align : "center",
			width : 50
		}, {
			name : "Rattrapable",
			type : "select",
			align : "center",
			items : reponse,
			selectedIndex : 0,
			valueField : "Id",
			textField : "Valeur"
		}, {
			type : "control"
		} ]
	});

}
