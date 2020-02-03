package components;

import fr.sorbonne_u.components.AbstractComponent;
import interfaces.BrokerServicesI;
import interfaces.ManagementCI;
import interfaces.PublicationCI;
import interfaces.ReceptionCI;
import port.BrokerServiceInboundPort;
import port.BrokerServiceOutboundPort;

public class Broker extends AbstractComponent {

	protected BrokerServiceOutboundPort bsop;
	protected BrokerServiceInboundPort bsip1;
	protected BrokerServiceInboundPort bsip2;

	public Broker(String bsopURI, String bsip1URI, String bsip2URI) throws Exception {
		super(1, 0);
		this.addOfferedInterface(BrokerServicesI.class);
		this.addOfferedInterface(ManagementCI.class);
		this.addOfferedInterface(PublicationCI.class);
		this.addRequiredInterface(ReceptionCI.class);

		this.bsop = new BrokerServiceOutboundPort(bsopURI, this);
		this.bsop.publishPort();

		this.bsip1 = new BrokerServiceInboundPort(bsip1URI, this);
		this.bsip1.publishPort();

		this.bsip2 = new BrokerServiceInboundPort(bsip2URI, this);
		this.bsip2.publishPort();
	}

}
