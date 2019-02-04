 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String usuario = request.getParameter("usuario");
			
			if(request.getSession(false) == null) { //si es nulo 
				HttpSession miSesion = request.getSession(true); //declaramos nueva sesion
				miSesion.setAttribute("nombreUsuario", usuario); //parametro en sesion
				miSesion.setAttribute("visitas", "1");
				
				//mandamos atributos al jsp
				request.setAttribute("nombreUsuario", request.getParameter("usuario"));
				request.setAttribute("visitas", "1");
			}else {//si hay sesion
				HttpSession miSesion = request.getSession(true); //la recuperamos
				//mandamos los datos al jsp con los datos recuperados de la sesion
				request.setAttribute("nombreUsuario", miSesion.getAttribute("nombreUsuario"));
				String visitas = (String) miSesion.getAttribute("visitas");
				String visitasAumentadas = Integer.toString(Integer.parseInt(visitas)+1);
				request.setAttribute("visitas", visitasAumentadas);
				
				//cambiamos la sesion de las visitas ya aumentadas
				miSesion.setAttribute("visitas", visitasAumentadas);
				
				//si el getParameter del usuario es nullo recogemos el valos de la sesion para pasarlo a la cookie
				if(usuario==null) {
					usuario=(String) miSesion.getAttribute("nombreUsuario");
				}
			}
			
			//cookies
			Cookie cookies[]=request.getCookies();//Recogemos todas las cookies existentes de la pagina
			if(cookies==null  || Metodos.comprobarCookies(cookies, usuario)<0) {
				String idioma="";
				String contadorVisitas="1";
				Cookie ck=new Cookie(usuario,usuario + "&" + idioma + "&" + contadorVisitas);
				ck.setMaxAge(60 * 60 * 24 * 365 * 10);
				response.addCookie(ck);	
				request.setAttribute("contadorVisitas",contadorVisitas);
			}else {
				//Cogemos la cookie que tenga como nombre el introducido por el usuario
	    		Cookie ck = cookies[Metodos.comprobarCookies(cookies, usuario)];
	    		//Dividimos cada parte del Valor de la cookie y lo metemos en un array
	    		String valor[]=ck.getValue().split("&");
	    		//Nos quedamos con la profesion
	    		String idioma=valor[1];			    		
	    		request.setAttribute("idioma", idioma);		    		
	    		//Nos quedamos con el contador de visitas del usuario
	    		String contadorVisitas=valor[2];
	    		request.setAttribute("contadorVisitas",contadorVisitas);
			}	
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home.jsp");
	        dispatcher.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String desconectar = request.getParameter("desconectar");
		String registrar = request.getParameter("registrar");
		String registraUsuario = request.getParameter("registraUsuario");
		String consulta = request.getParameter("consulta");
		String patron = request.getParameter("patron");
		String parametro = request.getParameter("parametro"); //ajax
		
		Connection conexion = Metodos.createConnection();
		
		if(desconectar!=null) {
			//recogemos el usuario de la sesion y lo metemos en la cookie
			String usuario="";
			String visitasSesion="";
			if(request.getSession(false) != null) { //si es nulo 
				HttpSession miSesion = request.getSession(true); //declaramos nueva sesion
				usuario = (String) miSesion.getAttribute("nombreUsuario");
				visitasSesion= (String) miSesion.getAttribute("visitas");
			}
			
			//creamos cookie
			Cookie cookies[]=request.getCookies();//Recogemos todas las cookies existentes de la pagina
			//Buscamos la cookie que coincide con el nombre
			Cookie ck=cookies[Metodos.comprobarCookies(cookies, usuario)];
			ck.setMaxAge(60 * 60 * 24 * 365 * 10);
			//Cogemos el contador de Visitas que tiene la cookie y le sumamos la de la sesion actual
			String contadorVisitas=(ck.getValue().split("&"))[2];			
			contadorVisitas=Integer.toString(Integer.parseInt(contadorVisitas)+Integer.parseInt(visitasSesion));
	
			//recogemos el idioma del formulario
			String idioma=request.getParameter("idioma");
			//Guardamos toda la informacion en la cookie
    		ck.setValue(usuario + "&" + idioma + "&" +contadorVisitas);	
    		response.addCookie(ck);
    				
			//invalidamos sesion
			request.getSession().invalidate();

			//redireccionamos el servlet a login
			//response.sendRedirect ( "LoginServlet");
			request.setAttribute("usuario", usuario); //mandamos el usuario para despedirlo
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
	        dispatcher.forward(request, response); 
		}else if(registrar!=null){
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Addnew.jsp");
	        dispatcher.forward(request, response); 
		}else if(registraUsuario!=null){ //si venimos del formulario del Addnew.jsp

				//recogemos parametros de registrar
				String us = request.getParameter("us");
				String pas = request.getParameter("pas");
				
				//llamamos al metodo de añadir y recogemos el mensaje 
				String mensaje = Metodos.addRegistro(conexion,us,pas);
				//pasamos ese mensaje al jsp, nos vamos al doGet para imprimir otra vez el formulario
				request.setAttribute("mensaje", mensaje);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
		        dispatcher.forward(request, response); 
		}else if(consulta!=null){
			//cogemos datos de la sesion		
				if(request.getSession(false) != null) { //si no es nulo 
					HttpSession miSesion = request.getSession(true); //la recuperamos
					String usuario = (String) miSesion.getAttribute("nombreUsuario");
					String visitas= (String) miSesion.getAttribute("visitas");
					visitas = Integer.toString(Integer.parseInt(visitas)+1);
					
					//guardamos las visitas ya aumentadas para que la proxima los aumente
					miSesion.setAttribute("visitas", visitas);
									
					//cogemos datos de la cookie
					Cookie cookies[]=request.getCookies();
		    		Cookie ck = cookies[Metodos.comprobarCookies(cookies, usuario)];
		    		String valor[]=ck.getValue().split("&");    		
		    		String contadorVisitas=valor[2];
		    		
					//mandamos datos al jsp
					request.setAttribute("nombreUsuario", usuario);
					request.setAttribute("visitas", visitas); //sesion
					request.setAttribute("contadorVisitas", contadorVisitas); //cokie
				}
				
			    //si le pasamos un patron llama al metodo de buscar si no muestra toda la tabla
				String resultado="";
				if(patron!=null) {
					resultado=Metodos.buscar(conexion, patron);
				}else {
					resultado = Metodos.mostraRegistro(conexion);
				}		
				request.setAttribute("resultado", resultado);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registro.jsp");
		        dispatcher.forward(request, response);
		}else if(parametro!=null){
			response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	             
	        // Comprobar si la petición es mediante Ajax
	       Boolean esAjax;
	        //la llamada que me ha llegado contiene esta solicitud? si es que si es que contiene un ajax
	        esAjax="XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")); // Cabecera X-Requested-With
	        if (esAjax) {
	        	String resultado="";
	        	
	        	if(parametro.equals("ES")) {
	        		resultado="Palabra,Traducción,Enviar";
	        	}
	        	if(parametro.equals("FR")) {
	        		resultado="Mot,Traduction,Envoyer";
	        	}
	        	if(parametro.equals("EN")) {
	        		resultado="Word,Translation,Submit";
	        	}	        	
	        	//enviamos el resultado al ajax
	        	out.println(resultado);
	        	
	        }else {
	            out.println("Este servlet solo se puede invocar vía Ajax");
	        }
			
		}else {
			doGet(request, response);
		}
	}

}
