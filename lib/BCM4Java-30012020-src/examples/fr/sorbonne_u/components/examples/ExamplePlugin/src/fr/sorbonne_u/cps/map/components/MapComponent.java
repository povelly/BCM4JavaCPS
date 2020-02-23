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

import java.io.Serializable;
import java.util.HashMap;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.cps.map.deployments.CVM;
import fr.sorbonne_u.cps.map.interfaces.MapI;
import fr.sorbonne_u.cps.map.ports.MapInboundPort;

// -----------------------------------------------------------------------------
/**
 * The class <code>MapComponent</code> implements a hash map component that
 * offers map services through the component interface <code>MapI</code>.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This is just a basic example aiming at showing how to use the plug-in
 * facility of BCM. Hence, the hash map component simply uses the standard
 * Java hash map class to implement its services. Indeed, the advantage of
 * this component is to make possible to have a distributed hash map, as
 * BCM components can be distributed. There is no need to use a thread safe
 * implementation of the hash map as long as the component has only one thread
 * because BCM components with a single thread serialises the execution of
 * their services.
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
@OfferedInterfaces(offered = {MapI.class})
// -----------------------------------------------------------------------------
public class			MapComponent<K extends Serializable,
									 V extends Serializable>
extends 	AbstractComponent
implements	MapImplementationI<K,V>
{
	// -------------------------------------------------------------------------
	// Component variables and constants
	// -------------------------------------------------------------------------

	/** the Java hash map that will contain the entries.					*/
	protected HashMap<K,V>			content ;
	/** the inbound port through which calls are received.					*/
	protected MapInboundPort<K,V>	mip ;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * create a hash map component.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @throws Exception	<i>to do</i>.
	 */
	protected			MapComponent() throws Exception
	{
		super(CVM.MAP_COMPONENT_RIBP_URI, 1, 0) ;

		this.content = new HashMap<>() ;
		this.mip = new MapInboundPort<>(this) ;
		this.mip.publishPort() ;
	}

	// -------------------------------------------------------------------------
	// Life cycle
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		this.content.clear() ;
		this.content = null ;

		super.finalise() ;
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			this.mip.unpublishPort() ;
			this.removeOfferedInterface(MapI.class) ;
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}
		super.shutdown();
	}

	// -------------------------------------------------------------------------
	// Services implementation
	// -------------------------------------------------------------------------
	
	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#put(java.io.Serializable, java.io.Serializable)
	 */
	@Override
	public void			put(K key, V value) throws Exception
	{
		this.content.put(key, value) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#get(java.io.Serializable)
	 */
	@Override
	public V			get(K key) throws Exception
	{
		return this.content.get(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#containsKey(java.io.Serializable)
	 */
	@Override
	public boolean		containsKey(K key) throws Exception
	{
		return this.content.containsKey(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#remove(java.io.Serializable)
	 */
	@Override
	public void			remove(K key) throws Exception
	{
		this.content.remove(key) ;
	}
}
// -----------------------------------------------------------------------------
