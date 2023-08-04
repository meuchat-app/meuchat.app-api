package app.meuchat.server.model;


public class Message {
	
	private String message;
	private String authorId;
	
	public Message(String message, String authorId) {
		this.message = message;
		this.authorId = authorId;
	}
	
	public Message() {}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}
	
	

}
