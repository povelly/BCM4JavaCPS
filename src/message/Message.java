package message;

import java.io.Serializable;

import interfaces.MessageI;

/**
 * Classe representant un Message
 * 
 * @author Bello Velly
 *
 */
public class Message implements MessageI {

	private static final long serialVersionUID = 1L;

	private String URI;
	private TimeStamp timeStamp;
	private Properties properties;
	private Serializable payload;

	public Message(Properties properties, Serializable payload) {
		this.URI = "URI1"; // TODO
		this.timeStamp = new TimeStamp(); // TODO
		this.properties = properties;
		this.payload = payload;
	}

	@Override
	public String getURI() {
		return URI;
	}

	@Override
	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public Serializable getPayload() {
		return payload;
	}

}
