package deployment;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

/**
 * Exemple de déploiement multi-jvm. L'exemple contient deux jvm, composant
 * chacune un Broker, un Subscriber et un Publisher
 * 
 * @author Bello Velly
 *
 */
public class DistributedCVM extends AbstractDistributedCVM {

	// Uris des composants
	/**
	 * URI du broker de la jvm1
	 */
	protected static final String BROKER_JVM1_COMPONENT_URI = "broker_jvm1";
	/**
	 * URI du broker de la jvm2
	 */
	protected static final String BROKER_JVM2_COMPONENT_URI = "broker_jvm2";

	/**
	 * URI du publisher de la jvm1
	 */
	protected static final String PUBLISHER1_JVM1_COMPONENT_URI = "publisher1_jvm1";
	/**
	 * URI du publisher de la jvm2
	 */
	protected static final String PUBLISHER1_JVM2_COMPONENT_URI = "publisher1_jvm2";

	/**
	 * URI du subscriber de la jvm1
	 */
	protected static final String SUBSCRIBER1_JVM1_COMPONENT_URI = "subscriber1_jvm1";
	/**
	 * URI du subscriber de la jvm2
	 */
	protected static final String SUBSCRIBER1_JVM2_COMPONENT_URI = "subscriber1_jvm2";

	// Uris des jvms
	/**
	 * URI de la jvm1
	 */
	protected static String JVM1_URI = "jvm1";
	/**
	 * URI de la jvm2
	 */
	protected static String JVM2_URI = "jvm2";

	// Uris des ports
	/**
	 * URI du port entrant de publication du broker de la jvm1
	 */
	protected final static String broker_jvm1_PIP_uri = "broker1pip";
	/**
	 * URI du port entrant de management du broker de la jvm1
	 */
	protected final static String broker_jvm1_MIP_uri = "broker1mip";
	/**
	 * URI du deuxieme port entrant de management du broker de la jvm1
	 */
	protected final static String broker_jvm1_MIP2_uri = "broker1mip2";
	/**
	 * URI du port sortant de publication du broker de la jvm1
	 */
	protected final static String broker_jvm1_POP_uri = "broker1pop";

	/**
	 * URI du port entrant de publication du broker de la jvm2
	 */
	protected final static String broker_jvm2_PIP_uri = "broker2pip";
	/**
	 * URI du port entrant de management du broker de la jvm2
	 */
	protected final static String broker_jvm2_MIP_uri = "broker2mip";
	/**
	 * URI du deuxieme port entrant de management du broker de la jvm2
	 */
	protected final static String broker_jvm2_MIP2_uri = "broker2mip2";
	/**
	 * URI du port sortant de publication du broker de la jvm2
	 */
	protected final static String broker_jvm2_POP_uri = "broker2pop";

	/**
	 * URI du port sortant de managemen du subscriber de la jvm1
	 */
	protected final static String subscriber1_jvm1_MOP_uri = "subscribermop";
	/**
	 * URI du port sortant de managemen du subscriber de la jvm2
	 */
	protected final static String subscriber1_jvm2_MOP_uri = "subscriber2mop";

	/**
	 * URI du port sortant de publication du publisher de la jvm1
	 */
	protected final static String publisher1_jvm1_POP_uri = "publisher1pop";
	/**
	 * URI du port sortant de management du publisher de la jvm1
	 */
	protected final static String publisher1_jvm1_MOP_uri = "publisher1mop";

	/**
	 * URI du port sortant de publication du publisher de la jvm2
	 */
	protected final static String publisher1_jvm2_POP_uri = "publisher2pop";
	/**
	 * URI du port sortant de management du publisher de la jvm2
	 */
	protected final static String publisher1_jvm2_MOP_uri = "publisher2mop";

	/**
	 * référence a l'uri du composant broker de la jvm1
	 */
	protected String comp_broker_jvm1_uri;
	/**
	 * référence a l'uri du composant broker de la jvm2
	 */
	protected String comp_broker_jvm2_uri;

	/**
	 * reférence a l'uri du composant subscriber de la jvm1
	 */
	protected String comp_subscriber1_jvm1_uri;
	/**
	 * reférence a l'uri du composant subscriber de la jvm2
	 */
	protected String comp_subscriber1_jvm2_uri;

	/**
	 * reférence a l'uri du composant publisher de la jvm1
	 */
	protected String comp_publisher1_jvm1_uri;
	/**
	 * reférence a l'uri du composant publisher de la jvm2
	 */
	protected String comp_publisher1_jvm2_uri;

