$(function () {
    var sangrias = $(".sangria");
    
    $.each(sangrias, function(index, element){
        $(element).on("change",generar_link_sangria);
        $.ajax({
            url: "/SIGIPRO/Caballeriza/Sangria",
            type: "GET",
            data: {"accion": "sangriasajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_sangria(datos,element);
            },
            error: function(){
                alert("Error");
            }
        });
    });
    
    var cc = $(".cc");
    
    $.each(cc, function(index, element){
        $(element).on("change",generar_link_cc);
        $.ajax({
            url: "/SIGIPRO/ControlCalidad/Solicitud",
            type: "GET",
            data: {"accion": "solicitudesajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_cc(datos,element);
            },
            error: function(){
                alert("Error");
            }
        });
       
    });
    
    var usuarios = $("select[id^='usuario']");
    
    $.each(usuarios, function(index, element){
        var id = $(element).prop("id").split("_")[1];
        $.ajax({
            url: "/SIGIPRO/Produccion/Lote",
            type: "GET",
            data: {"accion": "usuariosajax",
                    "id_seccion" : id},
            dataType: "json",
            success: function (datos) {
                generar_select_usuarios(datos,element);
            },
            error: function(){
                alert("Error");
            }
        });
       
    });
    
    var actividades = $("select[id^='aa']");
    
    $.each(actividades, function(index, element){
        $(element).on("change",generar_link_aa);
        var id = $(element).prop("id").split("_")[1];
        $.ajax({
            url: "/SIGIPRO/Produccion/Actividad_Apoyo",
            type: "GET",
            data: {"accion": "actividadesajax",
                    "id_actividad" : id},
            dataType: "json",
            success: function (datos) {
                generar_select_actividades(datos,element);
            },
            error: function(){
                alert("Error");
            }
        });
       
    });
    
    var subbodegas = $("select[id^='subbodega']");
    
    $.each(subbodegas, function(index, element){
        var id = $(element).prop("id").split("_")[1];
        $.ajax({
            url: "/SIGIPRO/Bodegas/SubBodegas",
            type: "GET",
            data: {"accion": "subbodegasajax",
                    "id_subbodega" : id},
            dataType: "json",
            success: function (datos) {
                generar_select_subbodegas(datos,element);
            },
            error: function(){
                alert("Error");
            }
        });
       
    });
    
});

$(document).on("click", ".aprobar-Modal", function () {
    var id_lote = $(this).data('id');
    var id_respuesta_actual = $(this).data('respuesta'); 
    var posicion_actual = $(this).data('posicion')
    $('#class-aprobar-paso #id_lote').val(id_lote);
    $("#class-aprobar-paso #id_respuesta_actual").val(id_respuesta_actual);
    $("#class-aprobar-paso #posicion_actual").val(posicion_actual);
});

function generar_select_sangria(datos,element) {
    
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_sangria + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.identificador);

        $(element).append(opcion);
    }
    
    $(element).select2();
     
}

function generar_select_cc(datos,element) {
    
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_solicitud + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.numero_solicitud);

        $(element).append(opcion);
    }
    
    $(element).select2();
     
}

function generar_select_usuarios(datos,element) {
    
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_usuario + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre_completo);

        $(element).append(opcion);
    }
    
    $(element).select2();
     
}

function generar_select_actividades(datos,element) {
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_respuesta + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre + " ["+elemento.fecha+"]");

        $(element).append(opcion);
    }
    
    $(element).select2();
     
}

function generar_select_subbodegas(datos,element) {
    
    $(element).append("<option value=\"\"></option>");
    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_producto + "\">";
        var opcion = $(opcion_string);
        opcion.text(elemento.nombre);

        $(element).append(opcion);
    }
    
    $(element).select2();
     
}

function generar_link_sangria(){
    var div = ($(this).prop("name"));
    
    var elemento = $("."+div +" .ver");
    
    var id = ($(this).val());
    
    $("."+div +" .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria="+id+"\"> Ver Sangría </a>");
}

function generar_link_cc(){
    alert("hola");
    var div = ($(this).prop("name"));
    alert(div);
    var elemento = $("."+div +" .ver");
    alert(elemento.prop("class"));
    var id = ($(this).val());
    
    $("."+div +" .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud="+id+"\"> Ver Solicitud de CC </a>");
}

function generar_link_aa(){
    var div = ($(this).prop("name"));
    
    var elemento = $("."+div +" .ver");
    
    var id = ($(this).val());
    
    $("."+div +" .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/Produccion/Actividad_Apoyo?accion=verrespuesta&id_respuesta="+id+"\"> Ver Actividad de Apoyo </a>");
}