<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="paso | actividad">
        <div class="widget-content row">
            <div class="widget widget-table col-sm-6">
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
                                <th>Valor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[not(tipo = 'seleccion') and not(tipo = ''aa'') and not(tipo = 'usuario') and not(tipo = 'subbodega') and not(tipo = 'cc') and not(tipo = 'sangria')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- Campos de selecciones -->
            <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="'Grupos de Usuarios'" /> 
                    </h3>
                </div>
                <div class="widget-content">
                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                        <thead>
                            <tr>
                                <th>Nombre de Campo</th>
                                <th>Sección</th>
                                <th>Usuarios</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[(tipo = 'usuario')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
            
        <div class="widget-content row">
            <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="'Artículos de Sub Bodegas'" /> 
                    </h3>
                </div>
                <div class="widget-content">
                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                        <thead>
                            <tr>
                                <th>Nombre de Campo</th>
                                <th>Sub Bodega</th>
                                <th>Con cantidades</th>
                                <th>Producto Interno</th>
                                <th>Cantidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[(tipo = 'subbodega')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="'Referencia a Control de Calidad'" /> 
                    </h3>
                </div>
                <div class="widget-content">
                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                        <thead>
                            <tr>
                                <th>Nombre de Campo</th>
                                <th>Referencia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[(tipo = 'cc')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="widget-content row">
            <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="'Referencia a Sangrías'" /> 
                    </h3>
                </div>
                <div class="widget-content">
                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                        <thead>
                            <tr>
                                <th>Nombre de Campo</th>
                                <th>Referencia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[(tipo = 'sangria')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <xsl:apply-templates select="campo[tipo = 'aa']"/>
        </div>
        <div class="widget-content row">
            <xsl:apply-templates select="campo[tipo = 'seleccion']"/>
        </div>

    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = 'seleccion') and not(tipo = ''aa'') and not(tipo = 'usuario') and not(tipo = 'subbodega') and not(tipo = 'cc') and not(tipo = 'sangria')]">
        
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:call-template name="tipo-dato">
                    <xsl:with-param name="tipo" select="tipo" />
                </xsl:call-template>
            </td>
            <td>
                <xsl:value-of select="valor"/>
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
        <div class="widget widget-table col-sm-6">
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
                            <th>Valor</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="opciones/opcion">
                            <xsl:param name="check" select="'check'"/>
                            <tr>
                                <td>
                                    <xsl:value-of select="etiqueta" />
                                </td>
                                <td>
                                    <xsl:call-template name="resultado-opcion">
                                        <xsl:with-param name="check" select="check" />
                                    </xsl:call-template>
                                </td>
                            </tr>

                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template name="resultado-opcion">
        <xsl:param name="check" />
        <xsl:choose>
            <xsl:when test="$check = 'true'">
                Sí
            </xsl:when>
            <xsl:otherwise>
                No
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="campo[tipo = 'aa']">
        <div class="widget widget-table col-sm-6">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="'Referencia a Actividades de Apoyo'" /> 
                </h3>
            </div>
            <div class="widget-content">
                <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                    <thead>
                        <tr>
                            <th>Nombre de Campo</th>
                            <th>Actividad de Apoyo</th>
                            <th>Referencia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:param name="valor" select="'valor'"/>
                        <!-- Campos diferentes de tablas -->
                        <td>
                            <xsl:value-of select="etiqueta" />
                        </td>
                        <td>
                            <xsl:value-of select="nombre-actividad" />
                        </td>
                        <td>
                            <a target="_blank" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verrespuesta&amp;id_respuesta={valor}"> Ver Actividad de Apoyo </a>
                        </td>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template match="campo[(tipo = 'subbodega')]">
        <xsl:param name="cantidad" select="'cantidad'"/>
        <xsl:param name="valor" select="'valor'"/>
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:value-of select="nombre-subbodega" />
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="cantidad = 'true'">
                        <xsl:value-of select="'Si'" />
                    </xsl:when>
                    <xsl:when test="cantidad = 'false'">
                        <xsl:value-of select="'No'" />
                    </xsl:when>
                </xsl:choose> 
            </td>
            <td>
                <a target="_blank" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=ver&amp;id_producto={valor}"> Ver Producto </a>
            </td>
            <td>
                <xsl:value-of select="valor-cantidad" />
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template match="campo[(tipo = 'usuario')]">
        
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <xsl:value-of select="nombre-seccion" />
            </td>
            <td>
                <xsl:value-of select="valor" />
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template match="campo[(tipo = 'cc')]">
        <xsl:param name="valor" select="'valor'"/>
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <a target="_blank" href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&amp;id_solicitud={valor}"> Ver Solicitud </a>
            </td>
        </tr>
        
    </xsl:template>
    
    <xsl:template match="campo[(tipo = 'sangria')]">
        <xsl:param name="cantidad" select="'cantidad'"/>
        <xsl:param name="valor" select="'valor'"/>
        <tr>
            <td>
                <xsl:value-of select="etiqueta" />
            </td>
            <td>
                <a target="_blank" href="/SIGIPRO/Caballeriza/Sangria?accion=ver&amp;id_sangria={valor}"> Ver Sangría </a>
            </td>
        </tr>
        
    </xsl:template>
</xsl:stylesheet>