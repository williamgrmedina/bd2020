<%-- 
    Document   : visualizar_produtos
    Created on : 10 de abr. de 2021, 05:14:14
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
        <title>Visualizar Produtos</title>
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

                <table class="table table-striped table-bordered table-sm success-color" id="tabela_produtos" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th scope="col" >idProduto</th>
                            <th scope="col" >Valor Unitário</th>
                            <th scope="col" >Quantidade</th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="produto" items="${requestScope.produtos}" varStatus="status" >
                        <tr>
                            <td>
                                <span class="h6"><c:out value="${produto.idProduto}"/></span>
                            </td>
                            <td>
                                <span class="h6"><c:out value="${produto.getFormatted(produto.valor)}"/></span>
                            </td>
                            <td>
                                <span class="h6"><c:out value="${produto.qtd}"/></span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_pedidos.js"></script>
    </body>
</html>
