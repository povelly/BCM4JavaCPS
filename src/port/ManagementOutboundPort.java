package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageFilterI;
import interfaces.SubscriptionImplementationI;

/**
 * Port sortant pour l'interface ManagementCI
 * 
 * 
 * @author Bello Velly
 *
 */
public class ManagementOutboundPort extends AbstractOutboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;

	public ManagementOutboundPort(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
	}

	public ManagementOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
	}

	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementImplementationI) this.connector).createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) throws Exception {
		((ManagementImplementationI) this.connector).createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementImplementationI) this.connector).destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementImplementationI) this.connector).isTopic(topic);
	}

	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementImplementationI) this.connector).getTopics();
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return ((ManagementImplementationI) this.connector).getPublicationPortURI();
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((SubscriptionImplementationI) this.connector).unsubscribe(topic, inboundPortURI);
	}

}
