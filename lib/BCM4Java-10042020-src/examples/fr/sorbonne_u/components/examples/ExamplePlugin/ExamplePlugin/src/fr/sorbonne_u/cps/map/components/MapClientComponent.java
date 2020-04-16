package fr.sorbonne_u.cps.map.components;

// Copyright Jacques Malenfant, Sorbonne Universite.
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to provide an example of
// the BCM component model that aims to define a basic component model for Java.
//
// This software is governed by the CeCILL-C license under French law and
// abiding by the rules of distribution of free software.  You can use,
// modify and/ or redistribute the software under the terms of the
// CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
// URL "http://www.cecill.info".
//
// As a counterpart to the access to the source code and  rights to copy,
// modify and redistribute granted by the license, users are provided only
// with a limited warranty  and the software's author,  the holder of the
// economic rights,  and the successive licensors  have only  limited
// liability. 
//
// In this respect, the user's attention is drawn to the risks associated
// with loading,  using,  modifying and/or developing or reproducing the
// software by the user in light of its specific status of free software,
// that may mean  that it is complicated to manipulate,  and  that  also
// therefore means  that it is reserved for developers  and  experienced
// professionals having in-depth computer knowledge. Users are therefore
// encouraged to load and test the software's suitability as regards their
// requirements in conditions enabling the security of their systems and/or 
// data to be ensured and,  more generally, to use and operate it in the 
// same conditions as regards security. 
//
// The fact that you are presently reading this means that you have had
// knowledge of the CeCILL-C license and that you accept its terms.

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;
import fr.sorbonne_u.cps.map.connectors.MapConnector;
import fr.sorbonne_u.cps.map.deployments.CVM;
import fr.sorbonne_u.cps.map.interfaces.MapI;
import fr.sorbonne_u.cps.map.ports.MapOutboundPort;

// -----------------------------------------------------------------------------
/**
 * The class <code>MapClientComponent</code> implements a client component for
 * the hash map component.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * The component requires the component interface <code>MapI</code> and uses
 * the global constant
 * <code>fr.sorbonne_u.cps.map.deployments.CVM.MAP_COMPONENT_RIBP_URI</code>
 * giving the reflection inbound port URI of the hash map component to connect
 * its oubound port to the inbound port of the hash map component in its
 * execute method, which then performs a series of calls to test the hash map
 * component.
 * </p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2019-03-21</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
// -----------------------------------------------------------------------------
@RequiredInterfaces(required = {ReflectionI.class,MapI.class})
// -----------------------------------------------------------------------------
public class			MapClientComponent
extends		AbstractComponent
{
	// -------------------------------------------------------------------------
	// Component variables and constants
	// -------------------------------------------------------------------------

	protected MapOutboundPort<String,Integer>		mop ;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	protected			MapClientComponent()
	{
		super(1, 0) ;
	}

	protected			MapClientComponent(String reflectionInboundPortURI)
	{
		super(reflectionInboundPortURI, 1, 0) ;
	}

	// -------------------------------------------------------------------------
	// Life cycle
	// -------------------------------------------------------------------------

	@Override
	public void			start() throws ComponentStartException
	{
		super.start() ;

		try {
			this.mop = new MapOutboundPort<String,Integer>(this) ;
			this.mop.publishPort() ;
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		} ;
	}

	@Override
	public void			execute() throws Exception
	{
		super.execute() ;

		// Uses the reflection approach to get the hash map inbound port URI
		// in order to connect to it.
		ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
		rop.publishPort() ;
		this.doPortConnection(
				rop.getPortURI(),
				CVM.MAP_COMPONENT_RIBP_URI,
				ReflectionConnector.class.getCanonicalName()) ;
		String[] uris = rop.findPortURIsFromInterface(MapI.class) ;
		assert	uris != null && uris.length == 1 ;

		// Connect the hash map component for tis services.
		this.doPortConnection(
				this.mop.getPortURI(),
				uris[0],
				MapConnector.class.getCanonicalName()) ;

		this.doPortDisconnection(rop.getPortURI()) ;
		rop.unpublishPort() ;
		rop.destroyPort() ;

		// Test scenario
		this.mop.put("a", 1) ;
		this.mop.put("b", 2) ;
		this.mop.put("c", 3) ;
		System.out.println("" + this.mop.containsKey("a")) ;
		System.out.println("" + this.mop.containsKey("b")) ;
		System.out.println("" + this.mop.containsKey("c")) ;
		System.out.println("" + this.mop.get("a")) ;
		System.out.println("" + this.mop.get("b")) ;
		System.out.println("" + this.mop.get("c")) ;
	}

	@Override
	public void			finalise() throws Exception
	{
		this.doPortDisconnection(this.mop.getPortURI()) ;
		this.mop.unpublishPort() ;

		super.finalise() ;
	}
}
// -----------------------------------------------------------------------------
