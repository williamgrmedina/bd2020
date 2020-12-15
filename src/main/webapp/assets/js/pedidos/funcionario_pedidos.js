/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
  
	$('#tabela_pedidos').DataTable({
		"order": [[0, "asc"]]
	});
	$('.dataTables_length').addClass('bs-select');
	
	$(document).on('click', '.link_confirmar_entrega', confirmarEntrega);
	$(document).on('click', '.link_confirmacao_entrega', function(e){
		e.preventDefault();
		var url = $(this).attr('href');
		$.get(url, function(data){
			location.replace(data);
		});
	});
	
	$(document).on('click', '.link_confirmar_pgmt', confirmarPagamento);
	$(document).on('click', '.link_confirmacao_pgmt', function(e){
		e.preventDefault();
		var url = $(this).attr('href');
		$.get(url, function(data){
			location.replace(data);
		});
	});
	
	$(document).on('click', '.link_cancelar', cancelarProduto);
	$(document).on('click', '.link_confirmacao_cancelar', function(e){
		e.preventDefault();
		var url = $(this).attr('href');
		$.get(url, function(data){
			location.replace(data);
		});
	});
});

function confirmarEntrega(e) {
    e.preventDefault();
	$('.link_confirmacao_entrega').attr('href', $(this).data('href'));
	$('.modal_confirmar_entrega').modal();
}

function confirmarPagamento(e) {
    e.preventDefault();
	$('.link_confirmacao_pgmt').attr('href', $(this).data('href'));
	$('.modal_confirmar_pgmt').modal();
}

function cancelarProduto(e) {
    e.preventDefault();
	$('.link_confirmacao_cancelar').attr('href', $(this).data('href'));
	$('.modal_cancelar').modal();
}