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
package org.jboss.reddeer.core.handler;

import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.core.util.Display;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.ui.forms.widgets.Section} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class SectionHandler {

	private static SectionHandler instance = null;

	private SectionHandler() {
	}

	/**
	 * Gets instance of SectionHandler.
	 * 
	 * @return instance of SectionHandler
	 */
	public static SectionHandler getInstance() {
		if (instance == null)
			instance = new SectionHandler();
		return instance;
	}
	
	/**
	 * Sets specified {@link Section} to specified expand state.
	 * 
	 * @param section section to handle
	 * @param expanded true for expand specified section, false for collapse
	 */
	public void setExpanded(final Section section, final boolean expanded) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				section.setExpanded(expanded);
			}
		});
	}
}
