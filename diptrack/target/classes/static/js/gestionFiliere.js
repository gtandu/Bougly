/**
 * 
 */
$(function() {

    addSemestreOnClick();
    saveFiliereNameOnClick();
    $('.modal').modal();
    $('select').material_select();
    //validateForm($("#formMccRules"));

})

function saveFiliereNameOnClick() {
    $("#formFiliere").submit(function(e) {
        e.preventDefault();
        var courseJson = {
            name: $("#courseNameInput").val(),
            responsibleName: $("#responsibleName").val(),
            newName: "",
        }
        if ($("#courseNameInput").data("mode") == "edit") {
            courseJson.name = $("#courseNameInput").data("name");
            courseJson.newName = $("#courseNameInput").val();
            postCourse("/responsable/editCourseName", courseJson);
        } else {
            postCourse("/responsable/createCourse", courseJson);
        }


    })
}

function postCourse(url, courseJson) {
    $.post(url, courseJson, function(id) {
        if (id != 0) {
            if (courseJson.newName.length != 0) {
                courseJson.name = courseJson.newName;
            }
            $("#courseNameError").hide();
            $("#courseNameCheck").hide();
            $('#courseNameInput').replaceWith(function() {
                return "<p id='courseName' class='courseNameValid' data-id=" + id + ">" + courseJson.name + "</p>";
            })
            $("#courseName").after("<i id='editMode' class='material-icons'>mode_edit</i>");
            addEventEditMode();
            $(".div-semestre").show();
            Materialize.toast('Filière "' + courseJson.name + '" crée avec succès !', 3000, 'rounded')
        } else {
            $("#courseNameCheck").hide();
            $("#courseNameError").show();
        }

    })
}

function addEventEditMode() {
    $("#editMode").click(function() {
        $("#courseName").replaceWith(function() {
            return "<input id='courseNameInput' placeholder='Saisissez le nom de la filiere' type='text' data-mode='edit' data-name=" + $(this).text() + " value=" + $(this).text() + ">";
        })
        $("#editMode").remove();
    })
}

function addSemestreOnClick() {

    $("#btn-addSemestre")
        .click(
            function() {
                var rowSemester = "<div class='row row-semestre'>" +
                    "<div class='card-panel'>" +
                    "<div class='semestre'>" +
                    "<div class='card-content card-content-semester'>" +
                    "<div class='row'>" +
                    "<div class='col s2'><span class='card-title card-title-semester'>Semestre 1</span></div>" +
                    "<div class='col s2 offset-s8 divIconDelete'><span><i class='small material-icons toggleIconSemester reduceIcon'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteSemestre'>clear</i></span></div>" +
                    "<div class='input-field col s12 div-ue'><a class='waves-effect waves-light btn btn-addUe'><i class='material-icons left'>add</i>Ajouter un UE</a></div>" +
                    "</div>" + "</div></div></div>" + "</div>";

                if ($(".courseNameValid").length != 0) {
                    if ($(".div-ue").length == 0) {
                        /*
                         * Pas de bloc semestre existant
                         */
                        $(".row-filiere").after(rowSemester);
                    } else {
                        /*
                         * On ajoute un bloc semestre apres le dernier
                         * existant
                         */
                        $(".row-semestre").last().after(rowSemester);
                    }
                    addUeOnClick();
                    resetElementIndex(".row-semestre",
                        ".card-title-semester", "Semestre");
                    deleteElementOnClick(".btn-deleteSemestre",
                        ".row-semestre", ".card-title-semester",
                        "Semestre");
                    toggleElementOnClick(".toggleIconSemester",
                        "toggleIconSemester", ".card-content-semester",
                        ".row-Ue")
                    postDataCreateSemester();
                    Materialize.toast('Semestre crée avec succès !', 3000, 'rounded')

                } else {
                    msg = "";
                    if ($("#courseNameInput").val() == "") {
                        msg = "Veuillez saisir un nom pour la filière"
                    } else {
                        msg = "Vous devez valider le nom de la filière"
                    }
                    showMessageError("#courseNameCheck", msg);

                }

            })

}

function postDataCreateSemester() {
    var url = "/responsable/createSemester";
    var semesterJson = {
        number: $(".row-semestre").last().find(".card-title-semester").attr("data-index"),
        branchName: $("#courseName").text()
    };
    $.post(url, semesterJson, function(id) {
        $(".row-semestre").last().find(".card-title-semester").attr("data-id", id);
        $(".row-semestre").last().find(".btn-addUe").attr("data-id", id);
    })
}

