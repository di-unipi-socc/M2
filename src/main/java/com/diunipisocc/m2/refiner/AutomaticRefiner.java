package com.diunipisocc.m2.refiner;

import java.util.ArrayList;
import java.util.List;

import com.diunipisocc.m2.graph.deployment.Deployment;

/**
 * This class in designed to perform node refinement automatically by implementing "parse()" method of NodeRefiner interface
 * @author javad khalili
 *
 */
public class AutomaticRefiner implements NodeRefiner {
	
	private List<String> dataStores = getDataStores();
	private List<String> messageBrokers = getMessageBrokeres();
	
	@Override
	public void refine(List<Deployment> deployments) {

		for (Deployment deployment : deployments) {
			if (deployment.getIsSink()) {
				String image = deployment.getSpec().getTemplate().getSpec().getContainers()[0].getImage();
				if (isDataStore(image, dataStores)) {
					deployment.setType("Datastore");
					continue;
				} else if (isMessageBroker(image, messageBrokers)) {
					deployment.setType("MessageBroker");
					continue;
				}

			}
		}
		
	}
	
	/**
	 * Check for implementation of Datastore pattern by a node 
	 * @param image
	 * @param dataStores
	 * @return
	 */
	private boolean isDataStore(String image, List<String> dataStores) {

		for (String dataStore : dataStores) {
			if (image.contains(dataStore)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks for implementation of MessageBroker pattern by a node
	 * @param image
	 * @param messageBrokers
	 * @return
	 */
	private boolean isMessageBroker(String image, List<String> messageBrokers) {

		for (String messageBroker : messageBrokers) {
			if (image.contains(messageBroker)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * List of MessagerBrokers Docker image names
	 * @return
	 */
	private List<String> getMessageBrokeres() {
		messageBrokers = new ArrayList<>();
		messageBrokers.add("rabbitmq");
		messageBrokers.add("kafka");
		messageBrokers.add("activemq");
		messageBrokers.add("mosquitto");
		messageBrokers.add("ibmmq");
		messageBrokers.add("nats");

		return messageBrokers;
	}

	/**
	 * List of Database Docker image name
	 * @return
	 */
	private List<String> getDataStores() {
		this.dataStores = new ArrayList<>();
		dataStores.add("postgres");
		dataStores.add("mongo");
		dataStores.add("maria");
		dataStores.add("mysql");
		dataStores.add("sqlite");
		dataStores.add("redis");
		dataStores.add("cassandra");
		dataStores.add("neo4j");
		dataStores.add("oracle");
		dataStores.add("db2");
		dataStores.add("iris");

		return dataStores;
	}


}
