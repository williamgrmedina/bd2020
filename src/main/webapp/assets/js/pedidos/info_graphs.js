/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const labels = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June'
];
const data = {
    labels: labels,
    datasets: [{
            label: 'My First dataset',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [0, 10, 5, 2, 20, 30, 45]
        }]
};

const config = {
  type: 'line',
  data,
  options: {}
};

var myChart = new Chart(
        document.getElementById('myChart'),
        config
        );
