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
        <title>Cadastro de produtos</title>
    </head>
    <body>

        <div class="container">
            <h2 class="text-center">Inserção de um novo produto</h2>

            <form
                class="form form_criar_pedido"
                action="${pageContext.servletContext.contextPath}/produto/create"
                method="POST">

                <div class="form-group">
                    <label class="control-label" for="produto-nome">Nome do produto</label>
                    <input id="produto-nome" class="form-control" type="text" name="nome" required autofocus/>

                    <p class="help-block"></p>
                </div>


                <div class="form-group">
                    <label class="control-label" for="produto-compra">Valor de compra</label>
					<input id="produto-compra" class="form-control" type="text" name="val_compra" required/>
                </div>

                <div class="form-group">
                    <label class="control-label" for="produto-venda">Valor de venda</label>
					<input id="produto-venda" class="form-control" type="text" name="val_venda" required/>
                </div>

                <div class="form-group">
                    <label class="control-label" for="produto-qtd">Quantidade</label>
                    <input id="produto-qtd" class="form-control" type="number" name="qtd" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <%@include file="/view/include/scripts.jsp" %>
		<script src="${pageContext.servletContext.contextPath}/assets/js/produtos.js"></script>
    </body>
</html>