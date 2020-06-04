package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageFilterI;
import interfaces.SubscriptionImplementationI;

/**
 * Port sortant Management pour Plugin
 * 
 * 
 * @author Bello Velly
 *
 */
public class ManagementOutboundPortForPlugin extends AbstractOutboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de ManagementOutboundPortForPlugin
	 * 
	 * @param owner composant qui possède le port
	 */
	public ManagementOutboundPortForPlugin(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
	}

	/**
	 * Constructeur de ManagementOutboundPortForPlugin
	 * 
	 * @param uri   uri du port
	 * @param owner composant qui possède le port
	 */
	public ManagementOutboundPortForPlugin(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
	}

	/**
	 * @see interfaces.ManagementImplementationI#createTopic(String)
	 */
	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementImplementationI) this.connector).createTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#createTopics(String[])
	 */
	@Override
	public void createTopics(String[] topics) throws Exception {
		((ManagementImplementationI) this.connector).createTopics(topics);
	}

	/**
	 * @see interfaces.ManagementImplementationI#destroyTopic(String)
	 */
	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementImplementationI) this.connector).destroyTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#isTopic(String)
	 */
	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementImplementationI) this.connector).isTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#getTopics()
	 */
	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementImplementationI) this.connector).getTopics();
	}

	/**
	 * @see interfaces.ManagementImplementationI#getPublicationPortURI()
	 */
	@Override
	public String getPublicationPortURI() throws Exception {
		return ((ManagementImplementationI) this.connector).getPublicationPortURI();
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, String)
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topic, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String[], String)
	 */
	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topics, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, MessageFilterI,
	 *      String)
	 */
	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topic, filter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#modifyFilter(String,
	 *      MessageFilterI, String)
	 */
	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).modifyFilter(topic, newFilter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#unsubscribe(String, String)
	 */
	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).unsubscribe(topic, inboundPortURI);
	}

}
