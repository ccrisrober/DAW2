<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<t:choose>
    <t:when test="${not empty error}">
        ${error}
    </t:when>
    <t:otherwise>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Nombre producto</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                </tr>
            </thead>
        <tbody>
        <t:forEach var="product" items="${pedido.getPedidoProducto()}">
            <tr>
                <td>${product.getProd().getName()}</td>
                <td>${product.getProd().getCategoria()}</td>
                <td>${product.getProd().getPrecio()}</td>
                <td>${product.getQuantity()}</td>
            </tr>
        </t:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">&nbsp;</td>
                    <th>Total:</th>
                    <td>${pedido.getPrice()}</td>
                </tr>
            </tfoot>
        </table>
    </t:otherwise>
</t:choose>