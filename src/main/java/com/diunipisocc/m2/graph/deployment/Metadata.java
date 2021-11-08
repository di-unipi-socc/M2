package com.diunipisocc.m2.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
	private String name;
	private String namespace;
	
	public Metadata() {
		super();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Metadata(String name, String nameSpace) {
		//super();
		this.name = name;
		this.namespace = nameSpace;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String nameSpace) {
		this.namespace = nameSpace;
	}

	@Override
	public String toString() {
		return "Metadata [name=" + name + ", nameSpace=" + namespace + "]";
	}
	

	
	
	
}
