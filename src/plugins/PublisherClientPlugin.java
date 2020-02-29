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

public class PublisherClientPlugin extends AbstractPlugin
		implements PublicationsImplementationI, ManagementImplementationI {

	private static final long serialVersionUID = 1L;

	protected PublicationOutboundPortForPlugin pop;
	protected ManagementOutboundPortForPlugin mop;
	protected String pipUri;
	protected String mipUri;

	public PublisherClientPlugin(String pipUri, String mipUri) {
		super();
		this.pipUri = pipUri;
		this.mipUri = mipUri;
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		// Publication
		this.addRequiredInterface(PublicationCI.class);
		this.pop = new PublicationOutboundPortForPlugin(this.owner);
		this.pop.publishPort();
		// Management
		this.addRequiredInterface(ManagementCI.class);
		this.mop = new ManagementOutboundPortForPlugin(this.owner);
		this.mop.publishPort();
	}

	@Override
	public void initialise() throws Exception {
		// Connection sur publication
		this.owner.doPortConnection(pop.getPortURI(), pipUri, PublicationConnector.class.getCanonicalName());
		// Connection sur management
		this.owner.doPortConnection(mop.getPortURI(), mipUri, ManagementConnector.class.getCanonicalName());
		super.initialise();
	}

	@Override
	public void finalise() throws Exception {
		// Deconnection sur publication
		this.owner.doPortDisconnection(pop.getPortURI());
		// Deconnection sur management
		this.owner.doPortDisconnection(mop.getPortURI());
		super.finalise();
	}

	@Override
	public void uninstall() throws Exception {
		// publication
		this.pop.unpublishPort();
		this.pop.destroyPort();
		this.removeRequiredInterface(PublicationCI.class);
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

	@Override
	public void publish(MessageI m, String topic) throws Exception {
		this.pop.publish(m, topic);
	}

	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		this.pop.publish(m, topics);
	}

	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		this.pop.publish(ms, topic);
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		this.pop.publish(ms, topics);
	}

}
