package app.meuchat.server.controller;

import app.meuchat.server.model.MeuChat;
import app.meuchat.server.model.Message;

public class Eco extends MeuChat {
	

	@Override
	public void receiveMessage(Message msg) {
		
		this.chat.setResponse("Alo mundo");
		if(msg.getMessage() != null) {
			this.chat.setResponse(msg.getMessage());
		}
	}

}
