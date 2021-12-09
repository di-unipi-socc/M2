package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Labels {
	
	private String app;
	
	public Labels() {
		super();
	}
	
	public Labels(String app, String version) {
		//super();
		this.app = app;
		//this.version = version;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	@Override
	public String toString() {
		return "Labels [app=" + app +  "]";
	}
	

}
