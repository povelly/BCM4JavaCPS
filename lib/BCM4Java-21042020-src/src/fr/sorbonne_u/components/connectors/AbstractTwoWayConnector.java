package fr.sorbonne_u.components.connectors;

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

import fr.sorbonne_u.components.interfaces.OfferedI;
import fr.sorbonne_u.components.interfaces.RequiredI;
import fr.sorbonne_u.components.interfaces.TwoWayI;
import fr.sorbonne_u.components.exceptions.PostconditionException;
import fr.sorbonne_u.components.exceptions.PreconditionException;

//-----------------------------------------------------------------------------
/**
 * The class <code>AbstractTwoWayConnector</code> partially implements a
 * basic connector between components calling each others services in a peer
 * to peer way rather than in a client-provider relationship.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * Two-way interfaces are the ones that are used to interconnect components
 * in a peer-to-peer way to exchange services or data.  In this case, both
 * components expose the same interface 
 * </p>
 * 
 * <p>
 * Ports that use this type of connectors must obey a strict calling
 * protocol to reach the other component. Each time a port wants to
 * call a method <code>m(...)</code>, it must call the method
 * <code>getProxyTowardsOtherComponent</code> passing its own URI.
 * This will return a proxy which, when called on <code>m(...)</code>,
 * will call the port of the other component. Hence, for a two way
 * interface <code>TW1</code> defining the method <code>m(...)</code>,
 * the sequence should always look like (in the out proxy class):
 * </p>
 * 
 * <pre>
 *    this.owner.getConnector().
 *        getProxyTowardsOtherComponent(this.owner.getPortURI()).m(...)
 * </pre>
 * 
 * <p>
 * This is simplified by the method <code>getProxyTowardsOtherComponent</code>
 * in the port out proxy to return a result of type parameter <code>T</code>
 * of <code>AbstractTwoWayPort.OutProxy</code>:
 * </p>
 * 
 * <pre>
 *   protected T		getProxyTowardsOtherComponent() throws Exception
 *   {
 *     return this.owner.getConnector().
 *               getProxyTowardsOtherComponent(this.owner.getPortURI()) ;
 *   }
 * </pre>
 * 
 * <p>
 * and then the above sequence will be simplified to:
 * </p>
 * 
 * <pre>
 *     this.getProxyTowardsOtherComponent().m(...)
 * </pre>

 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2012-01-24</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public abstract class	AbstractTwoWayConnector<TWI extends TwoWayI>
extends		AbstractConnector
{
	// ------------------------------------------------------------------------
	// Inner classes
	// ------------------------------------------------------------------------

	/**
	 * The abstract class <code>ProxyToOtherComponent</code> defines the
	 * baseline properties for the management of proxies forwarding calls
	 * to the other component for an identified sender.
	 *
	 * <p><strong>Description</strong></p>
	 * 
	 * <p><strong>Invariant</strong></p>
	 * 
	 * <pre>
	 * invariant		true
	 * </pre>
	 * 
	 * <p>Created on : 2018-03-23</p>
	 * 
	 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
	 * @version	$Name$ -- $Revision$ -- $Date$
	 */
	protected static abstract class	ProxyToOtherComponent<TWI extends TwoWayI>
	implements	TwoWayI
	{
		/** the two way connector owning this proxy.						*/
		protected final	AbstractTwoWayConnector<TWI>		owner ;
		/** 	the sender port URI for the next call.						*/
		protected final String				senderPortURI ;

		public				ProxyToOtherComponent(
			AbstractTwoWayConnector<TWI> owner,
			String senderPortURI
			)
		{
			super();
			this.owner = owner ;
			this.senderPortURI = senderPortURI ;
		}

		protected String		getRequiringPortURI()
		{
			return this.owner.getRequiringPortURI() ;
		}

		protected String		getOfferingPortURI()
		{
			return this.owner.getOfferingPortURI() ;
		}

		@SuppressWarnings("unchecked")
		protected TWI	getRequiring()
		{
			return (TWI) this.owner.requiring ;
		}

		@SuppressWarnings("unchecked")
		protected TWI	getOffering()
		{
			return (TWI) this.owner.offering ;
		}
	}

	// ------------------------------------------------------------------------
	// Services
	// ------------------------------------------------------------------------

	/**
	 * connect two way ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	peer1 instanceof TwoWayI
	 * pre	peer2 instanceof TwoWayI
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.connectors.AbstractConnector#connect(fr.sorbonne_u.components.interfaces.OfferedI, fr.sorbonne_u.components.interfaces.RequiredI)
	 */
	@Override
	public void			connect(OfferedI peer1, RequiredI peer2)
	throws	Exception
	{
		assert	!this.connected() :
					new PreconditionException("!this.connected()") ;
		assert	peer1 != null && peer2 != null :
					new PreconditionException("peer1 != null && peer2 != null") ;
		assert	peer1 instanceof TwoWayI :
					new PreconditionException("peer1 instanceof TwoWayI") ;
		assert	peer2 instanceof TwoWayI :
					new PreconditionException("peer2 instanceof TwoWayI") ;

		super.connect(peer1, peer2) ;

		assert	this.connected() :
					new PostconditionException("this.connected()") ;
	}

	/**
	 * disconnect two way ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	peer1 instanceof TwoWayI
	 * pre	peer2 instanceof TwoWayI
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.connectors.AbstractConnector#disconnect()
	 */
	@Override
	public void			disconnect() throws Exception
	{
		assert	this.connected() :
					new PreconditionException("this.connected()") ;

		super.disconnect() ;
		this.requiringPortURI = null ;
		this.offeringPortURI = null ;

		assert	!this.connected() :
					new PostconditionException("!this.connected()") ;
	}

	/**
	 * return a proxy connector that will forward the call to the other
	 * component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	senderPortURI != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param senderPortURI	URI of the port that wants to call the other component.
	 * @return				a proxy connector that will forward the call to the other component.
	 * @throws Exception		<i>todo.</i>
	 */
	public abstract TWI	getProxyTowardsOtherComponent(
		String senderPortURI
		) throws Exception ;
}
//-----------------------------------------------------------------------------
