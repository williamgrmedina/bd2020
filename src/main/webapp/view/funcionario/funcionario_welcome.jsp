<%-- 
    Document   : funcionario_index
    Created on : 9 de nov. de 2020, 01:53:17
    Author     : Medina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/view/include/head.jsp"  %>
        <title>Funcionario</title>
    </head>
    <body>
        <h1>Ola funcionário! Você não é gerente. Xispa.</h1>
		
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/logout"
			data-toggle="tooltip"
			data-original-title="Logout">
			 <i class="fa fa-sign-out"></i>
		 </a>
	</body>
</html>
