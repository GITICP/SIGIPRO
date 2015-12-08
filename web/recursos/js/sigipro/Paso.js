contador = 1;
opciones=  1;

$(function () {
    $("#sortable").sortable({
        placeholder: "ui-state-highlight",
        update: function () {
            var ordenElementos = $(this).sortable("toArray").toString();
            $("#orden").val(ordenElementos);
        }
    });
    $("#sortable").disableSelection();
    
    var ordenActual = $("#orden").val();
    var ordenNuevo = ordenActual.replace("]","").replace("[","").replace(/ /g,"");
    $("#orden").val(ordenNuevo);
    contador = parseInt($("#contador").val())+1;
    opciones = parseInt($("#cantidad").val())+1;
    
});

function agregarCampo() {
    fila = "<div class=\"widget widget-table campo_" + contador + "\" id=\"" + contador + "\">";
    fila += "<input hidden=\"true\" id=\"elemento_" + contador + "\" value=\"campo\">";
    fila += "   <div class=\"widget-header\">";
    fila += "       <h3><i class=\"fa fa-edit\"></i> Campo #" + contador + "</h3>";
    fila += "       <div class=\"btn-group widget-header-toolbar\">";
    fila += '           <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo(\'campo_' + contador + '\')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += "       </div>";
    fila += "   </div>";
    fila += "   <div class=\"widget-content\">";
    fila += "       <div class=\"col-md-12\">";
    fila += "           <label for=\"tipo\" class=\"control-label\"> *Tipo de Campo</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <select id=\"tipocampo_" + contador + "\" class=\"select2\" name=\"c_tipocampo_" + contador + "\"";
    fila += "                           style=\'background-color: #fff;\' required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           onchange=\"setCustomValidity(\'\')\">";
    fila += "                           <option value=\'\'></option>";
    fila += "                           <option value=\"number\">N&uacute;mero</option>";
    fila += "                           <option value=\"text\">Campo de Texto</option>";
    fila += "                           <option value=\"textarea\">Area de Texto</option>";
    fila += "                           <option value=\"fecha\">Fecha</option>";
    fila += "                       </select>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "           <label for=\"nombre\" class=\"control-label\">*Nombre del Campo</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre\" class=\"form-control\" name=\"c_nombre_" + contador + "\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" > ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "   </div>";
    fila += "</div>";
    var orden = $("#orden").val();
    if (orden === '') {
        $("#orden").val(contador);
    } else {
        $("#orden").val(orden + ',' + contador);
    }
    var orden = $("#orden").val();
    $(".campos").append(fila);

    $("#tipocampo_" + contador).select2();

    contador++;

}

function agregarSeleccion() {
    fila = "<div class=\"widget widget-table seleccion_" + contador + "\" id=\"" + contador + "\">";
    fila += "<input hidden=\"true\" id=\"elemento_" + contador + "\" value=\"seleccion\">";
    fila += "   <div class=\"widget-header\">";
    fila += "       <h3><i class=\"fa fa-edit\"></i> Selección Múltiple #" + contador + "</h3>";
    fila += "       <div class=\"btn-group widget-header-toolbar\">";
    fila += '           <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo(\'seleccion_' + contador + '\')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += "       </div>";
    fila += "   </div>";
    fila += "   <div class=\"widget-content\">";
    fila += "       <div class=\"col-md-12\">";
    fila += "           <label for=\"tipo\" class=\"control-label\"> *Nombre de Selección Múltiple</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre\" class=\"form-control\" name=\"s_snombre_" + contador + "\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" > ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "           <label for=\"nombre\" class=\"control-label\">*Opciones</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group opciones_" + contador + "\">";
    fila += "                       <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre de la Opción\" class=\"form-control\" name=\"o_opcion_" + contador +"_"+opciones+"\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" > ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "            <div class='col-md-12 form-group'>";
    fila += "                <button type=\"button\" onclick=\"agregarOpcion(" + contador + "); \" class=\"btn btn-primary\"><i class=\"fa fa-plus-circle\"></i> Agregar Opción</button>";
    fila += "                <br>";
    fila += "            </div>";
    fila += "       </div>";
    fila += "   </div>";
    fila += "</div>";
    var orden = $("#orden").val();
    if (orden === '') {
        $("#orden").val(contador);
    } else {
        $("#orden").val(orden + ',' + contador);
    }
    var orden = $("#orden").val();
    $(".campos").append(fila);

    contador++;
    opciones++;

}

function agregarOpcion(id) {
    fila = "                      <div class='col-md-8 o_opcion"+opciones+"_" + id+"_"+opciones+"'> <br><input type=\"text\" maxlength=\"45\" placeholder=\"Nombre de la Opción\" class=\"form-control\" name=\"o_opcion"+opciones+"_" + id+"_"+opciones+"\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" ></div> ";
    fila += '          <div class=\'col-md-4 o_opcion'+opciones+'_' + id+'_'+opciones+'\'> <br> <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarOpcion(\'o_opcion'+opciones+'_' + id+'_'+opciones+'\')" style="margin-left:7px;margin-right:5px;">Eliminar</button> </div>';

    $(".opciones_"+id).append(fila);
    
    opciones++;

}

Array.prototype.remove = function (x) {
    var i;
    for (i in this) {
        if (this[i].toString() === x.toString()) {
            this.splice(i, 1)
        }
    }
};

function eliminarCampo(campo) {
    var o = $("#orden").val().split(",");
    $("div > ." + campo).remove();
    var nombres = campo.split("_");
    if (nombres[0] === "campo" || nombres[0] === "tabla") {
        o.remove(nombres[1].toString());
        o = o.join();
        $("#orden").val(o);
    }
}

function eliminarOpcion(campo) {
    $("div > ." + campo).remove();
}

