package port;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.DynamicComponentCreationI;

public class DynamicComponentCreationOutboundPort extends AbstractOutboundPort implements DynamicComponentCreationI {
	private static final long serialVersionUID = 1L;

	public DynamicComponentCreationOutboundPort(ComponentI owner) throws Exception {
		super(DynamicComponentCreationI.class, owner);
	}

	public DynamicComponentCreationOutboundPort(String uri, ComponentI owner) throws Exception {
		super(uri, DynamicComponentCreationI.class, owner);
	}

	@Override
	public String createComponent(String classname, Object[] constructorParams) throws Exception {
		return ((DynamicComponentCreationI) this.connector).createComponent(classname, constructorParams);
	}
}