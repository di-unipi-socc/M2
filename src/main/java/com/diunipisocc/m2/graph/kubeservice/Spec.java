package com.diunipisocc.m2.graph.kubeservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spec {
	private String type;// we need this for node port and load balancer
	private Selector selector;
	public Spec() {
		super();
	}
	public Spec(String type, Selector selector) {
		//super();
		this.type = type;
		this.selector = selector;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Selector getSelector() {
		return selector;
	}
	public void setSelector(Selector selector) {
		this.selector = selector;
	}
	@Override
	public String toString() {
		return "Spec [type=" + type + ", selector=" + selector + "]";
	}
	
}
