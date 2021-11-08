package com.diunipisocc.m2.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.diunipisocc.m2.graph.deployment.Deployment;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
/**
 * This class is designed to parse Kubernetes manifest yaml file where the pod controllers are defined by implementing "parse()" method of M2MinerYamlParser interface
 * @author javad khalili
 *
 */
public class DeploymentYamlParser implements M2MinerYamlParser<Deployment> {

	@Override
	public List<Deployment> parse(List<File> files) throws IOException {
		List<File> validFiles = new ArrayList<>();

		// Filter only the yaml/yml files in case if there are other files are present
		// in the folder
		// Checking whether the folder is empty or not

		for (File file : files) {
			if (file.isFile()) {
				// System.out.println(file.getName());
				if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
					validFiles.add(file);
				}
			}
		}

		/*
		 * Iterate through all the files present in the folder, and read each file line
		 * by line if the line 'Kind: Deployment' is found the create a Deployment
		 * object otherwise pass to the next file
		 * 
		 */

		if (validFiles.isEmpty()) {

			System.err.print("There is no Valid .yaml/.yml Files in This Folder !!!");
			System.exit(0);
		}

		System.out.println("Parsing Kubernetes Deployment files...");
		List<Deployment> deployments = new ArrayList<>(); // create it after all checking passed!
		for (File file : validFiles) {
			BufferedReader br = null; // Reading one file (used for destination rule and virtual service object
			// creation)
			try {
				br = new BufferedReader(new FileReader(file));
				String line = br.readLine();
				while (line != null) {
					if (line.equals("kind: Deployment") || line.equals("kind: StatefulSet")) {
						Deployment deployment = createDeploymentObject(file);
						deployments.add(deployment);
						break;

					}
					line = br.readLine();
				} // while

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				br.close();
			}

		} // End of for loop

		// Display the number of Deployment object
		// If the size of Deployment list is zero then the execution should terminate
		// abruptly
		if (deployments.size() == 0) {
			System.err.println("It seems there is no kubernetes deployments file in the folder !!! ");
			System.exit(0);
		}

		// System.out.println(deployments);
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("List of Kuberenetes pod controller objects!");
		System.out.println("-------------------------------------------------------------------------------");
		deployments.forEach(System.out::println);
		System.out.println();
		return deployments;

	}

	private Deployment createDeploymentObject(File file) {

		File yamlFile = file.getAbsoluteFile();

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Deployment deployment = null;
		try {
			deployment = mapper.readValue(yamlFile, Deployment.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deployment;
	}

}
