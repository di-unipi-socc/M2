package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchLabels {
	private String app;
	
	public MatchLabels() {
		super();
	}
	
	public MatchLabels(String app, String version) {
		//super();
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
		return "MatchLabels [app=" + app + "]";
	}
	

}
