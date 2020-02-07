package components;

import connectors.ManagementConnector;
import connectors.PublicationConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;
import port.PublisherManagementOutboundPort;
import port.PublisherPublicationOutboundPort;

/**
 * Classe representant le composant publieur
 * 
 * @author Bello Velly
 *
 */

@RequiredInterfaces(required = { PublicationCI.class, ManagementCI.class })
public class Publisher extends AbstractComponent implements PublicationsImplementationI, ManagementImplementationI {

	// ports
	protected PublisherPublicationOutboundPort ppop;
	protected PublisherManagementOutboundPort pmop;

	public Publisher(String pmopURI, String ppopURI) throws Exception {
		super(1, 0);

		// verifications
		assert ppopURI != null;
		assert pmopURI != null;

		// creations ports
		this.ppop = new PublisherPublicationOutboundPort(ppopURI, this);
		this.ppop.publishPort();

		this.pmop = new PublisherManagementOutboundPort(pmopURI, this);
		this.pmop.publishPort();
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
			// TODO modifier ca en connectant ports sortants sur port entrants
			this.doPortConnection(this.ppop.getPortURI(), ???,
					PublicationConnector.class.getCanonicalName());
			this.doPortConnection(this.pmop.getPortURI(), ???,
					ManagementConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		// deconnection des ports
		try {
			this.doPortDisconnection(this.ppop.getPortURI());
			this.doPortDisconnection(this.pmop.getPortURI());
			ppop.doDisconnection();
			pmop.doDisconnection();
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
			ppop.doDisconnection();
			pmop.doDisconnection();
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

}
