<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 id="tituloListado">Listado de pedidos</h1>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Pedidos realizados por el cliente</div>
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
                    <t:forEach var="pedido" items="${pedidos}">
                        <tr>
                            <td>${pedido.getId_pedido()}</td>
                            <td>${pedido.getId_usu()}</td>
                            <td><fmt:formatDate type="date" value="${pedido.getDate()}" /></td>
                            <td>${pedido.getPrice()}&euro;</td>
                            <td class="procesado"><a<t:if test="${not pedido.isProcesado()}"> onClick="validar()"</t:if>><span class="glyphicon glyphicon-thumbs-<t:out value="${pedido.isProcesado() ? 'up': 'down'}"/>"></span></a></td>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <button id="btnSubmit" class="btn" style="">Validar pedidos</button>
    <br><br />
    
    
    <script>
        function validar() {
            alert("hola");
            var par = $(this).parent().parent(); //tr
            alert("hola");
            par.remove();
            alert("hola");
            return false;
        }
        $(function () {
    $("td .procesado").dblclick(function () {
        var OriginalContent = $(this).text();
 
        $(this).addClass("cellEditing");
        $(this).html("<input type=\"text\" value=\"&quot; + OriginalContent + &quot;\" />");
        $(this).children().first().focus();
 
        $(this).children().first().keypress(function (e) {
            if (e.which === 13) {
                var newContent = $(this).val();
                $(this).parent().text(newContent);
                $(this).parent().removeClass("cellEditing");
            }
        });
 
    $(this).children().first().blur(function(){
        $(this).parent().text(OriginalContent);
        $(this).parent().removeClass("cellEditing");
    });
    });
});
    </script>
</div>