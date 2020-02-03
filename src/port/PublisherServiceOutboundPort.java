package port;

import components.Publisher;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.PublisherServicesI;

public class PublisherServiceOutboundPort extends AbstractOutboundPort implements PublisherServicesI {
	
	private static final long serialVersionUID = 1L;

	public PublisherServiceOutboundPort(ComponentI owner) throws Exception {
		super(PublisherServicesI.class, owner);
		assert owner instanceof Publisher;
	}
	
	public PublisherServiceOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, PublisherServicesI.class, owner);
		assert owner instanceof Publisher;
	}

}
