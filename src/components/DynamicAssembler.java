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
 * Class reprensentant l'assembleur dynamique pour les composants, exemple CVM
 * non distribuée
 * 
 * @author Bello Velly
 *
 */
@RequiredInterfaces(required = { DynamicComponentCreationI.class })
public class DynamicAssembler extends AbstractComponent {

	/**
	 * URI du composant Broker
	 */
	protected static final String BROKER_COMPONENT_URI = "my-URI-broker";

	/**
	 * URI du composant Publisher
	 */
	protected static final String PUBLISHER_COMPONENT_URI = "my-URI-publisher";

	/**
	 * URI du composant Subscriber
	 */
	protected static final String SUBSCRIBER_COMPONENT_URI = "my-URI-subscriber";

	/**
	 * Préfix d'URI
	 */
	protected static final String PROVIDED_URI_PREFIX = "generated-URI-";

	/**
	 * Contient les URIs des composants déployés
	 */
	protected Set<String> deployerURISet;

	/**
	 * Port de creation de composant pour Subscriber
	 */
	protected DynamicComponentCreationOutboundPort portToSubscriberJVM;

	/**
	 * Port de creation de composant pour Broker
	 */
	protected DynamicComponentCreationOutboundPort portToBrokerJVM;

	/**
	 * Port de creation de composant pour Publisher
	 */
	protected DynamicComponentCreationOutboundPort portToPublisherJVM;

	// JVMURIs

	/**
	 * URI de la jvm du subscriber
	 */
	protected String subscriberJVMURI;

	/**
	 * URI de la jvm du broker
	 */
	protected String brokerJVMURI;

	/**
	 * URI de la jvm du publisher
	 */
	protected String publisherJVMURI;

	/**
	 * constructeur de DynamicAssembler
	 * 
	 * @param subscriberJVMURI @see {@link #subscriberJVMURI}
	 * @param brokerJVMURI     @see {@link #brokerJVMURI}
	 * @param publisherJVMURI  @see {@link #publisherJVMURI}
	 */
	protected DynamicAssembler(String subscriberJVMURI, String brokerJVMURI, String publisherJVMURI) {
		super(1, 0);
		this.subscriberJVMURI = subscriberJVMURI;
		this.brokerJVMURI = brokerJVMURI;
		this.publisherJVMURI = publisherJVMURI;
	}

	/**
	 * Création / Publication / Connexion des ports pour creation des composants
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
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

	/**
	 * Creation des composants
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#execute()
	 */
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
		String brokerUri = this.portToBrokerJVM.createComponent(Broker.class.getCanonicalName(),
				new Object[] { BROKER_COMPONENT_URI, CVM.brokerMIP_uri, CVM.brokerMIP2_uri, CVM.brokerPIP_uri });
		deployerURISet.add(brokerUri);

		String subscriberUri = this.portToSubscriberJVM.createComponent(Subscriber.class.getCanonicalName(),
				new Object[] { SUBSCRIBER_COMPONENT_URI, CVM.subscriberMOP_uri, CVM.brokerMIP_uri });
		deployerURISet.add(subscriberUri);

		String publisherUri = this.portToPublisherJVM.createComponent(Publisher.class.getCanonicalName(),
				new Object[] { PUBLISHER_COMPONENT_URI, CVM.publisherPOP_uri, CVM.publisherMOP_uri, CVM.brokerPIP_uri,
						CVM.brokerMIP2_uri });
		deployerURISet.add(publisherUri);

		// on appel start sur les composants
		portToSubscriberJVM.startComponent(subscriberUri);
		portToPublisherJVM.startComponent(publisherUri);
		portToBrokerJVM.startComponent(brokerUri);

		// on execute les composants
		portToSubscriberJVM.executeComponent(subscriberUri);
		Thread.sleep(2000);
		portToPublisherJVM.executeComponent(publisherUri);
	}

	/**
	 * Deconnection des ports de creation des composants
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
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

	/**
	 * Dépublication des ports pour creation des composants
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
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

	/**
	 * Dépublication des ports pour creation des composants
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdownNow()
	 */
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
