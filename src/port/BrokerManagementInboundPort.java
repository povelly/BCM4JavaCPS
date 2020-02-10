package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * Port d'entrée du Broker pour l'interface composant ManagementCI
 * 
 * @author Bello Velly
 *
 */
public class BrokerManagementInboundPort extends AbstractInboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;

	public BrokerManagementInboundPort(ComponentI owner) throws Exception {
		super(ManagementCI.class, owner);
		assert owner instanceof Broker;
	}

	public BrokerManagementInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
		assert owner instanceof Broker;
	}

	// TODO modifier les méthodes pour quelles fassent des handle request synchrone
	// / asynchrone en fonction

	@Override
	public void createTopic(String topic) {
		((Broker) owner).createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) {
		((Broker) owner).createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) {
		((Broker) owner).destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) {
		return ((Broker) owner).isTopic(topic);
	}

	@Override
	public String[] getTopics() {
		return ((Broker) owner).getTopics();
	}
	
	@Override
	public String getPublicationPortURI() throws Exception {
		return ((Broker) owner).getPublicationPortURI();
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) {
		((Broker) owner).subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		((Broker) owner).subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		((Broker) owner).subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		((Broker) owner).modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		((Broker) owner).unsubscribe(topic, inboundPortURI);
	}

}
