package fr.sorbonne_u.components.interfaces;

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

import java.io.Serializable;

//-----------------------------------------------------------------------------
/**
 * The interface <code>DataOfferedI</code> defines a basic interface
 * for components that exchange raw data rather than calling each others
 * services.
 *
 * <p><strong>Description</strong></p>
 * 
 * In data exchanges, the offering component produces data, while the requiring
 * component is consuming it.  With the companion interface
 * <code>DataRequiredI</code>, this interface establishes a data connection
 * in terms of basic methods called to pass the data from the offering component
 * to the requiring one.  These interfaces provide for both a push mode, where
 * the offering component takes the initiative of sending data, and a pull mode
 * where the requiring component takes the initiative of requesting data from
 * the offering one.
 * 
 * Required and offered data interfaces have three facets:
 * <ol>
 * <Li>A data interface <code>DataI</code> which provides for a generic type
 *   of data expected by the offering component.</li>
 * <li>A push interface <code>PushI</code> which contains the signatures
 *   of the methods that are called by the offering component to send data
 *   to the requiring component.</li>
 * <li>A pull interface <code>PullI</code> which contains the signatures
 *   of the methods that can be called by the requiring components to get
 *   data from the offering component.</li>
 * </ol>
 *
 * The push interface of the data required interface is connected to the push
 * interface of the data offered one, while the pull interface of the data
 * offered interface is connected to the pull interface of the data required
 * one.
 * 
 * Even though a data offered interface do not define methods directly and
 * hence cannot be "called upon", it is convenient in the framework to see
 * it as an offered interface in the interface reflective processing. 
 * 
 * <p>Created on : 2011-11-02</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public interface			DataOfferedI
extends		OfferedI
{
	/**
	 * The interface <code>DataOfferedI.DataI</code> provides for a generic
	 * type of data produced by the offering component.
	 *
	 * <p><strong>Description</strong></p>
	 * 
	 * <p>Created on : 2011-11-02</p>
	 * 
	 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
	 * @version	$Name$ -- $Revision$ -- $Date$
	 */
	public interface		DataI extends Serializable { }

	/**
	 * The interface <code>DataOfferedI.PushI</code> contains the signatures of
	 * the methods that are called by the offering component to send data to
	 * the requiring component.
	 * 
	 * <p>Created on : 2011-11-02</p>
	 * 
	 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
	 * @version	$Name$ -- $Revision$ -- $Date$
	 */
	public interface		PushI extends RequiredI {

		/**
		 * the method <code>send</code> is called from the offering
		 * component side to make the requiring side receiving a piece of data.
		 * 
		 * <p><strong>Contract</strong></p>
		 * 
		 * <pre>
		 * pre	true			// no precondition.
		 * post	true			// no postcondition.
		 * </pre>
		 *
		 * @param d		the piece of data sent by the offering component.
		 * @throws Exception <i>todo.</i>
		 */
		public void		send(DataI d) throws Exception  ;
	}

	/**
	 * The interface <code>DataOfferedI.PullI</code> contains the signatures of
	 * the methods that can be called by the requiring components to get data
	 * from the offering component.  For offered interfaces, it is an interface
	 * called from the outside of the component on an inbound port.
	 * 
	 * <p>Created on : 2011-11-02</p>
	 * 
	 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
	 * @version	$Name$ -- $Revision$ -- $Date$
	 */
	public interface		PullI extends OfferedI {

		/**
		 * the method <code>get</code> is called by the requiring component
		 * to trigger the obtaining of a piece of data from the offering one.
		 * 
		 * <p><strong>Contract</strong></p>
		 * 
		 * <pre>
		 * pre	true			// no precondition.
		 * post	true			// no postcondition.
		 * </pre>
		 *
		 * @return		the piece of data produced by the offering component
		 * @throws Exception <i>todo.</i>
		 */
		public DataI		get() throws Exception  ;
	}
}
//-----------------------------------------------------------------------------
