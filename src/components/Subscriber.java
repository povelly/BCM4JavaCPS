package components;

import connectors.ManagementConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.MessageI;
import interfaces.ReceptionCI;
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
public class Subscriber extends AbstractComponent implements ReceptionCI {

	// ports du composant
	protected SubscriberManagementOutboundPort smop;
	protected SubscriberReceptionInboundPort srip;
	// uris pour les connections
	protected String mipServerUri; // ManagementInboundPort du Broker

	protected Subscriber(String mipServerUri) throws Exception {
		super(1, 0);

		// verifications
		assert mipServerUri != null;

		// creation ports
		this.smop = new SubscriberManagementOutboundPort(this);
		this.smop.publishPort();

		this.srip = new SubscriberReceptionInboundPort(this);
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
	public void execute() throws Exception {
		super.execute();
		System.out.println("subscribed");
		// souscrit a un topic du broker en passant son port pour pouvoir etre contacté
		smop.subscribe("topic1", srip.getPortURI());
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		// deconnection des ports clients
		try {
			this.doPortDisconnection(smop.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException();
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		// deconnection des ports clients
		try {
			this.doPortDisconnection(smop.getPortURI());
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

	@Override
	public void acceptMessage(MessageI m) {
		try {
			System.out.println("PortURI : " + srip.getPortURI() + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		try {
			for (MessageI m : ms)
				System.out.println("PortURI : " + srip.getPortURI() + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
