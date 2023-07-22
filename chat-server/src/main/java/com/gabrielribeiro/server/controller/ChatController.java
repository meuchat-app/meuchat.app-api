package com.gabrielribeiro.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gabrielribeiro.server.model.Message;
import com.gabrielribeiro.server.model.MeuChat;

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
		
		MeuChat mc = new MeuPrograma();
		
		mc.receiveMessage(message);
		
		sendFilteredPrivateMessages(mc.getChat().getResponse(), "/user");
	}
	
	public void sendFilteredPrivateMessages(Message message, String destination) {
		simpMessagingTemplate.convertAndSend(destination, message);

		System.out.println(message.toString());
	}

}
