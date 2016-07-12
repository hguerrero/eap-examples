package mx.redhat.geb.ejb.cluster;

import java.util.Random;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.Clustered;

/**
 * Session Bean implementation class StatefulSessionBean
 */
//@Stateful
//@Clustered
//@Remote(StatefulSession.class)
public class StatefulSessionBean implements StatefulSession 
{
	static final Logger log = Logger.getLogger(StatefulSessionBean.class);
	
	long id = Long.MIN_VALUE;

    /**
     * Default constructor. 
     */
    public StatefulSessionBean() 
    {
    	id = new Random().nextLong();
    	
    	log.info("Created new Stateful bean with id " + id);
    }

	/**
     * @see StatefulSession#ejecutaTransaccion(String)
     */
    public String ejecutaTransaccion(String say) 
    {
    	String hello = "Hello " + say + " from bean " + id;
    	
    	log.info(hello);
    	
    	return hello;
    }

}
