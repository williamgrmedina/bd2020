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
});

$(document).ready(function () {
	$(document).on('click', '.link_excluir_produto', deleteProduto);  
	$(document).on('click', '.button_confirmacao_excluir_produtos', deleteProdutos);
	
	$("*[data-toggle='tooltip']").tooltip({
        'container': 'body',
		trigger: "hover"
    });
	
	$('#modal_edit_prod_nome').on('show.bs.modal', function (event) {
		var name = $(event.relatedTarget).data('nome');
		$(this).find(".modal-nome").text(name);
		var prod_id = $(event.relatedTarget).data('id');
		document.getElementById("prod_id_n").value = prod_id;
	});

	$('#form_alter_name').on('submit', function (event) {
		event.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		var id = $form.find("input[name='prod_id_n']").val();
		var nome = $form.find("input[name='nome']").val();
		
		$.post(url, {id:id, nome:nome}, function() {
			$('#modal_edit_prod_nome').modal('hide');
			document.location.reload();
		});
	});

	$('#modal_edit_prod_val_compra').on('show.bs.modal', function (event) {
		var name = $(event.relatedTarget).data('nome');
		$(this).find(".modal-nome").text(name);
		var prod_id = $(event.relatedTarget).data('id');
		document.getElementById("prod_id_c").value = prod_id;
	});
	
	$('#form_alter_prod_compra').on('submit', function (event) {
		event.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		var id = $form.find("input[name='prod_id_c']").val();
		var value = $form.find("input[name='value']").val();
		
		$.post(url, {id:id, value:value}, function() {
			$('#modal_edit_prod_val_compra').hide();
			document.location.reload();
		});
	});

	$('#modal_edit_prod_val_venda').on('show.bs.modal', function (event) {
		var name = $(event.relatedTarget).data('nome');
		$(this).find(".modal-nome").text(name);
		var prod_id = $(event.relatedTarget).data('id');
		document.getElementById("prod_id_v").value = prod_id;
	});

	$('#form_alter_prod_venda').on('submit', function (event) {
		event.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		var id = $form.find("input[name='prod_id_v']").val();
		var value = $form.find("input[name='value']").val();
		
		$.post(url, {id:id, value:value}, function() {
			$('#modal_edit_prod_val_venda').modal('hide');
			document.location.reload();
		});
	});

	$('#modal_edit_prod_qtd').on('show.bs.modal', function (event) {
		var name = $(event.relatedTarget).data('nome');
		$(this).find(".modal-nome").text(name);
		var prod_id = $(event.relatedTarget).data('id');
		document.getElementById("prod_id_q").value = prod_id;
	});
	
	$('#form_alter_prod_qtd').on('submit', function (event) {
		event.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		var id = $form.find("input[name='prod_id_q']").val();
		var value = $form.find("input[name='value']").val();
		
		$.post(url, {id:id, value:value}, function() {
			hideModal('modal_edit_prod_val_qtd');
			document.location.reload();
		});
	});
	
	$('.form_criar_pedido').on('submit', createProduto);
	
	var $chkboxes = $('.checkbox');
	var lastChecked = null;

	$chkboxes.click(function(e) {
		if (!lastChecked) {
			lastChecked = this;
			return;
		}

		if (e.shiftKey) {
			var start = $chkboxes.index(this);
			var end = $chkboxes.index(lastChecked);

			$chkboxes.slice(Math.min(start,end), Math.max(start,end)+ 1).prop('checked', lastChecked.checked);
		}

		lastChecked = this;
	});
});

function createProduto(e) {
	e.preventDefault();
	var $form = $(this),
			url = $form.attr( "action" ),
			nome = $form.find( "input[name='nome']" ).val(),
			val_compra = $form.find( "input[name='val_compra']" ).val(),
			val_venda = $form.find( "input[name='val_venda']" ).val(),
			qtd = $form.find( "input[name='qtd']" ).val();
	if(isNaN(val_compra)){
		alert("Erro: valor de compra deve ser um número positivo.");
	}else if (val_compra < 0){
		alert("Erro: valor de compra deve ser um número positivo.");
	}
	
	else if(isNaN(val_venda)){
		alert("Erro: valor de venda deve ser um número positivo.");
	}else if (val_venda < 0){
		alert("Erro: valor de venda deve ser um número positivo.");
	}
	
	$.post(url, {nome:nome, val_compra:val_compra, val_venda:val_venda, qtd:qtd})
		.done(function (data){
			alert(data);
			window.location.replace(data);
		});
}

function deleteProduto(e) {
    e.preventDefault();
    $('.link_confirmacao_excluir_produto').attr('href', $(this).data('href'));
    $('.modal_excluir_produto').modal();
}

function deleteProdutos(e) {
	e.preventDefault();
	$('.form_excluir_produtos').submit();
}

var hideInProgress = false;
var showModalId = '';

function showModal(elementId) {
	if (hideInProgress) {
		showModalId = elementId;
    } else {
        $("#" + elementId).modal("show");
    }
};

function hideModal(elementId) {
    hideInProgress = true;
    $("#" + elementId).on('hidden.bs.modal', hideCompleted);
    $("#" + elementId).modal("hide");

    function hideCompleted() {
		hideInProgress = false;
        if (showModalId) {
            showModal(showModalId);
        }
        showModalId = '';
        $("#" + elementId).off('hidden.bs.modal');
    }
};

/*function editarProdutoNome(e) {
    e.preventDefault();
    $('#modal_edit_prod_nome').modal();
}

function editarProdutoValorCompra(e) {
    e.preventDefault();
    $('#modal_edit_prod_val_compra').modal();
}

function editarProdutoValorVenda(e) {
    e.preventDefault();
    $('#modal_edit_prod_val_venda').modal();
}

function editarProdutoQtd(e) {
    e.preventDefault();
    $('#modal_edit_prod_qtd').modal();
}*/