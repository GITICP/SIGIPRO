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
                        <button type="button" id='{$nombre-campo}_eliminar' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(''{$nombre-campo}'')"> Borrar</button>
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
                        <select id="sangria" class="select2 sangria" name="{$nombre-campo}" value="{$valor}" style=''background-color: #fff;'' ></select>    
                        <div class="ver">
                            <a>Ver Sangría</a>
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
                                <input type="checkbox" name="{$nombre-campo}" value="{$valor}"> 
                                    <xsl:value-of select="$etiqueta"></xsl:value-of>
                                </input>
                            </div>
            
                
                        </xsl:for-each>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    
</xsl:stylesheet>
