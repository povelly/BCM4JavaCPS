package utils;

import fr.sorbonne_u.components.ComponentI;

/**
 * 
 * @author Bello Velly Cette classe permet d'afficher un message sur la sortie
 *         standard et de le logger sur un composant
 */
public class Log {

	public static void printAndLog(ComponentI component, String message) {
		System.out.println(message);
		component.logMessage(message);
	}
}
