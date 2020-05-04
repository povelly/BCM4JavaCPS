package message;

import java.io.Serializable;

/**
 * Classe representant une estampille de temps
 * 
 * @author Bello Velly
 *
 */
public class TimeStamp implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long time;
	protected String timestamper;

	public boolean isInitialized() {
		return timestamper != null && time != 0;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTimeStamper() {
		return timestamper;
	}

	public void setTimeStamper(String timestamper) {
		this.timestamper = timestamper;
	}

}
