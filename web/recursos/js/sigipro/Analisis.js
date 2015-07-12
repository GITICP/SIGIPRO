contador = 1;
columnas = 1;
filas = 1;
function agregarCampo() {
    fila = "<div class=\"widget widget-table\" id=\"" + contador + "\">";
    fila += "   <div class=\"widget-header\">";
    fila += "       <h3><i class=\"fa fa-edit\"></i> Campo #" + contador + "</h3>";
    fila += "       <div class=\"btn-group widget-header-toolbar\">";
    fila += "       </div>";
    fila += "   </div>";
    fila += "   <div class=\"widget-content\">";
    fila += "       <div class=\"col-md-6\">";
    fila += "           <label for=\"tipo\" class=\"control-label\"> *Tipo de Campo</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <select id=\"seleccionTipoCampo\" class=\"select2\" name=\"c_tipocampo_" + contador + "\"";
    fila += "                           style=\'background-color: #fff;\' required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           onchange=\"setCustomValidity(\'\')\">";
    fila += "                           <option value=\'\'></option>";
    fila += "                           <option value=\"number\">Número</option>";
    fila += "                           <option value=\"text\">Campo de Texto</option>";
    fila += "                       </select>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "           <label for=\"nombre\" class=\"control-label\">*Nombre del Campo</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre\" class=\"form-control\" name=\"c_nombre_" + contador + "\"";
    fila += "                           required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           oninput=\"setCustomValidity(\'\')\" > ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "       <div class=\"col-md-6\">";
    fila += "           <div class=\"col-sm-6\">";
    fila += "               <br>";
    fila += "               <div class=\"form-group\">";
    fila += "                   <div class=\"col-sm-12\">";
    fila += "                       <div class=\"input-group\">";
    fila += "                           <input id=\"manual_" + contador + "\" onchange=\"checkManual(this," + contador + ")\" type=\"checkbox\" name=\"c_manual_" + contador + "\" style=\"width:20px; height:20px;\"><span>  Automático</span>";
    fila += "                       </div>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "               <br>";
    fila += "               <div class=\"form-group\">";
    fila += "                   <div class=\"col-sm-12\">";
    fila += "                       <div class=\"input-group\">";
    fila += "                           <input id=\"visible_" + contador + "\" type=\"checkbox\" name=\"c_campovisible_" + contador + "\" style=\"width:20px; height:20px;\"><span>  Visible para Usuarios</span>";
    fila += "                       </div>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "           <div class=\"col-sm-6\">";
    fila += "               <br>";
    fila += "               <div class=\"form-group\">";
    fila += "                   <div class=\"col-sm-12\">";
    fila += "                       <div class=\"input-group\">";
    fila += "                           <input type=\"text\" maxlength=\"45\" placeholder=\"Celda\" class=\"form-control\" id=\"celda_" + contador + "\" name=\"c_celda_" + contador + "\" style=\"visibility:hidden\"";
    fila += "                               oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                               oninput=\"setCustomValidity(\'\')\" > ";
    fila += "                       </div>";
    fila += "                    </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "   </div>";
    fila += "</div>";
    var orden = $("#orden").val();
    if (orden === '') {
        $("#orden").val(contador);
    } else {
        $("#orden").val(orden + ',' + contador);
    }
    var orden = $("#orden").val();
    $(".campos").append(fila);
    contador++;
}

function checkManual(check, id) {
    if (check.checked) {
        $("#celda_" + id).css("visibility", 'visible');
    } else {
        $("#celda_" + id).css("visibility", 'hidden');
    }
}

function checkNombres(check, id) {
    if (check.checked) {
        $("#nombresfilas_"+id).css("visibility", 'visible');
    } else {
        $("#nombresfilas_"+id).css("visibility", 'hidden');
    }
}

