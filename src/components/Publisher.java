package components;

import connectors.ManagementConnector;
import connectors.PublicationConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;
import message.Message;
import port.PublisherManagementOutboundPort;
import port.PublisherPublicationOutboundPort;

/**
 * Classe representant le composant publieur
 * 
 * @author Bello Velly
 *
 */

@RequiredInterfaces(required = { PublicationCI.class, ManagementCI.class })
public class Publisher extends AbstractComponent implements PublicationsImplementationI {

	// ports du composant
	protected PublisherPublicationOutboundPort ppop;
	protected PublisherManagementOutboundPort pmop;
	// uris pour les connections
	protected String pipServerURI;
	protected String mipServerURI;

	protected Publisher(String pipServerURI, String mipServerURI) throws Exception {
		super(1, 0);

		// verifications
		assert pipServerURI != null;
		assert mipServerURI != null;

		// creations ports
		this.ppop = new PublisherPublicationOutboundPort(this);
		this.ppop.publishPort();

		this.pmop = new PublisherManagementOutboundPort(this);
		this.pmop.publishPort();

		// uris pour les connections
		this.pipServerURI = pipServerURI;
		this.mipServerURI = mipServerURI;
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
			this.doPortConnection(this.ppop.getPortURI(), pipServerURI, PublicationConnector.class.getCanonicalName());
			this.doPortConnection(this.pmop.getPortURI(), mipServerURI, ManagementConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}
	
	@Override
	public void execute() throws Exception {
		super.execute();
		
		this.publish(new Message(null, "msg1"), "topic1");
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		// deconnection des ports
		try {
			this.doPortDisconnection(this.ppop.getPortURI());
			this.doPortDisconnection(this.pmop.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		// deconnection des ports
		try {
			this.doPortDisconnection(this.ppop.getPortURI());
			this.doPortDisconnection(this.pmop.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	@Override
	public void finalise() throws Exception {
		// depublie les ports
		ppop.unpublishPort();
		pmop.unpublishPort();
		super.finalise();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void publish(MessageI m, String topic) {
		try {
			((PublicationConnector) ppop.getConnector()).publish(m, topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		try {
			((PublicationConnector) ppop.getConnector()).publish(m, topics);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		try {
			((PublicationConnector) ppop.getConnector()).publish(ms, topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		try {
			((PublicationConnector) ppop.getConnector()).publish(ms, topics);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
