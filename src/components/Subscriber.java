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
		super(4, 0);

		// verifications
		assert mipServerUri != null;

		// plugins
		this.ripUri = AbstractPort.generatePortURI();
		this.subscriberPlugin = new SubscriberPlugin(mipServerUri, ripUri);
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

		System.out.println("Test des méthodes de ManagementImplementationI :\n");
		// test des méthodes de création de topics + isTopic
		System.out.println("Test createTopic(\"topic1\") + isTopic");
		subscriberPlugin.createTopic("topic1");
		System.out.println("topic 1 créer : " + subscriberPlugin.isTopic("topic1"));

		System.out.println("Test createTopic({\"topic2\", \"topic3\"}) + isTopic");
		subscriberPlugin.createTopics(new String[] { "topic2", "topic3" });
		System.out.println("topic 2 et 3 créer : " + subscriberPlugin.isTopic("topic2") + " / "
				+ subscriberPlugin.isTopic("topic3"));

		// test getTopics
		String[] topics = subscriberPlugin.getTopics();
		System.out.println("\ntest getTopics");
		for (String s : topics)
			System.out.println(s);
		System.out.println("Si ok, ce sont affichés topic1, topic2 et topic3");

		// test destroyTopic
		System.out.println("\ntest destroyTopic(\"topic1\")");
		subscriberPlugin.destroyTopic("topic1");
		Thread.sleep(500);
		System.out.println("topic1 existe encore :" + subscriberPlugin.isTopic("topic1"));

		// tests des méthodes de subscription :
		System.out.println(
				"\n\nrecreation de topic1 puis test des méthodes des méthodes de PublicationCI, ReceptionCI, et SubscriptionImplementationI :");

		// souscrit a un topic du broker en passant son port pour pouvoir etre contacté
		subscriberPlugin.subscribe("topic1", m -> (false), ripUri);
		subscriberPlugin.subscribe(new String[] { "topic2", "topic3" }, ripUri);
		subscriberPlugin.subscribe("topic4", ripUri);
		subscriberPlugin.unsubscribe("topic4", ripUri);
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void acceptMessage(MessageI m) {
		try {
			System.out.println("Message reçu, thread : " + Thread.currentThread().getId() + "; PortURI : " + this.ripUri
					+ "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void acceptMessage(MessageI[] ms) {
		try {
			for (MessageI m : ms)
				System.out.println("Message reçu, thread : " + Thread.currentThread().getId() + "; PortURI : "
						+ this.ripUri + "; message : " + m.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
