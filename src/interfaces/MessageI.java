package interfaces;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Properties;

public interface MessageI {

	public String getURI();
	public Timestamp getTimeStamp();
	public Properties getProperties();
	public Serializable getPayload();
	
}
