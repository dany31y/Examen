<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="false"%>
    <%
    String mensaje = (String) request.getAttribute("mensaje");
    String usuario = (String) request.getAttribute("usuario");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Inicia sesión</title>
</head>
<body>
	<h1 align="center"><a href="LoginServlet">Inicia sesión</a></h1>
	<br>
	<%if(usuario!=null){ %>
			<div align="center"><p style="color:green">¡Hasta luego <b><%=usuario %></b>!</p></div>
	<%} %>
	<br>
	
	<form action="LoginServlet" method="post" align="center">
		Usuario <input type="text" name="usuario" placeholder="Usuario"/><br><br>
		Contraseña <input type="password" name="clave" placeholder="Contraseña" /><br><br>
		<input type="submit" value="Entrar"/>
	</form>
	<br>
	<%if(mensaje!=null){ %>
			<div align="center" ><p style="color:red"><%=mensaje %></p></div>
		<%} %>
	<br>
	<form action="HomeServlet" method="post" align="center">
		<input type="submit" value="Registrar" name="registrar"/>
	</form>
	
		
	
</body>
</html>