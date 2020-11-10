<%-- 
    Document   : welcome
    Created on : Sep. 8, 2020, 10:24:11 a.m.
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_gerente context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>[BD 2020] Início</title>
    </head>
    <body>
        
        <div class="container">
            
            <div class="jumbotron text-center">
                <h1>Bem-vindo,
                <c:out value="${gerente.PNome}"/>!</h1>
                <p class="lead text-muted">
                    Enquanto gerente deste restaurante, sinta-se livre
                    para contratar funcionários e gerenciar pedidos. 
                    O nosso restarante é também o seu!
                </p>
                <p>
                    <a class="btn btn-lg btn-primary my-2" href="${pageContext.servletContext.contextPath}/user">
                        Gerenciamento de usuários
                    </a> 
                    <a class="btn btn-lg btn-primary my-2" href="${pageContext.servletContext.contextPath}/gerente">
                        Gerenciamento de funcionários
                    </a> 
                    <a class="btn btn-lg btn-primary my-2" href="${pageContext.servletContext.contextPath}/pedidos">
                        Gerenciamento de pedidos
                    </a>
                    <a class="btn btn-default"
                       href="${pageContext.servletContext.contextPath}/logout"
                       data-toggle="tooltip"
                       data-original-title="Logout">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </p>
            </div>
        </div>
                               
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>        
    </body>
</html>