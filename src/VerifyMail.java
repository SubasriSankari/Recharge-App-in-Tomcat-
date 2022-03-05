

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class VerifyMail extends HttpServlet {
 private static final long serialVersionUID = 1L;
 
 static Connection con = null;
 static PreparedStatement ps;
 
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	
	HttpSession session = request.getSession(false);

	String name = (String)session.getAttribute("userName");
	String mailId = (String)session.getAttribute("Mail");

	String givenOTP = SendEmail.requestToSend(name,mailId);

	session.setAttribute("givenOtp",givenOTP);

	RequestDispatcher rd = request.getRequestDispatcher("verify.html");  
        rd.forward(request,response);

	out.close();	
  }

}