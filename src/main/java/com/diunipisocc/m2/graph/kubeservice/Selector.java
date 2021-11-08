package com.diunipisocc.m2.graph.kubeservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Selector {
	private String app;

	public Selector() {
		super();
	}

	public Selector(String app) {
		super();
		this.app = app;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return "Selector [app=" + app + "]";
	}
	
}
