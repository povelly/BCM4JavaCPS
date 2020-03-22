package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * Port sortant Management pour Plugin
 * 
 * 
 * @author Bello Velly
 *
 */
public class ManagementOutboundPortForPlugin extends AbstractOutboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;

	public ManagementOutboundPortForPlugin(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
	}

	public ManagementOutboundPortForPlugin(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
	}

	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementCI) this.connector).createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) throws Exception {
		((ManagementCI) this.connector).createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementCI) this.connector).destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementCI) this.connector).isTopic(topic);
	}

	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementCI) this.connector).getTopics();
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return ((ManagementCI) this.connector).getPublicationPortURI();
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.connector).unsubscribe(topic, inboundPortURI);
	}

}
