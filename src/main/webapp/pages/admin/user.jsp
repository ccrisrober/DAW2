<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de productos</h1>

<div id="contenedor">
    <div class="panel panel-default">
        <div class="panel-heading">Productos cargados en la página</div>
        <div class="table-responsive">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th style="display: none">Id Usuario</th>
                        <th>Nombre de Usuario</th>
                        <th>Password</th>
                        <th>Borrar</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <t:forEach var="user" items="${users}">
                        <tr>
                            <th style="display: none">${user.getId()}</td>
                            <td><a href="UserController?action=profile&id=${user.getId()}">${user.getName()}</a></td>
                            <td>${user.getPassword()}</td>
                            <td><a href="delete">
                                <span class="glyphicon glyphicon-remove"></span></a>
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
