<%-- 
    Document   : funcionario_index
    Created on : 9 de nov. de 2020, 01:53:17
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/session" prefix="session"%>
<session:my_funcionario context="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>Funcionário</title>
    </head>
    <body>
        <div class="container">

            <div class="jumbotron text-center">
                <h1>Bem-vindo,
					<c:out value="${funcionario.PNome}"/>!</h1>
                <p class="lead text-muted">
                    Pouca coisa é necessária para transformar inteiramente uma vida:<br/> 
					amor no coração e sorriso nos lábios.<br/>
					E o seu é o mais lindo deles!
                </p>
                <p>
                    <a class="btn dbtn-lg btn-primary my-2" href="${pageContext.servletContext.contextPath}/pedidos">
                        Proceder para pedidos
                    </a>
				</p>
				<a class="btn btn-default"
				   href="${pageContext.servletContext.contextPath}/logout"
				   data-toggle="tooltip"
				   data-original-title="Logout">
					<i class="fa fa-sign-out"></i>
				</a>
		<%@include file="/view/include/scripts.jsp"%>
	</body>
</html>
