

import java.sql.*;
import java.util.*;
import java.text.*; 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

public class GmailVerify extends HttpServlet{			

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	HttpSession session = request.getSession();
	String mobile = (String) session.getAttribute("number");
	String recipient = (String) session.getAttribute("Gmail");
	String url = SendEmail.generateURL(mobile);
	SendEmail.link(recipient,url);
	session.setAttribute("urlToken",url);

	RequestDispatcher rd = request.getRequestDispatcher("clientWS.html");
	rd.forward(request,response);
 }

}