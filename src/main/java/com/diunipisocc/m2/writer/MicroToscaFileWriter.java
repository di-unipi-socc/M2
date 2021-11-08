package com.diunipisocc.m2.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.diunipisocc.m2.graph.Edge;
import com.diunipisocc.m2.graph.deployment.Deployment;
import com.diunipisocc.m2.graph.kubeservice.KubService;

/**
 * The Class is designed for writing microTOSCA specification output file of the
 * application
 * 
 * @author javad khalili
 *
 */

public class MicroToscaFileWriter {

	/**
	 * Checks if any service is defined for a pods controller
	 * 
	 * @param deployment  The pod controller
	 * @param kubServices List Kubernetes Services
	 * @param edges       List of edges of graph
	 * @return True there is a service for the pods controller, false otherwise.
	 */
	private boolean hasService(Deployment deployment, List<KubService> kubServices, List<Edge> edges) {

		String app = deployment.getSpec().getSelector().getMatchLabels().getApp();
		if (app != null) {
			for (KubService kubService : kubServices) {
				if (kubService.getSpec().getSelector().getApp().equals(app)) {
					if (isPresentInGraph(kubService, edges)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Search for service defined for a pod controller
	 * 
	 * @param deployment   The pods controller
	 * @param kubeServices List of services
	 * @param edges        List of edges
	 * @return
	 */
	private KubService findRelatedService(Deployment deployment, List<KubService> kubeServices, List<Edge> edges) {
		String selector = deployment.getSpec().getSelector().getMatchLabels().getApp();
		KubService service = null;
		if (selector != null) {
			for (KubService kubeService : kubeServices) {
				if (kubeService.getSpec().getSelector().getApp().equals(selector)) {
					if (isPresentInGraph(kubeService, edges)) {
						service = kubeService;
						return service;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Searches for the pod controller for a service
	 * 
	 * @param service     Service candidate
	 * @param deployments List of pod controllers
	 * @return Object of related pod controller
	 */
	private Deployment findRelatedDeployment(KubService service, List<Deployment> deployments) {
		String app = service.getSpec().getSelector().getApp();
		if (app != null) {
			for (Deployment deployment : deployments) {
				String depApp = deployment.getSpec().getSelector().getMatchLabels().getApp();
				if (app.equals(depApp)) {
					return deployment;
				}
			}
		}
		return null;
	}

	/**
	 * Searches for list of pod controllers for a service
	 * 
	 * @param kubService  Service candidate
	 * @param deployments List of pod controllers
	 * @return
	 */
	private List<Deployment> findRelatedDeployments(KubService kubService, List<Deployment> deployments) {
		String app = kubService.getSpec().getSelector().getApp();
		List<Deployment> relatedDeployments = new ArrayList<>();
		if (app != null) {
			for (Deployment deployment : deployments) {
				String depApp = deployment.getSpec().getSelector().getMatchLabels().getApp();
				if (app.equals(depApp)) {
					relatedDeployments.add(deployment);
				}
			}
		}
		if (relatedDeployments.isEmpty()) {
			System.err.println(
					"Miner could not find any Deployment file for '" + kubService.getMetadata().getName() + "' !");
			System.exit(0);
		}
		return relatedDeployments;
	}

	/**
	 * Searches for interactions related to a service in graph
	 * 
	 * @param sourceName Name of candidate service
	 * @param edges      List of edges of graph
	 * @return List of edges which the service appear as the source
	 */

	private List<Edge> findInteractions(String sourceName, List<Edge> edges) {
		List<Edge> interactions = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getSourceName().equals(sourceName)) {
				interactions.add(edge);
			}
		}

		return interactions;
	}

	/**
	 * Checks whether a service is present in graph
	 * 
	 * @param kubService Service candidate
	 * @param edges      List of edges present in graph
	 * @return True if service is present in graph, false otherwise
	 */
	private boolean isPresentInGraph(KubService kubService, List<Edge> edges) {
		String name = kubService.getMetadata().getName();

		for (Edge edge : edges) {
			if (edge.getSourceName().equals(name) || edge.getTargetName().equals(name)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks whether a pod controller appear as source of any edge
	 * 
	 * @param deployment Pod controller candidate
	 * @param edges      List of edges present in graph
	 * @return True if the pod controller as a source of any edge, false otherwise
	 */
	private boolean hasEdge(Deployment deployment, List<Edge> edges) {
		String name = deployment.getMetadata().getName();
		for (Edge edge : edges) {
			if (edge.getSourceName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Writes the microTOSCA specification file of the application
	 * 
	 * @param deployments   List of pod controllers
	 * @param kubServices   List of services
	 * @param edges         List edges present in graph
	 * @param appName       Name of the application
	 * @param outputAddress Destination directory where output file get stored
	 * @throws IOException IOException
	 */
	public void writeFile(List<Deployment> deployments, List<KubService> kubServices, List<Edge> edges, String appName,
			String outputAddress) throws IOException {

		System.out.println("Writing microTOSCA output file ...");
		File microTOSCA = new File(outputAddress + "/" + "microTOSCA.yml");
		FileOutputStream fos = new FileOutputStream(microTOSCA);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("tosca_definitions_version: micro_tosca_yaml_1.2");
		bw.newLine();

		bw.write("description: Automatically generated microTOSCA specification of " + appName);
		bw.newLine();
		bw.write("imports:");
		bw.newLine();
		bw.write("- micro: https://di-unipi-socc.github.io/microTOSCA/microTOSCA.yaml");
		bw.newLine();
		bw.write("topology_template:");
		bw.newLine();
		bw.write("  node_templates:");
//===============================Writing MessageRuters=============================================

		for (KubService kubService : kubServices) {
			bw.newLine();
			// if the service is present in the dependency graph
			if (isPresentInGraph(kubService, edges)) {
				bw.write("    " + kubService.getMetadata().getName() + ".svc" + ":");
				bw.newLine();

				bw.write("      type: micro.nodes." + kubService.getType());
				bw.newLine();
				bw.write("      requirements:");

				// Finding list of pod controllers two created interactions with related service
				List<Deployment> relatedDeployments = findRelatedDeployments(kubService, deployments);

				for (Deployment relatedDeployment : relatedDeployments) {

					bw.newLine();
					bw.write("      - interaction:");
					bw.newLine();
					bw.write("         node: " + relatedDeployment.getMetadata().getName());
					bw.newLine();
				}

			}

		}
//=============================================Writing Services, Datastores, MessageBrokers==============================================

		for (Deployment deployment : deployments) {

			if (hasService(deployment, kubServices, edges)) {

				if (deployment.getIsSink()) {

					bw.newLine();
					bw.write("    " + deployment.getMetadata().getName() + ":");
					bw.newLine();

					bw.write("      type: micro.nodes." + deployment.getType());
					bw.newLine();
					continue;
				}
				// find that service
				KubService relatedKubService = findRelatedService(deployment, kubServices, edges);
				bw.newLine();
				bw.write("    " + deployment.getMetadata().getName() + ":");
				bw.newLine();

				bw.write("      type: micro.nodes." + deployment.getType());
				bw.newLine();
				bw.write("      requirements:");

				// find the edges with the source name as service name
				List<Edge> interactions = findInteractions(relatedKubService.getMetadata().getName(), edges);
				for (Edge edge : interactions) {
					bw.newLine();
					bw.write("      - interaction:");
					bw.newLine();
					bw.write("         node: " + edge.getTargetName() + ".svc");
					bw.newLine();
					bw.write("         relationship: " + edge.getT() + edge.getC() + edge.getD());
				}

			} else if (hasEdge(deployment, edges)) {
				bw.newLine();
				bw.write("    " + deployment.getMetadata().getName() + ":");
				bw.newLine();

				bw.write("      type: micro.nodes." + deployment.getType());
				bw.newLine();
				bw.write("      requirements:");
				List<Edge> interactions = findInteractions(deployment.getMetadata().getName(), edges);
				for (Edge edge : interactions) {
					bw.newLine();
					bw.write("      - interaction:");
					bw.newLine();
					bw.write("         node: " + edge.getTargetName() + ".svc");
					bw.newLine();
					bw.write("         relationship: " + edge.getT() + edge.getC() + edge.getD());

				}
			}
		}

//===============================================Writing Edge group====================================

		bw.newLine();
		bw.write("  groups:");
		bw.newLine();
		bw.write("    edge:");
		bw.newLine();
		bw.write("      type: micro.groups.Edge");
		bw.newLine();
		bw.write("      members: ["); // printing the list of message router

		boolean first = true;
		boolean found = false;
		if (!found) {

			for (KubService service : kubServices) {
				if (service.getIsEdge()) {
					if (isPresentInGraph(service, edges)) {
						if (first) {
							first = false;
							found = true;
							bw.write(service.getMetadata().getName() + ".svc");
							continue;
						}
						bw.write(", " + service.getMetadata().getName() + ".svc");
					} else {
						Deployment relatedDeployment = findRelatedDeployment(service, deployments);
						KubService kubService = findRelatedService(relatedDeployment, kubServices, edges);
						if (kubService != null) {
							if (first) {
								first = false;
								found = true;
								bw.write(kubService.getMetadata().getName() + ".svc");
								continue;
							}
							bw.write(", " + kubService.getMetadata().getName() + ".svc");
						}

					}
				}
			}
		}
		// in case of not finding members of Edge group, trying to find them using edges
		// where the source is "istio-ingressgateway"
		if (!found) {

			for (Edge edge : edges) {
				if (edge.getSourceName().equals("istio-ingressgateway")) {

					if (first) {
						bw.write(edge.getTargetName() + ".svc");
						first = false;
						continue;
					}
					bw.write(", " + edge.getTargetName() + ".svc");
				}
			}

		}
		bw.write("]");
//=======================================Relationship Templates========================================
		bw.newLine();
		bw.write("  relationship_templates:");
		bw.newLine();
		bw.write("    t:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        timeout: true");
		bw.newLine();
		// ---------------
		bw.write("    c:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        circuit_breaker: true");
		bw.newLine();
		// ---------------
		bw.write("    d:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        dynamic_discovery: true");
		bw.newLine();
		// ---------------
		bw.write("    tc:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        timeout: true");
		bw.newLine();
		bw.write("        circuit_breaker: true");
		bw.newLine();
		// ---------------
		bw.write("    td:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        timeout: true");
		bw.newLine();
		bw.write("        dynamic_discovery: true");
		bw.newLine();
		// ---------------
		bw.write("    cd:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        circuit_breaker: true");
		bw.newLine();
		bw.write("        dynamic_discovery: true");
		bw.newLine();
		// ---------------
		bw.write("    tcd:");
		bw.newLine();
		bw.write("      type: micro.relationships.InteractsWith");
		bw.newLine();
		bw.write("      properties:");
		bw.newLine();
		bw.write("        timeout: true");
		bw.newLine();
		bw.write("        circuit_breaker: true");
		bw.newLine();
		bw.write("        dynamic_discovery: true");
		bw.newLine();
		// ---------------

		System.out.println("Done!");
		bw.close();

	}

}
