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
package org.jboss.reddeer.junit.test.internal.configuration.configurator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement.CustomJavaAnnotation;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA;
import org.jboss.reddeer.junit.test.internal.requirement.TestRequirementA;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CustomConfiguratorTest {

	private CustomConfigurator configurator;
	private List<Object> configs = new ArrayList<>();
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongArgument() {
		configurator = new CustomConfigurator(configs);
		configurator.configure(mock(Requirement.class));
	}

	@Test
	public void customConfig() {
		ArgumentCaptor<Object> argument = ArgumentCaptor.forClass(Object.class);
		Object configurationObject = mock(TestCustomJavaConfiguration.class);
		configs.add(configurationObject);
		configurator = new CustomConfigurator(configs);
			
		TestCustomJavaRequirement requirement = mock(TestCustomJavaRequirement.class);
		configurator.configure(requirement);
		
		verify(requirement).setConfiguration((TestCustomJavaConfiguration)argument.capture());
		assertEquals(configurationObject, argument.getValue());
	}
	
	@SuppressWarnings({ "rawtypes" })
	//@Test
	public void customConfig_readerArgument() {
		ArgumentCaptor<Class> argument = ArgumentCaptor.forClass(Class.class);
		configs.add(mock(Object.class));
		configs.add(mock(Object.class));
		configurator = new CustomConfigurator(configs);
		
		CustomConfiguration requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));
		when(requirement.getConfigurationClass()).thenReturn(Integer.class);
		configurator.configure((Requirement) requirement);
		
		//verify(reader).getConfiguration(argument.capture());
		assertEquals(Integer.class, argument.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noConfigFound() {
		configurator = new CustomConfigurator(configs);
		
		CustomConfiguration<?> requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));
		configurator.configure((Requirement) requirement);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noConfigFits() {
		configs.add(mock(TestCustomJavaConfiguration.class));
		configurator = new CustomConfigurator(configs);
		
		configurator.configure(mock(TestCustomServerRequirement.class));
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noCustomConfig() {
		configs.add(mock(TestPropertyRequirementA.class));
		configurator = new CustomConfigurator(configs);
		
		configurator.configure(mock(TestCustomJavaRequirement.class));
	}
	
	@Test
	public void customConfig_moreConfigs() {
		configs.add(mock(TestCustomJavaConfiguration.class));
		configs.add(mock(TestCustomServerConfiguration.class));
		configurator = new CustomConfigurator(configs);
		
		configurator.configure(mock(TestCustomJavaRequirement.class));
		configurator.configure(mock(TestCustomServerRequirement.class));
	}
}
