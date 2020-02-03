package components;

import fr.sorbonne_u.components.AbstractComponent;
import port.PublisherServiceInboundPort;

public class Publisher extends AbstractComponent {

	protected PublisherServiceInboundPort psip;
	
	protected Publisher(int nbThreads, int nbSchedulableThreads) {
		super(nbThreads, nbSchedulableThreads);
		// TODO Auto-generated constructor stub
	}

	
}
