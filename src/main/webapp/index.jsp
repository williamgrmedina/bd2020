<%-- 
    Document   : index
    Created on : Sep. 8, 2020, 10:23:38 a.m.
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"%>
        <title>[BD 2020] Login</title>
    </head>
    <body>
        <div class="container text-center">
            <h2 class="form-signin-heading">Escolha uma opção.</h2>

                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/funcionario">
                    Sou funcionário
                </a>
					<p></p>
				<a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/cliente">
                    Sou cliente
                </a>
					
                        
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/base.js"></script>
    </body>
</html>