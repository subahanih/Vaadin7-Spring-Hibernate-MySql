package com.ninox.focus.view.components;

import com.vaadin.ui.TextArea;

public class FocusTextArea extends TextArea {
	private static final long serialVersionUID = 1L;
	
	public FocusTextArea(String caption) {
		setWidth("150px");
		setCaption(caption);
	}
}
