package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
	private TempMetadata metadata;
	private TempSpec spec;

	public Template() {
		super();
	}

	public Template(TempMetadata metadata, TempSpec spec) {
		// super();
		this.metadata = metadata;
		this.spec = spec;
	}

	public TempMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(TempMetadata metadata) {
		this.metadata = metadata;
	}

	public TempSpec getSpec() {
		return spec;
	}

	public void setSpec(TempSpec spec) {
		this.spec = spec;
	}

	@Override
	public String toString() {
		return "Templates [tempMetadata=" + metadata + ", tempSpec=" + spec + "]";
	}

}
