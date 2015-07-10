<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="analisis">
        
        <table>
            <!-- Campos diferentes de tablas -->
            <xsl:apply-templates select="campo[tipo != 'table' and visible = 'True']"/>
        </table>
        
        <br>
            <!-- Campos de tablas --></br>
        <xsl:apply-templates select="campo[tipo = 'table' and visible = 'True']"/>
        
    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[tipo != 'table']">
        
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        
        <tr>
            <td>
                <strong><xsl:value-of select="$etiqueta" /></strong>
            </td>
            <td>
                <xsl:value-of select="$valor" />
            </td>
        </tr>
        
    </xsl:template>
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = 'table']">
        <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
            <thead>
                <tr>
                    <xsl:apply-templates select="columnas" />
                </tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="filas" />
            </tbody>
        </table>
    </xsl:template>
    
    <xsl:template match="columna">
        <th>
            <xsl:value-of select="nombre" />
        </th>
    </xsl:template>
    
    <xsl:template match="fila">
        <tr>
            <xsl:for-each select="celdas/celda">
                <td>
                    <xsl:call-template name="campo-tabla">
                        <xsl:with-param name="valor" select="campo/valor" />
                        <xsl:with-param name="nombre" select="celda-nombre/nombre" />
                    </xsl:call-template>
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>
    
    <xsl:template name="campo-tabla" >
        <xsl:param name="valor" />
        <xsl:param name="nombre" />
        
        <!-- 
            Solamente uno va a tener un valor en un momento determinado. 
            Nunca ambos van a tener un valor
        -->
        <xsl:value-of select="concat($valor, $nombre)" />
    </xsl:template>
       
</xsl:stylesheet>