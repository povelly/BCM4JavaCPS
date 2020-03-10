package fr.sorbonne_u.cps.dyncreation.components;

// Copyright Jacques Malenfant, Sorbonne Universite.
// Jacques.Malenfant@lip6.fr
//
// This software is a computer program whose purpose is to provide a
// basic component programming model to program with components
// distributed applications in the Java programming language.
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

import java.util.function.Function;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.cps.dyncreation.CVM;
import fr.sorbonne_u.cps.dyncreation.connectors.ProcessorConnector;
import fr.sorbonne_u.cps.dyncreation.interfaces.ProcessorCI;
import fr.sorbonne_u.cps.dyncreation.ports.ProcessorInboundPort;
import fr.sorbonne_u.cps.dyncreation.ports.ProcessorOutboundPort;

// -----------------------------------------------------------------------------
/**
 * The class <code>A</code>
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2020-02-12</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@OfferedInterfaces(offered = {ProcessorCI.class})
@RequiredInterfaces(required = {ProcessorCI.class})
public class			Processor<T>
extends		AbstractComponent
implements	ProcessorImplementationI<T>
{
	protected Function<T,T>							f ;
	protected String								toURI ;
	protected ProcessorInboundPort<T>				iport ;
	protected ProcessorOutboundPort<T>				oport ;

	protected			Processor(
		String myURI,
		String toURI,
		Function<T,T> f
		) throws Exception
	{
		super(1, 0) ;
		this.f = f ;
		this.toURI = toURI ;
		this.iport = new ProcessorInboundPort<T>(myURI, this) ;
		this.iport.publishPort() ;

		if (myURI.equals(CVM.A_IBP_URI)) {
			this.tracer.setTitle("Processor " + myURI) ;
			this.tracer.setRelativePosition(1, 1) ;
		} else if (myURI.equals(CVM.B_IBP_URI)) {
			this.tracer.setTitle("Processor " + myURI) ;
			this.tracer.setRelativePosition(1, 2) ;
		} else {
			this.tracer.setTitle("Client") ;
			this.tracer.setRelativePosition(1, 0) ;
		}
		this.toggleTracing() ;
	}

	protected			Processor(String myURI, Function<T,T> f)
	throws Exception
	{
		this(myURI, null, f) ;
	}

	@Override
	public void			process(T value) throws Exception {
		this.logMessage(this.iport.getPortURI() + " processes " + value) ;
		if (this.oport != null) {
			this.oport.process(f.apply(value)) ;
		} else {
			this.logMessage("result = " + f.apply(value)) ;
		}
	}

	@Override
	public void			start() throws ComponentStartException {
		super.start() ;
		try {
			this.logMessage(this.iport.getPortURI()) ;
			if (this.toURI != null) {
				this.oport = new ProcessorOutboundPort<T>(this) ;
				this.oport.publishPort() ;
				this.doPortConnection(
								this.oport.getPortURI(),
								this.toURI,
								ProcessorConnector.class.getCanonicalName()) ;
			}
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
	}

	@Override
	public void			finalise() throws Exception {
		if (this.oport != null) {
			this.doPortDisconnection(this.oport.getPortURI()) ;
		}
		super.finalise() ;
	}

	@Override
	public void			shutdown() throws ComponentShutdownException {
		try {
			this.iport.unpublishPort() ;
			if (this.oport != null) {
				this.oport.unpublishPort() ;
			}
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}
		super.shutdown();
	}
}
// -----------------------------------------------------------------------------
