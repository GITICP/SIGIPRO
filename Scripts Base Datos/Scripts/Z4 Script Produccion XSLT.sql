DROP SCHEMA IF EXISTS produccion_xslt CASCADE;
CREATE SCHEMA produccion_xslt;

CREATE TABLE produccion_xslt.produccion_xslt (
    id_produccion_xslt integer NOT NULL,
    nombre varchar(50) NOT NULL,
    estructura XML NOT NULL,
    CONSTRAINT produccion_xslt_pk PRIMARY KEY (id_produccion_xslt)
);

INSERT INTO produccion_xslt.produccion_xslt (id_produccion_xslt, nombre, estructura) 
VALUES (1, 'Generador Formularios Produccion', 
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
                        <textarea rows="5" cols="50" maxlength="500" class="form-control" name="{$nombre-campo}" valor="{$valor}">
                            <xsl:value-of select="$valor" />
                        </textarea>
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
    
    <!-- Campo de texto fecha -->
    <xsl:template match="campo[tipo = ''hora'']">
        
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
                        <input type="time" class="form-control" name="{$nombre-campo}" value="{$valor}"></input>
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
    <!-- Campo de texto fecha -->
    <xsl:template match="campo[tipo = ''blanco'']">
        
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
                        
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    
    <!-- Campo de texto fecha -->
    <xsl:template match="campo[tipo = ''imagen'']">
        
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
                        <input type="file" id="{$nombre-campo}" name="{$nombre-campo}" accept="image/*" 
                                   oninvalid="setCustomValidity(''El tamaño debe ser de 300KB o menos. '')" 
                               onchange="previewFile(''{$nombre-campo}'')">
                        </input> 
                        <button type="button" id=''{$nombre-campo}_eliminar'' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(''{$nombre-campo}'')"> Borrar</button>
                        <div>
                            <img id="{$nombre-campo}_preview" src="" height="100" alt=""></img>
                        </div>
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
                        <select id="sangria" multiple="multiple" class="select2 sangria" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
                        <div class="{$nombre-campo}_ver">
                        </div>      
                    </div>
                </div>
            </div>
        </div>
        
    </xsl:template>
    <xsl:template match="campo[tipo = ''lote'']">
        
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
                        <select id="lote" class="select2 lote" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
                        <div class="ver">
                            <a>Ver Lote de Producción</a>
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
    
    <xsl:template match="campo[tipo = ''aa'']">
        
        <!-- Parámetros -->
        <xsl:param name="nombre-campo" select="nombre-campo" />
        <xsl:param name="etiqueta" select="etiqueta" />
        <xsl:param name="valor" select="valor" />
        <xsl:param name="actividad" select="actividad" />
        <xsl:param name="nombre-actividad" select="nombre-actividad" />
        
        <!-- Plantilla -->
        <div class="col-md-6">
            <label for="{$nombre-campo}" class="control-label">
                <xsl:value-of select="$etiqueta" />
            </label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group {$nombre-campo}">
                        <select id="aa_{$actividad}" class="select2 aa" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>
                        <div class="ver">
                            <a>Ver Actividad de Apoyo</a>
                        </div>              
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
                        <select id="cc" class="select2 cc" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select> 
                        <div class="ver">
                            <a>Ver Solicitud</a>
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
                        <select id="subbodega_{$subbodega}" multiple="multiple" class="select2 subbodega" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
                        <xsl:if test="$cantidad = ''true''">
                            <br>
                                <div class="{$nombre-campo}_cant">
                                </div>    
                            </br>
                        </xsl:if>      
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
                <xsl:choose>
                    <xsl:when test="$check = ''true''">
                        <input type="checkbox" name="{$nombre-campo}" value="{$valor}" checked="checked">
                                            <xsl:value-of select="$etiqueta"></xsl:value-of>
                                        </input>
                                    </xsl:when>
                                    <xsl:when test="$check = ''false''">
                                        <input type="checkbox" name="{$nombre-campo}" value="{$valor}"> 
                                            <xsl:value-of select="$etiqueta"></xsl:value-of>
                                        </input>
                                    </xsl:when>
                                </xsl:choose>
                </div>
            
                
            </xsl:for-each>
                    </div>
                </div>
        </div>
        </div>
    </xsl:template>
    
</xsl:stylesheet>

                ')
);

