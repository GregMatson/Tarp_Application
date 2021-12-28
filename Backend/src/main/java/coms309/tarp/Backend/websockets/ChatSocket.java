package coms309.tarp.Backend.websockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/{username}")
public class ChatSocket {

	private static MessageRepository msgRepo;
	
	@Autowired
	public void setMessageRepository(MessageRepository repo) {
		msgRepo = repo;
	}
	
	private static Map < Session, String > sessionUsernameMap = new Hashtable<>();
	private static Map < String, Session > usernameSessionMap = new Hashtable<>();
	
	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Client connected");
			
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
		
		sendMessageToParticularUser(username, getChatHistory());
		
		String message="User: " + username + " has joined the chat\n";
		broadcast(message);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		logger.info("Message Received: " + message); 
		String username = sessionUsernameMap.get(session); 
		if (message.startsWith("@")) {
			String destUsername = message.split(" ")[0].substring(1);	
			String realMessage = message.substring(message.lastIndexOf(" ") + 1);
			sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + realMessage);
			sendMessageToParticularUser(username, "[DM] " + username + ": " + message);
		}
		else {
			broadcast(username + ": " + message);
		}
		
		msgRepo.save(new Message(username, message));
	}
	
	@OnClose
	public void onClose(Session session) {
		logger.info("Client disconnected");
		
		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
		
		String message = username + " disconnected";
		broadcast(message);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("An error has occurred");
		throwable.printStackTrace();
	}
	
	private void sendMessageToParticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}
	
	private void broadcast(String message) {
		sessionUsernameMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});
	}
	
	private String getChatHistory() {
		List<Message> messages = msgRepo.findAll();
		
		StringBuilder sb = new StringBuilder();
		if(messages != null && messages.size() != 0) {
			for (Message message : messages) {
				sb.append(message.getUserName() + ": " + message.getContent() + "\r\n");
			}
		}
		return sb.toString();
	}

}
