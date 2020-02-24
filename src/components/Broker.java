package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import connectors.ReceptionConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;
import interfaces.MessageI;
import interfaces.PublicationCI;
import interfaces.ReceptionCI;
import message.Topic;
import port.BrokerManagementInboundPort;
import port.BrokerPublicationInboundPort;
import port.BrokerReceptionOutboundPort;

/**
 * Classe representant le composant courtier
 * 
 * @author Bello Velly
 */

@RequiredInterfaces(required = { ReceptionCI.class })
@OfferedInterfaces(offered = { ManagementCI.class, PublicationCI.class })
public class Broker extends AbstractComponent implements ManagementCI, PublicationCI {

	// ports du composant
	protected List<BrokerReceptionOutboundPort> brops = new ArrayList<>();
	protected BrokerManagementInboundPort bmip;
	protected BrokerManagementInboundPort bmip2;
	protected BrokerPublicationInboundPort bpip;

	protected final ReentrantReadWriteLock lock;

	// Map<identifiant du topic, Topic>
	protected Map<String, Topic> topics = new HashMap<>();

	protected Broker(String bmipURI, String bmip2URI, String bpipURI) throws Exception {
		super(1, 0);
		// verifications
		assert bmipURI != null;
		assert bmip2URI != null;
		assert bpipURI != null;

		this.lock = new ReentrantReadWriteLock();

		this.createNewExecutorService("publication", 3, false);
		this.createNewExecutorService("subscription", 3, false);
		
		this.bmip = new BrokerManagementInboundPort(bmipURI, this.getExecutorServiceIndex("subscription"), this);
		this.bmip.publishPort();

		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, this.getExecutorServiceIndex("subscription"), this);
		this.bmip2.publishPort();

		this.bpip = new BrokerPublicationInboundPort(bpipURI, this.getExecutorServiceIndex("publication"), this);
		this.bpip.publishPort();
		

