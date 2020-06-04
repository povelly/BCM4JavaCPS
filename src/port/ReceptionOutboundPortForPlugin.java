package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;

/**
 * Port sortant de recepetion pour plugin
 * 
 * @author Bello Velly
 *
 */
public class ReceptionOutboundPortForPlugin extends AbstractOutboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de ReceptionOutboundPortForPlugin
	 * 
	 * @param owner composant qui possède le port
	 */
	public ReceptionOutboundPortForPlugin(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
	}

	/**
	 * Constructeur de ReceptionOutboundPortForPlugin
	 * 
	 * @param uri   uri du port
	 * @param owner composant qui possède le port
	 */
	public ReceptionOutboundPortForPlugin(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	public void acceptMessage(MessageI m) throws Exception {
		((ReceptionImplementationI) this.connector).acceptMessage(m);
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI[])
	 */
	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		((ReceptionImplementationI) this.connector).acceptMessage(ms);
	}

}
