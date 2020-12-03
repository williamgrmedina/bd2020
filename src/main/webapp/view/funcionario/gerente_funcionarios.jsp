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
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/funcionario/create">
                    Inserir novo funcionário
                </a>
                    
                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_funcionario">
                    Excluir múltiplos funcionarios
				</button>
					
				<a class="btn btn-default"
                   href="${pageContext.servletContext.contextPath}/logout"
                   data-toggle="tooltip"
                   data-original-title="Logout">
                    <i class="fa fa-sign-out"></i>
                </a>
			</div>
				   
			<form class="form_excluir_funcionarios" action="${pageContext.servletContext.contextPath}/funcionario/delete" method="POST">				
				<table class="table table-striped table-bordered table-sm" id="lista_funcionarios" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th scope="col" >Nome</th>
							<th scope="col" >Sobrenome</th>
							<th scope="col" >E-mail</th>
							<th scope="col" >Cargo</th>
							<th scope="col" >Setor</th>
                            <th scope="col" >Ação</th>
							<th scope="col" >Excluir?</th>
                        </tr>
                    </thead>
					
					<tbody>
                        <c:forEach var="funcionario" items="${requestScope.funList}">
                            <tr>
                                <td>
                                    <span class="h6"><c:out value="${funcionario.PNome}"/></span>
                                </td>
								<td>
                                    <span class="h6"><c:out value="${funcionario.SNome}"/></span>
                                </td>							
								<td>
                                    <a class="text-primary h6" href="mailto:${funcionario.email}" target="_blank" 
                                        <span class="h6"><c:out value="${funcionario.email}"/></span>
                                    </a>
                                </td>
								<td>
                                    <span class="h6"><c:out value="${funcionario.cargo}"/></span>
                                </td>
								<td>
                                    <span class="h6"><c:out value="${funcionario.setor}"/></span>
                                </td>	
                                <td>
									<a class="btn btn-default"
									   href="#"
									   data-nome="Nome: ${funcionario.PNome} ${funcionario.SNome}"
									   data-login="Login: ${funcionario.login}"
                                       data-salario="Salario: ${funcionario.salario}"
                                       data-data_efetivacao="Data de contratação: ${funcionario.data_efetivacao}"
                                       data-toggle="modal" 
									   data-target="#my-modal"
                                       data-original-title="Mais Informações">
                                       <i class="fa fa-eye" aria-hidden="true"></i>
                                    </a>
									<a class="btn btn-default"
                                       href="${pageContext.servletContext.contextPath}/funcionario/update?login=${funcionario.login}"
                                       data-toggle="tooltip"
                                       data-original-title="Editar">
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    <a class="btn btn-default link_excluir_funcionario"
                                       href="#"
                                       data-href="${pageContext.servletContext.contextPath}/funcionario/delete?login=${funcionario.login}"
                                       data-toggle="tooltip"
                                       data-original-title="Excluir">
                                        <i class="fa fa-trash"></i>
                                    </a>
                                </td>
								<td class="d-flex align-items-center">
									<a>
										<input class="checkbox-inline checkbox" type="checkbox" name="delete" value="${funcionario.login}" />
									</a>
								</td>
                            </tr>
                        </c:forEach>
                    </tbody>

				</table>
			</form>
             
			<div class="modal fade" id="my-modal" tabindex="-1" role="dialog" aria-labelledby="my-modal" aria-hidden="true">
				<div class="modal-dialog" role="document">
				  <div class="modal-content">
					<div class="modal-header">
					  <h5 class="modal-title" id="exampleModalLabel">Mais informações</h5>
					  <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
						<span aria-hidden="true">&times;</span>
					  </button>
					</div>
					<div class="modal-nome" ></div>
					<div class="modal-login" ></div>
					<div class="modal-salario"></div>
					<div class="modal-data_efetivacao"></div>
					<div class="modal-footer">
					  <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
					</div>
				  </div>
				</div>
			</div>
				
            <div class="modal fade modal_excluir_funcionario">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este funcionário?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_funcionario">Sim</a>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<%@include file="/view/include/scripts.jsp"%>
		<script src="${pageContext.servletContext.contextPath}/assets/js/funcionario.js"></script>
    </body>
</html>
