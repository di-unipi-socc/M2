package diunipisocc.microTOM.graph.kubeservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {
	private String app;

	public Label(String app) {
		super();
		this.app = app;
	}

	public Label() {
		super();
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}
	
}
