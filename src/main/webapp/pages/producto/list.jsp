<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>List</h1>
        <select multiple class="form-control" id="filtrotipo" name="idfiltro">
            <option value="Alimentación">Alimentación</option>
            <option value="Ferretería">Ferretería</option>
            <option value="Prensa">Prensa</option>
            <option value="Droguería">Droguería</option>
          </select>
        
            <input type="text" name="filtroPrecioMin" id="filtroPrecioMin" /> 
            <input type="text" name="filtroPrecioMax" id="filtroPrecioMax" /> 
            
            <input type="text" name="filtronombre" id="filtronombre" /> 
            <button id="btnSubmit">Filtro Nigga</button>
            <div id="contenedor">
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
                                <span class="price_<% out.print(i); i++;%>">${product.getPrecio()} $</span>
                            </p>
                        </div>
                    </div>
                </div>
            </t:forEach>
            <t:forEach var="product" items="${products}">
                <div class="col-md-3 column bloqueProducto prod_<% out.print(i);%>">
                    <div class="thumbnail">
                        <img alt="${product.getName()}" src="${product.getImage()}" />
                        <div class="caption">
                            <h3 class="encabezadoh3 name_<% out.print(i);%>">${product.getName()}</h3>
                            <p class="type_<% out.print(i);%>">${product.getCategoria()}</p>
                            <p>
                                <span>Precio:</span> 
                                <span class="price_<% out.print(i); i++;%>">${product.getPrecio()} $</span>
                            </p>
                        </div>
                    </div>
                </div>
            </t:forEach>
            </div>
            <script>
                $(document).ready(function() {
                    $("#btnSubmit").click(function(){
                        var max = $(".thumbnail").length;
                        var filtro = $('#filtronombre').val();
                        
                        
                        var filtroname = $('#filtronombre').val();
                        
                        
                        
                        for ( var i = 0; i < max; i++ ) {
                            if(filtroname !== '') {
                                
                            }
                            
                            
                            if($('.type_' + i).text() === filtro) {
                                $('.prod_' + i).css('display', 'none');
                            } else if (filtro === "") {
                                $('.prod_' + i).css('display', 'block');
                            }
                            /*var data = "NOMBRE: " + $('.name_' + i).text() +'\n';
                            data += "TIPO: " + $('.type_' + i).text() +'\n';
                            data += "PRECIO: " + $('.price_' + i).text();
                            alert(data);*/
                        };
                    }); 
                });
                
            </script>