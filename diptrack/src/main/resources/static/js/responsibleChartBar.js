$(function() {
	$('select').material_select();
	validateForm();
});

var ctx = document.getElementById("chartBar").getContext('2d');
var myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: ["PROJET", "MELFORG", "ANGLAIS", "TECHNOWEB"],
    datasets: [{
      label: 'Personnel',
      data: [12, 19, 3, 17],
      backgroundColor : 'rgba(54, 162, 235, 0.5)',
      borderColor : 'rgba(54, 162, 235, 1)',
      borderWidth : 1
    }, {
      label: 'Moyenne',
      data: [10, 14, 16, 4],
      backgroundColor: 'rgba(255, 99, 132, 0.5)',
      borderColor : 'rgba(255,99,132,1)',
      borderWidth : 1
    }]
  },
  options: { 
      legend: {
    	  labels:{
    		  //fontColor:"white",
    		  fontSize: 18,
    		  }
      	  },
    	        
      scales: {
          yAxes: [{
              ticks: {
                  //fontColor: "white",
                  fontSize: 14,
                  stepSize: 2,
                  beginAtZero:true
              }
          }],
          xAxes: [{
              ticks: {
                  //fontColor: "white",
              }
          }]
      }
  }
});