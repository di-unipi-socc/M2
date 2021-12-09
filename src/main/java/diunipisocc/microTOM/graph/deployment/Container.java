package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Container {
	private String name;
	private String image;
	private Port[] ports;
	
	
	public Container() {
		super();
	}
	
	public Container(String name, String image, Port[] ports) {
		//super();
		this.name = name;
		this.image = image;
		this.ports = ports;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Port[] getPorts() {
		return ports;
	}
	public void setPorts(Port[] ports) {
		this.ports = ports;
	}
	@Override
	public String toString() {
		return "Containers [name=" + name + ", image=" + image + ", ports=" + ports + "]";
	}
}
