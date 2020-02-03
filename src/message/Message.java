package message;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Properties;

import interfaces.MessageI;

public class Message implements MessageI, Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public String getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimeStamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable getPayload() {
		// TODO Auto-generated method stub
		return null;
	}

}
