<%-- 
    Document   : ImagenesAgregar
    Created on : May 31, 2015, 1:33:27 PM
    Author     : ld.conejo
--%>
<div class="col-md-12">
    <div class="col-md-4">
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen1" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 300KB o menos. ')" 
                               onchange="previstaImagen(this,1)" /> <button type="button" id='botonCancelar1' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(1)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida1" src='' height="100" alt=""></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen2" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 300KB o menos. ')" 
                               onchange="previstaImagen(this,2)" /> <button type="button" id='botonCancelar2' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(2)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida2" src='' height="100" alt=""></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen3" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 300KB o menos. ')" 
                               onchange="previstaImagen(this,3)" /> <button type="button" id='botonCancelar3' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(3)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida3" src='' height="100" alt=""></div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>
<div class="col-md-12">
    <div class="col-md-4">
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen4" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 300KB o menos. ')" 
                               onchange="previstaImagen(this,4)" /> <button type="button" id='botonCancelar4' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(4)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida4" src='' height="100" alt=""></div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="col-md-4">
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen5" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 300KB o menos. ')" 
                               onchange="previstaImagen(this,5)" /> <button type="button" id='botonCancelar5' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(5)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida5" src='' height="100" alt=""></div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>