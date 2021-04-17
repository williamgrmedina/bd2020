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
                   href="${pageContext.servletContext.contextPath}/pedidos"
                   data-toggle="tooltip"
                   data-original-title="">
                    <i class="fa fa-arrow-left"></i>
                    Voltar para pedidos
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
                            <c:choose>
                                <c:when test="${pedido.status == 'pago'}">
                                    <tr class="info-color">
                                    </c:when>
                                    <c:when test="${pedido.status == 'cancelado'}">
                                    <tr class="warning-color">
                                    </c:when>
                                    <c:otherwise>
                                    <tr>
                                    </c:otherwise>
                                </c:choose>	
                                <td>
                                    <span class="h6"><c:out value="${pedido.id}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.comanda}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.clienteLogin}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${pedido.status}"/>                                  
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
                                </td>
                            </tr>	
                        </c:forEach>

                </table>	   
            </div>
            <%@include file="/view/include/scripts.jsp"%>
            <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_pedidos.js"></script>
    </body>
</html>
