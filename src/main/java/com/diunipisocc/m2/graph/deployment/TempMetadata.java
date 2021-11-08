package com.diunipisocc.m2.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TempMetadata {

	private Labels labels;

	public TempMetadata() {
		super();
	}

	public TempMetadata(Labels labels) {
		// super();
		this.labels = labels;
	}

	public Labels getLabels() {
		return labels;
	}

	public void setLabels(Labels labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		return "TempMetadata [label=" + labels + "]";
	}

}
