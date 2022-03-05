

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;


public class IndividualError extends HttpServlet {
 static Connection con = null;
 
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	
	out.print("<p style=\"color:white;\">You Entered wrong user name or mobile number or gmail....if you are new user, press new User!! </p>");
	RequestDispatcher rd = request.getRequestDispatcher("individual.html");  
       	rd.include(request,response);
	
	out.close();
  }

}