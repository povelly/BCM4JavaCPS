package utils;

import fr.sorbonne_u.components.ComponentI;

/**
 * Cette classe permet d'afficher un message sur la sortie standard et de le
 * logger sur un composant
 * 
 * @author Bello Velly
 */
public class Log {

	/**
	 * Affiche un message sur la sortie standard et le log sur un composant
	 * 
	 * @param component composant sur lequel le message doit être logé
	 * @param message   message à afficher/logé
	 */
	public static void printAndLog(ComponentI component, String message) {
		System.out.println(message);
		component.logMessage(message);
	}
}
