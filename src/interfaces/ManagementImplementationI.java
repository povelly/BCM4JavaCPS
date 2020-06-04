package interfaces;

/**
 * interface de gestion des sujets
 * 
 * @author Bello Velly
 *
 */
public interface ManagementImplementationI {

	/**
	 * Créer un topic donné
	 * 
	 * @param topic titre du topic à créer
	 */
	void createTopic(String topic) throws Exception;

	/**
	 * Créer plusieurs topics donnés
	 * 
	 * @param topics titres des topics à crée
	 */
	void createTopics(String[] topics) throws Exception;

	/**
	 * Détruit un topic
	 * 
	 * @param topic titre du topic à detruire
	 */
	void destroyTopic(String topic) throws Exception;

	/**
	 * Regarde si un topic existe
	 * 
	 * @param topic topic dont on veut vérifier l'existence
	 * @return true si le topic existe, false sinon
	 */
	boolean isTopic(String topic) throws Exception;

	/**
	 * Renvoie l'ensemble des topics
	 * 
	 * @return l'ensemble des topics
	 */
	String[] getTopics() throws Exception;

	/**
	 * Renvoie l'URI du port de publication
	 * 
	 * @return l'URI du port de publication
	 */
	String getPublicationPortURI() throws Exception;

}
