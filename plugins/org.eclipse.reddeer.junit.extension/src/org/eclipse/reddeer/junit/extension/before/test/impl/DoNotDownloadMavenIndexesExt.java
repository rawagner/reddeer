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
package org.eclipse.reddeer.junit.extension.before.test.impl;

import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.eclipse.reddeer.junit.extension.ExtensionPriority;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/** 
 * Extension for Extension point org.eclipse.reddeer.junit.before.test. Disables
 * Maven Repo Index downloading on startup Use this system property to
 * enable/disable it:
 *
 * - rd.disableMavenIndex=[true|false]
 * (default=true)
 * 
 * @author vlado pakan
 *
 */
public class DoNotDownloadMavenIndexesExt implements IBeforeTest {

	private static final Logger log = Logger
			.getLogger(DoNotDownloadMavenIndexesExt.class);

	private static final boolean DISABLE_MAVEN_DOWNLOAD_REPO_INDEX = RedDeerProperties.DISABLE_MAVEN_REPOSITORY_DOWNLOAD.getBooleanValue();

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		disableMavenDownloadRepoIndexOnStartup();		
	}

	/** 
	 * See {@link IBeforeTest}.
	 */
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		// do not run before each test
	}

	/** 
	 * Disables downloading Maven repo indexes on startup.
	 */
	private void disableMavenDownloadRepoIndexOnStartup() {
		log.info("MAVEN: 1");
		String updateIndexesPreferenceString = Platform
				.getPreferencesService()
				.getString("org.eclipse.m2e.core", "eclipse.m2.updateIndexes",
						"true", null);
		log.info("MAVEN: 2");
		// Maven is not installed
		if (updateIndexesPreferenceString == null) {
			return;
		}

		// It is already disabled
		if (updateIndexesPreferenceString.equalsIgnoreCase("false")){
			return;
		}

		log.debug("Trying to disable downloading maven repo indexes on startup "
				+ "via Windows > Preferences > Maven");

		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		MavenPreferencePage mavenPreferencePage = new MavenPreferencePage();

		log.info("MAVEN: 3");
		preferencesDialog.open();
		log.info("MAVEN: 4");
		try {
			preferencesDialog.select(mavenPreferencePage);
			log.info("MAVEN: 5");
		} catch (RedDeerException e){
			// Maven is not installed
			preferencesDialog.cancel();
			log.info("MAVEN: 6");
			return;
		}
		mavenPreferencePage.setDownloadRepoIndexOnStartup(false);
		log.info("MAVEN: 7");
		preferencesDialog.ok();
		log.info("MAVEN: 8");
		log.debug("Downloading maven repo indexes on startup disabled");
	}

	/** 
	 * See {@link IBeforeTest}.
	 * @return boolean
	 */
	@Override
	public boolean hasToRun() {
		return DoNotDownloadMavenIndexesExt.DISABLE_MAVEN_DOWNLOAD_REPO_INDEX;
	}

	@Override
	public long getPriority() {
		return ExtensionPriority.DO_NOT_DOWNLOAD_MAVEN_INDICES_PRIORITY;
	}
}
