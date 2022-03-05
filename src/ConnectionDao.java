

import java.sql.*;
import java.util.*;
import java.text.*;

public class ConnectionDao {
	
	static Connection con = null;
	static PreparedStatement ps;
	//static ResultSet rs = null ;

	public static boolean checkIsToday(java.sql.Date sqlDate){
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date expireDate = new java.util.Date(sqlDate.getTime());
		java.util.Date currDate = new java.util.Date();
		if(formatter.format(expireDate).equals(formatter.format(currDate))){
			return true;
		}
		return false;
	}	

	public static boolean checkExpired(java.sql.Date sqlDate){
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date expireDate = new java.util.Date(sqlDate.getTime());
		java.util.Date currDate = new java.util.Date();
		if(expireDate.before(currDate)){
			return false;
		}
		return true;
	}

	public static Connection makeConnection(){
		//boolean status = false;
		try{
			//Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rechargeapp","root","Suba@2000");
		}catch(Exception e){
			System.out.print(e);
		}
		return con;
	
 	}

	public static boolean numberAndUserNameExistOrNot(String name,String mobile,String gmail){	
		boolean status = false;
		try{
			ps=con.prepareStatement("select * from recharge_list where phone_number=? and user_name=? and mail_id=?");  
			ps.setString(1,mobile);
			ps.setString(2,name); 
			ps.setString(3,gmail);   
			ResultSet rs=ps.executeQuery();  
			status=rs.next();	
		}catch(Exception e){
			System.out.println(e);			
		}
		return status;
		
	}
	
	public static boolean numberAndUserNameAndNetworkExistOrNot(String name,String mobile,String network,String mail){	
		boolean status = false;
		try{
			ps=con.prepareStatement("select * from recharge_list where phone_number=? and user_name=? and sim_name=? and mail_id=?");  
			ps.setString(1,mobile);
			ps.setString(2,name); 
			ps.setString(3,network); 
			ps.setString(4,mail);  
			ResultSet rs=ps.executeQuery();  
			status=rs.next();	
		}catch(Exception e){
			System.out.println(e);			
		}
		return status;
	}


	public static boolean numberExistOrNot(String mobile){
		boolean status = false;
		try{
			ps=con.prepareStatement("select * from recharge_list where phone_number=?");  
			ps.setString(1,mobile);   
			ResultSet rs=ps.executeQuery();  
			status=rs.next();	
		}catch(Exception e){
			System.out.println(e);			
		}
		return status;
	}

	public static boolean checkPlanExpiredOrNot(String mobile){
		boolean status = false;
		try{
			ps = con.prepareStatement("select expiry_date from recharge_list where phone_number=?");
			ps.setString(1,mobile);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				java.sql.Date date = rs.getDate("expiry_date");
				//System.out.println(date);
				if(checkExpired(date) || checkIsToday(date)){
					status = true;
				}
			}
		}catch(Exception e){
			System.out.print(e);
		}
		return status;
	}
	
	public static void AddToDB(String mobile, String url){
		try{
			ps = con.prepareStatement("update ws_server_login SET token=?,verification=? where mobile=?");
			ps.setString(1,url);
			ps.setString(2,"required");
			ps.setString(3,mobile);
			ps.execute();

		}catch(Exception e){
			System.out.print(e);
		}
	}
	
	public static void AlterTable(String mobile){
		try{
			ps=con.prepareStatement("update ws_server_login SET verification=? where mobile=?");
			ps.setString(1,"verified");
			ps.setString(2,mobile);
			ps.execute();
		}catch(Exception e){
			System.out.print(e);
		}
	}


	public static boolean CheckVerifiedOrNot(String mobile){
		boolean status = false;	
		try{
			ps=con.prepareStatement("select verification from ws_server_login where mobile=?");
			ps.setString(1,mobile);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String checking = rs.getString(1);
				if(checking.equals("verified")){
					status = true;
				}
			}
		}catch(Exception e){
			System.out.print(e);
		}
		return status;
	}		
}
