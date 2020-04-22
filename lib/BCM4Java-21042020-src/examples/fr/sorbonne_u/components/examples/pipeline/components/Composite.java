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

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.examples.pipeline.connectors.ManagementConnector;
import fr.sorbonne_u.components.examples.pipeline.connectors.PropagatingConnector;
import fr.sorbonne_u.components.examples.pipeline.interfaces.ManagementCI;
import fr.sorbonne_u.components.examples.pipeline.interfaces.PropagatingCI;
import fr.sorbonne_u.components.examples.pipeline.ports.ManagementOutboundPort;
import fr.sorbonne_u.components.examples.pipeline.ports.PropagatingOutboundPort;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.components.reflection.connectors.ReflectionConnector;
import fr.sorbonne_u.components.reflection.ports.ReflectionOutboundPort;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

// -----------------------------------------------------------------------------
/**
 * The class <code>Composite</code>
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
@RequiredInterfaces(required = {ManagementCI.class,PropagatingCI.class})
@OfferedInterfaces(offered = {PropagatingCI.class})
public class			Composite<T,R>
extends		AbstractPipelineElement<T>
{
	// -------------------------------------------------------------------------
	// Inner interfaces and classes
	// -------------------------------------------------------------------------

	public static interface		PipeLineElementI<T1,R1>
	{
		public boolean			isPredicate() ;
		public Predicate<T1>	getPredicate() ;
		public Function<T1,R1>	getFunction() ;
	}

	public static class			FilterElement<T1>
	implements	PipeLineElementI<T1,Void>
	{
		protected Predicate<T1>	p ;

		public FilterElement(Predicate<T1> p) {
			super() ;
			this.p = p ;
		}

		@Override
		public boolean				isPredicate() { return true ; }

		@Override
		public Predicate<T1>		getPredicate() { return this.p ; }

		@Override
		public Function<T1, Void>	getFunction() {
			throw new RuntimeException("Not a processor element!") ;
		}

	}

	public static class			ProcessorElement<T1,R1>
	implements	PipeLineElementI<T1,R1>
	{
		protected Function<T1,R1> f ;

		public ProcessorElement(Function<T1, R1> f) {
			super();
			this.f = f;
		}

		@Override
		public boolean				isPredicate() { return false ; }

		@Override
		public Predicate<T1>		getPredicate()
		{
			throw new RuntimeException("Not a processor element!") ;
		}

		@Override
		public Function<T1, R1>		getFunction() { return this.f ; }
	}

	protected static class		CompositePropagatingInboundPort<T2>
	extends		AbstractInboundPort
	implements	PropagatingCI<T2>
	{
		private static final long serialVersionUID = 1L ;
		protected PropagatingOutboundPort<T2> outPort ;

		public				CompositePropagatingInboundPort(
			String uri,
			ComponentI owner,
			PropagatingOutboundPort<T2> outPort
			) throws Exception
		{
			super(uri, PropagatingCI.class, owner) ;
			this.outPort = outPort ;
		}

		public				CompositePropagatingInboundPort(
			ComponentI owner,
			PropagatingOutboundPort<T2> outPort
			) throws Exception
		{
			super(PropagatingCI.class, owner) ;
			this.outPort = outPort ;
		}

		/**
		 * @see fr.sorbonne_u.components.examples.pipeline.interfaces.PropagatingI#propagate(java.lang.Object)
		 */
		@Override
		public void			propagate(T2 value) throws Exception
		{
			// Coming from the subcomponents pipeline, propagating to the
			// outside of the composite component.
			this.outPort.propagate(value) ;
		}
	}

	// -------------------------------------------------------------------------
	// Constants and variables
	// -------------------------------------------------------------------------

	protected PropagatingOutboundPort<R>			propagatingOutPort ;
	protected PropagatingOutboundPort<T>			portToSubcomponents ;
	protected CompositePropagatingInboundPort<R>	portFromSubcomponents ;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	public <T1,R1>		Composite(ArrayList<PipeLineElementI<T1,R1>> elems)
	throws Exception
	{
		super(1, 0) ;
		this.initialise(elems) ;
	}

	public <T1,R1>		Composite(
		String reflectionInboundPortURI,
		ArrayList<PipeLineElementI<T1,R1>> elems
		) throws Exception
	{
		super(reflectionInboundPortURI, 1, 0) ;
		this.initialise(elems) ;
	}

	protected <T1,R1> void	initialise(
		ArrayList<PipeLineElementI<T1,R1>> elems
		) throws Exception
	{
		assert	elems != null && elems.size() > 0 ;

		super.initialise() ;
		this.propagatingOutPort = new PropagatingOutboundPort<R>(this) ;
		this.propagatingOutPort.localPublishPort() ;
		this.propagatingOutPortURI = this.propagatingOutPort.getPortURI() ;

		String[] uris = this.createElement(elems.get(0)) ;
		this.portToSubcomponents = new PropagatingOutboundPort<T>(this) ;
		this.portToSubcomponents.localPublishPort() ;
		this.doPortConnection(this.portToSubcomponents.getPortURI(),
							  uris[1],
							  PropagatingConnector.class.getCanonicalName()) ;
		ManagementOutboundPort mop = new ManagementOutboundPort(this) ;
		mop.localPublishPort() ;

		for (int i = 1 ; i < elems.size() ; i++) {
			String previous_MIP_URI = uris[0] ;
			uris = this.createElement(elems.get(i)) ;
			this.doPortConnection(
					mop.getPortURI(),
					previous_MIP_URI,
					ManagementConnector.class.getCanonicalName()) ;
			mop.connect(uris[1]) ;
			this.doPortDisconnection(mop.getPortURI()) ;
		}

		this.doPortConnection(
				mop.getPortURI(),
				uris[0],
				ManagementConnector.class.getCanonicalName()) ;
		this.portFromSubcomponents =
			new CompositePropagatingInboundPort<R>(this,
												   this.propagatingOutPort) ;
		this.portFromSubcomponents.localPublishPort() ;
		mop.connect(this.portFromSubcomponents.getPortURI()) ;
		this.doPortDisconnection(mop.getPortURI()) ;

	}

	protected <T1,R1> String[]	createElement(PipeLineElementI<T1,R1> elem)
	throws Exception
	{
		String[] ret = null ;
		if (elem.isPredicate()) {
			ret = this.createFilter(elem.getPredicate()) ;
		} else {
			ret = this.createProcessor(elem.getFunction()) ;
		}
		return ret ;
	}

	protected <V> String[]	createFilter(Predicate<V> p) throws Exception
	{
		String component_RIP_URI =
			Composite.this.createSubcomponent(
						Filter.class.getCanonicalName(),
						new Object[]{p}) ;
		return this.getPipelineElementURIs(component_RIP_URI) ;
	}

	public <V,W> String[]	createProcessor(Function<V,W> f) throws Exception
	{
		String component_RIP_URI =
			Composite.this.createSubcomponent(
						Processor.class.getCanonicalName(),
						new Object[]{f}) ;
		return this.getPipelineElementURIs(component_RIP_URI) ;
	}

	protected String[]	getPipelineElementURIs(String component_RIP_URI)
	throws Exception
	{
		String[] ret = new String[2] ;
		ReflectionOutboundPort rop =
							new ReflectionOutboundPort(Composite.this) ;
		rop.localPublishPort() ;
		Composite.this.doPortConnection(
							rop.getPortURI(),
							component_RIP_URI,
							ReflectionConnector.class.getCanonicalName()) ;
		String[] temp =
				rop.findInboundPortURIsFromInterface(ManagementCI.class) ;
		assert	temp != null && temp.length == 1 && temp[0] != null ;
		ret[0] = temp[0] ;
		temp = rop.findInboundPortURIsFromInterface(PropagatingCI.class) ;
		assert	temp != null && temp.length == 1 && temp[0] != null ;
		ret[1] = temp[0] ;
		Composite.this.doPortDisconnection(rop.getPortURI()) ;
		return ret ;
	}

	@Override
	public void			propagate(T value) throws Exception
	{
		// Coming from the outside of the composite component, propagating
		// into the subcomponents pipeline.
		this.portToSubcomponents.propagate(value) ;
	}
}
// -----------------------------------------------------------------------------
