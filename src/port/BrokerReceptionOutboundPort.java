package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;

/**
 * Port de sortie du Broker pour l'interface ReceptionCI
 * 
 * @author Bello Velly
 *
 */
public class BrokerReceptionOutboundPort extends AbstractOutboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public BrokerReceptionOutboundPort(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
		assert owner instanceof Broker;
	}

	public BrokerReceptionOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
		assert owner instanceof Broker;
	}

	@Override
	public void acceptMessage(MessageI m) {
		((ReceptionCI) this.connector).acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		((ReceptionCI) this.connector).acceptMessage(ms);
	}

}
