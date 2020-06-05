package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.MessageI;
import interfaces.ReceptionCI;

/**
 * Classe representant le connecteur de l'interface composant ReceptionCI
 * 
 * @author Bello Velly
 *
 */
public class ReceptionConnector extends AbstractConnector implements ReceptionCI {

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	public void acceptMessage(MessageI m) throws Exception {
		((ReceptionCI) this.offering).acceptMessage(m);
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI[])
	 */
	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		((ReceptionCI) this.offering).acceptMessage(ms);
	}

}
