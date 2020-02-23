package plugins;

import connectors.ManagementConnector;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;
import interfaces.SubscriptionImplementationI;
import port.ManagementOutboundPortForPlugin;
import port.ReceptionInboundPortForPlugin;

/**
 * plugin server pour le subscriber permet la reception de message (serveur) et
 * un acces a management en tant que client
 * 
 * @author Bello Velly
 *
 */
public class SubscriberPlugin extends AbstractPlugin implements ReceptionImplementationI, SubscriptionImplementationI {

	private static final long serialVersionUID = 1L;
	protected ReceptionInboundPortForPlugin rip;
	protected String ripUri;
	protected ManagementOutboundPortForPlugin mop;
	protected String mipUri;

	public SubscriberPlugin(String mipUri, String ripUri) {
		super();
		this.mipUri = mipUri;
		this.ripUri = ripUri;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		// Reception : serveur
		assert owner instanceof ReceptionImplementationI;
		this.addOfferedInterface(ReceptionCI.class);
		// TODO demander prof ici pour l'uri, car erreur dans son framework si on passe
		// une uri
		this.rip = new ReceptionInboundPortForPlugin(ripUri, this.getPluginURI(), this.owner);
		this.rip.publishPort();
		// Management : client
		this.addRequiredInterface(ManagementCI.class);
		this.mop = new ManagementOutboundPortForPlugin(this.owner);
		this.mop.publishPort();
	}

	@Override
	public void initialise() throws Exception {
		// Connection sur management
		this.owner.doPortConnection(mop.getPortURI(), mipUri, ManagementConnector.class.getCanonicalName());
		super.initialise();
	}

	@Override
	public void finalise() throws Exception {
		// Deconnection sur management
		this.owner.doPortDisconnection(mop.getPortURI());
		super.finalise();
	}

	@Override
	public void uninstall() throws Exception {
		// reception
		this.rip.unpublishPort();
		this.removeOfferedInterface(ReceptionCI.class);
		// management
		this.mop.unpublishPort();
		this.mop.destroyPort();
		this.removeRequiredInterface(ManagementCI.class);
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	private ReceptionImplementationI getOwner() {
		return (ReceptionImplementationI) this.owner;
	}

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		this.getOwner().acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		this.getOwner().acceptMessage(ms);
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		this.mop.subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		this.mop.subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		this.mop.subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		this.mop.modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		this.mop.unsubscribe(topic, inboundPortURI);
	}

}
