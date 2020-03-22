package components;

import java.util.HashSet;
import java.util.Set;

import deployment.CVM;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.interfaces.DynamicComponentCreationI;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;

/**
 * Class reprensentant l'assembleur dynamique pour les composants
 * 
 * @author Bello Velly
 *
 */
@RequiredInterfaces(required = { DynamicComponentCreationI.class })
public class DynamicAssembler extends AbstractComponent {

	protected static final String PROVIDED_URI_PREFIX = "generated-URI-";

	protected Set<String> deployerURISet;

	// DynamicComponentCreation ports
	protected DynamicComponentCreationOutboundPort portToSubscriberJVM;
	protected DynamicComponentCreationOutboundPort portToBrokerJVM;
	protected DynamicComponentCreationOutboundPort portToPublisherJVM;

	// JVMURIs
	protected String subscriberJVMURI;
	protected String brokerJVMURI;
	protected String publisherJVMURI;

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
			// portToSubscriber
			this.portToSubscriberJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToSubscriberJVM.publishPort();
			this.doPortConnection(this.portToSubscriberJVM.getPortURI(),
					this.subscriberJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

			// portToPublisher
			this.portToPublisherJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToPublisherJVM.publishPort();
			this.doPortConnection(this.portToPublisherJVM.getPortURI(),
					this.subscriberJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

			// portToBroker
			this.portToBrokerJVM = new DynamicComponentCreationOutboundPort(this);
			this.portToBrokerJVM.publishPort();
			this.doPortConnection(this.portToBrokerJVM.getPortURI(),
					this.brokerJVMURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
					DynamicComponentCreationConnector.class.getCanonicalName());

		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
	}

	@Override
	public void execute() throws Exception {
		super.execute();
		assert this.portToSubscriberJVM != null;
		assert this.portToSubscriberJVM.connected();
		assert this.portToBrokerJVM != null;
		assert this.portToBrokerJVM.connected();
		assert this.portToPublisherJVM != null;
		assert this.portToPublisherJVM.connected();
		this.deployerURISet = new HashSet<String>();
		// on va créer les composants
		String BrokerUri = this.portToBrokerJVM.createComponent(Broker.class.getCanonicalName(),
				new Object[] { CVM.brokerMIP_uri, CVM.brokerMIP2_uri, CVM.brokerPIP_uri });
		deployerURISet.add(BrokerUri);

		String subscriberUri = this.portToSubscriberJVM.createComponent(Subscriber.class.getCanonicalName(),
				new Object[] { CVM.brokerMIP_uri });
		deployerURISet.add(subscriberUri);

		String publisherUri = this.portToPublisherJVM.createComponent(Publisher.class.getCanonicalName(),
				new Object[] { CVM.brokerPIP_uri, CVM.brokerMIP2_uri });
		deployerURISet.add(publisherUri);

		// on appel start sur les composants
		portToSubscriberJVM.startComponent(subscriberUri);
		portToPublisherJVM.startComponent(publisherUri);
		portToBrokerJVM.startComponent(BrokerUri);

		// on execute les composants
		portToSubscriberJVM.executeComponent(subscriberUri);
		Thread.sleep(2000);
		portToPublisherJVM.executeComponent(publisherUri);
	}

	@Override
	public void finalise() throws Exception {
		// on deconnecte les ports
		if (this.portToSubscriberJVM.connected())
			this.doPortDisconnection(this.portToSubscriberJVM.getPortURI());

		if (this.portToBrokerJVM.connected())
			this.doPortDisconnection(this.portToBrokerJVM.getPortURI());

		if (this.portToPublisherJVM.connected())
			this.doPortDisconnection(this.portToPublisherJVM.getPortURI());

		super.finalise();
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		// on depublie les ports
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
		/// on depublie les ports
		try {
			this.portToSubscriberJVM.unpublishPort();
			this.portToBrokerJVM.unpublishPort();
			this.portToPublisherJVM.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdownNow();
	}

}