$(function () {
    $("#sortable").sortable({
        placeholder: "ui-state-highlight",
        update: function () {
            var ordenElementos = $(this).sortable("toArray").toString();
            $("#orden").val(ordenElementos);
        }
    });
    $("#sortable").disableSelection();
});
function agregarColumna(id) {
    fila = "<div class='columna col-md-12'>";
    fila += "   <div class='col-md-6'>";
    fila += "       <label for=\"nombre\" class=\"control-label\">*Nombre de Columna</label>";
    fila += "       <div class=\"form-group\">";
    fila += "           <div class=\"col-sm-12\">";
    fila += "               <div class=\"input-group\">";
    fila += "                   <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre\" class=\"form-control\" name=\"t_nombrecolumna_"+id+"_"+columnas+"\"";
    fila += "                       required";
    fila += "                       oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                       oninput=\"setCustomValidity('')\" > ";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "            </div>";
    fila += "       </div>";
    fila += "                                            <div class=\"col-md-6\">";
    fila += "                                                <label for=\"especie\" class=\"control-label\"> *Tipo del Campo</label>";
    fila += "                                                <div class=\"form-group\">";
    fila += "                                                    <div class=\"col-sm-12\">";
    fila += "                                                        <div class=\"input-group\">";
    fila += "                                                            <select id=\"seleccionTipoCampo\" class=\"select2\" name=\"t_tipocampocolumna_"+id+"_"+columnas+"\"";
    fila += "                                                                    style='background-color: #fff;' required";
    fila += "                                                                    oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                                                                    onchange=\"setCustomValidity('')\">";
    fila += "                                                                <option value=''></option>";
    fila += "                                                                <option value=\"number_tabla\">Número</option>";
    fila += "                                                                <option value=\"text_tabla\">Campo de Texto</option>";
    fila += "                                                            </select>";
    fila += "                                                        </div>";
    fila += "                                                    </div>";
    fila += "                                                </div>";
    fila += "                                            </div>";
    fila += "                                        </div>";
    columnas++;
    $(".columnas_"+id).append(fila);
}
;

function agregarFilaEspecial(id) {
    fila = "<div class=\'filaespecial col-md-12\'>";
    fila += "<div class=\'col-md-6\'>";
    fila += "    <label for=\"nombre\" class=\"control-label\">*Nombre de Fila Especial</label>";
    fila += "    <div class=\"form-group\">";
    fila += "        <div class=\"col-sm-12\">";
    fila += "            <div class=\"input-group\">";
    fila += "                <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre\" class=\"form-control\" name=\"t_nombrefilaespecial_"+id+"_"+filas+"\"";
    fila += "                       required";
    fila += "                       oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                       oninput=\"setCustomValidity(\'\')\" > ";
    fila += "            </div>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "<div class=\"col-md-6\">";
    fila += "    <label for=\"especie\" class=\"control-label\"> *Tipo del Campo</label>";
    fila += "    <div class=\"form-group\">";
    fila += "        <div class=\"col-sm-12\">";
    fila += "            <div class=\"input-group\">";
    fila += "                <select id=\"seleccionTipoCampo\" class=\"select2\" name=\"t_tipocampofilaespecial_"+id+"_"+filas+"\"";
    fila += "                        style=\'background-color: #fff;\' required";
    fila += "                        oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                        onchange=\"setCustomValidity(\'\')\">";
    fila += "                    <option value=\'\'></option>";
    fila += "                    <option value=\"promedio\">Promedio</option>";
    fila += "                    <option value=\"sumatoria\">Sumatoria</option>";
    fila += "                </select>";
    fila += "            </div>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";
    filas++;
    $(".filasespeciales_"+id).append(fila);
}
;

