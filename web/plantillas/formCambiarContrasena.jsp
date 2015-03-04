<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:modal idModal="modalCambiarContrasena" titulo="Cambiar contraseña">

  <jsp:attribute name="form">

    <form id='cambiarContrasena' action='/SIGIPRO/Cuenta/CambiarContrasena' method='post'>
      <div class='row'>
        <div class='form-group'>
          <div class='col-sm-1'></div>
          <input id='inputUsuario' type='hidden' placeholder='Contrase&ntilde;a' class='form-control' name='usuario' value="${sessionScope.usuario}">
          <div class='col-sm-10'>
            <label>Indique su nueva contrase&ntilde;a.</label>
            <div class='input-group' style='display:table'>
              <input id='contrasenna' type='password' placeholder='Contrase&ntilde;a' class='form-control' name='contrasenna' onkeyup='verificarConfirmacion();'>
              <span class='input-group-addon'><i class='fa fa-lock'></i></span>
            </div>
            <label>Confirme su contrase&ntilde;a.</label>
            <div class='input-group' style='display:table'>
              <input id='confirmacion' type='password' placeholder='Contrase&ntilde;a' class='form-control' name='confirmacion' onkeyup='verificarConfirmacion();'>
              <span class='input-group-addon'><i class='fa fa-lock'></i></span>
            </div>
            <p id='mensajePassword' style='color:red;'><p>
          </div>
          <div class='col-sm-1'></div>
        </div>
      </div>
    </form>

    <div class="form-group">
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
        <button id="btnConfirmarContrasena" type="button" class="btn btn-primary" onclick="confirmarContrasena()" disabled><i class="fa fa-check-circle"></i> Cambiar Contraseña </button>
      </div>
    </div>
  </form>

</jsp:attribute>

</t:modal>