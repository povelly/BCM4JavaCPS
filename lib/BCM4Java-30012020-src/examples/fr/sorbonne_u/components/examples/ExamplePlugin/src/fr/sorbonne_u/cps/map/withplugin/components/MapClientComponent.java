package fr.sorbonne_u.cps.map.withplugin.components;

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
import fr.sorbonne_u.cps.map.withplugin.plugins.MapClientPlugin;

//------------------------------------------------------------------------------
/**
 * The class <code>MapClientComponent</code> implements a client component for
 * the hash map component that uses a plug-in to connect and use the hash map.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p>
 * Instead of including all the required code to use the hash map component,
 * this code is factorised into the <code>MapClientPlugin</code> to be
 * reusable and this component has only to install an instance of this plug-in
 * and refer to it when it needs to call the hash map component.
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
public class			MapClientComponent
extends		AbstractComponent
{
	// -------------------------------------------------------------------------
	// Component variables and constants
	// -------------------------------------------------------------------------

	/** the URI that will be used for the plug-in (assumes a singleton).	*/
	protected final static String	MY_PLUGIN_URI = "map-client-plugin-uri" ;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	protected			MapClientComponent()
	{
		super(1, 0) ;
	}

	protected			MapClientComponent(String reflectionInboundPortURI)
	{
		super(reflectionInboundPortURI, 1, 0);
	}

	// -------------------------------------------------------------------------
	// Life cycle
	// -------------------------------------------------------------------------

	@Override
	public void			execute() throws Exception
	{
		super.execute() ;

		// Install the plug-in.
		MapClientPlugin<String,Integer> plugin = new MapClientPlugin<>() ;
		plugin.setPluginURI(MY_PLUGIN_URI) ;
		this.installPlugin(plugin) ;

		// Test scenario
		plugin.put("a", 1) ;		// note the reference to the plug-in
		plugin.put("b", 2) ;		// rather than directly to an outbound port
		plugin.put("c", 3) ;
		System.out.println("" + plugin.containsKey("a")) ;
		System.out.println("" + plugin.containsKey("b")) ;
		System.out.println("" + plugin.containsKey("c")) ;
		System.out.println("" + plugin.get("a")) ;
		System.out.println("" + plugin.get("b")) ;
		System.out.println("" + plugin.get("c")) ;
	}
}
//------------------------------------------------------------------------------
