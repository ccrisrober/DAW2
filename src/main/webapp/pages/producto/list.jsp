<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 id="tituloListado">Listado de productos</h1>

<div style="margin-bottom: 15px;">
    <button id="showfiltro" class="btn btn-primary">Mostrar filtro</button>
    <button id="btnClean" class="btn btn-warning">Limpiar</button>
</div>

<div class="thumbnail2" id="filtroBusqueda" 
     style=" height: auto; max-width: 240px; overflow:auto;padding-top:10px; 
     border: 1px solid #ddd; border-radius: 4px; display: none; margin-left:15px; padding-left:15px; ">

    <p>Tipo </p>
    <select class="form-control" id="filtroTipo" name="idfiltro" style="margin:0 0 15px 0; width: auto;">
        <option value="">Elija opción ...</option>
        <option value="Alimentación">Alimentación</option>
        <option value="Ferretería">Ferretería</option>
        <option value="Prensa">Prensa</option>
        <option value="Droguería">Droguería</option>
    </select>

    <div style="width: auto;clear:both;">
        <p style="">Precio mínimo <input type="text" name="filtroPrecioMin" id="filtroPrecioMin" min="0" style="width:75px; margin-left:20px;" /> &euro;</p>
        <p style="">Precio máximo <input type="text" name="filtroPrecioMax" id="filtroPrecioMax" min="0" style="width:75px; margin-left:18px;"/> &euro;</p>
        <p style="">Nombre producto <input type="text" name="filtroNombre" id="filtroNombre" style="width:90px;"/></p>
    </div>

    <div style="clear:both;">
        <p style="text-align: center; overflow: auto;">
            <button id="btnSubmit" style="float:left; margin-left:40px;" class="btn btn-success">Filtrar</button>
        </p>
    </div>
</div>
<br />
<div id="contenedor">
    <div id="noprods" style="display:none;"><p>No hay productos con esas características</p></div>
    <% int i = 0; %>
    <t:forEach var="product" items="${products}">
        <div class="col-md-3 column bloqueProducto prod_<% out.print(i);%>">
            <div class="thumbnail">
                <img alt="${product.getName()}" src="${product.getImage()}" />
                <div class="caption">
                    <h3 class="encabezadoh3 name_<% out.print(i);%>">${product.getName()}</h3>
                    <p class="type_<% out.print(i);%>">${product.getCategoria()}</p>
                    <p>
                        <span>Precio:</span> 
                        <span class="price_<% out.print(i);i++;%>">${product.getPrecio()}</span> &euro;
                    </p>
                </div>
            </div>
        </div>
    </t:forEach>
</div>
        
<script type="text/javascript" src="assets/js/filtrado.js"></script>