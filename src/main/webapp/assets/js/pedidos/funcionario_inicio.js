/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
  
  $('body').tooltip({
	  selector: '[data-toggle="tooltip"]',
	  trigger: 'hover'
	  }).on('click mousedown mouseup', '[data-toggle="tooltip"], [title]:not([data-toggle="popover"])', function () {
        $('[data-toggle="tooltip"], [title]:not([data-toggle="popover"])').tooltip('dispose');
    });
 
 $(document).on('click', '.link_adicionar_produto', adicionarProduto);  
 $(document).on('click', '.link_remover_produto', removerProduto);  
});

function adicionarProduto(e){
	e.preventDefault();
	alert(window.location.pathname);
	  
	var url = $(this).attr( "data-href");
	var qtd = parseInt($(this).attr( "data-qtd"));
	
	var index = $(this).attr( "data-index");
	
	if(qtd === 0){
		alert("Erro. Produto fora de estoque.");
	}
	else{
		$.post(url, {idx : index}, function (){
			var table = $('#tabela_produtos').DataTable();
			table.ajax.reload();
		})
		.fail(function(data) {
			alert("ocorreu um erro na solicitação do pedido.\n" +
				   "Tente novamente ou contate um administrador.");
		    location.replace(data);
		});
	}
} 

function removerProduto(e){
	e.preventDefault();
	var url = $(this).attr( "data-href");
	var qtd = parseInt($(this).attr( "data-qtd"));
	
	var index = $(this).attr( "data-index");
	
	if(qtd === 0){
		alert("Não há produto a ser removido.");
	}
	else{
		$.post(url, {idx : index}, function (data){
			location.replace(data);
		})
		.fail(function(data) {
			alert("ocorreu um erro na solicitação do pedido.\n" +
				   "Tente novamente ou contate um administrador.");
		    location.replace(data);
		});
	}
} 