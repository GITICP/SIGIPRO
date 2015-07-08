contador = 0;

function agregarCampo() {
    alert(contador);
    contador++;
}

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
