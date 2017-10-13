/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addDays(date, days) {
    date.setTime( date.getTime() + days * 86400000 );
    var yyyy = date.getFullYear();
    var mm = date.getMonth()+1;
    if(mm<10){
        mm='0'+mm
    } 
    var dd = date.getDate();
    var newdate = dd+'/'+mm+'/'+yyyy;
    return newdate;
}

var contadorEditar = 0; //Cuando se edita la cotización, se ejecuta la función siguiente 1 vez, lo cual afecta la selección de la orden de compra
$(function(){ /* DOM ready */ //
    $("#moneda").change(function () {
        var moneda = document.getElementById("moneda").value;
        var infofundevi = document.getElementById("Info_Fundevi");
        var tipo = document.getElementById("tipo").value;
        if ((moneda === "Colones" || moneda === "Dólares") && tipo === "FUNDEVI"){
            infofundevi.style.display = 'block';
            document.getElementById("proyecto").required = true;
            document.getElementById("plazo").required = true;
        }
        else{
            infofundevi.style.display = 'none';
            document.getElementById("proyecto").required = false;
            document.getElementById("plazo").required = false;
        }
    }).change();
    
    $("#tipo").change(function () {
        var moneda = document.getElementById("moneda").value;
        var infofundevi = document.getElementById("Info_Fundevi");
        var tipo = document.getElementById("tipo").value;
        if ((moneda === "Colones" || moneda === "Dólares") && tipo === "FUNDEVI"){
            infofundevi.style.display = 'block';
            document.getElementById("proyecto").required = true;
            document.getElementById("plazo").required = true;
        }
        else{
            infofundevi.style.display = 'none';
            document.getElementById("proyecto").required = false;
            document.getElementById("plazo").required = false;
        }
    }).change();
    
    $("#fecha").change(function () {
        var fecha = document.getElementById("fecha").value;
        var fecha_vencimiento = document.getElementById("fecha_vencimiento");
        var plazo = document.getElementById("plazo").value;
        var fv = new Date(fecha.split("/")[2],fecha.split("/")[1]-1,fecha.split("/")[0],0,0,0,0);
        fecha_vencimiento.value = addDays(fv,plazo);
    }).change();
    
    $("#contado").change(function () {
        
        var contado_o_credito = document.getElementById("contado")[document.getElementById("contado").selectedIndex].value;
        
        var plazo = document.getElementById("plazo");
        if (contado_o_credito == 2){ //Contado
            var opt = document.createElement('option');
            opt.value = 0;
            opt.innerHTML = "0";
            plazo.appendChild(opt);
            $('#plazo').val('0').change();
            plazo.value = 0;
            
            plazo.disabled = true;
            //plazo.readOnly = true;
            var fecha_vencimiento = document.getElementById("fecha_vencimiento");
            var fecha = document.getElementById("fecha").value;
            var fv = new Date(fecha.split("/")[2],fecha.split("/")[1]-1,fecha.split("/")[0],0,0,0,0);

            fecha_vencimiento.value = addDays(fv,plazo.value);
        }
        else{
            if (plazo.options[0].value == 0){
                plazo.remove(0);
            }
            if (plazo.options[plazo.options.length-1].value == 0){
                plazo.remove(plazo.options.length-1);
            }
            $('#plazo').val('30').change();
            plazo.value = "";
            plazo.disabled = false;
        }
    }).change();
    
    $("#plazo").change(function () {
        var fecha = document.getElementById("fecha").value;
        var fecha_vencimiento = document.getElementById("fecha_vencimiento");
        var plazo = document.getElementById("plazo").value;
        
        var fv = new Date(fecha.split("/")[2],fecha.split("/")[1]-1,fecha.split("/")[0],0,0,0,0);
        
        fecha_vencimiento.value = addDays(fv,plazo);
    }).change();
    
    $("#id_cliente").change(function () {
        var ac = document.getElementById("accion").value;
        
        if(ac === "Editar" && contadorEditar < 1){
            contadorEditar += 1;
        }
        else{
            //Quitar todas las opciones del select de ordenes
            var i;
            var select_orden = document.getElementById("id_orden");
            for(i=select_orden.options.length-1;i>=0;i--)
            {
                select_orden.remove(i);
            }

            //Agregar solo las opciones que contienen el data-cliente que corresponde a id_cliente[selectedindex].value
            var select_cliente = document.getElementById("id_cliente");
            var id_cliente = select_cliente[select_cliente.selectedIndex].value;

            var select_orden_completo = document.getElementById("id_orden_completo");
            var e;
            var opt2 = document.createElement('option');
            opt2.value = "";
            opt2.innerHTML = "";
            select_orden.appendChild(opt2);
            for (e = 0; e < select_orden_completo.length; e++) {
                if (select_orden_completo[e].getAttribute("data-cliente") === id_cliente){
                    var opt = document.createElement('option');
                    opt.value = select_orden_completo[e].value;
                    opt.setAttribute('data-cliente',select_orden_completo[e].getAttribute("data-cliente"));
                    opt.innerHTML = select_orden_completo[e].innerHTML;
                    select_orden.appendChild(opt);
                }
            }

            $("#id_orden").val(" ");
        }
    }).change();
    
});

