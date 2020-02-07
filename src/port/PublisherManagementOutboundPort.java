package port;

import components.Publisher;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * Port de sortie du Publisher pour l'interface composant ManagementCI
 * 
 * @author Bello Velly
 *
 */
public class PublisherManagementOutboundPort extends AbstractOutboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;

	public PublisherManagementOutboundPort(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
		assert owner instanceof Publisher;
	}

	public PublisherManagementOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
		assert owner instanceof Publisher;
	}

	@Override
	public void createTopic(String topic) {
		((ManagementCI) this.connector).createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) {
		((ManagementCI) this.connector).createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) {
		((ManagementCI) this.connector).destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) {
		return ((ManagementCI) this.connector).isTopic(topic);
	}

	@Override
	public String[] getTopics() {
		return ((ManagementCI) this.connector).getTopics();
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) {
		((ManagementCI) this.connector).subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		((ManagementCI) this.connector).subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		((ManagementCI) this.connector).subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		((ManagementCI) this.connector).modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		((ManagementCI) this.connector).unsubscribe(topic, inboundPortURI);
	}

}
