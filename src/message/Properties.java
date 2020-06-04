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

	/**
	 * contient les propriétées, associé a leurs identifiant
	 */
	private Map<String, Object> properties = new HashMap<String, Object>();

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, boolean v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, byte v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, char v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, double v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, float v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, int v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, long v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, short v) {
		properties.put(name, v);
	}

	/**
	 * enregistre une propriétée
	 * 
	 * @param name nom de la propriétée
	 * @param v    valeur de la propriétée
	 */
	public void putProp(String name, String v) {
		properties.put(name, v);
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public boolean getBooleanProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Boolean)
			return (boolean) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a boolean");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public byte getByteProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Byte)
			return (byte) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a byte");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public char getCharProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Character)
			return (char) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a char");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public double getDoubleProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Double)
			return (double) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a double");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public float getFloatProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Float)
			return (float) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a float");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public int getIntProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Integer)
			return (int) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be an int");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public long getLongProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Long)
			return (long) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a long");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public short getShortProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof Short)
			return (short) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a short");
	}

	/**
	 * renvoie une propriétée
	 * 
	 * @param name identifiant de la propriétée à renvoyé
	 */
	public String getStringProp(String name) throws BadPropertyType {
		Object res = properties.get(name);
		if (res == null)
			throw new PropertyDoesntExist(name);
		if (res instanceof String)
			return (String) res;
		throw new BadPropertyType("Propertiy " + name + " shloud be a String");
	}

	/**
	 * Exception indiquant un mauvais type de propriété
	 */
	static class BadPropertyType extends RuntimeException {

		private static final long serialVersionUID = 1L;

		/**
		 * constructeur de BadPropertyTyoe
		 * 
		 * @param text mesage d'erreur
		 */
		public BadPropertyType(String text) {
			super(text);
		}
	}

	/**
	 * Exception indiquant une propriétée inexistante
	 *
	 */
	static class PropertyDoesntExist extends RuntimeException {

		private static final long serialVersionUID = 1L;

		/**
		 * constructeur de PropertyDoesntExist
		 * 
		 * @param name nom de la propriété inexistante
		 */
		public PropertyDoesntExist(String name) {
			super("Property " + name + " doesnt exist.");
		}

	}
}