///ESTA ES LA PARTE DE PRODUCTOS - CARGA CON AJAX DEPENDIENDO DEL SELECT2 DE ORDENES
$(function (ready) {
  $("#id_orden").change(function () {
    var select_orden = $("#id_orden");
    var opcion = select_orden.val();


    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val("");


    $('#datatable-column-filter-productos tbody tr').remove();

    //ajax call
    ajax_productos(opcion);

  });
});

function ajax_productos(id_orden){
    
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            xmlDoc = xhttp.responseXML;
            var producto1 = xmlDoc.getElementsByTagName("producto");
            var id;
            var nombre;
            //var lote;
            var cantidad;
            var fecha;
            var producto;
            //alert(producto1.length);
            for (var i = 0; i < producto1.length; i++) {   
                producto = producto1[i];
                id = producto.getElementsByTagName('id')[0].firstChild.nodeValue;
                nombre = producto.getElementsByTagName('nombre')[0].firstChild.nodeValue;
                cantidad = producto.getElementsByTagName('cantidad')[0].firstChild.nodeValue;
                try{fecha = producto.getElementsByTagName('fecha')[0].firstChild.nodeValue;}
                catch (exception){fecha=" ";}
                agregarProductoInicio(id, nombre, cantidad, fecha);
            }
        }
    };
    try{
    enviarPeticionXHTTP("productos_orden_compra?id="+id_orden);}
    catch(exception){alert("Algo salió mal con la llamada Ajax");}
}

function enviarPeticionXHTTP(path){
    var pathArray = window.location.pathname.split( '/' );
    if (pathArray[pathArray.length-1] === '/'){
        pathArray.pop();
    }
    if (pathArray.length === 3){
        xhttp.open("GET", path, true);
        xhttp.send();
    }
    if (pathArray.length > 3){
        var carpetasEnElPath = pathArray.length;
        var irAtras = "";
        while(!(carpetasEnElPath < 3)){
            irAtras += "../";
            carpetasEnElPath = carpetasEnElPath - 2;
        }
        xhttp.open("GET", irAtras + path, true);
        xhttp.send();
    }
}

function agregarProductoInicio(id, producto, cantidad, fecha) {

    fila = '<tr ' + ' id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + fecha + '</td>';
    fila += '<td></td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto('+id+')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + id + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + cantidad + "#c#" + fecha + "#c#" + "");

    $('#datatable-column-filter-productos > tbody:last').append(fila);
    $('#inputGroupSeleccionProducto').find('.select2 option[value='+ id + ']').prop('id','').text('');
    alert($('#listaProductos').val());

}

//ESTA ES LA PARTE DEL CRUD DE PRODUCTOS
$( document ).ready(function() {
  var tabla = document.getElementById("datatable-column-filter-productos");
  var campoOcultoRoles = $('#listaProductos');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue);
      try {
      if (tabla.rows[i].cells[2].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
        }
      else
      {
        campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + " ");
      }
      if (tabla.rows[i].cells[3].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[3].firstChild.nodeValue);
        }
      else
      {
        campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + " ");
      }
      }
      catch (exception) {}
      $("#seleccionProducto option[value='"+id+"']").remove();
  }
  
  
  //alert("el valor del campo oculto es: " + $('#listaProductos').val());
});

function validarProductosYSubmit(){
    alert($('#listaProductos').val());
    if ($('#listaProductos').val() === ""){
        $('[data-toggle="confirmar"]').tooltip({title: "Asegúrese de agregar productos", placement: "bottom"});   
        $('[data-toggle="confirmar"]').tooltip('show');   
    }
    else{
        $('#formularioProducto').submit();
    }
}

function eliminarProducto(idRol) {
  fila = $('#' + idRol);
  $('#seleccionProducto')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoRoles = $('#listaProductos');
  var tabla = document.getElementById("datatable-column-filter-productos");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue );
      try {
      if (tabla.rows[i].cells[2].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
        }
      else
      {
        campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + " ");
      }
      if (tabla.rows[i].cells[3].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[3].firstChild.nodeValue);
        }
      else
      {
        campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + " ");
      }
      }
      catch (exception) {}}
  //alert("listaProductos = "+campoOcultoRoles.val());
}

