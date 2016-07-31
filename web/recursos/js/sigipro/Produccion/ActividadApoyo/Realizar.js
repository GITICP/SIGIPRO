function previewFile(id) {
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        // Great success! All the File APIs are supported.
        var preview = document.getElementById(id + "_preview"); //selects the query named img
        var file = document.querySelector('input[type=file][id="' + id + '"]').files[0]; //sames as here
        var imagen = document.getElementById(id); //sames as here
        var size = file.size;
        var reader = new FileReader();
        if (size > 307200) {
            document.getElementById(id).setCustomValidity("La imagen debe ser de 300KB o menos. ");
            document.getElementById(id + "_eliminar").style.visibility = "visible";
        } else {
            document.getElementById(id).setCustomValidity("");
            document.getElementById(id + "_eliminar").style.visibility = "visible";
        }
        reader.onload = function (e) {
            preview.src = reader.result;
            file.value = reader.toString();
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

function eliminarImagen(id) {
    var preview = document.getElementById(id + "_preview"); //selects the query named img
    preview.src = "";
    var imagen = document.getElementById(id);
    imagen.value = "";
    document.getElementById(id).setCustomValidity("");
    document.getElementById(id + "_eliminar").style.visibility = "hidden";
}

$(function () {
    var sangrias = $(".sangria.select2-offscreen");

    $.each(sangrias, function (index, element) {
        $(element).on("select2-selecting", function (e) {
            generar_link_sangria(e, element);
        });
        $(element).on("select2-removing", function (e) {
            remover_link_sangria(e, element);
        });
        var nombre = $(element).attr("name");
        $.ajax({
            url: "/SIGIPRO/Caballeriza/Sangria",
            async: false,
            type: "GET",
            data: {"accion": "sangriasajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_sangria(datos, element);
            },
            error: function () {
                alert("Error en Sangrias");
            }
        });
    });

    var lotes = $(".lote.select2-offscreen");
    $.each(lotes, function (index, element) {
        $(element).on("change", generar_link_lote);
        $.ajax({
            url: "/SIGIPRO/Produccion/Lote",
            async: false,
            type: "GET",
            data: {"accion": "lotesajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_lote(datos, element);
            },
            error: function () {
                alert("Error en Lotes");
            }
        });
    });

    var cc = $(".cc.select2-offscreen");

    $.each(cc, function (index, element) {
        $(element).on("change", generar_link_cc);
        $.ajax({
            url: "/SIGIPRO/ControlCalidad/Solicitud",
            async: false,
            type: "GET",
            data: {"accion": "solicitudesajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_cc(datos, element);
            },
            error: function () {
                alert("Error en CC");
            }
        });

    });

    var usuarios = $("select[id^='usuario']");

    $.each(usuarios, function (index, element) {
        var id = $(element).prop("id").split("_")[1];
        $.ajax({
            url: "/SIGIPRO/Produccion/Lote",
            async: false,
            type: "GET",
            data: {"accion": "usuariosajax",
                "id_seccion": id},
            dataType: "json",
            success: function (datos) {
                generar_select_usuarios(datos, element);
            },
            error: function () {
                alert("Error en Usuarios");
            }
        });

    });

    var actividades = $("select[id^='aa']");

    $.each(actividades, function (index, element) {
        $(element).on("change", generar_link_aa);
        var id = $(element).prop("id").split("_")[1];
        $.ajax({
            url: "/SIGIPRO/Produccion/Actividad_Apoyo",
            async: false,
            type: "GET",
            data: {"accion": "actividadesajax",
                "id_actividad": id},
            dataType: "json",
            success: function (datos) {
                generar_select_actividades(datos, element);
            },
            error: function () {
                alert("Error en AA");
            }
        });

    });

    var subbodegas = $("select[id^='subbodega']");

    $.each(subbodegas, function (index, element) {
        var id = $(element).prop("id").split("_")[1];
        $(element).on("select2-selecting", function (e) {
            crear_cantidad(e, element);
        });
        $(element).on("select2-removing", function (e) {
            remover_cantidad(e, element);
        });
        $.ajax({
            url: "/SIGIPRO/Bodegas/SubBodegas",
            async: false,
            type: "GET",
            data: {"accion": "subbodegasajax",
                "id_subbodega": id},
            dataType: "json",
            success: function (datos) {
                generar_select_subbodegas(datos, element);
            },
            error: function (datos) {
                alert("Error en Subbodegas");
                alert(datos);
            }
        });

    });

});

function crear_cantidad(e, element) {
    //ID del elemento
    var valor = e.val;
    //Nombre del elemento
    var nombre = e.object.text;
    //Nombre del campo
    var id = $(element).attr("name");

    var fila = "        <div class=\"" + id + "_" + valor + "\"> <label for=\"nombre\" class=\"control-label\">Cantidad - " + nombre + "</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <input type=\"number\" placeholder=\"Cantidad\" class=\"form-control\" name=\"" + id + "_" + valor + "\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" ></input> ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div> </div>";

    var cantidad = $("." + id + "_cant");

    cantidad.append(fila);

}

function remover_cantidad(e, element) {
    var valor = e.val;
    var id = $(element).attr("name");

    $("div > ." + id + "_" + valor).remove();

}

$(document).on("click", ".aprobar-Modal", function () {
    var id_lote = $(this).data('id');
    var id_respuesta_actual = $(this).data('respuesta');
    var posicion_actual = $(this).data('posicion');
    $('#class-aprobar-paso #id_lote').val(id_lote);
    $("#class-aprobar-paso #id_respuesta_actual").val(id_respuesta_actual);
    $("#class-aprobar-paso #posicion_actual").val(posicion_actual);
});

$(document).on("click", ".revisar-Modal", function () {
    var id_lote = $(this).data('id');
    var id_respuesta_actual = $(this).data('respuesta');
    var version = $(this).data('version');
    $('#class-revisar-paso #id_lote').val(id_lote);
    $("#class-revisar-paso #id_respuesta_actual").val(id_respuesta_actual);
    $("#class-revisar-paso #version").val(version);
});

$(document).on("click", ".verificar-Modal", function () {
    var id_lote = $(this).data('id');
    var id_respuesta_actual = $(this).data('respuesta');
    var version = $(this).data('version');
    $('#class-verificar-paso #id_lote').val(id_lote);
    $("#class-verificar-paso #id_respuesta_actual").val(id_respuesta_actual);
    $("#class-verificar-paso #version").val(version);
});

$(document).on("click", ".vencimiento-Modal", function () {
    var id_lote = $(this).data('id');
    $('#class-vencimiento-lote #id_lote').val(id_lote);
});

$(document).on("click", ".distribucion-Modal", function () {
    var id_lote = $(this).data('id');
    $('#class-distribucion-lote #id_lote').val(id_lote);
});

function generar_select_sangria(datos, element) {
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_sangria + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.identificador);

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();

    });
    $(element).select2("val", lista);
}

