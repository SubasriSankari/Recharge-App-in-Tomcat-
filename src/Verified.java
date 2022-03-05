
import java.sql.*; 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

public class Verified extends HttpServlet{

 static Connection con = null;

 private String styleFunction[] = {

				" <style>								",
				" h3{									",
				" 	font-family:Copperplate;					",
				" }									",
				" h4{									",
				" 	font-family:Courier New;					",
				"	font-weight:bold;						",
				" }									",
				" </style>								"
				};


 private String htmlFunction[] = {

				" <center>								",
				" 	<img src=\"https://www.crabbyjoes.com/wp-content/uploads/2020/11/chucks-roadhouse-verified.gif\" name=\"verify\" height=\"150\" width=\"150\" style=\"vertical-align:middle\"/>								",
				" 	<h3>Verified Successfully!!</h3>				",
				"	<h4>Now You can move to login page</h4>				",
				" </center>								"
				};
 
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	HttpSession session = request.getSession();
	String mobile = (String) session.getAttribute("number");

	for(int i = 0 ; i < styleFunction.length ; i ++ ){
		out.print(styleFunction[i]);
	}	
	
	for(int i = 0 ; i < htmlFunction.length ; i ++ ){
		out.print(htmlFunction[i]);
	}

	con = ConnectionDao.makeConnection();
	ConnectionDao.AlterTable(mobile);
 }

}