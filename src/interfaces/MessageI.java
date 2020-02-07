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

	public String getURI();

	public TimeStamp getTimeStamp();

	public Properties getProperties();

	public Serializable getPayload();

}
