package interfaces;

/**
 * interface de ation
 * 
 * @author Bello Velly
 *
 */
public interface PublicationsImplementationI {

	void publish(MessageI m, String topic) throws Exception;

	void publish(MessageI m, String[] topics) throws Exception;

	void publish(MessageI[] ms, String topic) throws Exception;

	void publish(MessageI[] ms, String[] topics) throws Exception;

}
