package deployment;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * CVM
 * 
 * @author Bello Velly
 *
 */
public class StaticCVM extends AbstractCVM {

	protected static final String BROKER_COMPONENT_URI = "my-URI-broker";
	protected static final String PUBLISHER_COMPONENT_URI = "my-URI-publisher";
	protected static final String SUBSCRIBER_COMPONENT_URI = "my-URI-subscriber";

	// single-JVM execution
	protected static String ASSEMBLER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String PUBLISHER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String SUBSCRIBER_JVM_URI = AbstractCVM.thisJVMURI;
	protected static String BROKER_JVM_URI = AbstractCVM.thisJVMURI;

	// InboundPort uris
	public final static String brokerPIP_uri = AbstractPort.generatePortURI();
	public final static String brokerMIP_uri = AbstractPort.generatePortURI();
	public final static String brokerMIP2_uri = AbstractPort.generatePortURI();

	public StaticCVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {
		String brokerUri = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { BROKER_COMPONENT_URI, CVM.brokerMIP_uri, CVM.brokerMIP2_uri, CVM.brokerPIP_uri });
		this.toggleTracing(brokerUri);
		this.toggleLogging(brokerUri);

		String subscriberUri = AbstractComponent.createComponent(Subscriber.class.getCanonicalName(),
				new Object[] { SUBSCRIBER_COMPONENT_URI, CVM.brokerMIP_uri });
		this.toggleTracing(subscriberUri);
		this.toggleLogging(subscriberUri);

		String publisherUri = AbstractComponent.createComponent(Publisher.class.getCanonicalName(),
				new Object[] { PUBLISHER_COMPONENT_URI, CVM.brokerPIP_uri, CVM.brokerMIP2_uri });
		this.toggleTracing(publisherUri);
		this.toggleLogging(publisherUri);
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
