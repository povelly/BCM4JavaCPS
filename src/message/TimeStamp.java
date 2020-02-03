package message;

public class TimeStamp {
	
	protected long time;
	protected String timeStamper;
	
	public boolean isInitialized() {
		return timeStamper != null;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public String getTimeStamper() {
		return timeStamper;
	}
	
	public void setTimeStamper(String timeStamper) {
		this.timeStamper = timeStamper;
	}

}