function editarProducto(idRol) {
  var x = document.getElementById(idRol);
  $('#idProductoEditar').val(idRol);
  $("#editarCantidad").val(x.children[1].innerHTML);
  $("#editarPosibleFechaDespacho").val(x.children[2].innerHTML);
  $("#editarLotes").val(x.children[3].innerHTML);
  $('#modalEditarProducto').modal('show');
  
}
/*
 * 
 * Funciones de Producto
 * 
 */

function confirmarEdicionProducto() {
  if (!$('#formEditarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarProducto')).click().remove();
    $('#formEditarProducto').find(':submit').click();
  }
  else {
    var id = $('#idProductoEditar').val();
    fila = $('#' + id);
    fila.children('td').eq(1).text($('#editarCantidad').val());
    fila.children('td').eq(2).text($('#editarPosibleFechaDespacho').val());
    fila.children('td').eq(3).text($('#editarLotes').val());
    $('#modalEditarProducto').modal('hide');

    //Aqui se cambia el campo oculto para que los nuevos valores se reflejen luego en la inserción del rol
    campoOcultoRoles = $('#listaProductos');
    var a = campoOcultoRoles.val().split("#r#"); //1#c#fecha#c#fecha, 2#c#fecha#c#fecha
    var nuevoValorCampoOculto = "";
    a.splice(0, 1);
    for (var i = 0; i < a.length; i++)
    {
      var cadarol = a[i].split("#c#");
      if (cadarol[0] === id)
      {
      }
      else
      {
        nuevoValorCampoOculto = nuevoValorCampoOculto + "#r#" + a[i];
      }

    }
    campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + $('#editarCantidad').val() + "#c#" + $('#editarPosibleFechaDespacho').val() + "#c#" + $('#editarLotes').val());
  }
  alert($('#listaProductos').val());
}

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarProducto() {
  
   if (!$('#formAgregarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarProducto')).click().remove();
    $('#formAgregarProducto').find(':submit').click();
  }
  else {
  
  
  //$('#inputGroupSeleccionProducto').find('#select2-chosen-1').text("");
  
  rolSeleccionado = $('#seleccionProducto :selected');
  inputFechaAct = $('#cantidad');
  inputFechaDesact = $('#posibleFechaEntrega');
  inputLotes = $('#lotes');
  var select = document.getElementById("seleccionProducto");
  var indice = select.selectedIndex;
  
    fechaAct = inputFechaAct.val();
    fechaDesact = inputFechaDesact.val();
    idRol = rolSeleccionado.val();
    lotes = inputLotes.val();
    textoRol = rolSeleccionado.text();

    rolSeleccionado.remove();
    $('#seleccionProducto').val('').change();
    $('#seleccionLote').val('').change();
    inputFechaAct.val("");
    inputFechaDesact.val("");
    inputLotes.val("");

    fila = '<tr ' + 'id=' + idRol + '>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>' + fechaAct + '</td>';
    fila += '<td>' + fechaDesact + '</td>';
    fila += '<td>' + lotes + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact + "#c#" + lotes );
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
    $('#inputGroupSeleccionProducto').find('.select2 option[value='+ idRol + ']').remove();
    $('#modalAgregarProducto').modal('hide');
    
   // $('body').removeClass('modal-open');
   // $('.modal-backdrop').remove();
   alert($('#listaProductos').val());
  }
}

$(function(){ /* DOM ready */
    $("#seleccionProducto").change(function () {

        var select = document.getElementById("seleccionProducto");
        var indice = select.selectedIndex;

        var cantidad = document.getElementById("cantidad");


    });
});


function confirmacionAgregar() {
  if (!$('#formularioProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formularioProducto')).click().remove();
    $('#formularioProducto').find(':submit').click();
  }
  else {
    $('#formAgregarProducto').submit();
    var cantidad = document.getElementById("cantidad");
    cantidad.placeholder = "";
  }
}

$(function (ready) {
  $("#seleccionLote").change(function () {
    var lotes = $("#lotes");
    var opcion = $("#seleccionLote").val();


    lotes.val(lotes.val() + opcion + ", ");

  });
});

$(function (ready) {
  $("#editarSeleccionLote").change(function () {
    var lotes = $("#editarLotes");
    var opcion = $("#editarSeleccionLote").val();


    lotes.val(lotes.val() + opcion + ", " );

  });
});