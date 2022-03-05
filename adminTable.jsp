<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
	<head>
		<style>
			input[type="button"]{
				border: none;
				outline: none;
				height: 30px;
				background: linear-gradient(to top right, #ffff00 0%, #cc0000 100%);
				color: #fff;
				font-size: 18px;
				border-radius: 20px;
				font-weight: bold;
			
			}
	
		</style>
	</head>
<body>
<center>
<h1> User Details </h1>
<table border="1">
<tr>
	<th>Serial number</th>
	<th>User Name</th>
	<th>Mobile number</th>
	<th>Gmail Address</th>
	<th>Network</th>
	<th>Amount</th>
	<th>Last Recharge Date</th>
	<th>Expiry Date</th>
	<th>Status</th>
</tr>
<%

	Connection connection = null;
	ResultSet resultset = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rechargeapp", "root", "Suba@2000");
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM recharge_list";
		resultset = statement.executeQuery(query);
		while(resultset.next()){%>
			<tr>
				<td><%=resultset.getString("series_num")%></td>
				<td><%=resultset.getString("user_name")%></td>
				<td><%=resultset.getString("phone_number")%></td>
				<td><%=resultset.getString("mail_id")%></td>
				<td><%=resultset.getString("sim_name")%></td>
				<td><%=resultset.getString("amount")%></td>
				<td><%=resultset.getString("last_recharge_date")%></td>
				<td><%=resultset.getString("expiry_date")%></td>
				<td><%=resultset.getString("status")%></td>
			</tr>
		<%}
		connection.close();
	}
	catch (Exception e)
	{
		out.println("An error occurred.");
	}
%>

	<input type="button" id="logout" value="Logout" onclick="window.location.href='index.html'" />
	<br>
</center>
</body>
</html>