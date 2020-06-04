package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

/**
 * Interface composant du systeme de gestion
 * 
 * @author Bello Velly
 *
 */
public interface ManagementCI extends ManagementImplementationI, SubscriptionImplementationI, OfferedI, RequiredI {

	/**
	 * @see interfaces.ManagementImplementationI#createTopic(String)
	 */
	@Override
	void createTopic(String topic) throws Exception;

	/**
	 * @see interfaces.ManagementImplementationI#createTopics(String[])
	 */
	@Override
	void createTopics(String[] topics) throws Exception;

	/**
	 * @see interfaces.ManagementImplementationI#destroyTopic(String)
	 */
	@Override
	void destroyTopic(String topic) throws Exception;

	/**
	 * @see interfaces.ManagementImplementationI#isTopic(String)
	 */
	@Override
	boolean isTopic(String topic) throws Exception;

	/**
	 * @see interfaces.ManagementImplementationI#getTopics()
	 */
	@Override
	String[] getTopics() throws Exception;

	/**
	 * @see interfaces.ManagementImplementationI#getPublicationPortURI()
	 */
	@Override
	String getPublicationPortURI() throws Exception;

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, String)
	 */
	@Override
	void subscribe(String topic, String inboundPortURI) throws Exception;

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String[], String)
	 */
	@Override
	void subscribe(String[] topics, String inboundPortURI) throws Exception;

	/**
	 * @see interfaces.SubscriptionImplementationI#subscribe(String, MessageFilterI,
	 *      String)
	 */
	@Override
	void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;

	/**
	 * @see interfaces.SubscriptionImplementationI#modifyFilter(String,
	 *      MessageFilterI, String)
	 */
	@Override
	void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;

	/**
	 * @see interfaces.SubscriptionImplementationI#unsubscribe(String, String)
	 */
	@Override
	void unsubscribe(String topic, String inboundPortURI) throws Exception;

}
