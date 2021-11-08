package com.diunipisocc.m2.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Deployment class is designed for parsing Kubernetes manifest files of the application where pod controllers are defined.
 * @author javad khalili
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deployment {

	private String apiVersion;
	private String kind;
	private Metadata metadata;
	private Spec spec;
	private String type = "Service";
	private boolean isSink;

	/**
	 * Parameterized constructor for Deployment class
	 * @param apiVersion apiVersion of controller definition
	 * @param kind Kind of controller definition
	 * @param metadata metadata related to controller
	 * @param spec specifications made for the controller
	 */
	public Deployment(String apiVersion, String kind, Metadata metadata, Spec spec) {
		// super();
		this.apiVersion = apiVersion;
		this.kind = kind;
		this.metadata = metadata;
		this.spec = spec;
	}

	public Deployment() {
		super();
	}

	/**
	 * Getter method for apiVersion of the pod controller
	 * @return apiVersion of the pod controller
	 */
	public String getApiVersion() {
		return apiVersion;
	}

	/**
	 * Setter method for apiVersion of pod controller
	 * @param apiVersion
	 */
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * Getter method for Metadata of pod controller
	 * @return Metadata of pod controller
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * Setter method for Metadata of pod controller
	 * @param metadata
	 */
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * Getter method for Spec of pod controller
	 * @return Spec of pod controller
	 */
	public Spec getSpec() {
		return spec;
	}

	/**
	 * Setter method for Spec of pod controller
	 * @param spec
	 */
	public void setSpec(Spec spec) {
		this.spec = spec;
	}

	/**
	 * Setter method for type of pod controller
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter method for type of pod controller
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Setter method for isSink of pod controller
	 * @param isSink
	 */
	public void setIsSink(boolean isSink) {
		this.isSink = isSink;
	}

	/**
	 * Getter method for isSink of pod controller
	 * @return
	 */
	public boolean getIsSink() {
		return this.isSink;
	}

	@Override
	public String toString() {
		return "Deployment [apiVersion=" + apiVersion + ", kind=" + kind + ", metadata=" + metadata + ", spec=" + spec
				+ ", type=" + type + ", isSink=" + isSink + "]";
	}

}
