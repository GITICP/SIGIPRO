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
            }
        });
       
    });
    
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
    var div = ($(this).prop("name"));
    
    var elemento = $("."+div +" .ver");
    
    var id = ($(this).val());
    
    $("."+div +" .ver > a").remove();

    elemento.append("<a target=\"_blank\" href=\"/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud="+id+"\"> Ver Solicitud de CC </a>");
}