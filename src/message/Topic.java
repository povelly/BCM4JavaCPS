package message;

/**
 * Classe representant un Topic, il contient la liste de touts ses messages
 * ainsi qu'un ensemble avec ses abonnés et pour chaques abonné son filtre
 * @author Bello Velly
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import interfaces.MessageFilterI;
import interfaces.MessageI;

public class Topic {

	// Tout les messages du topics
	private List<MessageI> messages;
	// Tout les abonnés au topics, avec leurs filtres
	private Map<String, MessageFilterI> subscriptions;

	public Topic() {
		this.messages = new ArrayList<MessageI>();
		this.subscriptions = new HashMap<String, MessageFilterI>();
	}

	public Set<String> getSubscribers() {
		return subscriptions.keySet();
	}

	public MessageFilterI getFilter(String subscriber) {
		return subscriptions.get(subscriber);
	}

	/**
	 * Ajoute un message au topic
	 * 
	 * @param m message à ajouté
	 */
	public void addMessage(MessageI m) {
		messages.add(m);
	}

	/**
	 * Ajoute un abonné au topic, avec son filtre
	 * 
	 * @param subscriber
	 * @param filter
	 */
	public void addSubscription(String subscriber, MessageFilterI filter) {
		subscriptions.put(subscriber, filter);
	}

	/**
	 * Met a jour le filtre d'un abonné
	 * 
	 * @param subscriber abonné dont il faut mettre a jour le filtre
	 * @param filter     nouveau filtre
	 */
	public void updateFilter(String subscriber, MessageFilterI filter) {
		subscriptions.remove(subscriber);
		subscriptions.put(subscriber, filter);
	}

	/**
	 * Supprime un abonné
	 * 
	 * @param subscriber abonné a supprimé
	 */
	public void removeSubscriber(String subscriber) {
		subscriptions.remove(subscriber);
	}

}
