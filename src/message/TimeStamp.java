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

	/**
	 * date
	 */
	protected long time;

	protected String timestamper;

	/**
	 * regarde si le timestamp est initialisé
	 * 
	 * @return true si le timestam est initialisé, false sinon
	 */
	public boolean isInitialized() {
		return timestamper != null && time != 0;
	}

	/**
	 * Renvoie la date
	 * 
	 * @return la date
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Fixe la date
	 * 
	 * @param time date à fixé
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Renvoie le timestamper
	 * 
	 * @return le timestamper
	 */
	public String getTimeStamper() {
		return timestamper;
	}

	/**
	 * Fixe le timestamper
	 * 
	 * @param timestamper timestamper à fixé
	 */
	public void setTimeStamper(String timestamper) {
		this.timestamper = timestamper;
	}

}
