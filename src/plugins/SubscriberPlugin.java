package plugins;

import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import interfaces.MessageFilterI;
import interfaces.ReceptionCI;
import interfaces.SubscriptionImplementationI;

public class SubscriberPlugin extends AbstractPlugin implements SubscriptionImplementationI {

	private static final long serialVersionUID = 1L;
	protected SubscriberInboundPortForPlugin sip;
	
	@Override
	public void installOn(ComponentI owner) throws Exception {
		super.installOn(owner);
		assert owner instanceof SubscriptionImplementationI;
		
		this.addOfferedInterface(ReceptionCI.class); // TODO
		this.sip = new SubscriberInboundPortForPlugin(this.getPluginURI(), this.owner);
		this.sip.publishPort();
	}
	
	@Override
	public void uninstall() throws Exception {
		this.sip.unpublishPort();
		this.sip.destroyPort();
		this.removeOfferedInterface(ReceptionCI.class); // TODO pas sur
	}
	
	private SubscriptionImplementationI getOwner() {
		return (SubscriptionImplementationI) this.owner;
	}

	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
		this.getOwner().subscribe(topic, inboundPortURI);
	}

	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		this.getOwner().subscribe(topics, inboundPortURI);
	}

	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {
		this.getOwner().subscribe(topic, filter, inboundPortURI);
	}

	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) throws Exception {
		this.getOwner().modifyFilter(topic, newFilter, inboundPortURI);
	}

	@Override
	public void unsubscribe(String topic, String inboundPortURI) throws Exception {
		this.getOwner().unsubscribe(topic, inboundPortURI);
	}

}
