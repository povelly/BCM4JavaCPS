package fr.sorbonne_u.components.examples.pipeline.components;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.examples.pipeline.interfaces.PropagatingI;
import fr.sorbonne_u.components.examples.pipeline.ports.PropagatingInboundPort;
import fr.sorbonne_u.components.examples.pipeline.ports.PropagatingOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;

// Copyright Jacques Malenfant, Sorbonne Universite.
//
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to provide a
// new implementation of the DEVS simulation standard for Java.
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

//------------------------------------------------------------------------------
/**
 * The class <code>Client</code>
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2020-01-07</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@RequiredInterfaces(required = {ReflectionI.class})
public class			Client
extends		AbstractComponent
implements	PropagatingI<Integer>
{
	protected PropagatingOutboundPort<Integer>	portToPipeline ;
	protected PropagatingInboundPort<Integer>	portFromPipeline ;
	protected String							pipeline_RIP_URI ;

	public				Client(String pipeline_RIP_URI)
	{
		super(1, 0) ;
		this.pipeline_RIP_URI = pipeline_RIP_URI ;
	}

	public				Client(
		String reflectionInboundPortURI,
		String pipeline_RIP_URI
		)
	{
		super(reflectionInboundPortURI, 1, 0) ;
		this.pipeline_RIP_URI = pipeline_RIP_URI ;
	}

	@Override
	public void			start() throws ComponentStartException
	{
		super.start() ;
		try {
			ReflectionOutboundPort rop = new ReflectionOutboundPort(this) ;
			rop.publishPort() ;
			this.doPortConnection(
					rop.getPortURI(),
					pipeline_RIP_URI,
					ReflectionConnector.class.getCanonicalName()) ;
			
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		super.execute();
	}

	@Override
	public void			propagate(Integer value) throws Exception
	{
		
	}

}
//------------------------------------------------------------------------------
