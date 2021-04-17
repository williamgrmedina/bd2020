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
                            <th scope="col" >Nome do produto</th>
                            <th scope="col" >Valor Unitário</th>
                            <th scope="col" >Quantidade</th>
                            <th scope="col" >Ação</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="produto" items="${requestScope.produtos}" varStatus="status" >
                            <tr>
                                <td>
                                    <span class="h6"><c:out value="${produto.produto.nome}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${produto.getFormatted(produto.valor)}"/></span>
                                </td>
                                <td>
                                    <span class="h6"><c:out value="${produto.qtd}"/></span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${produto.pedido.status == 'pago'}"/>
                                        <c:when test="${produto.pedido.status == 'cancelado'}"/>
                                        <c:otherwise>
                                            <a class="btn btn-warning link_cancelar_produto"
                                               href="#"
                                               data-nome_prod="${produto.produto.nome}"
                                               data-qtd_max="${produto.qtd}"
                                               data-toggle="modal" 
                                               data-target="#modal_cancelar_produto">

                                                Cancelar produto
                                            </a>
                                        </c:otherwise>   
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="modal fade" id="modal_cancelar_produto" tabindex="-1" role="dialog" 
             aria-labelledby="modal_cancelar_produto" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="cancel_prod">Cancelar Produto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                            <span aria-hidden="true">&times;</span>	
                        </button>
                    </div>				

                    <div class="h5 modal-nome_prod text-center" ></div>

                    <form
                        class="form" id="form_delete_prod"
                        action="${pageContext.servletContext.contextPath}/pedido/cancelar_produto"
                        method="POST">
                        <div class="form-group d-flex justify-content-center">
                            <span class="form-inline" >
                                <label class=" h5 control-label" style="color: red; padding-right: 15px" for="qtd_a_remover">Quantidade a cancelar: </label>
                                <select class="form-control" id="qtd_a_remover" disabled required>
                                    <option value="1">1</option>
                                </select>
                            </span>
                        </div>
                    </form>

                    <button form="form_delete_prod" type="submit" class="btn btn-secondary" >Salvar</button>
                </div>
            </div>
        </div>

        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/pedidos/funcionario_pedidos.js"></script>
    </body>
</html>
