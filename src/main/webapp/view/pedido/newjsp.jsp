<%-- 
    Document   : create
    Created on : 6 de dez. de 2020, 18:04:09
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_funcionario context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp" %>
        <title>Gerar novo pedido</title>
    </head>
    <body>
		<input type="hidden" id="json_file" name="json_file" value="${sessionScope.json}">
		<div class="container">
            <h2 class="text-center">Inserção de um novo pedido</h2>
			
			<p> 
				<label class="control-label flex-grow-1" for="prod-nome">Novo nome: </label>
				<input class="form-control h2" id="prod-nome" type="text" name="nome" 
					   placeholder="insira um novo nome..." required autofocus />
			</p>
			
			<div class="container">            
				<div class="text-center div_inserir_excluir">
					<a class="btn btn-lg btn-default"
					   href="${pageContext.servletContext.contextPath}"
					   data-toggle="tooltip"
					   data-original-title="">
						<i class="fa fa-arrow-left"></i>
						Voltar para gerenciamento
					</a>
					   
					<a class="btn btn-lg btn-primary"
					   href="${pageContext.servletContext.contextPath}"
					   data-toggle="tooltip"
					   data-original-title="">
						Confirmar pedido
					</a>

					<a class="btn btn-default"
					   href="${pageContext.servletContext.contextPath}/logout"
					   data-toggle="tooltip"
					   data-original-title="Logout">
						<i class="fa fa-sign-out"></i>
					</a>
				</div>

				<table class="table table-striped table-bordered table-sm success-color" id="tabela_produtos" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th scope="col" >id</th>
							<th scope="col" >Nome</th>
							<th scope="col" >preco</th>
							<th scope="col" >Disponíveis</th>
							<th scope="col" >Ação</th>
							<th scope="col" >Qtd</th>
						</tr>
					</thead>
				</table>
			</div>

			<%@include file="/view/include/scripts.jsp" %>
			<script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_gerar.js"></script>
    </body>
</html>