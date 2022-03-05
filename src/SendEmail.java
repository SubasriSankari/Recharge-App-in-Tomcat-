
import java.sql.*;
import java.lang.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;
import org.apache.commons.codec.digest.DigestUtils;


public class SendEmail
{
	static String sender = "subaorganizer@gmail.com";
	static String host = "smtp.gmail.com";
	static String user = "subaorganizer@gmail.com";
	static String password = "Suba@2000";
	static Connection con = null;

	static String getOTP(int length){

		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[length];
		
		for(int i = 0 ; i < length ; i ++ ){
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		String getOtp = new String(otp);
		return getOtp;
	}
	
	public static String requestToSend(String name, String recipient)
	{
		String otp = null ;
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication( user , password );
			}
		});	
	
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			otp = getOTP(6);
			message.setSubject("OTP Verification code");
			message.setContent("<p><h3>Welcome " + name + "!!</h3></p><br><h4>Your otp is: " + otp + "...Enter this otp to confirm</h4><br><br>Thanks for using our app:)<br><p>Don't Share this to anyone</p>","text/html");
			Transport.send(message);
			System.out.println("Mail successfully sent");

		}catch (MessagingException mex){
			mex.printStackTrace();
		}
		return otp;
	}
	
	public static void requestToAlertMessage(String recipient, String number){
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication( user , password );
			}
		});
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Alert!!    Your Recharge Plan will Expire");
			message.setContent("<p>Hi,<br><br>     Your Recharge plan will expires on today for the Mobile number is "+number+".To Recharge now <a href='http://localhost:8080/Recharge_App/newUser.html'>click here</a><br><br>Thanks for using our App:)</p>","text/html");
			Transport.send(message);
			System.out.println("Mail successfully sent");
		}catch(MessagingException mex){
			mex.printStackTrace();
		}
	}

	public static void link(String recipient, String url)
	{
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication( user , password );
			}
		});	
	
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Verify To Login");
			message.setContent("<p style=\"font-weight:bold;\">Hello,</p><br><p>Please click this secret link <a href=\"http://localhost:8080/Recharge_App/Verified\">"+url+"</a> to proceed further.</p><p>This link will expires in 5mins...</p><p>Don't share this link to anyone!!</p><br><p style=\"font-weight:bold;\">Thanks for using Our Recharge Application :)</p>","text/html");
			Transport.send(message);
			System.out.println("Mail successfully sent");

		}catch (MessagingException mex){
			mex.printStackTrace();
		}
	}

	public static String generateURL(String mobile){

		String url = "http://localhost:8080/Recharge_App/Verified?token=";
		String ts = String.valueOf(System.currentTimeMillis());

    		String rand = UUID.randomUUID().toString();

    		String name = DigestUtils.sha1Hex(ts + rand);

		String generatedURL = url+name;
		con = ConnectionDao.makeConnection();
		ConnectionDao.AddToDB(mobile,generatedURL);
		return generatedURL;
	}
}
