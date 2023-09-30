package app.meuchat.server.service;

import org.springframework.stereotype.Service;

import app.meuchat.server.model.Message;
import app.meuchat.server.model.MeuChat;

@Service
public class Eco extends MeuChat {

    @Override
    public void receiveMessage(Message msg) {
       chat.setResponse(msg.getMessage());
    }
    

    
}
