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

            <p> 
                <label class="control-label flex-grow-1" for="comanda">Comanda: </label>
                <input class="form-control h2" id="comanda" type="number" name="comanda" 
                       placeholder="insira o número da comanda..." required autofocus />
            </p>

            <div class="container">            
                <div class="text-center div_voltar_confirmar">
                    <a class="btn btn-lg btn-default"
                       href="${pageContext.servletContext.contextPath}/pedidos"
                       data-toggle="tooltip"
                       data-original-title="">
                        <i class="fa fa-arrow-left"></i>
                        Voltar para pedidos
                    </a>

                    <a class="btn btn-lg btn-primary link_confirmar_pedido"
                       href="${pageContext.servletContext.contextPath}/pedidos"
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

                    <tbody>
                        <c:forEach var="produto" items="${requestScope.produtos}" varStatus="status">
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
                                       data-index="${status.index}"
                                       data-toggle="tooltip"
                                       data-original-title="Adicionar">
                                        <i class="fas fa-plus"></i>
                                    </a>
                                    <a class="h3 link_remover_produto"
                                       href="#"
                                       data-href="${pageContext.servletContext.contextPath}/pedido/remove_item"
                                       data-index="${status.index}"
                                       data-toggle="tooltip"
                                       data-original-title="Remover">
                                        <i class="fas fa-minus"></i>
                                    </a>
                                </td>
                                <td>
                                    <span class="h6 qtd"> 0 </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>


            <div class="modal fade" id="modal_obs" tabindex="-1" role="dialog" 
                 aria-labelledby="modal_obs" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="observacoes">Observações?</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                                <span aria-hidden="true">&times;</span>	
                            </button>
                        </div>

                        <form
                            class="form" id="form_obs" 
                            href="${pageContext.servletContext.contextPath}/pedido/createPresencial">

                            <div class="form-group" width>
                                <input class="form-control h2" type="text" name="observacao" 
                                       placeholder="insira uma observação..." autofocus ></input>
                                <p class="help-block"></p>
                            </div>
                        </form>

                        <button form="form_obs" type="submit" class="btn btn-secondary" >Salvar</button>
                    </div>
                </div>
            </div>


            <div class="modal fade modal_error">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title red">GRAVE</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>
                                Ocorreu um erro ao processar o seu pedido.<br/>
                                Tente novamente ou contate um administrador do sistema.
                            </p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_produto" data-dismiss="modal">Entendi</a>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="/view/include/scripts.jsp" %>
            <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_gerar.js"></script>
    </body>
</html>