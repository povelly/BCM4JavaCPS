package tets;

import org.junit.Assert;
import org.junit.Test;

import message.Properties;

public class TestProperties {

	@Test
	public void getBooleanProp() {
		String name = "name";
		boolean v = true;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getBooleanProp(name));
	}

	@Test
	public void getByteProp() {
		String name = "name";
		byte v = 1;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getByteProp(name));
	}

	@Test
	public void getCharProp() {
		String name = "name";
		char v = 'a';
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getCharProp(name));
	}

	@Test
	public void getDoubleProp() {
		String name = "name";
		double v = 1.2d;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getDoubleProp(name), 0d);
	}

	@Test
	public void getFloatProp() {
		String name = "name";
		float v = 1.2f;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getFloatProp(name), 0f);
	}

	@Test
	public void getIntProp() {
		String name = "name";
		int v = 1;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getIntProp(name));
	}

	@Test
	public void getLongProp() {
		String name = "name";
		long v = 1;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getLongProp(name));
	}

	@Test
	public void getShortProp() {
		String name = "name";
		short v = 1;
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getShortProp(name));
	}

	@Test
	public void getStringProp() {
		String name = "name";
		String v = "v";
		Properties prop = new Properties();
		prop.putProp(name, v);
		Assert.assertEquals(v, prop.getStringProp(name));
	}

}
