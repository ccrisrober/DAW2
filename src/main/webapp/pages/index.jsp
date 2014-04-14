<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
    <h1>Hello World!</h1>
    <p>Soy el index</p>
<t:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</t:if>
