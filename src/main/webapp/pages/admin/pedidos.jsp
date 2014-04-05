<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 id="tituloListado">Listado de pedidos</h1>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Pedidos realizados por el cliente</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nº Pedido</th>
                        <th>Usuario</th>
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
                            <td><t:out value="${pedido.isProcesado() ? 'Validado': 'No validado'}"/></td>
                        </tr>
                    </t:forEach>
                        
                </tbody>
            </table>
                    
        </div>
    </div>
    
    <button id="btnSubmit" class="btn" style="">Validar pedidos</button>
    <br><br />
    
</div>
