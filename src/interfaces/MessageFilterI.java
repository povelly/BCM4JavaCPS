package interfaces;

/**
 * 
 * interface fonctionelle permettant de filter un message
 * 
 * @author Bello Velly
 *
 */
@FunctionalInterface
public interface MessageFilterI {

	/**
	 * 
	 * @param m Message à filtrer
	 * @return true si le message correspond au conditions, false sinon
	 */
	public boolean filter(MessageI m);

}
