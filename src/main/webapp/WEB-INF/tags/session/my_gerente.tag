<%-- 
    Document   : my_gerente
    Created on : Sep. 20, 2020, 10:46:12 a.m.
    Author     : Medina
--%>

<%@tag description="gerente authentication handler" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="context" required="true"%>

<c:if test="${empty sessionScope.gerente}">
    <c:redirect url="/" />
</c:if>