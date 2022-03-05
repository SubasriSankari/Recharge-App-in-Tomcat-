

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class BrowsePlanCheck extends HttpServlet {
 private static final long serialVersionUID = 1L;
 
 static Connection con = null;

 private String styleFunction[] = {
				  " <style>										",
				  " p{											",
				  "	padding: 0;									",
				  "	font-weight: bold;								",
				  "	left: 50%;									",
				  "	text-align: left;								",
				  "	font-size: 15px;								",
				  " }											",
				  " h3{											",
				  "	color: white;									",
				  " }											",
				  " button{										",
				  "	border: none;									",
				  "	outline: none;									",
				  "	height: 30px;									",
				  "	background: linear-gradient(to right, #00ffcc 0%, #ffff00 101%);		",
				  "	color: #fff;									",
				  "	font-size: 18px;								",
				  "	border-radius: 20px;								",
				  "	font-weight: bold;								",
				  " }											",
				  " body{										",
				  "	background-image:url(\"https://media.istockphoto.com/photos/abstract-background-picture-id810482982?k=20&m=810482982&s=612x612&w=0&h=F4HsKmjF-Qvs6Fvi2zKsQOKyWqdX6LPBBuW2lfi7MTo=\");					",
				  "	background-repeat: no-repeat;							",
				  "	background-size: 100% 100%;							",
				  " }											",
				  " button:hover{									",
				  "	cursor: pointer;								",
				  "	background: DodgerBlue;								",
				  "	color: black;									",
				  " }											",
				  " p{											",
				  "	color:red;									",
				  "	font-weight:bold;								",
				  " }											",
				  " .box{										",
				  "	width: 380px;									",
				  "	height: 450px;									",
				  "	background: linear-gradient(to left, #cc0066 0%, #ff9900 100%);			",
				  "	color: #fff;									",
				  "	top: 50%;									",
				  "	left: 50%;									",
				  "	position: absolute;								",
				  "	transform: translate(-50%, -50%);						",
				  "	box-sizing: border-box;								",
				  "	padding: 70px 30px;								",
				  " }											",
				  " </style>										"
				};
				
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	String name = request.getParameter("username");
	String mobile = request.getParameter("userphone");
	String network = request.getParameter("simname");
	String amount = request.getParameter("currentPlanAmount");
	String mail = request.getParameter("mailid");
	String days = request.getParameter("daysName");
	boolean checkExistingUser = true;
	boolean valid = true;

	HttpSession session = request.getSession();
	
	session.setAttribute("userName",name);
	session.setAttribute("userPhone",mobile);
	session.setAttribute("Network",network);
	session.setAttribute("Amount",amount);
	session.setAttribute("Days",days);
	session.setAttribute("Mail",mail);
	
	con = ConnectionDao.makeConnection();

	for(int i = 0 ; i < styleFunction.length ; i ++ ){
		out.print(styleFunction[i]);
	}

	String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
	valid = VerifyUtils.verify(gRecaptchaResponse,ConstantValue.SECRET_KEY);	
	if(!valid){
		out.print("<p>Error Invalid Captcha!</p>");
		RequestDispatcher rd = request.getRequestDispatcher("newUser.html");  
       		rd.include(request,response);
	}
	
	else{	
		if(ConnectionDao.numberAndUserNameAndNetworkExistOrNot(name, mobile, network, mail)){
			boolean flag = false;
			String Status = null;
		
			try{
				PreparedStatement ps=con.prepareStatement("select status from recharge_list where phone_number=?");
				ps.setString(1,mobile);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Status = rs.getString("status");
					flag = true;
				} 
				if(flag){
					if(Status.equals("Running")){
						out.print("<body>");
						out.print("<div class=\"box\">");
						out.print("<h3>Your Plan is not expired!! Please Login again to View Your Plan Detail.</h3>");
						out.print("<button onclick=\"window.location.href='individual.html'\">Go to User Login</button>");
						out.print("</div>");
						out.print("</body>");
					}
					else{
						session.setAttribute("existUser",checkExistingUser);
						RequestDispatcher rd = request.getRequestDispatcher("payment.html");  
        					rd.forward(request,response);
					}
				}		
			}catch(Exception e){
				out.print(e);
			}
		}		

		else if(ConnectionDao.numberExistOrNot(mobile)){
			out.print("<p>Entered Username or Mobile Number or<br>Network or Mail Id is incorrect</p>");
			RequestDispatcher rd = request.getRequestDispatcher("newUser.html");  
        		rd.include(request,response);	
		}
	
		else{
			checkExistingUser = false;
			session.setAttribute("existUser",checkExistingUser);
			RequestDispatcher rd = request.getRequestDispatcher("payment.html");  
        		rd.forward(request,response);
		}
	}
	out.close();	
  }

}