function generar_select_lote(datos, element) {
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_lote + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre + " [" + elemento.nombreProtocolo + "]");

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();

    });
    $(element).select2("val", lista);

}

function generar_select_cc(datos, element) {

    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_solicitud + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.numero_solicitud);

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();

    });
    $(element).select2("val", lista);

}

function generar_select_usuarios(datos, element) {

    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_usuario + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre_completo);

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();

    });
    $(element).select2("val", lista);

}

function generar_select_actividades(datos, element) {
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_respuesta + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre + " [" + elemento.fecha + "]");

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();
    });
    $(element).select2("val", lista);

}

function generar_select_subbodegas(datos, element) {
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\"" + elemento.id_producto + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre);

        $(element).append(opcion);
    }
    var nombre = $(element).attr("name");
    var ids = $("." + nombre + "_id > input");
    var lista = [];
    $.each(ids, function (datos, elemento) {
        lista[lista.length] = $(elemento).val();
    });
    $(element).select2("val", lista);

}

function generar_link_sangria(e, element) {
    //ID del objeto
    var valor = e.val;
    //Nombre del objeto
    var nombre = e.object.text;
    //Nombre del campo
    var id = $(element).attr("name");

    var link = "<div class=\"" + id + "_" + valor + "\"><a target=\"_blank\" href=\"/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=" + valor + "\"> Ver " + nombre + " </a></div>";

    var ver = $("." + id + "_ver");

    ver.append(link);

}

function remover_link_sangria(e, element) {
    var valor = e.val;
    var id = $(element).attr("name");

    $("div > ." + id + "_" + valor).remove();

}

function generar_link_lote() {
    var div = ($(this).prop("name"));

    var elemento = $("." + div + " .ver");

    var id = ($(this).val());

    $("." + div + " .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/Produccion/Lote?accion=ver&id_lote=" + id + "\"> Ver Lote de Producci&#243;n </a>");
}

function generar_link_cc() {
    var div = ($(this).prop("name"));
    var elemento = $("." + div + " .ver");
    var id = ($(this).val());

    $("." + div + " .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=" + id + "\"> Ver Solicitud de CC </a>");
}

function generar_link_aa() {
    var div = ($(this).prop("name"));

    var elemento = $("." + div + " .ver");

    var id = ($(this).val());

    $("." + div + " .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/Produccion/Actividad_Apoyo?accion=verrespuesta&id_respuesta=" + id + "\"> Ver Actividad de Apoyo </a>");
}