package com.diunipisocc.m2.refiner;

import java.util.List;

import com.diunipisocc.m2.graph.deployment.Deployment;

/**
 * This interface is designed for performing node refinement 
 * @author javad khalili
 *
 */
public interface NodeRefiner {
	public void refine(List<Deployment> deployments);	
}
