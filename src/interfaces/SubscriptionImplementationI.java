package interfaces;

/**
 * interface de souscription
 * 
 * @author Bello Velly
 *
 */
public interface SubscriptionImplementationI {

	/**
	 * abonne un Subscriber à un topic
	 * 
	 * @param topic          topic auquel le subscriber veut s'abonner
	 * @param inboundPortURI URI sur lequel le subscriber reçevra les messages
	 */
	void subscribe(String topic, String inboundPortURI) throws Exception;

	/**
	 * abonne un Subscriber à plusieurs topics
	 * 
	 * @param topic          topics auquels le subscriber veut s'abonner
	 * @param inboundPortURI URI sur lequel le subscriber reçevra les messages
	 */
	void subscribe(String[] topics, String inboundPortURI) throws Exception;

	/**
	 * abonne un Subscriber à un topic en precisent un filtre
	 * 
	 * @param topic          topic auquel le subscriber veut s'abonner
	 * @param filter         filtre à appliquer
	 * @param inboundPortURI URI sur lequel le subscriber reçevra les messages
	 */
	void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;

	/**
	 * modifie un filtre sur un topic auquel le subscriber est abonné
	 * 
	 * @param topic          topic du filtre à modifier
	 * @param newFilter      nouveau filtre à appliquer
	 * @param inboundPortURI URI sur lequel le subscriber reçevra les messages
	 */
	void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;

	/**
	 * Supprime la souscription d'un Subscriber à un topic
	 * 
	 * @param topic topic auquel on ne veut plus être abonné
	 * @param URI   du port sur lequel on reçevait les messages
	 */
	void unsubscribe(String topic, String inboundPortURI) throws Exception;

}
