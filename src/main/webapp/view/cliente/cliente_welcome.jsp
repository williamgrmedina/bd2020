<%-- 
    Document   : cliente_welcome
    Created on : 14 de dez. de 2020, 23:21:09
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_cliente context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>Cliente</title>
    </head>
    <body>
        <div class="container">

            <div class="jumbotron text-center">
                <h1>Bem-vindo,
                    <c:out value="${cliente.PNome}"/>!</h1>
                <p class="lead text-muted">
                    Este é o seu espaço pessoal com informações de pedidos feitos e o andamento de cada um,<br> 
                    feitos com carinho especialmente para você!
                </p>
                <a class="btn btn-lg btn-primary new_pedido" href="${pageContext.servletContext.contextPath}/pedido/createOnline">
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
                            <th scope="col" >Status</th>
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
                                    <span class="h6"><c:out value="${pedido.status}"/></span>
                                </td>
                                <td>
                                    <form action="${pageContext.servletContext.contextPath}/pedido/visualizar_produtos" method="GET">
                                        <input id='id' type='hidden' name='id' value='${pedido.id}'>
                                        <input class="btn btn-default link_visualizar_prods"
                                               type='submit' value='Visualizar Produtos'>
                                    </form>
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
                    </tbody>
                </table>
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
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/base.js"></script>
        <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/cliente_pedidos.js"></script>
    </body>
</html>

