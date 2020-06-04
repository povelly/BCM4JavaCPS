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

	/**
	 * uri du message
	 */
	private String URI;

	/**
	 * Date du message
	 */
	private TimeStamp timeStamp;

	/**
	 * Propriétés du message
	 */
	private Properties properties;

	/**
	 * contenu du message
	 */
	private Serializable payload;

	/**
	 * List d'uris des Broker qui ont déjà accepter le message
	 */
	private List<String> brokers;

	/**
	 * constructeur de Message
	 * 
	 * @param properties @see {@link #properties}
	 * @param payload    @see {@link #payload}
	 * 
	 */
	public Message(Properties properties, Serializable payload) {
		this.URI = AbstractPort.generatePortURI();
		this.timeStamp = new TimeStamp();
		this.properties = properties;
		this.payload = payload;
		this.brokers = new LinkedList<String>();
		// on récupère le date actuelle
		Date date = new Date();
		timeStamp.setTime(date.getTime());
		try {
			timeStamp.setTimeStamper(InetAddress.getLocalHost().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.MessageI#getURI()
	 */
	@Override
	public String getURI() {
		return URI;
	}

	/**
	 * @see interfaces.MessageI#getTimeStamp()
	 */
	@Override
	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @see interfaces.MessageI#getProperties()
	 */
	@Override
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @see interfaces.MessageI#getPayloard()
	 */
	@Override
	public Serializable getPayload() {
		return payload;
	}

	/**
	 * @see interfaces.MessageI#addBroker(String)
	 */
	@Override
	public MessageI addBroker(String broker_uri) throws Exception {
		brokers.add(broker_uri);
		return this;
	}

	/**
	 * @see interfaces.MessageI#containsBroker(String)
	 */
	@Override
	public Boolean containsBroker(String broker_uri) throws Exception {
		return brokers.contains(broker_uri);
	}

}
