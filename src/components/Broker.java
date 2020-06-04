package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import connectors.PublicationConnector;
import connectors.ReceptionConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ManagementCI;
import interfaces.ManagementImplementationI;
import interfaces.MessageFilterI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.PublicationsImplementationI;
import interfaces.ReceptionCI;
import interfaces.SubscriptionImplementationI;
import message.Topic;
import port.BrokerManagementInboundPort;
import port.BrokerPublicationInboundPort;
import port.BrokerPublicationOutboundPort;
import port.BrokerReceptionOutboundPort;
import utils.Log;

/**
 * Classe representant le composant courtier
 * 
 * @author Bello Velly
 */

@RequiredInterfaces(required = { ReceptionCI.class, PublicationCI.class })
@OfferedInterfaces(offered = { ManagementCI.class, PublicationCI.class })
public class Broker extends AbstractComponent
		implements ManagementImplementationI, SubscriptionImplementationI, PublicationsImplementationI {

	protected String myUri;

	// Executor services uris
	public static final String RECEPTION_EXECUTOR_URI = "reception";
	public static final String ENVOIE_EXECUTOR_URI = "envoie";

	// ports du composant
	protected List<BrokerReceptionOutboundPort> brops = new ArrayList<>();
	protected BrokerManagementInboundPort bmip;
	protected BrokerManagementInboundPort bmip2;
	protected BrokerPublicationInboundPort bpip;
	protected BrokerPublicationOutboundPort bpop;

	// verrou pour la Map topics
	protected final ReentrantReadWriteLock lock;

	// Map<identifiant du topic, Topic>
	protected Map<String, Topic> topics = new HashMap<>();

	protected String bpip2URI; // pour transferer les publications vers un autre Broker

	// Broker non connecté a un autre Broker
	protected Broker(String uri, String bmipURI, String bmip2URI, String bpipURI) throws Exception {
		super(uri, 1, 0);

		this.tracer.setTitle(uri);
		this.tracer.setRelativePosition(0, 4);

		// Verifications
		assert bmipURI != null;
		assert bmip2URI != null;
		assert bpipURI != null;

		this.myUri = uri;

		// Verrou pour la map, on permet plusieurs lectures simultanées, mais que une
		// ecriture simultanée.
		// De plus, si un écriture à lieu, on interdit toute lecture tans que celle-ci
		// n'est pas finie
		this.lock = new ReentrantReadWriteLock();

		// pool de threads pour l'envoie de messages vers des subscribers
		this.createNewExecutorService(ENVOIE_EXECUTOR_URI, 4, false);
		// pool de threads pour les requetes sur les ports entrant (management +
		// messages reçus)
		this.createNewExecutorService(RECEPTION_EXECUTOR_URI, 4, false);

		// creation des ports
		int executorServiceIndex = this.getExecutorServiceIndex(RECEPTION_EXECUTOR_URI);
		this.bmip = new BrokerManagementInboundPort(bmipURI, executorServiceIndex, this);
		this.bmip.publishPort();
		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, executorServiceIndex, this);
		this.bmip2.publishPort();
		this.bpip = new BrokerPublicationInboundPort(bpipURI, executorServiceIndex, this);
		this.bpip.publishPort();
	}

	// Broker connecté a un autre Broker
	protected Broker(String uri, String bmipURI, String bmip2URI, String bpipURI, String bpopURI, String bpip2URI)
			throws Exception {
		super(uri, 1, 0);

		this.tracer.setTitle(uri);
		this.tracer.setRelativePosition(0, 4);

		// Verifications
		assert bmipURI != null;
		assert bmip2URI != null;
		assert bpipURI != null;
		assert bpopURI != null;
		assert bpip2URI != null;

		// Verrou pour la map, on permet plusieurs lectures simultanées, mais que une
		// ecriture simultanée.
		// De plus, si un écriture à lieu, on interdit toute lecture tans que celle-ci
		// n'est pas finie
		this.lock = new ReentrantReadWriteLock();

		// pool de threads pour l'envoie de messages vers des subscribers
		this.createNewExecutorService(ENVOIE_EXECUTOR_URI, 4, false);
		// pool de threads pour les requetes sur les ports entrant (management +
		// messages reçus)
		this.createNewExecutorService(RECEPTION_EXECUTOR_URI, 4, false);

		this.bpip2URI = bpip2URI;

		// creation des ports
		int executorServiceIndex = this.getExecutorServiceIndex(RECEPTION_EXECUTOR_URI);
		this.bmip = new BrokerManagementInboundPort(bmipURI, executorServiceIndex, this);
		this.bmip.publishPort();
		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, executorServiceIndex, this);
		this.bmip2.publishPort();
		this.bpip = new BrokerPublicationInboundPort(bpipURI, executorServiceIndex, this);
		this.bpip.publishPort();
		this.bpop = new BrokerPublicationOutboundPort(bpopURI, this);
		this.bpop.localPublishPort();
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void start() throws ComponentStartException {
		// Connection sur un autre broker si nécéssaire
		if (bpop != null)
			try {
				this.doPortConnection(bpop.getPortURI(), bpip2URI, PublicationConnector.class.getCanonicalName());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ComponentStartException("can't connect brokers");
			}
		super.start();
	}

	@Override
	public void finalise() throws Exception {
		// Deconnection du port sur un autre Broker si nécéssaire
		if (bpop != null)
			this.doPortDisconnection(bpop.getPortURI());
		// deconnection des ports vers les Subscriber
		for (BrokerReceptionOutboundPort brop : brops)
			this.doPortDisconnection(brop.getPortURI());
		super.finalise();
	}

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			// Depublication du port sur un autre Broker si nécéssaire
			for (BrokerReceptionOutboundPort brop : brops) {
				brop.unpublishPort();
			}
			removeRequiredInterface(ReceptionCI.class);
			// on dépublie les autres ports
			bmip.unpublishPort();
			bmip2.unpublishPort();
			removeOfferedInterface(ManagementCI.class);
			bpip.unpublishPort();
			if (bpop != null)
				bpop.unpublishPort();
			removeOfferedInterface(PublicationCI.class);

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			for (BrokerReceptionOutboundPort brop : brops) {
				brop.unpublishPort();
			}
			removeRequiredInterface(ReceptionCI.class);
			// on dépublie les ports
			bmip.unpublishPort();
			bmip2.unpublishPort();
			removeOfferedInterface(ManagementCI.class);
			bpip.unpublishPort();
			if (bpop != null)
				bpop.unpublishPort();
			removeOfferedInterface(PublicationCI.class);
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void createTopic(String topic) {
		this.lock.writeLock().lock();
		try {
			if (!isTopic(topic))
				topics.put(topic, new Topic());
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void createTopics(String[] toCreate) {
		this.lock.writeLock().lock();
		try {
			for (String topic : toCreate) {
				if (!isTopic(topic))
					topics.put(topic, new Topic());
			}
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void destroyTopic(String topic) {
		this.lock.writeLock().lock();
		try {
			topics.remove(topic);
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public boolean isTopic(String topic) {
		boolean isTopic;
		this.lock.readLock().lock();
		try {
			isTopic = topics.containsKey(topic);
		} finally {
			this.lock.readLock().unlock();
		}
		return isTopic;
	}

	@Override
	public String[] getTopics() {
		String[] sTopics;
		this.lock.readLock().lock();
		try {
			Object[] topics = this.topics.keySet().toArray();
			sTopics = Arrays.stream(topics).toArray(String[]::new);
		} finally {
			this.lock.readLock().unlock();
		}
		return sTopics;
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return bpip.getPortURI();
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		Log.printAndLog(this, "broker reçoit, thread : " + Thread.currentThread().getId());

		// si on est connecté a un autre Broker, on lui transmet les messages
		// on enleve tout les messages qu'on a deja réçu une fois des messages à
		// transmettre
		// pour éviter un cycle de transmission (si on les à deja eu, ils sont deja
		// passer
		// par tout les autres Broker)
		if (bpop != null) {
			// on envoie les message
			if (ms.length > 0)
				this.runTask(ENVOIE_EXECUTOR_URI, owner -> {
					try {
						Log.printAndLog(this, "broker transmet, thread : " + Thread.currentThread().getId());
						List<MessageI> new_ms = Arrays.asList(ms);
						// on enlève les messages déjà reçu
						new_ms = new_ms.stream().filter(message -> {
							try {
								return !(message.containsBroker(this.myUri));
							} catch (Exception e1) {
								return false;
							}
						}).collect(Collectors.toList());
						// on marque les autres comme deja reçu pour ce Broker
						new_ms = new_ms.stream().map(message -> {
							try {
								return message.addBroker(this.myUri);
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}).collect(Collectors.toList());
						// on transmet les messages
						bpop.publish(new_ms.toArray(new MessageI[] {}), topics);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		}

		// on créer les topic si nécéssaire
		for (String topic : topics)
			this.createTopic(topic);

		this.lock.writeLock().lock();
		try {
			// on publie les messages dans les topics
			for (String topic : topics) {
				for (MessageI m : ms)
					this.topics.get(topic).addMessage(m);
			}

			// list des subscribers à qui on a deja envoyé les messages
			List<String> notifiedSubs = new ArrayList<>();

			// on parcours les subscriber de chaque topics
			for (String topic : topics)
				for (String subscriber : this.topics.get(topic).getSubscribers())

					// si il n'a pas deja reçu les messages
					if (!notifiedSubs.contains(subscriber)) {
						notifiedSubs.add(subscriber);
						for (MessageI m : ms)

							// on cherche le port associé au subscriber
							for (BrokerReceptionOutboundPort brop : brops) {
								try {
									boolean urisEq = brop.getServerPortURI().equals(subscriber);
									boolean filterOk = (this.topics.get(topic).getFilter(subscriber) == null
											|| this.topics.get(topic).getFilter(subscriber).filter(m));

									// on à le bon port
									if (urisEq && filterOk) {

										// on envoie le message
										this.runTask(ENVOIE_EXECUTOR_URI, owner -> {
											try {
												Log.printAndLog(this,
														"broker envoie, thread : " + Thread.currentThread().getId());
												brop.acceptMessage(m);
											} catch (Exception e) {
												e.printStackTrace();
											}
										});
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
					}
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void publish(MessageI m, String topic) {
		MessageI[] ms = { m };
		String[] topics = { topic };
		publish(ms, topics);
	}

	@Override
	public void publish(MessageI m, String[] topics) {
		MessageI[] ms = { m };
		publish(ms, topics);
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		String[] topics = { topic };
		publish(ms, topics);
	}

	/**
	 * Quand quelqu'un souscrit, on créer un port pour pouvoir contacter le
	 * subscriber apres
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) {

		this.lock.writeLock().lock();
		try {
			// si le topic n'existe pas, on l'ajoute
			if (!topics.containsKey(topic))
				topics.put(topic, new Topic());
			// on ajoute le subscriber au topic
			topics.get(topic).addSubscription(inboundPortURI, null);

			// on créer un port sortant et le lie a celui du subscriber si celui-ci n'existe
			// pas déjà
			try {
				BrokerReceptionOutboundPort brop = new BrokerReceptionOutboundPort(this);
				boolean alreadyExists = false;
				for (BrokerReceptionOutboundPort port : brops) {
					if (port.getServerPortURI() != null && port.getServerPortURI().equals(inboundPortURI))
						alreadyExists = true;
				}
				if (!alreadyExists) {
					brop.localPublishPort();
					brops.add(brop);
				}
				this.doPortConnection(brop.getPortURI(), inboundPortURI, ReceptionConnector.class.getCanonicalName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		for (String topic : topics)
			subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		subscribe(topic, inboundPortURI);
		modifyFilter(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		// on recupère le topic
		Topic t;
		this.lock.readLock().lock();
		try {
			t = topics.get(topic);
		} finally {
			this.lock.readLock().unlock();
		}

		// si le topic existe, on change le filtre
		if (t != null) {
			this.lock.writeLock().lock();
			try {
				t.updateFilter(inboundPortURI, newFilter);
			} finally {
				this.lock.writeLock().unlock();
			}
		}
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		Topic t;
		// on récupère le topic
		this.lock.readLock().lock();
		try {
			t = topics.get(topic);
		} finally {
			this.lock.readLock().unlock();
		}

		if (t != null) {
			// on enlève le subscriber du topic
			this.lock.writeLock().lock();
			try {
				t.removeSubscriber(inboundPortURI);
			} finally {
				this.lock.writeLock().unlock();
			}

			// on supprime le port lié au client
			brops.removeIf(brop -> {
				try {
					if (brop.getClientPortURI().equals(inboundPortURI)) {
						this.doPortDisconnection(brop.getPortURI());
						brop.unpublishPort();
						return true;
					}
					return false;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			});
		}
	}

}