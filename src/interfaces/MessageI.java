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

	/**
	 * Récupère l'URI du Message
	 * 
	 * @return URI du Message
	 */
	String getURI() throws Exception;

	/**
	 * Récupère le TimeStamp du Message
	 * 
	 * @return le TimeStamp du Message
	 */
	TimeStamp getTimeStamp() throws Exception;

	/**
	 * Récupères les Properties du Message
	 * 
	 * @return les Properties du Message
	 */
	Properties getProperties() throws Exception;

	/**
	 * Retourne le contenu du Message
	 * 
	 * @return contenu du Message
	 */
	Serializable getPayload() throws Exception;

	/**
	 * Ajoute un URI de Broker par lequel le Message à déjà été consulté
	 * 
	 * @param broker_uri uri du Broker
	 * @return le Message lui même, l'uri à été ajoutée
	 */
	MessageI addBroker(String broker_uri) throws Exception;

	/**
	 * Regarde si un URI de Broker donné existe des les URIs de Broker que le
	 * Message possède
	 * 
	 * @param broker_uri uri du Broker
	 * @return true si l'URI existe dans la liste d'URIs de Broker du Message, false
	 *         sinon
	 * @throws Exception
	 */
	Boolean containsBroker(String broker_uri) throws Exception;

}
