package port;

import components.Publisher;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.PublisherServicesI;

public class PublisherServiceInboundPort extends AbstractInboundPort implements PublisherServicesI {
	
	private static final long serialVersionUID = 1L;

	public PublisherServiceInboundPort(ComponentI owner) throws Exception {
		super(PublisherServicesI.class, owner);
		assert owner instanceof Publisher;
	}
	
	public PublisherServiceInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, PublisherServicesI.class, owner);
		assert owner instanceof Publisher;
	}

}
