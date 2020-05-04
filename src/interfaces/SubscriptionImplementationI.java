package interfaces;

/**
 * interface de souscriveur
 * 
 * @author Bello Velly
 *
 */
public interface SubscriptionImplementationI {

	void subscribe(String topic, String inboundPortURI) throws Exception;

	void subscribe(String[] topics, String inboundPortURI) throws Exception;

	void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;

	void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;

	void unsubscribe(String topic, String inboundPortURI) throws Exception;

}
