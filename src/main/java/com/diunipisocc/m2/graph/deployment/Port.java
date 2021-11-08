package com.diunipisocc.m2.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Port {
	private String name = null;
	private int containerPort;

	public Port() {
		super();
	}

	public Port(int containerPort) {
		// super();
		this.containerPort = containerPort;
	}

	public int getContainerPort() {
		return containerPort;
	}

	public void setContainerPort(int containerPort) {
		this.containerPort = containerPort;
	}

	public Port(String name, int containerPort) {
		// super();
		this.name = name;
		this.containerPort = containerPort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Port [name=" + name + ", containerPort=" + containerPort + "]";
	}

}
