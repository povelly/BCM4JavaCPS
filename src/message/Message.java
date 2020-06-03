package message;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import fr.sorbonne_u.components.AbstractPort;
import interfaces.MessageI;

/**
 * Classe representant un Message
 * 
 * @author Bello Velly
 *
 */
public class Message implements MessageI, Serializable {

	private static final long serialVersionUID = 1L;

	private String URI;
	private TimeStamp timeStamp;
	private Properties properties;
	private Serializable payload;
	private List<String> brokers;

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
		this.brokers = new LinkedList<String>();
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

	@Override
	public MessageI addBroker(String broker_uri) throws Exception {
		brokers.add(broker_uri);
		return this;
	}

	@Override
	public Boolean containsBroker(String broker_uri) throws Exception {
		return brokers.contains(broker_uri);
	}

}
