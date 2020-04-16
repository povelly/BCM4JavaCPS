package port;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.forplugins.AbstractInboundPortForPlugin;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import interfaces.ReceptionImplementationI;

/**
 * Port entrant de recepetion pour plugin
 * 
 * @author Bello Velly
 *
 */
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

	@Override
	public void acceptMessage(MessageI m) throws Exception {
		try {
			this.getOwner().runTask(AbstractComponent.STANDARD_REQUEST_HANDLER_URI, owner -> {
				try {
					((ReceptionImplementationI) owner).acceptMessage(m);

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		try {
			this.getOwner().runTask(AbstractComponent.STANDARD_REQUEST_HANDLER_URI, owner -> {
				try {
					((ReceptionImplementationI) owner).acceptMessage(ms);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
