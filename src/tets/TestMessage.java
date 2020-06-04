package tets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import interfaces.MessageI;
import message.Message;
import message.Properties;

/**
 * Tests pour la classe Message
 * 
 * @author Bello Velly
 *
 */
public class TestMessage {

	@Test
	public void getProperties() {
		Properties properties = new Properties();
		Message m = new Message(properties, "");
		Assert.assertEquals(properties, m.getProperties());
	}

	@Test
	public void getPlayload() {
		String playload = "p";
		Message m = new Message(new Properties(), playload);
		Assert.assertEquals(playload, m.getPayload());
	}

	@Test
	public void getTimeStamp() {
		Message m = new Message(new Properties(), "");
		Assert.assertNotNull(m.getTimeStamp());
	}

	@Test
	public void getUri() {
		Message m = new Message(new Properties(), "");
		Assert.assertNotNull(m.getURI());
	}

	@Test
	public void addBroker() {
		MessageI m = new Message(null, "");
		MessageI res = null;
		String brokerUri = "brokertest";
		try {
			res = m.addBroker(brokerUri);
			assertTrue(res.containsBroker(brokerUri));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void containsBroker() {
		MessageI m = new Message(null, "");
		MessageI res = null;
		String brokerUri = "brokertest";
		try {
			res = m.addBroker(brokerUri);
			assertTrue(res.containsBroker(brokerUri));
			assertFalse(res.containsBroker("blabla"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
