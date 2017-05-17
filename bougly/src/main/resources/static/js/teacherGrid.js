$(function() {
	
var clients = [
        { "Nom": "TANDU", "Prénom": "Glodie", "Note Continue": 18, "Note Partiel": 15,},
        { "Nom": "BATTAIS", "Prénom": "Benjamin", "Note Continue": 6, "Note Partiel": 12,},
        { "Nom": "SEURIN", "Prénom": "Lucie", "Note Continue": 7, "Note Partiel": 16,},
        { "Nom": "VACHON", "Prénom": "Benoit", "Note Continue": 10, "Note Partiel": 2,},
        { "Nom": "ELGOFF", "Prénom": "Saher", "Note Continue": 12, "Note Partiel": 17,},
    ];
  
    $("#jsGrid").jsGrid({
        width: "100%",
        //height: "400px",
 
        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
 
        data: clients,
 
        fields: [
            { name: "Nom", type: "text", width: 100, validate: "required" },
            { name: "Prénom", type: "text", width: 100 },
            { name: "Note Continue", type: "number", width: 50 },
            { name: "Note Partiel", type: "number", width: 50 },
            { type: "control" }
        ]
    });
    
});