package deployment;

import java.rmi.RemoteException;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import connectors.ManagementConnector;
import connectors.PublicationConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
import fr.sorbonne_u.components.helpers.CVMDebugModes;

public class DistributedCVM extends AbstractDistributedCVM {

	protected static final String BROKER_COMPONENT_URI = "my-URI-broker";
	protected static final String PUBLISHER_COMPONENT_URI = "my-URI-publisher";
	protected static final String SUBSCRIBER_COMPONENT_URI = "my-URI-subscriber";

	// URI of the CVM instances as defined in the config.xml file
	protected static String BROKER_JVM_URI = "broker";
	protected static String PUBLISHER_JVM_URI = "publisher";
	protected static String SUBSCRIBER_JVM_URI = "subscriber";

	public final static String brokerPIP_uri = "brokerpipbla";
	public final static String brokerMIP_uri = "brokermipbla";
	public final static String brokerMIP2_uri = "brokermip2blabla";

	public final static String subscriberMOP_uri = "subscribermopbla";

	public final static String publisherPOP_uri = "publisherpopbla";
	public final static String publisherMOP_uri = "publishermopbla";

	/**
	 * Reference to the broker component to share between deploy and shutdown.
	 */
	protected String uriBrokerURI;
	/**
	 * Reference to the publisher component to share between deploy and shutdown.
	 */
	protected String uriPublisherURI;
	/**
	 * Reference to the subscriber component to share between deploy and shutdown.
	 */
	protected String uriSubscriberURI;

	public DistributedCVM(String[] args, int xLayout, int yLayout) throws Exception {

		super(args, xLayout, yLayout);
	}

	/**
	 * do some initialisation before anything can go on.
	 * 
	 */
	@Override
	public void initialise() throws Exception {
		// debugging mode configuration; comment and uncomment the line to see
		// the difference
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING);
		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING);
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT);
//
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CALLING);

		super.initialise();
		// any other application-specific initialisation must be put here
	}

	/**
	 * instantiate components and publish their ports.
	 * 
	 */
	@Override
	public void instantiateAndPublish() throws Exception {
		if (thisJVMURI.equals(BROKER_JVM_URI)) {

			// create the broker component
			this.uriBrokerURI = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
					new Object[] { BROKER_COMPONENT_URI, brokerMIP_uri, brokerMIP2_uri, brokerPIP_uri });
			System.out.println("uri broker : " + uriBrokerURI);
			assert this.isDeployedComponent(this.uriBrokerURI);
			// make it trace its operations; comment and uncomment the line to see
			// the difference
			this.toggleTracing(this.uriBrokerURI);
			assert this.uriBrokerURI != null && this.uriSubscriberURI == null && this.uriPublisherURI == null;

		} else if (thisJVMURI.equals(PUBLISHER_JVM_URI)) {
			this.uriPublisherURI = AbstractComponent.createComponent(Publisher.class.getCanonicalName(), new Object[] {
					PUBLISHER_COMPONENT_URI, publisherPOP_uri, publisherMOP_uri, brokerPIP_uri, brokerMIP2_uri });
			this.toggleTracing(this.uriPublisherURI);
			assert this.isDeployedComponent(this.uriPublisherURI);
			assert this.uriBrokerURI == null && this.uriSubscriberURI == null && this.uriPublisherURI != null;

		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
			this.uriSubscriberURI = AbstractComponent.createComponent(Subscriber.class.getCanonicalName(),
					new Object[] { SUBSCRIBER_COMPONENT_URI, subscriberMOP_uri, brokerMIP_uri });
			this.toggleTracing(this.uriSubscriberURI);

			assert this.isDeployedComponent(this.uriSubscriberURI);
			assert this.uriBrokerURI == null && this.uriSubscriberURI != null && this.uriPublisherURI == null;
		} else {
			System.out.println("Unknown JVM URI... " + thisJVMURI);
		}

		super.instantiateAndPublish();
	}

	/**
	 * interconnect the components.
	 * 
	 */
	@Override
	public void interconnect() throws Exception, RemoteException {
		assert this.isIntantiatedAndPublished();

		if (thisJVMURI.equals(BROKER_JVM_URI)) {
			assert this.uriBrokerURI != null && this.uriPublisherURI == null && this.uriSubscriberURI == null;
		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
			this.doPortConnection(this.uriSubscriberURI, subscriberMOP_uri, brokerMIP_uri,
					ManagementConnector.class.getCanonicalName());
		} else if (thisJVMURI.equals(PUBLISHER_JVM_URI)) {
			this.doPortConnection(this.uriPublisherURI, publisherPOP_uri, brokerPIP_uri,
					PublicationConnector.class.getCanonicalName());
			this.doPortConnection(this.uriPublisherURI, publisherMOP_uri, brokerMIP2_uri,
					ManagementConnector.class.getCanonicalName());
		} else {
			System.out.println("Unknown JVM URI... " + thisJVMURI);
		}

		super.interconnect();
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#finalise()
	 */
	@Override
	public void finalise() throws Exception {

		if (thisJVMURI.equals(BROKER_JVM_URI)) {
			assert this.uriBrokerURI != null && this.uriSubscriberURI == null && this.uriPublisherURI == null;
			// nothing to be done on the provider side
		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
			this.doPortDisconnection(this.uriSubscriberURI, subscriberMOP_uri);
		} else {
			this.doPortDisconnection(this.uriPublisherURI, publisherMOP_uri);
			this.doPortDisconnection(this.uriPublisherURI, publisherPOP_uri);
		}

		super.finalise();
	}

	public static void main(String[] args) {
		try {
			DistributedCVM da = new DistributedCVM(args, 2, 5);
			da.startStandardLifeCycle(5000L);
			Thread.sleep(10000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
//-----------------------------------------------------------------------------
