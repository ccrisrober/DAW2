                    ${requestScope.error}
                    ${requestScope.ok}
                    <form role="form" method="post" action="ProductoController" enctype="multipart/form-data">
                        <input type="hidden" name="action" id="action" value="insert" />
                        <div class="form-group">
                            <label for="namefield">Nombre producto</label><input type="text" class="form-control" id="namefield" name="namefield"/>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="categoryfield" id="categoryfield">
                                <option value="">----</option>
                                <option value="Alimentación">Alimentación</option>
                                <option value="Droguería">Droguería</option>
                                <option value="Prensa">Prensa</option>
                                <option value="Ferretería">Ferretería</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield" name="pricefield"/>
                        </div>
                        <div class="form-group">
                            <label for="filefield">Foto</label><input type="file" id="file" name="file" />
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                        
                    </form>