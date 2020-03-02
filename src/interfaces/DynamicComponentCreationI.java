package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;

public interface DynamicComponentCreationI extends OfferedI, RequiredI {

	public String createComponent(String classname, Object[] constructorParams) throws Exception;

}
