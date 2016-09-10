/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

PARAMETROS_SELECT_IDENTIFICADORES = {
        minimumResultsForSearch: -1,
        tags: true,
        tokenSeparators: [',', '+', '.']
    };

$(document).on("click", ".anular-Modal", function () {
    var id_solicitud = $(this).data('id');
    var fila = $(this).parent().parent();
    var filas = fila.find("td");
    $("#modal_anular_num_solicitud").text(fila.find("td div").text());
    $("#modal_anular_solicitante").text(filas.eq(4).text());
    $("#modal_anular_tipos_muestras").text(filas.eq(3).text());
    $("#modal_anular_informacion").text(filas.eq(2).text());
    $("#id_solicitud_anular").val(id_solicitud);
});

$(document).on("click", ".recibir-Modal", function () {
    var id_solicitud = $(this).data('id');
    var fila = $(this).parent().parent();
    var filas = fila.find("td");
    $("#modal_num_solicitud").text(fila.find("td div").text());
    $("#modal_solicitante").text(filas.eq(4).text());
    $("#modal_tipos_muestras").text(filas.eq(3).text());
    $("#modal_informacion").text(filas.eq(2).text());
    $("#id_solicitud_recibir").val(id_solicitud);
});

contador = 1;

Array.prototype.remove = function (x) {
    var i;
    for (i in this) {
        if (this[i].toString() === x.toString()) {
            this.splice(i, 1);
        }
    }
};

function seleccionTipoMuestra(tipomuestra, id_formulario) {
    var id = $(tipomuestra).val();
    $("#seleccionAnalisis_" + id_formulario).empty();
    var listaAnalisis = $("#listaAnalisis_" + id).val();
    var dias_descarte = $("#listaAnalisis_" + id).data("dias-descarte");
    var parseLista = JSON.parse(listaAnalisis);
    $("#seleccionAnalisis_" + id_formulario).append('<optgroup id="opt-gr1_' + id_formulario + '" label="An&aacute;lisis Asociados"></optgroup>');
    $("#seleccionAnalisis_" + id_formulario).append('<optgroup id="opt-gr2_' + id_formulario + '" label="Otros"></optgroup>');
    $.each(parseLista, function (index, value) {
        if (value[2] === "true") {
            $("#opt-gr1_" + id_formulario).append("<option value=" + value[0] + ">" + value[1] + "</option>");
            $("#analisis_" + id_formulario).val([0]);
        }
        else {
            $("#opt-gr2_" + id_formulario).append("<option value=" + value[0] + ">" + value[1] + "</option>");
        }
    });

    $("#datepicker_" + id_formulario).datepicker("setDate", "+" + dias_descarte + "d");

    var analisis = $("#editaranalisis_" + id_formulario).val();

    if (analisis === undefined) {
        var seleccionados = $(".analisis_" + id_formulario).find("#opt-gr1_" + id_formulario + " > option");
        var selected = [];
        $.each(seleccionados, function (i, e) {
            selected[selected.length] = $(e).attr("value");
        });
        $(".analisis_" + id_formulario).select2("val", selected);
    } else {
        $(".analisis_" + id_formulario).select2("val", analisis.replace(" ", "").split(","));
    }
    
    //Elimina el TM seleccionado para los proximos
    //var listaTipoMuestra = $("#listaTipoMuestra").val();
    //var parseLista = JSON.parse(listaTipoMuestra);
    //var nueva_lista = [];
    //$.each(parseLista, function (e, i) {
    //    if (i[0].toString() !== id) {
    //        nueva_lista = nueva_lista.concat([i]);
    //    }
    //});
    //nueva_lista = JSON.stringify(nueva_lista);
    //$("#listaTipoMuestra").val(nueva_lista);
    //recargarTipoMuestra();
}

