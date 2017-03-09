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
package org.jboss.reddeer.junit.test.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfigurationImpl;
import org.jboss.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.NullConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.PropertyBasedConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.RequirementConfigurator;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;


public class RequirementsConfigurationImplTest {

	private RequirementsConfigurationImpl config = new RequirementsConfigurationImpl(new ArrayList<Object>());

	@Test
	public void propertyBased_caching(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(PropertyConfiguration.class));

		RequirementConfigurator configurator1 = config.getConfigurator(requirement);
		RequirementConfigurator configurator2 = config.getConfigurator(requirement);

		assertSame(configurator1, configurator2);
		assertThat(configurator1, instanceOf(PropertyBasedConfigurator.class));
	}

	@Test
	public void custom_caching(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(CustomConfiguration.class));

		RequirementConfigurator configurator1 = config.getConfigurator(requirement);
		RequirementConfigurator configurator2 = config.getConfigurator(requirement);

		assertSame(configurator1, configurator2);
		assertThat(configurator1, instanceOf(CustomConfigurator.class));
	}

	@Test
	public void void_caching(){
		Requirement<?> requirement = mock(Requirement.class);

		RequirementConfigurator configurator1 = config.getConfigurator(requirement);
		RequirementConfigurator configurator2 = config.getConfigurator(requirement);

		assertSame(configurator1, configurator2);
		assertThat(configurator1, instanceOf(NullConfigurator.class));
	}
}
