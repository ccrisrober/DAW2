<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>List</h1>
        <div id="contenedor">
            
            <style>
                .col-md-4 {
                    height: 200px;
                }
            </style>
        <t:forEach var="product" items="${products}">
            <div class="col-md-4">
                <div class="thumbnail">
                    <img alt="" src="" />
                    <div class="caption">
                        <h3>${product.getName()}</h3>
                        <p class="type">${product.getCategoria()}</p>
                        Cantidad: <input type="text" /> (Precio: ${product.getPrecio()}
                    </div>
                </div>
            </div>
        </t:forEach>
        <t:forEach var="product" items="${products}">
            <div class="col-md-4">
                <div class="thumbnail">
                    <img alt="" src="" />
                    <div class="caption">
                        <h3>${product.getName()}</h3>
                        <p class="type">${product.getCategoria()}</p>
                        Cantidad: <input type="text" /> (Precio: ${product.getPrecio()}
                    </div>
                </div>
            </div>
        </t:forEach>
        <t:forEach var="product" items="${products}">
            <div class="col-md-4">
                <div class="thumbnail">
                    <img alt="" src="" />
                    <div class="caption">
                        <h3>${product.getName()}</h3>
                        <p class="type">${product.getCategoria()}</p>
                        Cantidad: <input type="text" /> (Precio: ${product.getPrecio()}
                    </div>
                </div>
            </div>
        </t:forEach>
        </div>