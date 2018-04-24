/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.openperspective;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.requirements.exception.RequirementsLayerException;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.condition.MenuItemIsEnabled;

/**
 * Open perspective requirement<br><br>
 * 
 * This {@link Requirement} ensures, that given perspective is active before actual test
 * execution.<br><br>
 * 
 * Annotate test class with {@link OpenPerspective} annotation to have
 * the given perspective opened before the test cases are executed.<br><br>
 * 
 * Example:<br>
 * <pre>
 * {@code @OpenPerspective(DebugPerspective.class)
 * public class TestClass {
 *    // debug perspective will be opened before tests execution
 * }
 * }
 * </pre>
 * @author rhopp
 * 
 */
public class OpenPerspectiveRequirement implements Requirement<OpenPerspective> {
	
	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Marks test class, which requires opening of the specified perspective.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Documented
	public @interface OpenPerspective {
		
		/**
		 * specified perspective.
		 *
		 * @return the class&lt;? extends abstract perspective&gt;
		 */
		Class<? extends AbstractPerspective> value();
		
		/**
		 * If true, perspective will be reset to default. 
		 *
		 * @return true if successful, false otherwise
		 */
		boolean reset() default true;
	}

	private OpenPerspective openPerspective;

	/**
	 * Opens the given perspective.
	 * 
	 * @throws RequirementsLayerException when the given perspective can't be opened
	 */
	@Override
	public void fulfill() {
		try {
			AbstractPerspective perspective = getPerspectiveInstance();
			perspective.open();
			
			if (openPerspective.reset()){
				new WaitUntil(new AbstractWaitCondition() {
					
					@Override
					public boolean test() {
						return perspective.isResetEnabled();
					}
					
				}, TimePeriod.SHORT, false);
				if(!perspective.isResetEnabled()) {
					log.info("Reset Perspective menu is not enabled, skipping");
					return;
				}
				perspective.reset();				
			}
		} catch (Exception e) {
			throw new RequirementsLayerException(
					"Unable to fullffill requirement 'Open Perspective'", e);
		}
	}

	/**
	 * Sets perspective, which will be opened before actual test execution.
	 * 
	 * @param openPerspective annotation defining the perspective to be opened
	 */
	@Override
	public void setDeclaration(OpenPerspective openPerspective) {
		this.openPerspective = openPerspective;

	}

	private AbstractPerspective getPerspectiveInstance()
			throws InstantiationException, IllegalAccessException {
		AbstractPerspective perspectiveInstance = null;
		perspectiveInstance = openPerspective.value().newInstance();
		return perspectiveInstance;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}

	@Override
	public OpenPerspective getDeclaration() {
		return openPerspective;
	}

}
