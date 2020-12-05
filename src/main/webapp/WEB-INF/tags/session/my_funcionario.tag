<%-- 
    Document   : my_funcionario
    Created on : 5 de dez. de 2020, 18:41:30
    Author     : Medina
--%>

<%@tag description="gerente authentication handler" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="context" required="true"%>

<c:if test="${empty sessionScope.funcionario}">
    <c:redirect url="/" />
</c:if>