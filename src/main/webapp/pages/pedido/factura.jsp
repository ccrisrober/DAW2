<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<t:choose>
    <t:when test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
    </t:when>
    <t:otherwise>
        <div class="table-responsive">
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
                    <t:forEach var="product" items="${products}">
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
                        <td>${requestScope.total}</td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <form action="CarritoController" method="POST">
            <input type="hidden" value="finish" name="action" id="action" />
            <a href="javascript:history.back(1)" class="btn btn-warning">Volver Atrás</a>
            <input type="submit" value="Enviar" class="btn btn-success"/>
        </form>
    </t:otherwise>
</t:choose>