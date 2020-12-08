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
  
  $("*[data-toggle='tooltip']").tooltip({
		'container': 'body',
		trigger: "hover"
	});

 $(document).on('click', '.new_pedido', createPedidoSession);  
 $(document).on('click', '.link_adicionar_produto', adicionarProduto);  
 $(document).on('click', '.link_remover_produto', removerProduto);  
});

function createPedidoSession(e){
	e.preventDefault();
	var url = $(this).attr( "href" );
	$.post(url)
		.done(function(){
			location.replace(url);
		});
}

function adicionarProduto(e){
	e.preventDefault();
	var url = $(this).attr( "data-href");
	var qtd = parseInt($(this).attr( "data-qtd"));
	
	var index = $(this).attr( "data-index");
	
	if(qtd === 0){
		alert("Erro. Produto fora de estoque.");
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