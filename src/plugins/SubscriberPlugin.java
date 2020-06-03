package plugins;

import connectors.ManagementConnector;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
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
public class SubscriberPlugin extends AbstractPlugin
		implements ReceptionImplementationI, SubscriptionImplementationI, ManagementImplementationI {

	private static final long serialVersionUID = 1L;
	protected ReceptionInboundPortForPlugin rip;
	protected String ripUri;

	protected String mopUri;
	protected ManagementOutboundPortForPlugin mop;

	protected String mipUri;

	public SubscriberPlugin(String mopUri, String mipUri, String ripUri) {
		super();
		this.mopUri = mopUri;
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
		// Management : client
		this.addRequiredInterface(ManagementCI.class);
	}

	@Override
	public void initialise() throws Exception {
		// Reception
		this.rip = new ReceptionInboundPortForPlugin(ripUri, this.getPluginURI(), this.owner);
		this.rip.publishPort();
		// Management
		this.mop = new ManagementOutboundPortForPlugin(mopUri, this.owner);
		this.mop.localPublishPort();

		// Connection sur management
		this.owner.doPortConnection(mop.getPortURI(), mipUri, ManagementConnector.class.getCanonicalName());

		super.initialise();
	}

	@Override
	public void finalise() throws Exception {
		this.owner.printExecutionLog();
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

	@Override
	public void createTopic(String topic) throws Exception {
		this.mop.createTopic(topic);
	}

	@Override
	public void createTopics(String[] topics) throws Exception {
		this.mop.createTopics(topics);
	}

	@Override
	public void destroyTopic(String topic) throws Exception {
		this.mop.destroyTopic(topic);
	}

	@Override
	public boolean isTopic(String topic) throws Exception {
		return this.mop.isTopic(topic);
	}

	@Override
	public String[] getTopics() throws Exception {
		return this.mop.getTopics();
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return this.mop.getPublicationPortURI();
	}

}
