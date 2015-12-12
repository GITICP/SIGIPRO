DROP SCHEMA IF EXISTS produccion_xslt CASCADE;
CREATE SCHEMA produccion_xslt;

CREATE TABLE produccion_xslt.produccion_xslt (
    id_produccion_xslt integer NOT NULL,
    nombre varchar(50) NOT NULL,
    estructura XML NOT NULL,
    CONSTRAINT produccion_xslt_pk PRIMARY KEY (id_produccion_xslt)
);

INSERT INTO produccion_xslt.produccion_xslt (id_produccion_xslt, nombre, estructura) 
VALUES (1, 'Generador Formularios Calidad', 
                XML(
                '
                <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
            <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="campo">
        <xsl:apply-templates />
    </xsl:template>
    
    <!-- 
    
        Comienzo de las plantillas de los diferentes tipos de elementos.
    
    -->
    
    <!-- 
        Inputs estándar
    -->
        
    <!-- Campo de tipo número -->
    <xsl:template match="campo[tipo = ''number'']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />

        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="''number''" />
                            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
                            <xsl:with-param name="valor" select="$valor" />
                        </xsl:call-template>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    
    <!-- Campo de tipo texto -->
    <xsl:template match="campo[tipo = ''text'']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="''text''" />
                            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
                            <xsl:with-param name="valor" select="$valor" />
                        </xsl:call-template>
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
    <!-- Plantilla general de Input -->
    <xsl:template name="input">
        <!-- Parámetros -->
        <xsl:param name="tipo" />
        <xsl:param name="nombre-campo" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <input type="{$tipo}" name="{$nombre-campo}" class="form-control" value="{$valor}" step="any"></input>
        
    </xsl:template>
    
    <!-- Campo de área de texto -->
    <xsl:template match="campo[tipo = ''textarea'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" class="form-control" name="{$nombre-campo}" valor="{$valor}"></textarea>
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
    <!-- Campo de texto fecha -->
    <xsl:template match="campo[tipo = ''fecha'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" class="form-control sigiproDatePicker" name="{$nombre-campo}" data-date-format="dd/mm/yyyy" value="{$valor}"></input>
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
     <xsl:template match="campo[tipo = ''seleccion'']">
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
        <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
            <xsl:for-each select="opciones/opcion">
                <xsl:param name="etiqueta" select="etiqueta" />
                <xsl:param name="valor" select="valor" />
                <xsl:param name="check" select="check" />
                <div class="col-sm-12">
                <input type="checkbox" name="{$nombre-campo}" value="{$valor}"> 
                    <xsl:value-of select="$etiqueta"></xsl:value-of>
                </input>
                </div>
                
            </xsl:for-each>
                    </div>
                </div>
        </div>
        </div>
    </xsl:template>

<xsl:template match="campo[tipo = ''sangria'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group {$nombre-campo}">
                        <select id="sangria" class="select2" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>
                        <div class="ver">
                        </div> 
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
<xsl:template match="campo[tipo = ''usuario'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />

        <xsl:param name="seccion" select="seccion" />
        <xsl:param name="nombre-seccion" select="nombre-seccion" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group {$nombre-campo}">
                        <select id="usuario_{$seccion}" multiple="multiple" class="select2" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>          
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>

<xsl:template match="campo[tipo = ''cc'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group {$nombre-campo}">
                        <select id="cc" class="select2" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
<div class="ver">
                        </div>         
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
    <xsl:template match="campo[tipo = ''subbodega'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        <xsl:param name="subbodega" select="subbodega" />
        <xsl:param name="nombre-subbodega" select="nombre-subbodega" />
        <xsl:param name="cantidad" select="cantidad" />
        <xsl:param name="valor-cantidad" select="valor-cantidad" />
        <xsl:param name="nombre-cantidad" select="nombre-cantidad" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group {$nombre-campo}">
                        <select id="subbodega_{$subbodega}" class="select2" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
                        <xsl:if test="$cantidad = ''true''">
                            <br>
                                <label for="{$nombre-cantidad}" class="control-label">
                                    Cantidad
                                </label>
                                <input type="number" name="{$nombre-cantidad}" class="form-control" value="{$valor-cantidad}" step="any"></input>
                            </br>
                        </xsl:if>      
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
</xsl:stylesheet>
                ')
);

INSERT INTO produccion_xslt.produccion_xslt (id_produccion_xslt, nombre, estructura)
VALUES (4, 'Generador Ver Paso de Protocolo',
                XML(
                '
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="paso">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="''Campos''" /> 
                </h3>
            </div>
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Nombre de Campo</th>
                            <th>Tipo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:apply-templates select="campo[not(tipo = ''seleccion'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'')]"/>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Campos de selecciones -->
        <xsl:apply-templates select="campo[tipo = ''seleccion'']"/>
        
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="''Grupos de Usuarios''" /> 
                </h3>
            </div>
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Nombre de Campo</th>
                            <th>Sección</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:apply-templates select="campo[(tipo = ''usuario'')]"/>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="''Artículos de Sub Bodegas''" /> 
                </h3>
            </div>
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Nombre de Campo</th>
                            <th>Sub Bodega</th>
                            <th>Con cantidades</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:apply-templates select="campo[(tipo = ''subbodega'')]"/>
                    </tbody>
                </table>
            </div>
        </div>

        
    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = ''seleccion'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'')]">
        
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:call-template name="tipo-dato">
                    <xsl:with-param name="tipo" select="tipo" />
                </xsl:call-template>
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template name="tipo-dato">
        <xsl:param name="tipo" />
        
        <xsl:choose>
            <xsl:when test="$tipo = ''number''">
                <xsl:value-of select="''Número''" />
            </xsl:when>
            <xsl:when test="$tipo = ''text''">
                <xsl:value-of select="''Texto''" />
            </xsl:when>
            <xsl:when test="$tipo = ''textarea''">
                <xsl:value-of select="''Área de Texto''" />
            </xsl:when>
            <xsl:when test="$tipo = ''fecha''">
                <xsl:value-of select="''Fecha''" />
            </xsl:when>
            <xsl:when test="$tipo = ''cc''">
                <xsl:value-of select="''Referencia a Control de Calidad''" />
            </xsl:when>
            <xsl:when test="$tipo = ''sangria''">
                <xsl:value-of select="''Referencia a Sangría''" />
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = ''seleccion'']">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-check"></i> 
                    <xsl:value-of select=''etiqueta'' /> 
                </h3>
            </div>
            
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Opción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="opciones/opcion">
                            <tr>
                                <td>
                                    <xsl:value-of select="etiqueta" />
                                </td>
                            </tr>

                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template match="opciones">
        
        <xsl:for-each select="opciones/opcion">
            <xsl:param name="nombre" select="''etiqueta''"/>
            <tr>
                <td>
                    <xsl:value-of select="$nombre" />
                </td>
            </tr>

        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="campo[(tipo = ''subbodega'')]">
        <xsl:param name="cantidad" select="''cantidad''"/>
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:value-of select="nombre-subbodega" />
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="cantidad = ''true''">
                        <xsl:value-of select="''Si''" />
                    </xsl:when>
                    <xsl:when test="cantidad = ''false''">
                        <xsl:value-of select="''No''" />
                    </xsl:when>
                </xsl:choose> 
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template match="campo[(tipo = ''usuario'')]">
        
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:value-of select="nombre-seccion" />
            </td>
        </tr>
        
    </xsl:template>
</xsl:stylesheet>
                ')
);