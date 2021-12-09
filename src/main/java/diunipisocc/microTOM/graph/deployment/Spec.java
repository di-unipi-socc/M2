package diunipisocc.microTOM.graph.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spec {
	private Selector selector;
	private int replicas;
	private Template template;

	public Spec() {
		super();
	}

	public Spec(Selector selector, int replicas, Template template) {
		// super();
		this.selector = selector;
		this.replicas = replicas;
		this.template = template;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replica) {
		this.replicas = replica;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "Spec [selector=" + selector + ", replica=" + replicas + ", template=" + template + "]";
	}

}
