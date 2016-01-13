/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var tabla_cronograma;

window.onload = function() {
    // this approach is interesting if you need to dynamically create data in Javascript 
    var metadata = [];
    metadata.push({ name: "fecha", label: "FECHA", datatype: "string", editable: true});
    metadata.push({ name: "sangria", label:"SANGRIA", datatype: "string", editable: true});
    metadata.push({ name: "plasma_proy", label:"PLASMA PROY.", datatype: "string", editable: true});
    metadata.push({ name: "plasma_real", label:"PLASMA REAL", datatype: "string", editable: true});
    metadata.push({ name: "antivenenos_lote", label:"ANTIVENENOS LOTE", datatype: "string", editable: true});
    metadata.push({ name: "antivenenos_proy", label:"ANTIVENENOS Px PROY.", datatype: "string", editable: true});
    metadata.push({ name: "antivenenos_bruta", label:"ANTIVENENOS Px BRUTA", datatype: "string", editable: true});
    metadata.push({ name: "antivenenos_neta", label:"ANTIVENENOS Px NETA", datatype: "string", editable: true});
    metadata.push({ name: "entregas_producto", label:"ENTREGAS PRODUCTO", datatype: "string", editable: true});
    metadata.push({ name: "entregas_destino", label:"ENTREGAS DESTINO", datatype: "string", editable: true});
    metadata.push({ name: "entregas_lote", label:"ENTREGAS LOTE", datatype: "string", editable: true});
    var data = [];
    data.push({id: 1, values: {"fecha":"Antoine","sangria":"Antoine","plasma_proy":"Antoine","plasma_real":"Antoine","antivenenos_lote":"Antoine"
                             ,"antivenenos_proy":"Antoine","antivenenos_bruta":"Antoine","antivenenos_neta":"Antoine","entregas_producto":"Antoine",
                             "entregas_destino":"Antoine","entregas_lote":"Antoine"}});
    data.push({id: 2, values: {"fecha":"Antoine","sangria":"Antoine","plasma_proy":"Antoine","plasma_real":"Antoine","antivenenos_lote":"Antoine"
                             ,"antivenenos_proy":"Antoine","antivenenos_bruta":"Antoine","antivenenos_neta":"Antoine","entregas_producto":"Antoine",
                             "entregas_destino":"Antoine","entregas_lote":"Antoine"}});
    
    tabla_cronograma = new EditableGrid("Cronograma");
    tabla_cronograma.load({"metadata": metadata, "data": data});
    tabla_cronograma.renderGrid("table-content", "testgrid");
};