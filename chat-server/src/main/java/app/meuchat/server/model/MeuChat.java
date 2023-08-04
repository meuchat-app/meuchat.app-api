package app.meuchat.server.model;


public abstract class MeuChat {
	
	protected Chat chat = new Chat();
	
	public MeuChat(){}
	
	public Chat getChat() {
		return this.chat;
	}
	
	public abstract void receiveMessage(Message msg);
	
	

}
