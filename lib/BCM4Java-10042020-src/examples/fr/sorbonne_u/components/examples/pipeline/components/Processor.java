package fr.sorbonne_u.components.examples.pipeline.components;

// Copyright Jacques Malenfant, Sorbonne Universite.
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

import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.examples.pipeline.interfaces.ManagementCI;
import fr.sorbonne_u.components.examples.pipeline.interfaces.PropagatingCI;
import fr.sorbonne_u.components.examples.pipeline.ports.PropagatingOutboundPort;

import java.util.function.Function;

// -----------------------------------------------------------------------------
/**
 * The class <code>Processor</code>
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
@RequiredInterfaces(required = {PropagatingCI.class})
@OfferedInterfaces(offered = {ManagementCI.class,PropagatingCI.class})
public class			Processor<T, R>
extends		AbstractPipelineElement<T>
{
	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected Function<T,R>					processing ;
	protected PropagatingOutboundPort<R>	propagatingOutPort ;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	protected			Processor(Function<T,R> processing) throws Exception
	{
		super(1, 0) ;
		this.initialise(processing) ;
	}

	protected			Processor(
		String reflectionInboundPortURI,
		Function<T,R> processing
		) throws Exception
	{
		super(reflectionInboundPortURI, 1, 0) ;
		this.initialise(processing) ;
	}

	protected void		initialise(Function<T,R> processing) throws Exception
	{
		this.processing = processing ;

		this.propagatingOutPort = new PropagatingOutboundPort<R>(this) ;
		this.propagatingOutPort.localPublishPort() ;
		this.propagatingOutPortURI = this.propagatingOutPort.getPortURI() ;
	}

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.examples.pipeline.interfaces.ManagementI#connect(java.lang.String)
	 */
	@Override
	public void			connect(String inboundPortURI) throws Exception
	{
		assert	 this.propagatingOutPort != null &&
										!this.propagatingOutPort.connected() ;

		super.connect(inboundPortURI) ;
	}

	/**
	 * @see fr.sorbonne_u.components.examples.pipeline.interfaces.PropagatingI#propagate(java.lang.Object)
	 */
	@Override
	public void			propagate(T value) throws Exception
	{
		R ret = this.processing.apply(value) ;
		if (this.propagatingOutPort != null) {
			this.propagatingOutPort.propagate(ret) ;
		}
	}
}
// -----------------------------------------------------------------------------
