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
                    <xsl:value-of select="'Campos'" /> 
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
                        <xsl:apply-templates select="campo[not(tipo = 'seleccion')]"/>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Campos de selecciones -->
        <xsl:apply-templates select="campo[tipo = 'seleccion']"/>
        
    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = 'seleccion')]">
        
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
            <xsl:when test="$tipo = 'number'">
                <xsl:value-of select="'Número'" />
            </xsl:when>
            <xsl:when test="$tipo = 'text'">
                <xsl:value-of select="'Texto'" />
            </xsl:when>
            <xsl:when test="$tipo = 'textarea'">
                <xsl:value-of select="'Área de Texto'" />
            </xsl:when>
            <xsl:when test="$tipo = 'fecha'">
                <xsl:value-of select="'Fecha'" />
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = 'seleccion']">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-check"></i> 
                    <xsl:value-of select='etiqueta' /> 
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
            <xsl:param name="nombre" select="'etiqueta'"/>
            <tr>
                <td>
                    <xsl:value-of select="$nombre" />
                </td>
            </tr>

        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>