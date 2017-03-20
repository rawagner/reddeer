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
package org.jboss.reddeer.jface.test.dialogs;

import static org.junit.Assert.*;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;
import org.jboss.reddeer.jface.dialogs.MessageTypeEnum;
import org.jboss.reddeer.jface.dialogs.TitleAreaDialog;
import org.jboss.reddeer.jface.test.dialogs.impl.TestingNewWizard;
import org.jboss.reddeer.jface.test.dialogs.impl.TestingTitleAreaDialog;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.junit.After;
import org.junit.Test;

public class TitleAreaDialogTest {

	private static TestingTitleAreaDialog tt;
	private Shell s;
	private NewWizardDialogImpl nw;

	@Test
	public void titleAreaMessagesAndImages() {
		openTitleAreaDialog();
		TitleAreaDialogImpl dialog = new TitleAreaDialogImpl();
		assertEquals(TestingTitleAreaDialog.DEFAULT_MESSAGE, dialog.getMessage());
		assertEquals(MessageTypeEnum.NONE, dialog.getMessageType());
		assertEquals(TestingTitleAreaDialog.TITLE, dialog.getTitle());

		dialog.errorButton();
		assertEquals(TestingTitleAreaDialog.ERROR_MESSAGE, dialog.getMessage());
		assertEquals(MessageTypeEnum.ERROR, dialog.getMessageType());

		dialog.warningButton();
		assertEquals(TestingTitleAreaDialog.WARNING_MESSAGE, dialog.getMessage());
		assertEquals(MessageTypeEnum.WARNING, dialog.getMessageType());

		dialog.infoButton();
		assertEquals(TestingTitleAreaDialog.INFO_MESSAGE, dialog.getMessage());
		assertEquals(MessageTypeEnum.INFO, dialog.getMessageType());

		dialog.noneButton();
		assertEquals(TestingTitleAreaDialog.NONE_MESSAGE, dialog.getMessage());
		assertEquals(MessageTypeEnum.NONE, dialog.getMessageType());

		dialog.errorWithoutProviderButton();
		assertEquals(TestingTitleAreaDialog.ERROR_MESSAGE_WITHOUT_PROVIDER, dialog.getMessage());
		assertEquals(MessageTypeEnum.ERROR, dialog.getMessageType());

	}

	// check if WizardDialog can be used as TitleAreaDialog
	@Test
	public void testWizardDialogAsTitleAreaDialog() {
		nw = new NewWizardDialogImpl();
		nw.open();
		new TitleAreaDialog(TestingNewWizard.NAME);
	}

	// basic shell cannot be used as TitleAreaDialog
	@Test(expected = CoreLayerException.class)
	public void testShellAsTitleAreaDialog() {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				s = new Shell();
				s.setText("Testing shell");
				s.open();

			}
		});
		new TitleAreaDialog("Testing shell");
	}

	public void openTitleAreaDialog() {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				tt = new TestingTitleAreaDialog(null);
				tt.create();
				tt.open();

			}
		});

	}

	@After
	public void closeShell() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (s != null) {
					s.dispose();
				}
				if (tt != null) {
					tt.close();
				}
			}
		});
		if(nw != null && nw.isOpen()){
			nw.cancel();
		}
	}

	private class TitleAreaDialogImpl extends TitleAreaDialog {

		public TitleAreaDialogImpl() {
			super(TestingTitleAreaDialog.TEXT);
		}

		public void errorButton() {
			clickButton(TestingTitleAreaDialog.ERROR_MESSAGE);
		}

		public void warningButton() {
			clickButton(TestingTitleAreaDialog.WARNING_MESSAGE);
		}

		public void infoButton() {
			clickButton(TestingTitleAreaDialog.INFO_MESSAGE);
		}

		public void noneButton() {
			clickButton(TestingTitleAreaDialog.NONE_MESSAGE);
		}

		public void errorWithoutProviderButton() {
			clickButton(TestingTitleAreaDialog.ERROR_MESSAGE_WITHOUT_PROVIDER);
		}

		private void clickButton(String text) {
			new PushButton(text).click();
		}

	}

	private class NewWizardDialogImpl extends NewMenuWizard {

		public NewWizardDialogImpl() {
			super(TestingNewWizard.NAME, TestingNewWizard.CATEGORY, TestingNewWizard.NAME);
		}
	}

}
