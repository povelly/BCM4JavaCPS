package interfaces;

/**
 * interface de reception
 * 
 * @author Bello Velly
 *
 */
public interface ReceptionImplementationI {

	/**
	 * Reçoit un Message
	 * 
	 * @param m Message reçu
	 */
	void acceptMessage(MessageI m) throws Exception;

	/**
	 * Reçoit des Messages
	 * 
	 * @param ms Message reçus
	 */
	void acceptMessage(MessageI[] ms) throws Exception;

}
