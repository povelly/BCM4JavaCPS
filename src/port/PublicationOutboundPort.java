package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;

/**
 * Port sortant pour l'interface PublicationCI
 * 
 * @author Bello Velly
 *
 */
public class PublicationOutboundPort extends AbstractOutboundPort implements PublicationCI {

	private static final long serialVersionUID = 1L;

	public PublicationOutboundPort(ComponentI owner) throws Exception {
		super(PublicationCI.class, owner);
	}

	public PublicationOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
	}

	@Override
	public void publish(MessageI m, String topic) throws Exception {
		((PublicationsImplementationI) this.connector).publish(m, topic);
	}

	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		((PublicationsImplementationI) this.connector).publish(m, topics);
	}

	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		((PublicationsImplementationI) this.connector).publish(ms, topic);
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		((PublicationsImplementationI) this.connector).publish(ms, topics);
	}

}
