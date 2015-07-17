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
    <xsl:template match="campo[tipo = 'number']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />

        <!-- Plantilla -->
        <div class="col-md-12">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="'number'" />
                            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
                            <xsl:with-param name="valor" select="$valor" />
                        </xsl:call-template>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    
    <!-- Campo de tipo texto -->
    <xsl:template match="campo[tipo = 'text']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-12">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="'text'" />
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
    <xsl:template match="campo[tipo = 'textarea']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-12">
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
    <xsl:template match="campo[tipo = 'fecha']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <div class="col-md-12">
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
    
    <!-- Campo de Excel -->
    <xsl:template match="campo[tipo = 'Excel']">
        <xsl:param name="celda" select="celda" />
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="valor" select="valor" />
        <input type="hidden" value="{$celda}" name="{$nombre-campo}"></input>
    </xsl:template>
    
    <!-- 
        Inputs en tablas
    -->
    
    <!-- Plantilla general de una tabla -->
    <xsl:template match="campo[tipo = 'table']">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="nombre" /> 
                </h3>
            </div>
            <div class="widget-content">
            
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <!-- Aplicación de plantillas para las columnas de la tabla -->
                            <xsl:apply-templates select="columnas" />
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Aplicación de plantillas para las filas de la tabla -->
                        <xsl:apply-templates select="filas" />
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <!-- Campo de tipo texto_tabla -->
    <xsl:template match="campo[tipo = 'text_tabla']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <xsl:call-template name="input">
            <xsl:with-param name="tipo" select="'text'" />
            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
            <xsl:with-param name="valor" select="$valor" />
        </xsl:call-template>
        
    </xsl:template>
    
    <!-- Campo de tipo numero_tabla -->
    <xsl:template match="campo[tipo = 'number_tabla']">
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <!-- Plantilla -->
        <xsl:call-template name="input">
            <xsl:with-param name="tipo" select="'number'" />
            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
            <xsl:with-param name="valor" select="$valor" />
        </xsl:call-template>
        
    </xsl:template>
    
    <!-- Plantilla para las columnas de la tabla -->
    <xsl:template match="columna">
        <xsl:if test="not(@tipo = 'excel_tabla')">
            <th>
                <xsl:value-of select="nombre" />
            </th>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="fila[@tipo = 'especial']">
        <xsl:param name="funcion" select="current()/@funcion" />
        <tr class="fila-especial" data-funcion="{$funcion}">
            <xsl:for-each select="celdas/celda">
                <td class="especial-fila">
                    <xsl:apply-templates />
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>
    
    <!-- Plantilla para las filas de la tabla -->
    <xsl:template match="fila">
        <tr>
            <xsl:for-each select="celdas/celda">
                <xsl:if test="not(campo/tipo = 'excel_tabla')">
                    <td>
                        <xsl:apply-templates />
                    </td>                
                </xsl:if>
            </xsl:for-each>
        </tr>
    </xsl:template>
    
    <!-- Plantilla para las celdas de nombre -->
    <xsl:template match="celda-nombre">
        <xsl:value-of select="nombre" />
    </xsl:template>
    
</xsl:stylesheet>
