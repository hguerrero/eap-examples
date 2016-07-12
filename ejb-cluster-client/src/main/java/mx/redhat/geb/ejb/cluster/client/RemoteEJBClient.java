package mx.redhat.geb.ejb.cluster.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.redhat.geb.ejb.cluster.StatelessSession;

public class RemoteEJBClient 
{

	public static void main(String[] args) throws Exception 
	{
		// Invoke a stateless bean
		invokeStatelessBean();
	}

	/**
	 * Looks up a stateless bean and invokes on it
	 *
	 * @throws NamingException
	 */
	private static void invokeStatelessBean() throws Exception 
	{
		// Let's lookup the remote stateless bean
		final StatelessSession statelessRemoteBean = lookupRemoteStatelessBean();
		
		int loops = 10;
		
		for (int i=0; i < loops; i++) 
		{
			String say = statelessRemoteBean.ejecutaTransaccion("World! from Client " + (i+1));

			System.out.println(String.format("Petition %d from Server returned %s", i, say));
			
			if ((i+1<loops) && ((i+1)%5==0)) {
				Thread.sleep(30000);
			}
		}
	}

	/**
	 * Looks up and returns the proxy to remote stateless calculator bean
	 *
	 * @return
	 * @throws NamingException
	 */
	private static StatelessSession lookupRemoteStatelessBean() throws NamingException 
	{
		final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		final Context context = new InitialContext(jndiProperties);

		// The JNDI lookup name for a stateless session bean has the syntax of:
		// ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
		//
		// <appName> The application name is the name of the EAR that the EJB is deployed in 
		//           (without the .ear).  If the EJB JAR is not deployed in an EAR then this is
		//           blank.  The app name can also be specified in the EAR's application.xml
		//           
		// <moduleName> By the default the module name is the name of the EJB JAR file (without the
		//              .jar suffix).  The module name might be overridden in the ejb-jar.xml
		//
		// <distinctName> : AS7 allows each deployment to have an (optional) distinct name. 
		//                  This example does not use this so leave it blank.
		//
		// <beanName>     : The name of the session been to be invoked.
		//
		// <viewClassName>: The fully qualified classname of the remote interface.  Must include
		//                  the whole package name.

		// let's do the lookup
		return (StatelessSession) context.lookup(
				"ejb:ejb-cluster/ejb-cluster-ejb//StatelessSessionBean!" + StatelessSession.class.getName()
				);
	}
}
