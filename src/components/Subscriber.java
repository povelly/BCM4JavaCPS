package components;

import connectors.ManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;
import port.SubscriberManagementOutboundPort;
import port.SubscriberReceptionInboundPort;

/**
 * Classe representant le composant souscriveur
 * 
 * @author Bello Velly
 *
 */

@RequiredInterfaces(required = { ManagementCI.class })
@OfferedInterfaces(offered = { ReceptionCI.class })
public class Subscriber extends AbstractComponent implements ManagementImplementationI, ReceptionImplementationI {

	// ports du composant
	protected SubscriberManagementOutboundPort smop;
	protected SubscriberReceptionInboundPort srip;
	// uris pour les connections
	protected String mipServerUri;

	protected Subscriber(String sripURI, String mipServerUri) throws Exception {
		super(1, 0);

		// verifications
		assert sripURI != null;
		assert mipServerUri != null;

		// creation ports
		this.smop = new SubscriberManagementOutboundPort(this);
		this.smop.publishPort();

		this.srip = new SubscriberReceptionInboundPort(sripURI, this);
		this.srip.publishPort();

		// uris pour les connections
		this.mipServerUri = mipServerUri;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void start() throws ComponentStartException {
		super.start();
		// connection des ports
		try {
			this.doPortConnection(smop.getPortURI(), mipServerUri, ManagementConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException();
		}
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		// deconnection des ports
		try {
			this.doPortDisconnection(smop.getPortURI());
			this.doPortDisconnection(srip.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException();
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		// deconnection des ports
		try {
			this.doPortDisconnection(smop.getPortURI());
			this.doPortDisconnection(srip.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException();
		}
		super.shutdownNow();
	}

	@Override
	public void finalise() throws Exception {
		// depublication des ports
		smop.unpublishPort();
		srip.unpublishPort();
		super.finalise();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	// TODO implementer les services

	@Override
	public void acceptMessage(MessageI m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		// TODO Auto-generated method stub

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

}
