<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
    <t:choose>
        <t:when test="${not empty ok}">
            <div class="alert alert-success">
                ${ok}
            </div>
            Actualizado con éxito. Pulsa <a href="AdminController?action=list">aquí</a> para volver al listado de productos.
        </t:when>
        <t:otherwise>
            <t:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
            </t:if>
            <form role="form" method="post" action="ProductoController" enctype="multipart/form-data">
                <input type="hidden" name="action" id="action" value="edit" />
                <input type="hidden" id="id_prod" name="id_prod" value="${id}" />
                <div class="form-group">
                    <label for="namefield">Nombre producto</label><input type="text" class="form-control" id="namefield" name="namefield" value="${name}"/>
                </div>
                <div class="form-group">
                    <select class="form-control" name="categoryfield" id="categoryfield">
                        <option value="">----</option>
                        <option value="Alimentación" <t:if test="${category.equalsIgnoreCase('Alimentación')}"> selected</t:if>>Alimentación</option>
                        <option value="Droguería" <t:if test="${category.equalsIgnoreCase('Droguería')}"> selected</t:if>>Droguería</option>
                        <option value="Prensa" <t:if test="${category.equalsIgnoreCase('Prensa')}"> selected</t:if>>Prensa</option>
                        <option value="Ferretería"<t:if test="${category.equalsIgnoreCase('Ferretería')}"> selected</t:if>>Ferretería</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield" name="pricefield" value="${price}"/>
                </div>
                <div class="form-group">
                    <label for="filefield">Foto</label><input type="file" id="file" name="file" value="${photo}"/>
                    <br/>
                    <img src="${image}" alt="${name}" /><input type="hidden" name="file_hidden" id="file_hidden" value="${image}" />
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
        </t:otherwise>
    </t:choose>