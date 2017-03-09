/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.internal.configuration;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;

/**
 * Provides set of all configuration classes obtained from given list of requirements.
 * Creates XML reader and processes xml file that contains test run configurations and groups them into 
 * list of all configurations represented by {@link RequirementConfigurationObject}.
 * @author Ondrej Dockal
 *
 */
public class TestRunConfigurationReader {
	
	private XMLReader reader;
	
	// all requirements configuration classes, used for reading of xml config file
	private Set<Class<?>> allRequirementConfigurations = new HashSet<>();
	
	// all requirements that test classes use, used for cleaning up of requirement configuration that will not be used
	private List<Annotation> allRequirements;
	
	private List<RequirementConfigurationObject> configurationList;
	
	/**
	 * Class instance that sets up requirement configuration classes and list of {@link RequirementConfigurationObject}.
	 * @param file the configuration xml file
	 * @param requirements list of all requirements
	 */
	public TestRunConfigurationReader(File file, List<Annotation> requirements) {
		this.reader = new XMLReader(file);
		this.allRequirements = requirements;
		this.configurationList = new ArrayList<>();
		
		setRequirementConfigurationClasses();
		// uses loaded req. configurations to read configurations from config.xml file
		readConfigurationXML();
		// deletes configuration that will not be used by any test class requirements
		cleanUpRequirementConfiguration();
		// mix up all possible configuration
	}
	
	/**
	 * Loads requirement configuration classes.
	 */
	private void setRequirementConfigurationClasses() {
		for (Annotation annotation : this.allRequirements) {
			this.allRequirementConfigurations.add(annotation.annotationType());
		}
	}
	
	/**
	 * Returns configuration list.
	 * @return list of requirement configuration objects
	 */
	public List<RequirementConfigurationObject> getConfigurationList() {
		return this.configurationList;
	}
	
	/**
	 * Clean up unused configurations according to its requirements.
	 */
	private void cleanUpRequirementConfiguration() {
		for (RequirementConfigurationObject config : this.configurationList) {
			if (config.getRequirementConfiguration() == CustomConfiguration.class) {
				config.checkRequirementConfiguration(this.allRequirements);
			}
		}
	}
	
	/**
	 * Iterate over all requirement configuration classes
	 * used with test classes and add new {@link RequirementConfigurationObject} object
	 * into configuration list 
	 */
	private void readConfigurationXML() {
		for (Class<?> clazz : this.allRequirementConfigurations) {
			this.configurationList.add(new RequirementConfigurationObject(clazz, reader));
		}
	}
	
}
