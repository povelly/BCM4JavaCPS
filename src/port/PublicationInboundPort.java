package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.MessageI;
import interfaces.PublicationCI;

/**
 * Port entrant l'interface composant PublicationCI
 * 
 * @author Bello Velly
 *
 */
public class PublicationInboundPort extends AbstractInboundPort implements PublicationCI {

	private static final long serialVersionUID = 1L;
	protected final int executorIndex;

	public PublicationInboundPort(String uri, int executorIndex, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
		assert owner instanceof Broker;
		this.executorIndex = executorIndex;
	}

	@Override
	public void publish(MessageI m, String topic) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(m, topic));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(m, topics));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(ms, topic));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		try {
			this.getOwner().runTask(executorIndex, owner -> ((Broker) owner).publish(ms, topics));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
