package message;
import java.io.Serializable;

import interfaces.MessageI;

public class Message implements MessageI {

	private static final long serialVersionUID = 1L;
	
	private String URI;
	private TimeStamp timeStamp;
	private Properties properties;
	private Serializable payload;
	
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
