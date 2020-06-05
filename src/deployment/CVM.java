package deployment;

import components.DynamicAssembler;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * Exemple de deploiement en Mono JVM, l'exemple contient un Broker, un
 * Subscriber et un Publisher. Les composants sont déployés grave au
 * DynamicAssembler @see components.DynamicAssembler
 * 
 * @author Bello Velly
 *
 */
public class CVM extends AbstractCVM {

	// single-JVM execution
	/**
	 * URI de jvm de l'assembler
	 */
	protected static String ASSEMBLER_JVM_URI = AbstractCVM.thisJVMURI;
	/**
	 * URI de jvm du publisher
	 */
	protected static String PUBLISHER_JVM_URI = AbstractCVM.thisJVMURI;
	/**
	 * URI de jvm du subscriber
	 */
	protected static String SUBSCRIBER_JVM_URI = AbstractCVM.thisJVMURI;
	/**
	 * URI de jvm du broker
	 */
	protected static String BROKER_JVM_URI = AbstractCVM.thisJVMURI;

	// Uris des ports
	/**
	 * URI du port entrant de publication du broker
	 */
	public final static String brokerPIP_uri = "brokerpip";
	/**
	 * URI du port entrant de management du broker
	 */
	public final static String brokerMIP_uri = "brokermip";
	/**
	 * URI du deuxieme port entrant de management du broker
	 */
	public final static String brokerMIP2_uri = "brokermip2";

	/**
	 * URI du pour sortant de management du subscriber
	 */
	public final static String subscriberMOP_uri = "subscribermop";

	/**
	 * URI du port sortant de publication du publisher
	 */
	public final static String publisherPOP_uri = "publisherpop";
	/**
	 * URI du port sortant de management du publisher
	 */
	public final static String publisherMOP_uri = "publishermop";

	/**
	 * Constructeur de CVM
	 */
	public CVM() throws Exception {
		super();
	}

	/**
	 * Créer le DynamicAssembler et le déploie
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#deploy()
	 */
	@Override
	public void deploy() throws Exception {
		// on créer le DynamicAssembler
		@SuppressWarnings("unused")
		String da = AbstractComponent.createComponent(DynamicAssembler.class.getCanonicalName(),
				new Object[] { SUBSCRIBER_JVM_URI, BROKER_JVM_URI, PUBLISHER_JVM_URI });
		super.deploy();
	}

	/**
	 * Point d'entré du programme
	 * 
	 * @param args arguments du prgramme
	 */
	public static void main(String[] args) {
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(10000L);
			Thread.sleep(10000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
