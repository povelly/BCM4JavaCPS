package port;

import components.Subscriber;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;

/**
 * Port d'entrée du Subscriber pour l'interface composant Reception
 * 
 * @author Bello Velly
 *
 */
public class SubscriberReceptionInboundPort extends AbstractInboundPort implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public SubscriberReceptionInboundPort(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
		assert owner instanceof Subscriber;
	}

	public SubscriberReceptionInboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
		assert owner instanceof Subscriber;
	}

	@Override
	public void acceptMessage(MessageI m) {
		try {
			this.getOwner().runTask(owner -> {
				((Subscriber) owner).acceptMessage(m);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		try {
			this.getOwner().runTask(owner -> {
				((Subscriber) owner).acceptMessage(ms);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
