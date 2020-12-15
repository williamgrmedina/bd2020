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
		<%@include file="/view/include/scripts.jsp"%>
	</body>
</html>

