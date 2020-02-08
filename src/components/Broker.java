package components;

import java.util.Map;

import connectors.ReceptionConnector;
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

/**
 * Classe representant le composant courtier
 * 
 * @author Bello Velly
 */

@RequiredInterfaces(required = { ReceptionCI.class })
@OfferedInterfaces(offered = { ManagementCI.class, PublicationCI.class })
public class Broker extends AbstractComponent
		implements ManagementImplementationI, SubscriptionImplementationI, PublicationsImplementationI {

	// ports du composant
	protected BrokerReceptionOutboundPort brop;
	protected BrokerManagementInboundPort bmip;
	protected BrokerManagementInboundPort bmip2;
	protected BrokerPublicationInboundPort bpip;
	// uris pour les connections
	protected String ripServerURI;

	protected Map<String, String> subscribtions; // TODO ajouter filtre

	public Broker(String bmipURI, String bmip2URI, String bpipURI, String ripServerURI) throws Exception {
		super(1, 0);
		// verifications
		assert bmipURI != null;
		assert bmip2URI != null;
		assert bpipURI != null;
		assert ripServerURI != null;

		// creation ports
		this.brop = new BrokerReceptionOutboundPort(this);
		this.brop.publishPort();

		this.bmip = new BrokerManagementInboundPort(bmipURI, this);
		this.bmip.publishPort();

		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, this);
		this.bmip2.publishPort();

		this.bpip = new BrokerPublicationInboundPort(bpipURI, this);
		this.bpip.publishPort();

		// uris pour les connections
		this.ripServerURI = ripServerURI;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void start() throws ComponentStartException {
		super.start();
		try {
			this.doPortConnection(this.brop.getPortURI(), ripServerURI, ReceptionConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			this.doPortDisconnection(brop.getPortURI());
			this.doPortDisconnection(bmip.getPortURI());
			this.doPortDisconnection(bmip2.getPortURI());
			this.doPortDisconnection(bpip.getPortURI());
			brop.doDisconnection();
			bmip.doDisconnection();
			bmip2.doDisconnection();
			bpip.doDisconnection();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			this.doPortDisconnection(brop.getPortURI());
			this.doPortDisconnection(bmip.getPortURI());
			this.doPortDisconnection(bmip2.getPortURI());
			this.doPortDisconnection(bpip.getPortURI());
			brop.doDisconnection();
			bmip.doDisconnection();
			bmip2.doDisconnection();
			bpip.doDisconnection();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	@Override
	public void finalise() throws Exception {
		// on dépublie les ports
		brop.unpublishPort();
		bmip.unpublishPort();
		bmip2.unpublishPort();
		bpip.unpublishPort();
		super.finalise();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	// TODO implementer les services

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
