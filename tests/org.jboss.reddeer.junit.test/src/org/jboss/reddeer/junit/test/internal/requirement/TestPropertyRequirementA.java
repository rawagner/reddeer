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
package org.jboss.reddeer.junit.test.internal.requirement;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

public class TestPropertyRequirementA implements Requirement<Annotation>, PropertyConfiguration {

	private String property1;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface PropertyAnnotationA {
	}	
	
	@Override
	public boolean canFulfill() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fulfill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeclaration(Annotation declaration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	public String getProperty1() {
		return property1;
	}

	public void setProperty1(String property1) {
		this.property1 = property1;
	}

}
