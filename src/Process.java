

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class Process extends HttpServlet {
 private static final long serialVersionUID = 1L;
 
 static Connection con = null;
 static PreparedStatement ps;
 
 private String styleFunction[] = {
				" <style>											",
				" input[type=submit]{										",					
				"	border-radius : 10px;									",									"	background-image: linear-gradient(to right, pink , yellow);				",			
				"	padding: 4px 8px;									",									"	font-family: cursive;									",									" }												",
				" </style>											"

				};

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	
	HttpSession session = request.getSession(false);

	String enteredOTP = request.getParameter("otpConfirm");
	String givenOTP = (String)session.getAttribute("givenOtp");
	
	if(enteredOTP.equals(givenOTP)){
		response.sendRedirect("AddingUser");
	}else{
		for(int i = 0 ; i < styleFunction.length ; i ++){
			out.print(styleFunction[i]);
		} 
		out.print("Sorry You entered wrong otp:(... Process has been cancelled");
		out.print("<input type=\"button\" onclick=\"window.location.href='newUser.html' \" value=\"Ok\">");
	}
	out.close();
  }

}