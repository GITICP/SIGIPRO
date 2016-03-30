/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    var tabla = $('#tabla-notificaciones').dataTable(); 
    tabla.fnSort( [ [1,'desc'] ] );
} );