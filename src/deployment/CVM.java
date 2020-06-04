package deployment;

import components.DynamicAssembler;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * CVM
 * 
 * @author Bello Velly
 *
 */
public class CVM extends AbstractCVM {

	// single-JVM execution
	protected static String ASSEMBLER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String PUBLISHER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String SUBSCRIBER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String BROKER_JVM_URI = AbstractCVM.thisJVMURI;

	// Uris des ports
	public final static String brokerPIP_uri = "brokerpip";
	public final static String brokerMIP_uri = "brokermip";
	public final static String brokerMIP2_uri = "brokermip2";

	public final static String subscriberMOP_uri = "subscribermop";

	public final static String publisherPOP_uri = "publisherpop";
	public final static String publisherMOP_uri = "publishermop";

	public CVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {
		// on créer le DynamicAssembler
		@SuppressWarnings("unused")
		String da = AbstractComponent.createComponent(DynamicAssembler.class.getCanonicalName(),
				new Object[] { SUBSCRIBER_JVM_URI, BROKER_JVM_URI, PUBLISHER_JVM_URI });
		super.deploy();
	}

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
