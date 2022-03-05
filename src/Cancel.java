

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;


public class Cancel extends HttpServlet {
 private static final long serialVersionUID = 1L;

 private String styleFunction[] = {
				" <style>									",
				" p{										",
				"	padding: 0;								",
				"	font-weight: bold;							",
				"	left: 50%;								",
				"	text-align: left;							",
				"	font-size: 15px;							",
				" }										",
				" h1{										",
				"	font-family: sans-serif;						",
				"	margin: 0;								",
				"	padding: 0 0 20px;							",
				"	text-align: center;							",
				"	top: 20%;								",
				"	background: linear-gradient(to right, #003300 30%, #00ff00 100%);	",
				"	-webkit-background-clip: text;						",
				"	-webkit-text-fill-color: transparent;					",
				" }  										",
				" input[type=\"button\"]{							",
				"	border: none;								",
				"	outline: none;								",
				"	height: 30px;								",
				"	background: linear-gradient(to right, #00ffcc 0%, #ffff00 101%);	",
				"	color: #fff;								",
				"	font-size: 18px;							",
				"	border-radius: 20px;							",
				"	font-weight: bold;							",
				" }										",
				" body{										",
				"	background-image:url(\"https://media.istockphoto.com/photos/abstract-background-picture-id810482982?k=20&m=810482982&s=612x612&w=0&h=F4HsKmjF-Qvs6Fvi2zKsQOKyWqdX6LPBBuW2lfi7MTo=\");				",
				"	background-repeat: no-repeat;						",
				"	background-size: 100% 100%;						",
				" }										",
				" input[type=\"button\"]:hover{							",
				"	cursor: pointer;							",
				"	background: DodgerBlue;							",
				"	color: black;								",
				" }										",
				" .box{										",
				"	width: 380px;								",
				"	height: 450px;								",
				"	background: linear-gradient(to left, #cc0066 0%, #ff9900 100%);		",
				"	color: #fff;								",
				"	top: 50%;								",
				"	left: 50%;								",
				"	position: absolute;							",
				"	transform: translate(-50%, -50%);					",
				"	box-sizing: border-box;							",
				"	padding: 70px 30px;							",
				" }										",
				" </style>									"
			
				};

				
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	HttpSession session = request.getSession();
	
	for(int i = 0 ; i < styleFunction.length ; i ++ ){
		out.print(styleFunction[i]);
	}

	out.print("<center>");
	out.println("<div class=\"box\">");
	out.println("<h1>Cancel Page</h1>");	
	out.println("<br/>Sorry!!...Your Process has been cencelled\n");
	out.println("<br/><input type=\"button\" onclick=\"window.location.href = 'index.html'\" value=\"Home\">");
	out.println("</div>");
	out.print("</center>"); 
	session.invalidate();
	out.close();
  }

}