INSERT INTO produccion_xslt.produccion_xslt (id_produccion_xslt, nombre, estructura) 
VALUES (2, 'Generador Ver Resultado Producción Completo', 
                XML(
                '
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="paso | actividad">
        <div class="widget-content row">
            <div class="widget widget-table col-sm-12">
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
                                <th>Valor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Campos diferentes de tablas -->
                            <xsl:apply-templates select="campo[not(tipo = ''seleccion'') and not(tipo = ''aa'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'') and not(tipo = ''cc'') and not(tipo = ''lote'') and not(tipo = ''sangria'') and not(tipo = ''imagen'')]"/>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="widget-content row">
            <xsl:apply-templates select="campo[tipo = ''sangria'']"/>
            <xsl:apply-templates select="campo[tipo = ''aa'']"/>
            <xsl:apply-templates select="campo[tipo = ''seleccion'']"/>
            <xsl:apply-templates select="campo[tipo = ''subbodega'']"/>
            <xsl:apply-templates select="campo[tipo = ''usuario'']"/>
            <xsl:apply-templates select="campo[tipo = ''imagen'']"/>
            <xsl:apply-templates select="campo[tipo = ''lote'']"/>
            <xsl:apply-templates select="campo[tipo = ''cc'']"/>
        </div>

    </xsl:template>
       
    <!-- 
        Comienzo de las plantillas de los diferentes tipos de elementos.
    -->
        
    <!-- Campo de tipos diferentes de tabla -->
    <xsl:template match="campo[not(tipo = ''seleccion'') and not(tipo = ''aa'') and not(tipo = ''usuario'') and not(tipo = ''subbodega'') and not(tipo = ''cc'') and not(tipo = ''lote'') and not(tipo = ''sangria'') and not(tipo = ''imagen'')]">
        
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
            <xsl:when test="$tipo = ''hora''">
                <xsl:value-of select="''Hora''" />
            </xsl:when>
            <xsl:when test="$tipo = ''blanco''">
                <xsl:value-of select="''Espacio en blanco''" />
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = ''seleccion'']">
        <div class="widget widget-table col-sm-6">
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
                            <th>Valor</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="opciones/opcion">
                            <xsl:param name="check" select="''check''"/>
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
            <xsl:when test="$check = ''true''">
                Sí
            </xsl:when>
            <xsl:otherwise>
                No
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <!-- Campo de tipos de tabla -->
    <xsl:template match="campo[tipo = ''subbodega'']">
        <div class="widget widget-table col-sm-6">
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
                            <th>Producto Interno</th>
                            <th>Cantidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:for-each select="valor/producto">
                            <xsl:param name="id" select="''id''"/>
                            <xsl:param name="nombre" select="''nombre''"/>
                            <xsl:param name="cantidad" select="''cantidad''"/>
                            <tr>
                                <td>
                                   <a target="_blank" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=ver&amp;id_producto={id}"><xsl:value-of select="nombre" /></a>
                                </td>
                                <td>
                                    <xsl:value-of select="cantidad" />
                                </td>
                            </tr>

                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template match="campo[tipo = ''cc'']">
        <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="''Referencia a Control de Calidad''" /> 
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
                            <xsl:param name="valor" select="''valor''"/>
                            <tr>
                                <td>
                                    <xsl:value-of select="etiqueta" />
                                </td>
                                <td>
                                    <a target="_blank" href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&amp;id_solicitud={valor}"> Ver Solicitud </a>
                                </td>
                            </tr>
        
                        </tbody>
                    </table>
                </div>
            </div>
    </xsl:template>
    
<xsl:template match="campo[tipo = ''lote'']">
    <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="''Referencia a Lotes de Producción''" /> 
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
                            <xsl:param name="valor" select="''valor''"/>
                            <tr>
                                <td>
                                    <xsl:value-of select="etiqueta" />
                                </td>
                                <td>
                                    <a target="_blank" href="/SIGIPRO/Produccion/Lote?accion=ver&amp;id_lote={valor}"> Ver Lote de Producción </a>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
            </div>
</xsl:template>
    <xsl:template match="campo[tipo = ''imagen'']">
    <div class="widget widget-table col-sm-6">
                <div class="widget-header">
                    <h3>
                        <i class="fa fa-table"></i> 
                        <xsl:value-of select="''Imágenes''" /> 
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
                            <xsl:param name="valor" select="''valor''"/>
                            <tr>
                                <td>
                                    <xsl:value-of select="etiqueta" />
                                </td>
                                <td>
                                    <a target="_blank" href="/SIGIPRO/Produccion/Lote?accion=imagen&amp;path={valor}&amp;nombre={etiqueta}"> Ver Imagen </a>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
            </div>
    </xsl:template>

    <xsl:template match="campo[tipo = ''usuario'']">
        <div class="widget widget-table col-sm-6">
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
                            <th>Usuario</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:for-each select="valor/usuario">
                            <xsl:param name="id" select="''id''"/>
                            <xsl:param name="nombre" select="''nombre''"/>
                            <tr>
                                <td>
                                   <a target="_blank" href="/SIGIPRO/Seguridad/Usuarios/Ver?id={id}"><xsl:value-of select="nombre" /></a>
                                </td>
                            </tr>

                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="campo[tipo = ''sangria'']">
        <div class="widget widget-table col-sm-6">
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
                            <th>Sangría</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Campos diferentes de tablas -->
                        <xsl:for-each select="valor/sangria">
                            <xsl:param name="id" select="''id''"/>
                            <tr>
                                <td>
                                    <a target="_blank" href="//SIGIPRO/Caballeriza/Sangria?accion=ver&amp;id_sangria={id}">
                                        Ver Sangría (id: <xsl:value-of select="id" />)
                                    </a>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="campo[tipo = ''aa'']">
        <div class="widget widget-table col-sm-6">
            <div class="widget-header">
                <h3>
                    <i class="fa fa-table"></i> 
                    <xsl:value-of select="''Referencia a Actividades de Apoyo''" /> 
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
                        <xsl:param name="valor" select="''valor''"/>
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
                ')
);