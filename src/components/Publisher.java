package components;

import fr.sorbonne_u.components.AbstractComponent;
import interfaces.ManagementCI;
import interfaces.PublicationCI;
import interfaces.PublisherServicesI;
import port.PublisherServiceOutboundPort;

public class Publisher extends AbstractComponent {

	protected PublisherServiceOutboundPort psop1;
	protected PublisherServiceOutboundPort psop2;
	
	public Publisher(String psop1URI, String psop2URI) throws Exception {
		super(1, 0);
		this.addOfferedInterface(PublisherServicesI.class);
		this.addRequiredInterface(ManagementCI.class);
		this.addRequiredInterface(PublicationCI.class);

		this.psop1 = new PublisherServiceOutboundPort(psop1URI, this);
		this.psop1.publishPort();

		this.psop2 = new PublisherServiceOutboundPort(psop2URI, this);
		this.psop2.publishPort();
	}
	
}
