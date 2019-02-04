<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Añadir nuevo registro</title>
</head>
<body>
	<h1 align="center">Añadir nuevo registro</h1>
	
	<br><br>
	
	<div align="center">
		<form action="HomeServlet" method="post">
			Introduce usuario <input type="text" name="us" /><br><br>
			Introduce contraseña <input type="text" name="pas" /><br><br>
			<input type="submit" name="registraUsuario" value="Añade registro" />
		</form>	
	</div>
</body>
</html>