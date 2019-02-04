

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
        dispatcher.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rutaFicheroProperties = this.getServletContext().getRealPath("/")+"/credenciales/usuarios.properties";
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
			
			if(Metodos.comprobarUsuario(rutaFicheroProperties,usuario, clave)==true) {
				request.getRequestDispatcher("HomeServlet").forward(request, response); 
			}else {
				//Mandamos mensaje de datos incorrectos al jsp y mandamos de nuevo para el doGet
				request.setAttribute("mensaje", "Datos incorrectos");
				doGet(request, response);
			}
	}

}