function agregarTabla() {
    fila = "<div class=\"widget widget-table\" id=\"" + contador + "\">";
    fila += "<div class=\"widget-header\">";
    fila += "    <h3><i class=\"fa fa-table\"></i> Tabla #" + contador + "</h3>";
    fila += "    <div class=\"btn-group widget-header-toolbar\">";
    fila += "        <input id=\"visible_" + contador + "\" type=\"checkbox\" name=\"t_tablavisible_" + contador + "\" style=\"width:20px; height:20px; alignment-baseline: central\"><span>  Visible para Usuarios</span>";
    fila += "    </div>";
    fila += "</div>";
    fila += "<div class=\"widget-content\">";
    fila += "    <div class=\"widget widget-table\" id=\""+contador+"\">";
    fila += "        <div class=\"widget-header\">";
    fila += "            <h3><i class=\"fa fa-columns\"></i> Columnas</h3>";
    fila += "            <div class=\"btn-group widget-header-toolbar\">";
    fila += "            </div>";
    fila += "        </div>";
    fila += "        <div class='widget-content'>";
    fila += "            <div class='columnas_"+contador+"'>";
    fila += "            </div>";
    fila += "            <div class='col-md-12 form-group'>";
    fila += "                <button type=\"button\" onclick=\"agregarColumna(" + contador + "); \" class=\"btn btn-primary\"><i class=\"fa fa-plus-circle\"></i> Agregar Columna</button>";
    fila += "                <br>";
    fila += "            </div>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "    <div class=\"widget widget-table\" id=\""+contador+"\">";
    fila += "        <div class=\"widget-header\">";
    fila += "            <h3><i class=\"fa fa-bars\"></i> Filas</h3>";
    fila += "            <div class=\"btn-group widget-header-toolbar\">";
    fila += "            </div>";
    fila += "        </div>";
    fila += "        <div class='widget-content'>";
    fila += "            <div class='filas col-md-12'>";
    fila += "                <div class='col-md-6'>";
    fila += "                    <label for=\"nombre\" class=\"control-label\">*Cantidad</label>";
    fila += "                    <div class=\"form-group\">";
    fila += "                        <div class=\"col-sm-12\">";
    fila += "                            <div class=\"input-group\">";
    fila += "                                <input type=\"number\" min='0' maxlength=\"45\" placeholder=\"Cantidad de Filas\" class=\"form-control\" name=\"t_cantidadfilas_" + contador + "\"";
    fila += "                                       required";
    fila += "                                       oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                                       oninput=\"setCustomValidity('')\" > ";
    fila += "                            </div>";
    fila += "                        </div>";
    fila += "                    </div>";
    fila += "                    <label for=\"nombre\" class=\"control-label\">*Nombre de la Columna</label>";
    fila += "                    <div class=\"form-group\">";
    fila += "                        <div class=\"col-sm-12\">";
    fila += "                            <div class=\"input-group\">";
    fila += "                                <input type=\"text\" maxlength=\"45\" placeholder=\"Nombre de la columna de las filas\" class=\"form-control\" name=\"t_nombrefilacolumna_" + contador + "\"";
    fila += "                                       required";
    fila += "                                       oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                                       oninput=\"setCustomValidity('')\" > ";
    fila += "                            </div>";
    fila += "                        </div>";
    fila += "                    </div>";
    fila += "                </div>";
    fila += "                <div class=\"col-md-6\">";
    fila += "                    <br>";
    fila += "                    <div class=\"form-group\">";
    fila += "                        <div class=\"col-sm-12\">";
    fila += "                            <div class=\"input-group\">";
    fila += "                                <input id=\"connombre_" + contador + "\" onchange=\"checkNombres(this," + contador + ")\" type=\"checkbox\" name=\"t_connombre_" + contador + "\" style=\"width:20px; height:20px; \"><span>  Con Nombre</span>";
    fila += "                            </div>";
    fila += "                        </div>";
    fila += "                    </div>";
    fila += "                    <br>";
    fila += "                    <div class=\"form-group\">";
    fila += "                        <div class=\"col-sm-12\">";
    fila += "                            <div class=\"input-group\">";
    fila += "                                <input type=\"text\" maxlength=\"500\" placeholder=\"Nombre, filas, separadas, por, comas\" class=\"form-control\" name=\"t_nombresfilas_" + contador + "\" id=\"nombresfilas_" + contador + "\" style=\"visibility:hidden\"";
    fila += "                                       oninvalid=\"setCustomValidity('Este campo es requerido')\"";
    fila += "                                       oninput=\"setCustomValidity('')\" > ";
    fila += "                            </div>";
    fila += "                        </div>";
    fila += "                    </div>";
    fila += "                </div>";
    fila += "            </div>";
    fila += "            <br>";
    fila += "            <div class=\"filasespeciales_"+contador+"\">";
    fila += "            </div>";
    fila += "        <div class='col-md-12 form-group'>";
    fila += "            <button type=\"button\" onclick=\"agregarFilaEspecial(" + contador + "); \" class=\"btn btn-primary\"><i class=\"fa fa-plus-circle\"></i> Agregar Fila Especial</button>";
    fila += "        </div>";
    fila += "        </div>";
    fila += "    </div>";
    fila += "</div>";
    fila += "</div>";

    var orden = $("#orden").val();
    if (orden === '') {
        $("#orden").val(contador);
    } else {
        $("#orden").val(orden + ',' + contador);
    }
    var orden = $("#orden").val();
    $(".campos").append(fila);
    contador++;
}

