/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.internal.configuration.configurator;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Reads property based configuration from XML file and sets the found properties into the requirement.  
 * 
 * @author Lucia Jelinkova
 * @author Ondrej Dockal
 *
 */
public class PropertyBasedConfigurator implements RequirementConfigurator {

	private static final Logger log = Logger.getLogger(PropertyBasedConfigurator.class);
	
	private List<Object> configurations;
	
	private ConfigurationSetter setter;
	
	/**
	 * Instantiates a property based configurator.
	 *
	 * @param configurations the configurations
	 * @param setter the setter
	 */
	public PropertyBasedConfigurator(List<Object> configurations, ConfigurationSetter setter) {
		this.configurations = configurations;
		this.setter = setter;
	}

	/**
	* Iterate over all configurations and tries to cast the configuration object to expected configuration class
	* that must implement {@link PropertyBasedConfiguration}.
	* Each configuration is set via {@link ConfigurationSetter}, that assures that configuration is set to proper requirement.
	* Class cast exception is caught and iteration continues, if any configuration fits, 
	* {@link RedDeerConfigurationException} is thrown.
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		
		if (!(requirement instanceof PropertyConfiguration)){
			throw new IllegalArgumentException("The requirement does not implement " + PropertyConfiguration.class);
		}

		boolean configurationSet = false;
		for (Object configuration : this.configurations) {
			try {
				log.debug("Setting property based configuration to requirement " + requirement.getClass());
				PropertyBasedConfiguration config = (PropertyBasedConfiguration) configuration;
				setter.set(requirement, config);
				log.debug("Configuration successfully set");
				configurationSet = true;
				break;
			} catch (RedDeerConfigurationException e) {
				log.error("This property based configuration (" + configuration + ") cannot be set to " + requirement.toString());
			} catch (ClassCastException e) {
				log.error("This configuration (" + configuration + ") cannot be set to " + requirement.toString() + " " + e);
			}
		}
		if (!configurationSet) {
			throw new RedDeerConfigurationException("None of the given configurations "
					+ "could have ben set as configuration of the requirement " + requirement.getClass().getName());
		}
	}
}
