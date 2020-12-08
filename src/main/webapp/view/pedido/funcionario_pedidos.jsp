<%-- 
    Document   : funcionario_pedidos
    Created on : 5 de dez. de 2020, 13:06:03
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_funcionario context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
		<title>Pedidos</title>
    </head>
    <body>
        <div class="container">            
            <div class="text-center div_inserir_excluir">
                <a class="btn btn-lg btn-default"
                   href="${pageContext.servletContext.contextPath}"
                   data-toggle="tooltip"
                   data-original-title="">
					<i class="fa fa-arrow-left"></i>
					Voltar para gerenciamento
                </a>
				
				<a class="btn btn-lg btn-primary new_pedido" href="${pageContext.servletContext.contextPath}/pedido/createPresencial">
                    Gerar novo pedido
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
							<th scope="col" >Valor de compra</th>
							<th scope="col" >Valor de venda</th>
							<th scope="col" >Quantidade</th>
							<th scope="col" >Ação</th>
                        </tr>
                    </thead>

					<tbody>
                        <c:forEach var="produto" items="${requestScope.produtos}">
                            <c:choose>
								<c:when test="${produto.qtd > 50}">
									<tr class="info-color">
									</c:when>

									<c:when test="${produto.qtd > 20}">
									<tr class="warning-color">
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
									<a	class="h3 link_edit_prod_nome"
									   href ="#" 
									   data-nome="${produto.nome}"
									   data-id="${produto.id}" 
									   data-toggle="modal"
									   data-target="#modal_edit_prod_nome" >								
										<i 
											class="fa fa-pencil"
											data-toggle="tooltip"
											data-original-title="Editar" > 
										</i>
									</a>
								</td>							
								<td>
									<span class="h6"><c:out value="${produto.getFormatted(produto.valor_compra)}"/></span>
									<a	class="h3 link_edit_prod_val_compra"
									   href ="#" 
									   data-nome="${produto.nome}"
									   data-id="${produto.id}"
									   data-toggle="modal" 
									   data-target="#modal_edit_prod_val_compra" >
										<i 
											class="fa fa-pencil"
											data-toggle="tooltip"
											data-original-title="Editar" > 
										</i>
									</a>
								</td>
								<td>
									<span class="h6"><c:out value="${produto.getFormatted(produto.valor_venda)}"/></span>
									<a	class="h3 link_edit_prod_val_venda"
									   href ="#" 
									   data-nome="${produto.nome}"
									   data-id="${produto.id}"
									   data-toggle="modal" 
									   data-target="#modal_edit_prod_val_venda" >
										<i 
											class="fa fa-pencil"
											data-toggle="tooltip"
											data-original-title="Editar" > 
										</i>
									</a>
								</td>	
								<td>
									<span class="h6"><c:out value="${produto.qtd}"/></span>
									<a	class="h3 link_edit_prod_qtd"
									   href ="#" 
									   data-nome="${produto.nome}"
									   data-id="${produto.id}"
									   data-toggle="modal" 
									   data-target="#modal_edit_prod_qtd" >
										<i 
											class="fa fa-pencil"
											data-toggle="tooltip"
											data-original-title="Editar" > 
										</i>
									</a>
								</td>
								<td class="d-flex justify-content-around" >
									<a class="h3 link_excluir_produto"
                                       href="#"
                                       data-href="${pageContext.servletContext.contextPath}/produto/delete?id=${produto.id}"
                                       data-toggle="tooltip"
                                       data-original-title="Excluir">
                                        <i class="fa fa-trash"></i>
                                    </a>
									<a class="h3">
										<input class="checkbox-inline checkbox" type="checkbox" name="delete" value="${produto.id}" />
									</a>
								</td
							</tr>
                        </c:forEach>
                    </tbody>

				</table>
			</form>
		</div>
		<%@include file="/view/include/scripts.jsp"%>
		<script src="${pageContext.servletContext.contextPath}/assets/js/pedidos.js"></script>
	</body>
</html>