/*
 * 
 * Definición de código para la función de realizar análisis
 * 
 * 
 */

$(document).ready(function () {

    $(".fila-especial").each(function () {
        new FilaEspecial($(this));
    });
});

/*
 * 
 *      Definición de la clase de fila especial
 * 
 */

// Constructor y Atributos
var FilaEspecial = function (elemento_fila) {

    this.elemento = elemento_fila;
    this.celdas = new Array();
    this.obtener_celdas();
    this.funcion = elemento_fila.data("funcion");
    this.asignar_funciones();
    
};

// Métodos
FilaEspecial.prototype.obtener_celdas = function () {

    var fila_especial = this;

    this.elemento.find("td").each(function (i) {
        fila_especial.celdas.push(new CeldaEspecial($(this)));
    });

};

FilaEspecial.prototype.asignar_funciones = function() {
    
    var fila_especial = this;
    
    $.each(this.celdas, function(){
        this.asignar_evento_objeto(fila_especial.funcion);
    });
  
};

/*
 * 
 *      Definición de la clase de celda especial
 * 
 */

// Constructor y Atributos
var CeldaEspecial = function (elemento_celda) {

    this.fila = elemento_celda.parents("tr");
    this.elemento = elemento_celda;
    this.campos_superiores = new Array();
    this.indice = this.fila.find("td").index(elemento_celda);
    this.input = elemento_celda.find("input");
    this.input.attr("readonly", true);
    this.obtener_celdas_superiores();
    this.asignar_evento_campos();

};

CeldaEspecial.prototype.obtener_celdas_superiores = function () {
    
    var celda_especial = this;
    this.fila.prevAll(":not(.fila-especial)").find("td:eq(" + this.indice + ")").each(function () {
        celda_especial.campos_superiores.push($(this));
    });

};

CeldaEspecial.prototype.asignar_evento_campos = function() {
    
    var celda_especial = this;
    $.each(this.campos_superiores, function() {
        this.find("input").change(function(){
            celda_especial.actualizar();
        });
    });
    
};

CeldaEspecial.prototype.asignar_evento_objeto = function(funcion) {
    if (funcion === "sumatoria") {
        this.funcion = CeldaEspecial.prototype.sumatoria;
    } else if (funcion === "promedio") {
        this.funcion = CeldaEspecial.prototype.promedio;
    }
};

CeldaEspecial.prototype.actualizar = function() {
    this.funcion();
};

CeldaEspecial.prototype.sumatoria = function() {
    var sumatoria = this.sumatoria_elementos();
    this.elemento.find("input").val(sumatoria);
};

CeldaEspecial.prototype.promedio = function() {
    var sumatoria = this.sumatoria_elementos();
    this.elemento.find("input").val(sumatoria / this.campos_superiores.length);
};

CeldaEspecial.prototype.sumatoria_elementos = function() {
    var sumatoria = 0;
    $.each(this.campos_superiores, function() {
        var valor_campo = this.find("input").val();
        var esNaN = isNaN(valor_campo);
        if (!esNaN && valor_campo !== "") {
            sumatoria += parseFloat(valor_campo);
        } else {
            sumatoria += 0;
        }
    });
    return sumatoria;
};
