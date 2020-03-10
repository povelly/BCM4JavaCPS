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

import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.cps.dyncreation.connectors.TerminationNotificationConnector;
import fr.sorbonne_u.cps.dyncreation.interfaces.ProcessorCI;
import fr.sorbonne_u.cps.dyncreation.interfaces.TerminationNotificationCI;
import fr.sorbonne_u.cps.dyncreation.ports.TerminationNotificationOutboundPort;

// -----------------------------------------------------------------------------
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
 * <p>Created on : 2020-02-12</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
@OfferedInterfaces(offered = {ProcessorCI.class})
@RequiredInterfaces(required = {ProcessorCI.class,
								TerminationNotificationCI.class})
public class			Client
extends		Processor<Integer>
{
	protected int		result ;
	protected String	termNotURI ;

	protected			Client(
		String myURI,
		String toURI,
		String termNotURI
		) throws Exception
	{
		super(myURI, toURI, null) ;
		this.termNotURI = termNotURI ;
	}

	@Override
	public void			execute() throws Exception
	{
		this.logMessage("starting process(" + 1 + ")") ;
		this.oport.process(1) ;
	}

	@Override
	public void			process(Integer value) throws Exception
	{
		this.result = value ;
		this.logMessage("result = " + this.result) ;

		TerminationNotificationOutboundPort p =
							new TerminationNotificationOutboundPort(this) ;
		p.publishPort() ;
		this.doPortConnection(
				p.getPortURI(),
				this.termNotURI,
				TerminationNotificationConnector.class.getCanonicalName()) ;
		p.notifyTermination() ;
		this.doPortDisconnection(p.getPortURI()) ;
		p.unpublishPort() ;
	}
}
// -----------------------------------------------------------------------------
