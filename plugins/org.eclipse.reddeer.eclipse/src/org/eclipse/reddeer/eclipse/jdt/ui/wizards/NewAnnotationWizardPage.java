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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating an annotation.
 */
public class NewAnnotationWizardPage extends WizardPage {
	
	public NewAnnotationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given package name.
	 * 
	 * @param packageName
	 *            Package name
	 */
	public void setPackage(String packageName) {
		new LabeledText(referencedComposite, "Package:").setText(packageName);
	}

	/**
	 * Sets a given source folder.
	 * 
	 * @param sourceFolder
	 *            Source folder
	 */
	public void setSourceFolder(String sourceFolder) {
		new LabeledText(referencedComposite, "Source folder:").setText(sourceFolder);
	}

	/**
	 * Sets a given enclosing type.
	 * 
	 * @param enclosing
	 *            Eclosing type
	 */
	public void setEnclosingType(boolean enclosing) {
		new CheckBox(referencedComposite, "Enclosing type:").toggle(enclosing);
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name
	 *            Name
	 */
	public void setName(String name) {
		new LabeledText(referencedComposite, "Name:").setText(name);
	}

	/**
	 * Sets generating comments.
	 * 
	 * @param generate
	 *            Indicates whether to generate comments
	 */
	public void setGenerateComments(boolean generate) {
		new CheckBox(referencedComposite, "Generate comments").toggle(generate);
	}

	/**
	 * Sets public visibility.
	 *
	 * @param isPublic            Is public?
	 */
	public void setPublic(boolean isPublic) {
		new RadioButton(referencedComposite, "public").toggle(isPublic);
	}

	/**
	 * Sets default visibility.
	 * 
	 * @param isDefault
	 *            Is default?
	 */
	public void setDefault(boolean isDefault) {
		new RadioButton(referencedComposite, "default").toggle(isDefault);
	}

	/**
	 * Sets private visibility.
	 * 
	 * @param isPrivate
	 *            Is private?
	 */
	public void setPrivate(boolean isPrivate) {
		new RadioButton(referencedComposite, "private").toggle(isPrivate);
	}

	/**
	 * Sets protected visibility.
	 * 
	 * @param isProtected
	 *            Is protected?
	 */
	public void setProtected(boolean isProtected) {
		new RadioButton(referencedComposite, "protected").toggle(isProtected);
	}

}
