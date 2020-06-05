package plugins;

import connectors.ManagementConnector;
import connectors.PublicationConnector;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;
import port.ManagementOutboundPortForPlugin;
import port.PublicationOutboundPortForPlugin;

/**
 * Plugin pour le Publisher
 * 
 * @author Bello Velly
 *
 */
public class PublisherClientPlugin extends AbstractPlugin
		implements PublicationsImplementationI, ManagementImplementationI {

	private static final long serialVersionUID = 1L;

	/**
	 * Port sortant de publication
	 */
	protected PublicationOutboundPortForPlugin pop;

	/**
	 * URI du port sortant de publication
	 */
	protected String popUri;

	/**
	 * Port sortant de management
	 */
	protected ManagementOutboundPortForPlugin mop;

	/**
	 * URI du port sortant de management
	 */
	protected String mopUri;

	/**
	 * URI vers lequel le port sortant de publication va ce connecté @see
	 * {@link #pop}
	 */
	protected String pipUri;

	/**
	 * URI vers lequel le port sortant de management va ce connecté @see
	 * {@link #mop}
	 */
	protected String mipUri;

	/**
	 * Constructeur de PublisherClientPlugin
	 * 
	 * @param popUri @see {@link #popUri}
	 * @param mopUri @see {@link #mopUri}
	 * @param pipUri @see {@link #pipUri}
	 * @param mipUri @see {@link #mipUri}
	 */
	public PublisherClientPlugin(String popUri, String mopUri, String pipUri, String mipUri) {
		super();
		this.pipUri = pipUri;
		this.mipUri = mipUri;
		this.popUri = popUri;
		this.mopUri = mopUri;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	/**
	 * Installe le plugin, publie ses ports et ajoute ses interfaces.
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#installOn(ComponentI)
	 */
	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		// Publication
		this.addRequiredInterface(PublicationCI.class);
		this.pop = new PublicationOutboundPortForPlugin(popUri, this.owner);
		this.pop.localPublishPort();
		// Management
		this.addRequiredInterface(ManagementCI.class);
		this.mop = new ManagementOutboundPortForPlugin(mopUri, this.owner);
		this.mop.localPublishPort();
	}

	/**
	 * Connecte les ports
	 * 
	 * @see fr.sorbonne_u.components.AbstractPlugin#initialise()
	 */
	@Override
	public void initialise() throws Exception {
		// Connection sur publication
		this.owner.doPortConnection(pop.getPortURI(), pipUri, PublicationConnector.class.getCanonicalName());
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
		// Deconnection sur publication
		this.owner.doPortDisconnection(pop.getPortURI());
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
		// publication
		this.pop.unpublishPort();
		this.removeRequiredInterface(PublicationCI.class);
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

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String)
	 */
	@Override
	public void publish(MessageI m, String topic) throws Exception {
		this.pop.publish(m, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String[])
	 */
	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		this.pop.publish(m, topics);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String)
	 */
	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		this.pop.publish(ms, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String[])
	 */
	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		this.pop.publish(ms, topics);
	}

}
