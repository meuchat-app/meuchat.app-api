package app.meuchat.server.model;

public class Chat {
	private Message message;
	
	public void setResponse(String response) {
		this.message = new Message(response, "author-server");
	}
	
	public Message getResponse() {
		return this.message;
	}
}
