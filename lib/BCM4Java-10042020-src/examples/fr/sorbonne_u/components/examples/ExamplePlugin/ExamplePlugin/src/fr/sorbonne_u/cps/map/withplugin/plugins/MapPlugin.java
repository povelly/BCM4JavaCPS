package fr.sorbonne_u.cps.map.withplugin.plugins;

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

import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.cps.map.components.MapImplementationI;
import fr.sorbonne_u.cps.map.interfaces.MapI;
import fr.sorbonne_u.cps.map.withplugin.ports.MapInboundPortForPlugin;

import java.io.Serializable;

// -----------------------------------------------------------------------------
/**
 * The class <code>MapPlugin</code> implements the map component side plug-in
 * for the <code>MapI</code> component interface and associated ports and
 * connectors.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * This implementation forces calls to the services to pass by the plug-in, but
 * for this example, that would not be necessary. The inbound port could simply
 * call the implementation methods of the component without calling the plug-in
 * methods. This is done only to illustrate the case where the calls need to
 * pass by the plug-in implementation.
 * </p>
 * <p>
 * To obtain the simpler implementation, this plug-in would not implement
 * (in the Java sense) the interface <code>MapImplementationI</code> and
 * the related methods. It would use the original inbound port
 * <code>MapInboundPort</code> rather than <code>MapInboundPortForPlugin</code>.
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
public class			MapPlugin<K extends Serializable,
								  V extends Serializable>
extends 	AbstractPlugin
implements	MapImplementationI<K,V>
{
	// -------------------------------------------------------------------------
	// Plug-in variables and constants
	// -------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	/** the inbound port which calls will be on this plug-in.				*/
	protected MapInboundPortForPlugin<K,V>	mip ;

	// -------------------------------------------------------------------------
	// Life cycle
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.AbstractPlugin#installOn(fr.sorbonne_u.components.ComponentI)
	 */
	@Override
	public void			installOn(ComponentI owner) throws Exception
	{
		super.installOn(owner) ;

		assert	owner instanceof MapImplementationI ;

		// Add interfaces and create ports
		this.addOfferedInterface(MapI.class) ;
		this.mip = new MapInboundPortForPlugin<K,V>(
									this.getPluginURI(), this.owner) ;
		this.mip.publishPort() ;
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractPlugin#uninstall()
	 */
	@Override
	public void			uninstall() throws Exception
	{
		this.mip.unpublishPort() ;
		this.mip.destroyPort() ;
		this.removeOfferedInterface(MapI.class) ;
	}

	// -------------------------------------------------------------------------
	// Plug-in services implementation
	// -------------------------------------------------------------------------

	/**
	 * this methods is meant to simplify the code by making the cast of the
	 * reference to the owner component to <code>MapImplementationI</code>.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @return	the reference to the owner properly casted.
	 */
	@SuppressWarnings("unchecked")
	private MapImplementationI<K,V>	getOwner()
	{
		return ((MapImplementationI<K,V>)this.owner) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#put(java.io.Serializable, java.io.Serializable)
	 */
	@Override
	public void			put(K key, V value) throws Exception
	{
		this.getOwner().put(key, value) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#get(java.io.Serializable)
	 */
	@Override
	public V			get(K key) throws Exception
	{
		return this.getOwner().get(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#containsKey(java.io.Serializable)
	 */
	@Override
	public boolean		containsKey(K key) throws Exception
	{
		return this.getOwner().containsKey(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#remove(java.io.Serializable)
	 */
	@Override
	public void			remove(K key) throws Exception
	{
		this.getOwner().remove(key) ;
	}
}
// -----------------------------------------------------------------------------
