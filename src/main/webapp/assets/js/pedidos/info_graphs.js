/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Chart */
// Chart is a variable from Chart.js library. For more info check Chart.js API documentation */



$(document).ready(function () {
    populateSelect();

    var pie_chart = document.getElementById("pie_chart");
    var data_pie, config_pie, pieChart, data_line, config_line, lineChart, barChart;

    $.get(pie_chart.getAttribute('href'), {year: $('#year_selector').val()}, function (jsonString) {
        var obj = JSON.parse(jsonString);

        data_pie = {
            labels: [
                'Pedidos concluídos',
                'cancelamentos'
            ],
            datasets: [{
                    data: obj[0],
                    backgroundColor: [
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
        };

        config_pie = {
            type: 'pie',
            data: data_pie
        };

        pieChart = new Chart(
                document.getElementById('pie_chart'),
                config_pie
                );

        var $selector = $(document).find('#year_selector');
        var first_year = $selector.attr('first_year');
        var last_year = $selector.attr('last_year');
        populateSelect($selector, last_year - 1, first_year);
        
        data_line = {
            labels: months,
            datasets: [{
                    label: 'Pedidos Online',
                    data: obj[2],
                    fill: false,
                    borderColor: '#FFAEBC',
                    tension: 0.1
                }, {
                    label: 'Pedidos Presenciais',
                    data: obj[1],
                    fill: false,
                    borderColor: '#A0E7E5',
                    tension: 0.1
                }
            ]
        };

        config_line = {
            type: 'line',
            data: data_line
        };

        lineChart = new Chart(
                document.getElementById('line_chart'),
                config_line
                );
        
        data_line = {
            labels: months,
            datasets: [{
                    label: 'Receitas',
                    data: obj[3],
                    fill: false,
                    borderColor: '#00FF00',
                    tension: 0.1
                }, {
                    label: 'Despesas',
                    data: obj[4],
                    fill: false,
                    borderColor: '#FF0000',
                    tension: 0.1
                }
            ]
        };

        config_line = {
            type: 'line',
            data: data_line
        };
        
        receitas_despesasChart = new Chart(
                document.getElementById('receitas_despesas_chart'),
                config_line
                );
    });

    const months = [
        'Janeiro',
        'Fevereiro',
        'Março',
        'Abril',
        'Maio',
        'Junho',
        'Julho',
        'Agosto',
        'Setembro',
        'Outubro',
        'Novembro',
        'Dezembro'
    ];


    $("#year_selector").on('change', async function () {
        var year = $('#year_selector').val();
        updateCharts(year);
    });

    populateMonths($("#month_selector_2"), months);
    var year_selector = $("#year_selector_2");
    populateSelect(year_selector, year_selector.attr("last_year") - 1, year_selector.attr("first_year"));



    var bar_chart = $("#bar_chart");
    $.get(bar_chart.attr('href'), {month: $('#month_selector_2').val(), year: $('#year_selector_2').val()}, function (jsonString) {
        var obj = JSON.parse(jsonString);

        let {funcionarios, pedidos_atendidos, avg_line} = findBarChartData(obj);

        var data = {
            labels: funcionarios,
            datasets: [{
                    type: 'bar',
                    label: 'Pedidos por funcionário',
                    data: pedidos_atendidos,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 205, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(201, 203, 207, 0.2)'
                    ],
                    borderColor: [
                        'rgb(255, 99, 132)',
                        'rgb(255, 159, 64)',
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)',
                        'rgb(54, 162, 235)',
                        'rgb(153, 102, 255)',
                        'rgb(201, 203, 207)'
                    ],
                    borderWidth: 1
                },
                {
                    type: 'line',
                    label: 'média',
                    data: avg_line,
                    borderDash: [10, 2]
                }]
        };

        var config = {
            data: data,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                elements: {
                    point: {
                        radius: 0
                    }
                }
            }
        };

        barChart = new Chart(
                document.getElementById('bar_chart'),
                config
                );
    });


    $("#year_selector_2, #month_selector_2").on('change', async function () {
        var month = $('#month_selector_2').val();
        var year = $('#year_selector_2').val();
        updateFuncionarioPedidosChart(month, year);
    });


});

function populateSelect(selector, last_year, first_year) {
    for (var i = last_year; i >= first_year; i--) {
        selector.append('<option value=' + i + '>' + i + '</option>');
    }
}
;

function populateMonths(selector, month_array) {
    for (var i = 2; i <= month_array.length; i++) {
        selector.append('<option value=' + (i) + '>' + month_array[i - 1] + '</option>');
    }
}
;

function updateCharts(year) {
    var pie_chart = document.getElementById("pie_chart");
    $.get(pie_chart.getAttribute('href'), {year: year}, function (jsonString) {
        var obj = JSON.parse(jsonString);

        var pie_chart = Chart.getChart("pie_chart");
        pie_chart.data.datasets[0].data = obj[0];
        pie_chart.update();

        var line_chart = Chart.getChart("line_chart");
        line_chart.data.datasets[0].data = obj[2];
        line_chart.data.datasets[1].data = obj[1];
        line_chart.update();
        
        var receitas_despesas_chart = Chart.getChart("receitas_despesas_chart");
        receitas_despesas_chart.data.datasets[0].data = obj[3];
        receitas_despesas_chart.data.datasets[1].data = obj[4];
        receitas_despesas_chart.update();
        
    });
}
;

function updateFuncionarioPedidosChart(month, year) {
    var chart = $("#bar_chart");

    $.get(chart.attr('href'), {month: month, year: year}, function (jsonString) {
        var obj = JSON.parse(jsonString);
        
        let {funcionarios, pedidos_atendidos, avg_line} = findBarChartData(obj);

        var bar_chart = Chart.getChart("bar_chart");
        bar_chart.labels = funcionarios;
        bar_chart.data.datasets[0].data = pedidos_atendidos;
        bar_chart.data.datasets[1].data = avg_line;
        bar_chart.update();
        console.log(bar_chart.data);
        
    });
}

function findBarChartData(obj) {

    var funcionarios = [];
    var pedidos_atendidos = [];
    var total = 0;
    var avg = 0;
    var avg_line = [];

    obj.forEach(function (curValue) {
        funcionarios.push(curValue.nome);
        pedidos_atendidos.push(curValue.qtd);
        total += curValue.qtd;
    });
    avg = Math.round((total / (obj.length)));

    for (var i = 0; i < funcionarios.length; i++) {
        avg_line.push(avg);
    }

    return {funcionarios : funcionarios, pedidos_atendidos : pedidos_atendidos, avg_line : avg_line};
}