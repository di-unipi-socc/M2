package diunipisocc.microTOM.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import diunipisocc.microTOM.graph.Edge;
import diunipisocc.microTOM.graph.JsService;
import diunipisocc.microTOM.graph.deployment.Deployment;
import diunipisocc.microTOM.graph.kubeservice.KubService;

public class ServiceYamlParser implements M2MinerYamlParser<KubService> {

	@Override
	public List<KubService> parse(List<File> files) throws IOException {
		List<File> validFiles = new ArrayList<>();

		// Filter only the yaml/yml files in case if there are other files are present
		// in the folder
		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
					validFiles.add(file);
				}
			}
		}

		/*
		 * Iterate throgh all the files present in the folder, and read each file line
		 * by line if the line 'Kind: Service' is found the create a Deployment object
		 * otherwise pass to the next file
		 * 
		 */

		System.out.println("Parsing Kubernetes Services files...");
		List<KubService> kubServices = new ArrayList<>(); // create it after all checking passed!
		for (File file : validFiles) {
			BufferedReader br = null; // Reading one file (used for destination rule and virtual service object
			// creation)
			try {
				br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				while (line != null) {
					// System.out.println(line);
					if (line.equals("kind: Service")) {
						KubService kubService = createKubServiceObject(file);
						kubServices.add(kubService);
						// br.close();
						break;

					}
					line = br.readLine();
				} // while
					// br.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				br.close();
			}

		} // End of for loop

		// Display the number of Service objects
		// If the size of Service list is zero then the executions should terminate
		// abruptly
		if (kubServices.size() == 0) {
			System.err.println("It seems that there is no kubernetes Service file in the folder !!! ");
			System.exit(0);
		}

		setEdgeMembers(kubServices);
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("List of Kuberenetes service objects!");
		System.out.println("-------------------------------------------------------------------------------");
		// System.out.println(kubServices);
		kubServices.forEach(System.out::println);
		System.out.println();
		
		return kubServices;

	}

	/**
	 * Creates a new object of Kuberentes Service
	 * @param file
	 * @return
	 */
	private KubService createKubServiceObject(File file) {


		File yamlFile = file.getAbsoluteFile();

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		KubService yamlService = null;
		try {
			yamlService = mapper.readValue(yamlFile, KubService.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(deployment);
		return yamlService;
	}
	
	/**
	 * Identifies corresponding sink node components based on service mesh nodes
	 * @param kubServices
	 * @param deployments
	 * @param jsServices
	 * @param edges
	 */
	public void identifySinkNodes(List<KubService> kubServices, List<Deployment> deployments,
			List<JsService> jsServices, List<Edge> edges) {

		for (KubService kubService : kubServices) {

			if (isPresentInGraph(kubService, edges)) {
				for (JsService jsService : jsServices) {
					// compare the metadata.name of Kubeservice and jsservice name
					if (kubService.getMetadata().getName().equals(jsService.getServiceName())) {
						findRelatedDeployment(kubService, deployments).setIsSink(true);
					}
				}
			}
		}
	}
	
	/**
	 * Checks for presence of a service in service mesh graph
	 * @param kubService
	 * @param edges
	 * @return
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
	 * Finds related pod controller of service
	 * @param service
	 * @param deployments
	 * @return
	 */
	private Deployment findRelatedDeployment(KubService service, List<Deployment> deployments) {
		String app = service.getSpec().getSelector().getApp();
		if (hasDeployment(service, deployments)) {
			for (Deployment deployment : deployments) {
				String depApp = deployment.getSpec().getSelector().getMatchLabels().getApp();
				if (app.equals(depApp)) {
					return deployment;
				}
			}
		}
		System.err.println(
				"Miner could not find any Deployment for '" + service.getMetadata().getName() + "' service!!!");
		System.exit(0);
		return null;
	}
	/**
	 * Checks for existing of pod controller for a service
	 * @param service
	 * @param deployments
	 * @return
	 */
	private boolean hasDeployment(KubService service, List<Deployment> deployments) {
		String app = service.getSpec().getSelector().getApp();
		for (Deployment deployment : deployments) {
			String depApp = deployment.getSpec().getSelector().getMatchLabels().getApp();
			if (app.equals(depApp)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Identifies the candidates of Edge group
	 * @param services
	 */
	private void setEdgeMembers(List<KubService> services) {
		for(KubService service : services) {
			if(service.getSpec().getType() != null) {
				if(service.getSpec().getType().equals("NodePort") || service.getSpec().getType().equals("LoadBalancer")) {
					service.setIsEdge(true);
				}
			}
		}
	}
	
}
