/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var tabla_cronograma;
var xhttp;
var xmlDoc;
var data1 = [];

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

function obtenerSemanas(){
    
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            xmlDoc = xhttp.responseXML;
            var semanas = xmlDoc.getElementsByTagName("semana");
            var semana;
            var id_semana;
            var id_cronograma;
            var fecha;
            var sangria;
            var plasma_proyectado;
            var plasma_real;
            var antivenenos_lote;
            var antivenenos_proyectada;
            var antivenenos_bruta;
            var antivenenos_neta;
            var entregas_cantidad;
            var entregas_destino;
            var entregas_lote;
            var observaciones;
            
            for (var i = 0; i < 53; i++) {
                semana = semanas[i];
                id_semana = semana.getElementsByTagName('id_semana')[0].firstChild.nodeValue;
                id_cronograma = semana.getElementsByTagName('id_cronograma')[0].firstChild.nodeValue; 
                fecha = semana.getElementsByTagName('fecha')[0].firstChild.nodeValue;
                sangria = semana.getElementsByTagName('sangria')[0].firstChild.nodeValue;
                plasma_proyectado = semana.getElementsByTagName('plasma_proyectado')[0].firstChild.nodeValue;
                plasma_real = semana.getElementsByTagName('plasma_real')[0].firstChild.nodeValue;
                antivenenos_lote = semana.getElementsByTagName('antivenenos_lote')[0].firstChild.nodeValue;
                antivenenos_proyectada = semana.getElementsByTagName('antivenenos_proyectada')[0].firstChild.nodeValue;
                antivenenos_bruta = semana.getElementsByTagName('antivenenos_bruta')[0].firstChild.nodeValue;
                antivenenos_neta = semana.getElementsByTagName('antivenenos_neta')[0].firstChild.nodeValue;
                entregas_cantidad = semana.getElementsByTagName('entregas_cantidad')[0].firstChild.nodeValue;
                entregas_destino = semana.getElementsByTagName('entregas_destino')[0].firstChild.nodeValue;
                entregas_lote = semana.getElementsByTagName('entregas_lote')[0].firstChild.nodeValue;
                observaciones = semana.getElementsByTagName('observaciones')[0].firstChild.nodeValue;
                
                if (sangria === "null") sangria = "";
                if (plasma_proyectado === "null") plasma_proyectado = "";
                if (plasma_real === "null") plasma_real = "";
                if (antivenenos_lote === "null") antivenenos_lote = "";
                if (antivenenos_proyectada === "null") antivenenos_proyectada = "";
                if (antivenenos_bruta === "null") antivenenos_bruta = "";
                if (antivenenos_neta === "null") antivenenos_neta = "";
                if (entregas_cantidad === "null") entregas_cantidad = "";
                if (entregas_destino === "null") entregas_destino = "";
                if (entregas_lote === "null") entregas_lote = "";
                if (observaciones === "null") observaciones = "";
                
                var fecha_p = fecha.split("-");
                var date = new Date(fecha_p[0], fecha_p[1], fecha_p[2]);
                if (date.getMonth()===0){ //diciembre
                    var fechaf = date.getFullYear() + " " + 12 + " " + date.getDate();
                }
                else{
                    var fechaf = date.getFullYear() + " " + date.getMonth() + " " + date.getDate();
                }
                var m = moment(fechaf,"YYYY MM DD","es");
                
                data1.push({id: id_semana, values: {"fecha":m.format('ll'),"sangria":sangria,"plasma_proyectado":plasma_proyectado,"plasma_real":plasma_real,"antivenenos_lote":antivenenos_lote
                             ,"antivenenos_proyectada":antivenenos_proyectada,"antivenenos_bruta":antivenenos_bruta,"antivenenos_neta":antivenenos_neta,"entregas_cantidad":entregas_cantidad,
                             "entregas_destino":entregas_destino,"entregas_lote":entregas_lote}});
            }
        }
        if (data1.length > 0){
            var metadata = [];
            metadata.push({ name: "fecha", label: "FECHA", datatype: "string", editable: false});
            metadata.push({ name: "sangria", label:"SANGRIA", datatype: "string", editable: true});
            metadata.push({ name: "plasma_proyectado", label:"PLASMA PROY.", datatype: "string", editable: true});
            metadata.push({ name: "plasma_real", label:"PLASMA REAL", datatype: "string", editable: true});
            metadata.push({ name: "antivenenos_lote", label:"ANTIVENENOS LOTE", datatype: "string", editable: true});
            metadata.push({ name: "antivenenos_proyectada", label:"ANTIVENENOS Px PROY.", datatype: "string", editable: true});
            metadata.push({ name: "antivenenos_bruta", label:"ANTIVENENOS Px BRUTA", datatype: "string", editable: true});
            metadata.push({ name: "antivenenos_neta", label:"ANTIVENENOS Px NETA", datatype: "string", editable: true});
            metadata.push({ name: "entregas_cantidad", label:"ENTREGAS PRODUCTO", datatype: "string", editable: true});
            metadata.push({ name: "entregas_destino", label:"ENTREGAS DESTINO", datatype: "string", editable: true});
            metadata.push({ name: "entregas_lote", label:"ENTREGAS LOTE", datatype: "string", editable: true});
            
            tabla_cronograma = new EditableGrid("Cronograma");
            tabla_cronograma.load({"metadata": metadata, "data": data1});
            tabla_cronograma.renderGrid("table-content", "testgrid");
        }
    };
    enviarPeticionXHTTP("semanas_cronograma?id_cronograma="+$("#id_cronograma").val());
}

window.onload = function() {
    obtenerSemanas();
};