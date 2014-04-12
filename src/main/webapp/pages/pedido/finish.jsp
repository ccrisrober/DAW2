<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
        Pincha <a href="Index">aquí</a> para volver al inicio.
    </t:when>
    <t:when test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
    </t:when>
</t:choose>