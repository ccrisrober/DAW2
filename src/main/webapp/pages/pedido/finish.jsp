<t:choose>
    <t:when test="${not empty ok}">
        <div class="alert alert-success">
            ${ok}
        </div>
        Pincha <a href="Index">aquí</a> para volver al inicio.
    </t:when>
    <t:otherwise>
        <div class="alert alert-danger">
            ${error}
        </div>
    </t:otherwise>
</t:choose>