function recargarTipoMuestra() {
    var listaTipoMuestra = $("#listaTipoMuestra").val();
    var listaMuestra = $("#listaMuestras").val().split(",");
    var parseLista = JSON.parse(listaTipoMuestra);

    alert(parseLista);
    $.each(listaMuestra, function (i, e) {
        var seleccionado = $("#seleccionTipo_" + e + " :selected");
        $("#seleccionTipo_" + e).empty().append('<optgroup id="seleccionmuestras_' + e + '" label="Tipos de Muestra"></optgroup>');
        if (seleccionado.val() !== "") {
            $("#seleccionmuestras_" + e).append("<option value=" + seleccionado.val() + " selected>" + seleccionado.text() + "</option>");
        }
        $.each(parseLista, function (index, value) {
            $("#seleccionmuestras_" + e).append("<option value=" + value[0] + ">" + value[1] + "</option>");
        });

    });
}


function agregarMuestra() {
    fila = "<div id=" + contador + " class=\"col-sm-12\">";
    fila += "<div class=\"col-sm-3\">";
    fila += "<label for=\"tipo_reactivo\" class=\"control-label\">*Tipo de Muestra</label>";
    fila += "<div class=\"form-group\">";
    fila += "    <div class=\"col-sm-12\">";
    fila += "        <div class=\"input-group\">";
    fila += "            <select id=\"seleccionTipo_" + contador + "\" class=\"tipomuestra tipomuestra_" + contador + "\" name=\"tipomuestra_" + contador + "\"";
    fila += "                    style='background-color: #fff;' required";
    fila += "                    oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                    onchange=\"seleccionTipoMuestra(this," + contador + ")\">";
    fila += "                <option value=''></option>";
    fila += "            </select>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";
    fila += "<div class=\"col-sm-3\">";
    fila += "<label for=\"nombre\" class=\"control-label\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"Separar con coma, punto o '+'\">*Identificadores <i class=\"fa fa-question-circle\"></i></label>";
    fila += "<div class=\"form-group\">";
    fila += "<div class=\"col-sm-12\">";
    fila += "<div class=\"input-group\">";
    fila += "            <input type=\"text\" placeholder=\"Separar con coma, punto o '+' \" id=\"identificadores_" + contador + "\" class=\"input-identificadores identificadores_" + contador + " \" name=\"identificadores_" + contador + "\" ";
    fila += "          required ";
    fila += "          oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "          onchange=\"setCustomValidity('')\"";
    fila += "          oninput=\"setCustomValidity('')\" > ";
    fila += "</div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";
    fila += "<div class=\"col-sm-2\">";
    fila += "<label for=\"fecha_ingreso\" class=\"control-label\">Fecha de Descarte</label>";
    fila += "<div class=\"form-group\">";
    fila += "    <div class=\"col-sm-12\">";
    fila += "        <div class=\"input-group\">";
    fila += "            <input type=\"text\" id=\"datepicker_" + contador + "\" class=\"form-control\" name=\"fechadescarte_" + contador + "\"";
    fila += "                   oninvalid=\"setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')\"";
    fila += "                   onchange=\"setCustomValidity('')\">";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div> ";
    fila += "<div class=\"col-sm-3\">";
    fila += "    <label for=\"analisis\" class = \"control-label\" > *An&aacute;lisis </label>";
    fila += "<div class=\"form-group\">";
    fila += "        <div class=\"col-sm-12\">";
    fila += "   <div class=\"input-group\">";
    fila += "       <select id=\"seleccionAnalisis_" + contador + "\" class=\"select-analisis analisis_" + contador + "\" multiple=\"multiple\" name=\"analisis_" + contador + "\"";
    fila += "               style='background-color: #fff;' required";
    fila += "               oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "               onchange=\"setCustomValidity('')\">";
    fila += "           <option value=''></option>";
    fila += "       </select>";
    fila += "   </div>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "<div class=\"col-sm-1\"> <br>";
    fila += '           <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarMuestra(\'' + contador + '\')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += "</div>";
    fila += "</div>";

    $(".muestras").append(fila);

    $(".analisis_" + contador).select2();
    $("#identificadores_" + contador).select2(PARAMETROS_SELECT_IDENTIFICADORES)
      .on("change", function (e) {
        $(".select2-drop").hide();
    }).on("select2-opening", function () {
        $(".select2-drop").hide();
    }).on("select2-open", function () {
        $(".select2-drop").hide();
    });
   
    $(".tipomuestra_" + contador).select2();

    $('#datepicker_' + contador).datepicker({startDate: '-0d', format: 'dd/mm/yyyy'})
            .on('changeDate', function () {
                $(this).datepicker('hide');
                var indice = ($(':input').index(this) + 1);
                var proximo_elemento = $(':input:eq(' + indice + ')');
                while (proximo_elemento.attr('hidden') === "hidden") {
                    indice++;
                    proximo_elemento = $(':input:eq(' + indice + ')');
                }
                proximo_elemento.focus();
            });

    var listaTipoMuestra = $("#listaTipoMuestra").val();
    var parseLista = JSON.parse(listaTipoMuestra);
    $("#seleccionTipo_" + contador).append('<optgroup id="seleccionmuestras_' + contador + '" label="Tipos de Muestra"></optgroup>');
    $.each(parseLista, function (index, value) {
        $("#seleccionmuestras_" + contador).append("<option value=" + value[0] + ">" + value[1] + "</option>");
    });

    var muestras = $("#listaMuestras").val();
    if (muestras === '') {
        $("#listaMuestras").val(contador);
    } else {
        $("#listaMuestras").val(muestras + ',' + contador);
    }
    
    $('[data-toggle="tooltip"]').tooltip(); 

    contador++;
}

