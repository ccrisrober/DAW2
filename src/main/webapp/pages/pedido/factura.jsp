<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
    <tr>
        <th>Nombre producto</th>
        <th>Categoría</th>
        <th>Precio</th>
        <th>Cantidad</th>
    </tr>
<t:forEach var="product" items="${products}">
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

<form action="CarritoController" method="POST">
    <input type="hidden" value="finish" name="action" id="action" />
    <input type="submit" value="Enviar" />
    </div>
</form>