function postDataUpdateNumberSemester(rowSelector, cardTitleSelector) {
    $.each($(rowSelector), function(index, value) {
        var url = "/responsable/updateNumberSemester";
        var semesterJson = {
            id: $(value).find(cardTitleSelector).attr("data-id"),
            number: $(value).find(cardTitleSelector).attr("data-index"),
        };
        $.post(url, semesterJson, function(data) {

        })
    });

}

function postDataDeleteSemester(semesterSelector) {
    var url = "/responsable/deleteSemester";
    var id = semesterSelector.find(".card-title-semester").attr("data-id");
    url += "?id=" + id;
    $.post(url, function(data) {
        swal("Suppression !", "Le semestre a été supprimé !", "success");
    })
}

function postDataCreateUe(idSemester) {
    var url = "/responsable/createUe";
    var ueJson = {
        nom: "",
        number: $(".row-Ue").last().find(".card-title-ue").attr("data-index"),
        idSemester: idSemester
    }
    $.post(url, ueJson, function(id) {
        $(".row-Ue").last().find(".card-title-ue").attr("data-id", id);
    })

}

function postDataDeleteUe(ueSelector) {
    var url = "/responsable/deleteUe";
    var id = ueSelector.find(".card-title-ue").attr("data-id");
    url += "?id=" + id;
    $.post(url, function(data) {
        swal("Suppression !", "L'UE a été supprimé !", "success");
    })
}

function postDataUpdateNumberUe(semesterParentElement, cardTitleSelector) {
    $.each($(semesterParentElement).find(".row-Ue"), function(index, value) {
        var url = "/responsable/updateNumberUe";
        var semesterJson = {
            id: $(value).find(cardTitleSelector).attr("data-id"),
            number: $(value).find(cardTitleSelector).attr("data-index"),
        };
        $.post(url, semesterJson, function(data) {

        })
    });

}

function postDataMatiere() {
    $.post("", function(data) {
        var matiereData = {
            name: position,
            level: "",
            formation: "",
            average: "",
            date: ""
        };
    })

}

function addUeOnClick() {
    $(".btn-addUe")
        .off()
        .click(
            function() {
                var btnToggleUe = $(this).parent().prev().find(".extendIcon");
                if (btnToggleUe.length != 0) {
                    btnToggleUe.click();
                }
                var rowUe = "<div class='row row-Ue'>" +
                    "<div class='card-panel'>" +
                    "<div class='UE'>" +
                    "<div class='card-content card-content-ue'>" +
                    "<div class='row'>" +
                    "<div class='col s2'><span class='card-title card-title-ue'>UE 1</span></div>" +
                    "<div class='col s2 offset-s1'><span class='card-title card-title-coeff'>Coefficient : 0</span></div>" +
                    "<div class='col s2 offset-s5 divIconDelete'><span><i class='small material-icons toggleIconUe reduceIcon'>remove</i></span><span><i class='small material-icons deleteIcon btn-deleteUe'>clear</i></span></div></div>" +
                    "<div class='jsGrid'></div></div></div></div></div>";
                if ($(this).find(".row-Ue").length == 0) {
                    $(this).parents(".row-semestre .card-content")
                        .append(rowUe);
                } else {
                    $(this).parents(".row").find(".row-Ue").last()
                        .after(rowUe);
                }

                initJsGridLast($(this));
                resetUeNumber($(this).parents(".card-content-semester"));
                deleteElementOnClick(".btn-deleteUe", ".row-Ue",
                    ".card-title-ue", "UE");
                toggleElementOnClick(".toggleIconUe", "toggleIconUe",
                    ".card-content-ue", ".jsGrid");
                postDataCreateUe($(this).attr("data-id"));
                Materialize.toast('UE crée avec succès !', 3000, 'rounded')
            })
}

function showMessageError(selector, msg) {
    $(selector).text(msg);
    $(selector).show();
}

function resetUeNumber(semesterParentElement) {
    $.each($(semesterParentElement).find(".row-Ue"), function(index, value) {
        var ueNumber = index + 1;
        $(value).find(".card-title-ue").text("UE " + ueNumber).attr("data-index", ueNumber);;
    });

}

function resetElementIndex(rowSelector, cardTitleSelector, text) {
    $.each($(rowSelector), function(index, value) {
        var elementIndex = index + 1;
        $(value).find(cardTitleSelector).text(text + " " + elementIndex).attr("data-index", elementIndex);
    });

}

