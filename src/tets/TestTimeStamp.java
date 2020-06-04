package tets;

import org.junit.Assert;
import org.junit.Test;

import message.TimeStamp;

/**
 * Tests pour la classe TimeStamp
 * 
 * @author Bello Velly
 *
 */
public class TestTimeStamp {

	@Test
	public void isInitialized() {
		TimeStamp t = new TimeStamp();
		t.setTimeStamper("v");
		t.setTime(1);
		Assert.assertTrue(t.isInitialized());
	}

	@Test
	public void time() {
		TimeStamp t = new TimeStamp();
		t.setTime(1);
		Assert.assertEquals(1, t.getTime());
	}

	@Test
	public void timeStamper() {
		TimeStamp t = new TimeStamp();
		t.setTimeStamper("v");
		Assert.assertEquals("v", t.getTimeStamper());
	}
}
