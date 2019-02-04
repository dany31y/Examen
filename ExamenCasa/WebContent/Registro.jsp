<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="false"%>
    <%String resultado = (String) request.getAttribute("resultado");
    String nombreUsuario = (String) request.getAttribute("nombreUsuario");
    String visitas = (String) request.getAttribute("visitas"); //el de las sesiones
    String contadorVisitas = (String) request.getAttribute("contadorVisitas"); //el de las cookies
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lista de registros</title>
</head>
<body>
<h1 align="center">Lista de registros</h1>
<br>
Conectado como <b><%=nombreUsuario %></b> <br/>
Páginas visitadas en esta conexión: <b><%=visitas %></b> <br/>
Número de visitas pasadas: <b><%=contadorVisitas %></b> <br/> <br/> <br/>
<br><br><br>

	<p align="center">Buscar por patron del nombre </p>
		<form action="HomeServlet" method="post" align="center">
			<input type="text" name="patron">  
			<input type="submit" value="Buscar" name="consulta">
		</form>
	<br><br><br>
	
	<div align="center">
		<%=resultado %>
	</div>
	
	<br>
	<form action="HomeServlet" method="post" align="center">
		<button >Volver a Home</button>
	</form>
</body>
</html>