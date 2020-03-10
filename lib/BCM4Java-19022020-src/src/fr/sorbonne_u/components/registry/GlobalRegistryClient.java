package fr.sorbonne_u.components.registry;

//Copyright Jacques Malenfant, Sorbonne Universite.
//
//Jacques.Malenfant@lip6.fr
//
//This software is a computer program whose purpose is to provide a
//basic component programming model to program with components
//distributed applications in the Java programming language.
//
//This software is governed by the CeCILL-C license under French law and
//abiding by the rules of distribution of free software.  You can use,
//modify and/ or redistribute the software under the terms of the
//CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
//URL "http://www.cecill.info".
//
//As a counterpart to the access to the source code and  rights to copy,
//modify and redistribute granted by the license, users are provided only
//with a limited warranty  and the software's author,  the holder of the
//economic rights,  and the successive licensors  have only  limited
//liability. 
//
//In this respect, the user's attention is drawn to the risks associated
//with loading,  using,  modifying and/or developing or reproducing the
//software by the user in light of its specific status of free software,
//that may mean  that it is complicated to manipulate,  and  that  also
//therefore means  that it is reserved for developers  and  experienced
//professionals having in-depth computer knowledge. Users are therefore
//encouraged to load and test the software's suitability as regards their
//requirements in conditions enabling the security of their systems and/or 
//data to be ensured and,  more generally, to use and operate it in the 
//same conditions as regards security. 
//
//The fact that you are presently reading this means that you have had
//knowledge of the CeCILL-C license and that you accept its terms.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//-----------------------------------------------------------------------------
/**
 * The class <code>RegistryClient</code> provides a convenient intermediary
 * to send requests to the registry and get answers back.
 *
 * <p><strong>Description</strong></p>
 * 
 * For the component model, values put in and retrieved from the registry are
 * strings with the format:
 * 
 * value ::= rmi=hostname | socket=hostname:port
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2012-10-22</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 * @version	$Name$ -- $Revision$ -- $Date$
 */
public class				GlobalRegistryClient
{
	protected static int			BUFFER_SIZE = 512 ;
	protected InetAddress		registryHost ;
	protected Socket				s ;
	protected PrintStream		ps ;
	protected BufferedReader		br ;

	/**
	 * create a client, per JVM client object required.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 */
	public				GlobalRegistryClient()
	{
		super();
		this.registryHost = null ;
		this.s = null ;
		this.ps = null ;
		this.br = null ;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * send a command to the registry and return the answer as a string.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	command != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param command	command to be sent.
	 * @return			string representing the result of the request.
	 * @throws Exception	<i>to do.</i>
	 */
	protected String		sendCommand(String command)
	throws	Exception
	{
		String result = null ;

		if (this.registryHost == null) {
			try {
				this.registryHost =
					InetAddress.getByName(GlobalRegistry.REGISTRY_HOSTNAME) ;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		if (this.s == null) {
			this.s = new Socket(this.registryHost, GlobalRegistry.REGISTRY_PORT) ;
			this.ps = new PrintStream(s.getOutputStream(), true) ;
			this.br = new BufferedReader(
								new InputStreamReader(s.getInputStream())) ;
		} 
		ps.println(command) ;
		result = this.br.readLine() ;
		String[] tokens = result.split("\\s") ;
		if (!tokens[0].equals("ok")) {
			throw new Exception(result) ;
		}
		return tokens.length > 1 ? tokens[1] : tokens[0] ;
	}

	/**
	 * send a lookup command to the registry.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	key != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param key		key to be looked up.
	 * @return			result of the request.
	 * @throws Exception	<i>to do.</i>
	 */
	public synchronized String	lookup(String key) throws Exception {
		return this.sendCommand("lookup " + key) ;
	}

	/**
	 * send a put command to the registry.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	key != null and value != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param key		key under which the information must be stored.
	 * @param value		value (information) associated to the key.
	 * @throws Exception	<i>to do.</i>
	 */
	public synchronized void		put(String key, String value) throws Exception {
		this.sendCommand("put " + key + " " + value) ;
	}

	/**
	 * send a remove command to the registry.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	key != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param key		key under which the value to remove is stored.
	 * @throws Exception	<i>to do.</i>
	 */
	public synchronized void		remove(String key) throws Exception {
		this.sendCommand("remove " + key) ;
	}

	/**
	 * send a shutdown command to the registry.  NOT YET WORKING.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @throws Exception	<i>to do.</i>
	 */
	public synchronized void		shutdown() throws Exception {
		this.sendCommand("shutdown") ;
	}
}
//-----------------------------------------------------------------------------
