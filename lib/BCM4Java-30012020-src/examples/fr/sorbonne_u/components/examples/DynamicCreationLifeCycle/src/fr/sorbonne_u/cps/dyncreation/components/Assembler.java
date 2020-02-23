package fr.sorbonne_u.cps.dyncreation.components;

// Copyright Jacques Malenfant, Sorbonne Universite.
//
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

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.pre.dcc.connectors.DynamicComponentCreationConnector;
import fr.sorbonne_u.components.pre.dcc.interfaces.DynamicComponentCreationI;
import fr.sorbonne_u.components.pre.dcc.ports.DynamicComponentCreationOutboundPort;
import fr.sorbonne_u.cps.dyncreation.CVM;
import fr.sorbonne_u.cps.dyncreation.interfaces.TerminationNotificationCI;
import fr.sorbonne_u.cps.dyncreation.ports.TerminationNotificationInboundPort;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

// -----------------------------------------------------------------------------
/**
 * The class <code>Assembler</code>
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2020-02-17</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@RequiredInterfaces(required = {DynamicComponentCreationI.class})
@OfferedInterfaces(offered = {TerminationNotificationCI.class})
public class			Assembler
extends		AbstractComponent
implements	TerminationNotificationI
{
	protected DynamicComponentCreationOutboundPort	dccOutPort ;
	protected TerminationNotificationInboundPort	termNotPort ;
	protected String								jvmURI ;
	protected Set<String>							deployerURIs ;

	/**
	 * 
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param jvmURI	URI of the CVM on which the components must be deployed.
	 * @throws Exception 
	 */
	protected			Assembler(String jvmURI) throws Exception
	{
		super(1, 0) ;
		this.jvmURI = jvmURI ;

		this.termNotPort = new TerminationNotificationInboundPort(this) ;
		this.termNotPort.publishPort() ;

		this.tracer.setTitle("Assembler") ;
		this.tracer.setRelativePosition(0, 0) ;
		this.toggleTracing() ;
	}

	@Override
	public void			start() throws ComponentStartException
	{
		super.start() ;
		try {
			this.dccOutPort = new DynamicComponentCreationOutboundPort(this) ;
			this.dccOutPort.publishPort() ;
			this.doPortConnection(
				this.dccOutPort.getPortURI(),
				this.jvmURI + AbstractCVM.DCC_INBOUNDPORT_URI_SUFFIX,
				DynamicComponentCreationConnector.class.getCanonicalName()) ;
		} catch (Exception e) {
			throw new ComponentStartException(e) ;
		}
	}
	@Override
	public void			execute() throws Exception
	{
		super.execute() ;

		this.deployerURIs = new HashSet<String>() ;

		Function<Integer,Integer> f2 = (i -> i * 10) ;
		String processor2URI =
				this.dccOutPort.createComponent(
						Processor.class.getCanonicalName(),
						new Object[] {CVM.B_IBP_URI, CVM.CLIENT_IBP_URI, f2}) ;
		this.deployerURIs.add(processor2URI) ;

		Function<Integer,Integer> f1 = (i -> i + 1) ;
		String processor1URI =
				this.dccOutPort.createComponent(
						Processor.class.getCanonicalName(),
						new Object[] {CVM.A_IBP_URI, CVM.B_IBP_URI, f1}) ;
		this.deployerURIs.add(processor1URI) ;

		String clientURI =
			this.dccOutPort.createComponent(
				Client.class.getCanonicalName(),
				new Object[] {CVM.CLIENT_IBP_URI, CVM.A_IBP_URI,
							  this.termNotPort.getPortURI()}
				) ;
		this.deployerURIs.add(clientURI) ;

		for (String uri : this.deployerURIs) {
			this.dccOutPort.startComponent(uri) ;
		}

		this.dccOutPort.executeComponent(clientURI) ;
		this.logMessage("Execution...") ;

	}

	/**
	 * @see fr.sorbonne_u.cps.dyncreation.components.TerminationNotificationI#terminate()
	 */
	@Override
	public void			terminate() throws Exception
	{
		this.logMessage("Assembler now finalises and shuts down the components"
									+ " created dynamiccally.");

		for (String uri : this.deployerURIs) {
			this.dccOutPort.finaliseComponent(uri) ;
		}
		for (String uri : this.deployerURIs) {
			this.dccOutPort.shutdownComponent(uri) ;
		}
		this.deployerURIs.clear() ;

		this.logMessage("Assembler can now terminates.");
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		this.doPortDisconnection(this.dccOutPort.getPortURI()) ;
		super.finalise();
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public void			shutdown() throws ComponentShutdownException
	{
		try {
			this.dccOutPort.unpublishPort() ;
			this.termNotPort.unpublishPort() ;
		} catch (Exception e) {
			throw new ComponentShutdownException(e) ;
		}
		super.shutdown();
	}
}
// -----------------------------------------------------------------------------
