$(function() {
    addMethodRegexInApiValidate();
});

function addMethodRegexInApiValidate() {
    jQuery.validator.addMethod("regex", function(value, element, regexp) {
        if (regexp.constructor != RegExp)
            regexp = new RegExp(regexp);
        else if (regexp.global)
            regexp.lastIndex = 0;
        return this.optional(element) || regexp.test(value);
    }, "erreur expression reguliere");
}

function validateForm(form, functionCallBackValidate, arg) {
    return form.validate({
        rules: {
            "mdp": {
                "required": true,
                "minlength": 8,
                "regex": /[A-z0-9]{8,}/
            },
            "confirm_password": {
                "required": true,
                "equalTo": '#password'
            },
            "mail": {
                "required": true,
                "regex": /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
            },
            "lastName": {
                "required": true,
                "regex": /^([ \u00c0-\u01ffa-zA-Z'\-])+$/
            },
            "firstName": {
                "required": true,
                "regex": /^([ \u00c0-\u01ffa-zA-Z'\-])+$/
            },
            "studentNumber": {
                "required": true,
                "regex": /[0-9]{8}/
            },
            "name": {
                "required": true,
                "regex": /[A-Za-z0-9_]/
            },
            "coefficient": {
                "required": true,
                "regex": /^[1-9]\d*$/g
            },

            //CLASSE
            "nom_classe": {
                "required": true,
                "regex": /^([A-Za-z0-9])+$/
            }
        },
        // For custom messages
        messages: {
            mdp: {
                required: "Saisissez un mot de passe",
                minlength: "Saisissez au moins 8 caractères (sans accents)"
            },
            confirm_password: {
                required: "Resaisissez un mot de passe",
                equalTo: "Les mots de passes doivent être identiques"
            },
            mail: {
                required: "Saisissez une adresse email",
                regex: "Le format de l'adresse email est incorrecte",
            },
            lastName: {
                required: "Saisissez un nom",
                regex: "Le format de saisie est incorrecte",
            },
            firstName: {
                required: "Saisissez un prénom",
                regex: "Le format de saisie est incorrecte",
            },
            studentNumber: {
                required: "Saisissez un numero étudiant",
                regex: "Le numéro étudiant est composé de 8 chiffres",
            },
            name: {
                required: "Saisissez le nom de la règle MCC",
                regex: "Le format de saisie est incorrecte"
            },
            coefficient: {
                required: "Saisissez un coefficient",
                range: "Le coefficient doit être positif",
                number:"Veuillez saisir un numerique"
            },

            //CLASSE
            nom_classe: {
                required: "Saisissez l'intitulé de votre classe",
                regex: "Le format saisie est incorrecte",
            }
        },
        errorElement: 'div',
        errorPlacement: function(error, element) {
            var placement = $(element).data('error');
            if (placement) {
                $(placement).append(error)
            } else {
                error.insertAfter(element);
            }

        },

        submitHandler: function() {
            // do other things for a valid form
            if (functionCallBackValidate != undefined) {
                functionCallBackValidate(arg);
            } else {
                form.unbind().submit();
            }
        }
    });
}