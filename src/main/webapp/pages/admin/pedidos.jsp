<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 id="tituloListado">Listado de pedidos</h1>
<t:choose>
    <t:when test="${not empty ok}">
   <div class="alert alert-success">
       ${ok}
   </div>
    </t:when>
    <t:when test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
    </t:when>
</t:choose>

<form action="AdminController" method="POST">

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Pedidos realizados por los clientes</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nº Pedido</th>
                        <th>Usuario ID</th>
                        <th>Fecha</th>
                        <th>Precio</th>
                        <th>Validado</th>
                    </tr>
                </thead>
                <tbody>
                    <t:forEach var="pedido" items="${pedidos}">
                        <t:choose>
                            <t:when test="${pedido.isProcesado()}">
                                <tr style="background-color: greenyellow">
                            </t:when>
                            <t:otherwise>
                                <tr>
                            </t:otherwise>
                        </t:choose>
                            <td><a href="PedidoController?action=get&id_ped=${pedido.getId_pedido()}&id_user=${pedido.getId_usu()}">${pedido.getId_pedido()}</a></td>
                            <td>${pedido.getId_usu()}</td>
                            <td><fmt:formatDate type="date" value="${pedido.getDate()}" /></td>
                            <td>${pedido.getPrice()}&euro;</td>
                            <td class="procesado">
                                <t:choose>
                                    <t:when test="${pedido.isProcesado()}">
                                        <span class="glyphicon glyphicon-thumbs-up"></span>
                                    </t:when>
                                    <t:otherwise>
                                        <a href="AdminController?action=update&id_ped=${pedido.getId_pedido()}">
                                            <span class="glyphicon glyphicon-thumbs-down"></span>
                                        </a>
                                    </t:otherwise>
                                </t:choose>
                            </td>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <input type="hidden" value="validar" name="action" id="action" />
    <input type="submit" value="Validar pedidos" />
    <br><br />
</form>
</div>