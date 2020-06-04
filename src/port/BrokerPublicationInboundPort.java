package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.MessageI;
import interfaces.PublicationCI;

/**
 * Port d'entrée du Broker pour l'interface composant PublicationCI
 * 
 * @author Bello Velly
 *
 */
public class BrokerPublicationInboundPort extends AbstractInboundPort implements PublicationCI {

	private static final long serialVersionUID = 1L;

	/**
	 * index du pool de thread sur lequel on va éxécuté les appels
	 */
	protected final int executorIndex;

	/**
	 * Constructeur de BrokerPublicationInboundPort
	 * 
	 * @param uri           uri du port
	 * @param executorIndex @see {@link #executorIndex}
	 * @param owner         composant qui possède le port
	 */
	public BrokerPublicationInboundPort(String uri, int executorIndex, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
		assert owner instanceof Broker;
		this.executorIndex = executorIndex;
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String)
	 */
	@Override
	public void publish(MessageI m, String topic) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(m, topic));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI, String[])
	 */
	@Override
	public void publish(MessageI m, String[] topics) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(m, topics));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String)
	 */
	@Override
	public void publish(MessageI[] ms, String topic) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(ms, topic));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.PublicationsImplementationI#publish(MessageI[], String[])
	 */
	@Override
	public void publish(MessageI[] ms, String[] topics) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(ms, topics));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
