<%-- 
    Document   : index
    Created on : 8 de nov. de 2020, 12:30:31
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_gerente context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
		<!-- MDBootstrap Datatables  -->
        <title>Gerenciamento de Funcionários</title>

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
				
				<a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/produto/create">
                    Inserir novo produto
                </a>

                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_produtos">
                    Excluir múltiplos produtos
				</button>

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

            <div class="modal fade modal_excluir_produto">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este produto?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_produto">Sim</a>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>

			<div class="modal fade" id="modal_edit_prod_nome" tabindex="-1" role="dialog" 
				 aria-labelledby="modal_edit_prod_nome" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="edit_prod_nome">Editar produto</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
								<span aria-hidden="true">&times;</span>	
							</button>
						</div>

						<div class="h5 modal-nome text-center" ></div>

						<form
							class="form" id="form_alter_name"
							action="${pageContext.servletContext.contextPath}/produto/alter_name"
							method="POST">

								<input id="prod_id_n" type="hidden" name="prod_id_n" value="!substituted in javascript file!"/>

							<div class="form-group" width>
								<label class="control-label flex-grow-1" for="prod-nome">Novo nome: </label>
								<input class="form-control h2" type="text" name="nome" 
									   placeholder="insira um novo nome..." required autofocus ></input>
								<p class="help-block"></p>
							</div>
						</form>

						<button form="form_alter_name" type="submit" class="btn btn-secondary" >Salvar</button>
					</div>
				</div>
			</div>



			<div class="modal fade" id="modal_edit_prod_val_compra" tabindex="-1" role="dialog" 
				 aria-labelledby="modal_edit_prod_val_compra" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="edit_prod_val_compra">Editar produto</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
								<span aria-hidden="true">&times;</span>	
							</button>
						</div>

						<div class="h5 modal-nome text-center" ></div>

						<form
							class="form" id="form_alter_prod_compra"
							action="${pageContext.servletContext.contextPath}/produto/alter_compra"
							method="POST">

							<input id="prod_id_c" type="hidden" name="prod_id_c" value="!substituted in javascript file!"/>

							<div class="form-group" width>
								<label class="control-label flex-grow-1" for="prod-val-compra">Novo valor: </label>
								<input class="form-control h2" type="text" name="value" 
									   placeholder="insira um novo valor..." required autofocus ></input>
								<p class="help-block"></p>
							</div>
						</form>

						<button form="form_alter_prod_compra" type="submit" class="btn btn-secondary" >Salvar</button>
					</div>
				</div>
			</div>


			<div class="modal fade" id="modal_edit_prod_val_venda" tabindex="-1" role="dialog" 
				 aria-labelledby="modal_edit_prod_val_venda" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="edit_prod_val_venda">Editar produto</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
								<span aria-hidden="true">&times;</span>	
							</button>
						</div>				

						<div class="h5 modal-nome text-center" ></div>

						<form
							class="form" id="form_alter_prod_venda"
							action="${pageContext.servletContext.contextPath}/produto/alter_venda"
							method="POST">

							<input id="prod_id_v" type="hidden" name="prod_id_v" value="!substituted in javascript file!"/>

							<div class="form-group" width>
								<label class="control-label flex-grow-1" for="prod-val-compra">Novo valor: </label>
								<input class="form-control h2" type="text" name="value" 
									   placeholder="insira um novo valor..." required autofocus ></input>
								<p class="help-block"></p>
							</div>
						</form>

						<button form="form_alter_prod_venda" type="submit" class="btn btn-secondary" >Salvar</button>
					</div>
				</div>
			</div>


			<div class="modal fade" id="modal_edit_prod_qtd" tabindex="-1" role="dialog" 
				 aria-labelledby="modal_edit_prod_qtd" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="edit_prod_qtd">Editar produto</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
								<span aria-hidden="true">&times;</span>	
							</button>
						</div>				

						<div class="h5 modal-nome text-center" ></div>

						<form
							class="form" id="form_alter_prod_qtd"
							action="${pageContext.servletContext.contextPath}/produto/alter_qtd"
							method="POST">

							<input id="prod_id_q" type="hidden" name="prod_id_q" value="!substituted in javascript file!"/>

							<div class="form-group" width>
								<label class="control-label flex-grow-1" for="prod-qtd">Novo valor: </label>
								<input class="form-control h2" type="number" name="value" 
									   placeholder="insira um novo valor..." required autofocus ></input>
								<p class="help-block"></p>
							</div>
						</form>

						<button form="form_alter_prod_qtd" type="submit" class="btn btn-secondary" >Salvar</button>
					</div>
				</div>
			</div>
							
			<div class="modal fade modal_excluir_produtos">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir os usuários selecionados?</p>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-danger button_confirmacao_excluir_produtos" type="button">Sim</button>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>

		</div>

	</div>
	<%@include file="/view/include/scripts.jsp"%>
	<script src="${pageContext.servletContext.contextPath}/assets/js/produtos.js"></script>
</body>
</html>
