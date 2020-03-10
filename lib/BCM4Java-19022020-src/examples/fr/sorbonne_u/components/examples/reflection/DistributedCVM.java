package fr.sorbonne_u.components.examples.reflection;

import fr.sorbonne_u.components.AbstractComponent;

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

import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
import fr.sorbonne_u.components.examples.reflection.components.ReflectionClient;
import fr.sorbonne_u.components.examples.reflection.components.ReflectionServer;

//-----------------------------------------------------------------------------
/**
 * The class <code>DistributedCVM</code> assembles the component and start the
 * execution of the reflection example as a multi-JVM application.
 *
 * <p><strong>Description</strong></p>
 * 
 * To be able to compile and execute this example, the jar
 * <code>tools.jar</code> both in the classpath of the compiler and in
 * the classpath of the VM. The VM must also be passed the java agent
 * <code>hotswap.jar</code>, provided in the example directory, as
 * shown in the <code>start-dcvm</code>. The policy in
 * <code>dcvm.policy</code> must also be adjusted so that the Java
 * libraries are correctly pointed at.
 *
 * The jar of the tool javassist (<code>javassist.jar</code>, typically)
 * must also be in the classpath.
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2018-01-03</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public class				DistributedCVM
extends		AbstractDistributedCVM
{
	protected static String		SERVER_JVM_URI = "server" ;
	protected static String		CLIENT_JVM_URI = "client" ;
	protected static String		SERVER_RIP_URI = "server-rip" ;

	public				DistributedCVM(String[] args, int xLayout, int yLayout)
	throws Exception
	{
		super(args, xLayout, yLayout);
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#instantiateAndPublish()
	 */
	@Override
	public void			instantiateAndPublish() throws Exception
	{
		if (thisJVMURI.equals(SERVER_JVM_URI)) {

			AbstractComponent.createComponent(
					ReflectionServer.class.getCanonicalName(),
					new Object[]{SERVER_RIP_URI}) ;

		} else if (thisJVMURI.equals(CLIENT_JVM_URI)) {

			AbstractComponent.createComponent(
					ReflectionClient.class.getCanonicalName(),
					new Object[]{SERVER_RIP_URI}) ;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;
		}
		super.instantiateAndPublish() ;
	}

	public static void	main(String[] args)
	{
		try {
			DistributedCVM dcvm = new DistributedCVM(args, 2, 5) ;
			dcvm.startStandardLifeCycle(1000L) ;
			Thread.sleep(10000L) ;
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}
//-----------------------------------------------------------------------------
