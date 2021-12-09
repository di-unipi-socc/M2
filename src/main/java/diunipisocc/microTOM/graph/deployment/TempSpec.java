package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TempSpec {
	private Container[] containers;

	public TempSpec() {
		super();
	}

	public TempSpec(Container[] containers) {
		// super();
		this.containers = containers;
	}

	public Container[] getContainers() {
		return containers;
	}

	public void setContainers(Container[] containers) {
		this.containers = containers;
	}

	@Override
	public String toString() {
		return "TempSpec [containers=" + containers + "]";
	}

}
