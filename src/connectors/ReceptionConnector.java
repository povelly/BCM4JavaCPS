package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.MessageI;
import interfaces.ReceptionCI;

/**
 * Classe representant le connecteur de reception
 * 
 * @author Bello Velly
 *
 */
public class ReceptionConnector extends AbstractConnector implements ReceptionCI {

	@Override
	public void acceptMessage(MessageI m) {
		((ReceptionCI) this.offering).acceptMessage(m);
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		((ReceptionCI) this.offering).acceptMessage(ms);
	}

}
