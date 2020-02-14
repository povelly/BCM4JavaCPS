package deployment;

import components.Broker;
import components.Publisher;
import components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {
		// definitions des uris des ports entrants

		// broker
		String brokerPIP_uri = AbstractPort.generatePortURI(); // pour publisher
		String brokerMIP_uri = AbstractPort.generatePortURI(); // pour subscriber
		String brokerMIP2_uri = AbstractPort.generatePortURI(); // pour publisher

		// creation des composants
		AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { brokerMIP_uri, brokerMIP2_uri, brokerPIP_uri });
		AbstractComponent.createComponent(Subscriber.class.getCanonicalName(), new Object[] { brokerMIP_uri });
		AbstractComponent.createComponent(Publisher.class.getCanonicalName(),
				new Object[] { brokerPIP_uri, brokerMIP2_uri });
		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(10000L);
			Thread.sleep(10000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
