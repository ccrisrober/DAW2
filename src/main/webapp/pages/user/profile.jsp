<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
    </t:when>
    <t:otherwise>
        <t:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
        </t:if>
        <h2>Mi perfil</h2>
        
        <div class="form-group">
            <label for="username">Usuario: </label><input class="form-control" id="username" type="text" name="username" value="${user.getName()}" disabled/>
        </div>
        <a href="UserController?action=MisPedidos">Ver pedidos</a>
    </t:otherwise>
</t:choose>