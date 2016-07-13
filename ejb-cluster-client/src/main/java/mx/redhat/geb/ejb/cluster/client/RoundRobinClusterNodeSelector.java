package mx.redhat.geb.ejb.cluster.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.ejb.client.ClusterNodeSelector;

public class RoundRobinClusterNodeSelector implements ClusterNodeSelector
{
	private final static Logger logger = Logger.getLogger(RoundRobinClusterNodeSelector.class.getName());
	
    private int nodeIndex = 0;

    @Override
    public String selectNode(String clusterName, String[] connectedNodes, String[] availableNodes)
    {
    	String selectedNode;

        if (connectedNodes.length > 0) {
            selectedNode = connectedNodes[nodeIndex++ % connectedNodes.length];
        } else {
            nodeIndex = availableNodes.length - 1;
            selectedNode = availableNodes[nodeIndex++];
        }
        
        logger.log(Level.FINE, "RoundRobinClusterNodeSelector.selectNode => " + selectedNode);
        
        return selectedNode;
    }
}