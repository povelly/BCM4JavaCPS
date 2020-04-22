package fr.sorbonne_u.components.examples.pingpong;

import fr.sorbonne_u.components.AbstractComponent;

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

import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.examples.pingpong.components.PingPongPlayer;

//-----------------------------------------------------------------------------
/**
 * The class <code>CVM</code> deploys and run the ping pong example on a
 * single JVM.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2018-03-14</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public class				CVM
extends		AbstractCVM
{
	/** URI of the two way port of the first player.						*/
	public final static String	PING_PONG_URI_1 = "player1" ;
	/** URI of the two way port of the second player.						*/
	public final static String	PING_PONG_URI_2 = "player2" ;
	/** URI of the inbound port of the first player.						*/
	public final static String	PLAYER1_PING_PONG_INBOUND_PORT_URI =
														"player1ibpURI" ;
	/** URI of the inbound port of the second player.						*/
	public final static String	PLAYER2_PING_PONG_INBOUND_PORT_URI =
														"player2ibpURI" ;
	/** URI of the outbound port of the first player.						*/
	public final static String	PLAYER1_PING_PONG_DATA_OUTBOUND_PORT_URI =
														"player1dobpURI" ;
	/** URI of the inbound port of the first player.						*/
	public final static String	PLAYER1_PING_PONG_DATA_INBOUND_PORT_URI =
														"player1dibpURI" ;
	/** URI of the outbound port of the second player.						*/
	public final static String	PLAYER2_PING_PONG_DATA_OUTBOUND_PORT_URI =
														"player2dobpURI" ;
	/** URI of the inbound port of the second player.						*/
	public final static String	PLAYER2_PING_PONG_DATA_INBOUND_PORT_URI =
														"player2dibpURI" ;
	/** URI of the two way port of the first player.						*/
	public final static String	PLAYER1_PING_PONG_TWOWAY_PORT_URI =
														"player1twpURI" ;
	/** URI of the two way port of the second player.						*/
	public final static String	PLAYER2_PING_PONG_TWOWAY_PORT_URI =
														"player2twpURI" ;

	public				CVM() throws Exception
	{
		super() ;
	}

	@Override
	public void			deploy() throws Exception
	{
		// --------------------------------------------------------------------
		// Creation phase
		// --------------------------------------------------------------------

		// A first player that initially has the service.
		String pp1URI =
			AbstractComponent.createComponent(
					PingPongPlayer.class.getCanonicalName(),
					new Object[]{PING_PONG_URI_1,
								 true,
								 PLAYER1_PING_PONG_INBOUND_PORT_URI,
								 PLAYER2_PING_PONG_INBOUND_PORT_URI,
								 PLAYER1_PING_PONG_DATA_OUTBOUND_PORT_URI,
								 PLAYER1_PING_PONG_DATA_INBOUND_PORT_URI,
								 PLAYER2_PING_PONG_DATA_OUTBOUND_PORT_URI,
								 PLAYER2_PING_PONG_DATA_INBOUND_PORT_URI,
								 PLAYER1_PING_PONG_TWOWAY_PORT_URI,
								 PLAYER2_PING_PONG_TWOWAY_PORT_URI}) ;
		this.toggleTracing(pp1URI) ;

		// A second player that is initially passive.
		String pp2URI =
			AbstractComponent.createComponent(
					PingPongPlayer.class.getCanonicalName(),
					new Object[]{PING_PONG_URI_2,
								 false,
								 PLAYER1_PING_PONG_INBOUND_PORT_URI,
								 PLAYER2_PING_PONG_INBOUND_PORT_URI,
								 PLAYER1_PING_PONG_DATA_OUTBOUND_PORT_URI,
								 PLAYER1_PING_PONG_DATA_INBOUND_PORT_URI,
								 PLAYER2_PING_PONG_DATA_OUTBOUND_PORT_URI,
								 PLAYER2_PING_PONG_DATA_INBOUND_PORT_URI,
								 PLAYER2_PING_PONG_TWOWAY_PORT_URI,
								 PLAYER1_PING_PONG_TWOWAY_PORT_URI}) ;
		this.toggleTracing(pp2URI) ;

		// --------------------------------------------------------------------
		// Deployment done
		// --------------------------------------------------------------------

		super.deploy();
	}

	public static void		main(String[] args)
	{
		try {
			CVM cvm = new CVM() ;
			cvm.startStandardLifeCycle(60000L) ;
			Thread.sleep(5000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}		
	}
}
//-----------------------------------------------------------------------------
