package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.MessageI;
import interfaces.PublicationCI;

/**
 * Classe representant le connecteur de publication
 * 
 * @author Bello Velly
 *
 */
public class PublicationConnector extends AbstractConnector implements PublicationCI {

	@Override
	public void publish(MessageI m, String topic) throws Exception {
		((PublicationCI) this.offering).publish(m, topic);
	}

	@Override
	public void publish(MessageI m, String[] topics) throws Exception {
		((PublicationCI) this.offering).publish(m, topics);
	}

	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {
		((PublicationCI) this.offering).publish(ms, topic);
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {
		((PublicationCI) this.offering).publish(ms, topics);
	}

}
