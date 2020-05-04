package deployment;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

public class DistributedCVM extends AbstractDistributedCVM {

	// Uris des composants
	protected static final String BROKER_COMPONENT_URI = "my-URI-broker";
	protected static final String PUBLISHER_COMPONENT_URI = "my-URI-publisher";
	protected static final String SUBSCRIBER_COMPONENT_URI = "my-URI-subscriber";

	// Uris des jvms
	protected static String BROKER_JVM_URI = "broker";
	protected static String PUBLISHER_JVM_URI = "publisher";
	protected static String SUBSCRIBER_JVM_URI = "subscriber";

	// Uris des ports
	protected final static String brokerPIP_uri = "brokerpip";
	protected final static String brokerMIP_uri = "brokermip";
	protected final static String brokerMIP2_uri = "brokermip2";

	protected final static String subscriberMOP_uri = "subscribermop";

	protected final static String publisherPOP_uri = "publisherpop";
	protected final static String publisherMOP_uri = "publishermop";

	// Références au composants, partagés entre deploy et shutdown
	protected String uriBrokerURI;
	protected String uriPublisherURI;
	protected String uriSubscriberURI;

	public DistributedCVM(String[] args, int xLayout, int yLayout) throws Exception {
		super(args, xLayout, yLayout);
	}

	@Override
	public void instantiateAndPublish() throws Exception {
		if (thisJVMURI.equals(BROKER_JVM_URI)) {
			this.uriBrokerURI = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
					new Object[] { BROKER_COMPONENT_URI, brokerMIP_uri, brokerMIP2_uri, brokerPIP_uri });
			assert this.isDeployedComponent(this.uriBrokerURI);

			this.toggleTracing(this.uriBrokerURI);
			assert this.uriBrokerURI != null && this.uriSubscriberURI == null && this.uriPublisherURI == null;

		} else if (thisJVMURI.equals(PUBLISHER_JVM_URI)) {
			this.uriPublisherURI = AbstractComponent.createComponent(Publisher.class.getCanonicalName(), new Object[] {
					PUBLISHER_COMPONENT_URI, publisherPOP_uri, publisherMOP_uri, brokerPIP_uri, brokerMIP2_uri });
			assert this.isDeployedComponent(this.uriPublisherURI);

			this.toggleTracing(this.uriPublisherURI);
			assert this.uriBrokerURI == null && this.uriSubscriberURI == null && this.uriPublisherURI != null;

		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
			this.uriSubscriberURI = AbstractComponent.createComponent(Subscriber.class.getCanonicalName(),
					new Object[] { SUBSCRIBER_COMPONENT_URI, subscriberMOP_uri, brokerMIP_uri });
			assert this.isDeployedComponent(this.uriSubscriberURI);

			this.toggleTracing(this.uriSubscriberURI);
			assert this.uriBrokerURI == null && this.uriSubscriberURI != null && this.uriPublisherURI == null;
		} else {
			System.out.println("Unknown JVM URI... " + thisJVMURI);
		}

		super.instantiateAndPublish();
	}

//	@Override
//	public void interconnect() throws Exception, RemoteException {
//		assert this.isIntantiatedAndPublished();
//
//		if (thisJVMURI.equals(BROKER_JVM_URI)) {
//			assert this.uriBrokerURI != null && this.uriPublisherURI == null && this.uriSubscriberURI == null;
//		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
//			this.doPortConnection(this.uriSubscriberURI, subscriberMOP_uri, brokerMIP_uri,
//					ManagementConnector.class.getCanonicalName());
//		} else if (thisJVMURI.equals(PUBLISHER_JVM_URI)) {
//			this.doPortConnection(this.uriPublisherURI, publisherPOP_uri, brokerPIP_uri,
//					PublicationConnector.class.getCanonicalName());
//			this.doPortConnection(this.uriPublisherURI, publisherMOP_uri, brokerMIP2_uri,
//					ManagementConnector.class.getCanonicalName());
//		} else {
//			System.out.println("Unknown JVM URI... " + thisJVMURI);
//		}
//
//		super.interconnect();
//	}

//	/**
//	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#finalise()
//	 */
//	@Override
//	public void finalise() throws Exception {
//
//		if (thisJVMURI.equals(BROKER_JVM_URI)) {
//			assert this.uriBrokerURI != null && this.uriSubscriberURI == null && this.uriPublisherURI == null;
//			// nothing to be done on the provider side
//		} else if (thisJVMURI.equals(SUBSCRIBER_JVM_URI)) {
//			this.doPortDisconnection(this.uriSubscriberURI, subscriberMOP_uri);
//		} else {
//			this.doPortDisconnection(this.uriPublisherURI, publisherMOP_uri);
//			this.doPortDisconnection(this.uriPublisherURI, publisherPOP_uri);
//		}
//
//		super.finalise();
//	}

	public static void main(String[] args) {
		try {
			DistributedCVM da = new DistributedCVM(args, 2, 5);
			da.startStandardLifeCycle(15000L);
			Thread.sleep(10000L);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
//-----------------------------------------------------------------------------
