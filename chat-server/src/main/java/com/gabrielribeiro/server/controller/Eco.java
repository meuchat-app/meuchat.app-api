package com.gabrielribeiro.server.controller;

import com.gabrielribeiro.server.model.Message;
import com.gabrielribeiro.server.model.MeuChat;

public class Eco extends MeuChat {
	

	@Override
	public void receiveMessage(Message msg) {
		if(msg.getMessage() != null) {
			this.chat.setResponse("Eco: "+msg.getMessage());
		}
	}

}
