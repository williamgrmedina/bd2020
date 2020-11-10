<%-- 
    Document   : create
    Created on : Sep. 1, 2020, 11:27:40 a.m.
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_gerente context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Cadastro de funcionários</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Inserção de um novo funcionário</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/funcionario/create"
                enctype="multipart/form-data"
                method="POST">

                <div class="form-group">
                    <label class="control-label" for="funcionario-login">Login</label>
                    <input id="funcionario-login" class="form-control" type="text" name="login" required autofocus/>

                    <p class="help-block"></p>
                </div>


                <div class="form-group">
                    <label class="control-label">Senha</label>
                    <input class="form-control password-input"
                           type="password" name="senha"
                           pattern=".{8,}" required title="Pelo menos 8 caracteres."/>
                </div>

                <div class="form-group pwd-confirm">
                    <label class="control-label">Confirmar senha</label>
                    <input class="form-control password-confirm"
                           type="password" name="senha-confirmacao"
                           pattern=".{8,}" required title="Pelo menos 8 caracteres."/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label for="funcionario-nome" class="control-label">Nome</label>
                    <input id="funcionario-nome" class="form-control" type="text" name="nome" required/>
                </div>
				
				<div class="form-group">
                    <label for="funcionario-sobrenome" class="control-label">Sobrenome</label>
                    <input id="funcionario-sobrenome" class="form-control" type="text" name="sobrenome" required/>
                </div>
				
				<div class="form-group">
                    <label for="funcionario-email" class="control-label">E-mail</label>
                    <input id="funcionario-email" class="form-control" type="email" name="email" required/>
                </div>
				
				<div class="form-group">
                    <label for="funcionario-cargo" class="control-label">Cargo</label>
                    <input id="funcionario-cargo" class="form-control" type="text" name="cargo" required/>
                </div>
				
				<div class="form-group">
                    <label for="funcionario-setor" class="control-label">Setor</label>
                    <input id="funcionario-setor" class="form-control" type="text" name="setor" required/>
                </div>
				
				<div class="form-group">
                    <label for="funcionario-salario" class="control-label">Salario</label>
                    <input id="funcionario-salario" class="form-control" type="real" name="salario" required/>
                </div>
				
				 <div class="form-group">
                    <label for="funcionario-efetivacao" class="control-label">Data de efetivação</label>
                    <input id="funcionario-efetivacao" class="form-control datepicker" type="date" name="efetivacao"
                           placeholder="dd/mm/yyyy"
                           pattern="\d{2}/\d{2}/\d{4}" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
</html>