
import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.EndpointConfig;
import javax.websocket.server.ServerEndpoint;
import javax.servlet.http.HttpSession;
 
@ServerEndpoint(value="/websocketendpoint",configurator=ServerConfigurator.class)
public class WsServer {
   
    static Connection con = null;
    static Set<Session> usersSet = Collections.synchronizedSet(new HashSet<Session>());  

    @OnOpen
    public void onOpen(Session userSession, EndpointConfig epc){
	userSession.getUserProperties().put("number", epc.getUserProperties().get("number"));
	usersSet.add(userSession);
    }
     
    @OnMessage
    public String onMessage(String recipient,Session userSession){
	boolean check = false;
	int looping = 0;
	String mobile = (String) userSession.getUserProperties().get("number");
	con = ConnectionDao.makeConnection();
	try{
		while(looping<30){
			check = ConnectionDao.CheckVerifiedOrNot(mobile);
			if(check){
				String result = String.valueOf(check);
				return result;
			}
			Thread.sleep(10*1000);
			looping ++;
		}
	}catch(InterruptedException e){
		e.printStackTrace();
	}
	String result = String.valueOf(check);
	return result;
    }

    @OnClose
    public void onClose(Session userSession){
	usersSet.remove(userSession);
        System.out.println("Close Connection ...");
    }
 
    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }
 
}