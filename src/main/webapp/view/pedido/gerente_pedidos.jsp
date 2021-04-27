<%-- 
    Document   : funcionario_pedidos
    Created on : 5 de dez. de 2020, 13:06:03
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_gerente context="${pageContext.servletContext.contextPath}"/>
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
                    Voltar para página inicial
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

                <table class="table table-striped table-bordered table-sm success-color" id="tabela_pedidos" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th scope="col" >idPedido</th>
                            <th scope="col" >Comanda</th>
                            <th scope="col" >Cliente</th>
                            <th scope="col" >Status</th>
                            <th scope="col" >Observação</th>
                            <th scope="col" >Ação</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="pedido" items="${requestScope.pedidos}" varStatus="status" >
                            <tr>	
                                <td>
                                    <span class="h6"><c:out value="${pedido.id}"/></span>
                                </td>
                                <td>
                                <td>
                                    <c:choose>
                                        <c:when test="${pedido.tipo == 'online'}"/>
                                        <c:otherwise>
                                            <span class="h6"><c:out value="${pedido.comanda}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.clienteLogin}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.status}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.obs}"/></span>
                                </td>
                                <td>
                                    <form action="${pageContext.servletContext.contextPath}/pedido/visualizar_produtos" method="GET">
                                        <input type='hidden' name='id' value='${pedido.id}'>
                                        <input class="btn btn-default link_visualizar_prods"
                                               type='submit' value='Visualizar Produtos'>
                                    </form>
                                    <c:choose>
                                        <c:when test="${pedido.tipo == 'online'}">
                                            <c:choose>	
                                                <c:when test="${pedido.status == 'aguardando confirmacao'}">
                                                    <a class="btn btn-default link_confirmar_ped"
                                                       href="#"
                                                       data-href="${pageContext.servletContext.contextPath}/pedido/confirmar?id=${pedido.id}"
                                                       >Confirmar pedido</a>
                                                </c:when>
                                                <c:when test="${pedido.status == 'enviado para entrega'}">
                                                    <a class="btn btn-default link_confirmar_pgmt"
                                                       href="#"
                                                       data-href="${pageContext.servletContext.contextPath}/pedido/confirmar_pgmt?id=${pedido.id}"
                                                       >Confirmar pagamento</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-default link_enviar"
                                                       href="#"
                                                       data-href="${pageContext.servletContext.contextPath}/pedido/enviar?id=${pedido.id}"
                                                       >Enviar para entrega</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>

                                        <c:when test="${pedido.tipo == 'presencial'}"> 
                                            <c:choose>
                                                <c:when test="${pedido.status == 'entregue'}">
                                                    <a class="btn btn-default link_confirmar_pgmt"
                                                       href="#"
                                                       data-href="${pageContext.servletContext.contextPath}/pedido/confirmar_pgmt?id=${pedido.id}"
                                                       >Confirmar pagamento</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-default link_confirmar_entrega"
                                                       href="#"
                                                       data-href="${pageContext.servletContext.contextPath}/pedido/confirmar_entrega?id=${pedido.id}"
                                                       >Confirmar entrega</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>	
                                    <c:choose>
                                        <c:when test="${pedido.status == 'pago'}"/>
                                        <c:when test="${pedido.status == 'cancelado'}"/>
                                        <c:otherwise>
                                            <a class="btn btn-warning link_cancelar_pedido"
                                               href="#"
                                               data-href="${pageContext.servletContext.contextPath}/pedido/cancelar_pedido?idPed=${pedido.id}"
                                               >Cancelar pedido</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>	
                        </c:forEach>

                </table>

                <div>
                    <a class="btn btn-lg btn-primary new_pedido" href="${pageContext.servletContext.contextPath}/pedido/visualizar_todos">
                        Visualizar todos
                    </a>
                    <a class="btn btn-lg btn-primary new_pedido" href="${pageContext.servletContext.contextPath}/pedido/visualizar_estatisticas">
                        Visualizar estatísticas
                    </a>    
                </div>

                <div class="modal modal_confirmar_entrega">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Confirmação</h4>
                                <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <p>Confirmar entrega deste pedido?</p>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-primary link_confirmacao_entrega">Sim</a>
                                <button class="btn btn-default" type="button" data-dismiss="modal">Não</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal modal_confirmar_pgmt">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Confirmação</h4>
                                <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <p>Confirmar pagamento deste pedido?</p>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-primary link_confirmacao_pgmt">Sim</a>
                                <button class="btn btn-default" type="button" data-dismiss="modal">Não</button>
                            </div>
                        </div>
                    </div>
                </div> 

                <div class="modal fade modal_cancelar_pedido">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Confirmação</h4>
                                <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <p>Deseja mesmo cancelar este pedido e todos os produtos a ele atrelados?</p>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-danger link_confirmacao_cancelar" type="submit">Sim</button>
                                <button class="btn btn-default" type="button" data-dismiss="modal">Não</button>
                            </div>
                        </div>
                    </div>
                </div> 

            </div>

            <div class="modal modal_enviar">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Enviar este pedido para entrega?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-primary link_confirmacao_envio">Sim</a>
                            <button class="btn btn-default" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal modal_confirmar_ped">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Confirmar este pedido?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-primary link_confirmacao_ped">Sim</a>
                            <button class="btn btn-default" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>	   

        </div>
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_pedidos.js"></script>
    </body>
</html>
