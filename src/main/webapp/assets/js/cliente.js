/*
 * Checks whether the password and password-confirm fields match
 */

$(document).on('focusout', '.password-input,.password-confirm', function(e) {
    var $form = $(this).closest("form");
    var $password = $form.find(".password-input");
    var $passwordConfirm = $form.find(".password-confirm");

    if ($password.val().trim() == '') {
        return false;
    }

    if ($password.val() !== $passwordConfirm.val()) {
        $passwordConfirm.closest('.form-group').addClass('has-error');
        $password.closest('.form-group').addClass('has-error');
        $passwordConfirm.next('p.help-block').html('<strong>Erro</strong>: as senhas não coincidem!');
        $form.find("button,input[type='submit']").prop('disabled', true);
    } else {
        $passwordConfirm.closest('.form-group').removeClass('has-error').addClass('has-success');
        $password.closest('.form-group').removeClass('has-error').addClass('has-success');
        $passwordConfirm.next('p.help-block').html('');
        $form.find("button,input[type='submit']").prop('disabled', false);
    }
});

$(document).on('focusout', '#cliente-login', function (e) {
    var $input = $(this);
    if ($("#cliente-login").val() == $(this).data('value')) {
        var $formGroup = $input.parents(".form-group").first();
        if ($formGroup.hasClass("has-error")) {
            $formGroup.removeClass("has-error");
        }
        $input.next("p").html("");
    }
    else {
        $.post($.url("//user/checkLogin"), { login: $("#cliente-login").val() }, function(data) {
            var $formGroup = $input.parents(".form-group").first();
            if (data.status == "USADO") {
                if (!$formGroup.hasClass("has-error")) {
                    $formGroup.addClass("has-error");
                }
                $input.next("p").html("O login escolhido existe. Por favor, tente outro.");
            } else {
                if ($formGroup.hasClass("has-error")) {
                    $formGroup.removeClass("has-error");
                }
                $input.next("p").html("");
            }
        });
    }
});

$(document).ready(function () {
  $('#lista_clientes').DataTable();
  $('.dataTables_length').addClass('bs-select');
});

$('#my-modal').on('show.bs.modal', function (event) {
  var login = $(event.relatedTarget).data('login');
  $(this).find(".modal-login").text(nome);
  var nome = $(event.relatedTarget).data('nome');
  $(this).find(".modal-nome").text(login);
  var salario = $(event.relatedTarget).data('salario');
  $(this).find(".modal-salario").text(salario);
  var data_efetivacao = $(event.relatedTarget).data('data_efetivacao');
  $(this).find(".modal-data_efetivacao").text(data_efetivacao);
});