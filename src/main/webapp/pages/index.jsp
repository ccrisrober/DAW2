<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
    <h1>Bienvenid@ a la tienda En ca' la Paqui</h1>
    <p><a href="Register">Registrese</a> si aun no eres usuario para poder realizar un pedido. Si eres usuario, <a href="Login">logueese</a>.</p>
    <p>Espero que le gusten nuestros productos.</p>
    <p>Un saludo</p>
    <!--<img src="assets/img/tienda.jpg" alt="tienda" style="width: 100%" />-->
<t:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</t:if>