function deleteElementOnClick(btnSelector, rowSelector, cardTitleSelector, text) {
    $(btnSelector).off().click(function() {
        var $current = $(this);
        swal({
                title: "Etes-vous sûr ?",
                text: "Vous ne pourrez plus récupérer le contenu",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Oui",
                cancelButtonText: "Non",
                closeOnConfirm: false
            },
            function(isConfirm) {
                if (isConfirm) {

                    var semesterParentElement = $current.parents(".card-content-semester");
                    if (text == "UE") {
                        postDataDeleteUe($current.parents(rowSelector));
                    } else {
                        postDataDeleteSemester(semesterParentElement);

                    }
                    $current.parents(rowSelector).remove();
                    if (text == "UE") {
                        resetUeNumber(semesterParentElement);
                        postDataUpdateNumberUe(semesterParentElement, cardTitleSelector)
                    } else {
                        resetElementIndex(rowSelector, cardTitleSelector, text);
                        postDataUpdateNumberSemester(rowSelector, cardTitleSelector);

                    }

                } else {
                    swal("Annulation !", "Le contenu n'a pas été supprimé !", "error");
                }
            });
    });
}

function toggleIconSemesterOnClick() {
    $(".toggleIconSemester")
        .off()
        .click(

            function() {
                var btnHtml = $(this);
                $
                    .each(
                        $(this).parents(
                            ".card-content-semester").find(
                            ".row-Ue"),
                        function(index, value) {
                            $(value).toggle();
                            if (btnHtml.hasClass("extendIcon")) {
                                btnHtml
                                    .parent()
                                    .html(
                                        "<i class='small material-icons toggleIconSemester reduceIcon'>remove</i>");
                                toggleIconSemesterOnClick();
                            } else {
                                btnHtml
                                    .parent()
                                    .html(
                                        "<i class='small material-icons toggleIconSemester extendIcon'>crop_3_2</i>");
                                toggleIconSemesterOnClick();
                            }

                        });

            })

}

function toggleElementOnClick(toggleIconSelector, iconClass,
    cardContentSelector, elementToHide) {

    $(toggleIconSelector)
        .off()
        .click(
            function() {
                var template = "";
                var iconHtmlOutput = "";
                var btnHtml = $(this);
                var classElements = {
                    toggleIconClass: iconClass
                };
                $
                    .each(
                        $(this).parents(cardContentSelector)
                        .find(elementToHide),
                        function(index, value) {
                            $(value).toggle();

                            if (btnHtml.hasClass("extendIcon")) {
                                template = "<i class='small material-icons {{toggleIconClass}} reduceIcon'>remove</i>"
                                iconHtmlOutput = Mustache
                                    .render(template,
                                        classElements);
                            } else {
                                template = "<i class='small material-icons {{toggleIconClass}} extendIcon'>crop_3_2</i>"
                                iconHtmlOutput = Mustache
                                    .render(template,
                                        classElements);

                            }
                            btnHtml.parent().html(
                                iconHtmlOutput);
                            toggleElementOnClick(
                                toggleIconSelector,
                                iconClass,
                                cardContentSelector,
                                elementToHide);

                        });

            })
}

function toggleIconUeOnClick() {
    $(".toggleIconUe")
        .off()
        .click(
            function() {
                var btnHtml = $(this);
                $
                    .each(
                        $(this).parents(".card-content-ue")
                        .find(".jsGrid"),
                        function(index, value) {
                            $(value).toggle();
                            if (btnHtml.hasClass("extendIcon")) {
                                btnHtml
                                    .parent()
                                    .html(
                                        "<i class='small material-icons toggleIconUe reduceIcon'>remove</i>");
                                toggleIconUeOnClick();
                            } else {
                                btnHtml
                                    .parent()
                                    .html(
                                        "<i class='small material-icons toggleIconUe extendIcon'>crop_3_2</i>");
                                toggleIconUeOnClick();
                            }

                        });

            })

}

function checkIfSubjectNameExistInSemester(args) {
    var url = "/responsable/checkSubjectName";
    var json = {
        idSemester: "",
        subjectName: ""

    }
    json.idSemester = $("#courseName").attr("data-id");
    json.subjectName = args.item.Nom;
    $.ajax({
        type: "POST",
        async: false,
        url: "/responsable/checkSubjectName",
        data: json,
        dataType: "json",
        success: function(subjectNameExistInBranch) {
            if (subjectNameExistInBranch) {
                args.cancel = true;
                swal({
                    title: "Erreur",
                    text: "La matière " + json.subjectName + " existe déjà dans cette filière !",
                    type: "warning",
                    confirmButtonText: "Ok"
                });
            }
        }
    });
}

function addEventOpenModalOnClick() {

    $(".mccRules").click(function() {
        $("#modalMccRules").modal("open");
        validateForm($("#formMccRules"), addMccRules);
        //addMccRules($(this).parent());

    })

}

