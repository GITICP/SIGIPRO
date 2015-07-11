<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="analisis">
        
        <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
            <thead>
                <tr>
                    <th>Nombre de Campo</th>
                    <th>Tipo</th>
                    <th>Visible para Usuarios</th>
                </tr>
            </thead>
            <tbody>
                <!-- Campos diferentes de tablas -->
                <xsl:apply-templates select="campo[not(tipo = 'table')]"/>
            </tbody>
        </table>
            
        <br />
        <!-- Campos de tablas -->
        <xsl:apply-templates select="campo[tipo = 'table']"/>
        
    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = 'table')]">
        
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:call-template name="tipo-dato">
                    <xsl:with-param name="tipo" select="tipo" />
                    <xsl:with-param name="celda" select="celda" />
                </xsl:call-template>
            </td>
            <td>
                <xsl:call-template name="visible">
                    <xsl:with-param name="visible" select="visible" />
                </xsl:call-template>
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template name="tipo-dato">
        <xsl:param name="tipo" />
        <xsl:param name="celda" />
        
        <xsl:choose>
            <xsl:when test="$tipo = 'number' or $tipo = 'number_tabla'">
                <xsl:value-of select="'Número'" />
            </xsl:when>
            <xsl:when test="$tipo = 'text' or $tipo = 'text_tabla'">
                <xsl:value-of select="'Texto'" />
            </xsl:when>
            <xsl:when test="$tipo = 'Excel'">
                <xsl:value-of select="concat('Excel (', $celda, ')' )" />
            </xsl:when>
            <xsl:when test="$tipo = 'textarea'">
                <xsl:value-of select="'Área de Texto'" />
            </xsl:when>
            <xsl:when test="$tipo = 'fecha'">
                <xsl:value-of select="'Fecha'" />
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="visible">
        <xsl:param name="visible" />
        
        <xsl:choose>
            <xsl:when test="$visible = 'True'">
                <xsl:value-of select="'Sí'" />
            </xsl:when>
            <xsl:when test="$visible = 'False'">
                <xsl:value-of select="'No'" />
            </xsl:when>
        </xsl:choose>
        
    </xsl:template>
    
    <xsl:template name="visible-tabla">
        <xsl:param name="visible" />
        
        <xsl:choose>
            <xsl:when test="$visible = 'True'">
                <xsl:value-of select="'Visible'" />
            </xsl:when>
            <xsl:when test="$visible = 'False'">
                <xsl:value-of select="'No visible'" />
            </xsl:when>
        </xsl:choose>
        
    </xsl:template>
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = 'table']">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="nombre" /> 
                </h3>
                <div class="btn-group widget-header-toolbar">
                    <h3> 
                        <xsl:call-template name="visible-tabla">
                            <xsl:with-param name="visible" select="visible" />
                        </xsl:call-template>
                    </h3>
                </div>
            </div>
            
            <div class="widget-content">
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
            </div>
        </div>
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
                        <xsl:with-param name="nombre" select="celda-nombre/nombre" />
                        <xsl:with-param name="nombre-campo" select="campo/nombre-campo" />
                        <xsl:with-param name="tipo" select="campo/tipo" />
                    </xsl:call-template>
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>
    
    <xsl:template name="campo-tabla" >
        <xsl:param name="nombre" select="'valor_defecto'"/>
        <xsl:param name="nombre-campo" />
        <xsl:param name="tipo" />
        
        <!--
            Solamente uno va a tener un valor en un momento determinado. 
            Nunca ambos van a tener un valor
        -->
        
        <!-- El nombre y el nombre-campo + tipo nunca van a tener valores al mismo tiempo -->
        <xsl:value-of select="$nombre" />
        
        <xsl:choose>
            <xsl:when test="contains($nombre-campo, 'Promedio')">
                <xsl:value-of select="'Promedio'" />
            </xsl:when>
            <xsl:when test="contains($nombre-campo, 'Sumatoria')">
                <xsl:value-of select="'Sumatoria'" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="tipo-dato">
                    <xsl:with-param name="tipo" select="$tipo" />
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        
    </xsl:template> 
       
</xsl:stylesheet>