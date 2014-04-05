<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
${error}

<table>
    <tr>
        <th>Nombre producto</th>
        <th>Categoría</th>
        <th>Precio</th>
        <th>Cantidad</th>
    </tr>
<t:forEach var="product" items="${pedido.getPedidoProducto()}">
    <tr>
        <td>${product.getProd().getName()}</td>
        <td>${product.getProd().getCategoria()}</td>
        <td>${product.getProd().getPrecio()}</td>
        <td>${product.getQuantity()}</td>
    </tr>
</t:forEach>
    <tr>
        <td colspan="2">&nbsp;</td>
        <td>Total:</td>
        <td>${requestScope.total}</td>
    </tr>
</table>