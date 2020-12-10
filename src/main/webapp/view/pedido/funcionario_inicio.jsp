<%-- 
    Document   : funcionario_pedidos
    Created on : 5 de dez. de 2020, 13:06:03
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
					Voltar para gerenciamento
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
			</div>
				 
		</div>
		<%@include file="/view/include/scripts.jsp"%>
		<script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_inicio.js"></script>
	</body>
</html>
