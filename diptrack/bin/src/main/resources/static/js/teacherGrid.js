$(function() {
	
var clients = [
        { "Nom": "TANDU", "Prénom": "Glodie", "Note Continue": 18, "Note Partiel": 15,},
        { "Nom": "BATTAIS", "Prénom": "Benjamin", "Note Continue": 6, "Note Partiel": 12,},
        { "Nom": "SEURIN", "Prénom": "Lucie", "Note Continue": 7, "Note Partiel": 16,},
        { "Nom": "VACHON", "Prénom": "Benoit", "Note Continue": 10, "Note Partiel": 2,},
        { "Nom": "ELGOFF", "Prénom": "Saher", "Note Continue": 12, "Note Partiel": 17},
  
        ];

    $("#jsGrid").jsGrid({
        width: "100%",
        //height: "400px",
        
 
        editing: true,
        sorting: true,
        paging: true,
 
        data: clients,
 
        fields: [
            { name: "Nom", width: 100, align: "center" },
            { name: "Prénom", width: 100, align: "center" },
            { name: "Note Continue", type: "number", width: 50, align: "center" },
            { name: "Note Partiel", type: "number", width: 50, align: "center" },
            { name: "Moyenne", width: 50, align: "center" },
            { type: "control", deleteButton: false, }
        ]
    });
    
});