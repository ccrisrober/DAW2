                    <form role="form" method="post" action="ProductoController1">
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
                            <label for="filefield">Foto</label><input type="file" id="exampleInputFile" />
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                        
                        
                            <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield[0]" name="pricefield[0]"/>
                            <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield[1]" name="pricefield[1]"/>
                            <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield[2]" name="pricefield[2]"/>
                            <label for="pricefield">Precio</label><input type="text" class="form-control" id="pricefield[3]" name="pricefield[3]"/>
                        
                        
                        
                        
                    </form>