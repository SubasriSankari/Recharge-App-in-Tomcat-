
 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

public class GoogleAuthenticator extends HttpServlet{
 private static final long serialVersionUID = 1L;


 @Override 
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	HttpSession session = request.getSession();
	out.print("Its Google Authenticator");
	
 }

}