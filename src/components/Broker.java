package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import connectors.ReceptionConnector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
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
import port.BrokerReceptionOutboundPort;

/**
 * Classe representant le composant courtier
 * 
 * @author Bello Velly
 */

@RequiredInterfaces(required = { ReceptionCI.class })
@OfferedInterfaces(offered = { ManagementCI.class, PublicationCI.class })
public class Broker extends AbstractComponent
		implements ManagementImplementationI, SubscriptionImplementationI, PublicationsImplementationI {

	// ports du composant
	// protected BrokerReceptionOutboundPort brop;
	protected List<BrokerReceptionOutboundPort> brops = new ArrayList<>();
	protected BrokerManagementInboundPort bmip;
	protected BrokerManagementInboundPort bmip2;
	protected BrokerPublicationInboundPort bpip;

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

	// @Override
	// public void start() throws ComponentStartException {
	// super.start();
	// try {
	// this.doPortConnection(this.brop.getPortURI(), ripServerURI,
	// ReceptionConnector.class.getCanonicalName());
	// } catch (Exception e) {
	// throw new ComponentStartException(e);
	// }
	// }

	@Override
	public void shutdown() throws ComponentShutdownException {
		try {
			for (BrokerReceptionOutboundPort brop : brops)
				this.doPortDisconnection(brop.getPortURI());
			this.doPortDisconnection(bmip.getPortURI());
			this.doPortDisconnection(bmip2.getPortURI());
			this.doPortDisconnection(bpip.getPortURI());
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
			this.doPortDisconnection(bmip.getPortURI());
			this.doPortDisconnection(bmip2.getPortURI());
			this.doPortDisconnection(bpip.getPortURI());
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
	public void createTopics(String[] topics) {
		for (String topic : topics) {
			if (!isTopic(topic))
				topics.put(topic, new()); // TODO
		}
	}

	@Override
	public void destroyTopic(String topic) {
		if (isTopic(topic))
			messages.remove(topic);
	}

	@Override
	public boolean isTopic(String topic) {
		return topics.containsKey(topic);
	}

	@Override
	public String[] getTopics() {
		return (String[]) messages.keySet().toArray();
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return bpip.getPortURI();
	}

	@Override
	public void publish(MessageI m, String topic) {
		this.createTopic(topic);
		topics.get(topic).addMessage(m);

		for (Entry<String, MessageFilterI> subscription : topics.get(topic).getSubscriptions().entrySet()) {
			for (BrokerReceptionOutboundPort brop : brops) {
				try {
					if (brop.getPortURI().equals(subscription.getKey()) && subscription.getValue().filter(m)) {
						((BrokerReceptionOutboundPort) brop.getConnector()).acceptMessage(m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void publish(MessageI m, String[] topics) { // TODO on peut recevoir plusieurs fois le mm msg dans des topics
														// differents
		this.createTopics(topics);

		// for (String topic : topics) {
		// messages.get(topic).add(m);
		// for (Entry<String, MessageFilterI> topicSubscription :
		// subscriptions.get(topic).entrySet()) {
		// if (topicSubscription.getValue() == null ||
		// topicSubscription.getValue().filter(m)) {
		// try {
		// ((ReceptionConnector) brop.getConnector()).acceptMessage(m);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }

	}

	@Override
	public void publish(MessageI[] ms, String topic) {
		this.createTopic(topic);
		for (MessageI m : ms)
			messages.get(topic).add(m);
		//
		// for (Entry<String, MessageFilterI> topicSubscription :
		// subscriptions.get(topic).entrySet()) {
		// for (MessageI m : ms) {
		// if (topicSubscription.getValue() == null ||
		// topicSubscription.getValue().filter(m)) {
		// try {
		// ((ReceptionConnector) brop.getConnector()).acceptMessage(m);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// ;
		// }
		// }
		// }
	}

	@Override
	public void publish(MessageI[] ms, String[] topics) { // TODO mm pb plusieurs notifs sur le mm msg avec topics
															// differents
		this.createTopics(topics);
		for (String topic : topics) {
			for (MessageI m : ms) {
				messages.get(topic).add(m);

			}
		}

	}

	@Override
	public void subscribe(String topic, String inboundPortURI) {
		if (!subscriptions.containsKey(topic))
			subscriptions.put(topic, new HashMap<>());
		subscriptions.get(topic).put(inboundPortURI, null);
		try {
			BrokerReceptionOutboundPort brop = new BrokerReceptionOutboundPort(inboundPortURI, this);
			brop.publishPort();
			brops.add(brop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		for (String topic : topics) {
			subscriptions.put(topic, new HashMap<String, MessageFilterI>() {
				private static final long serialVersionUID = 1L;
				{
					put(inboundPortURI, null);
				}
			});
		}
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		subscriptions.put(topic, new HashMap<String, MessageFilterI>() {
			private static final long serialVersionUID = 1L;
			{
				put(inboundPortURI, filter);
			}
		});
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		subscriptions.get(topic).put(inboundPortURI, newFilter);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		subscriptions.get(topic).remove(inboundPortURI);
	}

}
