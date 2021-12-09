package diunipisocc.microTOM.refiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import diunipisocc.microTOM.graph.deployment.Deployment;

/**
 * This class is designed to perform node refinement interactively by implementing "refine()" method of NodeRefiner interface
 * @author javad khalili
 *
 */
public class InteractiveRefiner implements NodeRefiner {
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
				} else {
					String name = deployment.getMetadata().getName();

					boolean ok = false;
					while (!ok) {
						System.out.println();
						System.out.println("The minner could not infer type of '" + name + "'!");
						System.out.println(
								"Please choose one of the following options by selecting the corresponding digit and press Enter.");
						System.out.println("  1 . Datastore ");
						System.out.println("  2 . MessageBroker");
						System.out.println("  3 . None of the aboves (Service)");
						Scanner scanner = new Scanner(System.in);
						String input = scanner.nextLine();
						if (isNumber(input)) {

							switch (Integer.parseInt(input)) {

							case 1:
								deployment.setType("Datastore");
								System.out.println("'" + name + "' is set to be a Datastore.");
								System.out.println();
								ok = true;
								break;

							case 2:
								deployment.setType("MessageBroker");
								System.out.println("'" + name + "' is set to be a MessageBroker.");
								System.out.println();
								ok = true;
								break;

							case 3:
								// it's a service by default.
								System.out.println("'" + name + "' is set to be a Service.");
								System.out.println();
								ok = true;
								break;

							default:
								// The input may not be correspond to any option given.
								System.out.println(" The input is Invalid! Try again Please...");
								break;
							}

						} else {

							System.out.println("Please Enter an Input! / The input must be a digit!");
						}
					}// End of while
					
				}

			}
		}


		
	}
	
	
	/**
	 * List of MessageBroker Docker image names
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
	 * List of Database Docker image names
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

	private boolean isDataStore(String image, List<String> dataStores) {

		for (String dataStore : dataStores) {
			if (image.contains(dataStore)) {
				return true;
			}
		}

		return false;
	}

	private boolean isMessageBroker(String image, List<String> messageBrokers) {

		for (String messageBroker : messageBrokers) {
			if (image.contains(messageBroker)) {
				return true;
			}
		}

		return false;
	}

	private boolean isNumber(String input) {

		if (input.matches("[0-9]+")) {
			return true;

		} else
			return false;

	}

}
