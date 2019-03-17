package com.ninox.focus.view.components;

import com.vaadin.ui.Button;

public class FocusButton extends Button {
	private static final long serialVersionUID = 1L;
	
	public FocusButton(String caption, String stylename, ClickListener listener) {
		setCaption(caption);
		addStyleName(stylename);
		addClickListener(listener);
	}
	
	public FocusButton(String caption, String stylename) {
		setCaption(caption);
		addStyleName(stylename);
	}
}
