package com.gabrielribeiro.server.controller;

import com.gabrielribeiro.server.model.Message;
import com.gabrielribeiro.server.model.MeuChat;

public class MeuPrograma extends MeuChat {
	

	@Override
	public void receiveMessage(Message msg) {
		
		this.chat.setResponse("Alô mundo!");
	}

}
