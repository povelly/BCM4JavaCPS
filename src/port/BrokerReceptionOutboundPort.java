package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;

/**
 * Port de sortie du Broker pour l'interface ReceptionCI
 * 
 * @author Bello Velly
 *
 */
public class BrokerReceptionOutboundPort extends AbstractOutboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de BrokerReceptionOutboundPort
	 * 
	 * @param owner composant qui possède le port
	 */
	public BrokerReceptionOutboundPort(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
		assert owner instanceof Broker;
	}

	/**
	 * Constructeur de BrokerReceptionOutboundPort
	 * 
	 * @param uri   uri du port
	 * @param owner composant qui possède le port
	 */
	public BrokerReceptionOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
		assert owner instanceof Broker;
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
