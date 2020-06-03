package deployment;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

public class DistributedCVM extends AbstractDistributedCVM {

	// Uris des composants
	protected static final String BROKER_JVM1_COMPONENT_URI = "broker_jvm1";
	protected static final String BROKER_JVM2_COMPONENT_URI = "broker_jvm2";

	protected static final String PUBLISHER1_JVM1_COMPONENT_URI = "publisher1_jvm1";

	protected static final String SUBSCRIBER1_JVM1_COMPONENT_URI = "subscriber1_jvm1";
	protected static final String SUBSCRIBER1_JVM2_COMPONENT_URI = "subscriber1_jvm2";

	// Uris des jvms
	protected static String JVM1_URI = "jvm1";
	protected static String JVM2_URI = "jvm2";

	// Uris des ports
	protected final static String broker_jvm1_PIP_uri = "broker1pip";
	protected final static String broker_jvm1_MIP_uri = "broker1mip";
	protected final static String broker_jvm1_MIP2_uri = "broker1mip2";
	protected final static String broker_jvm1_POP_uri = "broker1pop";

	protected final static String broker_jvm2_PIP_uri = "broker2pip";
	protected final static String broker_jvm2_MIP_uri = "broker2mip";
	protected final static String broker_jvm2_MIP2_uri = "broker2mip2";
	protected final static String broker_jvm2_POP_uri = "broker2pop";

	protected final static String subscriber1_jvm1_MOP_uri = "subscribermop";
	protected final static String subscriber1_jvm2_MOP_uri = "subscriber2mop";

	protected final static String publisher1_jvm1_POP_uri = "publisher1pop";
	protected final static String publisher1_jvm1_MOP_uri = "publisher1mop";

	// Références au composants, partagés entre deploy et shutdown
	protected String comp_broker_jvm1_uri;
	protected String comp_broker_jvm2_uri;

	protected String comp_subscriber1_jvm1_uri;
	protected String comp_subscriber1_jvm2_uri;

	protected String comp_publisher1_jvm1_uri;

	public DistributedCVM(String[] args, int xLayout, int yLayout) throws Exception {
		super(args, xLayout, yLayout);
	}

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
			assert comp_broker_jvm2_uri == null && comp_subscriber1_jvm2_uri == null;
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

			assert comp_broker_jvm2_uri != null && comp_subscriber1_jvm2_uri != null;
			assert comp_broker_jvm1_uri == null && comp_subscriber1_jvm1_uri == null
					&& comp_publisher1_jvm1_uri == null;
		} else {
			System.out.println("Unknown JVM URI... " + thisJVMURI);
		}

		super.instantiateAndPublish();
	}

	public static void main(String[] args) {
		try {
			DistributedCVM da = new DistributedCVM(args, 2, 5);
			da.startStandardLifeCycle(100000L);
			Thread.sleep(100000L);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
//-----------------------------------------------------------------------------
