<%-- 
    Document   : modal
    Created on : Dec 14, 2014, 8:53:05 PM
    Author     : Boga
--%>

<%@tag description="Tag para las ventanas modal" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="idModal" required="true"%>
<%@attribute name="titulo"  required="true"%>
<%@attribute name="form"    required="true" fragment="true"%>

<div class="widget-content">
  <div class="modal fade" id="${idModal}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
          <h4 class="modal-title" id="myModalLabel">${titulo}</h4>
        </div>
        <div class="modal-body">

          <jsp:invoke fragment="form" />

        </div>
      </div>
    </div>
  </div>
</div>