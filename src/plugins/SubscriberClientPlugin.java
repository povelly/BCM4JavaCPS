package plugins;

import connectors.ReceptionConnector;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;
import port.ReceptionOutboundPortForPlugin;

/**
 * Plugin client pour le subscriber, permet l'envoie de messages
 * 
 * @author Bello Velly
 *
 */
public class SubscriberClientPlugin extends AbstractPlugin implements ReceptionImplementationI {

	private static final long serialVersionUID = 1L;

	protected ReceptionOutboundPortForPlugin rop;
	protected String ripURI;

	public SubscriberClientPlugin(String ripUri) {
		this.ripURI = ripUri;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		this.addRequiredInterface(ReceptionCI.class);
		this.rop = new ReceptionOutboundPortForPlugin(this.owner);
		this.rop.publishPort();
	}

	@Override
	public void initialise() throws Exception {
		this.owner.doPortConnection(rop.getPortURI(), ripURI, ReceptionConnector.class.getCanonicalName());
		super.initialise();
	}

	@Override
	public void finalise() throws Exception {
		this.owner.doPortDisconnection(this.rop.getPortURI());
		super.finalise();
	}

	@Override
	public void uninstall() throws Exception {
		this.rop.unpublishPort();
		this.rop.destroyPort();
		this.removeRequiredInterface(ReceptionCI.class);
		super.uninstall();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		this.rop.acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		this.rop.acceptMessage(ms);
	}

}
