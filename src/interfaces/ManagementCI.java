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

	@Override
	void createTopic(String topic) throws Exception;

	@Override
	void createTopics(String[] topics) throws Exception;

	@Override
	void destroyTopic(String topic) throws Exception;

	@Override
	boolean isTopic(String topic) throws Exception;

	@Override
	String[] getTopics() throws Exception;

	@Override
	String getPublicationPortURI() throws Exception;

	@Override
	void subscribe(String topic, String inboundPortURI) throws Exception;

	@Override
	void subscribe(String[] topics, String inboundPortURI) throws Exception;

	@Override
	void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception;

	@Override
	void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception;

	@Override
	void unsubscribe(String topic, String inboundPortURI) throws Exception;

}
