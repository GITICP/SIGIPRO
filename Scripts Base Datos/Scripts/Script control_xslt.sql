DROP SCHEMA IF EXISTS control_xslt CASCADE;
CREATE SCHEMA control_xslt;

CREATE TABLE control_xslt.control_xslt (
    id_control_xslt integer NOT NULL,
    nombre varchar(50) NOT NULL,
    estructura XML NOT NULL,
    CONSTRAINT control_xslt_pk PRIMARY KEY (id_control_xslt)
);

INSERT INTO control_xslt.control_xslt (id_control_xslt, nombre, estructura) 
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
                        <div class="col-md-12">
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
                        <div class="col-md-12">
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
                    <xsl:template match="campo[tipo = ''fecha'']">

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
                    <xsl:template match="campo[tipo = ''Excel'']">
                        <xsl:param name="celda" select="celda" />
                        <xsl:param name="nombre-campo" select="nombre-campo" />
                        <xsl:param name="valor" select="valor" />
                        <input type="hidden" value="{$celda}" name="{$nombre-campo}"></input>
                    </xsl:template>

                    <!-- 
                        Inputs en tablas
                    -->

                    <!-- Plantilla general de una tabla -->
                    <xsl:template match="campo[tipo = ''table'']">
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
                    <xsl:template match="campo[tipo = ''text_tabla'']">
                        <!-- Parámetros -->
                        <xsl:param name="nombre-campo" select="nombre-campo" />
                        <xsl:param name="etiqueta" select="etiqueta" />
                        <xsl:param name="valor" select="valor" />

                        <!-- Plantilla -->
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="''text''" />
                            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
                            <xsl:with-param name="valor" select="$valor" />
                        </xsl:call-template>

                    </xsl:template>

                    <!-- Campo de tipo numero_tabla -->
                    <xsl:template match="campo[tipo = ''number_tabla'']">
                        <!-- Parámetros -->
                        <xsl:param name="nombre-campo" select="nombre-campo" />
                        <xsl:param name="etiqueta" select="etiqueta" />
                        <xsl:param name="valor" select="valor" />

                        <!-- Plantilla -->
                        <xsl:call-template name="input">
                            <xsl:with-param name="tipo" select="''number''" />
                            <xsl:with-param name="nombre-campo" select="$nombre-campo" />
                            <xsl:with-param name="valor" select="$valor" />
                        </xsl:call-template>

                    </xsl:template>

                    <!-- Plantilla para las columnas de la tabla -->
                    <xsl:template match="columna">
                        <xsl:if test="not(@tipo = ''excel_tabla'')">
                            <th>
                                <xsl:value-of select="nombre" />
                            </th>
                        </xsl:if>
                    </xsl:template>

                    <xsl:template match="fila[@tipo = ''especial'']">
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
                                <xsl:if test="not(campo/tipo = ''excel_tabla'')">
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
                ')
);


