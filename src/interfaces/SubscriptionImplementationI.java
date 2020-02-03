package interfaces;

public interface SubscriptionImplementationI {

	public void subscribe(String topic, String inboundPortURI);
	public void subscribe(String[] topics, String inboundPortURI);
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI);
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI);
	public void unsubscribe(String topic, String inboundPortURI);
	
}
