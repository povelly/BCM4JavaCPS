package message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.MessageFilterI;
import interfaces.MessageI;

public class Topic {

	private List<MessageI> messages = new ArrayList<>();
	private Map<String, MessageFilterI> subscriptions = new HashMap<>();

	public List<MessageI> getMessages() {
		return messages;
	}

	public Map<String, MessageFilterI> getSubscriptions() {
		return subscriptions;
	}

	public void addMessage(MessageI m) {
		messages.add(m);
	}

	public void addSubscription(String subscriber, MessageFilterI filter) {
		subscriptions.put(subscriber, filter);
	}
}
