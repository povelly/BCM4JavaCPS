package message;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

import fr.sorbonne_u.components.AbstractPort;
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
		this.URI = AbstractPort.generatePortURI();
		this.timeStamp = new TimeStamp();
		Date date = new Date();
		timeStamp.setTime(date.getTime());
		try {
			timeStamp.setTimeStamper(InetAddress.getLocalHost().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
