package interfaces;

/**
 * interface de reception
 * 
 * @author Bello Velly
 *
 */
public interface ReceptionImplementationI {

	public void acceptMessage(MessageI m) throws Exception;

	public void acceptMessage(MessageI[] ms) throws Exception;

}
