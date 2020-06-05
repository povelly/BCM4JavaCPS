package interfaces;

/**
 * interface de publication
 * 
 * @author Bello Velly
 *
 */
public interface PublicationsImplementationI {

	/**
	 * Publie un Message sur un topic donné
	 * 
	 * @param m     Message à publié
	 * @param topic titre du topic
	 */
	void publish(MessageI m, String topic) throws Exception;

	/**
	 * Publie un Message sur des topics donnés
	 * 
	 * @param m      Message à publié
	 * @param topics titre des topics
	 */
	void publish(MessageI m, String[] topics) throws Exception;

	/**
	 * Publie des Message sur un topic donné
	 * 
	 * @param ms    Message qui doivent être publiés
	 * @param topic titre du topic
	 */
	void publish(MessageI[] ms, String topic) throws Exception;

	/**
	 * Publie des Message sur des topic donnés
	 * 
	 * @param ms     Message qui doivent être publiés
	 * @param topics titres des topics
	 */
	void publish(MessageI[] ms, String[] topics) throws Exception;

}
