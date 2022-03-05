
 
import java.io.PrintWriter; 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class ServerConfigurator extends ServerEndpointConfig.Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest req, HandshakeResponse res){
		HttpSession session = (HttpSession)req.getHttpSession();
		sec.getUserProperties().put("number",(String)((HttpSession) req.getHttpSession()).getAttribute("number"));
	}

}