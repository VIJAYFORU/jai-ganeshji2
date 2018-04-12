package io.websockets;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chatroomServerEndpoint", configurator = ChatroomServerConfigurator.class)
public class ChatroomServerEndpoint {
  	private static Set<Session> chatroomusers = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void handleOpen(EndpointConfig endpointconfig, Session userSession)

	{
		userSession.getUserProperties().put("username", endpointconfig.getUserProperties().get("username"));
		chatroomusers.add(userSession);
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		String username = (String) userSession.getUserProperties().get("username");

		if (username != null) {
			chatroomusers.stream().forEach(x -> {
				try {x.getBasicRemote().sendText(buildJsonData(username, message));}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

	}

	@OnClose
	public void handleClose(Session userSession) {

		chatroomusers.remove(userSession);

		System.out.println("reomved ssession" + userSession);
	}

	@OnError
	public void handleError(Throwable t) {

 	}

	private String buildJsonData(String username, String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username+ ": "+ message).build();
		StringWriter stringwriter = new StringWriter();

		try (JsonWriter jsonwriter = Json.createWriter(stringwriter))

		{
			jsonwriter.write(jsonObject);
		}
		return stringwriter.toString();

	}

}
