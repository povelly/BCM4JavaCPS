package connectors;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.DynamicComponentCreationI;

public class DynamicComponentCreationConnector extends AbstractConnector implements DynamicComponentCreationI {

	@Override
	public String createComponent(String classname, Object[] constructorParams) throws Exception {
		return ((DynamicComponentCreationI) this.offering).createComponent(classname, constructorParams);
	}
}