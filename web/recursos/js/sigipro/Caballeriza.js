// Variables globales de tablas
tEventos = null;
tCaballos = null;
T_EVENTOS_SELECTOR= "#caballos-evento";
T_CABALLOS_SELECTOR = "#caballos-grupo";

$(document).ready(function () {
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
});

// -- Caballos -- //
function agregarEventoCaballo() {
    var eventoSeleccionado = $("#eventoModal :selected");
    eventoSeleccionado.remove();
    var spliter = eventoSeleccionado.val().split("|");
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm boton-accion' style='margin-left:7px;margin-right:5px;' onclick=eliminarEventoDeCaballo(" + spliter[0] + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tEventos.row.add([spliter[2],spliter[3],spliter[4], botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "evento-" + spliter[0]);
}

function agregarCaballo() {
    var caballoSeleccionado = $("#seleccioncaballo :selected");
    caballoSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarCaballo(" + caballoSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tCaballos.row.add([caballoSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "caballo-" + caballoSeleccionado.val());
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

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    $("#seleccioncaballo").append(nuevaOpcion);
}
function eliminarCaballoSP(id_caballo) {
  fila = $('#' + id_caballo);
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

$(document).ready(function(){
     
    $("select[name='tipoevento']").on('change', function() {
        var split = $(this).val().split(",");
    
        $("textarea ").val(split[1]);
});
});
$(document).ready(function(){
     
    $("select[name='eventoModal']").on('change', function() {
        var split = $(this).val().split("|");
    
        $("textarea ").val(split[1]);
});
});
function confirmacionAgregarCaballos() {
    serpientesCodificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    
    {
      fila = $(this);
      serpientesCodificados += fila.attr('id');
      serpientesCodificados += "#r#";
    });
    $('#caballos').val(serpientesCodificados.slice(0, -3));
    //alert("El valor del campo escondido de permisos es: "+ $('#permisosRol').val());
}
$(document).ready(function () {

    $("select[name='inoculogrupo']").on('change', function () {
        document.getElementById("seleccionInoculoCaballo").disabled = false;
        id_grupo = this.value;
        seleccion = document.getElementById("seleccionInoculoCaballo");
        opciones = seleccion.getElementsByTagName("optgroup");
        for (i = 0; i < opciones.length; i++) //recoremos todos los controles
        {
            id_opt = opciones[i].id;
            if (opciones[i].id != id_grupo) //solo si es un checkbox entramos
            {
                $("select#seleccionInoculoCaballo option[id=" + id_opt + "]").remove();
                //opciones[i].disable = true; //si es un checkbox le damos el valor del checkbox que lo llamó (Marcar/Desmarcar Todos)
            }

        }
        document.getElementById("seleccionInoculoGrupo").disabled = true;
    });
});
//$(document).ready(function () {
//
//    $("select[name='inoculo']").on('change', function () {
//        document.getElementById("sangriap_caballos").hidden = false;
//        id_inoculo = this.value;
//        tabla = document.getElementById("sangriap_caballos");
//        filas = tabla.getElementsByTagName("tr");
////        document.getElementById("seleccionInoculoGrupo").disabled = true;
//    });
//});