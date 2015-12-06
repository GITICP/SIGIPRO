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
        <div class="col-md-6">
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
        <div class="col-md-6">
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
    <xsl:template match="campo[tipo = 'fecha']">
        
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
    
    <xsl:template match="campo[tipo = 'checkbox']">
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
