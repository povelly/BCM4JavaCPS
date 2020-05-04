package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import message.Message;
import plugins.PublisherClientPlugin;
import utils.Log;

/**
 * Classe representant le composant publieur
 * 
 * @author Bello Velly
 *
 */

public class Publisher extends AbstractComponent {

	protected PublisherClientPlugin publisherPlugin;

	protected Publisher(String uri, String popURI, String mopURI, String pipURI, String mipURI) throws Exception {
		super(uri, 1, 0);

		this.tracer.setTitle(uri);
		this.tracer.setRelativePosition(0, 3);

		// verifications
		assert pipURI != null;
		assert mipURI != null;

		// plugin
		publisherPlugin = new PublisherClientPlugin(popURI, mopURI, pipURI, mipURI);
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
		// envoie 20 messages basics de tests
		Thread.sleep(4000);
		Log.printAndLog(this, "début envoie messages publisher");
		for (int i = 0; i < 20; i++) {
			publisherPlugin.publish(new Message(null, "msg_i_" + i), "topic2");
		}

		// test d'autres scénarios
		publisherPlugin.publish(new Message(null, "msg2"), new String[] { "topic2", "topic3" });
		publisherPlugin.publish(new Message[] { new Message(null, "msg3"), new Message(null, "msg4") }, "topic2");
		publisherPlugin.publish(new Message[] { new Message(null, "msg5"), new Message(null, "msg6") },
				new String[] { "topic2", "topic3" });
		publisherPlugin.publish(new Message(null, "msg7"), "topic1");
		publisherPlugin.publish(new Message(null, "msg8"), "topic4");
	}

}
