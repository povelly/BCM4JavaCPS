package fr.sorbonne_u.components.examples.basic_cs.components;

//Copyright Jacques Malenfant, Sorbonne Universite.
//
//Jacques.Malenfant@lip6.fr
//
//This software is a computer program whose purpose is to provide a
//basic component programming model to program with components
//distributed applications in the Java programming language.
//
//This software is governed by the CeCILL-C license under French law and
//abiding by the rules of distribution of free software.  You can use,
//modify and/ or redistribute the software under the terms of the
//CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
//URL "http://www.cecill.info".
//
//As a counterpart to the access to the source code and  rights to copy,
//modify and redistribute granted by the license, users are provided only
//with a limited warranty  and the software's author,  the holder of the
//economic rights,  and the successive licensors  have only  limited
//liability. 
//
//In this respect, the user's attention is drawn to the risks associated
//with loading,  using,  modifying and/or developing or reproducing the
//software by the user in light of its specific status of free software,
//that may mean  that it is complicated to manipulate,  and  that  also
//therefore means  that it is reserved for developers  and  experienced
//professionals having in-depth computer knowledge. Users are therefore
//encouraged to load and test the software's suitability as regards their
//requirements in conditions enabling the security of their systems and/or 
//data to be ensured and,  more generally, to use and operate it in the 
//same conditions as regards security. 
//
//The fact that you are presently reading this means that you have had
//knowledge of the CeCILL-C license and that you accept its terms.

import java.util.concurrent.TimeUnit;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.examples.basic_cs.interfaces.URIConsumerI;
import fr.sorbonne_u.components.examples.basic_cs.ports.URIConsumerOutboundPort;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

//-----------------------------------------------------------------------------
/**
 * The class <code>URIConsumer</code> implements a component that gets URI
 * from a URI provider component.
 *
 * <p><strong>Description</strong></p>
 * 
 * The component declares its required service through the required interface
 * <code>URIConsumerI</code> which has a <code>getURI</code> requested service
 * signature.  The internal method <code>getURIandPrint</code> implements the
 * main task of the component, as it calls the provider component through the
 * outbound port implementing the connection.  It does that repeatedly ten
 * times then disconnect and halt.  The <code>start</code> method initiates
 * this process. 
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2014-01-22</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
// The next annotation requires that the referenced interface is added to
// the required interfaces of the component.
@RequiredInterfaces(required = {URIConsumerI.class})
public class			URIConsumer
extends		AbstractComponent
{
	// ------------------------------------------------------------------------
	// Constructors and instance variables
	// ------------------------------------------------------------------------

	protected final static int	N = 2 ;

	/**	the outbound port used to call the service.							*/
	protected URIConsumerOutboundPort	uriGetterPort ;
	/**	counting service invocations.										*/
	protected int						counter ;

	/**
	 * @param uri				URI of the component
	 * @param outboundPortURI	URI of the URI getter outbound port.
	 * @throws Exception			<i>todo.</i>
	 */
	protected				URIConsumer(
		String uri,
		String outboundPortURI
		) throws Exception
	{
		// the reflection inbound port URI is the URI of the component
		// no simple thread and one schedulable thread
		super(uri, 0, 1) ;
		// if the required interface is not declared in the annotation
		// on the component class, it can be added manually with the
		// following instruction:
		//this.addRequiredInterface(URIConsumerI.class) ;

		// create the port that exposes the required interface
		this.uriGetterPort =
						new URIConsumerOutboundPort(outboundPortURI, this) ;
		// publish the port (an outbound port is always local)
		this.uriGetterPort.localPublishPort() ;
		this.counter = 0 ;

		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir")) ;
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home")) ;
		}
		this.tracer.setTitle("consumer") ;
		this.tracer.setRelativePosition(1, 1) ;
	}

	//-------------------------------------------------------------------------
	// Component internal services
	//-------------------------------------------------------------------------

	/**
	 * method that implements the component's behaviour: call the URI service
	 * ten times and print the URI on the terminal, waiting a second between
	 * each call.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true			// no precondition.
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @throws Exception	<i>todo.</i>
	 */
	public void			getURIandPrint() throws Exception
	{
		this.counter++ ;
		if (this.counter <= 10) {
			// Get the next URI and print it
			String uri = this.uriGetterPort.getURI() ;
			this.logMessage("consumer getting a new URI no "
							+ this.counter + ": " + uri + ".") ;

			// Get a set of new URIs and print them
			String[] uris = this.uriGetterPort.getURIs(URIConsumer.N) ;
			StringBuffer mes = new StringBuffer() ;
			for (int i = 0 ; i < URIConsumer.N ; i++) {
				mes.append(uris[i]) ;
				if (i < URIConsumer.N - 1) {
					mes.append(", ") ;
				}
			}
			this.logMessage("consumer getting a new set of URIs no "
							+ this.counter + " [" + mes + "].") ;

			// Schedule the next service method invocation in one second.
			// All tasks and services of a component must be called through
			// the methods for running tasks and handling requests.  These
			// methods (from the CVM) handles the internal concurrency of
			// the component when required, and therefore ensure their good
			// properties (like synchronisation).
			this.scheduleTask(
					new AbstractComponent.AbstractTask() {
						@Override
						public void run() {
							try {
								((URIConsumer)this.getTaskOwner()).
													getURIandPrint() ;
							} catch (Exception e) {
								throw new RuntimeException(e) ;
							}
						}
					},
					1000, TimeUnit.MILLISECONDS) ;
		}
	}

	//-------------------------------------------------------------------------
	// Component life-cycle
	//-------------------------------------------------------------------------

	/**
	 * a component is always started by calling this method, so intercept the
	 * call and make sure the task of the component is executed.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
	@Override
	public void			start() throws ComponentStartException
	{
		super.start() ;
		this.logMessage("starting consumer component.") ;
		// Schedule the first service method invocation in one second.
		this.scheduleTask(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						((URIConsumer)this.getTaskOwner()).getURIandPrint() ;
					} catch (Exception e) {
						throw new RuntimeException(e) ;
					}
				}
			},
			1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		this.logMessage("stopping consumer component.") ;
		this.printExecutionLogOnFile("consumer");
		// This is the place where to clean up resources, such as
		// disconnecting and unpublishing ports that will be destroyed
		// when shutting down.
		// In static architectures like in this example, ports can also
		// be disconnected by the finalise method of the component
		// virtual machine.
		this.uriGetterPort.unpublishPort() ;

		// This called at the end to make the component internal
		// state move to the finalised state.
		super.finalise();
	}
}
//-----------------------------------------------------------------------------