function removeClassActive() {
    $(".close").click(function() {
        var cell = $(this).parent().parent();
        if (cell.find(".chip").length == 1) {
            cell.removeClass("active");
        }
    })
}

function createMccRulesObject() {
    var listMccRules = [];
    $(".active").find(".chip").each(function(index, element) {
        listMccRules[index] = {
            name: $(element).data('name'),
            coefficient: $(element).data('coefficient'),
            markType: $(element).data('marktype')
        }
        $(".jsgrid-row .empty").removeClass("empty");

    })
    $(".jsgrid-row .empty")
    return listMccRules;
}

function addMccRules() {
    //$("#formMccRules").submit(function(event) {
    var mccRules = {
        name: $("#nameMccRules").val(),
        coefficient: $("#coefficientMccRules").val(),
        markType: $("#markType option:selected").val()
    }
    var html = "<div class='chip' data-name='{{name}}' data-coefficient='{{coefficient}}' data-markType='{{markType}}'>{{name}}<i class='close material-icons'>close</i></div>";
    var html = Mustache.render(html, mccRules);
    var cell = $("#formFiliere > div.row.row-semestre > div > div > div > div.row.row-Ue > div > div > div > div.jsGrid.jsgrid > div.jsgrid-grid-header.jsgrid-header-scrollbar > table > tr.jsgrid-insert-row > td.jsgrid-cell.cellMccRules.empty");
    $(cell).first().addClass("active");
    $(cell).first().append(html);
    $("#nameMccRules").val('');
    $("#coefficientMccRules").val('');

    $(cell).first().removeClass("empty");

    $(cell).first().removeClass("errorEmpty");

    $("#modalMccRules").modal("close");
    removeClassActive();
    //});
}

function newTypeJsGridMccRules() {
    var MyMccRulesField = function(config) {
        jsGrid.Field.call(this, config);
    };

    MyMccRulesField.prototype = new jsGrid.Field({

        itemTemplate: function(value) {
            return this._grid._container.find(".active").html();

        },

        insertTemplate: function(value) {
            return "<i class='material-icons mccRules'>add</i>";
        },

        editTemplate: function(value) {
            if (!this.editing) {
                return this.itemTemplate.apply(this, arguments);

            }
            return "";
        },

        insertValue: function() {
            return "";
        },

        editValue: function() {
            return "";
        }
    });

    jsGrid.fields.mccRules = MyMccRulesField;
}

