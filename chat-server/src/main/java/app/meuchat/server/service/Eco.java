package app.meuchat.server.service;

import app.meuchat.server.model.MeuChat;
import app.meuchat.server.model.Message;
import org.springframework.stereotype.Service;

@Service
public class Eco extends MeuChat {

		

	@Override
	public void receiveMessage(Message msg) {
		
		if(msg.getMessage() != null) {
			this.chat.setResponse(msg.getMessage());
		}
	}

}
