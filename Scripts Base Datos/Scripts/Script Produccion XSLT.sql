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
    
    <xsl:template match="campo[tipo = ''checkbox'']">
        <xsl:param name="nombre" select="nombre" />
        <div class="col-md-6">
            <label for="{$nombre}" class="control-label">
                <xsl:value-of select="$nombre" />
            </label>
        
            <xsl:for-each select="opciones/opcion">
                <xsl:param name="etiqueta" select="etiqueta" />
                <xsl:param name="valor" select="valor" />
                <xsl:param name="check" select="check" />
                
                <input type="checkbox" name="{$nombre}" value="{$valor}"> 
                    <xsl:value-of select="$etiqueta"></xsl:value-of>
                </input>
            
                
            </xsl:for-each>
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
                        <xsl:apply-templates select="campo[not(tipo = ''seleccion'')]"/>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Campos de selecciones -->
        <xsl:apply-templates select="campo[tipo = ''seleccion'']"/>
        
    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = ''seleccion'')]">
        
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
                <tr><td>
                    <xsl:value-of select="etiqueta" />
                </td></tr>
            </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    
</xsl:stylesheet>
                ')
);