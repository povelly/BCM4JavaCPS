package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.MessageI;
import interfaces.PublicationCI;

/**
 * Classe representant le connecteur de l'interface composant PublicationCI
 * 
 * @author Bello Velly
 *
 */
public class PublicationConnector extends AbstractConnector implements PublicationCI {

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String)
	 */
	@Override
	public void publish(MessageI m, String topic) throws Exception {
		((PublicationCI) this.offering).publish(m, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String[])
	 */
	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		((PublicationCI) this.offering).publish(m, topics);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String)
	 */
	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		((PublicationCI) this.offering).publish(ms, topic);
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String[])
	 */
	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		((PublicationCI) this.offering).publish(ms, topics);
	}

}