function initJsGridLast(element) {

    newTypeJsGridMccRules();

    var reponse = [{
        Valeur: "Non",
        Id: 0
    }, {
        Valeur: "Oui",
        Id: 1
    }, ];

    $(element).parents(".card-content-semester").find(".jsGrid").last().jsGrid({
        width: "100%",
        height: "300px",

        inserting: true,
        editing: true,
        sorting: true,
        filtering: false,
        paging: false,
        confirmDeleting: false,

        invalidMessage: "Erreur lors de la saisie",
        invalidNotify: function(args) {
            var errorMsg = "";

            $.each(args.errors, function(item, value) {
                errorMsg += value.message + "\n";
            })
            swal("Oops...", errorMsg, "error");
        },

        onRefreshing: function(args) {
            addEventOpenModalOnClick();

        },

        onItemUpdating: function(args) {
            checkIfSubjectNameExistInSemester(args)

        },
        onItemDeleting: function(args) {
            $grid = args.grid._container;
            if (!args.item.deleteConfirmed) {
                args.cancel = true; // cancel deleting
                swal({
                    title: "Etes-vous sûr ?",
                    text: "Vous ne pourrez plus récupérer la matière " + args.item.Nom,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Oui",
                    cancelButtonText: "Non",
                    closeOnConfirm: false
                }, function(isConfirm) {
                    if (isConfirm) {
                        args.item.deleteConfirmed = true;
                        $grid.jsGrid('deleteItem', args.item)
                    } else {
                        swal("Annulation !", "La matière " + args.item.Nom + " n'a pas été supprimée !", "error");
                    }
                });
            }
        },
        onItemDeleted: function(args) {
            var url = "/responsable/deleteSubject";
            var objectJson = {
                subjectName: args.item.Nom,
                ueId: args.grid._container.parents(".card-content-ue").find(".card-title-ue").attr("data-id")
            }
            $.post(url, objectJson, function(ueCoefficient) {
                swal("Suppression !", "La matière " + args.item.Nom + " a été supprimée !", "success");
                args.grid._container.parents(".card-content-ue").find(".card-title-coeff").text("Coefficient : " + ueCoefficient)

            });
        },
        onItemInserting: function(args) {
            checkIfSubjectNameExistInSemester(args)
            if ($(".active").find(".chip").length == 0) {
                args.cancel = true;
                $(".active").addClass("jsgrid-invalid");
            }
        },

        onItemInserted: function(args) {
            var listMccRules = createMccRulesObject();
            var subjectJson = {
                name: "",
                coefficient: "",
                threshold: "",
                resit: "",
                year: "",
                ueId: "",
                listMccRulesDto: listMccRules
            }
            var url = "/responsable/createSubject";
            subjectJson.name = args.item.Nom;
            subjectJson.coefficient = args.item.Coefficient;
            subjectJson.threshold = args.item["Compensation"];
            subjectJson.resit = args.item.Rattrapable;
            subjectJson.year = args.item.Année;
            subjectJson.ueId = args.grid._container.parents(".card-content-ue").find(".card-title-ue").attr("data-id")

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(subjectJson),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function(data) {
                    args.grid._container.parents(".card-content-ue").find(".card-title-coeff").text("Coefficient : " + data.ueCoefficient);
                }
            })
        },
        onItemUpdated: function(args) {
            var subjectJson = {
                name: "",
                coefficient: "",
                threshold: "",
                resit: "",
                year: "",
                ueId: "",
                previousName: ""
            }
            var url = "/responsable/updateSubject";
            subjectJson.name = args.item.Nom;
            subjectJson.coefficient = args.item.Coefficient;
            subjectJson.threshold = args.item["Compensation"];
            subjectJson.resit = args.item.Rattrapable;
            subjectJson.year = args.item.Année;
            subjectJson.ueId = args.grid._container.parents(".card-content-ue").find(".card-title-ue").attr("data-id")
            subjectJson.previousName = args.previousItem.Nom;

            console.log(subjectJson);

            $.post(url, subjectJson, function(id) {

            });
        },

        noDataContent: "",

        /* data : matiere, */

        fields: [{
                name: "Nom",
                type: "text",
                align: "center",
                width: 80,
                validate: [{
                        validator: "required",
                        message: function(value, item) {
                            return "Saisissez le nom";
                        },
                    },
                    {
                        validator: function(value, item) {
                            return /^[a-zA-Z]+$/.test(value);
                        },
                        message: function(value, item) {
                            return "Le nom de la matière doit être composé uniquement de lettre";
                        }
                    }
                ],
            }, {
                name: "Coefficient",
                type: "number",
                align: "center",
                width: 45,
                validate: [{
                        validator: "required",
                        message: function(value, item) {
                            return "Saisissez le coefficient";
                        },
                    },
                    {
                        validator: "range",
                        message: function(value, item) {
                            return "Le coefficient doit être un entier positif";
                        },
                        param: [0, 100]
                    }
                ],
            }, {
                name: "Compensation",
                type: "number",
                align: "center",
                width: 58,
                validate: [{
                        validator: "required",
                        message: function(value, item) {
                            return "Saisissez le seuil de compensation";
                        },
                    },
                    {
                        validator: "range",
                        message: function(value, item) {
                            return "Le seuil de compensation doit être compris entre 0 et 10";
                        },
                        param: [0, 10]
                    }
                ],
            }, {
                name: "Rattrapable",
                type: "select",
                align: "center",
                width: 45,
                items: reponse,
                selectedIndex: 0,
                valueField: "Id",
                textField: "Valeur",
                validate: "required"
            },
            {
                name: "Règles MCC",
                type: "mccRules",
                css: "cellMccRules empty",
                /*
                validate: {
                    validator: function(value, item) {
                        $(".jsgrid-filter-row").remove();
                        if ($("td.empty").length == 1) {
                            args.cancel = true;
                            $("td.empty").addClass("errorEmpty");
                            return false;
                        }
                        return true;
                    },
                    message: function(value, item) {
                        return "Veuillez inserer une règle MCC";
                    },
                }
                */
            },
            {
                name: "Année",
                type: "number",
                align: "center",
                width: 30,
                validate: [{
                        validator: "required",
                        message: function(value, item) {
                            return "Saisissez l'année";
                        },
                    },
                    {
                        validator: function(value, item) {
                            var currentYear = new Date().getFullYear();
                            return value >= currentYear;
                        },
                        message: function(value, item) {
                            return "L'année doit être égale ou inférieur à : " + new Date().getFullYear();
                        },
                    }
                ]
            },
            {
                type: "control",
                width: 20
            }
        ]
    });

}