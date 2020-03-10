package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import message.Message;
import plugins.PublisherClientPlugin;

/**
 * Classe representant le composant publieur
 * 
 * @author Bello Velly
 *
 */

public class Publisher extends AbstractComponent {

	protected PublisherClientPlugin publisherPlugin;

	protected Publisher(String pipURI, String mipURI) throws Exception {
		super(1, 0);

		// verifications
		assert pipURI != null;
		assert mipURI != null;

		// plugin
		publisherPlugin = new PublisherClientPlugin(pipURI, mipURI);
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
		super.execute();
		// envoie 30 messages basics de tests
		for (int i = 0; i < 30; i++)
			publisherPlugin.publish(new Message(null, "msg_i_" + i), "topic1");
		// test d'autres scénarios
		publisherPlugin.publish(new Message(null, "msg2"), new String[] { "topic1", "topic2" });
		publisherPlugin.publish(new Message[] { new Message(null, "msg3"), new Message(null, "msg4") }, "topic1");
		publisherPlugin.publish(new Message[] { new Message(null, "msg5"), new Message(null, "msg6") },
				new String[] { "topic1", "topic2" });
		publisherPlugin.publish(new Message(null, "msg7"), "topic2");

	}

}
