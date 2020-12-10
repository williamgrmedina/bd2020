/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
  $('#tabela_produtos').DataTable({
	  "order": [[4, "asc"]]
  });
  $('.dataTables_length').addClass('bs-select');
  
  /*var table = $('#tabela_produtos').DataTable();
	
  $('#tabela_produtos').on( 'click', '.link_adicionar_produto', function (data) {
		console.log(table.row(3).data());
		var data = table.row(3).data();
		console.log(data[5]);
		var string = data[5];
		var val = getSpanValue(string);
		console.log(val);
		data[5] = '<span class="h6 qtd">' + (val + 1) + '</span>';
		console.log(table.row(3).data());
		table.row(3).data(data).draw();
  });*/
 
 $(document).on('click', '.link_adicionar_produto', adicionarProduto);  
 $(document).on('click', '.link_remover_produto', removerProduto);  
 
 $('body').tooltip({
	selector: '[data-toggle="tooltip"]',
	trigger: 'hover'
	}).on('click mousedown mouseup', '[data-toggle="tooltip"], [title]:not([data-toggle="popover"])', function () {
	  $('[data-toggle="tooltip"], [title]:not([data-toggle="popover"])').tooltip('dispose');
  });
});

function adicionarProduto(e){
	e.preventDefault();
	var table = $('#tabela_produtos').DataTable();   
	var index = $(this).attr( "data-index");

	var requisicoes = getSpanVal(table, index, 5);
	var disp = getSpanVal(table, index, 3);
	
	if(disp === 0){
		alert("Erro. Produto fora de estoque.");
	}
	else{
		setSpanVal(table, index, 5, requisicoes + 1);
		setSpanVal(table, index, 3, disp - 1);
		if( (disp - 1) == 0 ){ //acabaram os produtos
			var node = table.row(index).node();
			$(node).removeClass("info-color");
			$(node).addClass("danger-color");
		}
	}
} 

function removerProduto(e){
	e.preventDefault();
	var table = $('#tabela_produtos').DataTable();   
	var index = $(this).attr( "data-index");

	var requisicoes = getSpanVal(table, index, 5);
	var disp = getSpanVal(table, index, 3);
	
	if(requisicoes === 0){
		alert("Não há produtos a serem removidos.");
	}
	else{
		setSpanVal(table, index, 5, requisicoes - 1);
		setSpanVal(table, index, 3, disp + 1);
		if( (disp) == 0 ){ //produtos eram zero anteriormente
			var node = table.row(index).node();
			$(node).removeClass("danger-color");
			$(node).addClass("info-color");
		}
	}
}

function getSpanVal(table, row, col){
	var data = table.row(row).data();
	var val = getSpanValue(data[col]);
	return val;
}

function getSpanValue(string) {
	return parseInt(string.substring
		(
			string.indexOf(">") + 1,
			string.lastIndexOf("</span")
		));
}

function setSpanVal(table, row, col, value){
	var rowData = table.row(row).data();
	var colData = rowData[col];
	var prefix = colData.substring(0, colData.indexOf(">") + 1);
	var postFix = colData.substring(colData.lastIndexOf("</span"));
	var newColData = prefix + value + postFix; //this is a HTML string
	rowData[col] = newColData;
	console.log(table.row(row).node());	
	table.row(row).data(rowData).draw();
}