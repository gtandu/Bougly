$(function() {
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('.modal').modal({
        complete: function(e) {
            console.log(e);
            console.log('Appeler la methode post ici')
            addMatiereOnTab($('.current'));
        }
    });
    addUEOnClick();
    addMatiereOnClickEvent();
    deleteMatiereOnClick();
    deleteUeOnClick();
});

function addUEOnClick() {
    $("#addUE").on("click", function() {
        var rowUE = "<div class='card-panel'>" +
            "<div class='UE2'>" +
            "<div class='card-content'>" +
            "<div class='col s1'>" +
            "<span class='card-title'>UE 2</span>" +
            "</div>" +
            "<div class='col s1 offset-s10 divIconDelete'>" +
            "<span><i class='small material-icons deleteIcon deleteUe'>clear</i></span>" +
            "</div>" +
            "<button class='btn waves-effect waves-light addMatiere' data-target='modaleNewMatiere'>Ajouter une matière</button>" +
            "<a class='waves-effect waves-light btn btnAddMatiereExistante' href='#'>Ajouter une matière existante</a>" +
            "<table class='bordered striped highlight centered responsive-table'>" +
            "<thead>" +
            "<tr>" +
            "<th data-field='id'>Matière</th>" +
            "<th data-field='name'>Coefficient</th>" +
            "<th data-field='price'>Seuil de compensation</th>" +
            "<th data-field='price'>Rattrapable</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody>" +
            "</tbody>" +
            "</table>" +
            "<p>Coefficient total de l'UE : 0</p>" +
            "</div>";
        $("#tabUE").append(rowUE)
        addMatiereOnClickEvent();
        deleteUeOnClick();
    });
}

function addMatierePostData() {
    var matiere = {
        nom: "",
        description: "",
        coefficient: "",
        seuilDeComp: "",
        rattrapable: ""
    }


}

function addMatiereOnClickEvent() {
    $(".addMatiere").off().on("click", function() {
        $(this).addClass('current');

    });
}

function addMatiereOnTab(ueElement) {
    console.log(ueElement);
    var matiere = {
        name: "Java",
        coefficient: "4",
        seuilComp: "7",
        rattrapable: "Oui"
    };
    /*
    var template = $.get("templateRowMatiere.htm", function(templates) {
        var template = $(templates).filter('#tplRowMatiere').html();
        $("tbody").append(Mustache.render(template, matiere));
    });
    */
    var template = "<tr><td>{{name}}</td><td>{{coefficient}}</td><td>{{seuilComp}}</td><td>{{rattrapable}}</td><td><i class='small material-icons deleteIcon deleteMatiere'>clear</i></td></tr>"
    var rowMatiereOutput = Mustache.render(template, matiere);
    console.log($(ueElement).parent().find('tbody'));
    $(ueElement).parent().find('tbody').append(rowMatiereOutput);
    $(ueElement).removeClass('current');
    deleteMatiereOnClick();

}

function deleteMatiereOnClick() {
    $('.deleteMatiere').off().click(function() {
        $(this).parent().parent().remove();
    })
}

function deleteUeOnClick() {
    $('.deleteUe').off().click(function() {
        $(this).parents('.card-panel').remove();
    })
}