		// creation ports
//		this.bmip = new BrokerManagementInboundPort(bmipURI, this);
//		this.bmip.publishPort();
//
//		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, this);
//		this.bmip2.publishPort();
//
//		this.bpip = new BrokerPublicationInboundPort(bpipURI, this);
//		this.bpip.publishPort();
	}

	/***********************************************************************
	 * 
	 * CYCLE DE VIE
	 * 
	 ***********************************************************************/

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			for (BrokerReceptionOutboundPort brop : brops)
				this.doPortDisconnection(brop.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			for (BrokerReceptionOutboundPort brop : brops)
				this.doPortDisconnection(brop.getPortURI());
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdownNow();
	}

	@Override
	public void finalise() throws Exception {
		// on dépublie les ports
		for (BrokerReceptionOutboundPort brop : brops)
			brop.unpublishPort();
		bmip.unpublishPort();
		bmip2.unpublishPort();
		bpip.unpublishPort();
		super.finalise();
	}

	/***********************************************************************
	 * 
	 * IMPLANTATIONS DE SERVICES
	 * 
	 ***********************************************************************/

	@Override
	public void createTopic(String topic) {
		this.lock.writeLock().lock();
		if (!isTopic(topic))
			topics.put(topic, new Topic());
		this.lock.writeLock().unlock();
	}

	@Override
	public void createTopics(String[] toCreate) {
		this.lock.writeLock().lock();
		for (String topic : toCreate) {
			if (!isTopic(topic))
				topics.put(topic, new Topic());
		}
		this.lock.writeLock().unlock();
	}

	@Override
	public void destroyTopic(String topic) {
		this.lock.writeLock().lock();
		topics.remove(topic);
		this.lock.writeLock().unlock();
	}

	@Override
	public boolean isTopic(String topic) {
		this.lock.readLock().lock();
		boolean isTopic = topics.containsKey(topic);
		this.lock.readLock().unlock();
		return isTopic;
	}

	@Override
	public String[] getTopics() {
		this.lock.readLock().lock();
		Object[] topics = this.topics.keySet().toArray();
		String[] sTopics = Arrays.stream(topics).toArray(String[]::new);
		this.lock.readLock().unlock();
		return sTopics;
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return bpip.getPortURI();
	}

	@Override
	public void publish(MessageI m, String topic) {
		this.lock.writeLock().lock();
		try {
			this.createTopic(topic); // crée le topic s'il n'existe pas
			topics.get(topic).addMessage(m);
			// transmet le messages au abonnés
			for (String subscriber : topics.get(topic).getSubscribers()) {
				for (BrokerReceptionOutboundPort brop : brops) {
					try {
						if (brop.getServerPortURI().equals(subscriber)
								&& (topics.get(topic).getFilter(subscriber) == null
										|| topics.get(topic).getFilter(subscriber).filter(m))) {
							brop.acceptMessage(m);
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
	public void publish(MessageI m, String[] topics) {
		this.lock.writeLock().lock();
		try {
			for (String topic : topics) {
				this.createTopic(topic);
				this.topics.get(topic).addMessage(m);
			}
			List<String> notifiedSubs = new ArrayList<>();
			for (String topic : topics) {
				for (String subscriber : this.topics.get(topic).getSubscribers()) {
					if (!notifiedSubs.contains(subscriber)) {
						notifiedSubs.add(subscriber);
						for (BrokerReceptionOutboundPort brop : brops) {
							try {
								if (brop.getServerPortURI().equals(subscriber)
										&& (this.topics.get(topic).getFilter(subscriber) == null
												|| this.topics.get(topic).getFilter(subscriber).filter(m)))
									brop.acceptMessage(m);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		this.lock.writeLock().lock();
		try {
			for (MessageI m : ms)
				publish(m, topic);
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
		this.lock.writeLock().lock();
		try {
			for (String topic : topics) {
				this.createTopic(topic);
				for (MessageI m : ms)
					this.topics.get(topic).addMessage(m);
			}
			List<String> notifiedSubs = new ArrayList<>();
			for (String topic : topics)
				for (String subscriber : this.topics.get(topic).getSubscribers())
					if (!notifiedSubs.contains(subscriber)) {
						notifiedSubs.add(subscriber);
						for (MessageI m : ms)
							for (BrokerReceptionOutboundPort brop : brops) {
								try {
									if (brop.getServerPortURI().equals(subscriber)
											&& (this.topics.get(topic).getFilter(subscriber) == null
													|| this.topics.get(topic).getFilter(subscriber).filter(m)))
										brop.acceptMessage(m);
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
	/**
	 * Quand quelqu'un souscrit, on créer un port pour pouvoir le contacter
	 */
	public void subscribe(String topic, String inboundPortURI) {
		this.lock.writeLock().lock();
		try {
			// si le topic n'existe pas, on l'ajoute
			if (!topics.containsKey(topic))
				topics.put(topic, new Topic());
			// on ajoute le subscriber au topic
			topics.get(topic).addSubscription(inboundPortURI, null);
			// on créer un port sortant et le lie a celui du subscriber
			try {
				BrokerReceptionOutboundPort brop = new BrokerReceptionOutboundPort(this);
				brop.publishPort();
				boolean alreadyExists = false;
				for (BrokerReceptionOutboundPort port : brops) {
					if (port.getServerPortURI().equals(inboundPortURI))
						alreadyExists = true;
				}
				if (!alreadyExists)
					brops.add(brop);
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
		this.lock.writeLock().lock();
		try {
			for (String topic : topics)
				subscribe(topic, inboundPortURI);
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		this.lock.writeLock().lock();
		try {
			subscribe(topic, inboundPortURI);
			modifyFilter(topic, filter, inboundPortURI);
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		this.lock.writeLock().lock();
		try {
			Topic t = topics.get(topic);
			if (t != null) {
				t.updateFilter(inboundPortURI, newFilter);
			}
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		this.lock.writeLock().lock();
		try {
			Topic t = topics.get(topic);
			if (t != null) {
				t.removeSubscriber(inboundPortURI);
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
		} finally {
			this.lock.writeLock().unlock();
		}
	}

}