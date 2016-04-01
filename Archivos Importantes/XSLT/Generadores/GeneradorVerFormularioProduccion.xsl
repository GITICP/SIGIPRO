<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="paso | actividad">
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
                        <xsl:apply-templates select="campo[not(tipo = ''seleccion'') and not(tipo = ''aa'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'')]"/>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Campos de selecciones -->
        <xsl:apply-templates select="campo[tipo = ''seleccion'']"/>
        
        <xsl:apply-templates select="campo[tipo = ''aa'']"/>
        
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
    <xsl:template match="campo[not(tipo = ''seleccion'') and not(tipo = ''aa'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'')]">
        
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
            <xsl:when test="$tipo = ''lote''">
                <xsl:value-of select="''Referencia a Lote de Producción''" />
            </xsl:when>
            <xsl:when test="$tipo = ''hora''">
                <xsl:value-of select="''Hora''" />
            </xsl:when>
            <xsl:when test="$tipo = ''imagen''">
                <xsl:value-of select="''Imagen''" />
            </xsl:when>
            <xsl:when test="$tipo = ''blanco''">
                <xsl:value-of select="''Espacio en Blanco''" />
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
    
    <xsl:template match="campo[tipo = ''aa'']">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="''Referencia a Actividad de Apoyo''" /> 
                </h3>
            </div>
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Nombre de Campo</th>
                            <th>Actividad de Apoyo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <tr>
                            <td>
                                <xsl:value-of select="etiqueta" />
                            </td>
                            <td>
                                <xsl:value-of select="nombre-actividad" />
                            </td>
                        </tr>
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