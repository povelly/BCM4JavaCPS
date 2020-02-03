package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.BrokerServicesI;

public class BrokerServiceOutboundPort extends AbstractOutboundPort implements BrokerServicesI {
	
	private static final long serialVersionUID = 1L;

	public BrokerServiceOutboundPort(ComponentI owner) throws Exception {
		super(BrokerServicesI.class, owner);
		assert owner instanceof Broker;
	}
	
	public BrokerServiceOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, BrokerServicesI.class, owner);
		assert owner instanceof Broker;
	}

}
