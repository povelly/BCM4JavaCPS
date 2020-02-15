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

	public BrokerPublicationInboundPort(ComponentI owner) throws Exception {
		super(PublicationCI.class, owner);
		assert owner instanceof Broker;
	}

	public BrokerPublicationInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, PublicationCI.class, owner);
		assert owner instanceof Broker;
	}

	@Override
	public void publish(MessageI m, String topic) {
		try {
			this.getOwner().runTask(owner -> {
				((Broker) owner).publish(m, topic);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		try {
			this.getOwner().runTask(owner -> {
				((Broker) owner).publish(m, topics);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		try {
			this.getOwner().runTask(owner -> {
				((Broker) owner).publish(ms, topic);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		try {
			this.getOwner().runTask(owner -> {
				((Broker) owner).publish(ms, topics);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
