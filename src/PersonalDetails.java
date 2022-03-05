

import java.sql.*;

public class PersonalDetails{
	private int seriesNumber; 
	private String userName;
	private String simName;
	private String phoneNumber;
	private String mailId;
	private int amount;
	private Date lastRechargeDate;
	private Date expiryDate;
	private String status;

	public int getSeriesNumber(){
		return seriesNumber;
	}
	public void setSeriesNumber(int seriesNumber){
		this.seriesNumber = seriesNumber;
	}

	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount){
		this.amount = amount;
	}

	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getMailId(){
		return mailId;
	}
	public void setMailId(String mailId){
		this.mailId = mailId;
	}

	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}

	public String getSimName(){
		return simName;
	}
	public void setSimName(String simName){
		this.simName = simName;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public Date getLastRechargeDate(){
		return lastRechargeDate;
	}
	public void setLastRechargeDate(Date lastRechargeDate){
		this.lastRechargeDate = lastRechargeDate;
	}

	public Date getExpiryDate(){
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate){
		this.expiryDate = expiryDate;
	}
	
	
	
}