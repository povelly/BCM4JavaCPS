package interfaces;

import java.io.Serializable;

import message.Properties;
import message.TimeStamp;

/**
 * interface d'un message
 * 
 * @author Bello Velly
 *
 */
public interface MessageI extends Serializable {

	String getURI() throws Exception;

	TimeStamp getTimeStamp() throws Exception;

	Properties getProperties() throws Exception;

	Serializable getPayload() throws Exception;

	MessageI addBroker(String broker_uri) throws Exception;

	Boolean containsBroker(String broker_uri) throws Exception;

}
