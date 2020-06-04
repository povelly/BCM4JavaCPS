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

	/**
	 * Constructeur de ReceptionInboundPortForPlugin
	 * 
	 * @param pluginUri uri du plugin
	 * @param owner     composant qui possède le port
	 */
	public ReceptionInboundPortForPlugin(String pluginURI, ComponentI owner) throws Exception {
		super(ReceptionCI.class, pluginURI, owner);
		assert owner instanceof ReceptionImplementationI;
	}

	/**
	 * Constructeur de ReceptionInboundPortForPlugin
	 * 
	 * @param pluginUri uri du plugin
	 * @param uri       uri du port
	 * @param owner     composant qui possède le port
	 */
	public ReceptionInboundPortForPlugin(String uri, String pluginURI, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, pluginURI, owner);
		assert owner instanceof ReceptionImplementationI;
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI)
	 */
	@Override
	public void acceptMessage(MessageI m) throws Exception {
		try {
			this.owner.runTask(new AbstractComponent.AbstractTask(this.pluginURI) {
				@Override
				public void run() {
					try {
						((ReceptionImplementationI) this.getTaskProviderReference()).acceptMessage(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.ReceptionImplementationI#acceptMessage(MessageI[])
	 */
	@Override
	public void acceptMessage(MessageI[] ms) throws Exception {
		try {
			this.owner.runTask(new AbstractComponent.AbstractTask(this.pluginURI) {
				@Override
				public void run() {
					try {
						((ReceptionImplementationI) this.getTaskProviderReference()).acceptMessage(ms);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
