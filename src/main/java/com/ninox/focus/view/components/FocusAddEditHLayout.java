package com.ninox.focus.view.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.Runo;

public class FocusAddEditHLayout extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	
	public FocusAddEditHLayout() {
		removeAllComponents();
		setStyleName(Runo.PANEL_LIGHT);
		setWidth("100%");
		setSpacing(true);
		setSizeFull();
	}
}
