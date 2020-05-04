package interfaces;

/**
 * interface de reception
 * 
 * @author Bello Velly
 *
 */
public interface ReceptionImplementationI {

	void acceptMessage(MessageI m) throws Exception;

	void acceptMessage(MessageI[] ms) throws Exception;

}
