package fr.sorbonne_u.cps.map.ports;

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

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import fr.sorbonne_u.cps.map.interfaces.MapI;

import java.io.Serializable;

// -----------------------------------------------------------------------------
/**
 * The class <code>MapOutboundPort</code> implements an outbound port for the
 * <code>MapI</code> component interface.
 *
 * <p><strong>Description</strong></p>
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
public class				MapOutboundPort<K extends Serializable,
										V extends Serializable>
extends		AbstractOutboundPort
implements	MapI<K, V>
{
	private static final long serialVersionUID = 1L;

	public				MapOutboundPort(ComponentI owner)
	throws Exception
	{
		super(MapI.class, owner);
	}

	public				MapOutboundPort(String uri, ComponentI owner)
	throws Exception
	{
		super(uri, MapI.class, owner);
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#put(java.io.Serializable, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void			put(K key, V value) throws Exception
	{
		((MapI<K,V>)this.connector).put(key, value) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V				get(K key) throws Exception
	{
		return ((MapI<K,V>)this.connector).get(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#containsKey(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean		containsKey(K key) throws Exception
	{
		return ((MapI<K,V>)this.connector).containsKey(key) ;
	}

	/**
	 * @see fr.sorbonne_u.cps.map.components.MapImplementationI#remove(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void remove(K key) throws Exception
	{
		((MapI<K,V>)this.connector).remove(key) ;
	}
}
// -----------------------------------------------------------------------------
