package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;

/**
 * Port sortant de recepetion pour plugin
 * 
 * @author Bello Velly
 *
 */
public class ReceptionOutboundPortForPlugin extends AbstractOutboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public ReceptionOutboundPortForPlugin(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
	}

	public ReceptionOutboundPortForPlugin(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
	}

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		((ReceptionCI) this.connector).acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		((ReceptionCI) this.connector).acceptMessage(ms);
	}

}
