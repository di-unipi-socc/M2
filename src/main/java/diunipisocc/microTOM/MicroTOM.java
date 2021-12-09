package diunipisocc.microTOM;

import java.io.File;
import java.io.IOException;
import java.util.List;

import diunipisocc.microTOM.graph.Edge;
import diunipisocc.microTOM.graph.JsService;
import diunipisocc.microTOM.graph.deployment.*;
import diunipisocc.microTOM.graph.kubeservice.KubService;
import diunipisocc.microTOM.parsers.DeploymentYamlParser;
import diunipisocc.microTOM.parsers.EdgeParser;
import diunipisocc.microTOM.parsers.M2MinerJsonParser;
import diunipisocc.microTOM.parsers.M2MinerYamlParser;
import diunipisocc.microTOM.parsers.NodeParser;
import diunipisocc.microTOM.parsers.ServiceYamlParser;
import diunipisocc.microTOM.refiner.AutomaticRefiner;
import diunipisocc.microTOM.refiner.InteractionRefiner;
import diunipisocc.microTOM.refiner.InteractiveRefiner;
import diunipisocc.microTOM.refiner.NodeRefiner;
import diunipisocc.microTOM.writer.MicroToscaFileWriter;

public class MicroTOM {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("There is no enough Arguments (Miner Requirements)!!!");
			System.exit(0);
		}
		// TO RUN THE MINER
		// >> java Main <directory> [-i]

		InputValidator validator = new InputValidator();
		validator.setMode(args);
		List<File> fileList = validator.uploadFiles(args[0]);
		File jsonFile = validator.findJSON(fileList);

		M2MinerJsonParser<JsService> jsonNodeParser = new NodeParser();
		M2MinerJsonParser<Edge> jsonEdgeParser = new EdgeParser();
		NodeRefiner refiner = null;

		// Parsing

		// 1) Creating Deployment objects
		M2MinerYamlParser<Deployment> deploymentParser = new DeploymentYamlParser();

		List<Deployment> deployments = deploymentParser.parse(fileList);

		// 2) Creating KubService objects
		M2MinerYamlParser<KubService> serviceParser = new ServiceYamlParser();

		List<KubService> kubServices = serviceParser.parse(fileList);


		// 3) Extracting JSON service

		List<JsService> jsServices = jsonNodeParser.parse(jsonFile);

		// 4) Extracting Edges from JSON file

		List<Edge> edges = jsonEdgeParser.parse(jsonFile);

		// REFINEMENT

		InteractionRefiner edgeRefiner = new InteractionRefiner();

		// 5) Update the edges by naming the source and destination of each edge

		edgeRefiner.edgeNameSetter(edges, jsServices);

		// 6) Inferring the type ofj interaction for each edge

		edgeRefiner.labelEdges(jsServices, edges, fileList);

		// 7) creating a list of sink nodes

		List<JsService> jsonSinks = ((NodeParser) jsonNodeParser).findSinks(jsServices, edges);

		// identifying corresponding Kubernetes deployments
		((ServiceYamlParser) serviceParser).identifySinkNodes(kubServices, deployments, jsonSinks, edges);

		// 8) Refining services

		if (validator.getMode()) {// if '-i' appears in command line arguments
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("Refining Nodes Interactively! The Miner Might Need Some Help to Identify Types!");
			System.out.println("-------------------------------------------------------------------------------");
			refiner = new InteractiveRefiner();
			refiner.refine(deployments);
		} else {
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println("Refining Nodes Automatically!");
			System.out.println("-------------------------------------------------------------------------------");
			refiner = new AutomaticRefiner();
			refiner.refine(deployments);
		}

		// 9) Writing output microTOSCA file
		new MicroToscaFileWriter().writeFile(deployments, kubServices, edges, new File(args[0]).getName(), args[0]);

	}
}