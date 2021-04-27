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

                <a class="btn btn-default"
                   href="${pageContext.servletContext.contextPath}/logout"
                   data-toggle="tooltip"
                   data-original-title="Logout">
                    <i class="fa fa-sign-out"></i>
                </a>

                <div class="d-flex flex-row align-items-center" style="width: 400px; height: 400px;">
                    <canvas id="pie_chart" href="${pageContext.servletContext.contextPath}/pedido/info_graphs_upper"></canvas>               
                    <canvas id="line_chart" href="${pageContext.servletContext.contextPath}/pedido/info_graphs_upper"></canvas>
                    <div> 
                        <label for="ano">Ano</label> 
                        <select class="form-group" id="year_selector" required 
                                last_year = "${requestScope.last_year}"
                                first_year = "${requestScope.first_year}">
                            <option value="${requestScope.last_year}">${requestScope.last_year}</option>
                        </select>
                    </div>    
                </div> 

                <div>
                    <a href="${pageContext.servletContext.contextPath}/test/generate_dataset">
                        <button class="btn-info">
                            Gerar dataset aleatório                            
                        </button>
                    </a>
                        <canvas id="receitas_despesas_chart" href="${pageContext.servletContext.contextPath}/pedido/info_graphs"></canvas>
                </div>

                <div class="d-flex flex-row align-items-center" style="width: 400px; height: 400px;">
                    <canvas id="bar_chart" href="${pageContext.servletContext.contextPath}/pedido/info_graphs_lower"></canvas> 
                    <div> 
                        <label for="mes">Mês</label> 
                        <select class="form-group" id="month_selector_2" required>
                            <option value="1">Janeiro</option>
                        </select>
                        <label for="ano">Ano</label> 
                        <select class="form-group" id="year_selector_2" required 
                                last_year = "${requestScope.last_year}"
                                first_year = "${requestScope.first_year}">
                            <option value="${requestScope.last_year}">${requestScope.last_year}</option>
                        </select>
                    </div>      
                </div>     

            </div>
            <%@include file="/view/include/scripts.jsp"%>
            <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_pedidos.js"></script>
            <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/info_graphs.js"></script>
    </body>
</html>
