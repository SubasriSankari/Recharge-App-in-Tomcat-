

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class IndividualDisplay extends HttpServlet {
 private static final long serialVersionUID = 1L;

 static Connection con = null;
 static PreparedStatement ps;
 static String name;
 static String network;
 static String mail;
 static int payment;
 static java.sql.Date lastRecharge;
 static java.sql.Date expiryDate;
 
 private String styleFunction[] = {
				" <style>									",
				" h3{										",
				"	color:red;								",
				" }										",
				" p{										",
				"	padding: 0;								",
				"	font-weight: bold;							",
				"	color: white;								",
				"	left: 50%;								",
				"	text-align: left;							",
				"	font-size: 15px;							",
				" }										",
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

 public static boolean checkIsCurrentDate(java.sql.Date sqlDate){
	DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	java.util.Date expireDate = new java.util.Date(sqlDate.getTime());
	java.util.Date currDate = new java.util.Date();
	if(formatter.format(expireDate).equals(formatter.format(currDate))){
		return true;
	}
	return false;
 }

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	HttpSession session = request.getSession();
	String number = (String) session.getAttribute("number");
	boolean valid = true;
	con = ConnectionDao.makeConnection();
	for(int i = 0 ; i < styleFunction.length ; i ++ ){
		out.print(styleFunction[i]);
	}

	try{
		
		ps = con.prepareStatement("select * from recharge_list where phone_number=?");
		ps.setString(1,number);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			name = rs.getString(2);
			mail = rs.getString(4);
			network = rs.getString(5);
			payment = rs.getInt(6);
			lastRecharge = rs.getDate(7);
			expiryDate = rs.getDate(8);
		}	
	}catch(Exception e){
		out.print(e);
	}
	
	if(ConnectionDao.checkPlanExpiredOrNot(number)){
		out.println("<div class=\"box\">");
		out.println("<p>Welcome "+ name);
		out.println("</p><p>Your network is "+ network);
		out.println("</p><p>Your last Payment Rs." + payment + "/- on " + lastRecharge );
		out.println("</p><p>Your Plan will Expires on " + expiryDate);
		out.println("</p><p><input type=\"button\" onclick=\"window.location.href = 'individual.html' \" value = \"Logout\">");
		session.invalidate();
		out.println("</p><p>New Recharge for others press here :</p>"); 
		out.println("\n<p><input type=\"button\" onclick=\"window.location.href = 'newUser.html' \" value = \"For others\"></p>");
		out.println("</div>");
	
	}
	else{
		out.print("<h3>Sorry!! Your plan has expired.... <br>Please Recharge now to continue..</h3>");
		RequestDispatcher rd = request.getRequestDispatcher("newUser.html");  
        	rd.include(request,response);
	}

	if(checkIsCurrentDate(expiryDate)){
		SendEmail.requestToAlertMessage(mail,number);
	}

	out.close();
  }

}