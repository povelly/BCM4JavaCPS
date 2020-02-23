package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import interfaces.MessageI;
import interfaces.ReceptionCI;
import plugins.SubscriberPlugin;

/**
 * Classe representant le composant souscriveur
 * 
 * @author Bello Velly
 *
 */

public class Subscriber extends AbstractComponent implements ReceptionCI {

	protected SubscriberPlugin subscriberPlugin;
	protected String ripUri;

	// TODO verif les histoires d'uris, surtout ripUri
	protected Subscriber(String mipServerUri) throws Exception {
		super(1, 0);

		// verifications
		assert mipServerUri != null;

		// plugins
		this.ripUri = AbstractPort.generatePortURI();
		this.subscriberPlugin = new SubscriberPlugin(mipServerUri, ripUri);
		subscriberPlugin.setPluginURI(AbstractPort.generatePortURI()); // TODO verif si on genere comme ca l'uri ??
		this.installPlugin(this.subscriberPlugin);
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void execute() throws Exception {
		super.execute();
		// souscrit a un topic du broker en passant son port pour pouvoir etre contacté
		subscriberPlugin.subscribe("topic1", ripUri);
		subscriberPlugin.subscribe("topic2", m -> (false), ripUri);
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void acceptMessage(MessageI m) {
		try {
			System.out.println("Message reçu :\nPortURI : " + this.ripUri + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		try {
			for (MessageI m : ms)
				System.out.println("Message reçu :\nPortURI : " + this.ripUri + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
