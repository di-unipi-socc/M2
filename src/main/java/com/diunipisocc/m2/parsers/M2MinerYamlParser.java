package com.diunipisocc.m2.parsers;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This interface is designed for classes related to parsing Kubernetes manifest yaml files of the application
 * @author javad khalili
 *
 * @param <T>
 */
public interface M2MinerYamlParser<T> {
	public List<T> parse(List<File> files) throws IOException;
}
