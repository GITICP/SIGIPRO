/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
  if ($.fn.dataTable.isDataTable('.sortable-desc')) {
    table = $('.sortable-desc').DataTable();
    table.order([0, "desc"]).draw();
  }
  else if ($.fn.dataTable.isDataTable('.sortable-desc1')) {
    table = $('.sortable-desc1').DataTable();
    table.order([1, "desc"]).draw();
  }
  else if ($.fn.dataTable.isDataTable('.sortable-desc2')) {
    table = $('.sortable-desc2').DataTable();
    table.order([2, "desc"]).draw();
  }
  else if ($.fn.dataTable.isDataTable('.sortable-desc3')) {
    table = $('.sortable-desc3').DataTable();
    table.order([3, "desc"]).draw();
  }

});

