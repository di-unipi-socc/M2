package diunipisocc.microTOM.refiner;

import java.util.List;

import diunipisocc.microTOM.graph.deployment.Deployment;

/**
 * This interface is designed for performing node refinement 
 * @author javad khalili
 *
 */
public interface NodeRefiner {
	public void refine(List<Deployment> deployments);	
}
