/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
  $('#tabela_produtos').DataTable({
	  "order": [[4, "asc"]],
	   "columnDefs": [
            {
                "targets": [ 3 ],
                "visible": false,
                "searchable": false
            }
	   ]
  });
  $('.dataTables_length').addClass('bs-select');
 
 $(document).on('click', '.link_adicionar_produto', adicionarProduto);  
 $(document).on('click', '.link_remover_produto', removerProduto); 
 $(document).on('click', '.link_confirmar_pedido', confirmarPedido); 
 
 $('body').tooltip({
	selector: '[data-toggle="tooltip"]',
	trigger: 'hover'
	}).on('click mousedown mouseup', '[data-toggle="tooltip"], [title]:not([data-toggle="popover"])', function () {
	  $('[data-toggle="tooltip"], [title]:not([data-toggle="popover"])').tooltip('dispose');
  });
  
  $('.modal_error').on('hidden.bs.modal', function () {
    location.reload();
  });

  $('#form_obs').submit(function(e){
	e.preventDefault();
	$('#modal_obs').modal('hide');
  });
  
  $('#modal_obs').on('hidden.bs.modal', function(e){
		var $form = $('#form_obs');
		var	observacao = $form.find("input[name='observacao']").val();
		var table = $('#tabela_produtos').DataTable();
		var rowCount = $('#tabela_produtos tbody tr').length - 1;
		var i, id, qtd;
		var solicitados = [];

		for(i=0; i<rowCount; i++){
			id = getSpanVal(table, i, 0); //coluna id
			qtd = getSpanVal(table, i, 5); //coluna Qtd
			if(qtd > 0){ 
				var info = [id, qtd];
				solicitados.push(info);
			}
		}

		var comanda = parseInt($('#comanda').val(), 10);
		var items = JSON.stringify(solicitados);
		var url = $form.attr( "href");
		$.post(url, {items:items, comanda:comanda, observacao:observacao}, function(data){
			alert("pedido registrado.");
			location.replace(data);
		}).fail(function() {
			$('.modal_error').modal();
		});
	});
});

function adicionarProduto(e){
	e.preventDefault();
	var table = $('#tabela_produtos').DataTable();   
	var index = $(this).attr( "data-index");

	var requisicoes = getSpanVal(table, index, 5);
	var disp = getSpanVal(table, index, 3);
	
	if(disp === 0){
		alert("Desculpe. No momento este produto está fora de estoque :(");
	}
	else{
		setSpanVal(table, index, 5, requisicoes + 1);
		setSpanVal(table, index, 3, disp - 1);
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
		), 10);
}

function setSpanVal(table, row, col, value){
	var rowData = table.row(row).data();
	var colData = rowData[col];
	var prefix = colData.substring(0, colData.indexOf(">") + 1);
	var postFix = colData.substring(colData.lastIndexOf("</span"));
	var newColData = prefix + value + postFix; //this is a HTML string
	rowData[col] = newColData;
	table.row(row).data(rowData).draw();
}

function confirmarPedido(e){
	e.preventDefault();
		$('#modal_obs').modal({backdrop: 'static', keyboard: false});
		//from here activates $('#form_obs').submit function
}
