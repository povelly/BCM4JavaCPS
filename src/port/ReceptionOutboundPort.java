package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;

/**
 * Port sortant pour l'interface ReceptionCI
 * 
 * @author Bello Velly
 *
 */
public class ReceptionOutboundPort extends AbstractOutboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public ReceptionOutboundPort(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
		assert owner instanceof Broker;
	}

	public ReceptionOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
		assert owner instanceof Broker;
	}

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		((ReceptionImplementationI) this.connector).acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		((ReceptionImplementationI) this.connector).acceptMessage(ms);
	}

}
