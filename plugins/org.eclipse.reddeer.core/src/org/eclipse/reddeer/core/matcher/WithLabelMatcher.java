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
package org.eclipse.reddeer.core.matcher;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.eclipse.reddeer.core.lookup.WidgetLookup;

/**
 * Matcher matching text to label of {@link Widget}.
 * 
 * @author Rastislav Wagner
 * @author Radoslav Rabara
 * 
 */
public class WithLabelMatcher extends BaseMatcher<String> {

	private Matcher<String> matcher;
	
	/**
	 * Constructs new WithLabelMatcher matching specified text to label of {@link Widget}
	 * on exact match.
	 * 
	 * @param text text to match label
	 * 
	 */
	public WithLabelMatcher(String text) {
		this(Is.<String>is(text));
	}
	
	/**
	 * Constructs new WithLabelMatcher matching specified text matcher to label of {@link Widget}
	 * on exact match.
	 * 
	 * @param matcher text matcher to match label
	 * 
	 */
	public WithLabelMatcher(Matcher<String> matcher) {
		if(matcher == null)
			throw new NullPointerException("matcher");
		this.matcher = matcher;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("with label ").appendDescriptionOf(matcher);
	}

	/**
	 * Matches specified object to text or text matcher provided in constructor of this object.
	 *
	 * @param item the item
	 * @return true if label of specified object is matching text or
	 * text matcher of this object, false otherwise
	 */
	@Override
	public boolean matches(Object item) {		
		if (item instanceof Control) {
			String widgetLabel = WidgetLookup.getInstance().getLabel((Control)item);
			if (widgetLabel != null) {
				// Ignore asterisk and spaces
				String widgetLabel2 = widgetLabel.trim();
				if (widgetLabel2.endsWith("*")) {
					widgetLabel2 = widgetLabel2.substring(0, widgetLabel2.length() - 1).trim();
				}
				if (matcher.matches(widgetLabel) || matcher.matches(widgetLabel2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget with label:\n" + matcher.toString();
	}
}

