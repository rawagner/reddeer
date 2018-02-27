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
package org.eclipse.reddeer.workbench.api;

import org.eclipse.swt.graphics.Image;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Menu;

/**
 * Interface with base operations which can be performed with workbench part.
 * 
 * @author Vlado Pakan
 */
public interface WorkbenchPart extends ReferencedComposite {

	/**
	 * Activates workbench part.
	 */
	void activate();

	/**
	 * Close workbench part.
	 */
	void close();

	/**
	 * Returns Title of workbench part.
	 *
	 * @return Title of the workbench part
	 */
	String getTitle();

	/**
	 * Returns ToolTip text of workbench part
	 * 
	 * @return TooTip text of workbench part
	 */
	String getTitleToolTip();

	/**
	 * Returns Title Image of workbench part
	 * 
	 * @return Title Image of workbench part
	 */
	Image getTitleImage();

	/**
	 * Checks if workbench part is active
	 * 
	 * @return true if workbench part is active, false otherwise
	 */
	boolean isActive();

	/**
	 * Checks if editor is dirty.
	 * 
	 * @return true if editor is dirty
	 */
	boolean isDirty();

	/**
	 * Tries to perform save on this editor.
	 */
	void save();

	/**
	 * Closes this editor.
	 * 
	 * @param save
	 *            If true, content will be saved
	 */
	void close(boolean save);

	/**
	 * Returns a context menu associated to the editor.
	 * 
	 * @return Context menu associated to the editor
	 */
	Menu getContextMenu();

}