	/**
	 * constructeur de DistributedCVM
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#AbstractDistributedCVM(String[],
	 *      int, int)
	 */
	public DistributedCVM(String[] args, int xLayout, int yLayout) throws Exception {
		super(args, xLayout, yLayout);
	}

	/**
	 * Créer les composants appropriés selon la jvm sur laquelle on se trouve
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#instantiateAndPublish()
	 */
	@Override
	public void instantiateAndPublish() throws Exception {

		if (thisJVMURI.equals(JVM1_URI)) {
			// Broker
			this.comp_broker_jvm1_uri = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
					new Object[] { BROKER_JVM1_COMPONENT_URI, broker_jvm1_MIP_uri, broker_jvm1_MIP2_uri,
							broker_jvm1_PIP_uri, broker_jvm1_POP_uri, broker_jvm2_PIP_uri });
			this.toggleTracing(this.comp_broker_jvm1_uri);
			assert this.isDeployedComponent(comp_broker_jvm1_uri);
			// Subscriber
			this.comp_subscriber1_jvm1_uri = AbstractComponent.createComponent(Subscriber.class.getCanonicalName(),
					new Object[] { SUBSCRIBER1_JVM1_COMPONENT_URI, subscriber1_jvm1_MOP_uri, broker_jvm1_MIP_uri });
			this.toggleTracing(this.comp_subscriber1_jvm1_uri);
			assert this.isDeployedComponent(this.comp_subscriber1_jvm1_uri);
			// Publisher
			this.comp_publisher1_jvm1_uri = AbstractComponent.createComponent(Publisher.class.getCanonicalName(),
					new Object[] { PUBLISHER1_JVM1_COMPONENT_URI, publisher1_jvm1_POP_uri, publisher1_jvm1_MOP_uri,
							broker_jvm1_PIP_uri, broker_jvm1_MIP2_uri });
			this.toggleTracing(this.comp_publisher1_jvm1_uri);
			assert this.isDeployedComponent(this.comp_publisher1_jvm1_uri);

			assert comp_broker_jvm1_uri != null && comp_subscriber1_jvm1_uri != null
					&& comp_publisher1_jvm1_uri != null;
			assert comp_broker_jvm2_uri == null && comp_subscriber1_jvm2_uri == null
					&& comp_publisher1_jvm2_uri == null;
		} else if (thisJVMURI.equals(JVM2_URI)) {
			// Broker
			this.comp_broker_jvm2_uri = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
					new Object[] { BROKER_JVM2_COMPONENT_URI, broker_jvm2_MIP_uri, broker_jvm2_MIP2_uri,
							broker_jvm2_PIP_uri, broker_jvm2_POP_uri, broker_jvm1_PIP_uri });
			this.toggleTracing(this.comp_broker_jvm2_uri);
			assert this.isDeployedComponent(comp_broker_jvm2_uri);
			// Subscriber
			this.comp_subscriber1_jvm2_uri = AbstractComponent.createComponent(Subscriber.class.getCanonicalName(),
					new Object[] { SUBSCRIBER1_JVM2_COMPONENT_URI, subscriber1_jvm2_MOP_uri, broker_jvm2_MIP_uri });
			this.toggleTracing(this.comp_subscriber1_jvm2_uri);
			assert this.isDeployedComponent(this.comp_subscriber1_jvm2_uri);
			// Publisher
			this.comp_publisher1_jvm2_uri = AbstractComponent.createComponent(Publisher.class.getCanonicalName(),
					new Object[] { PUBLISHER1_JVM2_COMPONENT_URI, publisher1_jvm2_POP_uri, publisher1_jvm2_MOP_uri,
							broker_jvm2_PIP_uri, broker_jvm2_MIP2_uri });
			this.toggleTracing(this.comp_publisher1_jvm2_uri);
			assert this.isDeployedComponent(this.comp_publisher1_jvm2_uri);

			assert comp_broker_jvm2_uri != null && comp_subscriber1_jvm2_uri != null
					&& comp_publisher1_jvm2_uri != null;
			assert comp_broker_jvm1_uri == null && comp_subscriber1_jvm1_uri == null
					&& comp_publisher1_jvm1_uri == null;
		} else {
			System.out.println("Unknown JVM URI... " + thisJVMURI);
		}

		super.instantiateAndPublish();
	}

	/**
	 * Point d'entré du programme
	 * 
	 * @param args arguments du prgramme
	 */
	public static void main(String[] args) {
		try {
			DistributedCVM da = new DistributedCVM(args, 2, 5);
			da.startStandardLifeCycle(20000L);
			Thread.sleep(20000L);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
//-----------------------------------------------------------------------------
