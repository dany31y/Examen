<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="false"%>
    
    <%
    String nombreUsuario = (String) request.getAttribute("nombreUsuario");
    String visitas = (String) request.getAttribute("visitas"); //el de las sesiones
    String contadorVisitas = (String) request.getAttribute("contadorVisitas"); //el de las cookies
    String idioma = (String) request.getAttribute("idioma");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Home</title>
<script type="text/javascript">
function principal(){
	var idiomas = document.getElementsByName("idioma");
	var parametro;
	for(i=0;i<idiomas.length;i++){
		if(idiomas[i].checked == true){
			parametro = 'parametro='+idiomas[i].value;
		}
	}
	var xmlhttp;  // objeto XMLHttpRequest
    if (window.XMLHttpRequest) {  // para IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {  // para IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } //aqui el readystate = 0  
    xmlhttp.onreadystatechange = function() { //se ejecuta solo si el objeto xmlhttp cambia de valor
        // si el resultado está listo (readyState==4) y la respuesta es correcta (status==200)
        if (xmlhttp.readyState==4 && xmlhttp.status==200) { //200= respuesta corecta 404 = pagina no encontrada
        	
        	//recogemos el OUT.PRINT del servlet
            var respuesta = xmlhttp.responseText; 
        	var array = respuesta.split(",");
        	document.getElementById("lbl_palabra").innerHTML = array[0];
        	document.getElementById("lbl_traduccion").innerHTML = array[1];
        	document.getElementById("enviar").value = array[2];
		            
        }
    }
    xmlhttp.open("POST","HomeServlet?" +parametro,true);  // crea la conexión con parámetros: método, url, asíncrono? -aqui el readystate valdrá 1
    xmlhttp.setRequestHeader("X-Requested-With", "xmlhttprequest");  // establece la cabecera HTTP necesaria
    xmlhttp.send();  // lanza la solicitud
	
}
</script>
</head>
<body onload="principal()">
	<h1>Home</h1>
	
	<form action="HomeServlet" method="post"><button name="desconectar">Desconectar</button> <br />
		Conectado como <b><%=nombreUsuario %></b> <br/>
		Páginas visitadas en esta conexión: <b><%=visitas %></b> <br/>
		Número de visitas pasadas: <b><%=contadorVisitas %></b> <br/> <br/> <br/>
		<div>
			ES <input type="radio" name="idioma" value="ES" checked="checked" onclick="principal()"/>
			EN <input type="radio" name="idioma" value="EN"  onclick="principal()"/>
			FR <input type="radio" name="idioma" value="FR"  onclick="principal()"/>
		</div>
	</form>
		<div>
			<label for="palabra" id="lbl_palabra">Palabra</label> 
			<input type="text" name="palabra"> <br />
			<label for="traduccion" id="lbl_traduccion">Traducción</label>
			<input type="text" name="traduccion"> <br />
			<input type="button" id="enviar" value="Enviar" /> <br />
		</div>
	
	<br />
	
	<form action="HomeServlet" method="post">
		<button name="consulta" id="consulta" >Consulta de usuarios</button>
	</form>
	
<script type="text/javascript">
var idiomas = document.getElementsByTagName("input");
for(i=0;i<idiomas.length;i++){
	if(idiomas[i].value=='<%=idioma%>'){
		idiomas[i].checked=true;
	}
}
</script>
</body>
</html>