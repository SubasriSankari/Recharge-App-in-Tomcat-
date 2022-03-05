
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

public class Admin extends HttpServlet {
 private static final long serialVersionUID = 1L;

 static Connection con = null;
 static PreparedStatement ps = null;
 static ResultSet rs = null;
 static String number; 

 public static void callToUpdateStatus(String number){
	try{
		ps=con.prepareStatement("UPDATE recharge_list SET status=? where phone_number=?");
		ps.setString(1,"Expired");
		ps.setString(2,number);
		ps.executeUpdate();
	}catch(Exception e){
		System.out.print(e);
	}
 }

 public static void checkForUpdates(){
	con = ConnectionDao.makeConnection();
	try{
		ps = con.prepareStatement("select phone_number from recharge_list");
		rs = ps.executeQuery();
		while(rs.next()){
			number = rs.getString(1);
			if(!ConnectionDao.checkPlanExpiredOrNot(number)){
				callToUpdateStatus(number);
			}
		}
	}catch(Exception e){
		System.out.println(e);
	}finally{
		if (rs != null) try { rs.close(); } catch (Exception e) {}
    		if (ps != null) try { ps.close(); } catch (Exception e) {}
    		if (con != null) try { con.close(); } catch (Exception e) {}
	}
 }

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter();  
	HttpSession session = request.getSession();
	boolean valid = true;
	
	String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
	valid = VerifyUtils.verify(gRecaptchaResponse,ConstantValue.SECRET_KEY);	
	if(!valid){
		out.print("<p>Error Invalid Captcha!</p>");
		RequestDispatcher rd = request.getRequestDispatcher("admin.html");  
       		rd.include(request,response);
	}
	else{
		checkForUpdates();
		RequestDispatcher rd = request.getRequestDispatcher("adminTable.jsp");
		rd.forward(request,response);
	}
 	
	out.close();
  }

}