package port;

import components.Broker;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ManagementCI;
import interfaces.MessageFilterI;

/**
 * Port d'entrée du Broker pour l'interface composant ManagementCI
 * 
 * @author Bello Velly
 *
 */
public class BrokerManagementInboundPort extends AbstractInboundPort implements ManagementCI {

	private static final long serialVersionUID = 1L;
	protected final int executorIndex;

	// public BrokerManagementInboundPort(ComponentI owner) throws Exception {
	// super(ManagementCI.class, owner);
	// assert owner instanceof Broker;
	// }
	//
	// public BrokerManagementInboundPort(String uri, ComponentI owner) throws
	// Exception {
	// super(uri, ManagementCI.class, owner);
	// assert owner instanceof Broker;
	// }

	public BrokerManagementInboundPort(String uri, int executorIndex, ComponentI owner) throws Exception {
		super(uri, ManagementCI.class, owner);
		assert owner instanceof Broker;
		this.executorIndex = executorIndex;
	}

	@Override
	public void createTopic(String topic) {
		try {
			this.getOwner().handleRequestAsync(executorIndex, owner -> {
				((Broker) owner).createTopic(topic);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTopics(String[] topics) {
		try {
			this.getOwner().handleRequestAsync(executorIndex, owner -> {
				((Broker) owner).createTopics(topics);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroyTopic(String topic) {
		try {
			this.getOwner().handleRequestAsync(executorIndex, owner -> {
				((Broker) owner).destroyTopic(topic);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isTopic(String topic) {
		try {
			return this.getOwner().handleRequestSync(executorIndex, owner -> ((Broker) owner).isTopic(topic));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] getTopics() {
		try {
			return this.getOwner().handleRequestSync(executorIndex, owner -> ((Broker) owner).getTopics());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getPublicationPortURI() throws Exception {
		return this.getOwner().handleRequestSync(executorIndex, owner -> ((Broker) owner).getPublicationPortURI());
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) {
		try {
			this.getOwner().handleRequestAsync(executorIndex, owner -> {
				((Broker) owner).subscribe(topic, inboundPortURI);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) {
		try {
			this.getOwner().handleRequestAsync(this.getOwner().getExecutorServiceIndex("subscription"), owner -> {
				((Broker) owner).subscribe(topics, inboundPortURI);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) {
		try {
			this.getOwner().handleRequestAsync(this.getOwner().getExecutorServiceIndex("subscription"), owner -> {
				((Broker) owner).subscribe(topic, filter, inboundPortURI);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		try {
			this.getOwner().handleRequestAsync(this.getOwner().getExecutorServiceIndex("subscription"), owner -> {
				((Broker) owner).modifyFilter(topic, newFilter, inboundPortURI);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		try {
			this.getOwner().handleRequestAsync(this.getOwner().getExecutorServiceIndex("subscription"), owner -> {
				((Broker) owner).unsubscribe(topic, inboundPortURI);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
