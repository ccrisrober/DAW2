<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de productos</h1>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Productos cargados en la página</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Nº Pedido</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <t:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.getId()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getCategoria()}</td>
                            <td>${product.getPrecio()}</td>
                        </tr>
                    </t:forEach>
                        
                </tbody>
            </table>
                    
        </div>
    </div>
    
    <button id="btnSubmit" class="btn" style="">Nuevo producto</button>
    <br><br />
    
        
</div>
