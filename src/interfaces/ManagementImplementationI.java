package interfaces;

/**
 * interface de gestion des sujets
 * 
 * @author Bello Velly
 *
 */
public interface ManagementImplementationI {

	public void createTopic(String topic);

	public void createTopics(String[] topics);

	public void destroyTopic(String topic);

	public boolean isTopic(String topic);

	public String[] getTopics();

}
