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
		<div class="container">
            <h2 class="text-center">Inserção de um novo pedido</h2>

			<div class="container">            
				<div class="text-center div_inserir_excluir">
					<a class="btn btn-lg btn-default"
					   href="${pageContext.servletContext.contextPath}"
					   data-toggle="tooltip"
					   data-original-title="">
						<i class="fa fa-arrow-left"></i>
						Voltar para gerenciamento
					</a>

					<a class="btn btn-default"
					   href="${pageContext.servletContext.contextPath}/logout"
					   data-toggle="tooltip"
					   data-original-title="Logout">
						<i class="fa fa-sign-out"></i>
					</a>
				</div>

				<form class="form_excluir_produtos" action="${pageContext.servletContext.contextPath}/produto/delete" method="POST">				
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

						<tbody>
							<c:forEach var="produto" items="${sessionScope.produtos}" varStatus="status">
								<c:choose>
									<c:when test="${produto.qtd > 0}">
										<tr class="info-color">
										</c:when>

										<c:otherwise>
										<tr class="danger-color">
										</c:otherwise>
									</c:choose>	

									<td>
										<span class="h6"><c:out value="${produto.id}"/></span>
									</td>
									<td>
										<span class="h6"><c:out value="${produto.nome}"/></span>
									</td>							
									<td>
										<span class="h6"><c:out value="${produto.getFormatted(produto.valor_venda)}"/></span>
									</td>	
									<td>
										<span class="h6"><c:out value="${produto.qtd}"/></span>
									</td>
									<td class="d-flex justify-content-around" >
										<a class="h3 link_adicionar_produto"
										   href="#"
										   data-href="${pageContext.servletContext.contextPath}/pedido/add_item"
										   data-qtd="${produto.qtd}"
										   data-index="${status.index}"
										   data-toggle="tooltip"
										   data-original-title="Adicionar">
											<i class="fas fa-plus"></i>
										</a>
										<a class="h3 link_remover_produto"
										   href="#"
										   data-href="${pageContext.servletContext.contextPath}/pedido/remove_item"
										   data-qtd="${selectList[status.index]}"
										   data-index="${status.index}"
										   data-toggle="tooltip"
										   data-original-title="Remover">
											<i class="fas fa-minus"></i>
										</a>
									</td>
									<td>
										<span class="h6 qtd"> ${selectList[status.index]} </span>
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</form>
			</div>

			<%@include file="/view/include/scripts.jsp" %>
			<script src="${pageContext.servletContext.contextPath}/assets/js/pedidos.js"></script>
    </body>
</html>