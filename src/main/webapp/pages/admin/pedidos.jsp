<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 id="tituloListado">Listado de pedidos</h1>

<t:if test="${error = ''}">
   <div class="alert alert-success">
       Pedido actualizado con éxito.
   </div>
</t:if>
<t:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</t:if>

<form action="AdminController" method="POST">

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Pedidos realizados por el cliente</div>
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
                        <tr>
                            <td>${pedido.getId_pedido()}</td>
                            <td>${pedido.getId_usu()}</td>
                            <td><fmt:formatDate type="date" value="${pedido.getDate()}" /></td>
                            <td>${pedido.getPrice()}&euro;</td>
                            <td class="procesado"><a href="AdminController?action=update&id_ped=${pedido.getId_pedido()}" <t:if test="${not pedido.isProcesado()}"> onClick=""</t:if>>
                                    <span class="glyphicon glyphicon-thumbs-<t:out value="${pedido.isProcesado() ? 'up': 'down'}"/>"></span>
                                </a></td>
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