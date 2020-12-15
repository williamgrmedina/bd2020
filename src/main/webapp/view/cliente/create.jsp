<%-- 
    Document   : create
    Created on : 14 de dez. de 2020, 22:58:11
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Novo cliente</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Inserção de um novo cliente</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/user/create"
                enctype="multipart/form-data"
                method="POST">

                <div class="form-group">
                    <label class="control-label" for="cliente-login">Login</label>
                    <input id="cliente-login" class="form-control" type="text" name="login" required autofocus/>

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
                    <label for="cliente-nome" class="control-label">Nome</label>
                    <input id="cliente-nome" class="form-control" type="text" name="nome" required/>
                </div>
				
				<div class="form-group">
                    <label for="cliente-sobrenome" class="control-label">Sobrenome</label>
                    <input id="cliente-sobrenome" class="form-control" type="text" name="sobrenome" required/>
                </div>
				
				<div class="form-group">
                    <label for="cliente-email" class="control-label">E-mail</label>
                    <input id="cliente-email" class="form-control" type="email" name="email" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
        <script src="${pageContext.servletContext.contextPath}/assets/js/cliente.js"></script>
    </body>
</html>