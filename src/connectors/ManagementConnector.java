package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * Connecteur pour l'interface composant ManagementCI
 * 
 * @author Bello Velly
 *
 */
public class ManagementConnector extends AbstractConnector implements ManagementCI {

	/**
	 * @see interfaces.ManagementImplementationI#createTopic(String)
	 */
	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementCI) this.offering).createTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#createTopics(String[])
	 */
	@Override
	public void createTopics(String[] topics) throws Exception {
		((ManagementCI) this.offering).createTopics(topics);
	}

	/**
	 * @see interfaces.ManagementImplementationI#destroyTopic(String)
	 */
	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementCI) this.offering).destroyTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#isTopic(String)
	 */
	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementCI) this.offering).isTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#getTopics()
	 */
	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementCI) this.offering).getTopics();
	}

	/**
	 * @see interfaces.ManagementImplementationI#getPublicationPortURI()
	 */
	@Override
	public String getPublicationPortURI() throws Exception {
		return ((ManagementCI) this.offering).getPublicationPortURI();
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, String)
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topic, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String[], String)
	 */
	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topics, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, MessageFilterI,
	 *      String)
	 */
	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topic, filter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#modifyFilter(String,
	 *      MessageFilterI, String)
	 */
	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).modifyFilter(topic, newFilter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#unsubscribe(String, String)
	 */
	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).unsubscribe(topic, inboundPortURI);
	}

}
