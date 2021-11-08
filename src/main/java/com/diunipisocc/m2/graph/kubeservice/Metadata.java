package com.diunipisocc.m2.graph.kubeservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
	
	private String name;
	
	
	// COULD BE REMOVED
	private Label label;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Metadata [name=" + name + ", label=" + label + "]";
	}
	
}
