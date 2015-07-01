/* global tEventos, tCaballos */

// Variables globales de tablas
tEventos = null;
tCaballos = null;
valor_fecha_hoy = null;
T_EVENTOS_SELECTOR = "#caballos-evento";
T_CABALLOS_SELECTOR = "#caballos-grupo";
SELECTOR_SELECT_CABALLOS_GRUPO = "#seleccioncaballo";

$(document).ready(function () {
    var hoy = new Date();
    var dd = hoy.getDate();
    var mm = hoy.getMonth() + 1; //January is 0!
    var yyyy = hoy.getFullYear();

    valor_fecha_hoy = dd + mm * 100 + yyyy * 10000;

    var configuracion = {
        "paging": false,
        "ordering": false,
        "bFilter": false,
        "info": false
    };

    tEventos = $("#caballos-evento").DataTable(configuracion);
    tCaballos = $("#caballos-grupo").DataTable(configuracion);

    $("#caballosform").submit(function () {
        llenarCampoAsociacion('c', T_EVENTOS_SELECTOR, $("#ids-eventos"));
    });

    $("#grupodecaballosForm").submit(function () {
        llenarCampoAsociacion('c', T_CABALLOS_SELECTOR, $("#ids-caballos"));
    });

    $("#checkbox-asociar-caballos").change(function () {
        $(".cuadro-opciones").toggle();
        $("input[name='caballos']").each(function () {
            $(this).prop("checked", false);
        });
    });

    $(".seleccionar-todo").click(function () {
        var widget_padre = $(this).parent().parent().parent();
        if ($(this).html() === "Marcar Todos") {
            widget_padre.find("input[name='caballos']").each(function () {
                $(this).prop("checked", true);
            });
            $(this).html("Desmarcar Todos");
        } else {
            widget_padre.find("input[name='caballos']").each(function () {
                $(this).prop("checked", false);
            });
            $(this).html("Marcar Todos");
        }
    });

    $(".peso-caballo").click(function () {
        var fila = $(this).parent().parent();
        var idPeso = fila.data("id-peso");
        var fecha = fila.find(".fecha").text();
        var peso = fila.find(".peso").text();
        $("#editar-id-peso").val(idPeso);
        $("#editar-fecha-peso").val(fecha);
        $("#editar-peso").val(peso);
        $("#modalEditarPeso").modal("show");
    });

    $(".peso-caballo-eliminar").click(function () {
        var fila = $(this).parent().parent();
        var idPeso = fila.data("id-peso");
        $("#eliminar-peso-id").val(idPeso);
        $("#ModalConfirmacionEliminar").modal("show");
    });

    $("#caballosform").submit(function () {
        if ($("#mensaje-fechas").html() === "") {
            return true;
        } else {
            $('#ModalFechas').remove();
            $('body').append("<div class='modal fade' id='ModalFechas' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'> La fecha de nacimiento no puede ser mayor que la de ingreso, y ambas deben ser antes que hoy.</h5>\
                  <br>\
              </div>\
              <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-primary' data-dismiss='modal'><i class='fa fa-times-circle'></i> Confirmar</button>\
                    </div>\
                  </div>\
            </div>\
          </div>\
        </div>");
            $("#ModalFechas").modal('show');
            return false;
        }
    });

    $(".fecha-caballo").change(function () {
        try {
            var fecha_nacimiento = $("#nacimiento-caballo").val();
            var partes_fecha_nac = fecha_nacimiento.split("/");
            var valor_fecha_nac = parseInt(partes_fecha_nac[2]) * 10000 + parseInt(partes_fecha_nac[1]) * 100 + parseInt(partes_fecha_nac[0]);

            var fecha_ingreso = $("#ingreso-caballo").val();
            var partes_fecha_ing = fecha_ingreso.split("/");
            var valor_fecha_ing = parseInt(partes_fecha_ing[2]) * 10000 + parseInt(partes_fecha_ing[1]) * 100 + parseInt(partes_fecha_ing[0]);

            if (valor_fecha_nac > valor_fecha_ing || valor_fecha_nac > valor_fecha_hoy || valor_fecha_ing > valor_fecha_hoy) {
                $("#mensaje-fechas").html("La fecha de nacimiento no puede ser mayor que la de ingreso, y ambas deben ser antes que hoy.");
            } else {
                $("#mensaje-fechas").html("");
            }
        } catch (error_fechas) {
            $("#mensaje-fechas").html("La fecha de nacimiento no puede ser mayor que la de ingreso y ambas deben ser antes que hoy.");
        }
    });
    
    $("#tabla-sangrias-caballos").find("input[name='caballos']").change(function(){
        var inputs = $(this).parent().parent().parent().find("input[type='number']");
        if( $(this).prop("checked")) {
            inputs.each(function(){
               $(this).prop("disabled", false) ;
            });
        } else {
            inputs.each(function(){
               $(this).prop("disabled", true) ;
            });
        }
    });
    
    var select_caballos_grupo = $(SELECTOR_SELECT_CABALLOS_GRUPO);
    if (select_caballos_grupo.length >= 1) {
        crearSelectEditar(SELECTOR_SELECT_CABALLOS_GRUPO);
    }
});

// -- Caballos -- //
function agregarEventoCaballo() {
    var eventoSeleccionado = $("#eventoModal :selected");
    eventoSeleccionado.remove();
    var spliter = eventoSeleccionado.val().split("|");
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm boton-accion' style='margin-left:7px;margin-right:5px;' onclick=eliminarEventoDeCaballo(" + spliter[0] + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tEventos.row.add([spliter[2], spliter[3], spliter[4], botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "evento-" + spliter[0]);
}

