package components;

import java.util.Map;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
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

@RequiredInterfaces(required = {ReceptionCI.class})
@OfferedInterfaces(offered = {ManagementCI.class, PublicationCI.class})
public class Broker extends AbstractComponent
		implements ManagementImplementationI, SubscriptionImplementationI, PublicationsImplementationI {

	protected BrokerReceptionOutboundPort brop;
	protected BrokerManagementInboundPort bmip;
	protected BrokerPublicationInboundPort bpip;

	protected String bripURI;
	
	protected Map<String, String> subscribtions; // TODO ajouter filtre

	public Broker(String bripURI, String bmipURI, String bpipURI) throws Exception { // TODO verif pour bmipURI et bpipURI
		super(1, 0);
		assert bripURI != null;

		this.bripURI = bripURI;

		this.brop = new BrokerReceptionOutboundPort(this);
		this.brop.publishPort();

		this.bmip = new BrokerManagementInboundPort(bmipURI, this);
		this.bmip.publishPort();

		this.bpip = new BrokerPublicationInboundPort(bpipURI, this);
		this.bpip.publishPort();
	}

	@Override
	public void start() throws ComponentStartException {
		super.start();
//		TODO + doPortDisconnection dans finalize ou shutdown (on connecte seulement les ports des interfaces requises)
//		try {
//			this.doPortConnection(this.brop.getPortURI(), this.bripURI, ReceptionConnector.class.getCanonicalName());
//		} catch (Exception e) {
//			throw new ComponentStartException(e);
//		}
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
		subscribtions.put(inboundPortURI, topic);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		for (String topic : topics)
			subscribtions.put(inboundPortURI, topic);
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
		subscribtions.remove(inboundPortURI);
	}

}
