
 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CheckLogin extends HttpServlet{
 
 static Connection con = null;

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	String name = request.getParameter("name");
	String gmail = request.getParameter("gmail");
	String mobile = request.getParameter("mobile");
	String verifySource = request.getParameter("radio1");

	HttpSession session = request.getSession();
	session.setAttribute("number",mobile);
	session.setAttribute("Gmail",gmail);
	con = ConnectionDao.makeConnection();

	if(ConnectionDao.numberAndUserNameExistOrNot(name,mobile,gmail)){
		if(verifySource.equals("GAuth")){

			response.sendRedirect("GoogleAuthenticator");
		}
		else{
			response.sendRedirect("GmailVerify");
		}
	}
	else{
		response.sendRedirect("IndividualError");
	}
	
 }

}