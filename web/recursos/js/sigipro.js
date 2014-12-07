window.valorRB = null;
$("input[name='control']").click(function() {
    valorRB = this.value;    
    document.getElementById("controlID").value=valorRB;
});


function prueba(){
    var x = document.getElementById(valorRB);
    
    document.getElementById("editarIDUsuario").value=valorRB;
    document.getElementById("editarNombreUsuario").value=x.children[1].innerHTML;
    document.getElementById("editarNombreCompleto").value=x.children[2].innerHTML;
    document.getElementById("editarCorreoElectronico").value=x.children[3].innerHTML;
    document.getElementById("editarCedula").value=x.children[4].innerHTML;
    document.getElementById("editarDepartamento").value=x.children[5].innerHTML;
    document.getElementById("editarPuesto").value=x.children[6].innerHTML;
    document.getElementById("editarFechaActivacion").value=x.children[7].innerHTML;
    document.getElementById("editarFechaDesactivacion").value=x.children[8].innerHTML;
}

function setCookie(name, value, expires, path, domain, secure){
	cookieStr = name + "=" + escape(value) + "; ";
	
	if(expires){
		expires = setExpiration(expires);
		cookieStr += "expires=" + expires + "; ";
	}
	if(path){
		cookieStr += "path=" + path + "; ";
	}
	if(domain){
		cookieStr += "domain=" + domain + "; ";
	}
	if(secure){
		cookieStr += "secure; ";
	}
	
	document.cookie = cookieStr;
}

function setExpiration(cookieLife){
    var today = new Date();
    var expr = new Date(today.getTime() + cookieLife * 24 * 60 * 60 * 1000);
    return  expr.toGMTString();
}

function asignarCookieUsuario(){
    if(valorRB)
    {
        var x = document.getElementById(valorRB);
        var nombre = x.children[2].innerHTML;
        
        
        setCookie('idUsuario', valorRB.toString() + ';' + nombre, 1, '/');
        
        self.location="RolesUsuario.jsp";

    }
    else
    {
    }
}

function asignarCookieRol(){
    if(valorRBRol)
    {
        var x = document.getElementById(valorRBRol);
        var nombre = x.children[1].innerHTML;
        
        
        setCookie('idRol', valorRBRol.toString() + ';' + nombre, 1, '/');
        
        self.location="PermisosRol.jsp";

    }
    else
    {
    }
}
window.valorRBRol = null;
$("input[name='controlRol']").click(function() {
    valorRBRol = this.value;
    document.getElementById("controlIDRol").value=valorRBRol;
});

function EditarRolJS(){
    var x = document.getElementById(valorRBRol);
    document.getElementById("editarIdRol").value=valorRBRol;
    document.getElementById("editarNombre").value=x.children[1].innerHTML;
    document.getElementById("editarDescripcion").value=x.children[2].innerHTML;

}
