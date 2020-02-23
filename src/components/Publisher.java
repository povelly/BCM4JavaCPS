package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import message.Message;
import plugins.PublisherPlugin;

/**
 * Classe representant le composant publieur
 * 
 * @author Bello Velly
 *
 */

public class Publisher extends AbstractComponent {

	protected PublisherPlugin publisherPlugin;

	protected Publisher(String pipURI, String mipURI) throws Exception {
		super(1, 0);

		// verifications
		assert pipURI != null;
		assert mipURI != null;

		// plugin
		publisherPlugin = new PublisherPlugin(pipURI, mipURI);
		publisherPlugin.setPluginURI(AbstractPort.generatePortURI());
		this.installPlugin(publisherPlugin);
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void execute() throws Exception {
		Thread.sleep(2000); // pour attendre que subscriber ai souscrit avant d'envoyer message
		super.execute();
		// on envoie un message de test
		publisherPlugin.publish(new Message(null, "msg1"), "topic1");
		publisherPlugin.publish(new Message(null, "msg2"), new String[] { "topic1", "topic2" });
		publisherPlugin.publish(new Message[] { new Message(null, "msg3"), new Message(null, "msg4") }, "topic1");
		publisherPlugin.publish(new Message[] { new Message(null, "msg5"), new Message(null, "msg6") },
				new String[] { "topic1", "topic2" });
		publisherPlugin.publish(new Message(null, "msg7"), "topic2");

	}

}