INSERT INTO control_xslt.control_xslt (id_control_xslt, nombre, estructura) 
VALUES (2, 'Generador Ver Resultado Calidad Completo', 
                XML(
                '
                <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
                    <xsl:output method="html" indent="yes"/>

                    <xsl:template match="/">
                        <xsl:apply-templates />
                    </xsl:template>

                    <xsl:template match="analisis">

                        <table>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[tipo != ''table'']"/>
                        </table>

                        <br>
                            <!-- Campos de tablas -->
                        </br>
                        <xsl:apply-templates select="campo[tipo = ''table'']"/>

                    </xsl:template>

                    <!-- 
                        Comienzo de las plantillas de los diferentes tipos de elementos.
                    -->

                    <!-- Campo de tipos diferentes de tabla -->
                    <xsl:template match="campo[tipo != ''table'']">

                        <xsl:param name="etiqueta" select="etiqueta" />
                        <xsl:param name="valor" select="valor" />

                        <tr>
                            <td>
                                <strong>
                                    <xsl:value-of select="$etiqueta" />
                                </strong>
                            </td>
                            <td>
                                <xsl:value-of select="$valor" />
                            </td>
                        </tr>

                    </xsl:template>

                    <!-- Campo de tipos de tabla -->
                    <xsl:template match="campo[tipo = ''table'']">
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
                ')
);

INSERT INTO control_xslt.control_xslt (id_control_xslt, nombre, estructura) 
VALUES (3, 'Generador Ver Resultado Calidad Parcial', 
                XML(
                '
                <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
                    <xsl:output method="html" indent="yes"/>

                    <xsl:template match="/">
                        <xsl:apply-templates />
                    </xsl:template>

                    <xsl:template match="analisis">

                        <table>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[tipo != ''table'' and visible = ''True'']"/>
                        </table>

                        <br>
                            <!-- Campos de tablas -->
                        </br>
                        <xsl:apply-templates select="campo[tipo = ''table'' and visible = ''True'']"/>

                    </xsl:template>

                    <!-- 
                        Comienzo de las plantillas de los diferentes tipos de elementos.
                    -->

                    <!-- Campo de tipos diferentes de tabla -->
                    <xsl:template match="campo[tipo != ''table'']">

                        <xsl:param name="etiqueta" select="etiqueta" />
                        <xsl:param name="valor" select="valor" />

                        <tr>
                            <td>
                                <strong>
                                    <xsl:value-of select="$etiqueta" />
                                </strong>
                            </td>
                            <td>
                                <xsl:value-of select="$valor" />
                            </td>
                        </tr>

                    </xsl:template>

                    <!-- Campo de tipos de tabla -->
                    <xsl:template match="campo[tipo = ''table'']">
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
                ')
);

INSERT INTO control_xslt.control_xslt (id_control_xslt, nombre, estructura)
VALUES (4, 'Generador Ver Análisis',
                XML(
                '
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
                                    <th>Resultado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Campos diferentes de tablas -->
                                <xsl:apply-templates select="campo[not(tipo = ''table'')]"/>
                            </tbody>
                        </table>

                        <br />
                        <!-- Campos de tablas -->
                        <xsl:apply-templates select="campo[tipo = ''table'']"/>

                    </xsl:template>

                    <!-- 
                        Comienzo de las plantillas de los diferentes tipos de elementos.
                    -->

                    <!-- Campo de tipos diferentes de tabla -->
                    <xsl:template match="campo[not(tipo = ''table'')]">

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
                                <xsl:call-template name="resultado">
                                    <xsl:with-param name="resultado" select="resultado" />
                                </xsl:call-template>
                            </td>
                        </tr>

                    </xsl:template>

                    <xsl:template name="tipo-dato">
                        <xsl:param name="tipo" />
                        <xsl:param name="celda" />

                        <xsl:choose>
                            <xsl:when test="$tipo = ''number'' or $tipo = ''number_tabla''">
                                <xsl:value-of select="''Número''" />
                            </xsl:when>
                            <xsl:when test="$tipo = ''text'' or $tipo = ''text_tabla''">
                                <xsl:value-of select="''Texto''" />
                            </xsl:when>
                            <xsl:when test="$tipo = ''Excel'' or $tipo = ''excel_tabla''">
                                <xsl:value-of select="concat(''Excel ('', $celda, '')'' )" />
                            </xsl:when>
                            <xsl:when test="$tipo = ''textarea''">
                                <xsl:value-of select="''Área de Texto''" />
                            </xsl:when>
                            <xsl:when test="$tipo = ''fecha''">
                                <xsl:value-of select="''Fecha''" />
                            </xsl:when>
                        </xsl:choose>
                    </xsl:template>

                    <xsl:template name="resultado">
                        <xsl:param name="resultado" />

                        <xsl:choose>
                            <xsl:when test="$resultado = ''True''">
                                <xsl:value-of select="''Sí''" />
                            </xsl:when>
                            <xsl:when test="$resultado = ''False''">
                                <xsl:value-of select="''No''" />
                            </xsl:when>
                        </xsl:choose>

                    </xsl:template>

                    <xsl:template name="visible-tabla">
                        <xsl:param name="visible" />

                        <xsl:choose>
                            <xsl:when test="$visible = ''True''">
                                <xsl:value-of select="''Visible''" />
                            </xsl:when>
                            <xsl:when test="$visible = ''False''">
                                <xsl:value-of select="''No visible''" />
                            </xsl:when>
                        </xsl:choose>

                    </xsl:template>

                    <!-- Campo de tipos de tabla -->
                    <xsl:template match="campo[tipo = ''table'']">
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3>
                                    <i class="fa fa-table"></i> 
                                    <xsl:value-of select="nombre" /> 
                                </h3>
                                <!--
                                <div class="btn-group widget-header-toolbar">
                                    <h3> 
                                        <xsl:call-template name="visible-tabla">
                                            <xsl:with-param name="visible" select="visible" />
                                        </xsl:call-template>
                                    </h3>
                                </div>
                                -->
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
                                        <xsl:with-param name="celda" select="campo/celda" />
                                    </xsl:call-template>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:template>

                    <xsl:template name="campo-tabla" >
                        <xsl:param name="nombre" select="''valor_defecto''"/>
                        <xsl:param name="nombre-campo" />
                        <xsl:param name="tipo" />
                        <xsl:param name="celda" />

                        <!--
                            Solamente uno va a tener un valor en un momento determinado. 
                            Nunca ambos van a tener un valor
                        -->

                        <!-- El nombre y el nombre-campo + tipo nunca van a tener valores al mismo tiempo -->
                        <xsl:value-of select="$nombre" />

                        <xsl:choose>
                            <xsl:when test="contains($nombre-campo, ''Promedio'')">
                                <xsl:value-of select="''Promedio''" />
                            </xsl:when>
                            <xsl:when test="contains($nombre-campo, ''Sumatoria'')">
                                <xsl:value-of select="''Sumatoria''" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="tipo-dato">
                                    <xsl:with-param name="tipo" select="$tipo" />
                                    <xsl:with-param name="celda" select="$celda" />
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>

                    </xsl:template> 

                </xsl:stylesheet>
                ')
);