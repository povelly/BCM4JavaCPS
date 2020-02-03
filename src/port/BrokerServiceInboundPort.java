package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.BrokerServicesI;

public class BrokerServiceInboundPort extends AbstractInboundPort implements BrokerServicesI {

	private static final long serialVersionUID = 1L;

	public BrokerServiceInboundPort(ComponentI owner) throws Exception {
		super(BrokerServicesI.class, owner);
		assert owner instanceof Broker;
	}
	
	public BrokerServiceInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, BrokerServicesI.class, owner);
		assert owner instanceof Broker;
	}
	
}
