package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;

/**
 * Port sortant de publication pour plugin
 * 
 * @author Bello Velly
 *
 */
public class PublicationOutboundPortForPlugin extends AbstractOutboundPort implements PublicationCI {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de PublicationOutboundPortForPlugin
	 * 
	 * @param owner composant qui possède le port
	 */
	public PublicationOutboundPortForPlugin(ComponentI owner) throws Exception {
		super(PublicationCI.class, owner);
	}

	/**
	 * Constructeur de PublicationOutboundPortForPlugin
	 * 
	 * @param uri   uri du port
	 * @param owner composant qui possède le port
	 */
	public PublicationOutboundPortForPlugin(String uri, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String)
	 */
	@Override
	public void publish(MessageI m, String topic) throws Exception {
		((PublicationsImplementationI) this.connector).publish(m, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String[])
	 */
	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		((PublicationsImplementationI) this.connector).publish(m, topics);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String)
	 */
	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		((PublicationsImplementationI) this.connector).publish(ms, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String[])
	 */
	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		((PublicationsImplementationI) this.connector).publish(ms, topics);
	}

}
