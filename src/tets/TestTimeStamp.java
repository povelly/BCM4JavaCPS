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

	/**
	 * Test pour
	 * 
	 * @see message.TimeStamp#isInitialized()
	 */
	@Test
	public void isInitialized() {
		TimeStamp t = new TimeStamp();
		t.setTimeStamper("v");
		t.setTime(1);
		Assert.assertTrue(t.isInitialized());
	}

	/**
	 * Test pour
	 * 
	 * @see message.TimeStamp#setTime(long)
	 * @see message.TimeStamp#getTime()
	 */
	@Test
	public void time() {
		TimeStamp t = new TimeStamp();
		t.setTime(1);
		Assert.assertEquals(1, t.getTime());
	}

	/**
	 * Test pour
	 * 
	 * @see message.TimeStamp#setTimeStamper(String)
	 * @see message.TimeStamp#getTimeStamper()
	 */
	@Test
	public void timeStamper() {
		TimeStamp t = new TimeStamp();
		t.setTimeStamper("v");
		Assert.assertEquals("v", t.getTimeStamper());
	}
}
