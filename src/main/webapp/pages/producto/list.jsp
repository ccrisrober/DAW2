<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>List</h1>
        <div id="contenedor">
            
            <style>
                .col-md-4 {
                    height: 200px;
                }
            </style>
        <t:forEach var="product" items="${products}">
            <div class="col-md-4 column" style="height: 325px; max-height: 375px">
                <div class="thumbnail" style="min-heigh: 300px; max-height: 375px">
                    <img width="100" height="100" alt="${product.getName()}" src="${product.getImage()}" />
                    <div class="caption">
                        <h3 style="text-align: center">${product.getName()}</h3>
                        <p class="type" style="text-align: center">${product.getCategoria()}</p>
                        <p class="price" style="text-align: center"><span style="text-decoration: underline">Precio:</span> <span style="font-style:italic; font-weight: bold; text-decoration: none">${product.getPrecio()} $</span></p>
                    </div>
                </div>
            </div>
        </t:forEach>
        </div>