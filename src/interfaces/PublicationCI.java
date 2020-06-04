package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

/**
 * interface composant de publication
 * 
 * @author Bello Velly
 *
 */
public interface PublicationCI extends PublicationsImplementationI, OfferedI, RequiredI {

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String)
	 */
	@Override
	void publish(MessageI m, String topic) throws Exception;

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String[])
	 */
	@Override
	void publish(MessageI m, String[] topics) throws Exception;

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String)
	 */
	@Override
	void publish(MessageI[] ms, String topic) throws Exception;

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String[])
	 */
	@Override
	void publish(MessageI[] ms, String[] topics) throws Exception;
}
