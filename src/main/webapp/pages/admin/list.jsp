<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de pedidos</h1>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Tabla de pedidos realizados</div>
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
                    <% int i = 0; %>
                    <t:forEach var="product" items="${products}">
                        <tr>
                            <td class="prod_<% out.print(i);%>"><% out.print(i);%></td>
                            <td class="name_<% out.print(i);%>">${product.getName()}</td>
                            <td class="time_<% out.print(i);%>">Fecha</td>
                            <td class="price_<% out.print(i);
                                i++;%>">${product.getPrecio()}</td>
                            <td>Validado</td>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
                    
        </div>
    </div>
</div>
