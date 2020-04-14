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

//-----------------------------------------------------------------------------
/**
 * The class <code>ConnectionData</code> represents the data stored by the
 * reqistry to know for each port on which host the port is published on the
 * RMI registry.
 *
 * <p><strong>Description</strong></p>
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
public class				ConnectionData
{
	/**	RMI, socket, (to be implemented), others ??							*/
	protected ConnectionType		type ;
	/** host running the RMI registry on which the port is published.		*/
	protected String				hostname ;
	/** port number on which the RMI registry can be called.				*/
	protected int				port ;

	/**
	 * create a connection data object from the information received by the
	 * registry through a socket communication (hence strings).
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param type		type of connection
	 * @param hostname	name of the host on which RMI registry the port is published.
	 * @param port		port number of the RMI registry.
	 */
	public				ConnectionData(
		ConnectionType type,
		String hostname,
		int port
		)
	{
		super();
		this.type = type;
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * create a connection data object from the raw information received by the
	 * registry through a socket communication (hence one string).
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param value	string containing the information about the publised port.
	 */
	public				ConnectionData(
		String value
		)
	{
		String[] temp1 = value.split("=") ;
		if (temp1[0].equals("rmi")) {
			this.type = ConnectionType.RMI ;
			this.hostname = temp1[1] ;
		} else {
			assert	temp1[0].equals("socket") ;
			this.type = ConnectionType.SOCKET ;
			String[] temp2 = temp1[1].split(":") ;
			this.hostname = temp2[0] ;
			this.port = Integer.parseInt(temp2[1]) ;
		}
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @return the type
	 */
	public ConnectionType	getType() {
		return this.type;
	}

	/**
	 * @return the hostname
	 */
	public String			getHostname() {
		return this.hostname;
	}

	/**
	 * @return the port
	 */
	public int				getPort() {
		return this.port;
	}
}
//-----------------------------------------------------------------------------
