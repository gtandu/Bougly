/**
 * 
 */
$(function(){
	
	addUeOnClick();
	initJsGrid();
	deleteUeOnClick();
})

function addUeOnClick()
{
	$("#btn-addUe").click(function(){
		
		if($(".row-Ue").length == 0)
		{
			$(".row-filiere").after("<div class='row row-Ue'>" +
					"<div class='card-panel'>" +
					"<div class='UE1'>" +
					"<div class='card-content'>" +
					"<div class='row'>" +
					"<div class='col s2'><span class='card-title'>UE 1</span></div>" +
					"<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons deleteIcon btn-deleteUe'>clear</i></span></div></div>" +
			"<div class='jsGrid'></div></div></div></div></div>");
		}
		else
		{
			$(".row-Ue").last().after("<div class='row row-Ue'>" +
					"<div class='card-panel'>" +
					"<div class='UE1'>" +
					"<div class='card-content'>" +
					"<div class='row'>" +
					"<div class='col s2'><span class='card-title'></span></div>" +
					"<div class='col s1 offset-s9 divIconDelete'><span><i class='small material-icons deleteIcon btn-deleteUe'>clear</i></span></div></div>" +
			"<div class='jsGrid'></div></div></div></div></div>");
		}
		initJsGridLast();
		deleteUeOnClick();
		resetUeNumber();
	})
	
}
function resetUeNumber()
{
	 $.each($(".row-Ue"),function(index,value){
		 	var ueNumber = index +1;
	        $(value).find(".card-title").text("UE "+ueNumber);
	     });
	
}
function deleteUeOnClick()
{
	$(".btn-deleteUe").off().click(function(){
		$(this).parents(".row-Ue").remove();
		resetUeNumber();
	})
}

function initJsGridLast()
{
	/*
	var matiere = [ {
		"Nom" : "Java",
		"Description" : "Programmation J2EE",
		"Coefficient" : 2,
		"Seuil de compensation" : 7,
		"Rattrapable" : 0
	} ];*/

	var reponse = [ {
		Valeur : "Non",
		Id : 0
	}, {
		Valeur : "Oui",
		Id : 1
	}, ];

	$(".jsGrid").last().jsGrid({
		width : "100%",
		height : "300px",

		inserting : true,
		editing : true,
		sorting : true,
		paging : true,
		
		noDataContent: "",

		/*data : matiere,*/

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

function initJsGrid()
{
	/*
	var matiere = [ {
		"Nom" : "Java",
		"Description" : "Programmation J2EE",
		"Coefficient" : 2,
		"Seuil de compensation" : 7,
		"Rattrapable" : 0
	} ];*/

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

		/*data : matiere,*/
		
		noDataContent: "",

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
