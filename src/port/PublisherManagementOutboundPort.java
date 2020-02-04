package port;

import components.Publisher;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

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
		
		// TODO Auto-generated method stub
	}

	@Override
	public void createTopics(String[] topics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyTopic(String topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTopic(String topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		// TODO Auto-generated method stub
		
	}

}
