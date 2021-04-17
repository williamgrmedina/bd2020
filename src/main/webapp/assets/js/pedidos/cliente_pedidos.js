/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    $(document).on('click', '.link_cancelar_pedido', cancelarPedido);
    $(document).on('click', '.link_confirmacao_cancelar', function (e) {
        e.preventDefault();
        var url = $(this).attr('href');
        $.get(url, function (data) {
            location.replace(data);
        });
    });
    
});

function cancelarPedido(e) {
    e.preventDefault();
    $('.link_confirmacao_cancelar').attr('href', $(this).data('href'));
    $('.modal_cancelar_pedido').modal();
}