import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.Cookie;

public class Metodos {

	public static boolean comprobarUsuario(String rutaFicheroProperties,String usuario, String clave) {
		boolean resultado=false;
		Properties prop = new Properties();
		InputStream is = null;
		
		try {
			is = new FileInputStream(rutaFicheroProperties);
			prop.load(is);
		} catch(IOException e) {
		}
		
		for (Enumeration e = prop.keys(); e.hasMoreElements() ; ) {
			// Obtenemos el objeto
			Object us = e.nextElement();
			String pass = prop.getProperty(us.toString());

			if(usuario.equals(us) && clave.equals(pass)) {
				resultado=true;
			}
		}
		return resultado;
	}
	
	
	//Va a devolver la posicion de la Cookie. -1 si no existe
			public static int comprobarCookies(Cookie cookies [],String user) {
				int contador=-1;
				if(cookies!=null) {
					for(int i=0;i<cookies.length;i++){
						if(cookies[i].getName().equals(user)) {
							contador=i;
						}
					}
				}
				return contador;
			}
	
			
			// JDBC driver name and database URL
			static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
			static final String DB_URL = "jdbc:mysql://localhost:3306/ex1ev";

			//  Database credentials
			static final String USER = "root";
			static final String PASS = "root";
			
			static Connection conn = null;
			static Statement stmt = null;
			
			public static Connection createConnection() {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				}catch(ClassNotFoundException e){
				      e.printStackTrace();
				}
				Connection connection = null;
				try {
					if(connection == null) {
						connection = DriverManager.getConnection(DB_URL,USER,PASS);
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				return connection;
			}		
			public static String addRegistro(Connection conexion,String usuario, String pass) {
				 String mensaje="";
			try{
				stmt = conexion.createStatement();
				boolean comprobarUS=false;
				boolean comprobarPassVacio=false;
				String sql="";
				//comprobamos que el usuario no exista
				sql="select usuario from usuario";
				ResultSet rs = stmt.executeQuery(sql);

			      while(rs.next()){
			    	 String us = rs.getString("usuario");
			    	 	if(usuario.equals(us)) {
			    	 		mensaje="El usuario ya existe<br>";
			    	 		comprobarUS=true;  	 		
			    	 	}
			      }	
			      rs.close();
			    //comprobamos que el usuario no sea vacio
			      if(usuario.equals("")) {
			    	  mensaje+="El usuario es obligatorio<br>";
			    	  comprobarUS=true;
			    	  
			      }
				//comprobamos que la clave no sea vacia
			      if(pass.equals("")) {
			    	  mensaje+="La clave no puede estar vacia<br>";
			    	  comprobarPassVacio=true;
			    	  
			      }
			      if(comprobarUS==false && comprobarPassVacio==false) {
				            
				      sql = "insert into usuario values('"+usuario+"','"+pass+"');";      
				      stmt.executeUpdate(sql);      
				      mensaje+="Empleado insertado con exito";     	      
			      }
			      stmt.close();
			   }catch(SQLException se){
				   mensaje="Error, no se ha podido agregar este empleado<br>Comprueba la sentencia sql";
			   }catch(Exception e){
				   mensaje="Operacion no realizada <br>Revisa los datos introducidos";
			   }
			return mensaje;
		}
			
			//metodo que muestra el nombre en funcion del EMPNO
			public static String mostraRegistro(Connection conexion) {
					
				String resultado="";
				try{
				      stmt = conexion.createStatement();
				      String sql;
				      sql = "SELECT * from usuario";
				      ResultSet rs = stmt.executeQuery(sql);
				      
				      resultado+="<table align='center' border='1'>";
				      resultado+="<tr><th> Usuario </th><th> Clave </th></tr>";
				      while(rs.next()){
				    	 String us = rs.getString("usuario");
				         String pass  = rs.getString("clave");
				         
				         resultado+="<tr><td> "+us+" </td><td> "+pass+" </td></tr>";
				      }
				      resultado+="</table>";
				      rs.close();
				      stmt.close();
				   }catch(SQLException se){
				      resultado="Error, no hay datos";
				   }catch(Exception e){
					   resultado="Error, no hay datos";
				   }

				return resultado;
				
			}
			
			public static String buscar(Connection conexion,String us) {
				String resultado="";
				try{
				      stmt = conexion.createStatement();
				      String sql="";
				      sql="select * from usuario where usuario like '%"+us+"%';";
				      ResultSet rs = stmt.executeQuery(sql);
				      String us1="";
				      String pass="";
				      resultado+="<table align=center border=1>";
				      resultado+="<tr><th>Usuario</th><th>Clave</th></tr>";
				      while(rs.next()){
				    	 us1 = rs.getString("usuario");
				    	 pass = rs.getString("clave");
				    	 
				    	 resultado+="<tr><td> "+us1+" </td><td> "+pass+" </td></tr>";
				      }
				      resultado+="</table>";
				      rs.close();
				      if(us1.equals("")) {
				    	  resultado="No hay usuarios para esa busqueda";
				      }
				      
				      stmt.close();
				   }catch(SQLException se){
				      
				   }

				return resultado;
				
			}
}
