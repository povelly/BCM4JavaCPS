package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * 
 * @author Bello Velly
 *
 */
public class ManagementConnector extends AbstractConnector implements ManagementCI {

	@Override
	public void createTopic(String topic) throws Exception {
		((ManagementCI) this.offering).createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) throws Exception {
		((ManagementCI) this.offering).createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) throws Exception {
		((ManagementCI) this.offering).destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) throws Exception {
		return ((ManagementCI) this.offering).isTopic(topic);
	}

	@Override
	public String[] getTopics() throws Exception {
		return ((ManagementCI) this.offering).getTopics();
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		((ManagementCI) this.offering).unsubscribe(topic, inboundPortURI);
	}

}
