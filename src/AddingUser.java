

import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class AddingUser extends HttpServlet {
 private static final long serialVersionUID = 1L;
 
 static Connection con = null;
 static PreparedStatement ps;
 static PreparedStatement psForRoles;

 static java.sql.Date currentDate;
 static java.sql.Date expiryDate;

 private String styleFunction[] = {
				" <style>											",
				" button{											",
				"	border-radius : 10px;									",
				"	background-image: linear-gradient(to right, pink , yellow);				",
				"	padding: 4px 8px;									",
				"	font-family: cursive;									",
				" }												",
				"  </style>											"
				};

 
 public static void newDate(int days){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String currDate = date.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, days);
		String newDate = date.format(c.getTime());
		currentDate = java.sql.Date.valueOf(currDate);
		expiryDate = java.sql.Date.valueOf(newDate);
 }

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter(); 
	
	HttpSession session = request.getSession(false);

	String name = (String)session.getAttribute("userName");
	String mobile = (String)session.getAttribute("userPhone");
	String network = (String)session.getAttribute("Network");
	String amount = (String)session.getAttribute("Amount");
	String days = (String)session.getAttribute("Days");
	String mailId = (String)session.getAttribute("Mail");
	String url = (String)session.getAttribute("urlToken");
	int Amount = Integer.parseInt(amount);
	int Days = Integer.parseInt(days);
	//out.print("name "+name+" mobile "+mobile+" network " +network+" amount "+amount +" days "+days);
	boolean checkExistUser = (boolean)session.getAttribute("existUser");

	con = ConnectionDao.makeConnection();

	boolean flag = false;
	String statusKey = "Running";
	newDate(Days);	

	if(checkExistUser){
		
		try{
			ps = con.prepareStatement(  
				"UPDATE recharge_list SET amount=?, mail_id=?, last_recharge_date = ? , expiry_date = ?, status = ? where phone_number = ?");  
      			ps.setInt(1,Amount);
			ps.setString(2,mailId);
			ps.setDate(3,currentDate);
			ps.setDate(4,expiryDate);
			ps.setString(5,statusKey);
			ps.setString(6,mobile);
			ps.executeUpdate();
			
		}catch(Exception e){
			out.print(e);
		}finally{
    			if (ps != null) try { ps.close(); } catch (Exception e) {}
    			if (con != null) try { con.close(); } catch (Exception e) {}
		}
	}
	else{
		try{
			ps = con.prepareStatement(  
				"insert into recharge_list(user_name,phone_number,mail_id,sim_name,amount,last_recharge_date,expiry_date,status)values(?,?,?,?,?,?,?,?)");
			ps.setString(1,name);  
      			ps.setString(2,mobile);
			ps.setString(3,mailId);
			ps.setString(4,network);
			ps.setInt(5,Amount);
			ps.setDate(6,currentDate);
			ps.setDate(7,expiryDate);
			ps.setString(8,statusKey);
			ps.execute(); 
			psForRoles = con.prepareStatement("insert into ws_server_login()values(?,?,?,?)");
			psForRoles.setString(1,mobile);
			psForRoles.setString(2,mailId);
			psForRoles.setString(3,url);
			psForRoles.setString(4,"verified");
			psForRoles.execute();
			
		}catch(Exception e){
			out.print(e);
		}finally{
    			if (ps != null) try { ps.close(); } catch (Exception e) {}
    			if (con != null) try { con.close(); } catch (Exception e) {}
			if (psForRoles != null) try {psForRoles.close(); } catch (Exception e) {}
		}	
	}

	for(int i = 0 ; i < styleFunction.length ; i ++ ){
		out.print(styleFunction[i]);
	}
	
	out.print("You have Recharged Successfully!!...Login again to view your Recharge plan detail.");
	out.print("<br><button onclick=\"window.location.href='individual.html'\">Login</button>");
	session.invalidate();
	out.close();	
  }

}