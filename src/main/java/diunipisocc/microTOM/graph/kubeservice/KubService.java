package diunipisocc.microTOM.graph.kubeservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * This class designed for parsing Kubernetes manifest files where Services are defined
 * @author javad khalili
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KubService {
	private String apiVersion;
	private String kind;
	private Metadata metadata;
	private Spec spec;
	private String type = "MessageRouter";
	private boolean isEdge;

	/**
	 * No argument constructor for Kubernetes Service object
	 */
	public KubService() {
	}

	/**
	 * Parameterized constructor of Kubernetes Service object
	 * @param apiVersion
	 * @param kind
	 * @param metadata
	 * @param spec
	 */
	public KubService(String apiVersion, String kind, Metadata metadata, Spec spec) {
		this.apiVersion = apiVersion;
		this.kind = kind;
		this.metadata = metadata;
		this.spec = spec;
	}

	/**
	 * Getter method for apiVerstion of a service
	 * @return
	 */
	public String getApiVersion() {
		return apiVersion;
	}

	/**
	 * Setter method for apiVersion of a service
	 */
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * Getter method for a kind of a service
	 * @return
	 */
	public String getKind() {
		return kind;
	}

	/*
	 * Setter method for kind of a service
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Getter method for Metadata of a service
	 * @return
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * Setter method for Metadata of a service
	 * @param metadata
	 */
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * Getter method for Spec of a service
	 * @return
	 */
	public Spec getSpec() {
		return spec;
	}

	/**
	 * Setter method for Spec of a service
	 * @param spec
	 */
	public void setSpec(Spec spec) {
		this.spec = spec;
	}

	/**
	 * Setter method for type of a serivce
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter method for type of a service
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Setter method for isEdge of a service
	 * @param isEdge
	 */
	public void setIsEdge(Boolean isEdge) {
		this.isEdge = isEdge;
	}
	
	/**
	 * Getter method for isEdge of a service
	 * @return
	 */
	public Boolean getIsEdge() {
		return this.isEdge;
	}

	@Override
	public String toString() {
		return "KubService [apiVersion=" + apiVersion + ", kind=" + kind + ", metadata=" + metadata + ", spec=" + spec
				+", isEdge="+isEdge + "]";
	}

}
