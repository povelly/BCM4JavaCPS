package interfaces;

/**
 * interface de souscriveur
 * 
 * @author Bello Velly
 *
 */
public interface SubscriptionImplementationI {

	public void subscribe(String topic, String inboundPortURI) throws Exception;

	public void subscribe(String[] topics, String inboundPortURI) throws Exception;

	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;

	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;

	public void unsubscribe(String topic, String inboundPortURI) throws Exception;

}
