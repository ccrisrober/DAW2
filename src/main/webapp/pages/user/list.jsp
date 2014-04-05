<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de pedidos</h1>
${requestScope.error}
<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Tabla de pedidos</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Id Pedido</th>
                        <th>Fecha</th>
                        <th>Precio</th>
                        <th>Validado</th>
                    </tr>
                </thead>
                <tbody>
                    <t:set var="contador" value="1" />
                    <t:forEach var="pedido" items="${pedidos}">
                        <tr>
                            <td><a href="PedidoController?action=get&id_ped=${pedido.getId_pedido()}">${contador}</a></td>
                            <td>${pedido.getDate()}</td>
                            <td>${pedido.getPrice()}</td>
                            <td>${pedido.isProcesado()}</td>
                            <t:set var="contador" value="${contador+1}"/>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
                    
        </div>
    </div>
</div>