package fr.sorbonne_u.components;

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

import java.lang.annotation.Annotation;
import fr.sorbonne_u.components.helpers.Logger;
import fr.sorbonne_u.components.helpers.TracerOnConsole;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import fr.sorbonne_u.components.reflection.interfaces.ReflectionI;
import fr.sorbonne_u.components.reflection.utils.ConstructorSignature;
import fr.sorbonne_u.components.reflection.utils.ServiceSignature;

// -----------------------------------------------------------------------------
/**
 * The class <code>ReflectionInboundPort</code> defines the inbound port
 * associated the interface <code>ReflectionI</code>.
 *
 * <p><strong>Description</strong></p>
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : 2016-02-25</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 */
public class				ReflectionInboundPort
extends		AbstractInboundPort
implements	ReflectionI
{
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	public				ReflectionInboundPort(
		String uri,
		ComponentI owner
		) throws Exception
	{
		super(uri, ReflectionI.class, owner) ;
	}

	public				ReflectionInboundPort(
		ComponentI owner
		) throws Exception
	{
		super(ReflectionI.class, owner);
	}

	// -------------------------------------------------------------------------
	// Plug-ins facilities
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#installPlugin(fr.sorbonne_u.components.PluginI)
	 */
	@Override
	public void			installPlugin(final PluginI plugin) throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().installPlugin(plugin) ;
							return null ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#hasInstalledPlugins()
	 */
	@Override
	public boolean		hasInstalledPlugins() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
													hasInstalledPlugins() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#finalisePlugin(java.lang.String)
	 */
	@Override
	public void			finalisePlugin(final String pluginURI) throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().finalisePlugin(pluginURI) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#uninstallPlugin(java.lang.String)
	 */
	@Override
	public void			uninstallPlugin(final String pluginId) throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().uninstallPlugin(pluginId) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isInstalled(java.lang.String)
	 */
	@Override
	public boolean		isInstalled(final String pluginId) throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().isInstalled(pluginId) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getPlugin(java.lang.String)
	 */
	@Override
	public PluginI		getPlugin(final String pluginURI)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<PluginI>() {
						@Override
						public PluginI call() throws Exception {
							return this.getServiceOwner().getPlugin(pluginURI) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#initialisePlugin(java.lang.String)
	 */
	@Override
	public void			initialisePlugin(final String pluginURI)
	throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().initialisePlugin(pluginURI) ;
							return null ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isInitialised(java.lang.String)
	 */
	@Override
	public boolean		isInitialised(final String pluginURI)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												isInitialised(pluginURI) ;
						}
					}) ;
	}

	// -------------------------------------------------------------------------
	// Logging facilities
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#toggleLogging()
	 */
	@Override
	public void			toggleLogging() throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().toggleLogging() ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#setLogger(fr.sorbonne_u.components.helpers.Logger)
	 */
	@Override
	public void			setLogger(Logger logger) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().setLogger(logger) ;
						return null ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#toggleTracing()
	 */
	@Override
	public void			toggleTracing() throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().toggleTracing() ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#setTracer(fr.sorbonne_u.components.helpers.TracerOnConsole)
	 */
	@Override
	public void			setTracer(TracerOnConsole tracer) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().setTracer(tracer) ;
						return null ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#logMessage(java.lang.String)
	 */
	@Override
	public void			logMessage(final String message) throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().logMessage(message) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isLogging()
	 */
	@Override
	public boolean		isLogging() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().isLogging() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isTracing()
	 */
	@Override
	public boolean		isTracing() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().isTracing() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#printExecutionLog()
	 */
	@Override
	public void			printExecutionLog() throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().printExecutionLog() ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#printExecutionLogOnFile(java.lang.String)
	 */
	@Override
	public void			printExecutionLogOnFile(final String fileName)
	throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().
										printExecutionLogOnFile(fileName) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#traceMessage(java.lang.String)
	 */
	@Override
	public void			traceMessage(String message) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().traceMessage(message) ;
						return null ;
					}
				}) ;
	}

	// -------------------------------------------------------------------------
	// Internal behaviour requests
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isInStateAmong(fr.sorbonne_u.components.ComponentStateI[])
	 */
	@Override
	public boolean		isInStateAmong(final ComponentStateI[] states)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
													isInStateAmong(states) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#notInStateAmong(fr.sorbonne_u.components.ComponentStateI[])
	 */
	@Override
	public boolean		notInStateAmong(final ComponentStateI[] states)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
													notInStateAmong(states) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#hasItsOwnThreads()
	 */
	@Override
	public boolean		hasItsOwnThreads() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().hasItsOwnThreads() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#hasSerialisedExecution()
	 */
	@Override
	public boolean		hasSerialisedExecution() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												hasSerialisedExecution() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#canScheduleTasks()
	 */
	@Override
	public boolean		canScheduleTasks() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().canScheduleTasks() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getTotalNumberOfThreads()
	 */
	@Override
	public int			getTotalNumberOfThreads() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Integer>() {
						@Override
						public Integer call() throws Exception {
							return this.getServiceOwner().
												getTotalNumberOfThreads() ;
						}
					}) ;
	}

	// -------------------------------------------------------------------------
	// Implemented interfaces management
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getInterfaces()
	 */
	@Override
	public Class<?>[]	getInterfaces() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Class<?>[]>() {
						@Override
						public Class<?>[] call() throws Exception {
							return this.getServiceOwner().getInterfaces() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getInterface(java.lang.Class)
	 */
	@Override
	public Class<?>		getInterface(final Class<?> inter) throws Exception
	{
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Class<?>>() {
					@Override
					public Class<?> call() throws Exception {
						return this.getServiceOwner().getInterface(inter) ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getRequiredInterfaces()
	 */
	@Override
	public Class<?>[]	getRequiredInterfaces() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Class<?>[]>() {
						@Override
						public Class<?>[] call() throws Exception {
							return this.getServiceOwner().
												getRequiredInterfaces() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getRequiredInterface(java.lang.Class)
	 */
	@Override
	public Class<?>		getRequiredInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Class<?>>() {
					@Override
					public Class<?> call() throws Exception {
						return this.getServiceOwner().
												getRequiredInterface(inter) ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getOfferedInterfaces()
	 */
	@Override
	public Class<?>[]	getOfferedInterfaces() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Class<?>[]>() {
						@Override
						public Class<?>[] call() throws Exception {
							return this.getServiceOwner().
												getOfferedInterfaces() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getOfferedInterface(java.lang.Class)
	 */
	@Override
	public Class<?>		getOfferedInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Class<?>>() {
					@Override
					public Class<?> call() throws Exception {
						return this.getServiceOwner().
												getOfferedInterface(inter) ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#addRequiredInterface(java.lang.Class)
	 */
	@Override
	public void			addRequiredInterface(final Class<?> inter)
	throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().
												addRequiredInterface(inter) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#removeRequiredInterface(java.lang.Class)
	 */
	@Override
	public void			removeRequiredInterface(final Class<?> inter)
	throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().
											removeRequiredInterface(inter) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#addOfferedInterface(java.lang.Class)
	 */
	@Override
	public void			addOfferedInterface(final Class<?> inter) throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().
											addOfferedInterface(inter) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#removeOfferedInterface(java.lang.Class)
	 */
	@Override
	public void			removeOfferedInterface(final Class<?> inter)
	throws Exception
	{
		this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Void>() {
						@Override
						public Void call() throws Exception {
							this.getServiceOwner().
											removeOfferedInterface(inter) ;
							return null;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isInterface(java.lang.Class)
	 */
	@Override
	public boolean		isInterface(final Class<?> inter) throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().isInterface(inter) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isRequiredInterface(java.lang.Class)
	 */
	@Override
	public boolean		isRequiredInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												isRequiredInterface(inter) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isOfferedInterface(java.lang.Class)
	 */
	@Override
	public boolean		isOfferedInterface(final Class<?> inter) throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												isOfferedInterface(inter) ;
						}
					}) ;
	}

	// -------------------------------------------------------------------------
	// Port management
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#findPortURIsFromInterface(java.lang.Class)
	 */
	@Override
	public String[]		findPortURIsFromInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<String[]>() {
						@Override
						public String[] call() throws Exception {
							return this.getServiceOwner().
											findPortURIsFromInterface(inter) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#findInboundPortURIsFromInterface(java.lang.Class)
	 */
	@Override
	public String[]		findInboundPortURIsFromInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<String[]>() {
						@Override
						public String[] call() throws Exception {
							return this.getServiceOwner().
									findInboundPortURIsFromInterface(inter) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#findOutboundPortURIsFromInterface(java.lang.Class)
	 */
	@Override
	public String[]		findOutboundPortURIsFromInterface(final Class<?> inter)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<String[]>() {
						@Override
						public String[] call() throws Exception {
							return this.getServiceOwner().
									findOutboundPortURIsFromInterface(inter) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getPortImplementedInterface(java.lang.String)
	 */
	@Override
	public Class<?>		getPortImplementedInterface(final String portURI)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Class<?>>() {
						@Override
						public Class<?> call() throws Exception {
							return this.getServiceOwner().
										getPortImplementedInterface(portURI) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isPortExisting(java.lang.String)
	 */
	@Override
	public boolean			isPortExisting(String portURI) throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												isPortExisting(portURI) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#isPortConnected(java.lang.String)
	 */
	@Override
	public boolean		isPortConnected(final String portURI)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return this.getServiceOwner().
												isPortConnected(portURI) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#doPortConnection(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void			doPortConnection(
		final String portURI,
		final String otherPortURI,
		final String ccname
		) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().
							doPortConnection(portURI, otherPortURI, ccname) ;
						return null ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#doPortDisconnection(java.lang.String)
	 */
	@Override
	public void			doPortDisconnection(final String portURI)
	throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().doPortDisconnection(portURI) ;
						return null ;
					}
				}) ;
	}

	// -------------------------------------------------------------------------
	// Reflection facility
	// -------------------------------------------------------------------------

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getComponentDefinitionClassName()
	 */
	@Override
	public String		getComponentDefinitionClassName() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<String>() {
						@Override
						public String call() throws Exception {
							return this.getServiceOwner().
										getComponentDefinitionClassName() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getComponentAnnotations()
	 */
	@Override
	public Annotation[]	getComponentAnnotations() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Annotation[]>() {
						@Override
						public Annotation[] call() throws Exception {
							return this.getServiceOwner().
										getComponentAnnotations() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getComponentLoader()
	 */
	@Override
	public ClassLoader	getComponentLoader() throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<ClassLoader>() {
						@Override
						public ClassLoader call() throws Exception {
							return this.getServiceOwner().
												getComponentLoader() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getComponentServiceSignatures()
	 */
	@Override
	public ServiceSignature[]	getComponentServiceSignatures()
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<ServiceSignature[]>() {
						@Override
						public ServiceSignature[] call() throws Exception {
							return this.getServiceOwner().
											getComponentServiceSignatures() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntrospectionI#getComponentConstructorSignatures()
	 */
	@Override
	public ConstructorSignature[]	getComponentConstructorSignatures()
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<ConstructorSignature[]>() {
						@Override
						public ConstructorSignature[] call() throws Exception {
							return this.getServiceOwner().
										getComponentConstructorSignatures() ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#newInstance(java.lang.Object[])
	 */
	@Override
	public ComponentI		newInstance(Object[] parameters)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<ComponentI>() {
						@Override
						public ComponentI call() throws Exception {
							return this.getServiceOwner().
												newInstance(parameters) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#invokeService(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object		invokeService(String name, Object[] params)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Object>() {
						@Override
						public Object call() throws Exception {
							return this.getServiceOwner().
												invokeService(name, params) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#invokeServiceSync(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object		invokeServiceSync(String name, Object[] params)
	throws Exception
	{
		return this.getOwner().handleRequestSync(
					new AbstractComponent.AbstractService<Object>() {
						@Override
						public Object call() throws Exception {
							return this.getServiceOwner().
											invokeServiceSync(name, params) ;
						}
					}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#invokeServiceAsync(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void			invokeServiceAsync(String name, Object[] params)
	throws Exception
	{
		this.getOwner().handleRequestSync(
			new AbstractComponent.AbstractService<Void>() {
				@Override
				public Void call() throws Exception {
					this.getServiceOwner().invokeServiceAsync(name, params) ;
					return null ;
				}
			}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#execute()
	 */
	@Override
	public void			execute() throws Exception
	{
		this.getOwner().runTask(
			new AbstractComponent.AbstractTask() {
				@Override
				public void run() {
					try {
						this.getTaskOwner().execute() ;
					} catch (Exception e) {
						throw new RuntimeException(e) ;
					}
				}
			}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#runTask(fr.sorbonne_u.components.reflection.interfaces.IntercessionI.AbstractRemoteComponentTask)
	 */
	@Override
	public void			runTask(AbstractRemoteComponentTask t)
	throws Exception
	{
		AbstractComponent.AbstractTask task =
				new AbstractComponent.AbstractTask() {
					public void run() {
						try {
							t.run() ;
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				} ;
		t.setComponentTask(task) ;
		this.getOwner().runTask(task) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#runTask(java.lang.String, fr.sorbonne_u.components.reflection.interfaces.IntercessionI.AbstractRemoteComponentTask)
	 */
	@Override
	public void			runTask(
		String executorServiceURI,
		AbstractRemoteComponentTask t
		) throws Exception
	{
		AbstractComponent.AbstractTask task =
				new AbstractComponent.AbstractTask() {
					public void run() {
						try {
							t.run() ;
						} catch (Exception e) {
							throw new RuntimeException(e) ;
						}
					}
				} ;
		t.setComponentTask(task) ;
		this.getOwner().runTask(executorServiceURI, task) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#insertBeforeService(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public void			insertBeforeService(
		String methodName,
		String[] parametersCanonicalClassNames,
		String code
		) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().insertBeforeService(
							methodName, parametersCanonicalClassNames, code) ;
						return null ;
					}
				}) ;
	}

	/**
	 * @see fr.sorbonne_u.components.reflection.interfaces.IntercessionI#insertAfterService(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public void			insertAfterService(
		String methodName,
		String[] parametersCanonicalClassNames,
		String code
		) throws Exception
	{
		this.getOwner().handleRequestSync(
				new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						this.getServiceOwner().insertAfterService(
							methodName, parametersCanonicalClassNames, code) ;
						return null ;
					}
				}) ;
	}
}
// -----------------------------------------------------------------------------
