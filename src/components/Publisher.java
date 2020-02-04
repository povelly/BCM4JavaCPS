package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.ManagementCI;
import interfaces.PublicationCI;
import port.PublisherManagementOutboundPort;
import port.PublisherPublicationOutboundPort;

public class Publisher extends AbstractComponent {

	protected PublisherPublicationOutboundPort ppop;
	protected PublisherManagementOutboundPort pmop;
	
	public Publisher(String pmopURI, String ppopURI) throws Exception {
		super(1, 0);
		this.addRequiredInterface(ManagementCI.class);
		this.addRequiredInterface(PublicationCI.class);

		this.ppop = new PublisherPublicationOutboundPort(ppopURI, this);
		this.ppop.publishPort();
		
		this.pmop = new PublisherManagementOutboundPort(pmopURI, this);
		this.pmop.publishPort();
	}
	
	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			ppop.unpublishPort();
			pmop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			ppop.unpublishPort();
			pmop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}
	
}
