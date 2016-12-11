/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var contador = 1;
$(function(){ /* DOM ready */ //
    $("#id_cliente").change(function () {
        //Quitar todas las opciones del select de facturaes
        var i;
        var select_factura = document.getElementById("id_factura");
        for(i=select_factura.options.length-1;i>=0;i--)
        {
            select_factura.remove(i);
        }
        
        //Agregar solo las opciones que contienen el data-cliente que corresponde a id_cliente[selectedindex].value
        var select_cliente = document.getElementById("id_cliente");
        var id_cliente = select_cliente[select_cliente.selectedIndex].value;
        
        var select_factura_completo = document.getElementById("id_factura_completo");
        var e;
        var opt2 = document.createElement('option');
        opt2.value = "";
        opt2.innerHTML = "";
        select_factura.appendChild(opt2);
        for (e = 0; e < select_factura_completo.length; e++) {
            if (select_factura_completo[e].getAttribute("data-cliente") === id_cliente){
                var opt = document.createElement('option');
                opt.value = select_factura_completo[e].value;
                opt.setAttribute('data-cliente',select_factura_completo[e].getAttribute("data-cliente"));
                opt.innerHTML = select_factura_completo[e].innerHTML;
                select_factura.appendChild(opt);
            }
        }
        //buscar la factura que corresponde con el ID 
        if (document.getElementById("accion").value == "Editar"){
            var idfactura = document.getElementById("idfactura").value;
            for (e = 0; e < select_factura.length; e++) {
                if (select_factura[e].value == idfactura){
                    select_factura.selectedIndex = e;
                    break;
                }
            }
        }
        else{
            $("#id_factura").val(" ");
        }


    }).change();
    contador = document.getElementById("datatable-column-filter-productos").rows.length;
});

function eliminarProducto(id) {
  
  var tabla = document.getElementById("datatable-column-filter-productos");
    var filaCambiada;
    for (var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        if (fila.getAttribute('data-orden') == id){
            filaCambiada = fila;
            break;
        }
    }
    filaCambiada.remove();
    listaProductos = $('#listaProductos');
    listaProductos.val(""); //limpia ListaProductos
    
  for(var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        var idfila = fila.getAttribute('id');
        var productofila = fila.cells[0].firstChild.nodeValue; //tipo
        var cantidadfila = fila.cells[1].firstChild.nodeValue; // fecha
        var fechafila = fila.cells[2].firstChild.nodeValue; //observaciones
        
        listaProductos.val(listaProductos.val() + "#r#" + idfila + "#c#" + productofila + "#c#" + cantidadfila + "#c#" + fechafila);
    }
}

function agregarProducto(id, producto, cantidad, fecha) {

    fila = '<tr ' + 'data-orden=' + contador+ ' id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + fecha + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto('+contador+')"   style="margin-left:5px;margin-right:7px;">Modificar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + contador + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    //alert("Producto añadido a la lista: "+producto);

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + producto + "#c#" + cantidad + "#c#" + fecha);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
    contador ++;
}

function editarProducto(id) {
  //contador = número de fila, está en data-orden
  var tabla = document.getElementById("datatable-column-filter-productos");
    var filaCambiada;
    for (var i = 1; i<tabla.rows.length; i++){
          var fila = tabla.rows[i];
        if (fila.getAttribute('data-orden') == id){
              filaCambiada = fila;
            break;
        }
    }
  document.getElementById("idProductoEditar").value = id;
  for(var i=0; i < document.getElementById("editarCantidad").options.length; i++) //tipo
  {
    if(document.getElementById("editarCantidad").options[i].value === filaCambiada.cells[0].firstChild.nodeValue) {
      document.getElementById("editarCantidad").selectedIndex = i;
      break;
    }
  }
  document.getElementById("editarPosibleFechaDespacho").value = filaCambiada.cells[1].firstChild.nodeValue;
  document.getElementById("editarObservaciones").value = filaCambiada.cells[2].firstChild.nodeValue;
  $('#modalEditarProducto').modal('show');
}

function confirmarAgregar(){
  if (!$('#formAgregarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarProducto')).click().remove();
    $('#formAgregarProducto').find(':submit').click();
  }
  else{
    var id = 0; //id
    var producto = $('#agregarCantidad').val(); //tipo
    var cantidad = $('#agregarPosibleFechaDespacho').val();
    var fecha = $('#agregarObservaciones').val();
    $('#modalAgregarProducto').modal('hide');
    agregarProducto(id, producto, cantidad, fecha);
  }
}

function confirmarEdicionProducto() {
  if (!$('#formEditarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarProducto')).click().remove();
    $('#formEditarProducto').find(':submit').click();
  }
  else {
    var id = $('#idProductoEditar').val();
    //get fila que tenga data-orden = id
    var tabla = document.getElementById("datatable-column-filter-productos");
    var filaCambiada;
    for (var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        if (fila.getAttribute('data-orden') === id){
            filaCambiada = fila;
        }
    }
    filaCambiada.cells[0].firstChild.nodeValue = $('#editarCantidad').val(); //tipo
    filaCambiada.cells[1].firstChild.nodeValue = $('#editarPosibleFechaDespacho').val();
    filaCambiada.cells[2].firstChild.nodeValue = $('#editarObservaciones').val();
    $('#modalEditarProducto').modal('hide');
    
    /*
     * Actualizar ListaProductos = tabla
     */
    
    listaProductos = $('#listaProductos');
    listaProductos.val(""); //limpia ListaProductos
    
    for(var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        var idfila = fila.getAttribute('id');
        var productofila = fila.cells[0].firstChild.nodeValue; //tipo
        var cantidadfila = fila.cells[1].firstChild.nodeValue; // fecha
        var fechafila = fila.cells[2].firstChild.nodeValue; //observaciones
        
        listaProductos.val(listaProductos.val() + "#r#" + idfila + "#c#" + productofila + "#c#" + cantidadfila + "#c#" + fechafila);
    }
  }
}