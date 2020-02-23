package port;

import components.Subscriber;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.forplugins.AbstractInboundPortForPlugin;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;

public class ReceptionInboundPortForPlugin extends AbstractInboundPortForPlugin implements ReceptionCI {

	private static final long serialVersionUID = 1L;

	public ReceptionInboundPortForPlugin(String pluginURI, ComponentI owner) throws Exception {
		super(ReceptionCI.class, pluginURI, owner);
		assert owner instanceof ReceptionImplementationI;
	}

	public ReceptionInboundPortForPlugin(String uri, String pluginURI, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, pluginURI, owner);
		assert owner instanceof ReceptionImplementationI;
	}

	// TODO POURQUOI FAIRE CA ???
//	public V get(K key) throws Exception {
//		return this.owner.handleRequestSync(new AbstractComponent.AbstractService<V>(this.pluginURI) {
//			@SuppressWarnings("unchecked")
//			@Override
//			public V call() throws Exception {
//				return ((MapPlugin<K, V>) this.getServiceProviderReference()).get(key);
//			}
//		});
//	}

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
