/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

contador = 1;

Array.prototype.remove = function (x) {
    var i;
    for (i in this) {
        if (this[i].toString() === x.toString()) {
            this.splice(i, 1);
        }
    }
};

function agregarMuestra() {
    fila = "<div id=" + contador + " class=\"col-sm-12\">";
    fila += "<div class=\"col-sm-3\">";
    fila += "<label for=\"veneno\" class=\"control-label\">*Veneno de Producción</label>";
    fila += "<div class=\"form-group\">";
    fila += "    <div class=\"col-sm-12\">";
    fila += "        <div class=\"input-group\">";
    fila += "            <select id=\"seleccionVeneno_" + contador + "\" class=\"tipomuestra tipomuestra_" + contador + "\" name=\"veneno_" + contador + "\"";
    fila += "                    style='background-color: #fff;' required";
    fila += "                    oninvalid=\"setCustomValidity('Este campo es requerido')\">";
    fila += "                <option value=''></option>";
    fila += "            </select>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";
    fila += "<div class=\"col-sm-2\">";
    fila += "<label for=\"peso\" class=\"control-label\">*Peso (mg)</label>";
    fila += "<div class=\"form-group\">";
    fila += "<div class=\"col-sm-12\">";
    fila += "<div class=\"input-group\">";
    fila += "            <input type=\"number\" min=\"0\" id=\"peso_" + contador + "\" class=\"input-identificadores identificadores_" + contador + " \" name=\"peso_" + contador + "\" ";
    fila += "          required ";
    fila += "          oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "          onchange=\"setCustomValidity('')\"";
    fila += "          oninput=\"setCustomValidity('')\" > ";
    fila += "</div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";
    fila += "<div class=\"col-sm-2\"> <br>";
    fila += '           <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarMuestra(\'' + contador + '\')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += "</div>";
    fila += "</div>";
    

    $(".muestras").append(fila);
   
    $(".seleccionVeneno_" + contador).select2();

    var listaTipoMuestra = $("#listaTipoMuestra").val();
    
    var parseLista = JSON.parse(listaTipoMuestra);
    //$("#seleccionVeneno_" + contador).append('<optgroup id="seleccionvenenos_' + contador + '" label="Veneno de Producción"></optgroup>');
    $.each(parseLista, function (index, value) {
        $("#seleccionVeneno_" + contador).append("<option value=" + value[0] + ">" + value[1] + "</option>");
    });

    var muestras = $("#listaMuestras").val();
    if (muestras === '') {
        $("#listaMuestras").val(contador);
    } else {
        $("#listaMuestras").val(muestras + ',' + contador);
    }
    contador++;
}

function eliminarMuestra(id) {
    //var valor = $("#seleccionVeneno_" + id).val();
    //var texto = $("#seleccionVeneno_" + id + " :selected").text();
    //var nuevo_tipo = [valor, texto];
    var lista = $("#listaMuestras").val().split(",");
    $("div > #" + id).remove();
    lista.remove(id.toString());
    lista = lista.join();
    $("#listaMuestras").val(lista);

    //Agrega el TM seleccionado para los proximos
    //var listaTipoMuestra = $("#listaTipoMuestra").val();
    //var parseLista = JSON.parse(listaTipoMuestra);
    //parseLista = parseLista.concat([nuevo_tipo]);
    //parseLista = JSON.stringify(parseLista);
    //$("#listaTipoMuestra").val(parseLista);

    //recargarTipoMuestra();

}



/*
 * 
 * Código para la funcionalidad de ver solicitud
 * 
 */

$(document).ready(function () {
   

    //Formatea las solicitudes ya antes agregadas
    var ids = $("#listaIds").val();
    if (ids !== "") {
        var listaIds = ids.split(",");
        var cantidad = listaIds.length;
        contador = cantidad + 1;
    } else {
        contador = 1;
    }

    $.each(listaIds, function (i, contador) {
        var tipomuestra = $("#editartipomuestra_" + contador).val();

        $(".seleccionVeneno_" + contador).select2();

        var listaTipoMuestra = $("#listaTipoMuestra").val();
        var parseLista = JSON.parse(listaTipoMuestra);
        //$("#seleccionTipo_" + contador).append('<optgroup id="seleccionmuestras_' + contador + '" label="Tipos de Muestra"></optgroup>');
        $.each(parseLista, function (index, value) {
            if (tipomuestra.toString() === value[0].toString()) {
                $("#seleccionVeneno_" + contador).append("<option value='" + value[0] + "' selected=\"selected\">" + value[1] + "</option>");
                $("#seleccionVeneno_" + contador).select2("val", value[0]);

            } else {
                $("#seleccionVeneno_" + contador).append("<option value=" + value[0] + ">" + value[1] + "</option>");

            }
        });

        var muestras = $("#listaMuestras").val();
        if (muestras === '') {
            $("#listaMuestras").val(contador);
        } else {
            $("#listaMuestras").val(muestras + ',' + contador);
        }
    });



});
