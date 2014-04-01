<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
        <h1>List</h1>
        <div id="search" style="display: none; height: auto; max-width: 240px; overflow:auto;padding-top:10px; 
     border: 1px solid #ddd; border-radius: 4px; display: none; margin-left:15px; padding-left:15px; ">
            <fieldset>
                <div class="form-group">
                    <label for="filtronombre">Nombre:</label><input type="text" name="filtronombre" id="filtronombre" class="form-control" /> 
                </div>
                <div class="form-group">
                    <label for="idfiltro">Categoría:</label>
                    <select multiple class="form-control" id="filtrotipo" name="idfiltro">
                        <option value="Alimentación">Alimentación</option>
                        <option value="Ferretería">Ferretería</option>
                        <option value="Prensa">Prensa</option>
                        <option value="Droguería">Droguería</option>
                    </select>
                </div>
                <fieldset>
                    <legend>Precio:</legend>
                    <div class="form-group">
                        <label for="filtroPrecioMin">Mínimo:</label><input type="text" name="filtroPrecioMin" id="filtroPrecioMin" class="form-control" /> 
                    </div>
                    <div class="form-group">
                        <label for="filtroPrecioMax">Máximo:</label><input type="text" name="filtroPrecioMax" id="filtroPrecioMax" class="form-control" /> 
                    </div>
                </fieldset>
                <button type="button" class="btn btn-default" id="btnSubmit" style="float: right">
                    <span class="glyphicon glyphicon-zoom-in"></span> Filtro
                </button>
                <button type="button" class="btn btn-default" id="btnClean" style="float: right">
                    <span class="glyphicon glyphicon-zoom-out"></span> Limpiar filtro
                </button>
            </fieldset>
        </div>
        <button id="searchbutton" class="btn btn-info">BOTÓN</button>
        <div class="clear" style="height: 20px"></div>
        
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
                            <span class="price_<% out.print(i);i++;%>">${product.getPrecio()} $</span>
                        </p>
                    </div>
                </div>
            </div>
        </t:forEach>
        </div>
        <script>
            
            function realizarFiltro(class_select, filter, num) {
            if ($(class_select + num).text() === filter) {
                $(class_select + num).css('display', 'none');
            }
        }

        // filtrado de productos de "x" precio                
        function productosPrecio() {
        }

        // filtrado de productos de "x" nombre
        function productosNombre(nombre, max) {
            var prods = new Array();
            var x = 0;
            for (var i = 0; i < max; i++) {
                if ($('.name_' + i).text().match(nombre)) {
                    prods[x] = i; //prods.push($('.prod_' + i).text());  
                   x++;
                }
            };
            alert("prodsNombre:"+prods);
            return prods;
        }

        // filtrado de productos de "x" tipo
        function productosTipo(tipo, max) {
            var prods = new Array();
            var x = 0;

            for (var i = 0; i < max; i++) {
                if ($('.type_' + i).text() === tipo) {
                    prods[x] = i; //prods.push($('.prod_' + i).text());  
                    x++;
                }/* else {
                 $('.prod_' + i).css('display', 'none');
                 }*/
            };
            //alert("tipo":rods);
            return prods;
        }

        // limpia todos los inputs y restablece los productos
        function cleanAll() {
            var max = $(".thumbnail").length;
            for (var i = 0; i < max; i++) {
                $('.prod_' + i).css('display', 'block');
            }
            $("#filtroPrecioMin").val('');
            $("#filtroPrecioMax").val('');
            $("#filtroNombre").val('');
            $("#filtroTipo").val("");
        }
        
        $(document).ready(function() {

            // show/hidden div de filtrado
            $("#showfiltro").click(function() {
                $("#filtroBusqueda").toggle();

                var propiedad = $("#filtroBusqueda").css('display');

                if (propiedad === 'block') {
                    $("#showfiltro").text("Ocultar filtro");
                } else {
                    $("#showfiltro").text("Mostrar filtro");
                }
            });

            // limpia opciones elegidas y/o texto introducido al pulsar botón
            $("#btnClean").click(function() {
                cleanAll();
            });

            $("#btnSubmit").click(function() {
                var max = $(".thumbnail").length; //numero max productos
                var filtroName = $('#filtroNombre').val(); // campo de filtrado de nobmre
                var filtroMax = $('#filtroPrecioMax').val(); // campo de filtrado de precio maximo
                var filtroMin = $('#filtroPrecioMin').val(); // campo de filtrado de precio minimo
                //variable para la seleccion de tipo producto en el combobox
                var filtroOptionSelected = $("#filtroTipo").val();


                if (filtroOptionSelected !== "") {

                    var prodsTipo = productosTipo(filtroOptionSelected, max);


                    // mostrar productos
                    cleanAll();
                    var x = 0;
                    for (var i = 0; i < max; i++) {

                        if (prodsTipo[x] !== i) {
                            $('.prod_' + i).css('display', 'none');
                        } else {
                            $('.prod_' + i).css('display', 'block');
                            x++;
                        }
                    }; // fin for
                    $("#filtroTipo").val(filtroOptionSelected); // restablece el tipo, ya que limpiamos previamente
                }

                if (filtroName !== "") {
                    var prodsName = productosNombre(filtroName, max);
                }
                
                
                /*var result = $.merge(prodsTipo,prodsName);
                alert("r:"+result);
                var result2 = $.unique(result);
                alert("r2:"+result2);
                */

            }); // fin click boton submit 
        });//fin document ready
        
        
            /*function realizarFiltro(class_select, filter, num) {
                if($(class_select + num).text() === filter) {
                    $(class_select + num).css('display', 'none');
                }
            }

            $(document).ready(function() {
                $("#searchbutton").click(function () {    
                    $('#search').toggle("swing");
                });
                $("#btnClean").click(function () {    
                    var max = $(".thumbnail").length;
                    for ( var i = 0; i < max; i++ ) {
                        $('.prod_' + i).css('display', 'block');
                    }
                });
                $("#btnSubmit").click(function(){
                    var max = $(".thumbnail").length;
                    var filtro = $('#filtronombre').val();


                    var filtroname = $('#filtronombre').val();
                    var filtroPrecioMin = $('#filtroPrecioMin').val();
                    var filtroPrecioMax = $('#filtroPrecioMax').val();
                    var filtrotipo = []; 
                    $('#filtrotipo :selected').each(function(i, selected){ 
                      filtrotipo[i] = $(selected).text(); 
                    });
                    var maxtipo = filtrotip.length;

                    for ( var i = 0; i < max; i++ ) {
                        if(filtroname !== '') {
                            realizarFiltro('.name_', filtroname, i);
                        }
                        if(filtroPrecioMin !== '' && filtroPrecioMax !== '') {
                            //realizarFiltro()
                        }
                        for(var j = 0; j < maxtipo; j++) {
                            realizarFiltro()
                        }


                        if($('.type_' + i).text() === filtro) {
                            $('.prod_' + i).css('display', 'none');
                        } else if (filtro === "") {
                            $('.prod_' + i).css('display', 'block');
                        }*/
                        /*var data = "NOMBRE: " + $('.name_' + i).text() +'\n';
                        data += "TIPO: " + $('.type_' + i).text() +'\n';
                        data += "PRECIO: " + $('.price_' + i).text();
                        alert(data);*//*
                    };
                }); 
            });*/

        </script>