package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

/**
 * interface composant de reception
 * 
 * @author pablo
 *
 */
public interface ReceptionCI extends ReceptionImplementationI, OfferedI, RequiredI {

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	void acceptMessage(MessageI m) throws Exception;

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI[])
	 */
	@Override
	void acceptMessage(MessageI[] ms) throws Exception;
}
