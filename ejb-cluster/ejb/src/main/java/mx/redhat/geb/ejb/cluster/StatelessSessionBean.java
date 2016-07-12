package mx.redhat.geb.ejb.cluster;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.Clustered;

/**
 * Session Bean implementation class StatelessSessionBean
 */
@Stateless
@Clustered
@Remote(StatelessSession.class)
public class StatelessSessionBean implements StatelessSession 
{
	static final Logger log = Logger.getLogger(StatelessSessionBean.class);
	
    /**
     * Default constructor. 
     */
    public StatelessSessionBean() 
    {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see StatelessSession#ejecutaTransaccion()
     */
    public String ejecutaTransaccion(String say) 
    {
    	String hello = "Hello " + say;
    	
    	log.info(hello);
    	
    	return hello;
    }

}
