package components;

import connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.examples.basic_cs.connectors.URIServiceConnector;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerI;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIProviderI;
import fr.sorbonne_u.components.examples.ddeployment_cs.components.DynamicURIConsumer;
import fr.sorbonne_u.components.examples.ddeployment_cs.components.DynamicURIProvider;
import fr.sorbonne_u.components.examples.ddeployment_cs.interfaces.URIConsumerLaunchI;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;
import interfaces.DynamicComponentCreationI;
import port.DynamicComponentCreationOutboundPort;

@RequiredInterfaces(required = { DynamicComponentCreationI.class })
public class DynamicAssembler extends AbstractComponent {

	protected static final String PROVIDED_URI_PREFIX = "generated-URI-";

	protected DynamicComponentCreationOutboundPort portToSubscriberJVM;
	protected DynamicComponentCreationOutboundPort portToBrokerJVM;
	protected DynamicComponentCreationOutboundPort portToPublisherJVM;

	protected String subscriberJVMURI;
	protected String brokerJVMURI;
	protected String publisherJVMURI;

	protected String brokerPIP_uri;
	protected String brokerMIP_uri;
	protected String brokerMIP2_uri;

	protected DynamicAssembler(String subscriberJVMURI, String brokerJVMURI, String publisherJVMURI) {
		super(1, 0);
		this.subscriberJVMURI = subscriberJVMURI;
		this.brokerJVMURI = brokerJVMURI;
		this.publisherJVMURI = publisherJVMURI;
	}

	@Override
	public void start() throws ComponentStartException {
		super.start();

		try {
			this.portToSubscriberJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToSubscriberJVM.localPublishPort();
			this.doPortConnection(this.portToSubscriberJVM.getPortURI(),
					this.subscriberJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

			this.portToPublisherJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToPublisherJVM.localPublishPort();
			this.doPortConnection(this.portToPublisherJVM.getPortURI(),
					this.subscriberJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

			this.portToBrokerJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToBrokerJVM.localPublishPort();
			this.doPortConnection(this.portToBrokerJVM.getPortURI(),
					this.brokerJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

			this.runTask(new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						((DynamicAssembler) this.getTaskOwner()).dynamicDeploy();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});

		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void finalise() throws Exception {
		if (this.portToSubscriberJVM.connected()) {
			this.doPortDisconnection(this.portToSubscriberJVM.getPortURI());
		}
		if (this.portToBrokerJVM.connected()) {
			this.doPortDisconnection(this.portToBrokerJVM.getPortURI());
		}
		if (this.portToPublisherJVM.connected()) {
			this.doPortDisconnection(this.portToPublisherJVM.getPortURI());
		}

		super.finalise();
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			this.portToSubscriberJVM.unpublishPort();
			this.portToBrokerJVM.unpublishPort();
			this.portToPublisherJVM.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			this.portToSubscriberJVM.unpublishPort();
			this.portToBrokerJVM.unpublishPort();
			this.portToPublisherJVM.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdownNow();
	}

	public void dynamicDeploy() throws Exception {
		assert this.portToSubscriberJVM != null;
		assert this.portToSubscriberJVM.connected();
		assert this.portToBrokerJVM != null;
		assert this.portToBrokerJVM.connected();
		assert this.portToPublisherJVM != null;
		assert this.portToPublisherJVM.connected();

//		// call the dynamic component creator of the provider JVM to create
//		// the provider component
//		String providerRIPURI = this.portToPublisherJVM.createComponent(Publisher.class.getCanonicalName(),
//				new Object[] { brokerPIP_uri, brokerMIP_uri });
//
//		// call the dynamic component creator of the consumer JVM to create
//		// the provider component
//		String consumerRIPURI = this.portToSubscriberJVM.createComponent(Subscriber.class.getCanonicalName(),
//				new Object[] { brokerMIP_uri });
//
//		String brokerRIPURI = this.portToSubscriberJVM.createComponent(Broker.class.getCanonicalName(),
//				new Object[] {});
//
//		this.addRequiredInterface(ReflectionI.class);
//		ReflectionOutboundPort rop = new ReflectionOutboundPort(this);
//		rop.localPublishPort();
//
//		// connect to the provider (server) component
//		rop.doConnection(providerRIPURI, ReflectionConnector.class.getCanonicalName());
//		// toggle logging on the provider component
//		rop.toggleTracing();
//		// get the URI of the URI provider inbound port of the provider
//		// component.
//		String[] uris = rop.findInboundPortURIsFromInterface(URIProviderI.class);
//		assert uris != null && uris.length == 1;
//		this.providerInboundPortURI = uris[0];
//		this.doPortDisconnection(rop.getPortURI());
//
//		// connect to the consumer (client) component
//		rop.doConnection(consumerRIPURI, ReflectionConnector.class.getCanonicalName());
//		// toggle logging on the consumer component
//		rop.toggleTracing();
//		// get the URI of the launch inbound port of the consumer component.
//		uris = rop.findInboundPortURIsFromInterface(URIConsumerLaunchI.class);
//		assert uris != null && uris.length == 1;
//		this.consumerLaunchInboundPortURI = uris[0];
//		// get the URI of the URI consumer outbound port of the consumer
//		// component.
//		uris = rop.findOutboundPortURIsFromInterface(URIConsumerI.class);
//		assert uris != null && uris.length == 1;
//		this.consumerOutboundPortURI = uris[0];
//		// connect the consumer outbound port top the provider inbound one.
//		rop.doPortConnection(this.consumerOutboundPortURI, this.providerInboundPortURI,
//				URIServiceConnector.class.getCanonicalName());
//		this.doPortDisconnection(rop.getPortURI());
//		rop.unpublishPort();
//
//		this.runTask(new AbstractComponent.AbstractTask() {
//			@Override
//			public void run() {
//				try {
//					((DynamicAssembler) this.getTaskOwner()).launch();
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			}
//		});
	}

}
