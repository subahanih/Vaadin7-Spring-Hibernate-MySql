package com.ninox.focus.view.components;

import com.vaadin.ui.TextField;

public class FocusTextField extends TextField {
	private static final long serialVersionUID = 1L;
	
	public FocusTextField(String caption) {
		setWidth("150px");
		setHeight("22px");
		setCaption(caption);
		setRequired(false);
		setNullRepresentation("");
	}
}