function agregarCaballo() {
    $(T_CABALLOS_SELECTOR).dataTable().fnClearTable();
    var select = $("#seleccioncaballo");
    var caballosSeleccionados = select.val();
    for (var i = 0; i < caballosSeleccionados.length; i++) {
        var elemento = select.find("option[value=" + caballosSeleccionados[i] + "]");

        var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarCaballo(" + elemento.val() + ")>");
        botonEliminar.text("Eliminar");

        var nuevaFila = tCaballos.row.add([elemento.text(), botonEliminar[0].outerHTML]).draw().node();
        $(nuevaFila).attr("id", "caballo-" + elemento.val());
    }
    $("#modalAgregarCaballo").modal("hide");
}

function eliminarEventoDeCaballo(id) {
    var fila = tEventos.row('#evento-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();
}

function eliminarCaballo(id) {
    var fila = tCaballos.row('#caballo-' + id);

    fila.remove().draw();

    eliminarDeSelect(id, "#seleccioncaballo");
}

function eliminarCaballoSP(id_caballo) {
    var fila = $('#' + id_caballo);
    fila.remove();
}

function llenarCampoAsociacion(string_pivote, tabla_selector, campo_escondido) {
    var asociacionCodificada = "";
    var pivote = "#" + string_pivote + "#";
    $(tabla_selector).find("tr[id]").each(function () {
        var asociacion = pivote + $(this).attr('id').split("-")[1];
        asociacionCodificada += asociacion;
    });
    campo_escondido.val(asociacionCodificada);
}

$(document).ready(function () {
    
    $("#seleccionInoculoGrupo").change(function () {
        var id_grupo = $(this).val();

        $(".caballos-grupo").each(function () {
            $(this).find("input").each(function () {
                $(this).attr('checked', false);
            });
            $(this).hide();
        });
        $("#grupo-" + id_grupo).show();
    });

    $("#seleccionInoculo").change(function () {
        var id_inoculo = $(this).val();

        $(".tabla-caballos").each(function () {
            $(this).hide();
        });

        $("#inoculo-" + id_inoculo).show();
    });

    $("#seleccion-sangria-prueba").change(function () {
        var id_sangria_prueba = $(this).val();

        $(".caballos-prueba").each(function () {
            $(this).find("input").each(function () {
                $(this).attr('checked', false);
            });
            $(this).hide();
        });

        var contenedor_caballos = $("#prueba-" + id_sangria_prueba);
        $(contenedor_caballos).find("input").each(function () {
            $(this).attr('checked', true);
        });
        contenedor_caballos.show();
    });

    $("#form-prueba-sangria").submit(function () {
        llenar_campo_caballos();
    });
});

function llenar_campo_caballos() {
    var caballos_codificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    {
        var fila = $(this);
        caballos_codificados += fila.attr('id');
        caballos_codificados += "#c#";
    });
    $('#caballos').val(caballos_codificados.slice(0, -3));
}

function previstaImagen(input, id) {
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        var preview = document.getElementById("imagenSubida" + id); //selects the query named img
        var file = document.querySelector('input[id=' + input.id.toString() + ']').files[0]; //sames as here
        var size = file.size;
        var imagen = document.getElementById(input.id.toString());
        var reader = new FileReader();
        if (size > 102400) {
            input.setCustomValidity("La imagen debe ser de 100KB o menos. ");
            document.getElementById("botonCancelar" + id).style.visibility = "visible";
        } else {
            input.setCustomValidity("");
            document.getElementById("botonCancelar" + id).style.visibility = "visible";
        }
        reader.onload = function (e) {
            preview.src = reader.result;
            input.value = reader.toString();
        };
        if (file) {
            reader.readAsDataURL(file); //reads the data as a URL
        } else {
            preview.src = "";
        }
    } else {
        alert('The File APIs are not fully supported in this browser.');
    }
}

function mostrarGrande(imagen) {
    $('#imagenGrande').prop('src', imagen.src);
    $('#modalVerImagen').modal('show');

}

function eliminarImagen(id) {
    var preview = document.getElementById("imagenSubida" + id); //selects the query named img
    preview.src = "";
    var imagen = document.getElementById("imagen" + id);
    imagen.value = "";
    imagen.setCustomValidity("");
    document.getElementById("botonCancelar" + id).style.visibility = "hidden";
}

function borrarImagen(id) {
    var formData = $("#caballosform").serializeArray();
    var URL = $("#caballosform").attr("action") + "?accion=eliminarimagen&id_imagen="+id;
    $.ajax(
            {
                url : URL,
                type: "POST",
                success: function(data,textStatus,jqXHR)
                {
                    document.getElementById("botonBorrar"+id).style.visibility = "hidden";
                    $("#labelImagen"+id).text("Eliminada.");
                    $("#imagenActual"+id).prop("src","");
                    $("#imagenActual"+id).prop("height",0);
                    
                },
                error:function(jqXHR,textStatus,errorThrown)
                {
                    alert("Todo mal");
                    
                }
                
            });
    var preview = document.getElementById("imagenSubida" + id); //selects the query named img
    preview.src = "";
    var imagen = document.getElementById("imagen" + id);
    imagen.value = "";
    imagen.setCustomValidity("");
    document.getElementById("botonCancelar" + id).style.visibility = "hidden";
}


function eliminarDeSelect(id, selector_select) {
    var select = $(selector_select);
    var valor = select.val();
    var indice = valor.indexOf(id.toString());
    valor.splice(indice, 1);
    select.select2("val", valor);
}

function crearSelectEditar(selector_select) {
    var filas = $(T_CABALLOS_SELECTOR + ' tbody tr');
    var select = $(selector_select);
    var valor = [];

    if (filas.find(".dataTables_empty").length === 0) {
        filas.each(function () {
            valor.push($(this).attr('id').split('-')[1]);
        });
    }

    select.select2("val", valor);
}