package app.meuchat.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import app.meuchat.server.model.Message;
import app.meuchat.server.model.MeuChat;

import jakarta.servlet.ServletContext;

@Controller
public class ChatController {

	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/private-message")
	private void receivePrivateMessage(@Payload Message message) {
//		MeuChat mc = (MeuChat) servletContext.getAttribute("meuchat");
		
		MeuChat mc = new Eco();
		
		try {
			mc.receiveMessage(message);
			sendFilteredPrivateMessages(mc.getChat().getResponse(), "/user");
		} catch (Exception e) {
			
			e.printStackTrace();
			sendFilteredPrivateMessages(new Message("Ocorreu um erro :(", "author-server"), "/user");
			return;
		}
		
		
	}
	
	public void sendFilteredPrivateMessages(Message message, String destination) {
		simpMessagingTemplate.convertAndSend(destination, message);

		System.out.println(message.toString());
	}

}
