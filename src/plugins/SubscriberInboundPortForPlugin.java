package plugins;

import components.Subscriber;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.forplugins.AbstractInboundPortForPlugin;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.SubscriptionImplementationI;

public class SubscriberInboundPortForPlugin extends AbstractInboundPortForPlugin implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public SubscriberInboundPortForPlugin(String pluginURI, ComponentI owner) throws Exception {
		super(ReceptionCI.class, pluginURI, owner);
		assert owner instanceof SubscriptionImplementationI;
	}

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		try {
			this.getOwner().runTask(owner -> {
				((Subscriber) owner).acceptMessage(m);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		try {
			this.getOwner().runTask(owner -> {
				((Subscriber) owner).acceptMessage(ms);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
