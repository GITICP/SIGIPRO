<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:modal idModal="modalRegistrarExtraccion" titulo="Registrar Extraccion">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-registrar">
            <form class="form-horizontal" id="registrarExtraccion" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Registrar">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="observaciones" class="control-label">*Volumen Extra�do (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input name="volumen_extraido" id="volumen_extraido" type="number" min="0" step="any" placeholder="N�mero de mL extra�dos" class="form-control" value="" required
                             oninvalid="setCustomValidity('El volumen extra�do debe ser un n�mero mayor a 0. ')"
                            oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Extracci�n</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
<t:modal idModal="modalRegistrarCentrifugado" titulo="Registrar Volumen Recuperado">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-centrifugado">
            <form class="form-horizontal" id="registrarCentrifugado" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Centrifugado">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="observaciones" class="control-label">*Volumen Recuperado (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input name="volumen_recuperado" id="volumen_recuperado" type="number" step="any" min="0" placeholder="N�mero de mL recuperados" class="form-control" required
                            oninvalid="setCustomValidity('El volumen recuperado debe ser un n�mero mayor a 0. ')"
                            oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
            
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Centrifugado</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
<t:modal idModal="modalRegistrarLiofilizacionInicio" titulo="Registrar Inicio de Liofilizaci�n">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-inicio">
            <form class="form-horizontal" id="registrarLiofilizacionInicio" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Liofilizacioninicio">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="label" class="control-label">�Est� seguro que desea registrar el inicio de la Liofilizaci�n?</label>
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Iniciar Liofilizaci�n</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
      <t:modal idModal="modalRegistrarLiofilizacionFin" titulo="Registrar Fin de Liofilizaci�n">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-fin">
            <form class="form-horizontal" id="registrarLiofilizacionFin" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Liofilizacionfin">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="peso_recuperado" class="control-label">*Peso recuperado (G)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input name="peso_recuperado" id="peso_recuperado" type="number" step="any" min="0" placeholder="N�mero de G recuperados" class="form-control" value="" required
                             oninvalid="setCustomValidity('El peso recuperado debe ser un n�mero mayor a 0. ')"
                            oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Finalizar Liofilizaci�n</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>