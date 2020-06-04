package components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import interfaces.MessageI;
import interfaces.ReceptionImplementationI;
import plugins.SubscriberPlugin;
import utils.Log;

/**
 * Classe representant le composant souscriveur
 * 
 * @author Bello Velly
 *
 */

public class Subscriber extends AbstractComponent implements ReceptionImplementationI {

	protected SubscriberPlugin subscriberPlugin;
	protected String ripUri;

	// TODO verif les histoires d'uris, surtout ripUri
	protected Subscriber(String uri, String mopUri, String mipServerUri) throws Exception {
		super(uri, 4, 1);

		this.tracer.setTitle(uri);
		this.tracer.setRelativePosition(0, 2);

		// verifications
		assert mipServerUri != null;

		// plugins
		this.ripUri = AbstractPort.generatePortURI();
		this.subscriberPlugin = new SubscriberPlugin(mopUri, mipServerUri, ripUri);
		subscriberPlugin.setPluginURI(AbstractPort.generatePortURI());
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
		// test des méthodes de création de topics + isTopic
		this.runTask(component -> {
			try {
				Log.printAndLog(this, "Test createTopic(\"topic1\") + isTopic");
				subscriberPlugin.createTopic("topic1");
				Log.printAndLog(this, "topic 1 créer : " + subscriberPlugin.isTopic("topic1"));

				Log.printAndLog(this, "Test createTopic({\"topic2\", \"topic3\"}) + isTopic");
				subscriberPlugin.createTopics(new String[] { "topic2", "topic3" });
				Log.printAndLog(this, "topic 2 et 3 créer : " + subscriberPlugin.isTopic("topic2") + " / "
						+ subscriberPlugin.isTopic("topic3"));

				// test getTopics
				String[] topics = subscriberPlugin.getTopics();
				Log.printAndLog(this, "\ntest getTopics");
				for (String s : topics)
					Log.printAndLog(this, s);
				Log.printAndLog(this, "Si ok, ce sont affichés topic1, topic2 et topic3");

				// test destroyTopic
				Log.printAndLog(this, "\ntest destroyTopic(\"topic1\")");
				subscriberPlugin.destroyTopic("topic1");
				Thread.sleep(500);
				Log.printAndLog(this, "topic1 existe encore :" + subscriberPlugin.isTopic("topic1"));

				// tests des méthodes de subscription :
				Log.printAndLog(this,
						"\n\nrecreation de topic1 puis test des méthodes des méthodes de PublicationCI, ReceptionCI, et SubscriptionImplementationI :");

				// souscrit a un topic du broker en passant son port pour pouvoir etre contacté
				subscriberPlugin.subscribe("topic1", m -> (false), ripUri);
				subscriberPlugin.subscribe(new String[] { "topic2", "topic3" }, ripUri);
				subscriberPlugin.subscribe("topic4", ripUri);
				subscriberPlugin.unsubscribe("topic4", ripUri);
			} catch (Exception e) {
				Log.printAndLog(this, e.getMessage());
			}
		});
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void acceptMessage(MessageI m) {
		try {
			Log.printAndLog(this,
					"Message reçu, thread : " + Thread.currentThread().getId() + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		try {
			for (MessageI m : ms)
				Log.printAndLog(this,
						"Message reçu, thread : " + Thread.currentThread().getId() + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
