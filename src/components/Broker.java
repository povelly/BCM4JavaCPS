package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageFilterI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;
import interfaces.ReceptionCI;
import interfaces.SubscriptionImplementationI;
import port.BrokerManagementInboundPort;
import port.BrokerPublicationInboundPort;
import port.BrokerReceptionOutboundPort;

public class Broker extends AbstractComponent implements ManagementImplementationI, SubscriptionImplementationI, PublicationsImplementationI {

	protected BrokerReceptionOutboundPort brop;
	protected BrokerManagementInboundPort bmip;
	protected BrokerPublicationInboundPort bpip;

	public Broker(String bropURI, String bmipURI, String bpipURI) throws Exception {
		super(1, 0);
		this.addRequiredInterface(ReceptionCI.class);
		this.addOfferedInterface(ManagementCI.class);
		this.addOfferedInterface(PublicationCI.class);

		this.brop = new BrokerReceptionOutboundPort(bropURI, this);
		this.brop.publishPort();

		this.bmip = new BrokerManagementInboundPort(bmipURI, this);
		this.bmip.publishPort();

		this.bpip = new BrokerPublicationInboundPort(bpipURI, this);
		this.bpip.publishPort();
	}
	
	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			brop.unpublishPort();
			bmip.unpublishPort();
			bpip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			brop.unpublishPort();
			bmip.unpublishPort();
			bpip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
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
	public void publish(MessageI m, String topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		// TODO Auto-generated method stub
		
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
