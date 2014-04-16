<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de productos</h1>
<t:choose>
    <t:when test="${not empty ok}">
   <div class="alert alert-success">
       ${ok}
   </div>
    </t:when>
    <t:when test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
    </t:when>
</t:choose>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Productos cargados en la página</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th style="display: none">Nº Producto</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Precio</th>
                        <th>Editar</th>
                        <th>Borrar</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <t:forEach var="product" items="${products}">
                        <tr>
                            <th style="display: none">${product.getId()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getCategoria()}</td>
                            <td>${product.getPrecio()}</td>
                            <td>
                                <a href="ProductoController?action=edit&id_prod=${product.getId()}">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </a>
                            </td>
                            <td>
                                <a href="ProductoController?action=delete&id_prod=${product.getId()}">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>
                            </td>
                        </tr>
                    </t:forEach>
                        
                </tbody>
            </table>
                    
        </div>
    </div>
    
    <a class="btn btn-default" href="ProductoController?action=create">Nuevo producto</a>
    <br/><br/>
    
        
</div>
