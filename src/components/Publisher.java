package components;

import connectors.ManagementConnector;
import connectors.PublicationConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.PublicationCI;
import port.PublisherManagementOutboundPort;
import port.PublisherPublicationOutboundPort;

@RequiredInterfaces(required = {PublicationCI.class, ManagementCI.class})
public class Publisher extends AbstractComponent {

	protected PublisherPublicationOutboundPort ppop;
	protected PublisherManagementOutboundPort pmop;

	protected String ppipURI;
	protected String pmipURI;
	
	public Publisher(String pmipURI, String ppipURI) throws Exception {
		super(1, 0);
		assert ppipURI != null;
		assert pmipURI != null;

		this.ppipURI = ppipURI;
		this.pmipURI = pmipURI;

		this.ppop = new PublisherPublicationOutboundPort(this);
		this.ppop.publishPort();

		this.pmop = new PublisherManagementOutboundPort(this);
		this.pmop.publishPort();
	}

	@Override
	public void start() throws ComponentStartException {
		super.start();
		
		try {
			this.doPortConnection(this.ppop.getPortURI(), this.ppipURI, PublicationConnector.class.getCanonicalName());
			this.doPortConnection(this.pmop.getPortURI(), this.pmipURI, ManagementConnector.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void shutdown() throws ComponentShutdownException { // TODO a facto dans finalize()
		try {
			this.doPortDisconnection(this.ppop.getPortURI());
			this.doPortDisconnection(this.pmop.getPortURI());
			ppop.unpublishPort();
			pmop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException { // TODO same here
		try {
			this.doPortDisconnection(this.ppop.getPortURI());
			this.doPortDisconnection(this.pmop.getPortURI());
			ppop.unpublishPort();
			pmop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

}
