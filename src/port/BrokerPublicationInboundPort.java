package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.MessageI;
import interfaces.PublicationCI;

public class BrokerPublicationInboundPort extends AbstractInboundPort implements PublicationCI {

	private static final long serialVersionUID = 1L;

	public BrokerPublicationInboundPort(ComponentI owner) throws Exception {
		super(PublicationCI.class, owner);
		assert owner instanceof Broker;
	}
	
	public BrokerPublicationInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
		assert owner instanceof Broker;
	}

	@Override
	public void publish(MessageI m, String topic) {
		((Broker) owner).publish(m, topic);
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		((Broker) owner).publish(m, topics);
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		((Broker) owner).publish(ms, topic);
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		((Broker) owner).publish(ms, topics);
	}


}
