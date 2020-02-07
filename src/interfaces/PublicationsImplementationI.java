package interfaces;

/**
 * interface de publication
 * 
 * @author Bello Velly
 *
 */
public interface PublicationsImplementationI {

	public void publish(MessageI m, String topic);

	public void publish(MessageI m, String[] topics);

	public void publish(MessageI[] ms, String topic);

	public void publish(MessageI[] ms, String[] topics);

}
