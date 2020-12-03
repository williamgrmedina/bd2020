/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
  $('#tabela_produtos').DataTable({
	  "order": [[4, "asc"]],
	  columnDefs: [{
	  orderable: false,
	  targets: [0, 1, 2, 3]
	  }]
  });
  $('.dataTables_length').addClass('bs-select');
});

$(document).ready(function () {
	$(document).on('click', '.link_excluir_produto', deleteProduto);  
    $(document).on('click', '.link_editar_produto_nome', editarProdutoNome);
	$("*[data-toggle='tooltip']").tooltip({
        'container': 'body',
		trigger: "hover"
    });
});

function deleteProduto(e) {
    e.preventDefault();
    $('.link_confirmacao_excluir_produto').attr('href', $(this).data('href'));
    $('.modal_excluir_produto').modal();
}

function editarProdutoNome(e) {
    e.preventDefault();
    $('.modal_editar_produto_nome').modal();
}

$('#modal_edit_prod_nome').on('show.bs.modal', function (event) {
	var prev_name = $(event.relatedTarget).data('nome');
	$(this).find(".modal-nome").text(prev_name);
	var prod_id = $(event.relatedTarget).data('id');
	document.getElementById("prod_id").value = prod_id;
});

$('#modal_edit_prod_val_compra').on('show.bs.modal', function (event) {
	var name = $(event.relatedTarget).data('nome');
	$(this).find(".modal-nome").text(name);
	var prod_id = $(event.relatedTarget).data('id');
	document.getElementById("prod_id_c").value = prod_id;
});

$('#modal_edit_prod_val_venda').on('show.bs.modal', function (event) {
	var name = $(event.relatedTarget).data('nome');
	$(this).find(".modal-nome").text(name);
	var prod_id = $(event.relatedTarget).data('id');
	document.getElementById("prod_id_v").value = prod_id;
});

$('#modal_edit_prod_qtd').on('show.bs.modal', function (event) {
	var name = $(event.relatedTarget).data('nome');
	$(this).find(".modal-nome").text(name);
	var prod_id = $(event.relatedTarget).data('id');
	document.getElementById("prod_id_q").value = prod_id;
});