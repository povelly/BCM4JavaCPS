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

	/**
	 * Port entrant de reception des messages
	 */
	protected ReceptionInboundPortForPlugin rip;

	/**
	 * URI du port entrant de reception @see {@link #rip}
	 */
	protected String ripUri;

	/**
	 * URI du port sortant de management @see {@link #mop}
	 */
	protected String mopUri;

	/**
	 * Port sortant de management
	 */
	protected ManagementOutboundPortForPlugin mop;

	/**
	 * URI du port auquel le port sortant de management sera connecté
	 */
	protected String mipUri;

	/**
	 * Constructeur de SubscriberPlugin
	 * 
	 * @param mopUri @see {@link #mopUri}
	 * @param mipUri @see {@link #mipUri}
	 * @param ripUri @see {@link #ripUri}
	 */
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

	/**
	 * Installe le plugin et ajoute ses interfaces.
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#installOn(ComponentI)
	 */
	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		// Reception : serveur
		assert owner instanceof ReceptionImplementationI;
		this.addOfferedInterface(ReceptionCI.class);
		// Management : client
		this.addRequiredInterface(ManagementCI.class);

	}

	/**
	 * Publie et connecte les ports
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#initialise()
	 */
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

	/**
	 * Deconnecte les ports
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#finalise()
	 */
	@Override
	public void finalise() throws Exception {
		this.owner.printExecutionLog();
		// Deconnection sur management
		this.owner.doPortDisconnection(mop.getPortURI());
		super.finalise();
	}

	/**
	 * Depublie les ports et enleve les interfaces du plugin
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#uninstall()
	 */
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

	/**
	 * Retourne le composant propriétaire du plugin
	 * 
	 * @return le composant propriétaire du Plugin
	 */
	private ReceptionImplementationI getOwner() {
		return (ReceptionImplementationI) this.owner;
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	public void acceptMessage(MessageI m) throws Exception {
		this.getOwner().acceptMessage(m);
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		this.getOwner().acceptMessage(ms);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, String)
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		this.mop.subscribe(topic, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String[], String)
	 */
	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		this.mop.subscribe(topics, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, MessageFilterI,
	 *      String)
	 */
	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		this.mop.subscribe(topic, filter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#modifyFilter(String,
	 *      MessageFilterI, String)
	 */
	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		this.mop.modifyFilter(topic, newFilter, inboundPortURI);
	}

	/**
	 * @see interfaces.SubscriptionImplementationI#unsubscribe(String, String)
	 */
	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		this.mop.unsubscribe(topic, inboundPortURI);
	}

	/**
	 * @see interfaces.ManagementImplementationI#createTopic(String)
	 */
	@Override
	public void createTopic(String topic) throws Exception {
		this.mop.createTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#createTopics(String[])
	 */
	@Override
	public void createTopics(String[] topics) throws Exception {
		this.mop.createTopics(topics);
	}

	/**
	 * @see interfaces.ManagementImplementationI#destroyTopic(String)
	 */
	@Override
	public void destroyTopic(String topic) throws Exception {
		this.mop.destroyTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#isTopic(String)
	 */
	@Override
	public boolean isTopic(String topic) throws Exception {
		return this.mop.isTopic(topic);
	}

	/**
	 * @see interfaces.ManagementImplementationI#getTopics()
	 */
	@Override
	public String[] getTopics() throws Exception {
		return this.mop.getTopics();
	}

	/**
	 * @see interfaces.ManagementImplementationI#getPublicationPortURI()
	 */
	@Override
	public String getPublicationPortURI() throws Exception {
		return this.mop.getPublicationPortURI();
	}

}
