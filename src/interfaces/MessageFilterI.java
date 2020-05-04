package interfaces;

import java.io.Serializable;

/**
 * 
 * interface fonctionelle permettant de filter un message
 * 
 * @author Bello Velly
 *
 */
@FunctionalInterface
public interface MessageFilterI extends Serializable {

	/**
	 * 
	 * @param m Message à filtrer
	 * @return true si le message correspond au conditions, false sinon
	 */
	boolean filter(MessageI m);

}
