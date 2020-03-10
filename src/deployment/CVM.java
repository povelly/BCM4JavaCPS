package deployment;

import components.DynamicAssembler;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

	// single-JVM execution
	protected static String ASSEMBLER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String PUBLISHER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String SUBSCRIBER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String BROKER_JVM_URI = AbstractCVM.thisJVMURI;

	// InboundPort uris
	public final static String brokerPIP_uri = AbstractPort.generatePortURI();
	public final static String brokerMIP_uri = AbstractPort.generatePortURI();
	public final static String brokerMIP2_uri = AbstractPort.generatePortURI();

	public CVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {
		// definitions des uris des ports entrants

		// fonctionnent mais génèrent des erreurs
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING);
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT);

		// n'ont pas l'air de marcher
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING); // log la connexion des ports et des connecteurs
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CALLING); // log la transmission des appels dans les connecteurs et les ports

		// broker
//		String brokerPIP_uri = AbstractPort.generatePortURI(); // pour publisher
//		String brokerMIP_uri = AbstractPort.generatePortURI(); // pour subscriber
//		String brokerMIP2_uri = AbstractPort.generatePortURI(); // pour publisher

		// creation des composants
//		AbstractComponent.createComponent(Broker.class.getCanonicalName(),
//				new Object[] { brokerMIP_uri, brokerMIP2_uri, brokerPIP_uri });
//		AbstractComponent.createComponent(Subscriber.class.getCanonicalName(), new Object[] { brokerMIP_uri });
//		AbstractComponent.createComponent(Publisher.class.getCanonicalName(),
//				new Object[] { brokerPIP_uri, brokerMIP2_uri });

		// on créer le DynamicAssembler
		@SuppressWarnings("unused")
		String da = AbstractComponent.createComponent(DynamicAssembler.class.getCanonicalName(),
				new Object[] { SUBSCRIBER_JVM_URI, BROKER_JVM_URI, PUBLISHER_JVM_URI });
		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(7000L);
			Thread.sleep(7000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
