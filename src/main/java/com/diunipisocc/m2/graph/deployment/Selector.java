package com.diunipisocc.m2.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Selector {
	private MatchLabels matchLabels;

	public Selector() {
		super();
	}

	public Selector(MatchLabels matchLabels) {
		// super();
		this.matchLabels = matchLabels;
	}

	public MatchLabels getMatchLabels() {
		return matchLabels;
	}

	public void setMatchLabels(MatchLabels matchLabels) {
		this.matchLabels = matchLabels;
	}

	@Override
	public String toString() {
		return "Selector [matchLables=" + matchLabels + "]";
	}

}
