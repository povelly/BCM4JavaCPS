package message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe representant un ensemble de propriétés
 * 
 * @author Bello Velly
 *
 */
public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> properties = new HashMap<String, Object>();

	public void putProp(String name, boolean v) {
		properties.put(name, v);
	}

	public void putProp(String name, byte v) {
		properties.put(name, v);
	}

	public void putProp(String name, char v) {
		properties.put(name, v);
	}

	public void putProp(String name, double v) {
		properties.put(name, v);
	}

	public void putProp(String name, float v) {
		properties.put(name, v);
	}

	public void putProp(String name, int v) {
		properties.put(name, v);
	}

	public void putProp(String name, long v) {
		properties.put(name, v);
	}

	public void putProp(String name, short v) {
		properties.put(name, v);
	}

	public void putProp(String name, String v) {
		properties.put(name, v);
	}

	public boolean getBooleanProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Boolean)
			return (boolean) res;
		throw new BadPropertieType("Properties shloud be a boolean");
	}

	public byte getByteProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Byte)
			return (byte) res;
		throw new BadPropertieType("Properties shloud be a byte");
	}

	public char getCharProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Character)
			return (char) res;
		throw new BadPropertieType("Properties shloud be a char");
	}

	public double getDoubleProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Double)
			return (double) res;
		throw new BadPropertieType("Properties shloud be a double");
	}

	public float getFloatProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Float)
			return (float) res;
		throw new BadPropertieType("Properties shloud be a float");
	}

	public int getIntProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Integer)
			return (int) res;
		throw new BadPropertieType("Properties shloud be an int");
	}

	public long getLongProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Long)
			return (long) res;
		throw new BadPropertieType("Properties shloud be a long");
	}

	public short getShortProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof Short)
			return (short) res;
		throw new BadPropertieType("Properties shloud be a short");
	}

	public String getStringProp(String name) throws BadPropertieType {
		Object res = properties.get(name);
		if (res instanceof String)
			return (String) res;
		throw new BadPropertieType("Properties shloud be a String");
	}

	/**
	 * Propertie Exception class
	 */
	static class BadPropertieType extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public BadPropertieType(String text) {
			super(text);
		}
	}
}
