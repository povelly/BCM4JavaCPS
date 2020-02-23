package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// Map<identifiant du topic, Topic>
	protected Map<String, Topic> topics = new HashMap<>();

	protected Broker(String bmipURI, String bmip2URI, String bpipURI) throws Exception {
		super(1, 0);
		// verifications
		assert bmipURI != null;
		assert bmip2URI != null;
		assert bpipURI != null;

		// creation ports
		this.bmip = new BrokerManagementInboundPort(bmipURI, this);
		this.bmip.publishPort();

		this.bmip2 = new BrokerManagementInboundPort(bmip2URI, this);
		this.bmip2.publishPort();

		this.bpip = new BrokerPublicationInboundPort(bpipURI, this);
		this.bpip.publishPort();
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
		if (!isTopic(topic))
			topics.put(topic, new Topic());
	}

	@Override
	public void createTopics(String[] toCreate) {
		for (String topic : toCreate) {
			if (!isTopic(topic))
				topics.put(topic, new Topic());
		}
	}

	@Override
	public void destroyTopic(String topic) {
		topics.remove(topic);
	}

	@Override
	public boolean isTopic(String topic) {
		return topics.containsKey(topic);
	}

	@Override
	public String[] getTopics() {
		Object[] topics = this.topics.keySet().toArray();
		return Arrays.stream(topics).toArray(String[]::new);
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return bpip.getPortURI();
	}

	@Override
	public void publish(MessageI m, String topic) {
		this.createTopic(topic); // crée le topic s'il n'existe pas
		topics.get(topic).addMessage(m);
		// transmet le messages au abonnés
		for (String subscriber : topics.get(topic).getSubscribers()) {
			for (BrokerReceptionOutboundPort brop : brops) {
				try {
					if (brop.getServerPortURI().equals(subscriber) && (topics.get(topic).getFilter(subscriber) == null
							|| topics.get(topic).getFilter(subscriber).filter(m))) {
						brop.acceptMessage(m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void publish(MessageI m, String[] topics) {
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
	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		for (MessageI m : ms)
			publish(m, topic);
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) {
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
	}

	@Override
	/**
	 * Quand quelqu'un souscrit, on créer un port pour pouvoir le contacter
	 */
	public void subscribe(String topic, String inboundPortURI) {
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
		Topic t = topics.get(topic);
		if (t != null) {
			t.updateFilter(inboundPortURI, newFilter);
		}
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
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
	}

}