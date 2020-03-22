package tets;

import org.junit.Assert;
import org.junit.Test;

import interfaces.MessageFilterI;
import interfaces.MessageI;
import message.Topic;

/**
 * Tests pour la classe Topic
 * 
 * @author Bello Velly
 *
 */
public class TestTopic {

	@Test
	public void addSubscription() {
		Topic t = new Topic();
		String subscriber = "s";
		t.addSubscription(subscriber, m -> true);
		Assert.assertTrue(t.getSubscribers().contains(subscriber));
	}

	@Test
	public void removeSubscriber() {
		Topic t = new Topic();
		String subscriber = "s";
		t.addSubscription(subscriber, m -> true);
		t.removeSubscriber(subscriber);
		Assert.assertTrue(!t.getSubscribers().contains(subscriber));
	}

	@Test
	public void getFilter() {
		Topic t = new Topic();
		String subscriber = "s";
		MessageFilterI filter = new MessageFilterI() {

			@Override
			public boolean filter(MessageI m) {
				return false;
			}
		};
		t.addSubscription(subscriber, filter);
		Assert.assertEquals(filter, t.getFilter(subscriber));
	}

	@Test
	public void updateFilter() {
		Topic t = new Topic();
		String subscriber = "s";
		t.addSubscription(subscriber, m -> true);
		MessageFilterI filter = new MessageFilterI() {

			@Override
			public boolean filter(MessageI m) {
				return false;
			}
		};
		t.updateFilter(subscriber, filter);
		Assert.assertEquals(filter, t.getFilter(subscriber));
	}

}