function eliminarMuestra(id) {
    var valor = $("#seleccionTipo_" + id).val();
    var texto = $("#seleccionTipo_" + id + " :selected").text();
    var nuevo_tipo = [valor, texto];
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
    
    $("#btn-agrupar-muestras").click(function () {
        
        var select_tipo = $("#seleccion-tipo-muestra");
        var select_muestras = $("#seleccion-muestras");
        var id_tipo_muestra = select_tipo.find("option:selected").data("tipo");
        
        select_muestras.find("option").each(function() {
            if (id_tipo_muestra === $(this).data("tipo")) {
                $(this).attr("selected", true);
            }
            select_muestras.select2();
        });
        
    });

    $("#seleccion-tipo-muestra").change(function () {

        var select_muestras = $("#seleccion-muestras");
        select_muestras.attr("disabled", false);
        var id_tipo_muestra_seleccionada = $(this).find("option:selected").data("tipo");

        select_muestras.find("option").each(function () {
            $(this).attr("selected", false);
            var id_tipo_muestra_opcion = $(this).data("tipo");
            if (id_tipo_muestra_opcion === id_tipo_muestra_seleccionada) {
                $(this).removeClass("opcion-escondida");
            } else {
                $(this).addClass("opcion-escondida");
            }
            $("#btn-agrupar-muestras").attr("disabled", false);
        });
        select_muestras.select2();
    });

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
        $(".analisis_" + contador).select2();
        $("#identificadores_" + contador).select2(PARAMETROS_SELECT_IDENTIFICADORES)
          .on("change", function (e) {
            $(".select2-drop").hide();
        }).on("select2-opening", function () {
            $(".select2-drop").hide();
        }).on("select2-open", function () {
            $(".select2-drop").hide();
        });

        $(".tipomuestra_" + contador).select2();

        $('#datepicker_' + contador).datepicker({startDate: '-0d', format: 'dd/mm/yyyy'})
                .on('changeDate', function () {
                    $(this).datepicker('hide');
                    var indice = ($(':input').index(this) + 1);
                    var proximo_elemento = $(':input:eq(' + indice + ')');
                    while (proximo_elemento.attr('hidden') === "hidden") {
                        indice++;
                        proximo_elemento = $(':input:eq(' + indice + ')');
                    }
                    proximo_elemento.focus();
                });

        var listaTipoMuestra = $("#listaTipoMuestra").val();
        var parseLista = JSON.parse(listaTipoMuestra);
        $("#seleccionTipo_" + contador).append('<optgroup id="seleccionmuestras_' + contador + '" label="Tipos de Muestra"></optgroup>');
        $.each(parseLista, function (index, value) {
            if (tipomuestra.toString() === value[0].toString()) {
                $("#seleccionmuestras_" + contador).append("<option value='" + value[0] + "' selected=\"selected\">" + value[1] + "</option>");
                $("#seleccionTipo_" + contador).select2("val", value[0]);
                seleccionTipoMuestra($("#seleccionTipo_" + contador), contador);

            } else {
                $("#seleccionmuestras_" + contador).append("<option value=" + value[0] + ">" + value[1] + "</option>");

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
