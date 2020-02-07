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

	protected SubscriberManagementOutboundPort smop;
	protected SubscriberReceptionInboundPort srip;

	protected Subscriber(String smopURI, String sripURI) throws Exception {
		super(1, 0);

		// verifications
		assert smopURI != null;
		assert sripURI != null;

		// creation ports
		this.smop = new SubscriberManagementOutboundPort(smopURI, this);
		this.srip = new SubscriberReceptionInboundPort(sripURI, this);
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	// TODO connecter les port sortants
	@Override
	public void start() throws ComponentStartException {
		super.start();
		try {
			this.doPortConnection(smop.getPortURI(), ???, ManagementConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException();
		}
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			this.doPortDisconnection(smop.getPortURI());
			this.doPortDisconnection(srip.getPortURI());
			smop.doDisconnection();
			srip.doDisconnection();
		} catch (Exception e) {
			throw new ComponentShutdownException();
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			this.doPortDisconnection(smop.getPortURI());
			this.doPortDisconnection(srip.getPortURI());
			smop.doDisconnection();
			srip.doDisconnection();
		} catch (Exception e) {
			throw new ComponentShutdownException();
		}
		super.shutdownNow();
	}

	@Override
	public void finalise() throws Exception {
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
